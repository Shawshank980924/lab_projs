/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022_06_06 11:51:08
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-06-06 11:57:38
 * @FilePath: /block_pipe/src/main/java/com/block_pipe/utils/DateSplit.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.block_pipe.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateSplit {
    /**
     * 传入年，获取一年时间
     */
    public static List<String> getDaysByYear(int year, int mon, int da) {
        Calendar c = Calendar.getInstance();
        List<String> dates = new ArrayList<String>();
        for (int i = mon; i < 12; i++) {
            c.set(year, i, 1);
            int lastDay = c.getActualMaximum(Calendar.DATE);
            int j = 1;
            if (i == mon)
                j = da;
            for (; j <= lastDay; j++) {
                String month = "";
                String day = "";
                if (i < 9)
                    month = "_0" + (i + 1);
                else
                    month = "_" + (i + 1);
                if (j < 10)
                    day = "_0" + j;
                else
                    day = "_" + j;
                String date = year + month + day;
                System.out.println(date);
                dates.add(date);
            }
        }
        return dates;
    }
}