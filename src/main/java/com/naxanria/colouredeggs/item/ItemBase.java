package com.naxanria.colouredeggs.item;

import com.naxanria.colouredeggs.ColouredEggs;
import com.naxanria.colouredeggs.ColouredEggsTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public abstract class ItemBase extends Item implements IItemBase
{
  public final String name;
  
  public ItemBase(String name)
  {
    this.name = name;
  
    setUnlocalizedName(ColouredEggs.MODID + "." + name);
    setRegistryName(ColouredEggs.MODID, name);
  
    setCreativeTab(ColouredEggsTab.INSTANCE);
  }
  
  @Override
  public ItemBase getItem()
  {
    return this;
  }
  
  @Override
  public String getName()
  {
    return name;
  }
}
