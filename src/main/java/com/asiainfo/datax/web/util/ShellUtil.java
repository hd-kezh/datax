package com.asiainfo.datax.web.util;

import java.io.InputStream;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellUtil
{
    private static final Logger log = LoggerFactory.getLogger(ShellUtil.class);

    public static String execShell(String... shell)
            throws Exception
    {
        log.info("shell :" + Arrays.toString(shell));
        StringBuilder sb = new StringBuilder();
        Process process = Runtime.getRuntime().exec(shell);
        InputStream inputStream = process.getInputStream();
        byte[] buffer = new byte[1024];
        int count = -1;
        while ((count = inputStream.read(buffer)) != -1) {
            sb.append(new String(buffer, 0, count));
        }
        log.debug(sb.toString());
        return sb.toString();
    }
}
