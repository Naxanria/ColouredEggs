package com.naxanria.colouredeggs.gui;

import com.naxanria.colouredeggs.container.ContainerEgg;
import com.naxanria.colouredeggs.network.PacketHelper;
import com.naxanria.colouredeggs.tile.TileEgg;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;

import java.util.Arrays;

public class GuiColouredEgg extends GuiContainer
{
  private final ContainerEgg container;
  
  private GuiButton[] buttons = new GuiButton[6];
  
  public GuiColouredEgg(ContainerEgg container)
  {
    super(container);
    
    this.container = container;
    
    int i = 0;
  
    buttons[i] = new GuiButtonUp(i++, 10, 20);
    buttons[i] = new GuiButtonDown(i++, 10, 40);
  
    buttons[i] = new GuiButtonUp(i++, 10, 20);
    buttons[i] = new GuiButtonDown(i++, 10, 40);
  
    buttons[i] = new GuiButtonUp(i++, 10, 20);
    buttons[i] = new GuiButtonDown(i++, 10, 40);
  }
  
  @Override
  public void initGui()
  {
    super.initGui();
    
    buttonList.addAll(Arrays.asList(buttons));
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    int border = 2;
    drawRect(guiLeft, guiTop, guiLeft + xSize, guiTop + ySize, 0xFF454545);
    drawRect(guiLeft + border, guiTop + border, guiLeft + xSize - border, guiTop + ySize - border, 0xff888888);
  
    int spacing = (xSize) / 3 + 10;
  
    TileEgg egg = container.tile;
    
    for (int i = 0; i < buttons.length; i += 2)
    {
      int xpos = guiLeft + border + 10 + spacing * (i / 2);
      int ypos = guiTop + border + 3;

      buttons[i].x = xpos;
      buttons[i].y = ypos;
      buttons[i].drawButton(mc, mouseX, mouseY, partialTicks);
  
      ypos += 30;
      
      String txt = ((i == 0) ? egg.getRed() : (i == 2) ? egg.getGreen() : egg.getBlue()) + "";
      int c = ((i == 0) ? 0xFFFF0000 : (i == 2) ? 0xFF00FF00 : 0xFF0000FF);
      
      drawString(fontRenderer, txt, xpos, ypos, c);
      
      ypos += 15;

      buttons[i + 1].x = xpos;
      buttons[i + 1].y = ypos;
      buttons[i + 1].drawButton(mc, mouseX, mouseY, partialTicks);
    }

    drawRect(guiLeft + border, guiTop + ySize - border - 90, guiLeft + xSize - border, guiTop + ySize - border - 50, container.tile.getColour());
  
    drawString(fontRenderer, "CTRL: +/- 1, ", guiLeft + border + 2, guiTop + ySize - border - 48, 0xFFFFFFFF);
    drawString(fontRenderer, "Shift: +/- 50", guiLeft + border + 2, guiTop + ySize - border - 38, 0xFFFFFFFF);
    drawString(fontRenderer, "Alt: set to min/max", guiLeft + border + 2, guiTop + ySize - border - 28, 0xFFFFFFFF);
  }
  
  @Override
  protected void actionPerformed(GuiButton button)
  {
    PacketHelper.sendButtonPacket(container.tile, button.id);
  }
}
