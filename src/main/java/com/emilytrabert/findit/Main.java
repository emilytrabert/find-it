package com.emilytrabert.findit;

import com.emilytrabert.findit.NearbySearchResult.PartialPlace;
import com.emilytrabert.findit.gateway.GoogleMapAPIGateway;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		GoogleMapAPIGateway gateway = new GoogleMapAPIGateway();
		NearbySearchResult result = gateway.getNearby(35.808579, -78.814778, 6500);
		printResults(result);
		while (result.getNextPageToken() != null) {
			Thread.sleep(5000); // avoid hammering api
			result = gateway.getNextNearby(result.getNextPageToken()).get();
			printResults(result);
		}
	}

	public static void printResults(NearbySearchResult result) {
		for (PartialPlace place : result.getResults()) {
			System.out.println(String.format("Name: %s, Place_Id: %s", place.getName(), place.getPlaceId()));
		}
	}

}
