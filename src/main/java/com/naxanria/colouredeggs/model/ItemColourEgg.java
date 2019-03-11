package com.naxanria.colouredeggs.model;

import com.naxanria.colouredeggs.tile.TileEgg;
import com.naxanria.colouredeggs.util.ColourHelper;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class ItemColourEgg implements IItemColor
{
  public static final ItemColourEgg INSTANCE = new ItemColourEgg();
  
  private Random random = new Random();
  private int r;
  private int g;
  private int b;
  
  private int maxDistance = 25;
  
  private long last = 0;
  
  public ItemColourEgg()
  {
    r = random.nextInt(255);
    g = random.nextInt(255);
    b = random.nextInt(255);
    
    last = System.currentTimeMillis();
  }
  
  @Override
  public int colorMultiplier(ItemStack stack, int tintIndex)
  {
    long now = System.currentTimeMillis();
    maxDistance = 15;
    if (now - last >= 40)
    {
      last = now;
  
  
      int next = random.nextInt(255);
      int choice = random.nextInt(3);
      switch (choice)
      {
        case 0:
        default:
          r = MathHelper.clamp(next, Math.max(r - maxDistance, 0), Math.min(r + maxDistance, 255));
          break;
    
        case 1:
          g = MathHelper.clamp(next, Math.max(g - maxDistance, 0), Math.min(g + maxDistance, 255));
          break;
    
        case 2:
          b = MathHelper.clamp(next, Math.max(b - maxDistance, 0), Math.min(b + maxDistance, 255));
          break;
      }
    }
    
    return ColourHelper.getColour(r, g, b);
  }
}
