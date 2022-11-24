package dev.sleep.gmodels.mixin.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.sleep.gmodels.client.renderer.GonckoPlayerRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity> {

    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "HEAD"), cancellable = true)
    public void injectGeckolibRenderer(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        if (!(entity instanceof AbstractClientPlayer clientPlayer)) {
            return;
        }

        GonckoPlayerRenderer gonckoRenderer = this.getGeckoRenderer();
        if(gonckoRenderer == null){
            return;
        }

        gonckoRenderer.render(clientPlayer, entityYaw, partialTicks, matrixStack, buffer, packedLight);
        ci.cancel();
    }

    //Access list
    public GonckoPlayerRenderer getGeckoRenderer() {
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderer<?> entityRenderer = ((EntityRenderDispatcherAccessor) entityRenderDispatcher).getRenderers().get(EntityType.PLAYER);

        if (!(entityRenderer instanceof GonckoPlayerRenderer gonckoRenderer)) {
            return null;
        }

        return gonckoRenderer;
    }
}
