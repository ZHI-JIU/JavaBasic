package IO.FileTest;

import org.junit.Test;

import java.io.File;

public class FileTest {
    @Test
    public void test01() {
        // 构造器1
        // 相对路径
        File file = new File("hello.txt");
        System.out.println(file);
        // 绝对路径
        File file2 = new File("F:\\study\\JavaBasic\\IO\\FileTest\\Hello.txt");
        System.out.println(file2);

        // 构造器2
        File file3 = new File("F:\\study\\JavaBasic\\IO\\FileTest", "hello3.txt");
        System.out.println(file3);

        // 构造器3
        File file4 = new File(file3, "hi.txt");
        System.out.println(file4);
        // new完以后还是内存层面的对象，不涉及物理上真正的文件。

    }

    @Test
    public void test02() {
        File file = new File("F:\\study\\JavaBasic");
        String[] files = file.list();
        for (String f: files) {
            System.out.println(f);
        }
        System.out.println("*******");
        File[] files1= file.listFiles();
        for (File f: files1) {
            System.out.println(f);
        }
    }
}
