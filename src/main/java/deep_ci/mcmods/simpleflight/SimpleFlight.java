package deep_ci.mcmods.simpleflight;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import deep_ci.mcmods.simpleflight.common.CommonProxy;
import java.util.Arrays;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "DP_SimpleFlight", version = "0.9-prerelease")
public class SimpleFlight
{
	public static final String MODID = "DP_SimpleFlight";
	public static final String NAME = "Simple Flight";
	public static final String VERSION = "0.9-prerelease";
	@SidedProxy(clientSide = "deep_ci.mcmods.simpleflight.client.ClientProxy", serverSide = "deep_ci.mcmods.simpleflight.common.CommonProxy")
	public static CommonProxy proxy;
	public static SimpleConfig config;
	public static ModItems items;
	public static final Logger logger = LogManager.getLogger("SimpleFlight");

	@Mod.EventHandler
	public void init(FMLPreInitializationEvent event)
	{
		this.repairModMeta(event.getModMetadata());
		SimpleFlightAPI.init(event.getSide());

		config = SimpleFlightAPI.getConfig();
		items = ModItems.instance();
		items.load();

		logger.log(Level.INFO, "Simple Flight has pre-initialized.");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init();
		logger.log(Level.INFO, "Simple Flight has initialized.");
	}

	public static Side side()
	{
		return FMLCommonHandler.instance().getEffectiveSide();
	}

	private void repairModMeta(ModMetadata modmeta)
	{
		modmeta.name = "Simple Flight";
		modmeta.authorList = Arrays.asList(new String[] { "DeeP_ci" });
		modmeta.url = "http://www.curseforge.com/projects/221501/";
		modmeta.updateUrl = "http://minecraft.curseforge.com/mc-mods/221501-simple-flight/files";
		modmeta.description = "Simple Flight isn't your average wimpy flight mod that has you creative fly in survival mode. No, simple flight is HARDCORE. You can take fall damage if you're not careful, you can only fly for a limited amount of flaps per flight, and not all wings allow you to glide safetly.\n\nGood luck, survivalist. You'll be needing it.";
	}
}
