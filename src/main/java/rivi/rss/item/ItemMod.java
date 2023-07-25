package rivi.rss.item;


import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import rivi.rss.SimpleStaffsMod;
import rivi.rss.item.staffs.AbstractStaffItem;
import rivi.rss.item.staffs.FrostStaff;
import rivi.rss.item.staffs.WindStaff;

public class ItemMod {

    public static final Item VEX_WING = registerItem("vex_wing", new Item(new FabricItemSettings()));
    public static final Item ICE_STAFF = registerItem("frost_staff", FrostStaff.INSTANCE);
    public static final Item WIND_STAFF = registerItem("wind_staff", WindStaff.INSTANCE);
    public static final Item LATENT_STAFF = registerItem("latent_staff", new Item(new FabricItemSettings()));
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