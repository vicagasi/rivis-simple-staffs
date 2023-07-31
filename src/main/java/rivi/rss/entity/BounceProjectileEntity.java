package rivi.rss.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.Random;

public class BounceProjectileEntity extends ThrownItemEntity {

    protected int particleTimer = 0;
    protected final int BOUNCE_MAX = 5;
    protected int bounceCounter = 0;

    public BounceProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public BounceProjectileEntity(World world, LivingEntity owner) {
        super(EntityMod.BounceProjectileEntityType, owner, world);
    }

    public BounceProjectileEntity(World world, double x, double y, double z) {
        super(EntityMod.BounceProjectileEntityType, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SLIME_BALL;
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SLIME : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack)); //PLACEHOLDER
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
            int damage = 6;

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

    protected void wallBounce(HitResult hitResult) { // called on collision with a block
        if (!this.getWorld().isClient) { // checks if the world is client
            if(bounceCounter >= BOUNCE_MAX){

                onCollision(hitResult);

            } else {

                Vec3d vec3d = this.getVelocity();
                double d = this.getX() + vec3d.x;
                double e = this.getY() + vec3d.y;
                double f = this.getZ() + vec3d.z;

                BlockHitResult blockHitResult = (BlockHitResult)hitResult;
                BlockPos blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
                // BlockState blockState = this.getWorld().getBlockState(blockPos);

                double bX = blockPos.getX();
                double bY = blockPos.getY();
                double bZ = blockPos.getZ();
                Vec3d blockVec = new Vec3d(bX, bY, bZ);
                blockVec = blockVec.normalize();

                // reflect vector
                Vec3d bounceVec = (blockVec.multiply(vec3d).multiply(2).multiply(blockVec.subtract(vec3d)).negate());
                this.setVelocity(bounceVec);
                bounceCounter++;
            }

        }

    }

    protected void randomTrail(){
        Random rng = new Random();
        particleTimer++;
        if(particleTimer <= 30){

            particleTimer = 0;

            for(int i = 0; i < 1 + rng.nextInt(2); i++){
                this.getWorld().addParticle(ParticleTypes.ITEM_SLIME,
                        this.getX() - 0.5 + rng.nextDouble(1.0), this.getY() - 0.5 + rng.nextDouble(1.0), this.getZ() - 0.5 + rng.nextDouble(1.0),
                        0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        boolean bl = false;
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
            BlockState blockState = this.getWorld().getBlockState(blockPos);
            if (blockState.isOf(Blocks.NETHER_PORTAL)) {
                this.setInNetherPortal(blockPos);
                bl = true;
            } else if (blockState.isOf(Blocks.END_GATEWAY)) {
                BlockEntity blockEntity = this.getWorld().getBlockEntity(blockPos);
                if (blockEntity instanceof EndGatewayBlockEntity && EndGatewayBlockEntity.canTeleport(this)) {
                    EndGatewayBlockEntity.tryTeleportingEntity(this.getWorld(), blockPos, blockState, this, (EndGatewayBlockEntity)blockEntity);
                }

                bl = true;
            }
        }

        if (hitResult.getType() != HitResult.Type.MISS && !bl) {
            this.wallBounce(hitResult);
        }

        this.checkBlockCollision();
        Vec3d vec3d = this.getVelocity();
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.updateRotation();
        float h;
        if (this.isTouchingWater()) {
            for(int i = 0; i < 4; ++i) {
                float g = 0.25F;
                this.getWorld().addParticle(ParticleTypes.BUBBLE, d - vec3d.x * 0.25D, e - vec3d.y * 0.25D, f - vec3d.z * 0.25D, vec3d.x, vec3d.y, vec3d.z);
            }

            h = 0.8F;
        } else {
            h = 0.99F;
        }

        this.setVelocity(vec3d.multiply((double)h));
        if (!this.hasNoGravity()) {
            Vec3d vec3d2 = this.getVelocity();
            this.setVelocity(vec3d2.x, vec3d2.y - (double)this.getGravity(), vec3d2.z);
        }

        randomTrail();

        this.setPosition(d, e, f);
    }
}