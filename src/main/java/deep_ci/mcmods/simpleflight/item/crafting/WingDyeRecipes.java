package deep_ci.mcmods.simpleflight.item.crafting;

import java.util.ArrayList;
import deep_ci.mcmods.simpleflight.item.ItemArmorSimpleWingsDyable;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class WingDyeRecipes implements IRecipe
{
	@Override
	public boolean matches(InventoryCrafting inventory, World world)
	{
		ItemStack items = null;
		ArrayList list = new ArrayList();
		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			ItemStack itemstack = inventory.getStackInSlot(i);
			if (itemstack != null)
			{
				if ((itemstack.getItem() instanceof ItemArmorSimpleWingsDyable))
				{
					items = itemstack;
				}
				else
				{
					if (itemstack.getItem() != Items.dye)
					{
						return false;
					}
					list.add(itemstack);
				}
			}
		}
		return (items != null) && (!list.isEmpty());
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory)
	{
		ItemStack items = null;
		int[] aint = new int[3];
		int i = 0;
		int j = 0;
		ItemArmorSimpleWingsDyable armor = null;
		for (int k = 0; k < inventory.getSizeInventory(); k++)
		{
			ItemStack itemstack = inventory.getStackInSlot(k);
			if (itemstack != null)
			{
				if ((itemstack.getItem() instanceof ItemArmorSimpleWingsDyable))
				{
					armor = (ItemArmorSimpleWingsDyable)itemstack.getItem();

					items = itemstack.copy();
					items.stackSize = 1;
					if (armor.hasColor(itemstack))
					{
						int l = armor.getColor(items);
						float f1 = (l >> 16 & 0xFF) / 255.0F;
						float f2 = (l >> 8 & 0xFF) / 255.0F;
						float f3 = (l & 0xFF) / 255.0F;
						i = (int)(i + Math.max(f1, Math.max(f2, f3)) * 255.0F);
						aint[0] = ((int)(aint[0] + f1 * 255.0F));
						aint[1] = ((int)(aint[1] + f2 * 255.0F));
						aint[2] = ((int)(aint[2] + f3 * 255.0F));
						j++;
					}
				}
				else
				{
					if (itemstack.getItem() != Items.dye)
					{
						return null;
					}
					float[] afloat = net.minecraft.entity.passive.EntitySheep.fleeceColorTable[net.minecraft.block.BlockColored.func_150032_b(itemstack.getMetadata())];
					int j1 = (int)(afloat[0] * 255.0F);
					int k1 = (int)(afloat[1] * 255.0F);
					int l1 = (int)(afloat[2] * 255.0F);
					i += Math.max(j1, Math.max(k1, l1));
					aint[0] += j1;
					aint[1] += k1;
					aint[2] += l1;
					j++;
				}
			}
		}
		if (armor == null)
		{
			return null;
		}
		int k = aint[0] / j;
		int i1 = aint[1] / j;
		int l = aint[2] / j;
		float f1 = i / j;
		float f2 = Math.max(k, Math.max(i1, l));
		k = (int)(k * f1 / f2);
		i1 = (int)(i1 * f1 / f2);
		l = (int)(l * f1 / f2);
		int l1 = (k << 8) + i1;
		l1 = (l1 << 8) + l;
		armor.func_82813_b(items, l1);
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
