package com.naxanria.colouredeggs.item;

import com.naxanria.colouredeggs.ColouredEggs;
import com.naxanria.colouredeggs.tile.TileEgg;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemConfigurator extends ItemBase
{
  public static final Map<UUID, Integer> savedColours = new HashMap<>();
  
  public ItemConfigurator()
  {
    super("configurator");
    
    setMaxStackSize(1);
  }
  
  @Override
  public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
    if (!worldIn.isRemote)
    {
      if (player.isSneaking())
      {
        TileEntity tile = worldIn.getTileEntity(pos);
        
        if (tile != null && tile instanceof TileEgg)
        {
          TileEgg egg = (TileEgg) tile;
          
          Integer data = savedColours.get(player.getUniqueID());
          
          if (data == null)
          {
            int col = egg.getColour();
            savedColours.put(player.getUniqueID(), col);
            
            player.sendMessage(new TextComponentString("Saved the colour."));
  
            return EnumActionResult.SUCCESS;
          }
          else
          {
            egg.setColour(data);
  
            player.sendMessage(new TextComponentString("Pasted the colour."));
  
            return EnumActionResult.SUCCESS;
          }
        }
        
        return EnumActionResult.SUCCESS;
      }
    }
    
    return EnumActionResult.PASS;
  }
  
  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
  {
    if (!worldIn.isRemote)
    {
      if (GuiScreen.isAltKeyDown())
      {
        savedColours.remove(playerIn.getUniqueID());
        
        playerIn.sendMessage(new TextComponentString("Clearing the copied colour"));
      }
    }
    
    return super.onItemRightClick(worldIn, playerIn, handIn);
  }
  
  @Override
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
  {
    tooltip.add("Use this item to edit the colour of the egg.");
    tooltip.add("Sneak right-click an egg to copy its colour.");
    tooltip.add("Sneak right-click on an egg again to paste.");
    tooltip.add("Alt right-click to clear it");
    
    super.addInformation(stack, worldIn, tooltip, flagIn);
  }
}
