package com.naxanria.colouredeggs.network;

import com.naxanria.colouredeggs.ColouredEggs;
import com.naxanria.colouredeggs.util.NBTHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class PacketHelper
{
  public static void sendButtonPacket(TileEntity tile, int ID)
  {
    NBTTagCompound compound = new NBTTagCompound();
    
    NBTHelper.writeBlockPos(tile.getPos(), compound);
    
    compound.setInteger("WorldID", tile.getWorld().provider.getDimension());
    compound.setInteger("PlayerID", Minecraft.getMinecraft().player.getEntityId());
    compound.setInteger("ButtonID", ID);
    
    PacketHandler.networkWrapper.sendToServer(new Packet.ClientToServer(compound, PacketHandler.HANDLERS.GUI_BUTTON_TO_TILE_HANDLER));
  }
  
  public static void updateAround(TileEntity tile, NBTTagCompound compound)
  {
    NBTTagCompound data = new NBTTagCompound();
    data.setTag("Data", compound);
  
    BlockPos pos = tile.getPos();
    
    NBTHelper.writeBlockPos(pos, data);
    
    PacketHandler.networkWrapper.sendToAllAround
    (
      new Packet.ServerToClient
      (
        data, PacketHandler.HANDLERS.TILE_ENTITY_HANDLER
      ),
      new NetworkRegistry.TargetPoint
      (
        tile.getWorld().provider.getDimension(),
        pos.getX(),
        pos.getY(),
        pos.getZ(),
        64
      )
    );
  }
}
