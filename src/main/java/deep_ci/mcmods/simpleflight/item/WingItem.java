package deep_ci.mcmods.simpleflight.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import deep_ci.mcmods.simpleflight.client.model.ModelFeatherWings;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class WingItem extends ItemArmorSimpleWingsDyable
{
	@SideOnly(Side.CLIENT)
	private IIcon overlayIcon;
	protected String armorTextureName;

	public WingItem(ItemArmor.ArmorMaterial armorType, int renderType, int armorSlot)
	{
		super(armorType, renderType, armorSlot);
	}

	public WingItem(int maxFlaps, int flapCooldown, boolean isGlideCapable, ItemArmor.ArmorMaterial armorType, int renderType, int armorSlot)
	{
		this(maxFlaps, flapCooldown, isGlideCapable, true, armorType, renderType, armorSlot);
	}

	public WingItem(int maxFlaps, int flapCooldown, boolean isGlideCapable, boolean leatherBase, ItemArmor.ArmorMaterial armorType, int renderType, int armorSlot)
	{
		super(maxFlaps, flapCooldown, isGlideCapable, leatherBase, armorType, renderType, armorSlot);
	}

	@Override
	public int getBaseFlapCooldown()
	{
		return (int)(super.getBaseFlapCooldown() / 20.0F * 1000.0F);
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
	{
		return new CreativeTabs[] { CreativeTabs.tabCombat, CreativeTabs.tabTransport };
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack item, Entity entity, int slot, String type)
	{
		if (this.getUnlocalizedName().contains("feather"))
		{
			return "DP_SimpleFlight:textures/models/armor/diamond_feather" + (type != null ? "_overlay.png" : ".png");
		}
		if ((this.getUnlocalizedName().contains("leather")) || ((this.getUnlocalizedName().contains("cloth")) && (!this.getUnlocalizedName().contains("glider"))))
		{
			return "DP_SimpleFlight:textures/models/armor/leather" + (type != null ? "_overlay.png" : ".png");
		}
		int l = 1;
		if (slot == 2)
		{
			l = 2;
		}
		ItemArmor.ArmorMaterial material = ((ItemArmor)item.getItem()).getArmorMaterial();
		String name = "";
		if (material == ItemArmor.ArmorMaterial.CLOTH)
		{
			name = "leather";
		}
		else if (material == ItemArmor.ArmorMaterial.GOLD)
		{
			name = "gold";
		}
		else if (material == ItemArmor.ArmorMaterial.DIAMOND)
		{
			name = "diamond";
		}
		else
		{
			name = "iron";
		}
		return "textures/models/armor/" + name + "_layer_" + l + ".png";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
	{
		return (this.getUnlocalizedName().contains("glider")) || (this.getUnlocalizedName().contains("jet")) ? super.getArmorModel(entityLiving, itemStack, armorSlot) : new ModelFeatherWings(1.0F);
	}
}
