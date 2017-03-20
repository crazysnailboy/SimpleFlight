package deep_ci.mcmods.simpleflight.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentSimpleWings extends Enchantment
{
	final int level;

	public EnchantmentSimpleWings(int id, int weight, EnumEnchantmentType type)
	{
		super(id, weight, type);
		this.level = 8;
	}

	public EnchantmentSimpleWings(int id, int weight, int level, EnumEnchantmentType type)
	{
		super(id, weight, type);
		if ((level > 8) || (level < 0))
		{
			level = 8;
		}
		this.level = level;
	}

	@Override
	public int getMaxLevel()
	{
		return this.level;
	}

	@Override
	public boolean canApplyTogether(Enchantment enchant)
	{
		return true;
	}
}
