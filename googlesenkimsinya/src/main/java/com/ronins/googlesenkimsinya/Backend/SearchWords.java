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



    public SearchWords() {

    }

    public int getWordsCount(String URL,String Word){
        int count = 0;
        Document doc;
        try{
            doc = Jsoup.connect(URL).get();
            Elements elements = doc.select("html");
            String veri = elements.html();
            veri = veri.toUpperCase();
            String kelimeler[] = veri.split(" ");

            for(int i=0;i<kelimeler.length-1;i++){
                if(kelimeler[i].equalsIgnoreCase(Word)){
                    count++;
                }
            }

        }catch (IOException e){

        }
        return count;
    }

    public int[] getURLWordsCount(String URL,String Words){
        String[] kelimeler = Words.split(",");
        int counts[] = new int[kelimeler.length];

        try{
            Document doc;
            doc = Jsoup.connect(URL).get();
            String text = doc.body().text();
            String allWords[];
            if(!text.isEmpty()){
                allWords = text.split("\\s+");
            }else{
                return counts;
            }

            for(int i = 0;i<allWords.length;i++){
                for (int j = 0; j < kelimeler.length ; j++) {
                    if(kelimeler[j].equalsIgnoreCase(allWords[i])){
                        counts[j]++;
                    }
                }
            }

        }catch (Exception e){

        }

    return counts;
    }

    /*
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
        */

    public List<Pages> URLQualityAlgorithm(String links, String words){
        String linksArray[] = links.split(","); // contains URLS in every index
        String wordsArray[] = words.split(","); // contains keywords in every index
        List<Pages> pageList = new ArrayList<>(); // ?

        int URLSnWordCount[][] = new int[linksArray.length][wordsArray.length]; // will contain URLS and keyword counts for every URL
        double URLQuality[] = new double[linksArray.length]; // will contain our qualification points for URLS, low points = better pages

        for(int i = 0; i<linksArray.length; i++){
                //URLSnWordCount[i][j] = getWordsCount(linksArray[i],wordsArray[j]); // now contains URLS and keyword counts for every URL
                URLSnWordCount[i] = getURLWordsCount(linksArray[i],words);
        }

        double standardError[] = new double[linksArray.length]; // will contain standardError for every URL's keyword count.
        double keyWordSum[] = new double[linksArray.length]; // will contain sum of keywords for every URL
        double average[] = new double[linksArray.length]; // will contain keyword averages for every URL

        for(int i = 0; i<linksArray.length; i++){
            for(int j = 0; j<wordsArray.length; j++){
                keyWordSum[i] += URLSnWordCount[i][j]; // now keyWordSum contains keyword sums for every URL
            }
            average[i] = keyWordSum[i] / wordsArray.length; // now average contains keyword averages for every URL
        }

        for(int i = 0; i<linksArray.length; i++){ //  Calculation for part of standardError
            for(int j = 0; j<wordsArray.length; j++){
                standardError[i] += Math.pow(URLSnWordCount[i][j] - average[i],2);
            }
        }

        for(int i = 0; i<linksArray.length; i++){ // URLQuality calculation
            if(standardError[i]!=0){
                standardError[i] = Math.sqrt(standardError[i] /= (wordsArray.length - 1));
                URLQuality[i] = standardError[i] / Math.pow(keyWordSum[i],2);
            }else{
                URLQuality[i] = 1/Math.pow(keyWordSum[i],2);
            }
        }

        for(int i = 0; i<linksArray.length; i++){ // Double URLQuality Point if one of the keywords missing in that url so it loses its quality
            for(int j = 0; j<wordsArray.length; j++){
                if(URLSnWordCount[i][j]==0){
                    URLQuality[i] = URLQuality[i] * 2;
                }
            }
        }

        int URLQualityOrder[] = new int[linksArray.length];
        for (int i = 0; i <URLQualityOrder.length ; i++) {
            URLQualityOrder[i] = 1; // Now every URL has the number 1
        }

        for(int i = 0; i<linksArray.length; i++){ // Assign URL orders
            for(int j = 0; j<linksArray.length; j++){
                if(URLQuality[i]>URLQuality[j]){ // if url quality of j is higher than i
                    URLQualityOrder[i]++;
                }else if(URLQuality[i]==URLQuality[j]){
                    if(keyWordSum[i]<keyWordSum[j]){
                        URLQualityOrder[i]++;
                    }
                }
            }
        }

        for (int i = 0; i < linksArray.length ; i++) { // URLS added to list
            Pages page = new Pages();
            page.setURL(linksArray[i]);
            page.setWordsCount(URLSnWordCount[i]);
            page.setRank(URLQualityOrder[i]);
            page.setUrlQuality(URLQuality[i]);
            pageList.add(page);
        }

        return pageList;
    }

   /* public double algorithmForSubURLS(String links, String words){ // links = main URL and its sub URLS
        String linksArray[] = links.split(","); // contains URLS in every index
        String wordsArray[] = words.split(","); // contains keywords in every index
        List<Pages> URLS = new ArrayList<>(); // ?
        double qualitySum = 0;

        for(int i = 0; i < linksArray.length; i++){
            URLS.addAll(URLQualityAlgorithm(linksArray[i],words));
        }

        for(int i = 0; i < linksArray.length; i++){
            qualitySum += URLS.get(i).getUrlQuality();
        }

        return qualitySum;
    }*/

    public List<String> getLinks(String URL){
        List<String> links = new ArrayList<>();
        try {
            Document doc;
            doc = Jsoup.connect(URL).get();
            Elements elements = doc.select("a[href]");

            for (Element link : elements) {
                //log.info( link.attr("abs:href") );
                if(!links.contains(link.attr(link.attr("abs:href"))))
                     links.add(link.attr("abs:href"));
            }


        }catch (Exception e){

        }
        return links;
    }

    public List<String> getLinks(List<String> URLs){
        List<String> links = new ArrayList<>();
        try {
            Document doc;

            for (int i = 0; i < URLs.size() ; i++) {
                doc = Jsoup.connect(URLs.get(i)).get();
                Elements elements = doc.select("a[href]");
                for (Element link : elements) {
                    //log.info( link.attr("abs:href") );
                    if(!links.contains(link.attr(link.attr("abs:href"))) && !URLs.contains(link.attr(link.attr("abs:href"))) )
                        links.add(link.attr("abs:href"));
                }
            }

        }catch (Exception e){

        }
        return links;
    }



    public String getEsAnlam(String word){
        String URL = "http://www.es-anlam.com/kelime/" + word;
        Document doc;
        String veri;
        String esAnlam = new String();
        try {
            doc = Jsoup.connect(URL).get();
            Elements elements = doc.select("#esanlamlar");
            veri = elements.html();
            Document docx = Jsoup.parse(veri);
            Elements elementsx = docx.select("strong");
            esAnlam = elementsx.html();
            log.info("Es anlam : "+esAnlam);
        }catch (Exception e){

        }
        return esAnlam;
    }


}
