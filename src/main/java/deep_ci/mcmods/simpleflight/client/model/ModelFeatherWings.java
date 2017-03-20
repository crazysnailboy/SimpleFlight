package deep_ci.mcmods.simpleflight.client.model;

import net.minecraft.client.model.ModelRenderer;

public class ModelFeatherWings extends ModelWingArmor
{
	protected boolean furled = true;
	public ModelRenderer wingLeft;
	public ModelRenderer wingRight;
	public ModelRenderer idleLeft;
	public ModelRenderer idleRight;

	public ModelFeatherWings()
	{
		this(0.0F);
	}

	public ModelFeatherWings(float f)
	{
		super(f);

		this.wingLeft = new ModelRenderer(this).setTextureOffset(0, 8);
		this.wingLeft.addBox(0.0F, -6.0F, 0.0F, 0, 24, 32);
		this.wingLeft.setRotationPoint(2.0F, 4.0F, 3.0F);

		this.wingRight = new ModelRenderer(this).setTextureOffset(0, 8);
		this.wingRight.addBox(0.0F, -6.0F, 0.0F, 0, 24, 32);
		this.wingRight.setRotationPoint(-2.0F, 4.0F, 3.0F);
		this.wingRight.mirror = true;

		this.idleLeft = new ModelRenderer(this).setTextureOffset(0, 48);
		this.idleLeft.addBox(0.0F, -6.0F, 0.0F, 0, 24, 32);
		this.idleLeft.setRotationPoint(2.0F, 4.0F, 3.0F);

		this.idleRight = new ModelRenderer(this).setTextureOffset(0, 48);
		this.idleRight.addBox(0.0F, -6.0F, 0.0F, 0, 24, 32);
		this.idleRight.setRotationPoint(-2.0F, 4.0F, 3.0F);
		this.idleRight.mirror = true;
	}

	public ModelRenderer wingLeft()
	{
		return this.furled ? this.idleLeft : this.wingLeft;
	}

	public ModelRenderer wingRight()
	{
		return this.furled ? this.idleRight : this.wingRight;
	}

	@Override
	public void renderWings(float f1, float f2, float f3, float f4, float f5, float f6)
	{
		this.wingLeft().render(f6);
		this.wingRight().render(f6);
	}

	@Override
	public void animateGlide(float f1, float f2, float f3, float f4, float f5, float f6)
	{
		this.furled = false;
		this.wingLeft().rotateAngleY = (this.wingRight().rotateAngleY = 0.0F);
		this.wingLeft().rotateAngleY += 0.6F;
		this.wingRight().rotateAngleY += -0.6F;

		this.wingLeft().rotateAngleX = (this.wingRight().rotateAngleX = 1.0F);
		this.wingLeft().rotateAngleX *= f2;
		this.wingRight().rotateAngleX *= f2;

		this.wingLeft().rotateAngleX += this.bipedBody.rotateAngleX;
		this.wingRight().rotateAngleX += this.bipedBody.rotateAngleX;

		this.wingLeft().rotateAngleZ = (this.wingRight().rotateAngleZ = 0.0F);
		this.wingLeft().rotateAngleZ += 0.6F;
		this.wingRight().rotateAngleZ -= 0.6F;
	}

	@Override
	public void animateFlap(float flapFactor, float f1, float f2, float f3, float f4, float f5, float f6)
	{
		this.furled = false;

		this.wingLeft().rotateAngleY = (this.wingRight().rotateAngleY = 0.0F);
		this.wingLeft().rotateAngleY += 0.6F;
		this.wingRight().rotateAngleY += -0.6F;

		this.wingLeft().rotateAngleX = (this.wingRight().rotateAngleX = this.bipedBody.rotateAngleX);
		this.wingLeft().rotateAngleX += 0.5F;
		this.wingRight().rotateAngleX += 0.5F;

		this.wingLeft().rotateAngleZ *= f2;
		this.wingRight().rotateAngleZ *= f2;
	}

	@Override
	public void animateIdle(float f1, float f2, float f3, float f4, float f5, float f6)
	{
		this.furled = true;
		this.wingLeft().rotateAngleY = (this.wingRight().rotateAngleY = 0.0F);
		this.wingLeft().rotateAngleY += 0.6F;
		this.wingRight().rotateAngleY += -0.6F;

		this.wingLeft().rotateAngleX = (this.wingRight().rotateAngleX = this.bipedBody.rotateAngleX);
	}
}
