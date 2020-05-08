package com.ljz.diagnostic_system.common.Utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Utils {

    public static String getFileMD5String(MultipartFile file) {
        try {
            MessageDigest mMessageDigest;
            mMessageDigest = MessageDigest.getInstance("MD5");
            InputStream fis = file.getInputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer, 0, 1024)) > 0) {
                mMessageDigest.update(buffer, 0, length);
            }
            fis.close();
            return new BigInteger(1, mMessageDigest.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
