package deep_ci.mcmods.simpleflight.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import deep_ci.mcmods.simpleflight.SimpleFlight;
import deep_ci.mcmods.simpleflight.common.CommonProxy;
import deep_ci.mcmods.simpleflight.entity.ExtendedEntityPlayer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public final class SimpleFlightEventHandler
{
	private static SimpleFlightEventHandler INSTANCE;

	private SimpleFlightEventHandler()
	{
		INSTANCE = this;
	}

	public static SimpleFlightEventHandler instance()
	{
		return INSTANCE == null ? new SimpleFlightEventHandler() : INSTANCE;
	}

	public ExtendedEntityPlayer getExProps(EntityPlayer entity)
	{
		ExtendedEntityPlayer extend = (ExtendedEntityPlayer)entity.getExtendedProperties("DP_SimpleFlight:WingProperties");
		if (extend == null)
		{
			entity.registerExtendedProperties("DP_SimpleFlight:WingProperties", new ExtendedEntityPlayer(entity));
			extend = (ExtendedEntityPlayer)entity.getExtendedProperties("DP_SimpleFlight:WingProperties");
		}
		return extend;
	}

	@SubscribeEvent
	public void onConstruction(EntityJoinWorldEvent event)
	{
		if ((event.entity instanceof EntityPlayer))
		{
			this.getExProps((EntityPlayer)event.entity);
		}
	}

	@SubscribeEvent
	public void onUpdateTick(LivingEvent.LivingUpdateEvent event)
	{
		if ((event.entity instanceof EntityPlayer))
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			if (player.isInWater())
			{
				this.getExProps(player).setAltitude(player.posY);
			}
			if (player.onGround)
			{
				this.getExProps(player).setGlideOff();
			}
			if ((!player.onGround) && (!player.isInWater()))
			{
				ExtendedEntityPlayer props = this.getExProps((EntityPlayer)event.entity);
				if (SimpleFlight.side() == Side.CLIENT)
				{
					this.doControllerUpdate(player, event.entity.worldObj);
				}
				if (player.motionY < 0.0D)
				{
					if (props.isGlideForced())
					{
						props.setAltitude(player.posY);
						player.motionY *= 0.6D;
					}
					else if ((props.isAbleToGlide()) && (props.isGlideOn()))
					{
						double fallVelocity = props.getFeatherFall() * 0.15D;
						fallVelocity = 1.0D - fallVelocity;
						player.motionY *= fallVelocity;
						if (props.getFeatherFall() >= 4)
						{
							props.setAltitude(player.posY);
						}
					}
				}
				double upDraft = 0.0D;
				for (int a = 0; a < 6; a++)
				{
					if ((int)Math.floor(player.posY) - a >= 0)
					{
						Block block = player.worldObj.getBlock((int)Math.floor(player.posX), (int)Math.floor(player.posY) - a, (int)Math.floor(player.posZ));
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
				if (upDraft > 0.0D)
				{
					player.addVelocity(0.0D, upDraft, 0.0D);
				}
			}
		}
	}

	@SubscribeEvent
	public void onLanding(LivingFallEvent event)
	{
		if ((event.entity instanceof EntityPlayer))
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			ExtendedEntityPlayer props = this.getExProps(player);
			props.updateArmor();

			double currLoc = event.entity.posY;
			double lastLoc = props.getLastSafeAltitude();
			SimpleFlight.logger.info("Last Location: " + lastLoc + ", Current Location: " + currLoc + ", Difference: " + (lastLoc - currLoc));
			event.distance = ((float)(lastLoc < currLoc ? 0.0D : lastLoc - currLoc));
			if ((props.isAbleToGlide()) && (props.isGlideOn()))
			{
				float dist = event.distance;
				float quart = dist / 4.0F;
				for (int i = 0; i < props.getFeatherFall(); i++)
				{
					dist -= quart;
				}
				event.distance = dist;
			}
			props.onGrounded();
		}
	}

	@SideOnly(Side.CLIENT)
	public void doControllerUpdate(EntityPlayer player, World world)
	{
		Minecraft mc = Minecraft.getMinecraft();
		ExtendedEntityPlayer props = this.getExProps(player);
		if ((props.hasFlapsRemaining()) && (mc.gameSettings.keyBindJump.getIsKeyPressed()))
		{
			SimpleFlight.proxy.sendToServer(CommonProxy.Request.FLY, player);
		}
		if (props.isAbleToGlide())
		{
			if ((props.isGlideOn()) && (mc.gameSettings.keyBindSneak.getIsKeyPressed()))
			{
				SimpleFlight.proxy.sendToServer(CommonProxy.Request.GLIDE_OFF, player);
			}
			else if ((props.isGlideOff()) && (!mc.gameSettings.keyBindSneak.getIsKeyPressed()))
			{
				SimpleFlight.proxy.sendToServer(CommonProxy.Request.GLIDE_ON, player);
			}
		}
	}

	public void fly(EntityPlayer player)
	{
		player.jump();
		this.getExProps(player).onFlapping();
	}
}
