package de.metas.paypalplus.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

@Value
@Getter
@Builder
public class PaymentDetails
{
	@NonNull
	String shippingTax;

	@NonNull
	String subTotal;

	@NonNull
	String tax;
}
