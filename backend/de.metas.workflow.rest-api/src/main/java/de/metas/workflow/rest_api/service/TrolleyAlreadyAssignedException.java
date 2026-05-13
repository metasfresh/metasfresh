package de.metas.workflow.rest_api.service;

import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.qrcode.LocatorQRCode;

public class TrolleyAlreadyAssignedException extends AdempiereException
{
	private static final long serialVersionUID = 1L;

	private static final AdMessageKey MSG_GENERIC = AdMessageKey.of("WFRestAPI_TrolleyAlreadyAssigned");
	private static final AdMessageKey MSG_NAMED = AdMessageKey.of("WFRestAPI_TrolleyAlreadyAssignedTo");

	public static TrolleyAlreadyAssignedException forGenericConflict(@NonNull final LocatorQRCode locatorQRCode)
	{
		return new TrolleyAlreadyAssignedException(MSG_GENERIC, locatorQRCode);
	}

	public static TrolleyAlreadyAssignedException forNamedConflict(@NonNull final String holderDisplayName, @NonNull final LocatorQRCode locatorQRCode)
	{
		return new TrolleyAlreadyAssignedException(MSG_NAMED, holderDisplayName, locatorQRCode);
	}

	private TrolleyAlreadyAssignedException(@NonNull final AdMessageKey key, @NonNull final LocatorQRCode locatorQRCode)
	{
		super(key);
		setParameter("locatorQRCode", locatorQRCode);
	}

	private TrolleyAlreadyAssignedException(@NonNull final AdMessageKey key, @NonNull final String holderDisplayName, @NonNull final LocatorQRCode locatorQRCode)
	{
		super(key, holderDisplayName);
		setParameter("locatorQRCode", locatorQRCode);
	}
}
