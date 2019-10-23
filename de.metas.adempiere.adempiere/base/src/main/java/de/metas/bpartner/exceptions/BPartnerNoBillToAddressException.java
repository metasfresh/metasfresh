package de.metas.bpartner.exceptions;

import org.compiere.model.I_C_BPartner;

/**
 * Thrown when Bill To Address is required for a BPartner but not found.
 */
@SuppressWarnings("serial")
public class BPartnerNoBillToAddressException extends BPartnerException
{
	public static final String AD_Message = "BPartnerNoBillToAddress";

	public BPartnerNoBillToAddressException(final I_C_BPartner bpartner)
	{
		super(AD_Message, bpartner);
	}
}
