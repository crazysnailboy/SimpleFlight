package deep_ci.mcmods.simpleflight.item;

import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import deep_ci.mcmods.simpleflight.SimpleFlight;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

/**
 * @deprecated
 */
@Deprecated
public class OutdatedItemArmorSimpleWings extends ItemArmor
{
	public final int flapsPerFlight;
	public final int flapCooldownTime;
	protected boolean isGliding = true;
	protected double lastSafeHeight = 0.0D;
	public final boolean isGlider;
	protected int flapCounter;
	protected long lastFlap;

	public OutdatedItemArmorSimpleWings(int maxFlaps, int flapCooldown, boolean isGlider, ItemArmor.ArmorMaterial armorType, int renderType, int armorSlot)
	{
		super(armorType, renderType, armorSlot);

		this.flapsPerFlight = maxFlaps;
		this.flapCooldownTime = flapCooldown;

		this.isGlider = isGlider;
		this.flapCounter = 0;
		this.lastFlap = -9223372036854775808L;
	}

	public OutdatedItemArmorSimpleWings(ItemArmor.ArmorMaterial armorType, int renderType, int armorSlot)
	{
		this(0, 0, false, armorType, renderType, armorSlot);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack item)
	{
		if ((world.isRemote) && (!player.onGround) && (player.isAirBorne) && (!player.isRiding()))
		{
			if (Minecraft.getMinecraft().gameSettings.keyBindJump.getIsKeyPressed())
			{
				this.tryToFly(world, player, item);
			}
			if (Minecraft.getMinecraft().gameSettings.keyBindSneak.getIsKeyPressed())
			{
				this.switchGlideMode(false, world, player, item);
			}
			else
			{
				this.switchGlideMode(true, world, player, item);
			}
		}
		if (!player.onGround)
		{
			if (this.isGlider(item, player))
			{
				this.lastSafeHeight = player.posY;
			}
			if (this.isGliding)
			{
				double upDraft = 0.0D;
				for (int a = 0; a < 6; a++)
				{
					if ((int)Math.floor(player.posY) - a >= 0)
					{
						Block block = world.getBlock((int)Math.floor(player.posX), (int)Math.floor(player.posY) - a, (int)Math.floor(player.posZ));
						if ((a < 3) && (block == Blocks.fire))
						{
							upDraft += 0.5D;
						}
						else if (block == Blocks.lava)
						{
							upDraft += 1.0D;
							a = 6;
						}
					}
					else
					{
						a = 6;
					}
				}
				player.addVelocity(0.0D, upDraft, 0.0D);
			}
		}
		if (player.onGround)
		{
			this.isGliding = true;
			this.flapCounter = 0;
			this.lastSafeHeight = 1.7976931348623157E+308D;
			this.lastFlap = -9223372036854775808L;
		}
	}

	public final void switchGlideMode(boolean glideOn, World world, EntityPlayer player, ItemStack items)
	{
		boolean flag1 = this.isGliding;
		boolean flag2 = glideOn;
		if (flag1 != flag2)
		{
			if (world.isRemote)
			{
				ByteBuf payload = Unpooled.buffer();
				payload.writeInt(1);
				payload.writeInt(this.armorType);
				payload.writeBoolean(glideOn);
			}
			this.isGliding = glideOn;
		}
	}

	public final void tryToFly(World world, EntityPlayer player, ItemStack items)
	{
		boolean flag1 = false;
		boolean flag2 = false;
		if ((player.isAirBorne) && (!player.isRiding()))
		{
			flag1 = true;
		}
		if ((this.flapCounter < this.getMaxFlaps(items, player)) && (MinecraftServer.getCurrentTimeMillis() >= this.lastFlap + this.getFlapsCooldownMS(items, player)))
		{
			flag2 = true;
		}
		if ((flag1) && (flag2))
		{
			if (world.isRemote)
			{
				ByteBuf payload = Unpooled.buffer();
				payload.writeInt(1);
				payload.writeInt(this.armorType);
			}
			this.flap(world, player, items);
		}
	}

	private final void flap(World world, EntityPlayer player, ItemStack items)
	{
		SimpleFlight.logger.info("Flapping.");
		this.lastFlap = MinecraftServer.getCurrentTimeMillis();
		this.flapCounter += 1;
		this.lastSafeHeight = player.posY;
		player.jump();

		this.onFlap(world, player, items);
		player.playSound(this.getSound(items, player, world), this.getSoundVolume(items, player, world), this.getSoundPitch(items, player, world));
	}

	public void onFlap(World world, EntityPlayer player, ItemStack items)
	{
	}

	public boolean getIsGliding(ItemStack itemstack, EntityLivingBase entity)
	{
		return this.isGlider ? true : this.isGliding;
	}

	public double getLastHeight(ItemStack itemstack, EntityLivingBase entity)
	{
		return this.lastSafeHeight;
	}

	public boolean isGlider(ItemStack itemstack, EntityLivingBase entity)
	{
		return this.isGlider;
	}

	public int getMaxFlaps(ItemStack itemstack, EntityPlayer player)
	{
		int finalFlaps = this.flapsPerFlight;
		if (itemstack.isItemEnchanted())
		{
			NBTTagList nbt = itemstack.getEnchantmentTagList();
			for (int i = 0; i < nbt.tagCount(); i++)
			{
				NBTTagCompound comp = nbt.getCompoundTagAt(i);
				if (comp.getInteger("id") == SimpleFlight.items.flyLonger.effectId)
				{
					float power = (float)(0.2D * comp.getInteger("lvl"));
					finalFlaps += (int)(finalFlaps * power);
				}
			}
		}
		return finalFlaps;
	}

	public long getFlapsCooldownMS(ItemStack itemstack, EntityPlayer player)
	{
		long cooldown = (int)(this.flapCooldownTime / 20.0F * 1000.0F);
		if (itemstack.isItemEnchanted())
		{
			NBTTagList nbt = itemstack.getEnchantmentTagList();
			for (int i = 0; i < nbt.tagCount(); i++)
			{
				NBTTagCompound comp = nbt.getCompoundTagAt(i);
				if (comp.getInteger("id") == SimpleFlight.items.flyFaster.effectId)
				{
					int power = comp.getInteger("lvl");
					cooldown -= power * 100;
				}
			}
		}
		return cooldown < 0L ? 0L : cooldown;
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public int getFlapsCooldown(ItemStack itemstack, EntityPlayer player)
	{
		int cooldown = this.flapCooldownTime;
		if (itemstack.isItemEnchanted())
		{
			NBTTagList nbt = itemstack.getEnchantmentTagList();
			for (int i = 0; i < nbt.tagCount(); i++)
			{
				NBTTagCompound comp = nbt.getCompoundTagAt(i);
				if (comp.getInteger("id") == SimpleFlight.items.flyFaster.effectId)
				{
					int power = comp.getInteger("lvl");
					cooldown -= power;
				}
			}
		}
		return cooldown;
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

	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack item, Entity entity, int slot, String type)
	{
		return super.getArmorTexture(item, entity, slot, type);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
	{
		return super.getArmorModel(entityLiving, itemStack, armorSlot);
	}

	public float getFlapFactor()
	{
		float factor = 0.0F;
		long MScool = (long)(this.flapCooldownTime / 20.0F * 1000.0F);
		if (MinecraftServer.getCurrentTimeMillis() < MScool + this.lastFlap)
		{
			factor = (float)(MinecraftServer.getCurrentTimeMillis() - this.lastFlap) / (float)MScool;
		}
		return factor;
	}

	@Override
	public void addInformation(ItemStack items, EntityPlayer player, List list, boolean bool)
	{
		if ((items.getItem() instanceof OutdatedItemArmorSimpleWings))
		{
			OutdatedItemArmorSimpleWings wings = (OutdatedItemArmorSimpleWings)items.getItem();
			list.add(I18n.format("dp_simpleflight.tooltip.flapcount", new Object[] { Integer.valueOf(wings.getMaxFlaps(items, player)) }));

			list.add(I18n.format("dp_simpleflight.tooltip.cooldown", new Object[] { Float.valueOf(wings.getFlapsCooldown(items, player) / 20.0F) }));
		}
	}
}
