package deep_ci.mcmods.simpleflight.item.crafting;

import deep_ci.mcmods.simpleflight.item.ItemArmorSimpleWings;
import java.util.ArrayList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class EnchantmentRecipe implements IRecipe
{
	final Item mainItem;
	final Enchantment enchantment;
	final int maxPower;

	public EnchantmentRecipe(Item Item, Enchantment effect)
	{
		this(Item, effect, effect.getMaxLevel());
	}

	public EnchantmentRecipe(Item Item, Enchantment effect, int maxPower)
	{
		this.mainItem = Item;
		this.enchantment = effect;
		if (maxPower < 0)
		{
			this.maxPower = 0;
		}
		else if (maxPower > effect.getMaxLevel())
		{
			this.maxPower = effect.getMaxLevel();
		}
		else
		{
			this.maxPower = maxPower;
		}
	}

	@Override
	public boolean matches(InventoryCrafting inventory, World world)
	{
		ItemStack armor = null;
		ItemStack aspect = null;
		ArrayList bottlelist = new ArrayList();
		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			ItemStack itemstack = inventory.getStackInSlot(i);
			if (itemstack != null)
			{
				if ((itemstack.getItem() instanceof ItemArmorSimpleWings))
				{
					armor = itemstack;
				}
				else if (itemstack.getItem() == this.mainItem)
				{
					aspect = itemstack;
				}
				else
				{
					if (!itemstack.isItemEqual(new ItemStack(Items.experience_bottle)))
					{
						return false;
					}
					bottlelist.add(itemstack);
					if (bottlelist.size() > this.maxPower)
					{
						return false;
					}
				}
			}
		}
		return (armor != null) && (aspect != null) && (!bottlelist.isEmpty());
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory)
	{
		ItemStack items = null;
		ItemArmorSimpleWings armor = null;
		ItemStack aspect = null;
		int enchantPower = 0;
		for (int k = 0; k < inventory.getSizeInventory(); k++)
		{
			ItemStack itemstack = inventory.getStackInSlot(k);
			if (itemstack != null)
			{
				if ((itemstack.getItem() instanceof ItemArmorSimpleWings))
				{
					armor = (ItemArmorSimpleWings)itemstack.getItem();

					items = itemstack.copy();
					items.stackSize = 1;
					if (!items.isItemEnchantable())
					{
						return null;
					}
				}
				else if (itemstack.getItem() == this.mainItem)
				{
					if (aspect != null)
					{
						return null;
					}
					aspect = itemstack;
				}
				else
				{
					if (!itemstack.isItemEqual(new ItemStack(Items.experience_bottle)))
					{
						return null;
					}
					enchantPower++;
				}
			}
		}
		if ((armor == null) || (aspect == null) || (enchantPower > this.maxPower) || (enchantPower == 0))
		{
			return null;
		}
		items.addEnchantment(this.enchantment, enchantPower);
		return items;
	}

	@Override
	public int getRecipeSize()
	{
		return 10;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return null;
	}
}
