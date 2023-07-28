package rivi.rss;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rivi.rss.entity.EntityMod;
import rivi.rss.item.ItemGroupsMod;
import rivi.rss.item.ItemMod;

public class SimpleStaffsMod implements ModInitializer {

	public static final String MOD_ID = "rivis-simple-staffs";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// LOOT IDs
	private static final Identifier VEX_ID = EntityType.VEX.getLootTableId();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ItemMod.registerModItems();
		EntityMod.registerEntities();
		EntityMod.registerEntityRenderers();
		ItemGroupsMod.registerItemGroups();

		// Loot
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && VEX_ID.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.with(ItemEntry.builder(ItemMod.VEX_WING));
				tableBuilder.pool(poolBuilder);
			}
		});


	}
}