package rivi.rss.entity;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import rivi.rss.SimpleStaffsMod;

public class EntityMod {
    public static final EntityType<IceProjectileEntity> IceProjectileEntityType = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(SimpleStaffsMod.MOD_ID, "frost_staff_projectile"),
            FabricEntityTypeBuilder.<IceProjectileEntity>create(SpawnGroup.MISC, IceProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.05F, 0.05F))
                    .trackRangeBlocks(20).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
                    .build()
    );

    public static final EntityType<FlameProjectileEntity> FlameProjectileEntityType = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(SimpleStaffsMod.MOD_ID, "flame_staff_projectile"),
            FabricEntityTypeBuilder.<FlameProjectileEntity>create(SpawnGroup.MISC, FlameProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.05F, 0.05F))
                    .trackRangeBlocks(20).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
                    .build()
    );

    public static final EntityType<BounceProjectileEntity> BounceProjectileEntityType = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(SimpleStaffsMod.MOD_ID, "bounce_staff_projectile"),
            FabricEntityTypeBuilder.<BounceProjectileEntity>create(SpawnGroup.MISC, BounceProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.05F, 0.05F))
                    .trackRangeBlocks(20).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
                    .build()
    );


    private static <T extends Entity> EntityType<T> register(String name, EntityType<T> entity) {
        return Registry.register(Registries.ENTITY_TYPE, name, entity);
    }


    public static void registerEntities(){
        SimpleStaffsMod.LOGGER.info("Registering entities for " + SimpleStaffsMod.MOD_ID);
    }

    public static void registerEntityRenderers(){
        SimpleStaffsMod.LOGGER.info("Registering entity renderers for " + SimpleStaffsMod.MOD_ID);
        // EntityRendererRegistry.register(IceProjectileEntity.INSTANCE, IceProjectileRenderer::new);
    }
}
