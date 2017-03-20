package deep_ci.mcmods.simpleflight.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelJetwings extends ModelWingArmor
{
	public boolean isGlider;
	public ModelRenderer jetEngine;
	public ModelRenderer jetJets;
	public ModelRenderer gliderWingL;
	public ModelRenderer gliderWingR;

	public ModelJetwings()
	{
		this(0.0F);
	}

	public ModelJetwings(float fl)
	{
		this(fl, 0.0F, 64, 64);
	}

	public ModelJetwings(float f1, float f2, int i1, int i2)
	{
		this(f1, f2, i1, i2, false);
	}

	public ModelJetwings(float f1, float f2, int i1, int i2, boolean isGlider)
	{
		super(f1, f2, i1, i2);
		this.isGlider = isGlider;

		this.jetEngine = new ModelRenderer(this, 0, 16);
		this.jetEngine.addBox(0.0F, -5.5F, 0.0F, 6, 8, 3);

		this.jetJets = new ModelRenderer(this, 0, 16);

		this.gliderWingL = new ModelRenderer(this, 0, 16);
		this.gliderWingL.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);

		this.gliderWingR = new ModelRenderer(this, 0, 16);
		this.gliderWingR.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
		this.gliderWingR.mirror = true;
	}

	@Override
	public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6)
	{
		this.setRotationAngles(f1, f2, f3, f4, f5, f6, entity);
		super.render(entity, f1, f2, f3, f4, f5, f6);

		this.jetEngine.render(f6);
		if (this.isGlider)
		{
			this.gliderWingL.render(f6);
			this.gliderWingR.render(f6);
		}
	}

	public void doGlide(float f1, float f2, float f3, float f4, float f5, float f6, Entity entity)
	{
		if (this.isGlider)
		{
		}
	}

	public void doFlap(float f1, float f2, float f3, float f4, float f5, float f6, Entity entity)
	{
	}

	public void doIdle(float f1, float f2, float f3, float f4, float f5, float f6, Entity entity)
	{
		if (this.isGlider)
		{
		}
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
