package com.wow.common.util;

import com.wow.common.constant.CommonConstant;

import java.util.Random;

/**
 * Created by zhengzhiqing on 16/7/7.
 */
public class RandomGenerator {
    /**
     * 创建指定数量的随机字符串
     * @param numberFlag 是否是数字
     * @param length
     * @return
     */
    public static String createRandom(boolean numberFlag, int length){
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                    }
                retStr += strTable.charAt(intR);
                }
            if (count >= 2) {
                bDone = false;
                }
            } while (bDone);
        return retStr;
    }

    public static int createRandomNumBasedOnTimestamp() {
        long t = System.currentTimeMillis();//获得当前时间的毫秒数
        Random rd = new Random(t);//作为种子数传入到Random的构造器中
        return rd.nextInt(CommonConstant.PRODUCT_CODE_MAX);
    }
}
