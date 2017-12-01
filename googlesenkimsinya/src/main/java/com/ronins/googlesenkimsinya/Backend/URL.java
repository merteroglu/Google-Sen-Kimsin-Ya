package com.ronins.googlesenkimsinya.Backend;

import java.util.List;
import java.util.Map;


public class URL {
    String link;
    int rank;
    double puan;
    List<String> subURLs;
    List<URL> subURLList;
    Map<String,Integer> words;

    public List<URL> getSubURLList() {
        return subURLList;
    }

    public void setSubURLList(List<URL> subURLList) {
        this.subURLList = subURLList;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getPuan() {
        return puan;
    }

    public void setPuan(double puan) {
        this.puan = puan;
    }

    public List<String> getSubURLs() {
        return subURLs;
    }

    public void setSubURLs(List<String> subURLs) {
        this.subURLs = subURLs;
    }

    public Map<String, Integer> getWords() {
        return words;
    }

    public void setWords(Map<String, Integer> words) {
        this.words = words;
    }
}
