package com.naxanria.colouredeggs.gui;

import com.naxanria.colouredeggs.ColouredEggs;
import com.naxanria.colouredeggs.container.ContainerEgg;
import com.naxanria.colouredeggs.network.PacketHelper;
import com.naxanria.colouredeggs.tile.TileEgg;
import com.naxanria.colouredeggs.util.ColourHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;

import com.naxanria.colouredeggs.util.ColourHelper.ColourChannel;
//import static com.naxanria.colouredeggs.util.ColourHelper.getColour;

public class GuiColouredEgg extends GuiContainer
{
  private final ContainerEgg container;
  
  private GuiButton[] buttons = new GuiButton[6];
//  private GuiButton resetButton, confirmButton;
 
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
    
//    resetButton = new GuiButton(i++, 10, 10, "Reset");
//    confirmButton = new GuiButton(i++, 100, 10, "Confirm");
    
//    startColour = colour = container.tile.getColour();
//    updateRGB();
  }
  
  
  @Override
  public void initGui()
  {
    super.initGui();
    
    buttonList.addAll(Arrays.asList(buttons));
//    buttonList.add(resetButton);
//    buttonList.add(confirmButton);
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
  
    int rx = guiLeft + border;
    int ry = guiTop + ySize - border - 90;
    int rh = 40;
    int rw = xSize - border * 2;

    drawRect(rx, ry, rx + rw, ry + rh, container.tile.getColour());
    
//    int buttonY = guiTop + ySize - border - confirmButton.height;
//
//    confirmButton.width = 60;
//    confirmButton.x = guiLeft + border + 10;
//    confirmButton.y = buttonY;
//
//    confirmButton.drawButton(mc, mouseX, mouseY, partialTicks);
//
//    resetButton.width = 60;
//    resetButton.x = guiLeft + xSize - border - resetButton.width - 10;
//    resetButton.y = buttonY;
//
//    resetButton.drawButton(mc, mouseX, mouseY, partialTicks);
  
    drawString(fontRenderer, "CTRL: +/- 1, ", guiLeft + border + 2, guiTop + ySize - border - 48, 0xFFFFFFFF);
    drawString(fontRenderer, "Shift: +/- 50", guiLeft + border + 2, guiTop + ySize - border - 38, 0xFFFFFFFF);
    drawString(fontRenderer, "Alt: set to min/max", guiLeft + border + 2, guiTop + ySize - border - 28, 0xFFFFFFFF);
  }
  
  @Override
  protected void actionPerformed(GuiButton button)
  {
//    ColouredEggs.logger.info("Button pressed: " + button + " " + button.id);
    
    PacketHelper.sendButtonPacket(container.tile, button.id);
  }
}
