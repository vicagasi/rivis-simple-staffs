package rivi.rss.entity;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import rivi.rss.SimpleStaffsMod;
import rivi.rss.item.ItemMod;

public class EntityMod {
    public static final EntityType<IceProjectile> ICE_PROJECTILE = register("ice_projectile", IceProjectile.INSTANCE);


    private static <T extends Entity> EntityType<T> register(String name, EntityType<T> entity) {
        return Registry.register(Registries.ENTITY_TYPE, name, entity);
    }


    public static void registerEntities(){
        SimpleStaffsMod.LOGGER.info("Registering entities for " + SimpleStaffsMod.MOD_ID);
        
    }

    public static void registerEntityRenderers(){
        SimpleStaffsMod.LOGGER.info("Registering entity renderers for " + SimpleStaffsMod.MOD_ID);
        EntityRendererRegistry.register(IceProjectile.INSTANCE, IceProjectileRenderer::new);
    }
}
