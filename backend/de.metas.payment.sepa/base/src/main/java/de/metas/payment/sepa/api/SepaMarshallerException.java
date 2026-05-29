package de.metas.payment.sepa.api;

import de.metas.i18n.AdMessageKey;
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

	public SepaMarshallerException(AdMessageKey message, final Object... params)
	{
		super(message,params);
	}
}
