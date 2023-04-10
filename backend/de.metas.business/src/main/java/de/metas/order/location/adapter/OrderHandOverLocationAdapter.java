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

package de.metas.order.location.adapter;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.document.location.DocumentLocation;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.RecordBasedLocationAdapter;
import de.metas.document.location.RenderedAddressAndCapturedLocation;
import de.metas.document.location.adapter.IDocumentHandOverLocationAdapter;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;

import java.util.Optional;

public class OrderHandOverLocationAdapter
		implements IDocumentHandOverLocationAdapter, RecordBasedLocationAdapter<OrderHandOverLocationAdapter>
{
	private final I_C_Order delegate;

	OrderHandOverLocationAdapter(@NonNull final I_C_Order delegate)
	{
		this.delegate = delegate;
	}

	@Override
	public boolean isUseHandOver_Location()
	{
		return delegate.isUseHandOver_Location();
	}

	@Override
	public int getHandOver_Partner_ID()
	{
		return delegate.getHandOver_Partner_ID();
	}

	@Override
	public void setHandOver_Partner_ID(final int HandOver_Partner_ID)
	{
		delegate.setHandOver_Partner_ID(HandOver_Partner_ID);
	}

	@Override
	public int getHandOver_Location_ID()
	{
		return delegate.getHandOver_Location_ID();
	}

	@Override
	public void setHandOver_Location_ID(final int HandOver_Location_ID)
	{
		delegate.setHandOver_Location_ID(HandOver_Location_ID);
	}

	@Override
	public int getHandOver_Location_Value_ID()
	{
		return delegate.getHandOver_Location_Value_ID();
	}

	@Override
	public void setHandOver_Location_Value_ID(final int HandOver_Location_Value_ID)
	{
		delegate.setHandOver_Location_Value_ID(HandOver_Location_Value_ID);
	}

	@Override
	public int getHandOver_User_ID()
	{
		return delegate.getHandOver_User_ID();
	}

	@Override
	public void setHandOver_User_ID(final int HandOver_User_ID)
	{
		delegate.setHandOver_User_ID(HandOver_User_ID);
	}

	@Override
	public String getHandOverAddress()
	{
		return delegate.getHandOverAddress();
	}

	@Override
	public void setHandOverAddress(final String address)
	{
		delegate.setHandOverAddress(address);
	}

	@Override
	public void setRenderedAddressAndCapturedLocation(final @NonNull RenderedAddressAndCapturedLocation from)
	{
		IDocumentHandOverLocationAdapter.super.setRenderedAddressAndCapturedLocation(from);
	}

	@Override
	public I_C_Order getWrappedRecord()
	{
		return delegate;
	}

	@Override
	public Optional<DocumentLocation> toPlainDocumentLocation(final IDocumentLocationBL documentLocationBL)
	{
		return documentLocationBL.toPlainDocumentLocation(this);
	}

	@Override
	public OrderHandOverLocationAdapter toOldValues()
	{
		InterfaceWrapperHelper.assertNotOldValues(delegate);
		return new OrderHandOverLocationAdapter(InterfaceWrapperHelper.createOld(delegate, I_C_Order.class));
	}

	public void setFromHandOverLocation(@NonNull final I_C_Order from)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(from.getHandOver_Partner_ID());
		final BPartnerInfo bpartnerInfo = BPartnerInfo.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, from.getBill_Location_ID()))
				.contactId(BPartnerContactId.ofRepoIdOrNull(bpartnerId, from.getBill_User_ID()))
				.build();
		setFrom(bpartnerInfo);
	}
}
