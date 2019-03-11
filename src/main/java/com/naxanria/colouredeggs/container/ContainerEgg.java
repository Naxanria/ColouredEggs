package com.naxanria.colouredeggs.container;

import com.naxanria.colouredeggs.tile.TileEgg;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;

public class ContainerEgg extends Container
{
  public final TileEgg tile;
  public final EntityPlayer player;
  
  public int colour;
  public int prevColour;
  
  public ContainerEgg(TileEgg egg, EntityPlayer player)
  {
    this.tile = egg;
    this.player = player;
    
    colour = egg.getColour();
    prevColour = colour;
  }
  
  @Override
  public boolean canInteractWith(EntityPlayer playerIn)
  {
    return  (playerIn.isCreative());
  }
  
  @Override
  public void detectAndSendChanges()
  {
    if (colour != tile.getColour())
    {
      for (IContainerListener listener :
        listeners)
      {
        listener.sendWindowProperty(this, 0, tile.getColour());
      }
    }
  }
  
  @Override
  public void updateProgressBar(int id, int data)
  {
    if (id == 0)
    {
      colour = data;
      tile.setColour(data);
    }
  }
}
