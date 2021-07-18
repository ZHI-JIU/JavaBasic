package IO.FileRWTest;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class FileIOStreamTest {
    @Test
    // 实现图片的复制
    public void test01() {
        String srcPath = "F:\\study\\JavaBasic\\IO\\FileRWTest\\Princess.jpg";
        String dstPath = "F:\\study\\JavaBasic\\IO\\FileRWTest\\Princess_copy.jpg";
        long start = System.currentTimeMillis();
        copyFile(srcPath, dstPath);
        long end = System.currentTimeMillis();
        System.out.println("复制耗时：" + (end - start)); // 227
    }

    public void copyFile(String srcPath, String dstPath) {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            File srcFile = new File(srcPath);
            File dstFile = new File(dstPath);

            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(dstFile);

            byte[] buffer = new byte[5];
            int len;
            while((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (Objects.nonNull(fos)) {
                try {
                    fos.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (Objects.nonNull(fis)) {
                try {
                    fis.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
