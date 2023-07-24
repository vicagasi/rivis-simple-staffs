package rivi.rss.item.staffs;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class AbstractStaffItem extends Item {

    protected static int CHARGE_USED = 10;
    protected static int MAX_CHARGE = 1000;
    protected static int STAFF_COOLDOWN = 2;

    private static final Settings settings = new Settings().maxCount(1).maxDamage(MAX_CHARGE);
    public static Item INSTANCE = new AbstractStaffItem(settings);
    public AbstractStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        ItemStack itemStack = player.getStackInHand(hand);

        if(!(itemStack.getDamage() == MAX_CHARGE - 1) && !(player.getItemCooldownManager().isCoolingDown(itemStack.getItem()))){
            if(itemStack.getDamage() > MAX_CHARGE - CHARGE_USED){
                itemStack.setDamage(MAX_CHARGE - 1);
            } else {
                itemStack.setDamage(itemStack.getDamage() + CHARGE_USED);
            }

            player.getItemCooldownManager().set(itemStack.getItem(), STAFF_COOLDOWN);

            return TypedActionResult.pass(player.getStackInHand(hand));

        } else {

            return TypedActionResult.fail(itemStack);

        }
    }
}
