package com.carens.news;

import java.util.ArrayList;
import java.util.HashMap;

public class Ipsum {
    static String[] headLines = {
            "Lord Rangga Sunda Empire’s Viral Speech Talking About World War III, Netizens: Predictions Never Go Wrong!",
            "Bitcoin price steady around $43,000"
    };
    static String[] article = {
            "Russian military operations in Ukraine shocked the world community. Many feel that tensions from the two countries sparked World War 3. World War 3 or World War 3 also echoed on social media Twitter. In the midst of the chaos between Russia and Ukraine, Lord Rangga, the leader of the Sunda Empire group, is now busy being a public conversation.",
            "Bitcoin ran its stretch of gains to three days, before pulling back on Thursday morning.\n" +
            "\n" +
            "Through Wednesday, Bitcoin's price rose more than 17% over a three-day period, as investors looked at the cryptocurrency as a safe haven.\n" +
            "\n" +
            "Bitcoin was also up six of the last seven days, but remains down more than 5% year-to-date."
    };

    static ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();

    public static void addFirst(){
        for (int i=0;i<headLines.length;i++)
        {
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("headLines",headLines[i]);
            hashMap.put("article",article[i]);
            arrayList.add(hashMap);
        }
    }

    public static ArrayList<HashMap<String, String>> getArray(){
        if(arrayList.isEmpty()){
            addFirst();
        }
        return arrayList;
    }

    public static void addArray(String newTitle, String newArticle){
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("headLines",newTitle);
        hashMap.put("article",newArticle);
        arrayList.add(hashMap);
    }
}
