package rivi.rss.loot;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;
import rivi.rss.SimpleStaffsMod;
import rivi.rss.item.ItemMod;

public class LootMod {
    /* private static final Identifier VEX_LOOT_TABLE_ID = EntityType.VEX.getLootTableId();

    LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
        if (source.isBuiltin() && VEX_LOOT_TABLE_ID.equals(id)) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .with(ItemEntry.builder(ItemMod.VEX_WING));

            tableBuilder.pool(poolBuilder);
        }
    }); */

    public static void registerLootPools(){
        SimpleStaffsMod.LOGGER.info("Registering loot pools for " + SimpleStaffsMod.MOD_ID);
    }
}
