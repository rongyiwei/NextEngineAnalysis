package com.go.gl.util;

import java.util.Arrays;

public class FastQueue
{
  private transient Object[] a;
  private int b;
  private int c;
  private int d;
  private int e;

  public FastQueue(int paramInt)
  {
    if (paramInt < 0)
      throw new IllegalArgumentException();
    int i = 16;
    while (true)
    {
      if (i >= paramInt)
      {
        this.a = new Object[i];
        this.b = this.a.length;
        this.c = (-1 + this.b);
        return;
      }
      i <<= 1;
    }
  }

  public void cleanup()
  {
    Arrays.fill(this.a, null);
    this.e = 0;
    this.d = 0;
  }

  protected void handleOverflow(Object paramObject)
  {
  }

  public final boolean isEmpty()
  {
    if (this.d == this.e);
    for (int i = 1; ; i = 0)
      return i;
  }

  public final Object popFront()
  {
    Object localObject1 = null;
    int i = this.e;
    int j = this.d;
    if (i == j);
    while (true)
    {
      return localObject1;
      int k = j + 1 & this.c;
      Object localObject2 = this.a[j];
      this.a[j] = null;
      this.d = k;
      localObject1 = localObject2;
    }
  }

  public final void process(FastQueue.Processor paramProcessor)
  {
    int i = this.e;
    int j = this.d;
    if (i == j)
      return;
    int l;
    for (int k = j + 1 & this.c; ; k = l)
    {
      if (j != i);
      Object localObject = this.a[j];
      this.a[j] = null;
      this.d = k;
      l = k + 1 & this.c;
      paramProcessor.process(localObject);
      j = k;
    }
  }

  public final boolean pushBack(Object paramObject)
  {
    int i = this.d;
    int j = this.e;
    int k = j + 1 & this.c;
    if (k == i)
      handleOverflow(paramObject);
    for (int l = 0; ; l = 1)
    {
      return l;
      this.a[j] = paramObject;
      this.e = k;
    }
  }

  public boolean remove(Object paramObject)
  {
    int i = 0;
    int j = this.e;
    int k = this.d;
    if (j == k)
      return i;
    int i1;
    for (int l = k + 1 & this.c; ; l = i1)
    {
      if (k != j);
      if (this.a[k] == paramObject)
      {
        this.a[k] = null;
        i = 1;
      }
      i1 = l + 1 & this.c;
      k = l;
    }
  }
}

/* Location:           E:\apk\NextDesktop\classes-dex2jar.jar
 * Qualified Name:     com.go.gl.util.FastQueue
 * JD-Core Version:    0.5.4
 */