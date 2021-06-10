package de.metas.banking.payment.impl;

import de.metas.banking.payment.IPaymentStringDataProvider;

public abstract class AbstractPaymentStringDataProvider implements IPaymentStringDataProvider
{
	private final PaymentString paymentString;

	public AbstractPaymentStringDataProvider(final PaymentString paymentString)
	{
		this.paymentString = paymentString;
	}

	@Override
	public PaymentString getPaymentString()
	{
		return paymentString;
	}
}
