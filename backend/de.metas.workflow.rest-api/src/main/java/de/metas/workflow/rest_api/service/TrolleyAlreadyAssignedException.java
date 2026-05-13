package de.metas.workflow.rest_api.service;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.qrcode.LocatorQRCode;

// Stub created in Task 3 (RED phase). Real factories + AD_Message wiring land in Task 4.
public class TrolleyAlreadyAssignedException extends AdempiereException
{
	public TrolleyAlreadyAssignedException(final LocatorQRCode locatorQRCode)
	{
		super("TODO Task 4: trolley " + locatorQRCode + " already assigned");
	}
}
