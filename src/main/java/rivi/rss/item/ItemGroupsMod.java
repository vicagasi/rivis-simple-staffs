package rivi.rss.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import rivi.rss.SimpleStaffsMod;
import rivi.rss.item.staffs.FireStaffItem;

public class ItemGroupsMod {
    public static final ItemGroup RSS_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(SimpleStaffsMod.MOD_ID, "simplestaffs"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.simplestaffs"))
                    .icon(() -> new ItemStack(ItemMod.LATENT_STAFF)).entries((displayContext, entries) -> {
                        // Item entries go here
                        entries.add(ItemMod.ICE_STAFF);
                        entries.add(ItemMod.FIRE_STAFF);
                        entries.add(ItemMod.WIND_STAFF);
                        entries.add(ItemMod.WEATHER_STAFF);
                        entries.add(ItemMod.EC_STAFF);
                        entries.add(ItemMod.THUNDER_STAFF);
                        entries.add(ItemMod.LATENT_STAFF);
                        entries.add(ItemMod.VEX_WING);
                        // entries.add(ItemMod.ICE_PROJECTILE);
                    }).build());

    public static void registerItemGroups(){
        SimpleStaffsMod.LOGGER.info("Registering Item Groups for " + SimpleStaffsMod.MOD_ID);
    }
}
