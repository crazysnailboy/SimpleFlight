package deep_ci.mcmods.simpleflight;

import org.apache.logging.log4j.Level;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

public final class SimpleConfig
{
	protected final Configuration config;
	private final String ITEMS = "Items";
	private final String ENCHANTS = "Enchantments";
	protected boolean enableTechBased;
	protected boolean enableGliderBased;
	protected boolean enableMagicBased;
	protected boolean enableXPRecipe;
	protected int id_flyFaster;
	protected int id_flyLonger;
	protected int maxLevel_flyFaster;
	protected int maxLevel_flyLonger;
	protected Item flyFaster_item;
	protected Item flyLonger_item;
	protected Item featherFalling_item;

	// ----------

	public int goldFeatherWings_maxFlaps = 14;
	public int goldLeatherWings_maxFlaps = 16;
	public int goldClothWings_maxFlaps = 12;

	public int ironFeatherWings_maxFlaps = 22;
	public int ironLeatherWings_maxFlaps = 24;
	public int ironClothWings_maxFlaps = 20;

	public int chainFeatherWings_maxFlaps = 16;
	public int chainLeatherWings_maxFlaps = 18;
	public int chainClothWings_maxFlaps = 14;

	public int diamondFeatherWings_maxFlaps = 46;
	public int diamondLeatherWings_maxFlaps = 48;
	public int diamondClothWings_maxFlaps = 44;

	public int goldFeatherWings_coolDown = 5;
	public int goldLeatherWings_coolDown = 6;
	public int goldClothWings_coolDown = 6;

	public int ironFeatherWings_coolDown = 11;
	public int ironLeatherWings_coolDown = 12;
	public int ironClothWings_coolDown = 12;

	public int chainFeatherWings_coolDown = 7;
	public int chainLeatherWings_coolDown = 8;
	public int chainClothWings_coolDown = 8;

	public int diamondFeatherWings_coolDown = 8;
	public int diamondLeatherWings_coolDown = 9;
	public int diamondClothWings_coolDown = 9;

	// ----------

	public SimpleConfig(Configuration configFile)
	{
		this.config = configFile;
		this.configure();
	}

	public void configure()
	{
		this.config.load();

		this.id_flyLonger = this.config.get("Enchantments", "MoreFlapsID", 78).getInt(78);
		this.id_flyFaster = this.config.get("Enchantments", "QuickerFlapsID", 79).getInt(79);

		assert ((this.id_flyLonger >= 63) || (this.id_flyLonger <= 255)) : "MoreFlaps ID must be greater than 63, but lower than 255!";
		assert ((this.id_flyFaster >= 63) || (this.id_flyFaster <= 255)) : "QuickerFlaps ID must be greater than 63, but lower than 255!";
		assert (this.id_flyLonger != this.id_flyFaster) : "MoreFlaps ID cannot be the same as QuickerFlaps ID";

		this.maxLevel_flyFaster = this.config.get("Enchantments", "MoreFlapsMaxLevel", 8).getInt();
		this.maxLevel_flyLonger = this.config.get("Enchantments", "QuickerFlapsMaxLevel", 8).getInt();

		String moreFlapsItemString = this.config.get("Enchantments", "MoreFlapsEnchantItem", "minecraft:iron_ingot").getString();
		String quickFlapsItemString = this.config.get("Enchantments", "QuickerFlapsEnchantItem", "minecraft:redstone").getString();
		String featherFallItemString = this.config.get("Enchantments", "FeatherFallEnchantItem", "minecraft:feather").getString();
		if (getItem(moreFlapsItemString) == null)
		{
			SimpleFlight.logger.log(Level.INFO, "Item (" + moreFlapsItemString + ") not found. Using Iron Ingot instead.");
			moreFlapsItemString = "iron_ingot";
		}
		this.flyLonger_item = getItem(moreFlapsItemString);
		if (getItem(quickFlapsItemString) == null)
		{
			SimpleFlight.logger.log(Level.INFO, "Item (" + quickFlapsItemString + ") not found. Using Redstone instead.");
			quickFlapsItemString = "redstone";
		}
		this.flyFaster_item = getItem(quickFlapsItemString);
		if (getItem(featherFallItemString) == null)
		{
			SimpleFlight.logger.log(Level.INFO, "Item (" + featherFallItemString + ") not found. Using Feather instead.");
			featherFallItemString = "feather";
		}
		this.featherFalling_item = getItem(featherFallItemString);

		this.enableTechBased = this.config.get("Items", "EnableTechBased", true).getBoolean(true);
		this.enableGliderBased = this.config.get("Items", "EnableGliders", true).getBoolean(true);
		this.enableMagicBased = this.config.get("Items", "EnableOtherWings", true).getBoolean(true);

		this.enableXPRecipe = this.config.get("Items", "EnableExperienceBottleRecipe", true).getBoolean(true);

		// ----------

		goldFeatherWings_maxFlaps = this.config.get("maxFlaps", "goldFeatherWings", goldFeatherWings_maxFlaps).getInt();
		goldLeatherWings_maxFlaps = this.config.get("maxFlaps", "goldLeatherWings", goldLeatherWings_maxFlaps).getInt();
		goldClothWings_maxFlaps = this.config.get("maxFlaps", "goldClothWings", goldClothWings_maxFlaps).getInt();

		ironFeatherWings_maxFlaps = this.config.get("maxFlaps", "ironFeatherWings", ironFeatherWings_maxFlaps).getInt();
		ironLeatherWings_maxFlaps = this.config.get("maxFlaps", "ironLeatherWings", ironLeatherWings_maxFlaps).getInt();
		ironClothWings_maxFlaps = this.config.get("maxFlaps", "ironClothWings", ironClothWings_maxFlaps).getInt();

		chainFeatherWings_maxFlaps = this.config.get("maxFlaps", "chainFeatherWings", chainFeatherWings_maxFlaps).getInt();
		chainLeatherWings_maxFlaps = this.config.get("maxFlaps", "chainLeatherWings", chainLeatherWings_maxFlaps).getInt();
		chainClothWings_maxFlaps = this.config.get("maxFlaps", "chainClothWings", chainClothWings_maxFlaps).getInt();

		diamondFeatherWings_maxFlaps = this.config.get("maxFlaps", "diamondFeatherWings", diamondFeatherWings_maxFlaps).getInt();
		diamondLeatherWings_maxFlaps = this.config.get("maxFlaps", "diamondLeatherWings", diamondLeatherWings_maxFlaps).getInt();
		diamondClothWings_maxFlaps = this.config.get("maxFlaps", "diamondClothWings", diamondClothWings_maxFlaps).getInt();

		goldFeatherWings_coolDown = this.config.get("coolDown", "goldFeatherWings", goldFeatherWings_coolDown).getInt();
		goldLeatherWings_coolDown = this.config.get("coolDown", "goldLeatherWings", goldLeatherWings_coolDown).getInt();
		goldClothWings_coolDown = this.config.get("coolDown", "goldClothWings", goldClothWings_coolDown).getInt();

		ironFeatherWings_coolDown = this.config.get("coolDown", "ironFeatherWings", ironFeatherWings_coolDown).getInt();
		ironLeatherWings_coolDown = this.config.get("coolDown", "ironLeatherWings", ironLeatherWings_coolDown).getInt();
		ironClothWings_coolDown = this.config.get("coolDown", "ironClothWings", ironClothWings_coolDown).getInt();

		chainFeatherWings_coolDown = this.config.get("coolDown", "chainFeatherWings", chainFeatherWings_coolDown).getInt();
		chainLeatherWings_coolDown = this.config.get("coolDown", "chainLeatherWings", chainLeatherWings_coolDown).getInt();
		chainClothWings_coolDown = this.config.get("coolDown", "chainClothWings", chainClothWings_coolDown).getInt();

		diamondFeatherWings_coolDown = this.config.get("coolDown", "diamondFeatherWings", diamondFeatherWings_coolDown).getInt();
		diamondLeatherWings_coolDown = this.config.get("coolDown", "diamondLeatherWings", diamondLeatherWings_coolDown).getInt();
		diamondClothWings_coolDown = this.config.get("coolDown", "diamondClothWings", diamondClothWings_coolDown).getInt();

		// ----------

		this.config.save();
	}

	public static Item getItem(String s)
	{
		String modId = "minecraft";
		if (s.indexOf(":") != -1)
		{
			modId = s.substring(0, s.indexOf(":"));
		}
		String name = s.substring(s.indexOf(":") + 1);

		return GameRegistry.findItem(modId, name);
	}
}

/* ====================================================================================================
 * NOTICE OF MODIFICATIONS
 * ----------------------------------------------------------------------------------------------------
 *
 * This Work is Copyright 2014 _Antihero_ and is licensed under the Apache License, Version 2.0.
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Pursuant to section 4b of the above license, this file contains modifications made by csb987.
 *
 * ====================================================================================================
 */