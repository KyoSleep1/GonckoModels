package dev.sleep.gmodels.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.GeoReplacedEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.factory.AnimationFactory;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static software.bernie.geckolib.constant.DefaultAnimations.IDLE;
import static software.bernie.geckolib.constant.DefaultAnimations.WALK;

public class GonckoPlayer extends LivingEntity implements GeoReplacedEntity {

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public GonckoPlayer() {
        super(EntityType.PLAYER, null);
    }

    @Override
    public void registerControllers(AnimatableManager<?> animatableManager) {
        animatableManager.addController(this.genericWalkIdleController(this));
    }

    public <T extends GeoAnimatable> AnimationController<T> genericWalkIdleController(T animatable) {
        return new AnimationController<>(animatable, "Walk/Idle", 0, event -> {
            if(!event.isMoving()){
                event.getController().setAnimation(IDLE);
                event.getController().setAnimationSpeed(1);
                return PlayState.CONTINUE;
            }

            event.getController().setAnimationSpeed(2);
            event.getController().setAnimation(WALK);
            return PlayState.CONTINUE;
        });
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
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
