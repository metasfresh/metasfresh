package de.metas.document.callout;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.IDocumentLocationBL;
import de.metas.document.model.IDocumentBillLocation;
import de.metas.document.model.IDocumentDeliveryLocation;
import de.metas.document.model.IDocumentLocation;

public class DocumentLocation extends CalloutEngine
{
	public void isUseBillToAddress(final ICalloutField calloutField)
	{
		if (calloutField.isRecordCopyingModeIncludingDetails())
		{
			return;
		}

		final IDocumentBillLocation docLocation = calloutField.getModel(IDocumentBillLocation.class);
		Services.get(IDocumentLocationBL.class).setBillToAddress(docLocation);
	}

	public void isUseBPartnerAddress(final ICalloutField calloutField)
	{
		if (calloutField.isRecordCopyingModeIncludingDetails())
		{
			return;
		}
		final IDocumentLocation docLocation = calloutField.getModel(IDocumentLocation.class);
		Services.get(IDocumentLocationBL.class).setBPartnerAddress(docLocation);
	}

	public void cBPartnerLocationId(final ICalloutField calloutField)
	{
		if (calloutField.isRecordCopyingModeIncludingDetails())
		{
			return;
		}

		final IDocumentLocation docLocation = calloutField.getModel(IDocumentLocation.class);
		Services.get(IDocumentLocationBL.class).setBPartnerAddress(docLocation);
	}

	public void billLocationId(final ICalloutField calloutField)
	{
		if (calloutField.isRecordCopyingModeIncludingDetails())
		{
			return;
		}

		final IDocumentBillLocation docLocation = calloutField.getModel(IDocumentBillLocation.class);
		Services.get(IDocumentLocationBL.class).setBillToAddress(docLocation);
	}

	// DropShip_Location_ID
	public void deliveryLocationId(final ICalloutField calloutField)
	{
		if (calloutField.isRecordCopyingModeIncludingDetails())
		{
			return;
		}

		final IDocumentDeliveryLocation docLocation = calloutField.getModel(IDocumentDeliveryLocation.class);
		Services.get(IDocumentLocationBL.class).setDeliveryToAddress(docLocation);
	}

	// IsDropShip, M_Warehouse_ID
	public void deliveryLocationIdForWarehouse(final ICalloutField calloutField)
	{
		final IDocumentDeliveryLocation docDeliveryLocation = calloutField.getModel(IDocumentDeliveryLocation.class);
		if (!InterfaceWrapperHelper.hasModelColumnName(docDeliveryLocation, I_C_Order.COLUMNNAME_IsUseDeliveryToAddress))
		{
			return;
		}
		Services.get(IDocumentLocationBL.class).setDeliveryToAddress(docDeliveryLocation);
	}

	public void billUserId(final ICalloutField calloutField)
	{
		final IDocumentBillLocation docLocation = calloutField.getModel(IDocumentBillLocation.class);
		if (!InterfaceWrapperHelper.hasModelColumnName(docLocation, I_C_Order.COLUMNNAME_IsUseDeliveryToAddress))
		{
			return;
		}
		Services.get(IDocumentLocationBL.class).setBillToAddress(docLocation);
	}

	public void adUserId(final ICalloutField calloutField)
	{
		final IDocumentLocation docLocation = calloutField.getModel(IDocumentLocation.class);
		if (!InterfaceWrapperHelper.hasModelColumnName(docLocation, I_C_Order.COLUMNNAME_IsUseDeliveryToAddress))
		{
			return;
		}
		Services.get(IDocumentLocationBL.class).setBPartnerAddress(docLocation);
	}
}
