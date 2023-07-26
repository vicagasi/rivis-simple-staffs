package rivi.rss;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import rivi.rss.entity.EntityMod;

public class SimpleStaffsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityMod.IceProjectileEntityType, (context) ->
                new FlyingItemEntityRenderer(context));
        EntityRendererRegistry.register(EntityMod.FlameProjectileEntityType, (context) ->
                new FlyingItemEntityRenderer(context));
    }
}
