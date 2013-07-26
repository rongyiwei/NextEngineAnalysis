package com.go.gl.util;

import java.util.Arrays;

/**
 * FastQueue: 一个快速队列的实现
 *
 * 使用到 FastQueue 的核心类:
 *   - BitmapRecycler
 *   - Texture
 *   - TextureManager
 *   - TextureRecycler
 *   - GLContentView
 *   - GLViewWrapper
 */
public class FastQueue
{
  // 核心数据结构， Object[] 数组作为实现一个循环队列(头尾相连)的基础
  // 关键字 transient 确保 FastQueue 实例在被序列化(Serialization)时，不会序列化成员变量 queue
  private transient Object[] queue;

  // 队列的长度
  private int length;

  // 模数 - 用于求索引值余数，以保正索引值不超过数组的长度，以实现循环队列
  private int modulus;

  // 核心数据结构，队列头
  private int head;

  // 核心数据结构，队列尾
  private int tail;

  /**
   * FastQueue 构造函数
   *
   * @param lengthHint 构造函数调用者所希望的队列长度，但未必会是最终的队列长度
   */
  public FastQueue(int lengthHint)
  {
    if (lengthHint < 0)  //若所期望的队列长度为负数
      throw new IllegalArgumentException();  //则直接抛出异常
    int validLength = 16; //局部变量 validLength 为有效的候选队列长度，必须是 2^N (N >= 4)
    while (true)
    {
      if (validLength >= lengthHint) //若候选队列长度大于或等于所期望的队列长度，则正式作为队列长度
      {
        this.queue = new Object[validLength]; //初始数组队列
        this.length = this.queue.length; //初始 length 成员
        this.modulus = (-1 + this.length); // 模数，用于求索引值的余数，保证索引取值范围不超过数组长度，以实现循环队列
        return;
      }
      validLenght <<= 1; //若候选队列长度仍小于所期望的队列长度，则长度翻倍
    }
  }

  /**
   * cleanup() 清空整个队列
   */
  public void cleanup()
  {
    Arrays.fill(this.queue, null); //取消对队列中所有对象的引用
    this.tail = 0; //尾部索引指向数组头部
    this.head = 0; //头部索引也指向数组头部，注意队列头部不等同于数组头部 
  }

  /**
   * handleOverflow() 当队列发生溢出时(即队列满的情况下继续添加对象)，调用此函数
   *
   * 实现为空，protected 表示留给子类去 override
   */
  protected void handleOverflow(Object paramObject)
  {
      //空实现
  }

  /**
   * isEmpty() 判断队列是否为空
   */
  public final boolean isEmpty()
  {
    if (this.head == this.tail) { //若队列的头部索引与尾部索引指向同一个位置
	return true; //则说明队列为空
    }
    return false; //反之，则队列不为空
  }

  /**
   * popFront() 弹出位于队列头部的对象
   *
   * @return 返回被弹出的对象，失败则返回 null
   */
  public final Object popFront()
  {
    Object poppedObject = null; //局部变量 poppedObject 用于存储将被弹出的对象，初始为 null
    if (this.tail == this.head) { //若队列的头部索引与尾部索引指向同一个位置
	return poppedObject; // 则说明是空队列，无对象可被 pop，返回 null 
    }

    int nextIndex = this.head + 1 & this.modulus; //计算新的头部索引值， 存入局部变量 nextIndex
    Object frontObject = this.queue[this.head]; //获取位于队列头部的对象
    this.queue[this.head] = null; // 取消对即将被 pop 对象的引用
    this.head = nextIndex; // 更新队列的头部索引取值
    poppedObject = frontObject; //此时，队列头部的对象被成功弹出
    return poppedObject; // 返回被 pop 对象
  }

  /**
   * process(...) 函数对队列中的每一个对象进行处理
   *
   * @param 参数为一个 FastQueue.Processor 接口实现，执行了具体的对象处理逻辑
   */
  public final void process(FastQueue.Processor processor)
  {
    if (this.tail == this.head) //若队列为空
      return; //则无对象需处理，直接返回

    int l;
    //for 循环从队列头部遍历至尾部，直到头尾索引指向相同位置，此时队列为空
    for (int nextIndex = this.head + 1 & this.modulus; this.head != this.tail; nextIndex = l) 
    {
      Object objectToProcess = this.queue[this.head]; //获取位于队列头部的对象，亦即将被处理的对象
      this.queue[this.head] = null; //取消对这个对象的引用
      this.head = nextIndex; //更新队列的头部索引，指向下一个位置，即队列在不断缩小
      l = nextIndex + 1 & this.modulus; //计算下一轮循环时 nextIndex 的取值，存入局部变量 l
      processor.process(objectToProcess); //处理当前位于头部位置的对象
    }
  }

  /**
   * pushBack(...)函数向队列尾部压入一个新的对象
   *
   * @param objectToPush 为即将被 push 进队列尾部的对象
   * @return boolean 返回值提示 push 操作是否成功
   */
  public final boolean pushBack(Object objectToPush)
  {
    int nextIndex = this.tail + 1 & this.modulus; // 计算队列尾部的下一个位置索引，存入局部变量 nextIndex
    boolean pushed = false; 
    if (nextIndex == this.head) { // 若 nextIndex 与队列的头部索引相同
      handleOverflow(objectToPush); // 则说明队列已满，发生了溢出情况
      return pushed; // 返回 false, push 操作无法进行
    }
    this.queue[this.tail] = objectToPush; //若队列未满，则将 objectToPush 对象压入队列尾部
    this.tail = newIndex; //更新队列尾部的索引值
    pushed = true;
    return pushed; // 返回 true, push 操作成功
  }

  /**
   * remove(...) 函数按照从队列头部向队列尾部依次遍历搜寻 objectToRemove 对象，若找到，则取消对这个对象的引用
   * - 注意1. 只是取消对 objectToRemove 的引用，队列的长度保持不变
   * - 注意2. 移除第一个 objectToRemove 对象后立即返回，不能保证队列中已不存在第二个 objectToRemove 对象
   *
   * @param objectToRemove 要从队列中移除的对象
   * @return 若成功移除，返回 true，否则返回 false
   */
  public boolean remove(Object objectToRemove)
  {
    boolean removed = false; 
    int currentIndex = this.head; // currentIndex 用于遍历队列，顺序为从头部至尾部，因此初始为头部索引
    if (this.tail == this.head) // 若队列为空
      return removed; // 则返回 false
    int i1;
    // 若遍历至队列尾部，for 循环停止
    for (int nextIndex = currentIndex + 1 & this.modulus; currentIndex!= this.tail; nextIndex = i1)
    {
      if (this.queue[currentIndex] == objectToRemove) // 若当前遍历到的对象与 objectToRemove 是同一个对象
      {
        this.queue[currentIndex] = null; // 则取消对这个对象的引用
        removed = true; 
	return removed; // 返回 true
      }
      i1 = nextIndex + 1 & this.modulus; // 计算下一轮循环中 nextIndex 的值，保存于局部变量 il 之中
      currentIndex = nextIndex; // 更新 currentIndex
    }
    return removed; // 若队列中不存在 objectToRemove 对象，则返回 false
  }
}

/* Location:           E:\apk\NextDesktop\classes-dex2jar.jar
 * Qualified Name:     com.go.gl.util.FastQueue
 * JD-Core Version:    0.5.4
 */