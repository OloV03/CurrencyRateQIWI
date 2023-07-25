package com.IlyaJukov.FreshQIWI;

import com.IlyaJukov.FreshQIWI.API.CentralBank;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        if (!args[0].equals("currency_rate")) {
            System.out.println("Incorrect input");
            return;
        }

        Date date = null;
        String code = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--code")) {
                code = args[i+2];
                i++;
            }
            if (args[i].equals("--date")) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = format.parse(args[i+2]);
                    i++;
                } catch (ParseException e) {
                    System.out.println("Incorrect input date");
                }
            }
        }
        String res = new CentralBank().getCurrencyRate(code, date);
        System.out.println(res);
    }
}
