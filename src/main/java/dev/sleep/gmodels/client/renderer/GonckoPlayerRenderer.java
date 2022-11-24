package dev.sleep.gmodels.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.sleep.gmodels.client.renderer.model.GonckoPlayerModel;
import dev.sleep.gmodels.entity.GonckoPlayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;
import software.bernie.geckolib.renderer.layer.ItemArmorGeoLayer;

public class GonckoPlayerRenderer extends GeoReplacedEntityRenderer<Player, GonckoPlayer> {

    // Pre-define our bone names for easy and consistent reference later
    private static final String LEFT_HAND = "bipedHandLeft";
    private static final String RIGHT_HAND = "bipedHandRight";
    private static final String LEFT_BOOT = "armorBipedLeftFoot";
    private static final String RIGHT_BOOT = "armorBipedRightFoot";
    private static final String LEFT_BOOT_2 = "armorBipedLeftFoot2";
    private static final String RIGHT_BOOT_2 = "armorBipedRightFoot2";
    private static final String LEFT_ARMOR_LEG = "armorBipedLeftLeg";
    private static final String RIGHT_ARMOR_LEG = "armorBipedRightLeg";
    private static final String LEFT_ARMOR_LEG_2 = "armorBipedLeftLeg2";
    private static final String RIGHT_ARMOR_LEG_2 = "armorBipedRightLeg2";
    private static final String CHESTPLATE = "armorBipedBody";
    private static final String RIGHT_SLEEVE = "armorBipedRightArm";
    private static final String LEFT_SLEEVE = "armorBipedLeftArm";
    private static final String HELMET = "armorBipedHead";

    private Player cachedPlayer;

    public GonckoPlayerRenderer(EntityRendererProvider.Context renderManager){
        super(renderManager, new GonckoPlayerModel(), new GonckoPlayer());
        // Add some armor rendering
        addRenderLayer(new ItemArmorGeoLayer<>(this) {

            @Override
            public void preRender(PoseStack poseStack, GonckoPlayer animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
                this.mainHandStack = cachedPlayer.getItemBySlot(EquipmentSlot.MAINHAND);
                this.offhandStack = cachedPlayer.getItemBySlot(EquipmentSlot.OFFHAND);
                this.helmetStack = cachedPlayer.getItemBySlot(EquipmentSlot.HEAD);
                this.chestplateStack = cachedPlayer.getItemBySlot(EquipmentSlot.CHEST);
                this.leggingsStack = cachedPlayer.getItemBySlot(EquipmentSlot.LEGS);
                this.bootsStack = cachedPlayer.getItemBySlot(EquipmentSlot.FEET);
            }

            @Nullable
            @Override
            protected ItemStack getArmorItemForBone(GeoBone bone, GonckoPlayer animatable) {
                // Return the items relevant to the bones being rendered for additional rendering
                return switch (bone.getName()) {
                    case LEFT_BOOT, RIGHT_BOOT, LEFT_BOOT_2, RIGHT_BOOT_2 -> this.bootsStack;
                    case LEFT_ARMOR_LEG, RIGHT_ARMOR_LEG, LEFT_ARMOR_LEG_2, RIGHT_ARMOR_LEG_2 -> this.leggingsStack;
                    case CHESTPLATE, RIGHT_SLEEVE, LEFT_SLEEVE -> this.chestplateStack;
                    case HELMET -> this.helmetStack;
                    default -> null;
                };
            }

            @NotNull
            @Override
            protected EquipmentSlot getEquipmentSlotForBone(GeoBone bone, ItemStack stack, GonckoPlayer animatable) {
                return switch (bone.getName()) {
                    case LEFT_BOOT, RIGHT_BOOT, LEFT_BOOT_2, RIGHT_BOOT_2 -> EquipmentSlot.FEET;
                    case LEFT_ARMOR_LEG, RIGHT_ARMOR_LEG, LEFT_ARMOR_LEG_2, RIGHT_ARMOR_LEG_2 -> EquipmentSlot.LEGS;
                    case RIGHT_SLEEVE -> EquipmentSlot.MAINHAND;
                    case LEFT_SLEEVE -> EquipmentSlot.OFFHAND;
                    case CHESTPLATE -> EquipmentSlot.CHEST;
                    case HELMET -> EquipmentSlot.HEAD;
                    default -> super.getEquipmentSlotForBone(bone, stack, animatable);
                };
            }

            @NotNull
            @Override
            protected ModelPart getModelPartForBone(GeoBone bone, EquipmentSlot slot, ItemStack stack, GonckoPlayer animatable, HumanoidModel<?> baseModel) {
                return switch (bone.getName()) {
                    case LEFT_BOOT, LEFT_BOOT_2, LEFT_ARMOR_LEG, LEFT_ARMOR_LEG_2 -> baseModel.leftLeg;
                    case RIGHT_BOOT, RIGHT_BOOT_2, RIGHT_ARMOR_LEG, RIGHT_ARMOR_LEG_2 -> baseModel.rightLeg;
                    case RIGHT_SLEEVE -> baseModel.rightArm;
                    case LEFT_SLEEVE -> baseModel.leftArm;
                    case CHESTPLATE -> baseModel.body;
                    case HELMET -> baseModel.head;
                    default -> super.getModelPartForBone(bone, slot, stack, animatable, baseModel);
                };
            }
        });

        // Add some held item rendering
        addRenderLayer(new BlockAndItemGeoLayer<>(this) {
            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, GonckoPlayer animatable) {
                // Retrieve the items in the entity's hands for the relevant bone
                return switch (bone.getName()) {
                    case LEFT_HAND -> cachedPlayer.getOffhandItem();
                    case RIGHT_HAND -> cachedPlayer.getMainHandItem();
                    default -> null;
                };
            }

            @Override
            protected ItemTransforms.TransformType getTransformTypeForStack(GeoBone bone, ItemStack stack, GonckoPlayer animatable) {
                // Apply the camera transform for the given hand
                return switch (bone.getName()) {
                    case LEFT_HAND, RIGHT_HAND -> ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
                    default -> ItemTransforms.TransformType.NONE;
                };
            }

            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, GonckoPlayer animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                if (stack == cachedPlayer.getMainHandItem()) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90f));

                    if (stack.getItem() instanceof ShieldItem)
                        poseStack.translate(0, 0.125, -0.25);
                }
                else if (stack == cachedPlayer.getOffhandItem()) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90f));

                    if (stack.getItem() instanceof ShieldItem) {
                        poseStack.translate(0, 0.125, 0.25);
                        poseStack.mulPose(Axis.YP.rotationDegrees(180));
                    }
                }

                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }
        });
    }

    @Override
    public void render(@NotNull Player player, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        cachedPlayer = player;
        super.render(player, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
