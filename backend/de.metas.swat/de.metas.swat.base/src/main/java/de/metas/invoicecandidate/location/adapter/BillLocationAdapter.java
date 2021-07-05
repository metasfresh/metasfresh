/*
 * #%L
 * de.metas.swat.base
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

package de.metas.invoicecandidate.location.adapter;

import de.metas.document.location.DocumentLocation;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.RecordBasedLocationAdapter;
import de.metas.document.location.RenderedAddressAndCapturedLocation;
import de.metas.document.location.adapter.IDocumentBillLocationAdapter;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;

import java.util.Optional;

public class BillLocationAdapter
		implements IDocumentBillLocationAdapter, RecordBasedLocationAdapter<BillLocationAdapter>
{
	private final I_C_Invoice_Candidate delegate;

	@Getter
	@Setter
	private String billToAddress;

	public BillLocationAdapter(@NonNull final I_C_Invoice_Candidate delegate)
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
	public void setRenderedAddressAndCapturedLocation(final @NonNull RenderedAddressAndCapturedLocation from)
	{
		IDocumentBillLocationAdapter.super.setRenderedAddressAndCapturedLocation(from);
	}

	public void setFrom(@NonNull final I_C_Invoice_Candidate from)
	{
		setFrom(new BillLocationAdapter(from).toDocumentLocation());
	}

	public void setFrom(@NonNull final I_C_Order order)
	{
		setFrom(OrderDocumentLocationAdapterFactory.billLocationAdapter(order).toDocumentLocation());
	}

	@Override
	public I_C_Invoice_Candidate getWrappedRecord()
	{
		return delegate;
	}

	@Override
	public Optional<DocumentLocation> toPlainDocumentLocation(final IDocumentLocationBL documentLocationBL)
	{
		return documentLocationBL.toPlainDocumentLocation(this);
	}

	@Override
	public BillLocationAdapter toOldValues()
	{
		InterfaceWrapperHelper.assertNotOldValues(delegate);
		return new BillLocationAdapter(InterfaceWrapperHelper.createOld(delegate, I_C_Invoice_Candidate.class));
	}
}
