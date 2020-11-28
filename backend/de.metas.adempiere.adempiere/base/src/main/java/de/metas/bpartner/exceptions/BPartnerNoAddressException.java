package de.metas.bpartner.exceptions;

import de.metas.i18n.AdMessageKey;
import org.compiere.model.I_C_BPartner;

import javax.annotation.Nullable;

/**
 * Thrown when an location/address is required for a BPartner but not found.
 */
@SuppressWarnings("serial")
public class BPartnerNoAddressException extends BPartnerException
{
	private static final AdMessageKey MSG = AdMessageKey.of("BPartnerNoAddress");

	public BPartnerNoAddressException(@Nullable final I_C_BPartner bpartner)
	{
		super(MSG, bpartner);
	}

}
