package com.naxanria.colouredeggs.item;

import com.naxanria.colouredeggs.ColouredEggs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemConfigurator extends Item
{
  public final String name;
  
  public ItemConfigurator()
  {
    name = "configurator";
    setUnlocalizedName(ColouredEggs.MODID + "." + name);
    setRegistryName(name);
    
    setCreativeTab(CreativeTabs.MISC);
  }
}
