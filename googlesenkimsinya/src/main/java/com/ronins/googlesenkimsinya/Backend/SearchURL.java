package com.ronins.googlesenkimsinya.Backend;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SearchURL {

    Logger log = Logger.getLogger(SearchURL.class.getName());
    SearchWords searchWords;

    public SearchURL(){
        searchWords = new SearchWords();
    }

    public double getWebSiteRank(String words,String URL){ // degerin yuksek olmasi kaliteli olduğu anlamına geliyor.
        String kelimeler[] = words.split(",");
        double standardError[] = new double[3];
        double keyWordSum[] = new double[3];
        double average[] = new double[3];
        double URLQuality[] = new double[3];
        double sumOfURLQualities;

        int tekrarSayilari[][] = new int[3][kelimeler.length]; // Derinlik 3 . Anasayfa 0.index [0][1] Anasayfada geçen 1. kelimenin tekrar sayısı

        for (int i = 0; i < kelimeler.length ; i++) {
           tekrarSayilari[0][i] = searchWords.getWordsCount(URL,kelimeler[i]);
        }

        List<String> linksInMainPage = searchWords.getLinks(URL);

        for (int i = 0; i < linksInMainPage.size() ; i++) {
            for (int j = 0; j < kelimeler.length ; j++) {
                tekrarSayilari[1][j] += searchWords.getWordsCount(linksInMainPage.get(i),kelimeler[j]);
            }
        }

        List<String> linkInSubURLs = searchWords.getLinks(linksInMainPage);

        for (int i = 0; i < linkInSubURLs.size() ; i++) {
            for (int j = 0; j < kelimeler.length ; j++) {
                tekrarSayilari[2][j] += searchWords.getWordsCount(linkInSubURLs.get(i),kelimeler[j]);
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
