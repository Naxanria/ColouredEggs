package com.naxanria.colouredeggs.tile;

import com.naxanria.colouredeggs.ColouredEggs;
import com.naxanria.colouredeggs.gui.Button;
import com.naxanria.colouredeggs.gui.IButtonResponder;
import com.naxanria.colouredeggs.model.ItemColourEgg;
import com.naxanria.colouredeggs.network.PacketHelper;
import com.naxanria.colouredeggs.util.WorldUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.Chunk;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileEgg extends TileEntityBase implements IButtonResponder
{
  public static final int RED = 0;
  public static final int GREEN = 1;
  public static final int BLUE = 2;
  
  public List<Button> buttons = new ArrayList<>();
 
  
  private int colour;

  public TileEgg()
  {
    createButtons();
    init(0);
  }
  
  private void init(int layers)
  {
    colour = ItemColourEgg.INSTANCE.colorMultiplier(null, 0);
  }
  
  private void createButtons()
  {
    int id = 0;
  
    buttons.add(new Button(id++, () -> increase(getAmount(), RED)));
    buttons.add(new Button(id++, () -> increase(-getAmount(), RED)));
  
    buttons.add(new Button(id++, () -> increase(getAmount(), GREEN)));
    buttons.add(new Button(id++, () -> increase(-getAmount(), GREEN)));
  
    buttons.add(new Button(id++, () -> increase(getAmount(), BLUE)));
    buttons.add(new Button(id++, () -> increase(-getAmount(), BLUE)));
  }
  
  private int getAmount()
  {
    if (GuiScreen.isShiftKeyDown())
    {
      return 50;
    }
    
    if (GuiScreen.isCtrlKeyDown())
    {
      return 1;
    }
    
    if (GuiScreen.isAltKeyDown())
    {
      return 255;
    }
    
    return 10;
  }
  
  private void increase(int amount, int colour)
  {
    int r = getRed();
    int g = getGreen();
    int b = getBlue();
    
    int currCol = this.colour;
    
    switch (colour)
    {
      case RED:
        r = MathHelper.clamp(r + amount, 0, 255);
        break;
  
      case GREEN:
        g = MathHelper.clamp(g + amount, 0, 255);
        break;
  
      case BLUE:
        b = MathHelper.clamp(b + amount, 0, 255);
        break;
    }
    
    this.colour = getColour(r, g, b);
  
    if (!world.isRemote && currCol != colour)
    {
      sendUpdate();
  
      markDirty();
  
  
      Chunk chunk = world.getChunkFromBlockCoords(pos);
  
      chunk.setModified(true);
      chunk.markDirty();
    }
  
    
  }
  
  public final void sendUpdate()
  {
    if (world != null  && !world.isRemote)
    {
      NBTTagCompound compound = new NBTTagCompound(); //getUpdateTag();
      writeSyncableNBT(compound);
  
      IBlockState state = world.getBlockState(pos);

      world.setBlockState(pos, state, WorldUtil.FLAG_STATE_RERENDER);
      world.notifyBlockUpdate(pos, state, state, WorldUtil.FLAG_STATE_RERENDER);
      
      PacketHelper.updateAround(this, compound);
    }
    
  }
  
  @Override
  public void readFromNBT(NBTTagCompound compound)
  {
    super.readFromNBT(compound);
    
    readSyncableNBT(compound);
  }
  
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound)
  {
    super.writeToNBT(compound);
    
    writeSyncableNBT(compound);
    
    return compound;
  }
  
  @Nullable
  @Override
  public SPacketUpdateTileEntity getUpdatePacket()
  {
    NBTTagCompound compound = new NBTTagCompound();
    writeSyncableNBT(compound);
    
    return new SPacketUpdateTileEntity(pos, -1, compound);
  }
  
  @Override
  public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
  {
    readSyncableNBT(pkt.getNbtCompound());
  }
  
  @Override
  public NBTTagCompound getUpdateTag()
  {
    NBTTagCompound compound = super.getUpdateTag();
    writeSyncableNBT(compound);
    
    
    
    return compound;
  }
  
  public void writeSyncableNBT(NBTTagCompound compound)
  {
    compound.setInteger("Colour", colour);
  }
  
  public void readSyncableNBT(NBTTagCompound compound)
  {
    int oldCol = colour;
    colour = compound.getInteger("Colour");
    
    if (colour != oldCol)
    {
      if (world != null)
      {
        world.markChunkDirty(pos, this);
        
        if (world.isRemote)
        {
//          ColouredEggs.logger.info("== RERENDER ==");
          Minecraft mc = Minecraft.getMinecraft();
//          mc.skipRenderWorld = false;
//          mc.entityRenderer.updateRenderer();
//          mc.entityRenderer.updateCameraAndRender(mc.getRenderPartialTicks(), System.nanoTime());
          
          // Fixme: This is probably not the correct way! But it works.
          mc.renderGlobal.loadRenderers();
          
//          mc.renderGlobal.updateChunks(System.nanoTime() + 1);
        }
      }
    }
  }
  
  @Override
  public void onButtonPressed(int id, EntityPlayer player)
  {
    if (id < 0 || id >= buttons.size())
    {
      throw new IllegalArgumentException("No button with id " + id);
    }
    buttons.get(id).onPressed();
  }
  
  @Override
  public boolean isButtonEnabled(int id, EntityPlayer player)
  {
    return true;
  }
  
  public int getRed()
  {
    return (colour >> 16) & 0xFF;
  }
  
  public int getGreen()
  {
    return (colour >> 8) & 0xFF;
  }
  
  public int getBlue()
  {
    return colour & 0xFF;
  }
  
  public int getColour()
  {
    return colour;
  }
  
  public static int getColour(int r, int g, int b)
  {
    return (255 << 24) | (r << 16) | (g << 8) | b;
  }
  
  public void setColour(int colour)
  {
    this.colour = colour;
  }
}
