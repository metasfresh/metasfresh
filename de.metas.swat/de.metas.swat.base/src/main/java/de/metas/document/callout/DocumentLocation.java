package de.metas.document.callout;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.IDocumentLocationBL;
import de.metas.document.model.IDocumentBillLocation;
import de.metas.document.model.IDocumentDeliveryLocation;
import de.metas.document.model.IDocumentLocation;

public class DocumentLocation extends CalloutEngine
{
	public String isUseBillToAddress(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		if (mTab.getTableModel().isCopyWithDetails())
		{
			return NO_ERROR;
		}

		final IDocumentBillLocation docLocation = InterfaceWrapperHelper.create(mTab, IDocumentBillLocation.class);
		Services.get(IDocumentLocationBL.class).setBillToAddress(docLocation);

		return NO_ERROR;
	}

	public String isUseBPartnerAddress(final Properties ctx,
			final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
	{

		if (mTab.getTableModel().isCopyWithDetails())
		{
			return NO_ERROR;
		}
		final IDocumentLocation docLocation = InterfaceWrapperHelper.create(mTab, IDocumentLocation.class);
		Services.get(IDocumentLocationBL.class).setBPartnerAddress(docLocation);

		return NO_ERROR;
	}

	public String cBPartnerLocationId(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		if (mTab.getTableModel().isCopyWithDetails())
		{
			return NO_ERROR;
		}

		final IDocumentLocation docLocation = InterfaceWrapperHelper.create(mTab, IDocumentLocation.class);
		Services.get(IDocumentLocationBL.class).setBPartnerAddress(docLocation);

		return NO_ERROR;
	}

	public String billLocationId(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		if (mTab.getTableModel().isCopyWithDetails())
		{
			return NO_ERROR;
		}

		final IDocumentBillLocation docLocation = InterfaceWrapperHelper.create(mTab, IDocumentBillLocation.class);
		Services.get(IDocumentLocationBL.class).setBillToAddress(docLocation);

		return NO_ERROR;
	}

	// DropShip_Location_ID
	public String deliveryLocationId(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		if (mTab.getTableModel().isCopyWithDetails())
		{
			return NO_ERROR;
		}

		final IDocumentDeliveryLocation docLocation = InterfaceWrapperHelper.create(mTab, IDocumentDeliveryLocation.class);
		Services.get(IDocumentLocationBL.class).setDeliveryToAddress(docLocation);

		return NO_ERROR;
	}

	// IsDropShip, M_Warehouse_ID
	public String deliveryLocationIdForWarehouse(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		if (mTab.getValue(I_C_Order.COLUMNNAME_IsUseDeliveryToAddress) == null)
		{
			return NO_ERROR;
		}

		final IDocumentDeliveryLocation docDeliveryLocation = InterfaceWrapperHelper.create(mTab, IDocumentDeliveryLocation.class);
		Services.get(IDocumentLocationBL.class).setDeliveryToAddress(docDeliveryLocation);

		return NO_ERROR;
	}

	public String billUserId(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		if (mTab.getValue(I_C_Order.COLUMNNAME_IsUseDeliveryToAddress) == null)
		{
			return NO_ERROR;
		}

		final IDocumentBillLocation docLocation = InterfaceWrapperHelper.create(mTab, IDocumentBillLocation.class);
		Services.get(IDocumentLocationBL.class).setBillToAddress(docLocation);

		return NO_ERROR;
	}

	public String adUserId(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		if (mTab.getValue(I_C_Order.COLUMNNAME_IsUseDeliveryToAddress) == null)
		{
			return NO_ERROR;
		}

		final IDocumentLocation docLocation = InterfaceWrapperHelper.create(mTab, IDocumentLocation.class);
		Services.get(IDocumentLocationBL.class).setBPartnerAddress(docLocation);

		return NO_ERROR;
	}
}
