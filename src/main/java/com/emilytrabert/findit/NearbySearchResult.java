package com.emilytrabert.findit;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NearbySearchResult {

	@SerializedName("next_page_token")
	private String nextPageToken;
	private List<PartialPlace> results;
	
	@Getter
	@Setter
	class PartialPlace {
		private String name;
		@SerializedName("place_id")
		private String placeId;
	}
}
