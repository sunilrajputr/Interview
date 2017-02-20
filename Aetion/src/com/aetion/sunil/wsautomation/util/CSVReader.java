package com.aetion.sunil.wsautomation.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {

	public static ArrayList<Object[]> readFile(String filename){

		String csvFile = filename;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		ArrayList<Object[]> fileData = new ArrayList();

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				System.out.print("File info --> ");
				Object[] userdata = line.split(cvsSplitBy);
				for( int i = 0; i <userdata.length ; i++ ){
					System.out.print( userdata[i] + "  " );

				}
				fileData.add(userdata);

				System.out.println("");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return fileData;
	}
}
