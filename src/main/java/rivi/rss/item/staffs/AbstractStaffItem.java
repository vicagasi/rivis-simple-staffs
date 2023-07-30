package rivi.rss.item.staffs;

import com.google.common.math.Stats;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import rivi.rss.item.ArcaneFocusItem;
import rivi.rss.item.ItemMod;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractStaffItem extends Item {

    protected static int CHARGE_USED = 10;
    protected static int MAX_CHARGE = 1000;
    protected static int STAFF_COOLDOWN = 2;

    protected static SoundEvent STAFF_SOUND = SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP;

    private static final Settings settings = new Settings().maxCount(1).maxDamage(MAX_CHARGE);
    // public static Item INSTANCE = new AbstractStaffItem(settings);
    public AbstractStaffItem(Settings settings) {
        super(settings);
    }

    // Getters
    public int getMaxCharge(){
        return MAX_CHARGE;
    }

    public int getChargeUsed(){
        return CHARGE_USED;
    }

    public int getGetCooldown(){
        return STAFF_COOLDOWN;
    }

    public SoundEvent getStaffSound(){
        return STAFF_SOUND;
    }

    // Main methods
    protected void playStaffSound(World world, PlayerEntity player){
        if (!world.isClient) {
            world.playSound(null, player.getBlockPos(), getStaffSound(), SoundCategory.PLAYERS, 1f, 1f);
        }
    }

    public abstract void staffEffect(ItemStack itemStack, World world, PlayerEntity player);

    // Overrides

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    public boolean canBeDrained(ItemStack itemStack, int chargeUsed){
        return !(itemStack.getDamage() == MAX_CHARGE - 1);
    }

    public void drainCharge(ItemStack itemStack, int chargeUsed) {
        if (!(itemStack.getDamage() == MAX_CHARGE - 1)) {
            if (itemStack.getDamage() > MAX_CHARGE - chargeUsed) {
                itemStack.setDamage(MAX_CHARGE - 1);
            } else {
                itemStack.setDamage(itemStack.getDamage() + chargeUsed);
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        ItemStack itemStack = player.getStackInHand(hand);
        ItemStack focusStack = null;
        Inventory inventory = player.getInventory();

        int maxCharge = getMaxCharge();
        int chargeUsed = getChargeUsed();
        int staffCooldown = getGetCooldown();

        boolean hasFocus = false;

        Set<Item> focusItems = new HashSet<>();
        focusItems.add(ItemMod.ARCANE_FOCUS);

        // Check for Arcane Focuses
        if(inventory.containsAny(focusItems)){
            for (int i = 0; i < inventory.size(); ++i) {
                ItemStack tempStack = inventory.getStack(i);
                if (!itemStack.getItem().equals(ItemMod.ARCANE_FOCUS)) {
                    if(canBeDrained(tempStack, chargeUsed)){
                        focusStack = tempStack;
                        hasFocus = true;
                        break;
                    }
                }
            }
        }


        // Drain focus and do stuff if there is one
        if(hasFocus && !(player.getItemCooldownManager().isCoolingDown(itemStack.getItem()))){

            drainCharge(focusStack, chargeUsed);

            // Set cooldown
            player.getItemCooldownManager().set(itemStack.getItem(), staffCooldown);

            // Play staff sound
            playStaffSound(world, player);

            // Do what the staff does
            staffEffect(itemStack, world, player);

            return TypedActionResult.success(player.getStackInHand(hand));
        }

        // If there is charge left and it's not on cool down...
        if(!(itemStack.getDamage() == maxCharge - 1) && !(player.getItemCooldownManager().isCoolingDown(itemStack.getItem()))){
            // Drain charge
            drainCharge(itemStack, chargeUsed);

            // Set cooldown
            player.getItemCooldownManager().set(itemStack.getItem(), staffCooldown);

            // Play staff sound
            playStaffSound(world, player);

            // Do what the staff does
            staffEffect(itemStack, world, player);

            return TypedActionResult.success(player.getStackInHand(hand));

        } else {

            return TypedActionResult.fail(itemStack);

        }
    }
}
