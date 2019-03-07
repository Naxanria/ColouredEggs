package com.naxanria.colouredeggs.block;

import com.naxanria.colouredeggs.ColouredEggs;
import com.naxanria.colouredeggs.Init;
import com.naxanria.colouredeggs.gui.ModGuiHandler;
import com.naxanria.colouredeggs.tile.TileEgg;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockEgg extends BlockTileBase<TileEgg>
{
  public BlockEgg(String name)
  {
    super(Material.DRAGON_EGG, name);
  }
  
  @Override
  public boolean isOpaqueCube(IBlockState state)
  {
    return false;
  }
  
  @Override
  public boolean hasTileEntity(IBlockState state)
  {
    return true;
  }
  
  public TileEgg getTileEntity(IBlockAccess world, BlockPos pos)
  {
    return (TileEgg) world.getTileEntity(pos);
  }
  
  public Class getTileEntityClass()
  {
    return TileEgg.class;
  }
  
  @Nullable
  @Override
  public TileEgg createTileEntity(World world, IBlockState state)
  {
    return new TileEgg();
  }
  
  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    if (!worldIn.isRemote && !playerIn.isSneaking())
    {
      if (playerIn.getHeldItemMainhand().getItem() == Init.Items.CONFIGURATOR)
      {
        playerIn.openGui(ColouredEggs.instance, ModGuiHandler.EGG_COLOUR_EDITOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
        
        return true;
      }
    }
    
    return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
  }
  
  @Override
  public BlockRenderLayer getBlockLayer()
  {
    return super.getBlockLayer();
  }
}
