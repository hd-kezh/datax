package com.asiainfo.datax.web.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DesCipher
{
    private static final String ALGORITHM = "DES/ECB/PKCS5Padding";
    private static final Logger LOG = LoggerFactory.getLogger(DesCipher.class);

    public static String encrypt(String message)
    {
        return encrypt(message, "b6fa92796c6431c5");
    }

    private static String fillChar(String key)
    {
        return CipherHelper.fillChar(key, 8);
    }

    public static String encrypt(String message, String key)
    {
        try
        {
            DESKeySpec dks = new DESKeySpec(fillChar(key).getBytes("UTF-8"));


            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            cipher.init(1, securekey, new SecureRandom());


            byte[] encVal = cipher.doFinal(message.getBytes("UTF-8"));
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
        catch (IllegalBlockSizeException e)
        {
            e.printStackTrace();
        }
        catch (BadPaddingException e)
        {
            e.printStackTrace();
        }
        catch (InvalidKeySpecException e)
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
            DESKeySpec dks = new DESKeySpec(fillChar(key).getBytes("UTF-8"));


            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            cipher.init(2, securekey, new SecureRandom());
            byte[] decordedValue = Base64.decodeBase64(encryptedData);
            byte[] decValue = cipher.doFinal(decordedValue);
            return new String(decValue);
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
        catch (IllegalBlockSizeException e)
        {
            LOG.error(e.getMessage());
            LOG.error("\r\n密文和密钥不匹配\r\n或者采用高版本的密码包解密低版本密码包的密文产生的错误，需要采用当前版本的密码包重新产生密文");
        }
        catch (BadPaddingException e)
        {
            e.printStackTrace();
        }
        catch (InvalidKeySpecException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return encryptedData;
    }
}
