package com.hkycu.goods.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderUtil {
    public static String generateTransferNo(){
        String result = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
        String date = dateFormat.format(new Date());
        Random random = new Random();
        for(int i=0;i<3;i++){
            result +=  random.nextInt(10);
        }
        return date + result;
    }


}
