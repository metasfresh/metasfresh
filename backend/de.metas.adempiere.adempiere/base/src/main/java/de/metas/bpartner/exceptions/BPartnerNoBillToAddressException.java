package de.metas.bpartner.exceptions;

import de.metas.i18n.AdMessageKey;
import org.compiere.model.I_C_BPartner;

import javax.annotation.Nullable;

/**
 * Thrown when Bill To Address is required for a BPartner but not found.
 */
@SuppressWarnings("serial")
public class BPartnerNoBillToAddressException extends BPartnerException
{
	private static final AdMessageKey MSG = AdMessageKey.of("BPartnerNoBillToAddress");

	public BPartnerNoBillToAddressException(@Nullable final I_C_BPartner bpartner)
	{
		super(MSG, bpartner);
	}

	public BPartnerNoBillToAddressException(@Nullable final String bpartnerName)
	{
		super(MSG, bpartnerName);
	}
}
