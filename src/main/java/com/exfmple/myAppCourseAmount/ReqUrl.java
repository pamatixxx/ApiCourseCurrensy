package com.exfmple.myAppCourseAmount;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReqUrl {

    public static String generateInterfaceText(String pair) throws Exception {
        String finalText = "Курс "+ pair + "<br />";

        //обработка 1 источника
        Object object = sendGet("https://www.bitstamp.net/api/v2/ticker/btc" + pair + "/"); //тут нет UAH - сделать проверку в базе - есть ли такая пара + урл
        //  System.out.println(object);
        Map<String, Double> bts = sendParse(object.toString());


        //обработка 2 источника
        ReqUrl obj = new ReqUrl();
        String urlBTC2 = "https://api.coinbase.com/v2/prices/BTC-" + pair + "/spot";
        Coinbase coinbaseBTCUSD = new ObjectMapper().readValue(new StringReader(obj.sendGet(urlBTC2)), Coinbase.class);
        //   {"data":{"base":"BTC","currency":"USD","amount":"9211.085"}}
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 00:00 z");


        finalText += ("<br />Источник : " + "https://www.bitstamp.net/api/v2/ticker/btc" + pair + "/" + "<br />");
        finalText += ("Цена на начало дня : " + bts.get("vwap") + "<br />");//первая цена дня
        finalText += ("Цена на момент запроса : " + bts.get("last") + "<br />");//цена на момент запроса
        finalText += ("Текущее время " + date + "<br />");//метка времени текущее время
       // System.out.println("////////////////");
        finalText += ("<br />Источник : " + urlBTC2 + "<br />");
        finalText += ("Цена на момент запроса : " + coinbaseBTCUSD.data.amount + "<br />");
        finalText += ("Текущее время : " + date + "<br />");


        return finalText;
    }

    public static String sendGet(String url) throws Exception {


        HttpURLConnection httpClient =
                (HttpURLConnection) new URL(url).openConnection();

        // optional default is GET
        httpClient.setRequestMethod("GET");

        //add request header
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = httpClient.getResponseCode();
        //  System.out.println("\nSending 'GET' request to URL : " + url);
        //  System.out.println("Response Code : " + responseCode);

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            //print result
            return response.toString();
        }
    }

    public static Map sendParse(String s) {

        Map<String, Double> mapBTCUSD = new HashMap<>();

        String[] dataCells = s.split(",");
        for (int i = 0; i < dataCells.length; i++) {
            String[] dataCells2 = dataCells[i].split(":");
            mapBTCUSD.put(dataCells2[0].replaceAll("[^A-Za-zА-Яа-я0-9]", ""), Double.valueOf(dataCells2[1].replaceAll("[^\\d.]", "")));
        }

        return mapBTCUSD;
    }
}
