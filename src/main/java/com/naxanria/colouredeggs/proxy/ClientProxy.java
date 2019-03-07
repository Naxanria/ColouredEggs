package com.naxanria.colouredeggs.proxy;

import com.naxanria.colouredeggs.ColouredEggs;
import com.naxanria.colouredeggs.Init;
import com.naxanria.colouredeggs.model.ItemColourEgg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{
  @Override
  public void registerItemRender(Item item, int meta, String name)
  {
    ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(ColouredEggs.MODID + ":" + name, "inventory"));
  }
  
  @Override
  public void registerTileEntityRenderer(Class<TileEntity> tileEntityClass, String tileEntityRenderer)
  {
    try
    {
      Class clazz = Class.forName(tileEntityRenderer);
      TileEntitySpecialRenderer renderer = (TileEntitySpecialRenderer) clazz.newInstance();
      ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, renderer);
      ColouredEggs.logger.info("Successfully registered renderer " + tileEntityRenderer + " for " + tileEntityClass.getCanonicalName());
    }
    catch (ClassNotFoundException | IllegalAccessException | InstantiationException e)
    {
      ColouredEggs.logger.error("Unable to register renderer " + tileEntityRenderer + " for " + tileEntityClass.getCanonicalName());
      e.printStackTrace();
    }
  }
  
  @Override
  public void registerColours()
  {
    Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(Init.Blocks.EGG, Init.Blocks.EGG);
    
    Minecraft.getMinecraft().getItemColors()
      .registerItemColorHandler(ItemColourEgg.INSTANCE, Init.Blocks.EGG);
  }
  
}
