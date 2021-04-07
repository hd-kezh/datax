package com.asiainfo.datax.web.util;

public class CipherHelper
{
    public static String fillChar(String key, int minLen)
    {
        if ((key == null) || (key.length() < 8)) {
            return "b6fa92796c6431c5";
        }
        int leng = key.length();
        if (leng < minLen)
        {
            for (int i = leng; i < minLen; i++) {
                key = key + "m";
            }
            return key;
        }
        return key;
    }
}
