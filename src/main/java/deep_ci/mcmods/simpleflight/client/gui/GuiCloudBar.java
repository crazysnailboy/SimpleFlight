package deep_ci.mcmods.simpleflight.client.gui;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import deep_ci.mcmods.simpleflight.entity.ExtendedEntityPlayer;
import deep_ci.mcmods.simpleflight.event.SimpleFlightEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiCloudBar extends Gui
{
	private Minecraft mc;
	private static final ResourceLocation texturePath = new ResourceLocation("DP_SimpleFlight:textures/gui/icons.png");

	public GuiCloudBar(Minecraft mc)
	{
		this.mc = mc;
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderExperienceBar(RenderGameOverlayEvent event)
	{
		if ((event.isCancelable()) || (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE))
		{
			return;
		}
		ExtendedEntityPlayer props = SimpleFlightEventHandler.instance().getExProps(this.mc.thePlayer);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(2896);

		this.mc.getTextureManager().bindTexture(texturePath);
		if (props.getMaxFlaps() <= 0)
		{
			return;
		}
		int used = (int)Math.round(props.getCurrFlaps() / props.getMaxFlaps() * 20.0D);
		int remain = 20 - used;

		int xPos = event.resolution.getScaledWidth() / 2 + 10;
		int yPos = event.resolution.getScaledHeight() - (this.mc.thePlayer.getAir() == 300 ? 49 : 59);

		int empty = 16;
		int half = 25;
		int full = 34;

		int renderAs = full;
		for (int i = 0; i < 20; i++)
		{
			if (remain == i)
			{
				renderAs = half;
			}
			if (remain < i)
			{
				renderAs = empty;
			}
			else if (remain > i)
			{
				renderAs = full;
			}
			if (this.isOdd(i))
			{
				this.drawTexturedModalRect(xPos + 8 * (i / 2), yPos, renderAs, 0, 9, 9);
			}
		}
	}

	private boolean isOdd(int i)
	{
		return (i & 0x1) != 0;
	}
}
