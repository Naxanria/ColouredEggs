package com.naxanria.colouredeggs;

import com.naxanria.colouredeggs.block.BlockEgg;
import com.naxanria.colouredeggs.item.ItemConfigurator;

public class Init
{
  public static class Items
  {
    public static final ItemConfigurator CONFIGURATOR = new ItemConfigurator();
  }
  
  public static class Blocks
  {
    public static final BlockEgg EGG = new BlockEgg();
  }
}
