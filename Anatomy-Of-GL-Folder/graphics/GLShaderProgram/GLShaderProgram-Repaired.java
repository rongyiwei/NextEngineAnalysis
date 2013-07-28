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
  //存放 shader 源文件的目录路径， private & null 初始值说明这个成员其实未被使用, 非核心数据结构
  private static final String shaderSrcDir = null;
    
  //
  static int f;

  // TODO，非核心数据结构 
  private String b;
    
  // TODO，非核心数据结构
  private String c;

  // Shader Program 对象的 ID, 核心数据结构
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
   * 注: createShaderObject(...) 在 GLShaderProgram.class.bytecode 中找不到对应的 bytecode ? 代码修复未严格按照 bytecode
   *
   * @param shaderType
   * @param shaderSrc
   * @return 若创建 Shader 对象成功，返回其 Shader ID, 否则返回 0
   */
  private static int createShaderObject(int shaderType, String shaderSrc)
  {
    int shaderID = GLES20.glCreateShader(shaderType);  //利用 GLES2 创建一个 Shader 对象
    String logStr; // 供 log 错误信息使用
    if (shaderID != 0) // 若 Shader 对象创建成功
    {
	GLES20.glShaderSource(shaderID, shaderSrc); //则通过 GLES2 上传 Shader 源码
	GLES20.glCompileShader(shaderID); //编译 Shader 源码
	int[] shaderStatus = new int[1]; // shaderStatus 用于存储 Shader 的状态信息
	GLES20.glGetShaderiv(shaderID, GL_COMPILE_STATUS, shaderStatus, 0); // 获取 Shader 的编译状态
	if (shaderStatus[0] == 0) // 若编译失败
	{ 
	    if (shaderType == GL_VERTEX_SHADER) //若 Shader 类型为 Vertex Shader
	    {
		logStr = "vertex shader";
	    } else { //若 Shader 类型为 Fragment Shader
		logStr = "fragment shader";  
	    }    
	    Log.e("GLERROR", "Could not compile " + logStr + ":"); //在日志中输出"无法成功编译顶点/片段着色器""
	    Log.e("GLERROR", GLES20.glGetShaderInfoLog(shaderID)); //在日志中 Dump 出 Shader 的当前状态信息
	    GLES20.glDeleteShader(shaderID); //若编译失败，则销毁 Shader 对象
	    GLError.clearGLError(); // 通过 GLES2 API － glGetError 清空状态机中的错误标识
	    shaderID = 0;
	}	
    }

    return shaderID;
  }

  /**  
   * createProgramObject(...) 通过 GLES2.0 API 创建 Program 对象，获得有效的 Program ID
   * 注: createProgramObject(...) 在 GLShaderProgram.class.bytecode 中找不到对应的 bytecode ? 代码修复未严格按照 bytecode
   *
   * @param vs_src Vertex Shader 的源码字符串
   * @param fs_src Fragment Shader 的源码字符串
   * @return 若创建 Program 对象成功，返回其 Program ID, 否则返回 0
   */
  private static int createProgramObject(String vs_src, String fs_src)
  {
    int programID = 0; //programID 初始为 0
    if ((vs_src == null) || (fs_src == null)) // 若 Vertex Shader 或 Fragment Shader 中任意一者的源代码字符串为空
    {
	return programID; // 则没有创建 Program 对象的必要，直接返回 0
    }

    GLError.clearGLError(); // 通过 GLES2 API － glGetError 清空状态机中的错误标识
    int vertexShaderID = createShaderObject(GL_VERTEX_SHADER, vs_src); // 创建与编译 Vertex Shader 对象
    if (vertexShaderID == 0) // 若创建与编译 Vertex Shader 对象不成功
    {
        return programID; // 则没有创建 Program 对象的必要，直接返回 0
    }
    int fragmentShaderID = createShaderObject(GL_FRAGMENT_SHADER, fs_src); // 创建与编译 Fragment Shader 对象
    if (fragmentShaderID == 0) // 若创建与编译 Fragment Shader 对象不成功
    {
	GLES20.glDeleteShader(vertexShaderID); // 则先前创建的 Vertex Shader 对象没有存在的必要，销毁它
	return programID; // 没有创建 Program 对象的必要，直接返回 0
    }
    programID = GLES20.glCreateProgram(); //利用 GLES2 创建一个 Program 对象
    if (programID != 0) // 若 Program 对象创建成功
    {
        GLES20.glAttachShader(programID, vertexShaderID); // Attach 上 Vertex Shader 对象
        GLError.checkGLError("glAttachShader"); // 检查 Attach Vertex Shader 是否失败，若失败，输出出错信息
        GLES20.glAttachShader(programID, fragmentShaderID); // Attach 上 Fragment Shader 对象
        GLError.checkGLError("glAttachShader"); // 检查 Attach Fragment Shader 是否失败，若失败，输出出错信息
        GLES20.glLinkProgram(programID); // 链接 Program 对象
        int[] programStatus = new int[1]; // programStatus 数组用于存储 Program 对象的状态信息
        GLES20.glGetProgramiv(programID, GL_LINK_STATUS, programStatus, 0); // 获取当前的链接信息
        if (GL_LINK_STATUS[0] != 1) // 若链接失败
        {
	  Log.e("GLERROR", "Could not link program: "); //在日志中输出"无法成功链接程序对象"
          Log.e("GLERROR", GLES20.glGetProgramInfoLog(i1)); //在日志中 Dump 出 Program 的当前状态信息 
          GLES20.glDeleteShader(vertexShaderID); // 销毁 Vertex Shader 对象
          GLES20.glDeleteShader(fragmentShaderID); // 销毁 Fragment Shader 对象
          GLES20.glDeleteProgram(programID); // 销毁 Program 对象
          GLError.clearGLError(); // 通过 GLES2 API － glGetError 清空状态机中的错误标识
	  programID = 0; // 返回值清零
        }
    }

    return programID;
  }
    
  /**
   * createProgramObject() 根据核心成员变量(this.vs_src & this.fs_src) 创建、编译和链接 GLShaderProgram
   * @reutrn 若成功，返回 true, 否则返回 false
   */
  private boolean createProgramObject()
  {
    boolean created = false; // 返回值初始为 false
    GLError.clearGLError(); // 通过 GLES2 API － glGetError 清空状态机中的错误标识
    this.shaderProgramID = GLShaderProgram.createProgramObject(this.vs_src, this.fs_src); //创建、编译和链接 Program 对象
    if ((this.shaderProgramID != 0) && (onProgramCreated())) //若成功，则调用 onProgramCreated() 回调函数
    {
	created = true; //返回值变为 true
    }
    return created; //created 作为返回值
  }

  /**  
   * TODO ...
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