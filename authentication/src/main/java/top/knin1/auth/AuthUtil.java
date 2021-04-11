package top.knin1.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author KNin1
 */
public class AuthUtil {
    public static String getDigestByMD5(String input) {
        if (input == null || input.length() == 0) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : md5.digest()) {
                // 一个 byte 格式化成两位的16进制，不足两位高位补零
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
