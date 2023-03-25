package com.example.thundermarket.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

 public class TimeInteval {


    public static String Calculate(LocalDateTime createAt){
        LocalDateTime today  = LocalDateTime.now();
        long years = ChronoUnit.YEARS.between(createAt, today);
        long months = ChronoUnit.MONTHS .between(createAt, today);
        long weeks = ChronoUnit.WEEKS.between(createAt, today);
        long days = ChronoUnit.DAYS.between(createAt, today);
        long hours = ChronoUnit.HOURS.between(createAt, today);
        long minutes = ChronoUnit.MINUTES.between(createAt, today);
        long seconds = ChronoUnit.SECONDS.between(createAt, today);
        if(years > 0){
            return ""+years+"년 전";
        }else if(months > 0){
            return ""+months+"달 전";
        }else if(weeks > 0) {
            return "" + weeks + "주 전";
        }else if (days > 0) {
            return ""+days+"일 전";
        } else if (hours > 0) {
            return ""+hours+"시간 전";
        } else if (minutes > 0) {
            return "" + minutes + "분 전";
        }else{
            return "" + seconds + "초 전";
        }
    }
}
