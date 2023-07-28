package rivi.rss.item.staffs;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class WindStaffItem extends AbstractStaffItem{

    protected static int CHARGE_USED = 100;
    protected static int MAX_CHARGE = 1000;
    protected static int STAFF_COOLDOWN = 60;

    protected static SoundEvent STAFF_SOUND = SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH; //PLACEHOLDER
    protected static double BOOST_HEIGHT = 1.75;
    protected static double PUSH_AMOUNT = 1;
    protected static int EFFECT_DURATION = 200;

    private static final Settings settings = new Settings().maxCount(1).maxDamage(MAX_CHARGE);
    public static Item INSTANCE = new WindStaffItem(settings);

    public WindStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getChargeUsed(){
        return CHARGE_USED;
    }

    @Override
    public int getGetCooldown(){
        return STAFF_COOLDOWN;
    }

    @Override
    public SoundEvent getStaffSound(){
        return STAFF_SOUND;
    }

    // Need to figure out how to make switch statement use doubles to avoid else if hell
    // Forgive this garbage ass hard coded implementation, I'm just a little stupid
    public double determineMultiplyer(double p, double e){
        double x = Math.abs(p - e);
        if(x <= 0.33)
            return 1.20;
        else if (x <= 0.66)
            return 1.10;
        else if (x <= 0.99)
            return 1.00;
        else if (x <= 1.33)
            return 0.90;
        else if (x <= 1.66)
            return 0.80;
        else if (x <= 1.99)
            return 0.60;
        else if (x <= 2.33)
            return 0.40;
        else if (x <= 2.66)
            return 0.20;
        else
            return 0.10;
    }

    public void windPush(World world, PlayerEntity player){

        // Player coords
        double pX = player.getX();
        double pY = player.getY();
        double pZ = player.getZ();

        // Box bounds
        double bbX1 = pX + 3;
        double bbY1 = pY + 2;
        double bbZ1 = pZ + 3;
        double bbX2 = pX - 3;
        double bbY2 = pY - 2;
        double bbZ2 = pZ - 3;

        Box searchBox = new Box(bbX2, bbY2, bbZ2,
                bbX1, bbY1, bbZ1);

        List<LivingEntity> entities = world.getTargets(LivingEntity.class, TargetPredicate.DEFAULT, player, searchBox);

        entities.forEach(entity -> {
            if(entity != null){

                // Entity cords
                double eX = entity.getX();
                double eY = entity.getY();
                double eZ = entity.getZ();

                // Multipliers are yuck, more math savvy person probably has a better solution
                Vec3d pushVec = new Vec3d(-(pX - eX) * determineMultiplyer(pX, eX),
                        -(pY - eY) + 1 * 0.5,
                        -(pZ - eZ) * determineMultiplyer(pZ, eZ));

                entity.addVelocity(pushVec);
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, EFFECT_DURATION / 2));
            }
        });


    }

    @Override
    public void staffEffect(ItemStack itemStack, World world, PlayerEntity player) {
        windPush(world, player);
        player.addVelocity(0, BOOST_HEIGHT, 0);
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, EFFECT_DURATION));
        world.addParticle(ParticleTypes.EXPLOSION, //PLACEHOLDER
                player.getX(), player.getY() + 1, player.getZ(), 0, BOOST_HEIGHT, 0);

    }
}
