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

import de.metas.document.location.DocumentLocation;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.RecordBasedLocationAdapter;
import de.metas.document.location.RenderedAddressAndCapturedLocation;
import de.metas.document.location.adapter.IDocumentBillLocationAdapter;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;

import java.util.Optional;

public class OrderBillLocationAdapter
		implements IDocumentBillLocationAdapter, RecordBasedLocationAdapter<OrderBillLocationAdapter>
{
	private final I_C_Order delegate;

	OrderBillLocationAdapter(@NonNull final I_C_Order delegate)
	{
		this.delegate = delegate;
	}

	@Override
	public int getBill_BPartner_ID()
	{
		return delegate.getBill_BPartner_ID();
	}

	@Override
	public void setBill_BPartner_ID(final int Bill_BPartner_ID)
	{
		delegate.setBill_BPartner_ID(Bill_BPartner_ID);
	}

	@Override
	public int getBill_Location_ID()
	{
		return delegate.getBill_Location_ID();
	}

	@Override
	public void setBill_Location_ID(final int Bill_Location_ID)
	{
		delegate.setBill_Location_ID(Bill_Location_ID);
	}

	@Override
	public int getBill_Location_Value_ID()
	{
		return delegate.getBill_Location_Value_ID();
	}

	@Override
	public void setBill_Location_Value_ID(final int Bill_Location_Value_ID)
	{
		delegate.setBill_Location_Value_ID(Bill_Location_Value_ID);
	}

	@Override
	public int getBill_User_ID()
	{
		return delegate.getBill_User_ID();
	}

	@Override
	public void setBill_User_ID(final int Bill_User_ID)
	{
		delegate.setBill_User_ID(Bill_User_ID);
	}

	@Override
	public String getBillToAddress()
	{
		return delegate.getBillToAddress();
	}

	@Override
	public void setBillToAddress(final String address)
	{
		delegate.setBillToAddress(address);
	}

	@Override
	public void setRenderedAddressAndCapturedLocation(final @NonNull RenderedAddressAndCapturedLocation from)
	{
		IDocumentBillLocationAdapter.super.setRenderedAddressAndCapturedLocation(from);
	}

	public void setFromBillLocation(@NonNull final I_C_Order from)
	{
		setFrom(OrderDocumentLocationAdapterFactory.billLocationAdapter(from).toDocumentLocation());
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
	public OrderBillLocationAdapter toOldValues()
	{
		InterfaceWrapperHelper.assertNotOldValues(delegate);
		return new OrderBillLocationAdapter(InterfaceWrapperHelper.createOld(delegate, I_C_Order.class));
	}
}
