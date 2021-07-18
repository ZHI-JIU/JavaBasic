package IO;

import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BufferedTest {
    @Test
    // 实现文件的复制
    public void test01() {
        String srcPath = "F:\\study\\JavaBasic\\IO\\FileRWTest\\Princess.jpg";
        String dstPath = "F:\\study\\JavaBasic\\IO\\FileRWTest\\Princess_copy.jpg";
        long start = System.currentTimeMillis();
        copyFile(srcPath, dstPath);
        long end = System.currentTimeMillis();
        System.out.println("复制耗时：" + (end - start)); // 7
    }

    public void copyFile(String srcPath, String dstPath) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            // 造文件
            File srcFile = new File(srcPath);
            File dstFile = new File(dstPath);

            // 造节点流
            FileInputStream fis = new FileInputStream(srcFile);
            FileOutputStream fos = new FileOutputStream(dstFile);
            // 造处理流
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);

            // 复制
            copy(bis, bos);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            // 关闭流，先关外层再关内层, 关闭外层流时，内层流会自动关闭
            if (Objects.nonNull(bis)) {
                try {
                    bis.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (Objects.nonNull(bos)) {
                try {
                    bos.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            //        fis.close();
            //        fos.close();
        }
    }

    public void copy(BufferedInputStream bis, BufferedOutputStream bos) throws IOException {
        byte[] buffer = new byte[5];
        int len;
        while((len = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
    }

    @Test
    // 实现文件的加密、解密
    public void test02() {
        String srcPath = "F:\\study\\JavaBasic\\IO\\FileRWTest\\Princess.jpg";
        String encryptPath = "F:\\study\\JavaBasic\\IO\\FileRWTest\\Princess_encrypt.jpg";
        String decryptPath = "F:\\study\\JavaBasic\\IO\\FileRWTest\\Princess_decrypt.jpg";
        encryptFile(srcPath, encryptPath);
        // 再次加密相当于解谜 m ^ n ^ n = m
        encryptFile(encryptPath, decryptPath);
        // Princess_encrypt.jpg无法查看，Princess_decrypt.jpg与Princess.jpg一致
    }

    public void encryptFile(String srcPath, String dstPath) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            // 造文件
            File srcFile = new File(srcPath);
            File dstFile = new File(dstPath);

            // 造节点流
            FileInputStream fis = new FileInputStream(srcFile);
            FileOutputStream fos = new FileOutputStream(dstFile);
            // 造处理流
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);

            // 加密
            encrypt(bis, bos);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            // 关闭流，先关外层再关内层, 关闭外层流时，内层流会自动关闭
            if (Objects.nonNull(bis)) {
                try {
                    bis.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (Objects.nonNull(bos)) {
                try {
                    bos.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            //        fis.close();
            //        fos.close();
        }
    }

    public void encrypt(BufferedInputStream bis, BufferedOutputStream bos) throws IOException {
        byte[] buffer = new byte[5];
        int len;
        while((len = bis.read(buffer)) != -1) {
            for (int i= 0; i < len; i++) {
                buffer[i] = (byte) (buffer[i] ^ 5);
            }
            bos.write(buffer, 0, len);
        }
    }

    @Test
    // 获取每个字符出现的次数
    public void test03() {
        String srcPath = "F:\\study\\JavaBasic\\IO\\FileRWTest\\hello.txt";
        String dstPath = "F:\\study\\JavaBasic\\IO\\FileRWTest\\hello_cnt.txt";
        cntFile(srcPath, dstPath);
    }

    public void cntFile(String srcPath, String dstPath) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            // 造文件
            File srcFile = new File(srcPath);
            File dstFile = new File(dstPath);

            // 造节点流
            FileReader fis = new FileReader(srcFile);
            FileWriter fos = new FileWriter(dstFile);
            // 造处理流
            br = new BufferedReader(fis);
            bw = new BufferedWriter(fos);

            // 计算每个字符个数
            Map<Character, Integer> cntMap = new HashMap<>();
            String str;
            while (Objects.nonNull(str = br.readLine())) {
                for (int i=0; i < str.length(); i++) {
                    Character key = str.charAt(i);
                    if (cntMap.containsKey(key)) {
                        cntMap.put(key, cntMap.get(key) + 1);
                    } else {
                        cntMap.put(key, 1);
                    }
                }
            }
            bw.write(map2String(cntMap));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            // 关闭流，先关外层再关内层, 关闭外层流时，内层流会自动关闭
            if (Objects.nonNull(br)) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (Objects.nonNull(bw)) {
                try {
                    bw.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public String map2String(Map<Character, Integer> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Character, Integer> entry: map.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        }
        return sb.toString();
    }
}
