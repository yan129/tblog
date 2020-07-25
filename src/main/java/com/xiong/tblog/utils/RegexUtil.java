package com.xiong.tblog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static boolean regexData(String data, String regexRule){
        Pattern pattern = Pattern.compile(regexRule);
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }
}
