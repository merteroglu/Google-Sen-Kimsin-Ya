package com.ronins.googlesenkimsinya.Backend;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import java.io.*;


public class SearchWords {

    Logger log = Logger.getLogger("Search Words");

    Document doc;

    public SearchWords() {

    }

    public int getWordsCount(String URL,String Word){
        int count = 0;
        try{
            doc = Jsoup.connect(URL).get();
            Elements elements = doc.select("html");
            String veri = elements.html();
            veri = veri.toUpperCase();
            String kelimeler[] = veri.split(" ");

            for(int i=0;i<kelimeler.length-1;i++){
                if(kelimeler[i].equalsIgnoreCase(Word.toUpperCase())){
                    count++;
                }
            }

        }catch (IOException e){

        }
        return count;
    }

    public List<Pages> rankPages(String links, String words){
        String linkler[] = links.split(",");
        String kelimeler[] = words.split(",");
        List<Pages> pagesList = new ArrayList<>();

        int ranks[][] = new int[linkler.length][kelimeler.length];
        int sitePuan[] = new int[linkler.length];

        for(int i = 0;i<linkler.length;i++){
            for(int j=0;j<kelimeler.length;j++){
                ranks[i][j] = getWordsCount(linkler[i],kelimeler[j]);
            }
        }

        int standartSapmaToplami[] = new int[linkler.length];
        double standartSapmaOrtalamasi[] = new double[linkler.length];
        double ortalamalar[] = new double[linkler.length];


        for(int i=0;i<linkler.length;i++){
            for(int j=0;j<kelimeler.length;j++){
                sitePuan[i] += ranks[i][j];
            }
            ortalamalar[i] = sitePuan[i] / kelimeler.length;
        }

        for (int i = 0; i < linkler.length ; i++) {
            for (int j = 0; j < kelimeler.length ; j++) {
                standartSapmaToplami[i] += Math.abs(ortalamalar[i] - ranks[i][j]);
            }
            standartSapmaOrtalamasi[i] = standartSapmaToplami[i]/kelimeler.length;
        }

        double sitePuanlari[] = new double[linkler.length];

        for (int i = 0; i < linkler.length ; i++) {
            sitePuanlari[i] = standartSapmaOrtalamasi[i] / ortalamalar[i];
        }

        int siralama[] = new int[linkler.length];
        for (int i = 0; i <siralama.length ; i++) {
            siralama[i] = 1;
        }

        for (int i = 0; i < siralama.length  ; i++) {
            for (int j = 0; j < siralama.length ; j++) {
                if(sitePuanlari[i] < sitePuanlari[j]){
                    siralama[i]++;
                }else if(sitePuanlari[i] == sitePuanlari[j]){
                    if(sitePuan[i] > sitePuan[j]){
                        siralama[i]++;
                    }
                }
            }
        }


        for (int i = 0; i < linkler.length ; i++) {
            Pages page = new Pages();
            page.setURL(linkler[i]);
            page.setWordsCount(ranks[i]);
            page.setRank(siralama[i]);
            pagesList.add(page);
        }

       return pagesList;
    }

    public List<String> getLinks(String URL){
        List<String> links = new ArrayList<>();
        try {
            doc = Jsoup.connect(URL).get();
            Elements elements = doc.select("a[href]");

            for (Element link : elements) {
                //log.info( link.attr("abs:href") );
                links.add(link.attr("abs:href"));
            }


        }catch (Exception e){

        }
        return links;
    }


}
