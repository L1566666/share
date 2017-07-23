package com.share.modules.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lin on 2017/7/8 20:06.
 */
public class Tool {

    private Tool(){}

    /**
     * 2017年7月8日20:07:00
     * 生成md5加密字符串
     * @param uuid
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String MD5(String uuid) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("md5");    //获取md5加密摘要
        md.update(uuid.getBytes());
        byte[] b = md.digest();
        StringBuffer token = new StringBuffer("");
        for (int i = 0; i < b.length; i++) {
            int j = b[i];
            if(j < 0){
                j += 256;
            }
            if(j <16){
                token.append("0");
            }
            token.append(Integer.toHexString(j));
        }
        return token.toString();
    }

}
