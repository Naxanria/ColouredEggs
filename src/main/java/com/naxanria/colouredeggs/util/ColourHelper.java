package com.naxanria.colouredeggs.util;

public class ColourHelper
{
  public enum ColourChannel
  {
    ALPHA,
    RED,
    GREEN,
    BLUE
  }
  
  public static int getColour(int r, int g, int b)
  {
    return getColour(255, r, g, b);
  }
  
  public static int getColour(int a, int r, int g, int b)
  {
    return (a << 24) | (r << 16) | (g << 8) | b;
  }
  
  public static int get(int colour, ColourChannel channel)
  {
    switch (channel)
    {
      case ALPHA:
        return getAlpha(colour);

      case RED:
        return getRed(colour);
        
      case GREEN:
        return getGreen(colour);
        
      case BLUE:
        return getBlue(colour);
    }
    
    return 0;
  }
  
  public static int getAlpha(int colour)
  {
    return (colour >> 24) & 0xFF;
  }
  
  public static int getRed(int colour)
  {
    return (colour >> 16) & 0xFF;
  }
  
  public static int getGreen(int colour)
  {
    return (colour >> 8) & 0xFF;
  }
  
  public static int getBlue(int colour)
  {
    return colour & 0xFF;
  }
}
