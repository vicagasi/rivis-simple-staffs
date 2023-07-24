package rivi.rss.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class IceProjectile extends ProjectileEntity {

    private int DAMAGE = 4;
    public static EntityType INSTANCE = FabricEntityTypeBuilder
            .create(SpawnGroup.MISC, IceProjectile::new)
            .dimensions(EntityDimensions.fixed(.25f,.25f)).build();
    public IceProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        // Do double damage if enemy is a Blaze
        if(entity instanceof BlazeEntity){
            DAMAGE = DAMAGE * 2;
        }
        // Do damage
        entity.damage(this.getDamageSources().freeze(), DAMAGE);
    }
}
