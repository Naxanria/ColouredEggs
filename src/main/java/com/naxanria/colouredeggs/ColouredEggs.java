package com.naxanria.colouredeggs;


import com.naxanria.colouredeggs.gui.ModGuiHandler;
import com.naxanria.colouredeggs.network.PacketHandler;
import com.naxanria.colouredeggs.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

@Mod
(
  modid = ColouredEggs.MODID,
  name = ColouredEggs.NAME,
  version = ColouredEggs.VERSION
)
public class ColouredEggs
{
  public static final String MODID = "colouredeggs";
  public static final String NAME = "Coloured Eggs";
  public static final String VERSION = "${version}";
  
  public static final String PACKAGE = "com.naxanria.colouredeggs";
  public static final String PROXY = PACKAGE + ".proxy";
  
  public static Logger logger;
  
  @Mod.Instance
  public static ColouredEggs instance;
  
  @SidedProxy(clientSide = PROXY + ".ClientProxy", serverSide = PROXY + ".ServerProxy")
  public static CommonProxy proxy;
  
  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event)
  {
    logger = event.getModLog();
    logger.info("Pre-initialization");
  
    PacketHandler.init();
    
    NetworkRegistry.INSTANCE.registerGuiHandler(this, new ModGuiHandler());
  }
  
  @Mod.EventHandler
  public void init(FMLInitializationEvent event)
  {
    logger.info("Registering colour handlers.");
    proxy.registerColours();
    
    
  }
  
  @Mod.EventBusSubscriber
  public static class RegistryHandler
  {
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
      logger.info("Registering items");
  
      Init.init();
      
      Init.registerItems(event.getRegistry());
      
//      event.getRegistry().register(Init.Items.CONFIGURATOR);
//      event.getRegistry().register(new ItemBlock(Init.Blocks.EGG).setRegistryName(Init.Blocks.EGG.getRegistryName()));
    }
    
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
      logger.info("Registering blocks");
  
      Init.init();
      
      Init.registerBlocks(event.getRegistry());
      
//      event.getRegistry().register(Init.Blocks.EGG);
//      GameRegistry.registerTileEntity(Init.Blocks.EGG.getTileEntityClass(), Init.Blocks.EGG.getRegistryName());
//      String tileEntityRenderClass = Init.Blocks.EGG.getTileEntityRenderClass();
//      if (tileEntityRenderClass != null)
//      {
//        proxy.registerTileEntityRenderer(Init.Blocks.EGG.getTileEntityClass(), tileEntityRenderClass);
//      }
      
      
    }
    
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
      logger.info("Registering models");
  
      Init.init();
      
      Init.registerModels();
//      proxy.registerItemRender(Item.getItemFromBlock(Init.Blocks.EGG), 0, Init.Blocks.EGG.name);
//      proxy.registerItemRender(Init.Items.CONFIGURATOR, 0, Init.Items.CONFIGURATOR.name);
    }
  }
  
}
