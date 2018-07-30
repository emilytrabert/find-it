package com.emilytrabert.findit;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Place {

	@SerializedName("place_id")
	private String placeId;
	private String name;
	@SerializedName("formatted_phone_number")
	private String phoneNumber;
	@SerializedName("formatted_address")
	private String address;
}
