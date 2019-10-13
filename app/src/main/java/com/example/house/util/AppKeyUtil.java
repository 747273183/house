package com.example.house.util;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AppKeyUtil{
    private static final String TAG = "AppKeyUtil";
    public static  String getPwd(String key)
    {
        Log.d(TAG, "key: "+key);
        String substring = md5Decode32(key).toUpperCase().substring(0, 18);
        Log.d(TAG, "getPwd: "+substring);
        return substring;
    }

    /**
     * 32位MD5加密
     * @param content -- 待加密内容
     * @return
     */
    private static String md5Decode32(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException",e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }
        //对生成的16字节数组进行补零操作
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10){
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
