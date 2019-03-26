package de.metas.paypalplus.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreditCard
{
	@NonNull
	String firstName;

	@NonNull
	String lastName;

	@NonNull
	String cardNumber;

	@NonNull
	Integer expirationYear;

	@NonNull
	Integer expirationMonth;

	@NonNull
	String cvv2;

	@NonNull
	String cardType;

}
