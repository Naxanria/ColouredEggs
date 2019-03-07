package com.naxanria.colouredeggs.gui;

import com.naxanria.colouredeggs.ColouredEggs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiButtonUp extends GuiButton
{
  private static final ResourceLocation UP = new ResourceLocation(ColouredEggs.MODID,"textures/gui/up.png");
  
  public GuiButtonUp(int buttonId, int x, int y)
  {
    super(buttonId, x, y, 24, 20, "");
  }
  
  @Override
  public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
  {
    super.drawButton(mc, mouseX, mouseY, partialTicks);
    
    mc.getTextureManager().bindTexture(UP);
  
    drawTexturedModalRect(x + 4, y + 2, 0, 0, 16, 16);
  }
}
