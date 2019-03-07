package com.naxanria.colouredeggs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ColouredEggsTab extends CreativeTabs
{
  public static final ColouredEggsTab INSTANCE = new ColouredEggsTab();
  
  public ColouredEggsTab()
  {
    super(ColouredEggs.MODID);
  }
  
  @Override
  public ItemStack getTabIconItem()
  {
    return new ItemStack(Init.Blocks.EGG_STANDARD);
  }
}
