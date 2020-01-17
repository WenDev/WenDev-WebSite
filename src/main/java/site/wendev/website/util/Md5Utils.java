package site.wendev.website.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类，提供MD5加密操作
 *
 * @author jiangwen
 */
public class Md5Utils {
    public static String encode(String str) {
        try {
            var md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());

            byte[] bytes = md.digest();
            int i = 0;
            var sb = new StringBuffer("");

            for (byte aByte : bytes) {
                i = aByte;

                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
