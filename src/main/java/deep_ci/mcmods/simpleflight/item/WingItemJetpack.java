package deep_ci.mcmods.simpleflight.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WingItemJetpack extends WingItem
{
	public WingItemJetpack(ItemArmor.ArmorMaterial armorType, int renderType, int armorSlot)
	{
		super(armorType, renderType, armorSlot);
	}

	public WingItemJetpack(int maxFlaps, int flapCooldown, boolean isGlideCapable, ItemArmor.ArmorMaterial armorType, int renderType, int armorSlot)
	{
		super(maxFlaps, flapCooldown, isGlideCapable, armorType, renderType, armorSlot);
	}

	public WingItemJetpack(int maxFlaps, int flapCooldown, boolean isGlideCapable, boolean leatherBase, ItemArmor.ArmorMaterial armorType, int renderType, int armorSlot)
	{
		super(maxFlaps, flapCooldown, isGlideCapable, leatherBase, armorType, renderType, armorSlot);
	}

	@Override
	public void onFlap(EntityPlayer player, ItemStack items, World world)
	{
		for (int i = 0; i < 2; i++)
		{
			world.spawnParticle("largesmoke", player.posX + (player.getRNG().nextDouble() - 0.5D) * player.width, player.posY + player.getRNG().nextDouble() * player.height - 1.8D, player.posZ + (player.getRNG().nextDouble() - 0.5D) * player.width, 0.0D, 0.0D, 0.0D);

			world.spawnParticle("lava", player.posX + (player.getRNG().nextDouble() - 0.5D) * player.width, player.posY + player.getRNG().nextDouble() * player.height - 1.8D, player.posZ + (player.getRNG().nextDouble() - 0.5D) * player.width, 0.0D, 0.0D, 0.0D);
		}
		player.playSound("fire.fire", 1.1F, 1.0F);
		player.playSound("mob.ghast.fireball", 1.0F, 1.0F);
	}
}
