package com.naxanria.colouredeggs.gui;

public class Button implements IButton
{
  public interface handler
  {
    void handle();
  }
  
  public final int ID;
  public final handler handler;
  
  public Button(int id, handler handler)
  {
    ID = id;
    this.handler = handler;
  }
  
  
  @Override
  public int getId()
  {
    return ID;
  }
  
  @Override
  public void onPressed()
  {
    handler.handle();
  }
}
