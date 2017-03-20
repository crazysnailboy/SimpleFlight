package deep_ci.mcmods.simpleflight.item;

import deep_ci.mcmods.simpleflight.SimpleFlight;
import java.util.List;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class ItemArmorSimpleWings extends ItemArmor
{
	private final int baseFlaps;
	private final int baseCooldown;
	private final boolean isGlider;

	public ItemArmorSimpleWings(ItemArmor.ArmorMaterial material, int renderIndex, int armorType)
	{
		this(0, 0, false, material, renderIndex, armorType);
	}

	public ItemArmorSimpleWings(int baseFlaps, int baseCooldown, boolean glider, ItemArmor.ArmorMaterial material, int renderIndex, int armorType)
	{
		super(material, renderIndex, armorType);
		this.baseFlaps = baseFlaps;
		this.baseCooldown = baseCooldown;
		this.isGlider = glider;
	}

	public String getSound(ItemStack stack, EntityPlayer player, World world)
	{
		return "mob.enderdragon.wings";
	}

	public float getSoundPitch(ItemStack stack, EntityPlayer player, World world)
	{
		return 1.25F;
	}

	public float getSoundVolume(ItemStack stack, EntityPlayer player, World world)
	{
		return 0.8F;
	}

	public void onFlap(EntityPlayer player, ItemStack items, World world)
	{
		player.playSound(this.getSound(items, player, world), this.getSoundVolume(items, player, world), this.getSoundPitch(items, player, world));
	}

	public int getBaseFlapCount()
	{
		return this.baseFlaps;
	}

	public int getBaseFlapCooldown()
	{
		return this.baseCooldown;
	}

	public boolean isGlider()
	{
		return this.isGlider;
	}

	@Override
	public void addInformation(ItemStack items, EntityPlayer player, List list, boolean bool)
	{
		int maxFlaps = this.getBaseFlapCount();
		int cooldown = this.getBaseFlapCooldown();
		boolean glider = this.isGlider();
		int featherFall = 0;
		int flapBonus = 0;
		int coolBonus = 0;
		if (items.isItemEnchanted())
		{
			NBTTagList nbt = items.getEnchantmentTagList();
			for (int e = 0; e < nbt.tagCount(); e++)
			{
				NBTTagCompound comp = nbt.getCompoundTagAt(e);
				if (comp.getInteger("id") == Enchantment.featherFalling.effectId)
				{
					featherFall = comp.getInteger("lvl");
					if (featherFall > 4)
					{
						featherFall = 4;
					}
				}
				if (comp.getInteger("id") == SimpleFlight.items.flyFaster.effectId)
				{
					coolBonus = comp.getInteger("lvl") * 100;
					if (coolBonus > SimpleFlight.items.flyFaster.getMaxLevel() * 100)
					{
						coolBonus = SimpleFlight.items.flyFaster.getMaxLevel() * 100;
					}
				}
				if (comp.getInteger("id") == SimpleFlight.items.flyLonger.effectId)
				{
					flapBonus = comp.getInteger("lvl") * 4;
					if (flapBonus > SimpleFlight.items.flyLonger.getMaxLevel() * 4)
					{
						flapBonus = SimpleFlight.items.flyLonger.getMaxLevel() * 4;
					}
				}
			}
		}
		if ((items.getItem() instanceof WingItemJetpack))
		{
			list.add(I18n.format("dp_simpleflight.tooltip.jetpackflapcount", new Object[] { Integer.valueOf(this.getBaseFlapCount()), Integer.valueOf(this.baseFlaps) }));
			list.add(I18n.format("dp_simpleflight.tooltip.cooldown", new Object[] { Float.valueOf(this.getBaseFlapCooldown() / 1000.0F) }));
		}
		else if (!this.isGlider())
		{
			list.add(I18n.format("dp_simpleflight.tooltip.flapcount", new Object[] { Integer.valueOf(this.getBaseFlapCount()) }));
			list.add(I18n.format("dp_simpleflight.tooltip.cooldown", new Object[] { Float.valueOf(this.getBaseFlapCooldown() / 1000.0F) }));
		}
		if (this.isGlider())
		{
			list.add(I18n.format("dp_simpleflight.tooltip.fallreduction", new Object[] { "100%" }));
		}
		else if (featherFall > 0)
		{
			list.add(I18n.format("dp_simpleflight.tooltip.fallreduction", new Object[] { 25 * featherFall + "%" }));
		}
		if (flapBonus > 0)
		{
			list.add(I18n.format("dp_simpleflight.tooltip.bonusflaps", new Object[] { Integer.valueOf(flapBonus) }));
		}
		if (coolBonus > 0)
		{
			list.add(I18n.format("dp_simpleflight.tooltip.coolbonus", new Object[] { Float.valueOf(coolBonus / 1000.0F) }));
		}
	}
}
