package de.metas.paypalplus.model;

import com.paypal.base.rest.PayPalRESTException;

public class PayPalPlusException extends PayPalRESTException
{
	public PayPalPlusException(String message)
	{
		super(message);
	}
}
