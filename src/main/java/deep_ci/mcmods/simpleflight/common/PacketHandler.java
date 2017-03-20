package deep_ci.mcmods.simpleflight.common;

import java.io.IOException;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import deep_ci.mcmods.simpleflight.SimpleFlight;
import deep_ci.mcmods.simpleflight.entity.ExtendedEntityPlayer;
import deep_ci.mcmods.simpleflight.event.SimpleFlightEventHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;

public class PacketHandler
{
	public static FMLProxyPacket constructPacket(CommonProxy.Request type, String channel, EntityPlayer player)
	{
		ByteBuf payload = Unpooled.buffer();

		payload.writeShort(type.id);
		SimpleFlightEventHandler.instance().getExProps(player).writeSyncData(payload);

		return new FMLProxyPacket(payload, channel);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static FMLProxyPacket getPacket(CommonProxy.Request type, String channel)
	{
		ByteBuf payload = Unpooled.buffer();
		if (type != CommonProxy.Request.SYNC)
		{
			payload.writeShort(type.id);
		}
		else
		{
			return null;
		}
		return new FMLProxyPacket(payload, channel);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static FMLProxyPacket getSyncPacket(CommonProxy.Request type, String channel, EntityPlayer player)
	{
		ByteBuf payload = Unpooled.buffer();
		if (type != CommonProxy.Request.SYNC)
		{
			payload.writeShort(type.id);
		}
		else
		{
			payload.writeShort(type.id);
			SimpleFlightEventHandler.instance().getExProps(player).writeSyncData(payload);
		}
		return new FMLProxyPacket(payload, channel);
	}

	@SubscribeEvent
	public void onServerPacket(FMLNetworkEvent.ServerCustomPacketEvent event)
	{
		EntityPlayerMP player = ((NetHandlerPlayServer)event.handler).playerEntity;
		ByteBufInputStream stream = new ByteBufInputStream(event.packet.payload());

		short i = -32768;
		try
		{
			i = stream.readShort();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		boolean notifyClient = true;
		CommonProxy.Request r = CommonProxy.Request.SYNC;

		ExtendedEntityPlayer props = SimpleFlightEventHandler.instance().getExProps(player);
		if (i == CommonProxy.Request.FLY.id)
		{
			SimpleFlight.logger.info("Recieved Flight Query (Packet ID #" + i + ":SERVER)");
			if (props.canFlapNow())
			{
				SimpleFlightEventHandler.instance().fly(player);
				r = CommonProxy.Request.FLY;
			}
		}
		else if (i == CommonProxy.Request.GLIDE_OFF.id)
		{
			SimpleFlight.logger.info("Recieved Glide OFF Query (Packet ID #" + i + ":SERVER)");
			if (props.isGlideOn())
			{
				props.setGlideOff();
				r = CommonProxy.Request.GLIDE_OFF;
			}
		}
		else if (i == CommonProxy.Request.GLIDE_ON.id)
		{
			SimpleFlight.logger.info("Recieved Glide ON Query (Packet ID #" + i + ":SERVER)");
			if (props.isGlideOff())
			{
				props.setGlideOn();
				r = CommonProxy.Request.GLIDE_ON;
			}
		}
		else if (i == 2147483647)
		{
			SimpleFlight.logger.info("Recieved Sync Request (Packet ID #" + i + ":SERVER)");
		}
		if (notifyClient)
		{
			SimpleFlight.proxy.sendToClient(r, player);
		}
		try
		{
			stream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
