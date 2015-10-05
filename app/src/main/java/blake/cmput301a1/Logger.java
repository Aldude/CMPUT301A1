package blake.cmput301a1;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class Logger {

    // Save locations
    public final String reactionSave = "reactionTimes.sav";
    public final String buzzerSave = "buzzerTallies.sav";

    private Activity owner;
    private Gson gson;
    private ArrayList<Long> reactionTimes;
    private ArrayList<Long> buzzes;

    public Logger(Activity caller) {
        owner = caller;
        gson = new Gson();
    }

    public void addReaction(final long time) {
        try {
            FileInputStream fis = owner.openFileInput(reactionSave);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            // Following line based on https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
            Type listType = new TypeToken<ArrayList<Long>>() {}.getType();
            reactionTimes = gson.fromJson(in, listType);
        } catch(FileNotFoundException e) {
            reactionTimes = new ArrayList<>(1);
        }

        try {
            reactionTimes.add(0, time);
        } catch(NullPointerException e) {
            reactionTimes = new ArrayList<>(1);
            reactionTimes.add(time);
        }

        try {
            FileOutputStream fos = owner.openFileOutput(reactionSave, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            gson.toJson(reactionTimes, writer);
            writer.flush();
            fos.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMultiplayerBuzz(int players, int player) {
        try {
            FileInputStream fis = owner.openFileInput(reactionSave);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            // Following line based on https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
            Type listType = new TypeToken<ArrayList<Long>>() {}.getType();
            buzzes = gson.fromJson(in, listType);
        } catch(FileNotFoundException e) {
            buzzes = new ArrayList<Long>(9);
        }

        if(players == 2) {
            buzzes.set(player - 1, buzzes.get(player - 1) + 1);
        } else if(players == 3) {
            buzzes.set(player + 1, buzzes.get(player + 1) + 1);
        } else {
            buzzes.set(player + 4, buzzes.get(player + 4) + 1);
        }

        try {
            FileOutputStream fos = owner.openFileOutput(reactionSave, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            gson.toJson(buzzes, writer);
            writer.flush();
            fos.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public long[] reactionStats() {
        try {
            FileInputStream fis = owner.openFileInput(reactionSave);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            // Following line based on https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
            Type listType = new TypeToken<ArrayList<Long>>() {}.getType();
            reactionTimes = gson.fromJson(in, listType);
        } catch(FileNotFoundException e) {
            reactionTimes = new ArrayList<Long>();
        }

        long sum_all = 0;
        long sum_10 = 0;
        long sum_100 = 0;
        long count = 0;

        for(Long val : reactionTimes) {
            count++;
            sum_all += val;
            if(count <= 10) { sum_10 += val; }
            if(count <= 100) { sum_100 += val; }
        }

        long avg_all = sum_all / reactionTimes.size();
        long avg_10 = sum_10 / (reactionTimes.size() < 10 ? reactionTimes.size() : 10);
        long avg_100 = sum_100 / (reactionTimes.size() < 100 ? reactionTimes.size() : 100);

        ArrayList<Long> sortedTimes = new ArrayList<Long>(reactionTimes);
        Collections.sort(sortedTimes);
        long med_all = sortedTimes.get(sortedTimes.size() / 2);
        long max_all = sortedTimes.get(sortedTimes.size() - 1);
        long min_all = sortedTimes.get(0);

        long med_10, max_10, min_10;
        if(reactionTimes.size() >= 10) {
            sortedTimes = new ArrayList<Long>(reactionTimes.subList(0, 9));
            Collections.sort(sortedTimes);
            med_10 = sortedTimes.get(5);
            max_10 = sortedTimes.get(sortedTimes.size() - 1);
            min_10 = sortedTimes.get(0);
        } else {
            med_10 = med_all;
            max_10 = max_all;
            min_10 = min_all;
        }

        long med_100, max_100, min_100;
        if(reactionTimes.size() >= 100) {
            sortedTimes = new ArrayList<Long>(reactionTimes.subList(0, 99));
            Collections.sort(sortedTimes);
            med_100 = sortedTimes.get(50);
            max_100 = sortedTimes.get(sortedTimes.size() - 1);
            min_100 = sortedTimes.get(0);
        } else {
            med_100 = med_all;
            max_100 = max_all;
            min_100 = min_all;
        }

        long[] rv;
        rv = new long[]{max_all, min_all, avg_all, med_all, max_10, min_10, avg_10, med_10, max_100, min_100, avg_100, med_100};

        return rv;
    }

}
