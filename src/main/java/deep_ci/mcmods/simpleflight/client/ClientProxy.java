package deep_ci.mcmods.simpleflight.client;

import cpw.mods.fml.common.network.NetworkRegistry;
import deep_ci.mcmods.simpleflight.client.gui.GuiCloudBar;
import deep_ci.mcmods.simpleflight.common.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		CHANNEL = NetworkRegistry.INSTANCE.newEventDrivenChannel("DP_SimpleFlight");
		CHANNEL.register(new ClientPacketHandler());

		MinecraftForge.EVENT_BUS.register(new GuiCloudBar(Minecraft.getMinecraft()));
	}
}
