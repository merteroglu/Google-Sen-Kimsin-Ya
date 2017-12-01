package com.ronins.googlesenkimsinya.Backend;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class SearchURL {

    Logger log = Logger.getLogger(SearchURL.class.getName());
    SearchWords searchWords;
    String Urls[];
    String Words[];


    public SearchURL(String URLs,String WORDs){
        searchWords = new SearchWords();
        try{
            Urls = URLs.split(",");
            Words = WORDs.split(",");
        }catch (Exception e){

        }
    }


    public URL getUrlInfo(String URL){
        List<String> links = new ArrayList<>();
        int wordCounts[] = new int[Words.length];
        URL newURL = new URL();

        try{
            Document doc;
            doc = Jsoup.connect(URL).timeout(0).userAgent("Mozilla").get();
            Elements elements = doc.select("a[href]");

            for(Element link : elements) {
                links.add(link.attr("abs:href"));
            }

            String text = doc.body().text();
            String allWords[];
            if(!text.isEmpty()){
                allWords = text.split("\\s+");
            }else{
                allWords = new String[0];
            }

            for(int i = 0;i<allWords.length;i++){
                for (int j = 0; j < Words.length ; j++) {
                    if(allWords[i].equalsIgnoreCase(Words[j])){
                        wordCounts[j]++;
                    }
                }
            }

            Set<String> hs = new HashSet<>();
            hs.addAll(links);
            links.clear();
            links.addAll(hs);

            for (int i = 0; i < links.size(); i++) {
                if(links.get(i).contains(".pdf") || links.get(i).contains(".xls") || links.get(i).contains(".png") || links.get(i).contains(".jpg")
                        || links.get(i).contains(".jpeg") ||links.get(i).contains(".gif") || links.get(i).contains(".doc") || links.get(i).contains(".docx") ||
                        links.get(i).contains(".xlsx") || links.get(i).charAt(links.get(i).length()-1) == '#' || links.get(i).equals(URL)
                        ){
                    links.remove(i);
                    i--;
                }
            }

            newURL.setLink(URL);
            newURL.setSubURLs(links);


            HashMap<String,Integer> hashMap = new HashMap<>();
            for (int i = 0; i < Words.length ; i++) {
                hashMap.put(Words[i],wordCounts[i]);
            }

            newURL.setWords(hashMap);

            newURL.setSubURLList(getUrlListInfo(links));

        }catch (IOException e){
            log.info("Exception in getUrlInfo : " + e.getMessage());
        }

        log.info("Derinlik 1 alındı");
        return newURL;
    }

    private List<URL> getUrlListInfo(List<String> Urls){
        List<URL> listURLs = new ArrayList<>();
        List<String> links = new ArrayList<>();
        Document doc;

            for (int i = 0; i < Urls.size(); i++) {
                try {
                    URL uri = new URL();
                int wordCounts[] = new int[Words.length];
                doc = Jsoup.connect(Urls.get(i)).timeout(3000).userAgent("Mozilla").get();
                Elements elements = doc.select("a[href]");
                for(Element link : elements) {
                    links.add(link.attr("abs:href"));
                }

                String text = doc.body().text();
                String allWords[];
                if(!text.isEmpty()){
                    allWords = text.split("\\s+");
                }else{
                    allWords = new String[0];
                }

                for(int k = 0;k<allWords.length;k++){
                    for (int j = 0; j < Words.length ; j++) {
                        if(allWords[k].equalsIgnoreCase(Words[j])){
                            wordCounts[j]++;
                        }
                    }
                }

                Set<String> hs = new HashSet<>();
                hs.addAll(links);
                links.clear();
                links.addAll(hs);

                for (int j = 0; j < links.size(); j++) {
                    if(links.get(j).contains(".pdf") || links.get(j).contains(".xls") || links.get(j).contains(".png") || links.get(j).contains(".jpg")
                            || links.get(j).contains(".jpeg") ||links.get(j).contains(".gif") || links.get(j).contains(".doc") || links.get(j).contains(".docx") ||
                            links.get(j).contains(".xlsx") || links.get(j).charAt(links.get(j).length()-1) == '#' || links.get(j).equals(Urls.get(i))
                            ){
                        links.remove(j);
                        j--;
                    }
                }

                uri.setLink(Urls.get(i));
                uri.setSubURLs(links);

                HashMap<String,Integer> hashMap = new HashMap<>();
                for (int j = 0; j < Words.length ; j++) {
                    hashMap.put(Words[j],wordCounts[j]);
                }

                uri.setWords(hashMap);

                uri.setSubURLList(getURLList(links));
                listURLs.add(uri);
                } catch (IOException e) {
                    log.info(i + " Exception in getUrlListInfo : " + e.getMessage());
                }
            }



        log.info("Derinlik 2 alındı");
        return listURLs;
    }

    private List<URL> getURLList(List<String> Urls){
        List<URL> listURLs = new ArrayList<>();
        Document doc;

            for (int i = 0; i < Urls.size(); i++) {
                try {
                URL uri = new URL();
                int wordCounts[] = new int[Words.length];
                doc = Jsoup.connect(Urls.get(i)).timeout(3000).userAgent("Mozilla").get();

                String text = doc.body().text();
                String allWords[];
                if(!text.isEmpty()){
                    allWords = text.split("\\s+");
                }else{
                    allWords = new String[0];
                }

                for(int k = 0;k<allWords.length;k++){
                    for (int j = 0; j < Words.length ; j++) {
                        if(allWords[k].equalsIgnoreCase(Words[j])){
                            wordCounts[j]++;
                        }
                    }
                }

                uri.setLink(Urls.get(i));


                HashMap<String,Integer> hashMap = new HashMap<>();
                for (int j = 0; j < Words.length ; j++) {
                    hashMap.put(Words[j],wordCounts[j]);
                }

                uri.setWords(hashMap);

                listURLs.add(uri);
                } catch (IOException e) {
                    log.info(i + " Exception in getUrlList : " + e.getMessage() );
                }
            }



        log.info("Derinlik 3 alındı");
        return listURLs;
    }


    public double getWebSiteRank(String words,String URL){ // degerin yuksek olmasi kaliteli olduğu anlamına geliyor.
        String kelimeler[] = words.split(",");
        double standardError[] = new double[3];
        double keyWordSum[] = new double[3];
        double average[] = new double[3];
        double URLQuality[] = new double[3];
        double sumOfURLQualities;

        int tekrarSayilari[][] = new int[3][kelimeler.length]; // Derinlik 3 . Anasayfa 0.index [0][1] Anasayfada geçen 1. kelimenin tekrar sayısı

        tekrarSayilari[0] = searchWords.getURLWordsCount(URL,words);

        List<String> linksInMainPage = searchWords.getLinks(URL);
        Set<String> hs = new HashSet<>();
        hs.addAll(linksInMainPage);
        linksInMainPage.clear();
        linksInMainPage.addAll(hs);
        hs.clear();

        for (int i = 0; i < linksInMainPage.size() ; i++) {
            int arrayKelimeler[] = searchWords.getURLWordsCount(linksInMainPage.get(i),words);
            for (int j = 0; j < kelimeler.length ; j++) {
                tekrarSayilari[1][j] += arrayKelimeler[j];
            }
        }

        List<String> linkInSubURLs = searchWords.getLinks(linksInMainPage);
        hs.addAll(linkInSubURLs);
        linkInSubURLs.clear();
        linkInSubURLs.addAll(hs);
        hs.clear();

        for (int i = 0; i < linkInSubURLs.size() ; i++) {
            int arrayKelimeler[] = searchWords.getURLWordsCount(linkInSubURLs.get(i),words);
            for (int j = 0; j < kelimeler.length ; j++) {
                tekrarSayilari[2][j] += arrayKelimeler[j];
            }
        }

        for(int i = 0; i<3; i++){
            for(int j = 0; j<kelimeler.length; j++){
                keyWordSum[i] += tekrarSayilari[i][j]; // now keyWordSum contains keyword sums for every URL
            }
            average[i] = keyWordSum[i] / kelimeler.length; // now average contains keyword averages for every URL
        }

        for(int i = 0; i<3; i++){ //  Calculation for part of standardError
            for(int j = 0; j<kelimeler.length; j++){
                standardError[i] += Math.pow(tekrarSayilari[i][j] - average[i],2);
            }
        }

        for(int i = 0; i<3; i++){ // URLQuality calculation
            if(standardError[i]!=0){
                standardError[i] = Math.sqrt(standardError[i] /= (kelimeler.length - 1));
                URLQuality[i] = standardError[i] / Math.pow(keyWordSum[i],2);
            }else{
                URLQuality[i] = 1/Math.pow(keyWordSum[i],2);
            }
        }

        for(int i = 0; i<3; i++){ // Double URLQuality Point if one of the keywords missing in that url so it loses its quality
            for(int j = 0; j<kelimeler.length; j++){
                if(tekrarSayilari[i][j]==0){
                    URLQuality[i] = URLQuality[i] * 2;
                }
            }
        }

        for(int i = 0; i<3; i++){
            URLQuality[i] = 1/URLQuality[i];
        }

        sumOfURLQualities = URLQuality[0] + (URLQuality[1]*0.5) + (URLQuality[2] * 0.25);
        return sumOfURLQualities;
    }

}
