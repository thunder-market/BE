package com.example.thundermarket.util;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

public class KorPrice {

    public static String format(int money){
        DecimalFormat df = new DecimalFormat("###,###");
        String won = df.format(money);
        return won+"원";
    }
}
