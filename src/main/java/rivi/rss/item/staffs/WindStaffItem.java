package rivi.rss.item.staffs;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class WindStaffItem extends AbstractStaffItem{

    protected static int CHARGE_USED = 100;
    protected static int MAX_CHARGE = 1000;
    protected static int STAFF_COOLDOWN = 60;

    protected static SoundEvent STAFF_SOUND = SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH; //PLACEHOLDER
    protected static double BOOST_HEIGHT = 1.75;
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

    @Override
    public void staffEffect(ItemStack itemStack, World world, PlayerEntity player) {
        player.addVelocity(0, BOOST_HEIGHT, 0);
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, EFFECT_DURATION));
        world.addParticle(ParticleTypes.EXPLOSION, //PLACEHOLDER
                player.getX(), player.getY() + 1, player.getZ(), 0, BOOST_HEIGHT, 0);
    }
}
