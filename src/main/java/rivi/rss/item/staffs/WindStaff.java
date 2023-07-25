package rivi.rss.item.staffs;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class WindStaff extends AbstractStaffItem{

    protected static int CHARGE_USED = 100;
    protected static int MAX_CHARGE = 1000;
    protected static int STAFF_COOLDOWN = 60;
    protected static double BOOST_HEIGHT = 1.75;
    protected static int EFFECT_DURATION = 200;

    private static final Settings settings = new Settings().maxCount(1).maxDamage(MAX_CHARGE);
    public static Item INSTANCE = new WindStaff(settings);

    public WindStaff(Settings settings) {
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
    public void staffEffect(ItemStack itemStack, World world, PlayerEntity player) {
        player.addVelocity(0, BOOST_HEIGHT, 0);
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, EFFECT_DURATION));
        world.addParticle(ParticleTypes.EXPLOSION,
                player.getX(), player.getY() + 0.5, player.getZ(), 0, 1, 0);
    }
}
