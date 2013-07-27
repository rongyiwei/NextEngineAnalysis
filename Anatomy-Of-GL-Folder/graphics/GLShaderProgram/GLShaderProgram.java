package com.go.gl.graphics;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.util.Log;

public abstract class GLShaderProgram
  implements TextureListener
{
  private static final String a = null;
  static int f;
  private String b;
  private String c;
  int g;
  String h;
  String i;

  public GLShaderProgram()
  {
  }

  public GLShaderProgram(Resources paramResources, String paramString1, String paramString2)
  {
    this(loadFromAssetsFile(paramResources, paramString1), loadFromAssetsFile(paramResources, paramString2));
  }

  public GLShaderProgram(String paramString1, String paramString2)
  {
    this.h = paramString1;
    this.i = paramString2;
    this.b = ("init program " + super.toString());
    this.c = ("bind program " + super.toString());
  }

  private static int a(int paramInt, String paramString)
  {
    int j = GLES20.glCreateShader(paramInt);
    String str;
    if (j != 0)
    {
      GLES20.glShaderSource(j, paramString);
      GLES20.glCompileShader(j);
      int[] arrayOfInt = new int[1];
      GLES20.glGetShaderiv(j, 35713, arrayOfInt, 0);
      if (arrayOfInt[0] == 0)
        if (paramInt == 35633)
        {
          str = "vertex shader";
          label49: Log.e("GLERROR", "Could not compile " + str + ":");
          Log.e("GLERROR", GLES20.glGetShaderInfoLog(j));
          GLES20.glDeleteShader(j);
          GLError.clearGLError();
        }
    }
    for (int k = 0; ; k = j)
    {
      return k;
      str = "fragment shader";
      break label49:
    }
  }

  private static int a(String paramString1, String paramString2)
  {
    int j = 0;
    if ((paramString1 == null) || (paramString2 == null));
    while (true)
    {
      return j;
      GLError.clearGLError();
      int k = a(35633, paramString1);
      if (k == 0)
        continue;
      int l = a(35632, paramString2);
      if (l == 0)
        GLES20.glDeleteShader(k);
      int i1 = GLES20.glCreateProgram();
      if (i1 != 0)
      {
        GLES20.glAttachShader(i1, k);
        GLError.checkGLError("glAttachShader");
        GLES20.glAttachShader(i1, l);
        GLError.checkGLError("glAttachShader");
        GLES20.glLinkProgram(i1);
        int[] arrayOfInt = new int[1];
        GLES20.glGetProgramiv(i1, 35714, arrayOfInt, 0);
        if (arrayOfInt[0] != 1)
        {
          Log.e("GLERROR", "Could not link program: ");
          Log.e("GLERROR", GLES20.glGetProgramInfoLog(i1));
          GLES20.glDeleteShader(k);
          GLES20.glDeleteShader(l);
          GLES20.glDeleteProgram(i1);
          GLError.clearGLError();
        }
      }
      j = i1;
    }
  }

  private boolean a()
  {
    GLError.clearGLError();
    this.g = a(this.h, this.i);
    if ((this.g != 0) && (onProgramCreated()));
    for (int j = 1; ; j = 0)
      return j;
  }

  static void b()
  {
    f = 0;
  }

  // ERROR //
  public static String loadFromAssetsFile(Resources paramResources, String paramString)
  {
    // Byte code:
    //   0: getstatic 19	com/go/gl/graphics/GLShaderProgram:a	Ljava/lang/String;
    //   3: ifnonnull +109 -> 112
    //   6: aload_0
    //   7: invokevirtual 158	android/content/res/Resources:getAssets	()Landroid/content/res/AssetManager;
    //   10: aload_1
    //   11: invokevirtual 164	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   14: astore 14
    //   16: aload 14
    //   18: astore_2
    //   19: new 166	java/io/ByteArrayOutputStream
    //   22: dup
    //   23: invokespecial 167	java/io/ByteArrayOutputStream:<init>	()V
    //   26: astore_3
    //   27: aload_2
    //   28: invokevirtual 172	java/io/InputStream:read	()I
    //   31: istore 5
    //   33: iload 5
    //   35: bipush 255
    //   37: if_icmpne +137 -> 174
    //   40: aload_3
    //   41: invokevirtual 176	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   44: astore 6
    //   46: new 178	java/lang/String
    //   49: dup
    //   50: aload 6
    //   52: ldc 180
    //   54: invokespecial 183	java/lang/String:<init>	([BLjava/lang/String;)V
    //   57: astore 7
    //   59: aload 7
    //   61: ldc 185
    //   63: ldc 187
    //   65: invokevirtual 191	java/lang/String:replaceAll	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   68: astore 8
    //   70: aload_3
    //   71: invokevirtual 194	java/io/ByteArrayOutputStream:close	()V
    //   74: aload_2
    //   75: invokevirtual 195	java/io/InputStream:close	()V
    //   78: aload 8
    //   80: areturn
    //   81: astore 13
    //   83: aload 13
    //   85: invokevirtual 198	java/io/IOException:printStackTrace	()V
    //   88: new 200	java/lang/RuntimeException
    //   91: dup
    //   92: new 36	java/lang/StringBuilder
    //   95: dup
    //   96: ldc 202
    //   98: invokespecial 41	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   101: aload_1
    //   102: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: invokevirtual 50	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   108: invokespecial 203	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   111: athrow
    //   112: new 205	java/io/FileInputStream
    //   115: dup
    //   116: new 36	java/lang/StringBuilder
    //   119: dup
    //   120: getstatic 19	com/go/gl/graphics/GLShaderProgram:a	Ljava/lang/String;
    //   123: invokestatic 209	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   126: invokespecial 41	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   129: aload_1
    //   130: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   133: invokevirtual 50	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   136: invokespecial 210	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   139: astore_2
    //   140: goto -121 -> 19
    //   143: astore 12
    //   145: aload 12
    //   147: invokevirtual 211	java/io/FileNotFoundException:printStackTrace	()V
    //   150: new 200	java/lang/RuntimeException
    //   153: dup
    //   154: new 36	java/lang/StringBuilder
    //   157: dup
    //   158: ldc 213
    //   160: invokespecial 41	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   163: aload_1
    //   164: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: invokevirtual 50	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   170: invokespecial 203	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   173: athrow
    //   174: aload_3
    //   175: iload 5
    //   177: invokevirtual 216	java/io/ByteArrayOutputStream:write	(I)V
    //   180: goto -153 -> 27
    //   183: astore 4
    //   185: aload 4
    //   187: invokevirtual 198	java/io/IOException:printStackTrace	()V
    //   190: new 200	java/lang/RuntimeException
    //   193: dup
    //   194: ldc 218
    //   196: invokespecial 203	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   199: athrow
    //   200: astore 11
    //   202: aload 11
    //   204: invokevirtual 219	java/io/UnsupportedEncodingException:printStackTrace	()V
    //   207: new 200	java/lang/RuntimeException
    //   210: dup
    //   211: ldc 221
    //   213: invokespecial 203	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   216: athrow
    //   217: astore 9
    //   219: aload 9
    //   221: invokevirtual 198	java/io/IOException:printStackTrace	()V
    //   224: new 200	java/lang/RuntimeException
    //   227: dup
    //   228: ldc 223
    //   230: invokespecial 203	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   233: athrow
    //   234: astore 10
    //   236: aload 10
    //   238: invokevirtual 198	java/io/IOException:printStackTrace	()V
    //   241: new 200	java/lang/RuntimeException
    //   244: dup
    //   245: ldc 225
    //   247: invokespecial 203	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   250: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   6	16	81	java/io/IOException
    //   112	140	143	java/io/FileNotFoundException
    //   27	33	183	java/io/IOException
    //   174	180	183	java/io/IOException
    //   46	59	200	java/io/UnsupportedEncodingException
    //   70	74	217	java/io/IOException
    //   74	78	234	java/io/IOException
  }

  public boolean bind()
  {
    int j = 0;
    if (this.g == 0)
      if (a());
    while (true)
    {
      return j;
      if (GLError.checkGLError(this.b))
        continue;
      if (f != this.g)
      {
        f = this.g;
        GLES20.glUseProgram(this.g);
        onProgramBind();
        if (GLError.checkGLError(this.c))
          Log.w("GLERROR", "mProgram=" + this.g);
      }
      j = 1;
    }
  }

  protected int getAttribLocation(String paramString)
  {
    int j = GLES20.glGetAttribLocation(this.g, paramString);
    if (j == -1)
      throw new RuntimeException("cannot find " + paramString + ".");
    return j;
  }

  protected int getUniformLocation(String paramString)
  {
    int j = GLES20.glGetUniformLocation(this.g, paramString);
    if (j == -1)
      throw new RuntimeException("cannot find " + paramString + ".");
    return j;
  }

  protected abstract void onProgramBind();

  protected abstract boolean onProgramCreated();

  protected GLShaderProgram onRender(RenderContext paramRenderContext)
  {
    return this;
  }

  public void onTextureInvalidate()
  {
    this.g = 0;
  }
}

/* Location:           E:\apk\NextDesktop\classes-dex2jar.jar
 * Qualified Name:     com.go.gl.graphics.GLShaderProgram
 * JD-Core Version:    0.5.4
 */