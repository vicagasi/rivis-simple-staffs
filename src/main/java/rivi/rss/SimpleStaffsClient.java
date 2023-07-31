package rivi.rss;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.*;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.particle.SonicBoomParticle;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import rivi.rss.entity.EntityMod;

public class SimpleStaffsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityMod.IceProjectileEntityType, (context) ->
                new FlyingItemEntityRenderer(context));
        EntityRendererRegistry.register(EntityMod.FlameProjectileEntityType, (context) ->
                new FlyingItemEntityRenderer(context));
        EntityRendererRegistry.register(EntityMod.BounceProjectileEntityType, (context) ->
                new FlyingItemEntityRenderer(context));
    }
}
