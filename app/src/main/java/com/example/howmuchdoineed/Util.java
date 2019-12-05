package com.example.howmuchdoineed;


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

class CsvReadWrite {
    private String baseDir;

    public CsvReadWrite(String directory) {
        baseDir = directory;
    }

    public void writeCsvToStorage(ArrayList<String[]> arrayToWrite) {
        String[] courseName = arrayToWrite.get(0);
        String[] assignmentNames = arrayToWrite.get(1);
        String[] assignmentWeights = arrayToWrite.get(2);
        String[] grades = arrayToWrite.get(3);

        try {
            System.out.println(baseDir + "/" + courseName[0] + ".csv");
            CSVWriter writer = new CSVWriter(new FileWriter(baseDir + "/" + courseName[0] + ".csv"));

            writer.writeNext(courseName);
            writer.writeNext(assignmentNames);
            writer.writeNext(assignmentWeights);
            writer.writeNext(grades);

            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String[]> readCsvFromStorage(String courseName) {
        ArrayList<String[]> arrList = new ArrayList<>();
        try {
            System.out.println(baseDir + "/" + courseName + ".csv");
            CSVReader reader = new CSVReader(new FileReader(baseDir + "/" + courseName + ".csv"));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                arrList.add(nextLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrList;
    }
}


class ScrollViewWithMaxHeight extends ScrollView {

    public static int WITHOUT_MAX_HEIGHT_VALUE = -1;

    private int maxHeight = WITHOUT_MAX_HEIGHT_VALUE;

    public ScrollViewWithMaxHeight(Context context) {
        super(context);
    }

    public ScrollViewWithMaxHeight(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewWithMaxHeight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            if (maxHeight != WITHOUT_MAX_HEIGHT_VALUE
                    && heightSize > maxHeight) {
                heightSize = maxHeight;
            }
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST);
            getLayoutParams().height = heightSize;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }
}

