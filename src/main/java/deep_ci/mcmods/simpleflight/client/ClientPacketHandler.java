package deep_ci.mcmods.simpleflight.client;

import java.io.IOException;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import deep_ci.mcmods.simpleflight.SimpleFlight;
import deep_ci.mcmods.simpleflight.common.CommonProxy;
import deep_ci.mcmods.simpleflight.common.PacketHandler;
import deep_ci.mcmods.simpleflight.event.SimpleFlightEventHandler;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientPacketHandler extends PacketHandler
{
	@SubscribeEvent
	public void onClientPacket(FMLNetworkEvent.ClientCustomPacketEvent event)
	{
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
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
		if (i == CommonProxy.Request.SYNC.id)
		{
			SimpleFlight.logger.info("Recieved SYNC Demand (Packet ID #" + i + ":CLIENT)");
			try
			{
				SimpleFlightEventHandler.instance().getExProps(player).readSyncData(stream);
			}
			catch (IOException e2)
			{
				e2.printStackTrace();
			}
		}
		else if (i == CommonProxy.Request.FLY.id)
		{
			SimpleFlight.logger.info("Recieved Flight Demand (Packet ID #" + i + ":CLIENT)");
			SimpleFlightEventHandler.instance().fly(player);
		}
		else if (i == CommonProxy.Request.GLIDE_OFF.id)
		{
			SimpleFlight.logger.info("Recieved Glide OFF Demand (Packet ID #" + i + ":CLIENT)");
			SimpleFlightEventHandler.instance().getExProps(player).setGlideOff();
		}
		else if (i == CommonProxy.Request.GLIDE_ON.id)
		{
			SimpleFlight.logger.info("Recieved Glide ON Demand (Packet ID #" + i + ":CLIENT)");
			SimpleFlightEventHandler.instance().getExProps(player).setGlideOn();
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
