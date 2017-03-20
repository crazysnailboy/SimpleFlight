package deep_ci.mcmods.simpleflight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Level;
import cpw.mods.fml.common.registry.GameRegistry;
import deep_ci.mcmods.simpleflight.item.WingItem;
import deep_ci.mcmods.simpleflight.item.WingItemJetpack;
import deep_ci.mcmods.simpleflight.item.crafting.EnchantmentRecipe;
import deep_ci.mcmods.simpleflight.item.crafting.WingDyeRecipes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public final class ModItems
{
	private static boolean loaded = false;
	private static ModItems thiz;
	private HashMap<ItemStack, Object[]> recipeMap = new HashMap();
	private List<Item> itemList = new ArrayList();
	public Item glider_leather;
	public Item glider_paper;
	public Item glider_cloth;
	public Item glider_reed_paper;
	public Item glider_reed_cloth;
	public Item leatherWings;
	public Item clothWings;
	public Item goldFeatherWings;
	public Item goldLeatherWings;
	public Item goldClothWings;
	public Item ironFeatherWings;
	public Item ironLeatherWings;
	public Item ironClothWings;
	public Item chainFeatherWings;
	public Item chainLeatherWings;
	public Item chainClothWings;
	public Item diamondFeatherWings;
	public Item diamondLeatherWings;
	public Item diamondClothWings;
	public Item jetpack;
	public Item jetglider;
	public Enchantment flyLonger;
	public Enchantment flyFaster;

	public void load()
	{
		this.init();

		this.flyLonger = SimpleFlightAPI.moreFlaps;
		this.flyFaster = SimpleFlightAPI.quickerFlaps;

		this.register();
	}

	public static ModItems instance()
	{
		if (thiz == null)
		{
			thiz = new ModItems();
		}
		return thiz;
	}

	public static String getTex(String name)
	{
		return "DP_SimpleFlight:" + name;
	}

	public void init()
	{
		if (this.itemList.isEmpty())
		{
			this.initItems();
		}
		if (this.recipeMap.isEmpty())
		{
			this.initRecipes();
		}
	}

	public void register()
	{
		if (!this.itemList.isEmpty())
		{
			this.registerItems();
		}
		if (!this.recipeMap.isEmpty())
		{
			this.registerRecipes();
		}
	}

	private void initItems()
	{
		if (SimpleFlight.config.enableGliderBased)
		{
			this.initGliders();
		}
		if (SimpleFlight.config.enableMagicBased)
		{
			this.initWings();
		}
		if (SimpleFlight.config.enableTechBased)
		{
			this.initJetpacks();
		}
	}

	private void initGliders()
	{
		this.glider_leather = new WingItem(0, 0, true, ItemArmor.ArmorMaterial.CLOTH, 0, 1);
		this.glider_leather.setTextureName(getTex("stick_glider")).setUnlocalizedName("leather_glider");
		this.itemList.add(this.glider_leather);

		this.glider_paper = new WingItem(0, 0, true, false, ItemArmor.ArmorMaterial.CLOTH, 0, 1);
		this.glider_paper.setTextureName(getTex("stick_glider")).setUnlocalizedName("paper_glider");
		this.itemList.add(this.glider_paper);

		this.glider_cloth = new WingItem(0, 0, true, false, ItemArmor.ArmorMaterial.CLOTH, 0, 1);
		this.glider_cloth.setTextureName(getTex("stick_glider")).setUnlocalizedName("cloth_glider");
		this.itemList.add(this.glider_cloth);

		this.glider_reed_cloth = new WingItem(0, 0, true, false, ItemArmor.ArmorMaterial.CLOTH, 0, 1);
		this.glider_reed_cloth.setTextureName(getTex("reed_glider")).setUnlocalizedName("reed_cloth_glider");
		this.itemList.add(this.glider_reed_cloth);

		this.glider_reed_paper = new WingItem(0, 0, true, false, ItemArmor.ArmorMaterial.CLOTH, 0, 1);
		this.glider_reed_paper.setTextureName(getTex("reed_glider")).setUnlocalizedName("reed_paper_glider");
		this.itemList.add(this.glider_reed_paper);

		SimpleFlight.logger.log(Level.INFO, "Initialized gliders.");
	}

	private void initWings()
	{
		this.leatherWings = new WingItem(8, 10, false, ItemArmor.ArmorMaterial.CLOTH, 0, 1);
		this.leatherWings.setTextureName(getTex("leather_wings")).setUnlocalizedName("leather_wings");
		this.itemList.add(this.leatherWings);

		this.goldFeatherWings = new WingItem(14, 5, false, false, ItemArmor.ArmorMaterial.GOLD, 4, 1);
		this.goldFeatherWings.setTextureName(getTex("golden_feather_wings")).setUnlocalizedName("gold_feather_wings");
		this.goldLeatherWings = new WingItem(16, 6, false, ItemArmor.ArmorMaterial.GOLD, 4, 1);
		this.goldLeatherWings.setTextureName(getTex("golden_leather_wings")).setUnlocalizedName("gold_leather_wings");
		this.goldClothWings = new WingItem(12, 6, false, false, ItemArmor.ArmorMaterial.GOLD, 4, 1);
		this.goldClothWings.setTextureName(getTex("golden_leather_wings")).setUnlocalizedName("gold_cloth_wings");
		this.itemList.add(this.goldFeatherWings);
		this.itemList.add(this.goldLeatherWings);
		this.itemList.add(this.goldClothWings);

		this.ironFeatherWings = new WingItem(22, 11, false, false, ItemArmor.ArmorMaterial.IRON, 2, 1);
		this.ironFeatherWings.setTextureName(getTex("iron_feather_wings")).setUnlocalizedName("iron_feather_wings");
		this.ironLeatherWings = new WingItem(24, 12, false, ItemArmor.ArmorMaterial.IRON, 2, 1);
		this.ironLeatherWings.setTextureName(getTex("iron_leather_wings")).setUnlocalizedName("iron_leather_wings");
		this.ironClothWings = new WingItem(20, 12, false, false, ItemArmor.ArmorMaterial.IRON, 2, 1);
		this.ironClothWings.setTextureName(getTex("iron_leather_wings")).setUnlocalizedName("iron_cloth_wings");
		this.itemList.add(this.ironFeatherWings);
		this.itemList.add(this.ironLeatherWings);
		this.itemList.add(this.ironClothWings);

		this.chainFeatherWings = new WingItem(16, 7, false, false, ItemArmor.ArmorMaterial.CHAIN, 2, 1);
		this.chainFeatherWings.setTextureName(getTex("chainmail_feather_wings")).setUnlocalizedName("chain_feather_wings");
		this.chainLeatherWings = new WingItem(18, 8, false, ItemArmor.ArmorMaterial.CHAIN, 2, 1);
		this.chainLeatherWings.setTextureName(getTex("chainmail_leather_wings")).setUnlocalizedName("chain_leather_wings");
		this.chainClothWings = new WingItem(14, 8, false, false, ItemArmor.ArmorMaterial.CHAIN, 2, 1);
		this.chainClothWings.setTextureName(getTex("chainmail_leather_wings")).setUnlocalizedName("chain_cloth_wings");
		this.itemList.add(this.chainFeatherWings);
		this.itemList.add(this.chainLeatherWings);
		this.itemList.add(this.chainClothWings);

		this.diamondFeatherWings = new WingItem(46, 8, false, false, ItemArmor.ArmorMaterial.DIAMOND, 3, 1);
		this.diamondFeatherWings.setTextureName(getTex("diamond_feather_wings")).setUnlocalizedName("diamond_feather_wings");
		this.diamondLeatherWings = new WingItem(48, 9, false, ItemArmor.ArmorMaterial.DIAMOND, 3, 1);
		this.diamondLeatherWings.setTextureName(getTex("diamond_leather_wings")).setUnlocalizedName("diamond_leather_wings");
		this.diamondClothWings = new WingItem(44, 9, false, false, ItemArmor.ArmorMaterial.DIAMOND, 3, 1);
		this.diamondClothWings.setTextureName(getTex("diamond_leather_wings")).setUnlocalizedName("diamond_cloth_wings");
		this.itemList.add(this.diamondFeatherWings);
		this.itemList.add(this.diamondLeatherWings);
		this.itemList.add(this.diamondClothWings);

		SimpleFlight.logger.log(Level.INFO, "Initialized wings.");
	}

	private void initJetpacks()
	{
		this.jetpack = new WingItemJetpack(4096, 0, false, false, ItemArmor.ArmorMaterial.CHAIN, 2, 1);
		this.jetpack.setTextureName(getTex("jetpack")).setUnlocalizedName("jetpack");
		this.jetglider = new WingItemJetpack(4096, 0, true, ItemArmor.ArmorMaterial.CHAIN, 2, 1);
		this.jetglider.setTextureName(getTex("jetpack")).setUnlocalizedName("jetglider");
		this.itemList.add(this.jetpack);
		this.itemList.add(this.jetglider);

		SimpleFlight.logger.log(Level.INFO, "Initialized jetpacks.");
	}

	private void initRecipes()
	{
		if (SimpleFlight.config.enableGliderBased)
		{
			this.initGliderRecipies();
		}
		if (SimpleFlight.config.enableMagicBased)
		{
			this.initWingRecipies();
		}
		if (SimpleFlight.config.enableTechBased)
		{
			this.initJetpackRecipies();
		}
	}

	private void initGliderRecipies()
	{
		this.recipeMap.put(new ItemStack(this.glider_leather, 1, 0), new Object[] { "QQ ", "WQQ", "QWQ", Character.valueOf('Q'), Items.stick, Character.valueOf('W'), Items.leather });
		this.recipeMap.put(new ItemStack(this.glider_paper, 1, 0), new Object[] { "QQ ", "WQQ", "QWQ", Character.valueOf('Q'), Items.stick, Character.valueOf('W'), Items.paper });
		this.recipeMap.put(new ItemStack(this.glider_cloth, 1, 0), new Object[] { "QQ ", "WQQ", "QWQ", Character.valueOf('Q'), Items.stick, Character.valueOf('W'), Blocks.wool });
		this.recipeMap.put(new ItemStack(this.glider_reed_paper, 1, 0), new Object[] { "QQ ", "WQQ", "QWQ", Character.valueOf('Q'), Items.reeds, Character.valueOf('W'), Items.paper });
		this.recipeMap.put(new ItemStack(this.glider_reed_cloth, 1, 0), new Object[] { "QQ ", "WQQ", "QWQ", Character.valueOf('Q'), Items.reeds, Character.valueOf('W'), Blocks.wool });

		SimpleFlight.logger.log(Level.INFO, "Initialized glider recipies.");
	}

	private void initWingRecipies()
	{
		this.recipeMap.put(new ItemStack(this.leatherWings, 1, 0), new Object[] { "Q Q", "WQW", "WCW", Character.valueOf('Q'), Items.stick, Character.valueOf('W'), Items.leather, Character.valueOf('C'), Items.leather_chestplate });

		this.recipeMap.put(new ItemStack(this.goldFeatherWings, 1, 0), new Object[] { "F F", "FFF", "FCF", Character.valueOf('F'), Items.feather, Character.valueOf('C'), Items.golden_chestplate });
		this.recipeMap.put(new ItemStack(this.goldLeatherWings, 1, 0), new Object[] { "I I", "LIL", "LCL", Character.valueOf('I'), Items.gold_ingot, Character.valueOf('C'), Items.golden_chestplate, Character.valueOf('L'), Items.leather });
		this.recipeMap.put(new ItemStack(this.goldClothWings, 1, 0), new Object[] { "I I", "LIL", "LCL", Character.valueOf('I'), Items.gold_ingot, Character.valueOf('C'), Items.golden_chestplate, Character.valueOf('L'), Blocks.wool });

		this.recipeMap.put(new ItemStack(this.ironFeatherWings, 1, 0), new Object[] { "F F", "FFF", "FCF", Character.valueOf('F'), Items.feather, Character.valueOf('C'), Items.iron_chestplate });
		this.recipeMap.put(new ItemStack(this.ironLeatherWings, 1, 0), new Object[] { "I I", "LIL", "LCL", Character.valueOf('I'), Items.iron_ingot, Character.valueOf('C'), Items.iron_chestplate, Character.valueOf('L'), Items.leather });
		this.recipeMap.put(new ItemStack(this.ironClothWings, 1, 0), new Object[] { "I I", "LIL", "LCL", Character.valueOf('I'), Items.iron_ingot, Character.valueOf('C'), Items.iron_chestplate, Character.valueOf('L'), Blocks.wool });

		this.recipeMap.put(new ItemStack(this.chainFeatherWings, 1, 0), new Object[] { "F F", "FFF", "FCF", Character.valueOf('F'), Items.feather, Character.valueOf('C'), Items.chainmail_chestplate });
		this.recipeMap.put(new ItemStack(this.chainLeatherWings, 1, 0), new Object[] { "I I", "LIL", "LCL", Character.valueOf('I'), Items.iron_ingot, Character.valueOf('C'), Items.chainmail_chestplate, Character.valueOf('L'), Items.leather });
		this.recipeMap.put(new ItemStack(this.chainClothWings, 1, 0), new Object[] { "I I", "LIL", "LCL", Character.valueOf('I'), Items.iron_ingot, Character.valueOf('C'), Items.chainmail_chestplate, Character.valueOf('L'), Blocks.wool });

		this.recipeMap.put(new ItemStack(this.diamondFeatherWings, 1, 0), new Object[] { "F F", "FFF", "FCF", Character.valueOf('F'), Items.feather, Character.valueOf('C'), Items.diamond_chestplate });
		this.recipeMap.put(new ItemStack(this.diamondLeatherWings, 1, 0), new Object[] { "I I", "LIL", "LCL", Character.valueOf('I'), Items.diamond, Character.valueOf('C'), Items.diamond_chestplate, Character.valueOf('L'), Items.leather });
		this.recipeMap.put(new ItemStack(this.diamondClothWings, 1, 0), new Object[] { "I I", "LIL", "LCL", Character.valueOf('I'), Items.diamond, Character.valueOf('C'), Items.diamond_chestplate, Character.valueOf('L'), Blocks.wool });

		SimpleFlight.logger.log(Level.INFO, "Initialized wing recipies.");
	}

	private void initJetpackRecipies()
	{
		this.recipeMap.put(new ItemStack(this.jetpack, 1, 0), new Object[] { "I I", "BCB", "I I", Character.valueOf('I'), Items.iron_ingot, Character.valueOf('C'), Items.iron_chestplate, Character.valueOf('B'), Items.blaze_powder });
		this.recipeMap.put(new ItemStack(this.jetglider, 1, 0), new Object[] { "I I", "LCL", "LLL", Character.valueOf('I'), Items.iron_ingot, Character.valueOf('C'), this.jetpack, Character.valueOf('L'), Items.leather });

		SimpleFlight.logger.log(Level.INFO, "Initialized jetpacks.");
	}

	private void registerItems()
	{
		for (Item item : this.itemList)
		{
			GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
		}
		this.itemList.clear();
		SimpleFlight.logger.log(Level.DEBUG, "Item Registration COMPLETE.");
	}

	private void registerRecipes()
	{
		for (Map.Entry<ItemStack, Object[]> recipe : this.recipeMap.entrySet())
		{
			GameRegistry.addShapedRecipe((ItemStack)recipe.getKey(), (Object[])recipe.getValue());
		}
		if (SimpleFlight.config.enableXPRecipe)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.experience_bottle), new Object[] { Items.ender_eye, new ItemStack(Items.dye, 1, 4), new ItemStack(Items.dye, 1, 4), new ItemStack(Items.potionitem, 1, 0) });
		}
		GameRegistry.addRecipe(new WingDyeRecipes());
		GameRegistry.addRecipe(new EnchantmentRecipe(SimpleFlight.config.flyFaster_item, this.flyFaster, SimpleFlight.config.maxLevel_flyFaster));
		GameRegistry.addRecipe(new EnchantmentRecipe(SimpleFlight.config.flyLonger_item, this.flyLonger, SimpleFlight.config.maxLevel_flyLonger));
		GameRegistry.addRecipe(new EnchantmentRecipe(SimpleFlight.config.featherFalling_item, Enchantment.featherFalling, Enchantment.featherFalling.getMaxLevel()));

		this.recipeMap.clear();
		SimpleFlight.logger.log(Level.DEBUG, "Recipe Registration COMPLETE.");
	}
}
