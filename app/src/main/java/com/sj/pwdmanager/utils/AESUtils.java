package com.sj.pwdmanager.utils;

import android.util.Base64;

import com.sj.pwdmanager.App;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by SJ on 2020/6/16.
 */
public class AESUtils {

    public static String encrypt(String strIn) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(getIV());
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(), iv);
        byte[] encrypted = cipher.doFinal(strIn.getBytes());
        return Base64.encodeToString(encrypted,Base64.NO_WRAP);
    }

    public static String decrypt(String strIn) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(getIV());
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), iv);
        byte[] encrypted1 = Base64.decode(strIn,Base64.NO_WRAP);
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original);
        return originalString;
    }

    private static SecretKeySpec getSecretKey(){
        byte[] arrBTmp = App.getApp().getKey().getBytes();
        byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");
        return skeySpec;
    }

    private static byte[] getIV() {
        byte[] arrBTmp = App.getApp().getKey().getBytes();
        byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        return arrB;
    }

}
