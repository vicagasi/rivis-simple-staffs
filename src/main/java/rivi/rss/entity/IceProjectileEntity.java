package rivi.rss.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SnowballItem;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import rivi.rss.item.ItemMod;

public class IceProjectileEntity extends ThrownItemEntity {
    public IceProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public IceProjectileEntity(World world, LivingEntity owner) {
        super(EntityMod.IceProjectileEntityType, owner, world);
    }

    public IceProjectileEntity(World world, double x, double y, double z) {
        super(EntityMod.IceProjectileEntityType, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ItemMod.ICE_PROJECTILE;
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack)); //PLACEHOLDER
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) { // Also not entirely sure, but probably also has to do with the particles. This method (as well as the previous one) are optional, so if you don't understand, don't include this one.
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void onEntityHit(EntityHitResult entityHitResult) { // called on entity hit.
        super.onEntityHit(entityHitResult);

        Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
        DamageSource damageSource = this.getDamageSources().freeze();

        if (entity instanceof LivingEntity livingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
            int damage = 3;

            // Extra damage agaisnt blazes
            if(livingEntity.getType() == EntityType.BLAZE){
                damage = damage * 3;
            }

            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 1))); // applies a status effect
            entity.damage(damageSource, damage);
        }
    }

    protected void onCollision(HitResult hitResult) { // called on collision with a block
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) { // checks if the world is client
            this.getWorld().sendEntityStatus(this, (byte)3); // particle?
            this.kill(); // kills the projectile
        }

    }

}
