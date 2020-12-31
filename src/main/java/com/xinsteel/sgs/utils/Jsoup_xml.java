package com.xinsteel.sgs.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class Jsoup_xml {
    public static Map<String, String> getTrMessage(String xml)  throws Exception{

        Map<String, String> map = new HashMap<String, String>();

        Document document = Jsoup.parse(xml);
        //System.out.println(document);

        Elements jhh = document.getElementsByTag("jhh");
        map.put("jhh",jhh.get(0).text());

        Elements ch = document.getElementsByTag("ch");
        map.put("ch",ch.get(0).text());

        Elements mz = document.getElementsByTag("mz");
        map.put("mz",mz.get(0).text());

        Elements pz = document.getElementsByTag("pz");
        map.put("pz",pz.get(0).text());

        Elements jz = document.getElementsByTag("jz");
        map.put("jz",jz.get(0).text());

        Elements msj = document.getElementsByTag("msj");
        map.put("msj",msj.get(0).text());

        Elements psj = document.getElementsByTag("psj");
        map.put("psj",psj.get(0).text());

        Elements xczl = document.getElementsByTag("xczl");
        map.put("xczl",xczl.get(0).text());

        Elements ly = document.getElementsByTag("ly");
        map.put("ly",ly.get(0).text());

        return map;

    }
}
