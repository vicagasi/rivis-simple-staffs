package rivi.rss.item.staffs;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rivi.rss.entity.IceProjectile;

public class FrostStaff extends AbstractStaffItem {

    protected static int CHARGE_USED = 10;
    protected static int MAX_CHARGE = 1000;
    protected static int STAFF_COOLDOWN = 2;
    protected static final float SPEED = 10f;

    private static final Settings settings = new Settings().maxCount(1).maxDamage(MAX_CHARGE);
    public static Item INSTANCE = new FrostStaff(settings);

    public FrostStaff(Settings settings) {
        super(settings);
    }

    @Override
    public void staffEffect(ItemStack itemStack, World world, PlayerEntity player) {
        IceProjectile projectile = new IceProjectile(IceProjectile.INSTANCE, world);
        // projectile.setVelocity(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
        //shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, 1.5F, 1.0F);
        projectile.setVelocity(player, 0, 0, 0, SPEED, 0);
        world.spawnEntity(projectile);
    }
}
