package com.naxanria.colouredeggs.util;

public class WorldUtil
{
  public static final int FLAG_STATE_UPDATE_BLOCK = 1;
  public static final int FLAG_STATE_SEND_TO_ALL_CLIENTS = 2;
  public static final int FLAG_STATE_PREVENT_RERENDER = 4;
  public static final int FLAG_STATE_RERENDER_MAINTHREAD = 8;
  public static final int FLAG_STATE_PREVENT_OBSERVERS = 16;
  
  public static final int FLAG_STATE_DEFAULT = FLAG_STATE_UPDATE_BLOCK | FLAG_STATE_SEND_TO_ALL_CLIENTS;
  public static final int FLAG_STATE_RERENDER = FLAG_STATE_DEFAULT | FLAG_STATE_RERENDER_MAINTHREAD;
}
