package IO.FileRWTest;

import org.junit.Test;

import java.io.*;
import java.util.Objects;

public class FileRWTest {
    @Test
    public void test01() throws IOException {
        FileReader fr = null;
        // 实例化File对象，指明要操作的文件
        // FileNotFoundException
//        File file = new File("hello.txt");
        File file = new File("F:\\study\\JavaBasic\\IO\\FileRWTest", "hello.txt");
        System.out.println(file.getAbsolutePath());

        try {
            // 实例化一个具体的输入流
            fr = new FileReader(file);

            // 使用流读入数据
            int data;
            // read()：返回读入的字符，如果到达文件末尾，返回-1
            while((data = fr.read()) != -1) {
                System.out.print((char) data);
            }
        } catch (FileNotFoundException e) {
            System.out.println(String.format("文件%s不存在", file.getAbsolutePath()));
        } catch (IOException e) {
            System.out.println("读取数据异常");
        }
        if (Objects.nonNull(fr)) {
            // 关闭文件
            fr.close();
        }
    }

    // 一次读入多个字符
    @Test
    public void test02() {
        FileReader fr = null;
        File file = new File("F:\\study\\JavaBasic\\IO\\FileRWTest", "hello.txt");
        System.out.println(file.getAbsolutePath());

        try {
            fr = new FileReader(file);

            int len;
            char[] cbuf = new char[5];
            // read()：返回读入的字符个数，如果到达文件末尾，返回-1
            while((len = fr.read(cbuf)) != -1) {
                // 方式一
                // 不能这么输出，如果文件的字符不是5的倍数，最后一轮会打印上一轮残留的字符
                // test hello 12llo
                // System.out.println(cbuf);

                // 方式二
                // test hello 12
                for(int i=0; i < len; i++) {
                    System.out.print(cbuf[i]);
                }
                System.out.println();

                // 方式三
                String str = new String(cbuf, 0, len);
                System.out.println(str);
            }
        } catch (FileNotFoundException e) {
            System.out.println(String.format("文件%s不存在", file.getAbsolutePath()));
        } catch (IOException e) {
            System.out.println("读取数据异常");
        } finally {
            if (Objects.nonNull(fr)) {
                // 关闭文件
                try {
                    fr.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Test
    // 输出数据
    public void test03() {
        FileWriter fw = null;
        File file = new File("F:\\study\\JavaBasic\\IO\\FileRWTest", "hello.txt");
        System.out.println(file.getAbsolutePath());

        try {
            // 追加
//            fw = new FileWriter(file, true);
            // 覆盖
            fw = new FileWriter(file, false);

            // 写出会自动创建文件，如果已有文件，会覆盖已有的内容
            fw.write("test FileWriter");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (Objects.nonNull(fw)) {
                // 关闭文件
                try {
                    fw.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
