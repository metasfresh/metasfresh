/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.shippingnotification.location.adapter;

import de.metas.document.location.DocumentLocation;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.RecordBasedLocationAdapter;
import de.metas.document.location.RenderedAddressAndCapturedLocation;
import de.metas.document.location.adapter.IDocumentShipFromLocationAdapter;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.Optional;

public class ShippingNotificationShipFromLocationAdapter
		implements IDocumentShipFromLocationAdapter, RecordBasedLocationAdapter<ShippingNotificationShipFromLocationAdapter>
{
	private final I_M_Shipping_Notification delegate;

	ShippingNotificationShipFromLocationAdapter(@NonNull final I_M_Shipping_Notification delegate)
	{
		this.delegate = delegate;
	}

	@Override
	public int getShipFrom_Partner_ID()
	{
		return delegate.getShipFrom_Partner_ID();
	}

	@Override
	public void setShipFrom_Partner_ID(final int shipFrom_Partner_ID)
	{
		delegate.setShipFrom_Partner_ID(shipFrom_Partner_ID);
	}

	@Override
	public int getShipFrom_Location_ID()
	{
		return delegate.getShipFrom_Location_ID();
	}

	@Override
	public void setShipFrom_Location_ID(final int shipFrom_Location_ID)
	{
		delegate.setShipFrom_Location_ID(shipFrom_Location_ID);
	}

	@Override
	public int getShipFrom_Location_Value_ID()
	{
		return delegate.getShipFrom_Location_Value_ID();
	}

	@Override
	public void setShipFrom_Location_Value_ID(final int shipFrom_Location_Value_ID)
	{
		delegate.setShipFrom_Location_Value_ID(shipFrom_Location_Value_ID);
	}

	@Override
	public int getShipFrom_User_ID()
	{
		return delegate.getShipFrom_User_ID();
	}

	@Override
	public void setShipFrom_User_ID(final int shipFrom_User_ID)
	{
		delegate.setShipFrom_User_ID(shipFrom_User_ID);
	}

	@Override
	public String getShipFromAddress()
	{
		return delegate.getShipFromAddress();
	}

	@Override
	public void setShipFromAddress(final String address)
	{
		delegate.setShipFromAddress(address);
	}

	@Override
	public void setRenderedAddressAndCapturedLocation(final @NonNull RenderedAddressAndCapturedLocation from)
	{
		IDocumentShipFromLocationAdapter.super.setRenderedAddressAndCapturedLocation(from);
	}

	@Override
	public I_M_Shipping_Notification getWrappedRecord()
	{
		return delegate;
	}

	@Override
	public Optional<DocumentLocation> toPlainDocumentLocation(final IDocumentLocationBL documentLocationBL)
	{
		return documentLocationBL.toPlainDocumentLocation(this);
	}

	@Override
	public ShippingNotificationShipFromLocationAdapter toOldValues()
	{
		InterfaceWrapperHelper.assertNotOldValues(delegate);
		return new ShippingNotificationShipFromLocationAdapter(InterfaceWrapperHelper.createOld(delegate, I_M_Shipping_Notification.class));
	}
}
