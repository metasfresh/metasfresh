/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.document.location.DocumentLocation;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.RecordBasedLocationAdapter;
import de.metas.document.location.RenderedAddressAndCapturedLocation;
import de.metas.document.location.adapter.IDocumentLocationAdapter;
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;

import javax.annotation.Nullable;
import java.util.Optional;

@ToString
public class ShippingNotificationMainLocationAdapter
		implements IDocumentLocationAdapter, RecordBasedLocationAdapter<ShippingNotificationMainLocationAdapter>
{
	private final I_M_Shipping_Notification delegate;

	ShippingNotificationMainLocationAdapter(@NonNull final I_M_Shipping_Notification delegate)
	{
		this.delegate = delegate;
	}

	@Override
	public int getC_BPartner_ID()
	{
		return delegate.getC_BPartner_ID();
	}

	@Override
	public void setC_BPartner_ID(final int C_BPartner_ID)
	{
		delegate.setC_BPartner_ID(C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		return delegate.getC_BPartner_Location_ID();
	}

	@Override
	public void setC_BPartner_Location_ID(final int C_BPartner_Location_ID)
	{
		delegate.setC_BPartner_Location_ID(C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_Value_ID()
	{
		return delegate.getC_BPartner_Location_Value_ID();
	}

	@Override
	public void setC_BPartner_Location_Value_ID(final int C_BPartner_Location_Value_ID)
	{
		delegate.setC_BPartner_Location_Value_ID(C_BPartner_Location_Value_ID);
	}

	@Override
	public int getAD_User_ID()
	{
		return delegate.getAD_User_ID();
	}

	@Override
	public void setAD_User_ID(final int AD_User_ID)
	{
		delegate.setAD_User_ID(AD_User_ID);
	}

	@Override
	public String getBPartnerAddress()
	{
		return delegate.getBPartnerAddress();
	}

	@Override
	public void setBPartnerAddress(String address)
	{
		delegate.setBPartnerAddress(address);
	}

	@Override
	public void setRenderedAddressAndCapturedLocation(final @NonNull RenderedAddressAndCapturedLocation from)
	{
		IDocumentLocationAdapter.super.setRenderedAddressAndCapturedLocation(from);
	}

	public void setFrom(@NonNull final I_M_Shipping_Notification from)
	{
		setFrom(ShippingNotificationDocumentLocationAdapterFactory.locationAdapter(from).toDocumentLocation());
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
	public ShippingNotificationMainLocationAdapter toOldValues()
	{
		InterfaceWrapperHelper.assertNotOldValues(delegate);
		return new ShippingNotificationMainLocationAdapter(InterfaceWrapperHelper.createOld(delegate, I_M_Shipping_Notification.class));
	}

	public void setLocationAndResetRenderedAddress(@Nullable final BPartnerLocationAndCaptureId from)
	{
		setC_BPartner_Location_ID(from != null ? from.getBPartnerLocationRepoId() : -1);
		setC_BPartner_Location_Value_ID(from != null ? from.getLocationCaptureRepoId() : -1);
		setBPartnerAddress(null);
	}
}
