package com.naxanria.colouredeggs.gui;

import net.minecraft.entity.player.EntityPlayer;

public interface IButtonResponder
{
  void onButtonPressed(int id, EntityPlayer player);
  
  default boolean isButtonEnabled(int id, EntityPlayer player)
  {
    return true;
  }
}
