/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.document.location.impl;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.DocumentLocation;
import de.metas.document.location.adapter.IDocumentBillLocationAdapter;
import de.metas.document.location.adapter.IDocumentDeliveryLocationAdapter;
import de.metas.document.location.adapter.IDocumentHandOverLocationAdapter;
import de.metas.document.location.adapter.IDocumentLocationAdapter;
import de.metas.document.location.adapter.IDocumentLocationAdapterTemplate;
import de.metas.document.location.commands.update_record_location.UpdateRecordLocationCommand;
import de.metas.document.location.commands.update_record_location.UpdateRecordLocationCommand.UpdateRecordLocationCommandBuilder;
import de.metas.location.LocationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentLocationBL implements IDocumentLocationBL
{
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IBPartnerBL bpartnerBL;

	public DocumentLocationBL(@NonNull final IBPartnerBL bpartnerBL)
	{
		this.bpartnerBL = bpartnerBL;
	}

	@Override
	public String mkFullAddress(@NonNull final DocumentLocation location)
	{
		final BPartnerId bpartnerId = location.getBpartnerId();
		final I_C_BPartner bpartner = bpartnerId != null ? bpartnerDAO.getById(bpartnerId) : null;
		if (bpartner == null)
		{
			return "";
		}

		final BPartnerLocationId bpLocationId = location.getBpartnerLocationId();
		final I_C_BPartner_Location bpLocation = bpLocationId != null ? bpartnerDAO.getBPartnerLocationByIdEvenInactive(bpLocationId) : null;
		final LocationId locationId = location.getLocationId();

		final BPartnerContactId bpContactId = location.getContactId();
		final I_AD_User bpContact = bpContactId != null ? bpartnerDAO.getContactById(bpContactId) : null;

		return bpartnerBL.mkFullAddress(bpartner, bpLocation, locationId, bpContact);
	}

	@Override
	public Optional<DocumentLocation> toPlainDocumentLocation(@NonNull final IDocumentLocationAdapter location)
	{
		final BPartnerLocationId bpLocationId = BPartnerLocationId.ofRepoIdOrNull(location.getC_BPartner_ID(), location.getC_BPartner_Location_ID());
		if (bpLocationId == null)
		{
			return Optional.empty();
		}
		else
		{
			return Optional.of(
					DocumentLocation.builder()
							.bpartnerId(bpLocationId.getBpartnerId())
							.bpartnerLocationId(bpLocationId)
							.locationId(LocationId.ofRepoIdOrNull(location.getC_BPartner_Location_Value_ID()))
							.contactId(BPartnerContactId.ofRepoIdOrNull(bpLocationId.getBpartnerId(), location.getAD_User_ID()))
							.bpartnerAddress(location.getBPartnerAddress())
							.build());
		}
	}

	@Override
	public Optional<DocumentLocation> toPlainDocumentLocation(@NonNull final IDocumentBillLocationAdapter location)
	{
		final BPartnerLocationId bpLocationId = BPartnerLocationId.ofRepoIdOrNull(location.getBill_BPartner_ID(), location.getBill_Location_ID());
		if (bpLocationId == null)
		{
			return Optional.empty();
		}
		else
		{
			return Optional.of(
					DocumentLocation.builder()
							.bpartnerId(bpLocationId.getBpartnerId())
							.bpartnerLocationId(bpLocationId)
							.locationId(LocationId.ofRepoIdOrNull(location.getBill_Location_Value_ID()))
							.contactId(BPartnerContactId.ofRepoIdOrNull(bpLocationId.getBpartnerId(), location.getBill_User_ID()))
							.bpartnerAddress(location.getBillToAddress())
							.build());
		}
	}

	@Override
	public Optional<DocumentLocation> toPlainDocumentLocation(@NonNull final IDocumentDeliveryLocationAdapter location)
	{
		if (!location.isDropShip())
		{
			final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(location.getM_Warehouse_ID());
			return warehouseId != null
					? Optional.of(warehouseBL.getPlainDocumentLocation(warehouseId))
					: Optional.empty();
		}
		else
		{
			final BPartnerLocationId bpLocationId = BPartnerLocationId.ofRepoIdOrNull(location.getDropShip_BPartner_ID(), location.getDropShip_Location_ID());
			if (bpLocationId == null)
			{
				return Optional.empty();
			}

			return Optional.of(
					DocumentLocation.builder()
							.bpartnerId(bpLocationId.getBpartnerId())
							.bpartnerLocationId(bpLocationId)
							.locationId(LocationId.ofRepoIdOrNull(location.getDropShip_Location_Value_ID()))
							.contactId(BPartnerContactId.ofRepoIdOrNull(bpLocationId.getBpartnerId(), location.getDropShip_User_ID()))
							.bpartnerAddress(location.getDeliveryToAddress())
							.build());
		}
	}

	@Override
	public Optional<DocumentLocation> toPlainDocumentLocation(@NonNull final IDocumentHandOverLocationAdapter location)
	{
		if (!location.isUseHandOver_Location())
		{
			return Optional.empty();
		}

		final BPartnerLocationId bpLocationId = BPartnerLocationId.ofRepoIdOrNull(location.getHandOver_Partner_ID(), location.getHandOver_Location_ID());
		if (bpLocationId == null)
		{
			return Optional.empty();
		}

		return Optional.of(
				DocumentLocation.builder()
						.bpartnerId(bpLocationId.getBpartnerId())
						.bpartnerLocationId(bpLocationId)
						.locationId(LocationId.ofRepoIdOrNull(location.getHandOver_Location_Value_ID()))
						.contactId(BPartnerContactId.ofRepoIdOrNull(bpLocationId.getBpartnerId(), location.getHandOver_User_ID()))
						.bpartnerAddress(location.getHandOverAddress())
						.build());
	}

	@Override
	public void setBPartnerAddress(final IDocumentLocationAdapter location)
	{
		toPlainDocumentLocation(location)
				.map(this::mkFullAddress)
				.ifPresent(location::setBPartnerAddress);
	}

	@Override
	public void setBillToAddress(final IDocumentBillLocationAdapter location)
	{
		toPlainDocumentLocation(location)
				.map(this::mkFullAddress)
				.ifPresent(location::setBillToAddress);
	}

	@Override
	public void setDeliveryToAddress(final IDocumentDeliveryLocationAdapter location)
	{
		toPlainDocumentLocation(location)
				.map(this::mkFullAddress)
				.ifPresent(location::setDeliveryToAddress);
	}

	@Override
	public void setHandOverAddress(final IDocumentHandOverLocationAdapter location)
	{
		toPlainDocumentLocation(location)
				.map(this::mkFullAddress)
				.ifPresent(location::setHandOverAddress);
	}

	@Override
	public <RECORD, ADAPTER extends IDocumentLocationAdapterTemplate>
	UpdateRecordLocationCommandBuilder<RECORD, ADAPTER> prepareUpdateRecordLocation()
	{
		return UpdateRecordLocationCommand.<RECORD, ADAPTER>builder()
				.documentLocationBL(this)
				.bpartnerDAO(bpartnerDAO);
	}
}
