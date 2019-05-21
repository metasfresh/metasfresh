package de.metas.paypalplus.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class PaymentAmount
{
	@NonNull
	String currency;

	@NonNull
	BigDecimal subTotal;
	@NonNull
	BigDecimal shippingTax;
	@NonNull
	BigDecimal tax;

	@NonNull
	BigDecimal total;

	@Builder
	private PaymentAmount(
			@NonNull final String currency,
			@NonNull final BigDecimal subTotal,
			@NonNull final BigDecimal shippingTax,
			@NonNull final BigDecimal tax)
	{
		this.currency = currency;

		this.subTotal = subTotal;
		this.shippingTax = shippingTax;
		this.tax = tax;

		this.total = subTotal.add(shippingTax).add(tax);
	}
}
