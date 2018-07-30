package com.emilytrabert.findit;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceDetailsResult {

	private String status;

	@SerializedName("result")
	private Place place;
}
