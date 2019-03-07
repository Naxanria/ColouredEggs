package com.naxanria.colouredeggs;

import com.naxanria.colouredeggs.block.BlockBase;
import com.naxanria.colouredeggs.block.BlockEgg;
import com.naxanria.colouredeggs.block.BlockTileBase;
import com.naxanria.colouredeggs.block.IBlockBase;
import com.naxanria.colouredeggs.item.IItemBase;
import com.naxanria.colouredeggs.item.ItemConfigurator;
import com.naxanria.colouredeggs.model.BlockColourEgg;
import com.naxanria.colouredeggs.model.ItemColourEgg;
import com.naxanria.colouredeggs.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class Init
{
  public static class Items
  {
    public static final ItemConfigurator CONFIGURATOR = new ItemConfigurator();
  }
  
  public static class Blocks
  {
    public static final BlockEgg EGG_STANDARD = new BlockEgg("block_egg_standard");
    public static final BlockEgg EGG_STONE = new BlockEgg("block_egg_stone");
    public static final BlockEgg EGG_FLAT = new BlockEgg("block_egg_flat");
    public static final BlockEgg EGG_DARK_SPOTS = new BlockEgg("block_egg_dark_spots");
    public static final BlockEgg EGG_STRIPES_VERT = new BlockEgg("block_egg_stripes_vert");
    public static final BlockEgg EGG_STRIPES_HOR = new BlockEgg("block_egg_stripes_hor");
    public static final BlockEgg EGG_NOISE = new BlockEgg("block_egg_noise");
  }
  
  private static final List<IBlockBase> blocksToAdd = new ArrayList<>();
  private static final List<IItemBase> itemsToAdd = new ArrayList<>();
  
  private static void addAll(Block... blocks)
  {
    blocksToAdd.addAll(Arrays.asList(blocks));
  }
  
  private static void addAll(Item... items)
  {
    itemsToAdd.addAll(Arrays.asList(items));
  }
  
  private static boolean initialized = false;
  
  public static void init()
  {
    if (initialized)
    {
      return;
    }
    
    initialized = true;
    
    addAll
    (
      Blocks.EGG_STANDARD,
      Blocks.EGG_FLAT,
      Blocks.EGG_STONE,
      Blocks.EGG_DARK_SPOTS,
      Blocks.EGG_STRIPES_HOR,
      Blocks.EGG_STRIPES_VERT,
      Blocks.EGG_NOISE
    );
    
    addAll
    (
      Items.CONFIGURATOR
    );
  }
  
  public static void registerBlocks(IForgeRegistry<Block> registry)
  {
    List<String> registered = new ArrayList<>();
    
    for (IBlockBase blockBase :
      blocksToAdd)
    {
      BlockBase b = (BlockBase) blockBase.getBlock();
      
      registry.register(b);
      
      if (b.hasTileEntity(b.getDefaultState()))
      {
        Class c = ((BlockTileBase)b).getTileEntityClass();
        
        if (registered.contains(c.getCanonicalName()))
        {
          continue;
        }

        GameRegistry.registerTileEntity(c, b.getRegistryName());
        
        registered.add(c.getCanonicalName());
      }
    }
    
    ColouredEggs.logger.info("Registered " + blocksToAdd.size() + " blocks");
  }
  
  public static void registerItems(IForgeRegistry<Item> registry)
  {
    for (IItemBase item :
      itemsToAdd)
    {
      registry.register(item.getItem());
    }
  
    ColouredEggs.logger.info("Registered " + itemsToAdd.size() + " items");
    
    for (IBlockBase bb :
      blocksToAdd)
    {

      registry.register(bb.createItemBlock());
    }
  
    ColouredEggs.logger.info("Registered " + blocksToAdd.size() + " item-blocks");
  }
  
  public static void registerModels()
  {
    CommonProxy proxy = ColouredEggs.proxy;
  
    for (IItemBase item :
         itemsToAdd)
    {
      proxy.registerItemRender(item.getItem(), 0, item.getName());
    }
    
    for (IBlockBase bb :
      blocksToAdd)
    {
      bb.registerItemModel();
    }
  }
  
  public static void registerColours()
  {
    Minecraft mc = Minecraft.getMinecraft();
    
    for (IBlockBase bb :
      blocksToAdd)
    {
      if (bb.getBlock() instanceof BlockEgg)
      {
        mc.getBlockColors().registerBlockColorHandler(BlockColourEgg.INSTANCE, bb.getBlock());
        mc.getItemColors().registerItemColorHandler(ItemColourEgg.INSTANCE, bb.getBlock());
      }
    }
  }
}
