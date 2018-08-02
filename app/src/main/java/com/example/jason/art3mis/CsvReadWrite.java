package com.example.jason.art3mis;

import android.view.View;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.Arrays;

public class CsvReadWrite {
	private String baseDir;
	
	public CsvReadWrite(String directory) {
		baseDir = directory;
	}
	
	public void writeCsvToStorage(String[][] arrayToWrite) {
		String[] courseName = getArrayAtIndex(arrayToWrite, 0);
		String[] assignmentNames = getArrayAtIndex(arrayToWrite, 1);
		String[] assignmentWeights = getArrayAtIndex(arrayToWrite, 2);
		String[] grades = getArrayAtIndex(arrayToWrite, 3);
		
		try {
			System.out.println(baseDir + "/" + courseName[0] + ".csv");
//			CSVWriter writer = new CSVWriter(new FileWriter(baseDir + "/" + courseName[0] + ".csv"));
//
//			writer.writeNext(courseName);
//			writer.writeNext(assignmentNames);
//			writer.writeNext(assignmentWeights);
//			writer.writeNext(grades);
//
//			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String[] getArrayAtIndex(String[][] bigArray, int index) {
		String[] smallArray = new String[bigArray[index].length];
		for (int i = 0; i < bigArray[index].length; i++) {
			smallArray[i] = bigArray[index][i];
		}
		return smallArray;
	}

	public void writeTesting(View v) {
		String[][] testArrays = {
			{"AST121"},
			{"Midterm", "Final"},
			{"29", "71"},
			{"95", "100"},
		};
//		String[][] testArrays = getSyllabusArray();

		String line1 = Arrays.toString(getArrayAtIndex(testArrays, 0));
		String line2 = Arrays.toString(getArrayAtIndex(testArrays, 1));
		String line3 = Arrays.toString(getArrayAtIndex(testArrays, 2));
		String line4 = Arrays.toString(getArrayAtIndex(testArrays, 3));

		System.out.println(line1);
		System.out.println(line2);
		System.out.println(line3);
		System.out.println(line4);

		writeCsvToStorage(testArrays);
	}

	public void read(View v) {
		String text = "";
		try {

			CSVReader reader = new CSVReader(new FileReader(baseDir + "/testingCsvReadWrite.csv"));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				System.out.println(Arrays.toString(nextLine));
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
