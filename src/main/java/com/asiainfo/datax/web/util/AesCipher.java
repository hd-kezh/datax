package com.asiainfo.datax.web.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AesCipher
{
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static byte[] ivValue = null;

    static
    {
        try
        {
            ivValue = "7b51fd7053196308".getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }

    private static final IvParameterSpec IV_SPEC = new IvParameterSpec(ivValue);
    private static Logger LOG = LoggerFactory.getLogger(AesCipher.class);

    public static String encrypt(String message)
    {
        return encrypt(message, "b6fa92796c6431c5");
    }

    public static String encrypt(String message, String key)
    {
        try
        {
            return encrypt(message, new SecretKeySpec(CipherHelper.fillChar(key, 16).getBytes("UTF-8"), "AES"));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return message;
    }

    public static String encrypt(String data, String key, String iv)
    {
        try
        {
            return encrypt(data, "AES/CBC/PKCS5Padding", new SecretKeySpec(CipherHelper.fillChar(key, 16).getBytes("UTF-8"), "AES"), new IvParameterSpec(iv.getBytes("UTF-8")));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return data;
    }

    public static String encrypt(String data, SecretKeySpec keySpec)
    {
        return encrypt(data, "AES/CBC/PKCS5Padding", keySpec, IV_SPEC);
    }

    public static String encrypt(String message, String algorithm, SecretKeySpec keySpec, IvParameterSpec ivSpec)
    {
        try
        {
            Cipher c = Cipher.getInstance(algorithm);
            if (ivSpec != null) {
                c.init(1, keySpec, ivSpec);
            } else {
                c.init(1, keySpec);
            }
            byte[] encVal = c.doFinal(message.getBytes("UTF-8"));
            return Base64.encodeBase64String(encVal);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchPaddingException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (InvalidKeyException e)
        {
            e.printStackTrace();
        }
        catch (InvalidAlgorithmParameterException e)
        {
            e.printStackTrace();
        }
        catch (IllegalBlockSizeException e)
        {
            e.printStackTrace();
        }
        catch (BadPaddingException e)
        {
            e.printStackTrace();
        }
        return message;
    }

    public static String decrypt(String encryptedData)
    {
        return decrypt(encryptedData, "b6fa92796c6431c5");
    }

    public static String decrypt(String encryptedData, String key)
    {
        try
        {
            return decrypt(encryptedData, new SecretKeySpec(CipherHelper.fillChar(key, 16).getBytes("UTF-8"), "AES"));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return encryptedData;
    }

    public static String decrypt(String encryptedData, String key, String iv)
    {
        try
        {
            return decrypt(encryptedData, "AES/CBC/PKCS5Padding", new SecretKeySpec(CipherHelper.fillChar(key, 16).getBytes("UTF-8"), "AES"), new IvParameterSpec(iv.getBytes("UTF-8")));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return encryptedData;
    }

    public static String decrypt(String data, SecretKeySpec keySpec)
    {
        return decrypt(data, "AES/CBC/PKCS5Padding", keySpec, IV_SPEC);
    }

    public static String decrypt(String encryptedData, String algorithm, SecretKeySpec keySpec, IvParameterSpec ivSpec)
    {
        if ((encryptedData == null) || (encryptedData.length() == 0)) {
            return "";
        }
        try
        {
            Cipher c = Cipher.getInstance(algorithm);
            if (ivSpec != null) {
                c.init(2, keySpec, ivSpec);
            } else {
                c.init(2, keySpec);
            }
            byte[] decordedValue = Base64.decodeBase64(encryptedData);
            byte[] decValue = c.doFinal(decordedValue);
            return new String(decValue, "utf-8");
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchPaddingException e)
        {
            e.printStackTrace();
        }
        catch (InvalidKeyException e)
        {
            e.printStackTrace();
        }
        catch (InvalidAlgorithmParameterException e)
        {
            e.printStackTrace();
        }
        catch (IllegalBlockSizeException e)
        {
            LOG.error(e.getMessage(), e);
        }
        catch (BadPaddingException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return encryptedData;
    }
}
