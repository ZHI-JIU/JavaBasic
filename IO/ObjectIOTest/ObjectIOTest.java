package IO.ObjectIOTest;

import org.junit.Test;

import java.io.*;
import java.util.Objects;

public class ObjectIOTest {
    public static String FILE_PATH = "F:\\study\\JavaBasic\\IO\\ObjectIOTest\\ObjIO.txt";

    @Test
    // String的序列化
    public void test01() {
        ObjectOutputStream oos = null;
        try {
            // 序列化，通过ObjectOutputStream实现
            oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
            // 写完后是乱码，需要反序列化来读取
            oos.writeObject(new String("测试序列化"));
            oos.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (Objects.nonNull(oos)) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    // String的反序列化
    public void test02() {
        ObjectInputStream oos = null;
        try {
            // 反序列化
            oos = new ObjectInputStream(new FileInputStream(FILE_PATH));
            // 写完后是乱码，需要反序列化来读取
            System.out.println((String) oos.readObject());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(oos)) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    // 自定义类的的序列化
    public void test03() {
        ObjectOutputStream oos = null;
        try {
            // 序列化，通过ObjectOutputStream实现
            oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
            // 写完后是乱码，需要反序列化来读取
            oos.writeObject(new Person("张哲瀚", 29));
            oos.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (Objects.nonNull(oos)) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    // 自定义类的的反序列化
    public void test04() {
        ObjectInputStream oos = null;
        try {
            // 反序列化
            oos = new ObjectInputStream(new FileInputStream(FILE_PATH));
            // 写完后是乱码，需要反序列化来读取
            System.out.println((Person) oos.readObject());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(oos)) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
