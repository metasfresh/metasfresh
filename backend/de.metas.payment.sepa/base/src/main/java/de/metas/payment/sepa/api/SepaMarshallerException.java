package de.metas.payment.sepa.api;

import org.adempiere.exceptions.AdempiereException;

@SuppressWarnings("serial")
public class SepaMarshallerException extends AdempiereException
{
	public SepaMarshallerException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public SepaMarshallerException(String message)
	{
		super(message);
	}
}
