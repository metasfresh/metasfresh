package de.metas.bpartner.exceptions;

import org.compiere.model.I_C_BPartner;

/**
 * Thrown when Ship To Address is required for a BPartner but not found.
 */
@SuppressWarnings("serial")
public class BPartnerNoShipToAddressException extends BPartnerException
{
	public static final String AD_Message = "BPartnerNoShipToAddress";

	public BPartnerNoShipToAddressException(final I_C_BPartner bpartner)
	{
		super(AD_Message, bpartner);
	}
}
