package com.naxanria.colouredeggs.gui;

import com.naxanria.colouredeggs.ColouredEggs;
import com.naxanria.colouredeggs.container.ContainerEgg;
import com.naxanria.colouredeggs.tile.TileEgg;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class ModGuiHandler implements IGuiHandler
{
  public static final int EGG_COLOUR_EDITOR = 0;
  
  @Nullable
  @Override
  public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
  {
    if (ID == EGG_COLOUR_EDITOR)
    {
      TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
      
      if (tile instanceof TileEgg)
      {
        return new ContainerEgg((TileEgg) tile, player);
      }
    }
    
    return null;
  }
  
  @Nullable
  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
  {
    Container container = getServerGuiElement(ID, player, world, x, y, z);
    
    if (ID == EGG_COLOUR_EDITOR)
    {
      return new GuiColouredEgg((ContainerEgg) container);
    }
    
    return null;
  }
}
