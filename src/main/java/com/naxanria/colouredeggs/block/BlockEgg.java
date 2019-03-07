package com.naxanria.colouredeggs.block;

import com.naxanria.colouredeggs.ColouredEggs;
import com.naxanria.colouredeggs.Init;
import com.naxanria.colouredeggs.gui.ModGuiHandler;
import com.naxanria.colouredeggs.model.ItemColourEgg;
import com.naxanria.colouredeggs.tile.TileEgg;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockEgg extends Block implements IBlockColor
{
  public final String name;
  
  public BlockEgg()
  {
    super(Material.DRAGON_EGG);
    
    name = "block_egg";
    
    setRegistryName(ColouredEggs.MODID, name);
    setUnlocalizedName(ColouredEggs.MODID + name);
    
    setCreativeTab(CreativeTabs.DECORATIONS);
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
  public TileEntity createTileEntity(World world, IBlockState state)
  {
    return new TileEgg();
  }
  
  public String getTileEntityRenderClass()
  {
    return null;
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
  public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
  {
//    return ItemColourEgg.INSTANCE.colorMultiplier(null, 0);

    TileEgg egg = getTileEntity(worldIn, pos);

    if (egg != null)
    {
      return egg.getColour();
    }
    
    if (worldIn != null && worldIn instanceof World)
    {
      ((World)worldIn).scheduleBlockUpdate(pos, state.getBlock(), 10, 0);
    }
    
    return 0xFF323232;
  }
  
  @Override
  public BlockRenderLayer getBlockLayer()
  {
    return super.getBlockLayer();
  }
}
