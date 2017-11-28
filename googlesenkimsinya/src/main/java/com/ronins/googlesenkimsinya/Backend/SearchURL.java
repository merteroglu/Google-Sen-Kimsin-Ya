package com.ronins.googlesenkimsinya.Backend;

import java.util.List;
import java.util.logging.Logger;

public class SearchURL {

    Logger log = Logger.getLogger(SearchURL.class.getName());
    SearchWords searchWords;

    SearchURL(){
        searchWords = new SearchWords();
    }

    public double getWebSiteRank(String words,String URL){
        String kelimeler[] = words.split(",");

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









        return 0;
    }







}
