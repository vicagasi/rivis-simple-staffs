package rivi.rss.item.staffs;

import com.google.common.math.Stats;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public abstract class AbstractStaffItem extends Item {

    protected static int CHARGE_USED = 10;
    protected static int MAX_CHARGE = 1000;
    protected static int STAFF_COOLDOWN = 2;

    private static final Settings settings = new Settings().maxCount(1).maxDamage(MAX_CHARGE);
    // public static Item INSTANCE = new AbstractStaffItem(settings);
    public AbstractStaffItem(Settings settings) {
        super(settings);
    }

    public abstract void staffEffect(ItemStack itemStack, World world, PlayerEntity player);


    public int getMaxCharge(){
        return MAX_CHARGE;
    }

    public int getChargeUsed(){
        return CHARGE_USED;
    }

    public int getGetCooldown(){
        return STAFF_COOLDOWN;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        ItemStack itemStack = player.getStackInHand(hand);

        int maxCharge = getMaxCharge();
        int chargeUsed = getChargeUsed();
        int staffCooldown = getGetCooldown();

        // If there is charge left and it's not on cool down...
        if(!(itemStack.getDamage() == maxCharge - 1) && !(player.getItemCooldownManager().isCoolingDown(itemStack.getItem()))){
            if(itemStack.getDamage() > maxCharge - chargeUsed){
                itemStack.setDamage(maxCharge - 1);
            } else {
                itemStack.setDamage(itemStack.getDamage() + chargeUsed);
            }

            // Set cooldown
            player.getItemCooldownManager().set(itemStack.getItem(), staffCooldown);

            // Do what the staff does
            staffEffect(itemStack, world, player);

            return TypedActionResult.pass(player.getStackInHand(hand));

        } else {

            return TypedActionResult.fail(itemStack);

        }
    }
}
