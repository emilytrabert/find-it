package com.emilytrabert.findit.api;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GoogleAPIKeyReader {

	private String fileLocation = "google-api-key";
	private String key;
	
	public String getKey() {
		if (key == null) {
			try (BufferedReader reader = new BufferedReader(new FileReader(fileLocation))) {
				key = reader.readLine();
			} catch (IOException e) {
				throw new KeyNotFoundException("Error retrieving key", e);
			}
		}
		return key;
	}
}
