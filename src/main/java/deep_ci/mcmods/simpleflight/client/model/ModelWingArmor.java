package deep_ci.mcmods.simpleflight.client.model;

import deep_ci.mcmods.simpleflight.entity.ExtendedEntityPlayer;
import deep_ci.mcmods.simpleflight.event.SimpleFlightEventHandler;
import deep_ci.mcmods.simpleflight.item.WingItem;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;

public abstract class ModelWingArmor extends ModelBiped
{
	protected EntityLivingBase entity;
	protected WingItem wings;

	public ModelWingArmor()
	{
		this(0.0F);
	}

	public ModelWingArmor(float fl)
	{
		this(fl, 0.0F, 64, 64);
	}

	public ModelWingArmor(float f1, float f2, int i1, int i2)
	{
		this.textureWidth = i1;
		this.textureHeight = i2;

		this.bipedBody = new ModelRenderer(this, 16, 0);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, f1);
		this.bipedBody.setRotationPoint(0.0F, 0.0F + f2, 0.0F);
		this.bipedRightArm = new ModelRenderer(this, 40, 0);
		this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, f1);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + f2, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 0, 0);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, f1);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + f2, 0.0F);
	}

	@Override
	public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6)
	{
		if (!(entity instanceof EntityLivingBase))
		{
			return;
		}
		ItemStack items = ((EntityLivingBase)entity).getEquipmentInSlot(3);
		if (items == (ItemStack)null)
		{
			return;
		}
		if (!(items.getItem() instanceof WingItem))
		{
			return;
		}
		this.entity = ((EntityLivingBase)entity);
		this.wings = ((WingItem)items.getItem());

		this.animate(f1, f2, f3, f4, f5, f6);

		this.bipedBody.render(f6);
		this.bipedLeftArm.render(f6);
		this.bipedRightArm.render(f6);

		this.renderWings(f1, f2, f3, f4, f5, f6);
	}

	public void animate(float f1, float f2, float f3, float f4, float f5, float f6)
	{
		this.isSneak = this.entity.isSneaking();
		this.isChild = this.entity.isChild();
		this.aimedBow = false;
		if ((this.entity.getHeldItem() != null) && ((this.entity.getHeldItem().getItem() instanceof ItemBow)))
		{
			this.aimedBow = true;
		}
		super.setRotationAngles(f1, f2, f3, f4, f5, f6, this.entity);
		if ((this.entity instanceof EntityPlayer))
		{
			if ((!this.entity.isRiding()) && (!this.entity.isInWater()))
			{
				ExtendedEntityPlayer props = SimpleFlightEventHandler.instance().getExProps((EntityPlayer)this.entity);
				props.updateArmor();
				if ((props.getFlapFactor() >= 0.1F) && (!this.entity.onGround))
				{
					this.animateFlap(props.getFlapFactor(), f1, f2, f3, f4, f5, f6);
				}
				else if (((props.isGlideForced()) || (props.isGlideOn())) && (!this.entity.onGround))
				{
					this.animateGlide(f1, f2, f3, f4, f5, f6);
				}
				else
				{
					this.animateIdle(f1, f2, f3, f4, f5, f6);
				}
			}
			else
			{
				this.animateIdle(f1, f2, f3, f4, f5, f6);
			}
		}
		else
		{
			this.animateIdle(f1, f2, f3, f4, f5, f6);
		}
	}

	public abstract void renderWings(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

	public abstract void animateGlide(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

	public abstract void animateFlap(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7);

	public abstract void animateIdle(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);
}
