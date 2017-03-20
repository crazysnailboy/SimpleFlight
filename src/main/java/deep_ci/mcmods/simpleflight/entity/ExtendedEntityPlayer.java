package deep_ci.mcmods.simpleflight.entity;

import java.io.IOException;
import java.util.List;
import cpw.mods.fml.relauncher.Side;
import deep_ci.mcmods.simpleflight.SimpleFlight;
import deep_ci.mcmods.simpleflight.common.CommonProxy;
import deep_ci.mcmods.simpleflight.item.ItemArmorSimpleWings;
import deep_ci.mcmods.simpleflight.item.WingItemJetpack;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedEntityPlayer implements IExtendedEntityProperties
{
	public static final String IDENTIFIER = "DP_SimpleFlight:WingProperties";
	private EntityPlayer entity;
	private ItemStack[] armorPieces = new ItemStack[4];
	private int maxFlaps = 0;
	private long flapCooldown = 0L;
	private int featherFall = 0;
	private boolean wearingGlider = false;
	private int flapCounter = 0;
	private boolean isGlideOff = false;
	private long lastFlapMS;
	private double lastSafeHeight;

	public ExtendedEntityPlayer(EntityPlayer entity)
	{
		this.init(entity, entity.worldObj);
	}

	@Override
	public void init(Entity entity, World world)
	{
		this.entity = ((EntityPlayer)entity);
		this.initArmor();
	}

	public void updateArmor()
	{
		boolean armorChanged = false;
		for (int i = 1; i < 5; i++)
		{
			ItemStack itemArmor = this.entity.getEquipmentInSlot(i);
			if (ItemStack.areItemStacksEqual(itemArmor, this.armorPieces[(i - 1)]))
			{
				armorChanged = true;
			}
			this.armorPieces[(i - 1)] = itemArmor;
		}
		if (armorChanged)
		{
			this.initArmor();
		}
	}

	public void initArmor()
	{
		this.maxFlaps = 0;
		this.flapCooldown = 0L;
		this.featherFall = 0;
		this.wearingGlider = false;

		boolean wearingWings = false;
		int gliderBonus = 0;

		int bonusFlaps = 0;
		int bonusCooldown = 0;
		int featherLevel = 0;

		int maxFlaps = 0;
		int flapsCooldown = 0;
		for (int i = 1; i < 5; i++)
		{
			// ItemStack itemArmor = this.armorPieces[(i - 1)] =  = this.entity.getEquipmentInSlot(i); // decompilation error? TODO
			ItemStack itemArmor = this.armorPieces[(i - 1)]; if (itemArmor == null) itemArmor = this.entity.getEquipmentInSlot(i);
			if (itemArmor != null)
			{
				boolean isNotWings = true;
				if ((itemArmor.getItem() instanceof ItemArmorSimpleWings))
				{
					ItemArmorSimpleWings item = (ItemArmorSimpleWings)itemArmor.getItem();

					this.wearingGlider = (item.isGlider() ? true : this.wearingGlider);
					isNotWings = (item.isGlider()) && (!(item instanceof WingItemJetpack));
					wearingWings = !isNotWings ? true : wearingWings;

					int armorFlapValue = item.getBaseFlapCount();
					int armorCooldown = item.getBaseFlapCooldown();

					this.maxFlaps = Math.max(this.maxFlaps, armorFlapValue);
					this.flapCooldown = Math.max(this.flapCooldown, armorCooldown);
				}
				if (itemArmor.isItemEnchanted())
				{
					NBTTagList nbt = itemArmor.getEnchantmentTagList();
					for (int e = 0; e < nbt.tagCount(); e++)
					{
						NBTTagCompound comp = nbt.getCompoundTagAt(e);
						if (comp.getInteger("id") == SimpleFlight.items.flyFaster.effectId)
						{
							int lvl = comp.getInteger("lvl");
							bonusCooldown += 100 * lvl;
						}
						if (comp.getInteger("id") == SimpleFlight.items.flyLonger.effectId)
						{
							if (isNotWings)
							{
								gliderBonus += comp.getInteger("lvl") * 4;
							}
							else
							{
								int lvl = comp.getInteger("lvl");
								bonusFlaps += 4 * lvl;
							}
						}
						if (comp.getInteger("id") == Enchantment.featherFalling.effectId)
						{
							int lvl = comp.getInteger("lvl");
							featherLevel += lvl;
						}
					}
				}
			}
			if (wearingWings)
			{
				bonusFlaps += gliderBonus;
			}
			this.featherFall = Math.min(featherLevel, Enchantment.featherFalling.getMaxLevel());
			this.maxFlaps += Math.min(4 * SimpleFlight.items.flyLonger.getMaxLevel(), bonusFlaps);
			this.flapCooldown -= Math.min(100 * SimpleFlight.items.flyFaster.getMaxLevel(), bonusCooldown);
		}
	}

	public void sync()
	{
		this.updateArmor();
		if (SimpleFlight.side() == Side.CLIENT)
		{
			SimpleFlight.proxy.sendToServer(CommonProxy.Request.SYNC, this.entity);
		}
		else
		{
			for (WorldServer server : MinecraftServer.getServer().worldServers)
			{
				List list = server.playerEntities;
				for (int i = 0; i < list.size(); i++)
				{
					Object o = list.get(i);
					if ((o instanceof EntityPlayerMP))
					{
						if (((EntityPlayerMP)o).getUniqueID() == this.entity.getUniqueID())
						{
							SimpleFlight.proxy.sendToClient(CommonProxy.Request.SYNC, (EntityPlayerMP)o);
						}
					}
				}
			}
		}
	}

	public void onGrounded()
	{
		this.flapCounter = 0;
		this.lastFlapMS = 0L;
		this.lastSafeHeight = this.entity.posY;
		this.updateArmor();
	}

	public void onFlapping()
	{
		this.flapCounter += 1;
		this.lastFlapMS = MinecraftServer.getCurrentTimeMillis();
		this.lastSafeHeight = this.entity.posY;
		for (int a = 0; a < 4; a++)
		{
			ItemStack item = this.armorPieces[a];
			if ((item != null) && ((item.getItem() instanceof ItemArmorSimpleWings)))
			{
				((ItemArmorSimpleWings)item.getItem()).onFlap(this.entity, item, this.entity.worldObj);
			}
		}
	}

	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setInteger("CurrentFlapCount", this.flapCounter);

		compound.setTag("DP_SimpleFlight:WingProperties", nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound nbt = (NBTTagCompound)compound.getTag("DP_SimpleFlight:WingProperties");

		this.flapCounter = nbt.getInteger("CurrentFlapCount");
	}

	public float getFlapFactor()
	{
		float factor = 0.0F;
		if (MinecraftServer.getCurrentTimeMillis() < this.flapCooldown + this.lastFlapMS)
		{
			factor = (float)(MinecraftServer.getCurrentTimeMillis() - this.lastFlapMS) / (float)this.flapCooldown;
		}
		return factor;
	}

	public int getCurrFlaps()
	{
		return this.flapCounter;
	}

	public long getLastFlapMS()
	{
		return this.lastFlapMS;
	}

	public long getCooldownMS()
	{
		return this.flapCooldown;
	}

	public int getMaxFlaps()
	{
		return this.maxFlaps;
	}

	public double getLastSafeAltitude()
	{
		return this.lastSafeHeight;
	}

	public boolean isGlideForced()
	{
		return this.wearingGlider;
	}

	public boolean isGlideOn()
	{
		return !this.isGlideOff;
	}

	public boolean isGlideOff()
	{
		return this.isGlideOff;
	}

	public boolean hasFlapsRemaining()
	{
		this.updateArmor();
		return this.flapCounter < this.maxFlaps;
	}

	public boolean canFlapNow()
	{
		return (this.hasFlapsRemaining()) && (this.flapCooldown + this.lastFlapMS <= MinecraftServer.getCurrentTimeMillis());
	}

	public void setGlideOn()
	{
		this.isGlideOff = false;
	}

	public void setGlideOff()
	{
		this.isGlideOff = true;
	}

	public void setAltitude(double posY)
	{
		this.lastSafeHeight = posY;
	}

	public boolean isAbleToGlide()
	{
		this.updateArmor();
		return this.featherFall > 0;
	}

	public int getFeatherFall()
	{
		return this.featherFall;
	}

	public void writeSyncData(ByteBuf payload)
	{
		payload.writeLong(this.lastFlapMS);
		payload.writeBoolean(this.isGlideOff);
		payload.writeInt(this.flapCounter);
		payload.writeInt((int)this.lastSafeHeight);
	}

	public void readSyncData(ByteBufInputStream stream) throws IOException
	{
		this.lastFlapMS = stream.readLong();
		this.isGlideOff = stream.readBoolean();
		this.flapCounter = stream.readInt();
		this.lastSafeHeight = stream.readInt();
	}
}
