package com.emilytrabert.findit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.emilytrabert.findit.NearbySearchResult.PartialPlace;
import com.emilytrabert.findit.api.GoogleAPIKeyReader;
import com.google.gson.Gson;

public class Main {

	private static final Gson GSON = new Gson();

	public static void main(String[] args) throws InterruptedException {
		String key = new GoogleAPIKeyReader().getKey();
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("https://maps.googleapis.com/maps/api/place");
		WebTarget baseNearby = target.path("nearbysearch").path("json").queryParam("key", key);
		WebTarget allNearby = baseNearby.queryParam("location", "35.808579,%20-78.814778").queryParam("radius", 6500);
		Response response = allNearby.request().get();
		NearbySearchResult result = GSON.fromJson(response.readEntity(String.class), NearbySearchResult.class);
		printResults(result);
		while (result.getNextPageToken() != null) {
			Thread.sleep(5000); // avoid hammering api
			result = getNext(baseNearby, result.getNextPageToken());
			printResults(result);
		}
	}
	
	public static NearbySearchResult getNext(WebTarget baseNearby, String nextPageToken) {
		Response response = baseNearby.queryParam("pagetoken", nextPageToken).request().get();
		return GSON.fromJson(response.readEntity(String.class), NearbySearchResult.class);
	}
	
	public static void printResults(NearbySearchResult result) {
		for (PartialPlace place : result.getResults()) {
			System.out.println(String.format("Name: %s, Place_Id: %s", place.getName(), place.getPlaceId()));
		}
	}

}
