package com.naxanria.colouredeggs.gui;

import com.naxanria.colouredeggs.ColouredEggs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiButtonDown extends GuiButton
{
  private static final ResourceLocation DOWN = new ResourceLocation(ColouredEggs.MODID,"textures/gui/down.png");
  
  public GuiButtonDown(int buttonId, int x, int y)
  {
    super(buttonId, x, y, 24, 20, "");
  }
  
  @Override
  public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
  {
    super.drawButton(mc, mouseX, mouseY, partialTicks);
    
    mc.getTextureManager().bindTexture(DOWN);
    drawTexturedModalRect(x + 4, y + 1, 0, 0, 16, 16);
  }
}
