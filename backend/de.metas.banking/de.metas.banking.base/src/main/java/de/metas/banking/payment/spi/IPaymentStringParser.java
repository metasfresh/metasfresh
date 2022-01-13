package de.metas.banking.payment.spi;

import de.metas.banking.payment.PaymentString;

public interface IPaymentStringParser
{
	/**
	 * @param paymentText
	 * @return {@link IPaymentString} implementation filled with relevant data from the <code>paymentString</code>
	 *
	 * @throws IndexOutOfBoundsException if the paymentText has invalid length
	 */
	PaymentString parse(String paymentText) throws IndexOutOfBoundsException;
}
