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

package de.metas.inout.location.adapter;

import de.metas.document.location.DocumentLocation;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.RecordBasedLocationAdapter;
import de.metas.document.location.RenderedAddressAndCapturedLocation;
import de.metas.document.location.adapter.IDocumentDeliveryLocationAdapter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOut;

import java.util.Optional;

@ToString
public class DocumentDeliveryLocationAdapter
		implements IDocumentDeliveryLocationAdapter, RecordBasedLocationAdapter<DocumentDeliveryLocationAdapter>
{
	private final I_M_InOut delegate;

	DocumentDeliveryLocationAdapter(@NonNull final I_M_InOut delegate)
	{
		this.delegate = delegate;
	}

	@Override
	public int getDropShip_BPartner_ID()
	{
		return delegate.getDropShip_BPartner_ID();
	}

	@Override
	public void setDropShip_BPartner_ID(final int DropShip_BPartner_ID)
	{
		delegate.setDropShip_BPartner_ID(DropShip_BPartner_ID);
	}

	@Override
	public int getDropShip_Location_ID()
	{
		return delegate.getDropShip_Location_ID();
	}

	@Override
	public void setDropShip_Location_ID(final int DropShip_Location_ID)
	{
		delegate.setDropShip_Location_ID(DropShip_Location_ID);
	}

	@Override
	public int getDropShip_Location_Value_ID()
	{
		return delegate.getDropShip_Location_Value_ID();
	}

	@Override
	public void setDropShip_Location_Value_ID(final int DropShip_Location_Value_ID)
	{
		delegate.setDropShip_Location_Value_ID(DropShip_Location_Value_ID);
	}

	@Override
	public int getDropShip_User_ID()
	{
		return delegate.getDropShip_User_ID();
	}

	@Override
	public void setDropShip_User_ID(final int DropShip_User_ID)
	{
		delegate.setDropShip_User_ID(DropShip_User_ID);
	}

	@Override
	public int getM_Warehouse_ID()
	{
		return delegate.getM_Warehouse_ID();
	}

	@Override
	public boolean isDropShip()
	{
		return delegate.isDropShip();
	}

	@Override
	public String getDeliveryToAddress()
	{
		return delegate.getDeliveryToAddress();
	}

	@Override
	public void setDeliveryToAddress(final String address)
	{
		delegate.setDeliveryToAddress(address);
	}

	@Override
	public void setRenderedAddressAndCapturedLocation(final @NonNull RenderedAddressAndCapturedLocation from)
	{
		IDocumentDeliveryLocationAdapter.super.setRenderedAddressAndCapturedLocation(from);
	}

	@Override
	public I_M_InOut getWrappedRecord()
	{
		return delegate;
	}

	@Override
	public Optional<DocumentLocation> toPlainDocumentLocation(final IDocumentLocationBL documentLocationBL)
	{
		return documentLocationBL.toPlainDocumentLocation(this);
	}

	@Override
	public DocumentDeliveryLocationAdapter toOldValues()
	{
		InterfaceWrapperHelper.assertNotOldValues(delegate);
		return new DocumentDeliveryLocationAdapter(InterfaceWrapperHelper.createOld(delegate, I_M_InOut.class));
	}
}
