package de.metas.bpartner.exceptions;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;

/**
 * Thrown when an exception related to a BPartner happened.
 */
@SuppressWarnings("serial")
public abstract class BPartnerException extends AdempiereException
{
	BPartnerException(final String ad_message, final I_C_BPartner bpartner)
	{
		super(buildMsg(ad_message, bpartner));
	}

	private static final String buildMsg(final String ad_message, final I_C_BPartner bpartner)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("@").append(ad_message).append("@");

		sb.append(" - @C_BPartner_ID@: ");
		if (bpartner == null)
		{
			sb.append("none");
		}
		else
		{
			sb.append(bpartner.getValue()).append("_").append(bpartner.getName());
			final int bpartnerId = bpartner.getC_BPartner_ID();
			sb.append(" (ID=").append(bpartnerId <= 0 ? "new" : bpartnerId).append(")");
		}

		return sb.toString();
	}
}
