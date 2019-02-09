package com.noubo.oldmancare.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by admin on 2019/2/9.
 */
public class PhoneFormatCheckUtilsTest {
    @Test
    public void testisPhoneLegal(){
        if(!PhoneFormatCheckUtils.isPhoneLegal("1311311503"))
            System.out.println("hh");
        else
            System.out.println("bb");
    }
}