package com.go.gl.graphics;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.util.Log;

/**
 * TODO 
 *
 */
public abstract class GLShaderProgram
  implements TextureListener
{
  // TODO 
  private static final String a = null;
    
  // TODO 
  static int f;

  // TODO，非核心数据结构 
  private String b;
    
  // TODO，非核心数据结构
  private String c;

  // TODO, 核心数据结构
  int shaderProgramID;
    
  // vertex shader 代码字符串，核心数据结构
  String vs_src;
    
  // fragment shader 代码字符串，核心数据结构
  String fs_src;

  /**  
   * GLShaderProgram 构造函数一
   */
  public GLShaderProgram()
  {
    // 所有核心数据成员变量均未被初始，取默认值(0 或 null) 
  }

  /**
   * GLShaderProgram 构造函数二
   * @param resources
   * @param paramString1
   * @param paramString2
   */
  public GLShaderProgram(Resources resources, String paramString1, String paramString2)
  {
    this(loadFromAssetsFile(resources, paramString1), loadFromAssetsFile(resources, paramString2));
  }

  /**  
   * GLShaderProgram 构造函数三
   * @param vs_src 传入 vertex shader 源码字符串
   * @param fs_src 传入 fragment shader 源码字符串
   *
   */
  public GLShaderProgram(String vs_src, String fs_src)
  {
    this.vs_src = vs_src; // 
    this.fs_src = fs_src; // 
    this.b = ("init program " + super.toString()); //
    this.c = ("bind program " + super.toString()); // 
  }

  /**
   * createShaderObject(...)  通过 GLES2.0 API 创建 Shader 对象，获得有效的 Shader ID
   * @param shaderType
   * @param shaderSrc
   * @return 若创建 Shader 对象成功，返回其 Shader ID, 否则返回 0
   */
  private static int createShaderObject(int shaderType, String shaderSrc)
  {
    int shaderID = GLES20.glCreateShader(shaderType);  //利用 GLES2 创建一个 Shader 对象
    String str; //
    if (shaderID != 0) 
    {
      GLES20.glShaderSource(shaderID, shaderSrc);
      GLES20.glCompileShader(shaderID);
      int[] arrayOfInt = new int[1];
      GLES20.glGetShaderiv(shaderID, GL_COMPILE_STATUS, arrayOfInt, 0);
      if (arrayOfInt[0] == 0)
        if (paramInt == GL_VERTEX_SHADER)
        {
          str = "vertex shader";
	} else {
	  str = "fragment shader";  
	}    

        Log.e("GLERROR", "Could not compile " + str + ":");
        Log.e("GLERROR", GLES20.glGetShaderInfoLog(shaderID));
        GLES20.glDeleteShader(j);
        GLError.clearGLError();
    }

    for (int k = 0; ; k = j)
    {
      return k;
      str = "fragment shader";
      break label49:
    }
  }

  /**  
   *
   *
   */
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
    
  /**
   *
   *
   */
  private boolean a()
  {
    GLError.clearGLError();
    this.g = a(this.h, this.i);
    if ((this.g != 0) && (onProgramCreated()));
    for (int j = 1; ; j = 0)
      return j;
  }

  /**  
   *
   *
   */
  static void b()
  {
    f = 0;
  }

  /**
   * loadFromAssetsFile(...) TODO
   * @param resources TODO 
   * @param TODO 
   * @return TODO 
   */
  public static String loadFromAssetsFile(Resources resources, String paramString)
  {
    InputStream local_2;  
    ByteArrayOutputStream local_3;
    int local_5;
    Byte[] local_6;
    String local_7;
    String local_8;
    InputStream local_14;

    if (GLShaderProgramm.a != null) {
	try {
	    local_2 = new FileInputStream(String.valueof(GLShaderProgram.a) + paramString);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	    throw new RuntimeException("GLShaderProgram: FileNotFoundException: open file: " + paramString);
	}
    } else {
	try {
	    local_14 = resources.getAssets().open(paramString);
	    local_2 = local_14;
	} catch (IOException e) {
	    e.printStackTrace();
	    throw new RuntimeException("GLShaderProgram: IOException: open assets: " + paramString);
	}
    }
    
    local_3 = new ByteArrayOutputStream();
    try {
	local_5 = local_2.read();
	while(local_5 != -1) {
	    local_3.write(local_5);
	    local_5 = local_2.read();
	}
    } catch (IOException e) {
	e.printStackThree();
        throw new RuntimeException("GLShaderProgram: IOException: baos.write(ch);");
    }
    
    local_6 = local_3.toByteArray();
    try {
	local_7 = new String(local_6, "UTF-8");
    } catch (UnsupportedEncodingException e) {
	e.printStackTrance();
	throw new RuntimeException("GLShaderProgram: UnsupportedEncodingException");
    }
    local_8 = local_7.replaceAll("\r\n", "\n");
    try {
	local_3.close();
    } catch(IOException e) {
	e.printStackTrace();
	throw new RuntimeException("GLShaderProgram: IOException: baos.close();");
    }
    try {
	local_2.close();
    } catch (IOException e) {
	e.printStackTrace();
	throw new RuntimeException("GLShaderProgram: IOException: in.close();");
    }
    
  }

  /**
   *
   *
   */
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

  /**
   *
   *
   */  
  protected int getAttribLocation(String paramString)
  {
    int j = GLES20.glGetAttribLocation(this.g, paramString);
    if (j == -1)
      throw new RuntimeException("cannot find " + paramString + ".");
    return j;
  }

  /**
   *
   *
   */    
  protected int getUniformLocation(String paramString)
  {
    int j = GLES20.glGetUniformLocation(this.g, paramString);
    if (j == -1)
      throw new RuntimeException("cannot find " + paramString + ".");
    return j;
  }

  /**
   *
   *
   */  
  protected abstract void onProgramBind();

  /**
   *
   *
   */  
  protected abstract boolean onProgramCreated();

  /**
   *
   *
   */  
  protected GLShaderProgram onRender(RenderContext paramRenderContext)
  {
    return this;
  }

  /**
   *
   *
   */
  public void onTextureInvalidate()
  {
    this.g = 0;
  }
}

/* Location:           E:\apk\NextDesktop\classes-dex2jar.jar
 * Qualified Name:     com.go.gl.graphics.GLShaderProgram
 * JD-Core Version:    0.5.4
 */