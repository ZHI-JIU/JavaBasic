# 目录  
* File类的使用
* IO流原理及流的分类
* 节点流（文件流）
* 缓冲流
* 转换流
* 标准输入输出流
* 打印流
* 数据流
* 对象流
* 随机存取文件流
* NIO.2中Path、Paths、Files类的使用

# File类的使用
File类的一个对象，代表一个文件或文件夹。  
File类中设计到关于文件或文件目录的创建、删除、重命名等方法，但是没有设计到读写文件内容的操作。读写必须使用**IO流**来完成。  
File类的对象常会作为参数传递到流的构造器中，作为读取、写入的一个端点。

* 常见方法：
  ```java
  // 获取绝对路径
  public String getAbsolutePath()
    
  // 获取路径
  public String getPath()
  
  // 获取名称
  public String getName()

  // 获取上级目录
  public String getParent()
    
  // 获取文件长度
  public String getLength()

  // 获取最近修改时间，毫秒值
  public long lastModified()()
  
  // 下面两个适用于文件目录，详见FileTest.test02
  // 获取指定目录下所有文件或文件夹的名称数组
  public String[] list()
  
  // 获取指定目录下所有文件或文件夹的File数组
  public File[] listFiles()
  
  // 把文件名重命名为指定的文件路径
  // file1在硬盘中存在destFile在硬盘中不存在才能保证重命名成功
  public boolean renameTo(File dest)
  
  // 判断是否是文件夹
  public boolean isDirectory()
  
  // 判断是否是文件
  public boolean isFile()
  
  public boolean exists()
  
  public boolean canRead()
  
  public boolean canWrite()
  
  public boolean isHidden()
  
  // 创建文件，如果已存在，则不创建返回false
  public boolean createNewFile() 
  
  // 创建目录，如果存在，则不创建；如果父目录不存在，则不创建
  public boolean mkdir()
  
  // 创建目录，如果存在，则不创建；如果父目录不存在，一并创建
  public boolean mkdir()
  
  // 删除文件或文件夹
  public boolean delete()
  ```
## 创建File类的实例
* 路径分隔符：  
    * window: ``\\``  
    * unix: ``/``

* 构造器, 参考{@link FileTest.test01}：
    ```java
    // 相对路径：在main下是相对project的路径，在函数下是相对module的路径
    public File(String pathname)
    public File(String parent, String child)
    public File(File parent, String child)
    ```
# IO流原理及流的分类
按数据流向分：
* 输入流（从磁盘到内存）
* 输出流（从内存到磁盘）

按操作数据单位分：
* 字节流（8 bit），适用于图片、视频
* 字符流（16 bit，2个字节），适用于文本

按留的角色分：
* 节点流（直接作用文件）
* 处理流（在已有流上包的一层，将已有流作为参数传入构造器）

Java的IO流涉及40多个类，都是从四个抽象类派生出来的，派生子类都以父类名作为后缀。  

| 抽象基类 | 字节流       | 字符流 |
|----------|--------------|--------|
| 输入流   | InputStream  | Reader |
| 输出流   | OutputStream | Writer |

![image](https://github.com/ZHI-JIU/JavaBasic/blob/main/IO/png/IO.png)

# 节点流（文件流）
文件流分为下面四种：FileInputStream、FileOutputStream、FileReader、FileWriter
字节流（FileInputStream、FileOutputStream）可以处理图片、视频，也可以处理文本，但是处理中文等占用多个字节的文本时会出现乱码的问题。因为用char[]去取字节时，可能会截断字符。如果仅复制，不在内存层面去查看看则也没有问题。  
字符流（FileReader、FileWriter）只能处理文本，不能处理图片、视频等非文本文件。

## FileReader
### 步骤
详见FileRWTest.test01~test02：
1. 实例化File对象，指明要操作的文件
2. 实例化一个具体的输入流
3. 使用流读入数据
4. 关闭流（物理连接jvm无法回收，需要手动管理）
```java
public void test01() throws IOException {
    FileReader fr = null;
    // 实例化File对象，指明要操作的文件
    File file = new File("F:\\study\\JavaBasic\\IO\\FileRWTest", "hello.txt");
    System.out.println(file.getAbsolutePath());
    // 实例化一个具体的输入流
    fr = new FileReader(file);
    try {
        // 使用流读入数据
        int data;
        // read()：返回读入的字符，如果到达文件末尾，返回-1
        while((data = fr.read()) != -1) {
            System.out.print((char) data);
        }
    } catch (IOException e) {
        System.out.println("读取数据异常");
    }
    if (Objects.nonNull(fr)) {
        // 关闭文件
        fr.close();
    }
}
```
### 说明
* 为了保证流资源可以执行关闭，要用try-catch-finally处理异常，不能直接抛出。  
* 读入的文件一定要存在，否则会报FileNotFoundException
* 读取方法：``read(char[] cbuf)``

## FileWriter
### 步骤
详见FileRWTest.test03：
1. 实例化File对象，指明要操作的文件
2. 实例化一个具体的输出流
3. 使用流输出数据
4. 关闭流（物理连接jvm无法回收，需要手动管理）

### 说明
* 输出的文件可以不存在
    * 如果不存在会自动创建；
    * 如果存在:
        * 如果构造器是FileWriter(File file, boolean append)且指定append为true，则追加模式写入文本。
        * 如果构造器是FileWriter(File file)或append为false，则以覆盖模式写入文本。
* 输出方法：``write(char[] cbuf, 0, len)``

## FileInputStream、FileOutputStream
* 和FileReader、FileWriter类似，只是可以处理的对象有所区别。
* 输入方法：``read(byte[] cbuf)``
* 输出方法：``write(byte[] cbuf, 0, len)``

# 缓冲流 (处理流的一种)
缓冲流分为下面四种：BufferedInputStream、BufferedOutputStream、BufferedReader、BufferedWriter  
作用：提高流读取、写入的速度。
速度快的原因：内部提供了缓冲区DEFAULT_BUFFER_SIZE = 8192，缓冲区满了以后才会进行IO操作，用空间换取了时间。调用``flush()``方法能手动将缓冲区中的内容写入文件中。

### 步骤
1. 实例化File对象，指明要操作的文件
2. 实例化一个具体的节点流
3. 实例化一个具体的缓冲流
4. 使用流读入、输出数据
5. 关闭流  
5.1. 先关外层再关内层
5.2. 关闭外层流时，内层流会自动关闭


## BufferedReader
* 输入方法：``read(char[] cbuf)``，``readLine()``

读入可以使用``public String readLine()``读入一行，读到文件最后一行时返回null。  
其他三种类型与节点流的输入输出操作相同。

# 转换流(处理流的一种)
提供字节流和字符流之间的的转换。  
分为两种：
* InputStreamReader：将**字节**输入流转换为**字符**输入流
* OutputStreamWriter：将**字符**输出流转换为**字节**输出流  
![image](https://github.com/ZHI-JIU/JavaBasic/blob/main/IO/png/TransformStream.png)

* 构造器除了要传入字节流外，还需要传入目标文件的编码方式。
# 标准输入输出流
``System.in``: 默认从键盘输入，可以通过``setIn(InputStream is)``改变输入流。
``System.out``: 默认从控制台输出，可以通过``setOut(OutputStream os)``改变输出流。

# 打印流
# 数据流
# 对象流（处理流的一种）
用于存储和读取基本数据类型或对象的处理流，可以把Java中的对象放到数据流中，也可以将数据流中的数据转换成对象。  

``ObjectInputStream``: 反序列化，从文件中的数据读取到内存中的对象。
``ObjectOutputStream``：序列化，将内存中的对象作为数据输出到文件中。
* ``ObjectInputStream``和``ObjectOutputStream``不能序列化``static``和``transient``修饰的成员变量

## 对象的序列化机制
* 序列化：将内存中的Java对象转换成与平台无关的二进制流，这个二进制流可以保存在磁盘中，也可以通过网络传输到另一个网络节点上。
* 反序列化：程序获取到二进制流，可以恢复出内存中的Java对象。

## 实现序列化机制
Java对象可序列化需要满足以下几点，详见[ObjectIOTest.Person](https://github.com/ZHI-JIU/JavaBasic/blob/main/IO/ObjectIOTest/Person.java)
1. 实现Serializable接口
2. 提供全局常量serialVersionUID  
2.1. IDEA中设置自动生成：File -> Setting -> Editor -> Inspections -> 搜索UID -> Serializable class without xxx
3. 序列化后，类的结构发生变化(如增删属性)，但是serialVersionUID不变，仍可以反序列化。但是如果不在类中指定serialVersionUID，这个值由系统自动生成，修改类的结构后，这个值变了，无法再反序列化。
4. 类内的所有属性可序列化（基本数据类型都是可序列化的）。
5. ``static``和``transient``修饰的成员变量不可序列化，反序列化后得到null。

# 随机存取文件流
# NIO.2中Path、Paths、Files类的使用
