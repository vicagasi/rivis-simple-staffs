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

    Box SEARCH_BOX = new Box(-105, -102, -105, 105, 102, 105);

    public void windPush(World world, PlayerEntity player){

        double pX = player.getX();
        double pY = player.getY();
        double pZ = player.getZ();

        Entity entity = world.getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT, null,
                pX, pY, pZ, SEARCH_BOX);

        if(entity != null){

            double eX = entity.getX();
            double eY = entity.getY();
            double eZ = entity.getZ();

            Vec3d pushVec = new Vec3d(pX - eX, pY - eY, pY - eZ);

            entity.addVelocity(pushVec);
            entity.addVelocity(0, 1000, 0); //FIXME
        }
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
