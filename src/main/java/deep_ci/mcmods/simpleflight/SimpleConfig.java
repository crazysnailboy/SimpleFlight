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
