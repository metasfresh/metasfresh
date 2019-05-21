package de.metas.paypalplus.model;

import java.time.YearMonth;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreditCard
{
	@NonNull
	String cardType;

	@NonNull
	String cardNumber;

	@NonNull
	YearMonth expirationDate;

	@NonNull
	String firstName;

	@NonNull
	String lastName;

	@NonNull
	String cvv2;
}
