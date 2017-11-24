package com.ronins.googlesenkimsinya.Backend;

public class Pages {

    private String URL;
    private int rank;
    private int wordsCount[];

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int[] getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(int[] wordsCount) {
        this.wordsCount = wordsCount;
    }
}
