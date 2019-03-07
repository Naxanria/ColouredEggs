package com.naxanria.colouredeggs.proxy;

import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

public class ServerProxy extends CommonProxy
{
  @Override
  public void registerItemRender(Item item, int meta, String name)
  { }
  
  @Override
  public void registerTileEntityRenderer(Class<? extends TileEntity> tileEntityClass, String tileEntityRenderer)
  { }
  
  @Override
  public void registerColours()
  { }
}
