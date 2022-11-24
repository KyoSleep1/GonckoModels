package dev.sleep.gmodels.client.renderer.model;

import dev.sleep.gmodels.entity.GonckoPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationEvent;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GonckoPlayerModel extends DefaultedEntityGeoModel<GonckoPlayer> {

    public GonckoPlayerModel() {
        super(new ResourceLocation(GeckoLib.ModID, "mutant_zombie"));
    }

    @Override
    public void setCustomAnimations(GonckoPlayer animatable, long instanceId, AnimationEvent<GonckoPlayer> animationEvent) {
        CoreGeoBone head = getAnimationProcessor().getBone("bipedHead");
        if (head != null) {
            EntityModelData entityData = animationEvent.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
