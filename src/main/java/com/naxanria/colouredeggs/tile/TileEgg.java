package com.naxanria.colouredeggs.tile;

import com.naxanria.colouredeggs.gui.Button;
import com.naxanria.colouredeggs.gui.IButtonResponder;
import com.naxanria.colouredeggs.model.ItemColourEgg;
import com.naxanria.colouredeggs.network.PacketHelper;
import com.naxanria.colouredeggs.util.ColourHelper;
import com.naxanria.colouredeggs.util.WorldUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileEgg extends TileEntityBase implements IButtonResponder
{
  public List<Button> buttons = new ArrayList<>();
 
  private int colour;

  public TileEgg()
  {
    colour = ItemColourEgg.INSTANCE.colorMultiplier(null, 0);
    
    int id = 0;
    buttons.add(new Button(id++, ()-> increase(getAmount(), ColourHelper.ColourChannel.RED)));
    buttons.add(new Button(id++, ()-> increase(-getAmount(), ColourHelper.ColourChannel.RED)));
    buttons.add(new Button(id++, ()-> increase(getAmount(), ColourHelper.ColourChannel.GREEN)));
    buttons.add(new Button(id++, ()-> increase(-getAmount(), ColourHelper.ColourChannel.GREEN)));
    buttons.add(new Button(id++, ()-> increase(getAmount(), ColourHelper.ColourChannel.BLUE)));
    buttons.add(new Button(id++, ()-> increase(-getAmount(), ColourHelper.ColourChannel.BLUE)));
  }
  
  public int getAmount()
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
  
  public void increase(int amount, ColourHelper.ColourChannel channel)
  {
//    ColouredEggs.logger.info("changing " + channel.name() + " by " + amount);
    
    int r = ColourHelper.getRed(colour);
    int g = ColourHelper.getGreen(colour);
    int b = ColourHelper.getBlue(colour);
    
    int oldCol = colour;
    
    switch (channel)
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
    
    this.colour = ColourHelper.getColour(r, g, b);
    
    if (!world.isRemote && colour != oldCol)
    {
      sendUpdate();
      markDirty();
    }
  }
  
  public final void sendUpdate()
  {
    if (world != null  && !world.isRemote)
    {
      NBTTagCompound compound = new NBTTagCompound();
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
    return ColourHelper.getRed(colour);
  }
  
  public int getGreen()
  {
    return ColourHelper.getGreen(colour);
  }
  
  public int getBlue()
  {
    return ColourHelper.getBlue(colour);
  }
  
  public int getColour()
  {
    return colour;
  }
  
  public void setColour(int colour)
  {
    int prev = this.colour;
    this.colour = colour;
    
    if (this.colour != prev)
    {
      markDirty();
      sendUpdate();
    }
  }
}
