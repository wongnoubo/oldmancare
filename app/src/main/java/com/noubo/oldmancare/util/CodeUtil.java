package com.noubo.oldmancare.util;

import java.util.UUID;

/**
 * Created by admin on 2019/2/8.
 */

public class CodeUtil {
    //生成唯一的激活码
    public static String generateUniqueCode(){
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0,6);
    }
}