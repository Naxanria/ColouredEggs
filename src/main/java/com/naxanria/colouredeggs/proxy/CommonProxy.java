package com.naxanria.colouredeggs.proxy;

import com.naxanria.colouredeggs.tile.TileEgg;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

public abstract class CommonProxy
{
  public abstract void registerItemRender(Item item, int meta, String name);
  
  public abstract void registerTileEntityRenderer(Class<TileEntity> tileEntityClass, String tileEntityRenderer);
  
  public abstract void registerColours();
}
