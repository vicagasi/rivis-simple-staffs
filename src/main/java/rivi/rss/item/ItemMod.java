package rivi.rss.item;


import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import rivi.rss.SimpleStaffsMod;
import rivi.rss.item.staffs.*;

public class ItemMod {

    public static final Item VEX_WING = registerItem("vex_wing", new Item(new FabricItemSettings()));
    // Staffs
    public static final Item ICE_STAFF = registerItem("frost_staff", FrostStaffItem.INSTANCE);
    public static final Item WIND_STAFF = registerItem("wind_staff", WindStaffItem.INSTANCE);
    public static final Item FIRE_STAFF = registerItem("fire_staff", FireStaffItem.INSTANCE);
    public static final Item WEATHER_STAFF = registerItem("weather_staff", WeatherStaffItem.INSTANCE);
    public static final Item EC_STAFF = registerItem("ec_staff", EnderChestStaffItem.INSTANCE);

    public static final Item THUNDER_STAFF = registerItem("thunder_staff", ThunderStaffItem.INSTANCE);
    public static final Item LATENT_STAFF = registerItem("latent_staff", new Item(new FabricItemSettings()));

    // Misc
    public static final Item ICE_PROJECTILE = registerItem("frost_projectile", new Item(new FabricItemSettings()));
    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries){
        entries.add(VEX_WING);
    }
    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(SimpleStaffsMod.MOD_ID, name), item);
    }

    public static void registerModItems(){
        SimpleStaffsMod.LOGGER.info("Registering items for " + SimpleStaffsMod.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ItemMod::addItemsToIngredientTabItemGroup);
    }

}