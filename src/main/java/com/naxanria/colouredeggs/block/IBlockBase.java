package com.naxanria.colouredeggs.block;

import com.naxanria.colouredeggs.tile.TileEgg;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IBlockBase
{
  Block getBlock();
  
  Item createItemBlock();
  
  void registerItemModel(Item itemBlock);
  
  void registerItemModel();
}
