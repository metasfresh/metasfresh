/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.location.adapter;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.location.DocumentLocation;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.RecordBasedLocationAdapter;
import de.metas.document.location.RenderedAddressAndCapturedLocation;
import de.metas.document.location.adapter.IDocumentDeliveryLocationAdapter;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.model.InterfaceWrapperHelper;

import javax.annotation.Nullable;
import java.util.Optional;

public class ContractDropshipLocationAdapter implements IDocumentDeliveryLocationAdapter, RecordBasedLocationAdapter<ContractDropshipLocationAdapter>
{
	private final I_C_Flatrate_Term delegate;

	@Getter
	@Setter
	private String deliveryToAddress;

	ContractDropshipLocationAdapter(@NonNull final I_C_Flatrate_Term delegate)
	{
		this.delegate = delegate;
	}

	@Override
	public boolean isDropShip()
	{
		return BPartnerLocationId.ofRepoIdOrNull(getDropShip_BPartner_ID(), getDropShip_Location_ID()) != null;
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
		return -1;
	}

	@Override
	public void setRenderedAddressAndCapturedLocation(final @NonNull RenderedAddressAndCapturedLocation from)
	{
		IDocumentDeliveryLocationAdapter.super.setRenderedAddressAndCapturedLocation(from);
	}

	@Override
	public Optional<DocumentLocation> toPlainDocumentLocation(final IDocumentLocationBL documentLocationBL)
	{
		return documentLocationBL.toPlainDocumentLocation(this);
	}

	@Override
	public ContractDropshipLocationAdapter toOldValues()
	{
		InterfaceWrapperHelper.assertNotOldValues(delegate);
		return new ContractDropshipLocationAdapter(InterfaceWrapperHelper.createOld(delegate, I_C_Flatrate_Term.class));
	}

	@Override
	public I_C_Flatrate_Term getWrappedRecord()
	{
		return delegate;
	}

	public void setFrom(final @NonNull BPartnerLocationAndCaptureId dropshipLocationId, final @Nullable BPartnerContactId dropshipContactId)
	{
		setDropShip_BPartner_ID(dropshipLocationId.getBpartnerRepoId());
		setDropShip_Location_ID(dropshipLocationId.getBPartnerLocationRepoId());
		setDropShip_Location_Value_ID(dropshipLocationId.getLocationCaptureRepoId());
		setDropShip_User_ID(dropshipContactId == null ? -1 : dropshipContactId.getRepoId());
	}

	public void setFrom(final @NonNull BPartnerLocationAndCaptureId dropshipLocationId)
	{
		setDropShip_BPartner_ID(dropshipLocationId.getBpartnerRepoId());
		setDropShip_Location_ID(dropshipLocationId.getBPartnerLocationRepoId());
		setDropShip_Location_Value_ID(dropshipLocationId.getLocationCaptureRepoId());
	}
}
