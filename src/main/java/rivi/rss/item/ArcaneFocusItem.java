package rivi.rss.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ArcaneFocusItem extends Item {

    protected static int MAX_CHARGE = 500;

    private static final Settings settings = new Settings().maxCount(1).maxDamage(MAX_CHARGE);
    public static Item INSTANCE = new ArcaneFocusItem(settings);

    public ArcaneFocusItem(Settings settings) {
        super(settings);
    }

}
