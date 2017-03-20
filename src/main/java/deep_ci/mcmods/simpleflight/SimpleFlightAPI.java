package deep_ci.mcmods.simpleflight;

import cpw.mods.fml.relauncher.Side;
import deep_ci.mcmods.simpleflight.enchantment.EnchantmentSimpleWings;
import deep_ci.mcmods.simpleflight.event.SimpleFlightEventHandler;
import deep_ci.mcmods.simpleflight.item.crafting.EnchantmentRecipe;
import deep_ci.mcmods.simpleflight.item.crafting.WingDyeRecipes;
import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.RecipeSorter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class SimpleFlightAPI
{
	private static boolean LOADED = false;
	private static File configFilePath;
	private static SimpleConfig config;
	public static Enchantment moreFlaps;
	public static Enchantment quickerFlaps;
	private static Logger logger;

	public static void init(Side side)
	{
		if (!LOADED)
		{
			logger = LogManager.getLogger("SimpleFlightAPI");
			initConfig(side);
			registerEventHandler();
			registerEnchantments();
			registerRecipies();
			LOADED = true;
		}
	}

	private static void initConfig(Side side)
	{
		if (LOADED)
		{
			return;
		}
		if (side == Side.SERVER)
		{
			configFilePath = new File(MinecraftServer.getServer().getFolderName(), "config/simpleflight.cfg");
		}
		else
		{
			configFilePath = new File(Minecraft.getMinecraft().mcDataDir, "config/simpleflight.cfg");
		}
		config = new SimpleConfig(new Configuration(configFilePath));
	}

	private static void registerEventHandler()
	{
		MinecraftForge.EVENT_BUS.register(SimpleFlightEventHandler.instance());
		logger.log(Level.INFO, "Simple Flight Event Handler has been registered.");
	}

	private static void registerEnchantments()
	{
		moreFlaps = new EnchantmentSimpleWings(getConfig().id_flyLonger, 10, getConfig().maxLevel_flyLonger, EnumEnchantmentType.armor);
		moreFlaps.setName("fly_longer");
		Enchantment.addToBookList(moreFlaps);

		quickerFlaps = new EnchantmentSimpleWings(getConfig().id_flyFaster, 10, getConfig().maxLevel_flyFaster, EnumEnchantmentType.armor);
		quickerFlaps.setName("fly_faster");
		Enchantment.addToBookList(quickerFlaps);
	}

	private static void registerRecipies()
	{
		RecipeSorter.register("simpleflight:dyes", WingDyeRecipes.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("simpleflight:enchantments", EnchantmentRecipe.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		logger.log(Level.INFO, "IRecipes Registered.");
	}

	public static SimpleConfig getConfig()
	{
		return config;
	}
}
