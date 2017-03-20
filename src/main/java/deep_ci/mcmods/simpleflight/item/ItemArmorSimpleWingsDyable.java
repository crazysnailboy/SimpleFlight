package deep_ci.mcmods.simpleflight.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public class ItemArmorSimpleWingsDyable extends ItemArmorSimpleWings
{
	@SideOnly(Side.CLIENT)
	private IIcon overlayIcon;
	private int defaultColor = 10511680;

	public ItemArmorSimpleWingsDyable(ItemArmor.ArmorMaterial armorType, int renderType, int armorSlot)
	{
		this(0, 0, false, armorType, renderType, armorSlot);
	}

	public ItemArmorSimpleWingsDyable(int maxFlaps, int flapCooldown, boolean isGlideCapable, ItemArmor.ArmorMaterial armorType, int renderType, int armorSlot)
	{
		super(maxFlaps, flapCooldown, isGlideCapable, armorType, renderType, armorSlot);
	}

	public ItemArmorSimpleWingsDyable(int maxFlaps, int flapCooldown, boolean isGlideCapable, boolean leatherBaseColor, ItemArmor.ArmorMaterial armorType, int renderType, int armorSlot)
	{
		super(maxFlaps, flapCooldown, isGlideCapable, armorType, renderType, armorSlot);
		this.defaultColor = (leatherBaseColor ? 10511680 : 16777215);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack items, int pass)
	{
		if (pass > 0)
		{
			return 16777215;
		}
		int color = this.getColor(items);
		if (color < 0)
		{
			color = 16777215;
		}
		return color;
	}

	@Override
	public boolean hasColor(ItemStack items)
	{
		return !items.getTagCompound().hasKey("display", 10) ? false : !items.hasTagCompound() ? false : items.getTagCompound().getCompoundTag("display").hasKey("color", 3);
	}

	@Override
	public int getColor(ItemStack items)
	{
		NBTTagCompound nbttag = items.getTagCompound();
		if (nbttag == null)
		{
			return this.defaultColor;
		}
		NBTTagCompound tags = nbttag.getCompoundTag("display");
		return tags.hasKey("color", 3) ? tags.getInteger("color") : tags == null ? this.defaultColor : this.defaultColor;
	}

	@Override
	public void removeColor(ItemStack items)
	{
		NBTTagCompound nbttag = items.getTagCompound();
		if (nbttag != null)
		{
			NBTTagCompound tags = nbttag.getCompoundTag("display");
			if (tags.hasKey("color"))
			{
				tags.removeTag("color");
			}
		}
	}

	@Override
	public void func_82813_b(ItemStack items, int color)
	{
		NBTTagCompound nbttag = items.getTagCompound();
		if (nbttag == null)
		{
			nbttag = new NBTTagCompound();
			items.setTagCompound(nbttag);
		}
		NBTTagCompound tags = nbttag.getCompoundTag("display");
		if (!nbttag.hasKey("display", 10))
		{
			nbttag.setTag("display", tags);
		}
		tags.setInteger("color", color);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int damage, int pass)
	{
		return pass == 1 ? this.overlayIcon : super.getIconFromDamageForRenderPass(damage, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register)
	{
		this.overlayIcon = register.registerIcon(this.iconString + "_overlay");
		this.itemIcon = register.registerIcon(this.iconString);
	}
}
