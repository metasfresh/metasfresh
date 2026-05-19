package de.metas.workflow.rest_api.service;

import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.qrcode.LocatorQRCode;

public class TrolleyAlreadyAssignedException extends AdempiereException
{
	private static final long serialVersionUID = 1L;

	private static final AdMessageKey MSG_NAMED = AdMessageKey.of("WFRestAPI_TrolleyAlreadyAssignedTo");

	public static TrolleyAlreadyAssignedException forNamedConflict(@NonNull final String holderDisplayName, @NonNull final LocatorQRCode locatorQRCode)
	{
		return new TrolleyAlreadyAssignedException(holderDisplayName, locatorQRCode);
	}

	private TrolleyAlreadyAssignedException(@NonNull final String holderDisplayName, @NonNull final LocatorQRCode locatorQRCode)
	{
		super(MSG_NAMED, holderDisplayName);
		setParameter("locatorQRCode", locatorQRCode);
	}
}
