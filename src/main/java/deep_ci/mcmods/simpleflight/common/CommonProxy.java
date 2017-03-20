package deep_ci.mcmods.simpleflight.common;

import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class CommonProxy
{
	protected static FMLEventChannel CHANNEL;
	public static final String SIGNAL = "DP_SimpleFlight";

	public void init()
	{
		CHANNEL = NetworkRegistry.INSTANCE.newEventDrivenChannel("DP_SimpleFlight");
		CHANNEL.register(new PacketHandler());
	}

	public void sendToServer(Request r, EntityPlayer player)
	{
		CHANNEL.sendToServer(PacketHandler.constructPacket(r, "DP_SimpleFlight", player));
	}

	public void sendToClient(Request r, EntityPlayerMP player)
	{
		CHANNEL.sendTo(PacketHandler.constructPacket(r, "DP_SimpleFlight", player), player);
	}

	public static enum Request
	{
		SYNC((short)-32768), FLY((short)0), GLIDE_OFF((short)1), GLIDE_ON((short)2);

		public final short id;

		private Request(short id)
		{
			this.id = id;
		}
	}
}
