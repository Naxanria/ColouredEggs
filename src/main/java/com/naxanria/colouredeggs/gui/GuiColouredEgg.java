package com.naxanria.colouredeggs.gui;

import com.naxanria.colouredeggs.ColouredEggs;
import com.naxanria.colouredeggs.container.ContainerEgg;
import com.naxanria.colouredeggs.network.PacketHelper;
import com.naxanria.colouredeggs.tile.TileEgg;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;

import java.io.IOException;
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
  
    buttons[i] = new GuiButton(i++, 10, 20, "/\\");
    buttons[i] = new GuiButton(i++, 10, 40, "\\/");
  
    buttons[i] = new GuiButton(i++, 10, 20, "/\\");
    buttons[i] = new GuiButton(i++, 10, 40, "\\/");
  
    buttons[i] = new GuiButton(i++, 10, 20, "/\\");
    buttons[i] = new GuiButton(i++, 10, 40, "\\/");
  
    //buttonList.addAll(Arrays.asList(buttons).subList(0, i));
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
  
    int spacing = (guiLeft - xSize) / -3 + 10;
    
    for (int i = 0; i < buttons.length; i += 2)
    {
      int xpos = guiLeft + border + 10 + spacing * i;
      int ypos = guiTop + border + 3;
      
//      buttons[i].visible = true;
      buttons[i].x = xpos;
      buttons[i].y = ypos;
      buttons[i].width = 25;
      buttons[i].drawButton(mc, mouseX, mouseY, partialTicks);
  
      ypos += 30;
  
      TileEgg egg = container.tile;
      String txt = ((i == 0) ? egg.getRed() : (i == 2) ? egg.getGreen() : egg.getBlue()) + "";
      int c = ((i == 0) ? 0xFFFF0000 : (i == 2) ? 0xFF00FF00 : 0xFF0000FF);
      
      drawString(fontRenderer, txt, xpos, ypos, c);
      
      ypos += 15;
      
//      buttons[i + 1].visible = true;
      buttons[i + 1].x = xpos;
      buttons[i + 1].y = ypos;
      buttons[i + 1].width = 25;
      buttons[i + 1].drawButton(mc, mouseX, mouseY, partialTicks);
    }

 
    
//    drawString(fontRenderer, container.tile.getRed() + "", guiLeft + border + 10 + 3, guiTop + border + 3 + 30, 0xFF0000);
//    drawString(fontRenderer, container.tile.getGreen() + "", guiLeft + border + 10 + 3, guiTop + border + 3 + 30, 0xFF0000);
//    drawString(fontRenderer, spacing + ":" + xSize + ":" + guiLeft, (guiLeft + border) / 2, guiTop + border + 3, 0xFF000000);
//
   
    
    drawRect(guiLeft + border, guiTop + ySize - border - 90, guiLeft + xSize - border, guiTop + ySize - border, container.tile.getColour());
  }
  
  @Override
  protected void actionPerformed(GuiButton button) throws IOException
  {
    PacketHelper.sendButtonPacket(container.tile, button.id);
  }
}
