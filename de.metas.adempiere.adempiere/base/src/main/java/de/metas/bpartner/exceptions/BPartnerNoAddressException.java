package de.metas.bpartner.exceptions;

import org.compiere.model.I_C_BPartner;

/**
 * Thrown when an location/address is required for a BPartner but not found.
 */
@SuppressWarnings("serial")
public class BPartnerNoAddressException extends BPartnerException
{
	public static final String AD_Message = "BPartnerNoAddress";

	public BPartnerNoAddressException(final I_C_BPartner bpartner)
	{
		super(AD_Message, bpartner);
	}

}
