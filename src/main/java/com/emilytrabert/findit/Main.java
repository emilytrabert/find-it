package com.emilytrabert.findit;

import java.util.ArrayList;
import java.util.List;

import com.emilytrabert.findit.NearbySearchResult.PartialPlace;
import com.emilytrabert.findit.gateway.GoogleMapAPIGateway;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		GoogleMapAPIGateway gateway = new GoogleMapAPIGateway();
		NearbySearchResult result = gateway.getNearby(35.808579, -78.814778, 6500);
		List<PartialPlace> partialPlaces = new ArrayList<>();
		partialPlaces.addAll(result.getResults());
		//printPartialPlaceResults(result);
		while (result.getNextPageToken() != null) {
			Thread.sleep(5000); // avoid hammering api
			result = gateway.getNextNearby(result.getNextPageToken()).get();
			partialPlaces.addAll(result.getResults());
			//printPartialPlaceResults(result);
		}
		List<Place> places = new ArrayList<>();
		for (PartialPlace partialPlace : partialPlaces) {
			Thread.sleep(1000); // avoid hammering api
			PlaceDetailsResult place = gateway.getPlaceDetails(partialPlace.getPlaceId());
			places.add(place.getPlace());
			System.out.println(String.format("Name: %s, Phone: %s, Address: %s", place.getPlace().getName(), place.getPlace().getPhoneNumber(), place.getPlace().getAddress()));
		}
	}

	public static void printPartialPlaceResults(NearbySearchResult result) {
		for (PartialPlace place : result.getResults()) {
			System.out.println(String.format("Name: %s, Place_Id: %s", place.getName(), place.getPlaceId()));
		}
	}

}
