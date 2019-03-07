package com.naxanria.colouredeggs.tile;

import com.naxanria.colouredeggs.ColouredEggs;
import com.naxanria.colouredeggs.gui.Button;
import com.naxanria.colouredeggs.gui.IButtonResponder;
import com.naxanria.colouredeggs.model.ItemColourEgg;
import com.naxanria.colouredeggs.network.PacketHelper;
import com.naxanria.colouredeggs.util.WorldUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileEgg extends TileEntity implements IButtonResponder
{
  public static final int RED = 0;
  public static final int GREEN = 1;
  public static final int BLUE = 2;
  
  public List<Button> buttons = new ArrayList<>();
 
  
  private int colour;
  
  public TileEgg()
  {
    colour = ItemColourEgg.INSTANCE.colorMultiplier(null, 0);
    
    markDirty();
    sendUpdate();
    
    int id = 0;
  
    buttons.add(new Button(id++, () -> increase(10, RED)));
    buttons.add(new Button(id++, () -> increase(-10, RED)));
  
    buttons.add(new Button(id++, () -> increase(10, GREEN)));
    buttons.add(new Button(id++, () -> increase(-10, GREEN)));
  
    buttons.add(new Button(id++, () -> increase(10, BLUE)));
    buttons.add(new Button(id++, () -> increase(-10, BLUE)));
  }
  
  private void increase(int amount, int colour)
  {
    int r = getRed();
    int g = getGreen();
    int b = getBlue();
    
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
  
    sendUpdate();
    
    markDirty();
  }
  
  public final void sendUpdate()
  {
    if (world != null  && !world.isRemote)
    {
      NBTTagCompound compound = new NBTTagCompound(); //getUpdateTag();
      writeSyncableNBT(compound);
  
      IBlockState state = world.getBlockState(pos);
      world.markBlockRangeForRenderUpdate(pos.add(-1, -1, -1), pos.add(1, 1, 1));
      world.markChunkDirty(pos, this);
      
      world.markAndNotifyBlock(pos, world.getChunkFromBlockCoords(pos), state, state, WorldUtil.FLAG_STATE_UPDATE_BLOCK | WorldUtil.FLAG_STATE_SEND_TO_ALL_CLIENTS | WorldUtil.FLAG_STATE_RERENDER_MAINTHREAD);
      
      world.notifyNeighborsOfStateChange(pos.add(1, 0, 0), state.getBlock(), true);
      world.notifyBlockUpdate(pos, state, state, WorldUtil.FLAG_STATE_UPDATE_BLOCK | WorldUtil.FLAG_STATE_SEND_TO_ALL_CLIENTS | WorldUtil.FLAG_STATE_RERENDER_MAINTHREAD);
      world.scheduleBlockUpdate(pos, state.getBlock(), 100, 0);
      
      state.getBlock().onNeighborChange(world, pos, pos.add(0, 1, 0));
      
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
