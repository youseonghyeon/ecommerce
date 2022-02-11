package study.ecommerce.security;

import study.ecommerce.entity.Member;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class SHA256Util {

    /**
     * SHA-256 암호화 함
     * @param source 원본
     * @param salt(String) SALT 값
     * @return
     */
    public static String getEncrypt(String password, String salt) {
        return getEncrypt(password, salt.getBytes());
    }

    /**
     * SHA-256 암호화 함
     * @param source 원본
     * @param salt(byte[]) SALT 값
     * @return
     */
    private static String getEncrypt(String password, byte[] salt) {

        String result = "";

        byte[] a = password.getBytes();
        byte[] bytes = new byte[a.length + salt.length];

        System.arraycopy(a, 0, bytes, 0, a.length);
        System.arraycopy(salt, 0, bytes, a.length, salt.length);

        try {
            // 암호화 방식 지정 메소드
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytes);

            byte[] byteData = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte byteDatum : byteData) {
                sb.append(Integer.toString((byteDatum & 0xFF) + 256, 16).substring(1));
            }

            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String getEncrypt(String password, Member member) {
        String salt = member.getSalt();
        return SHA256Util.getEncrypt(password, salt);
    }

    public static String generateSalt() {
        Random random = new Random();

        byte[] salt = new byte[8];
        random.nextBytes(salt);

        StringBuilder sb = new StringBuilder();
        for (byte b : salt) {
            // byte 값을 Hex 값으로 바꾸기.
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
