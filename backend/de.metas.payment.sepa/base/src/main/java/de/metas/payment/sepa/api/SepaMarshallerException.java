package de.metas.payment.sepa.api;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
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

	/**
	 * @param message taken as it is, i.e. not parsed for "@...@" translation tokens
	 */
	public SepaMarshallerException(final ITranslatableString message)
	{
		super(message);
	}

	public SepaMarshallerException(final AdMessageKey message, final Object... params)
	{
		super(message, params);
	}
}
