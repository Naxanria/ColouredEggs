package com.naxanria.colouredeggs.model;

import com.naxanria.colouredeggs.ColouredEggs;
import com.naxanria.colouredeggs.block.BlockTileBase;
import com.naxanria.colouredeggs.tile.TileEgg;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockColourEgg implements IBlockColor
{
  public static final BlockColourEgg INSTANCE = new BlockColourEgg();
  
  @Override
  public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
  {
    TileEgg egg = (TileEgg) ((BlockTileBase)state.getBlock()).getTileEntity(worldIn, pos);
    
    if (egg != null)
    {
//      ColouredEggs.logger.info("== colour request: " + pos + " [" + egg.getRed() + "," + egg.getGreen() + "," + egg.getBlue() + "] ==");
      return egg.getColour();
    }
//
//    if (worldIn != null && worldIn instanceof World)
//    {
//      ((World)worldIn).scheduleBlockUpdate(pos, state.getBlock(), 10, 0);
//    }
    
    return 0xFF323232;
  }
}
