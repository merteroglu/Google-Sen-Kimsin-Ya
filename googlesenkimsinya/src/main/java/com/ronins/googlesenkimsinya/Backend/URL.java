package com.ronins.googlesenkimsinya.Backend;

import java.util.List;
import java.util.Map;


public class URL {
    double puan;
    int tekrarSayilari[][];
    List<String> linksInMainPage;
    List<String> linkInSubURLs;
    int sira;

    public int getSira() {
        return sira;
    }

    public void setSira(int sira) {
        this.sira = sira;
    }

    public double getPuan() {
        return puan;
    }

    public void setPuan(double puan) {
        this.puan = puan;
    }

    public int[][] getTekrarSayilari() {
        return tekrarSayilari;
    }

    public void setTekrarSayilari(int[][] tekrarSayilari) {
        this.tekrarSayilari = tekrarSayilari;
    }

    public List<String> getLinksInMainPage() {
        return linksInMainPage;
    }

    public void setLinksInMainPage(List<String> linksInMainPage) {
        this.linksInMainPage = linksInMainPage;
    }

    public List<String> getLinkInSubURLs() {
        return linkInSubURLs;
    }

    public void setLinkInSubURLs(List<String> linkInSubURLs) {
        this.linkInSubURLs = linkInSubURLs;
    }
}
