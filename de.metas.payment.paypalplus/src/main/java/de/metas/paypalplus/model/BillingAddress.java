package de.metas.paypalplus.model;

import javax.annotation.Nullable;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class BillingAddress
{
	/** Street and No */
	@NonNull
	String address1;

	/** suite, apt etc */
	@Nullable
	String address2;

	@NonNull
	String postalCode;

	@NonNull
	String city;

	@Nullable
	String state;

	@NonNull
	String countryCode;
}
