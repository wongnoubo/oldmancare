package com.noubo.oldmancare.util;

import android.util.Log;

import org.junit.Test;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.noubo.oldmancare.model.GPSModel;

import static org.junit.Assert.*;

/**
 * Created by admin on 2019/2/8.
 */
public class CodeUtilTest {
    @Test
    public void testGenerateUniqueCode(){
        System.out.println(CodeUtil.generateUniqueCode());
    }

//    @Test
//    public void test1() {
//        // 对象嵌套数组嵌套对象
//        String json1 = "{\n" +
//                "\t\"errno\": 0,\n" +
//                "\t\"data\": {\n" +
//                "\t\t\"update_at\": \"2019-03-13 23:55:31\",\n" +
//                "\t\t\"id\": \"location\",\n" +
//                "\t\t\"create_time\": \"2019-03-12 21:19:09\",\n" +
//                "\t\t\"current_value\": {\n" +
//                "\t\t\t\"lon\": 113.5318215,\n" +
//                "\t\t\t\"lat\": 22.25326103\n" +
//                "\t\t}\n" +
//                "\t},\n" +
//                "\t\"error\": \"succ\"\n" +
//                "}";
//        // 数组
//        //1、
//        //静态方法
//        GPSModel grade= JSON.parseObject(json1, GPSModel.class);
//        System.out.println(grade.getData().getCurrent_value().getLat());

//    }
}