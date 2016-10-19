package com.tywho.appdemo.framework.helper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * http://www.tywho.com
 *
 * @author：sunlimiter
 * @create：2016-04-19 11:37
 */
public class AESUtil {
    public static String md5(String pass) {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
            byte[] data = pass.getBytes();
            m.update(data, 0, data.length);
            BigInteger i = new BigInteger(1, m.digest());
            return String.format("%1$032x", i);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return pass;
        }
    }
}
