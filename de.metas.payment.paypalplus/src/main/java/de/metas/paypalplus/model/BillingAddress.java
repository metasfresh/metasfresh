package de.metas.paypalplus.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

@Value
@Getter
@Builder
public class BillingAddress
{
	@NonNull
	String city;

	@NonNull
	String countryCode;

	@NonNull
	String address;

	@NonNull
	String postalCode;

	@NonNull
	String state;
}
