package dev.sleep.gmodels.client;

import dev.sleep.gmodels.client.renderer.GonckoPlayerRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.world.entity.EntityType;

public class MainClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerRenderers();
    }

    public void registerRenderers(){
        EntityRendererRegistry.register(EntityType.PLAYER, GonckoPlayerRenderer::new);
    }

}
