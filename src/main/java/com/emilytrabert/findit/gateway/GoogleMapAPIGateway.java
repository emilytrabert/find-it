package com.emilytrabert.findit.gateway;

import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.emilytrabert.findit.NearbySearchResult;
import com.emilytrabert.findit.PlaceDetailsResult;
import com.emilytrabert.findit.api.GoogleAPIKeyReader;
import com.google.gson.Gson;

public class GoogleMapAPIGateway {
	
	private static final Gson GSON = new Gson();

	private final WebTarget baseNearbyWebTarget;
	private final WebTarget basePlaceDetailsWebTarget;
	
	public GoogleMapAPIGateway() {
		String key = new GoogleAPIKeyReader().getKey();
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("https://maps.googleapis.com/maps/api/place");
		baseNearbyWebTarget = target.path("nearbysearch").path("json").queryParam("key", key);
		basePlaceDetailsWebTarget = target.path("details").path("json").queryParam("key", key);
	}

	public NearbySearchResult getNearby(double lon, double lat, int radius) {
		WebTarget allNearby = baseNearbyWebTarget.queryParam("location", String.format("%f,%f", lon, lat)).queryParam("radius", radius);
		Response response = allNearby.request().get();
		return GSON.fromJson(response.readEntity(String.class), NearbySearchResult.class);
	}
	
	public Optional<NearbySearchResult> getNextNearby(String nextPageToken) {
		if (nextPageToken == null) {
			return Optional.empty();
		}
		Response response = baseNearbyWebTarget.queryParam("pagetoken", nextPageToken).request().get();
		return Optional.of(GSON.fromJson(response.readEntity(String.class), NearbySearchResult.class));
	}

	public PlaceDetailsResult getPlaceDetails(String placeId) {
		WebTarget placeDetails = basePlaceDetailsWebTarget.queryParam("placeid", placeId);
		Response response = placeDetails.request().get();
		return GSON.fromJson(response.readEntity(String.class), PlaceDetailsResult.class);
	}
}
