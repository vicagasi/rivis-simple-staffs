package rivi.rss;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rivi.rss.entity.EntityMod;
import rivi.rss.item.ItemGroupsMod;
import rivi.rss.item.ItemMod;

public class SimpleStaffsMod implements ModInitializer {
	public static final String MOD_ID = "rivis-simple-staffs";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ItemMod.registerModItems();
		EntityMod.registerEntities();
		EntityMod.registerEntityRenderers();
		ItemGroupsMod.registerItemGroups();
	}
}