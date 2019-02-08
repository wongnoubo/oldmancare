package com.noubo.oldmancare.util;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by admin on 2019/2/8.
 */
public class CodeUtilTest {
    @Test
    public void testGenerateUniqueCode(){
        System.out.println(CodeUtil.generateUniqueCode());
    }
}