package deep_ci.mcmods.simpleflight.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLeatherWings extends ModelWingArmor
{
	public ModelRenderer wingLeftBase;
	public ModelRenderer wingLeftShoulder;
	public ModelRenderer wingLeftTip;
	public ModelRenderer wingRightBase;
	public ModelRenderer wingRightTip;

	public ModelLeatherWings()
	{
		this(0.0F);
	}

	public ModelLeatherWings(float fl)
	{
		this(fl, 0.0F, 64, 64);
	}

	public ModelLeatherWings(float f1, float f2, int i1, int i2)
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

		this.wingLeftBase = new ModelRenderer(this, 0, 0);
		this.wingLeftBase.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, f1);
		this.wingLeftBase.setRotationPoint(1.0F, 2.0F + f2, 4.0F);

		this.wingLeftShoulder = new ModelRenderer(this, 12, 42);
	}

	@Override
	public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6)
	{
		this.setRotationAngles(f1, f2, f3, f4, f5, f6, entity);

		this.bipedBody.render(f6);
		this.bipedLeftArm.render(f6);
		this.bipedRightArm.render(f6);

		this.wingLeftBase.render(f6);
	}

	public void doGlide(float f1, float f2, float f3, float f4, float f5, float f6, Entity entity)
	{
		this.wingLeftBase.rotateAngleX = this.bipedLeftArm.rotateAngleX;
		this.wingLeftBase.rotateAngleY = this.bipedLeftArm.rotateAngleY;
		this.wingLeftBase.rotateAngleZ = this.bipedLeftArm.rotateAngleZ;
	}

	public void doFlap(float f1, float f2, float f3, float f4, float f5, float f6, Entity entity)
	{
		this.wingLeftBase.rotateAngleX = this.bipedLeftArm.rotateAngleX;
		this.wingLeftBase.rotateAngleY = this.bipedLeftArm.rotateAngleY;
		this.wingLeftBase.rotateAngleZ = this.bipedLeftArm.rotateAngleZ;
	}

	public void doIdle(float f1, float f2, float f3, float f4, float f5, float f6, Entity entity)
	{
		this.wingLeftBase.rotateAngleX = this.bipedLeftArm.rotateAngleX;
		this.wingLeftBase.rotateAngleY = this.bipedLeftArm.rotateAngleY;
		this.wingLeftBase.rotateAngleZ = this.bipedLeftArm.rotateAngleZ;
	}

	@Override
	public void renderWings(float f1, float f2, float f3, float f4, float f5, float f6)
	{
	}

	@Override
	public void animateGlide(float f1, float f2, float f3, float f4, float f5, float f6)
	{
	}

	@Override
	public void animateFlap(float flapFactor, float f1, float f2, float f3, float f4, float f5, float f6)
	{
	}

	@Override
	public void animateIdle(float f1, float f2, float f3, float f4, float f5, float f6)
	{
	}
}
