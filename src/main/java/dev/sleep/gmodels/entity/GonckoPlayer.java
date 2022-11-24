package dev.sleep.gmodels.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.GeoReplacedEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationEvent;
import software.bernie.geckolib.core.animation.factory.AnimationFactory;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GonckoPlayer extends LivingEntity implements GeoReplacedEntity {

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public GonckoPlayer() {
        super(EntityType.PLAYER, null);
    }

    @Override
    public void registerControllers(AnimatableManager<?> animatableManager) {
        animatableManager.addController(DefaultAnimations.genericIdleController(this))
                .addController(new AnimationController<>(this, 5, this::poseBody));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    // Create the animation handler for the body segment
    protected PlayState poseBody(AnimationEvent<GonckoPlayer> event) {
        if (event.isMoving()) {
            //Yeah i know
            event.getController().setAnimation(DefaultAnimations.SNEAK);
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    @Override
    public EntityType<?> getReplacingEntityType() {
        return EntityType.PLAYER;
    }

    @Override
    public void createRenderer(Consumer<RenderProvider> consumer) {

    }

    @Override
    public Supplier<RenderProvider> getRenderProvider() {
        return null;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return null;
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return null;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return null;
    }
}
