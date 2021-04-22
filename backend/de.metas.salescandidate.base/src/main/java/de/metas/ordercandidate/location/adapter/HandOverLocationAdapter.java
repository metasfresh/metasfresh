/*
 * #%L
 * de.metas.salescandidate.base
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

package de.metas.ordercandidate.location.adapter;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.location.DocumentLocation;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.RecordBasedLocationAdapter;
import de.metas.document.location.RenderedAddressAndCapturedLocation;
import de.metas.document.location.adapter.IDocumentHandOverLocationAdapter;
import de.metas.ordercandidate.model.I_C_OLCand;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.Optional;

public class HandOverLocationAdapter
		implements IDocumentHandOverLocationAdapter, RecordBasedLocationAdapter<HandOverLocationAdapter>
{
	private final I_C_OLCand delegate;

	@Getter
	@Setter
	private String handOverAddress;

	HandOverLocationAdapter(@NonNull final I_C_OLCand delegate)
	{
		this.delegate = delegate;
	}

	@Override
	public boolean isUseHandOver_Location()
	{
		return BPartnerLocationId.ofRepoIdOrNull(getHandOver_Partner_ID(), getHandOver_Location_ID()) != null;
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
	public void setRenderedAddressAndCapturedLocation(final @NonNull RenderedAddressAndCapturedLocation from)
	{
		IDocumentHandOverLocationAdapter.super.setRenderedAddressAndCapturedLocation(from);
	}

	@Override
	public Optional<DocumentLocation> toPlainDocumentLocation(final IDocumentLocationBL documentLocationBL)
	{
		return documentLocationBL.toPlainDocumentLocation(this);
	}

	@Override
	public HandOverLocationAdapter toOldValues()
	{
		InterfaceWrapperHelper.assertNotOldValues(delegate);
		return new HandOverLocationAdapter(InterfaceWrapperHelper.createOld(delegate, I_C_OLCand.class));
	}

	@Override
	public I_C_OLCand getWrappedRecord()
	{
		return delegate;
	}
}
