package rivi.rss.item.staffs;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import rivi.rss.entity.EntityMod;
import rivi.rss.entity.IceProjectileEntity;

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
        if (!world.isClient) {
            IceProjectileEntity projectile = new IceProjectileEntity(EntityMod.IceProjectileEntityType, world);
            projectile.setItem(itemStack);
            projectile.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 1.5F, 0F);
            world.spawnEntity(projectile);
        }
    }

}
