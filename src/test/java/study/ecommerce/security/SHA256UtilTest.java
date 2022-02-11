package study.ecommerce.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SHA256UtilTest {

    @Test
    void generateSalt() {
        String s = SHA256Util.generateSalt();
        System.out.println("s = " + s);
    }
}
