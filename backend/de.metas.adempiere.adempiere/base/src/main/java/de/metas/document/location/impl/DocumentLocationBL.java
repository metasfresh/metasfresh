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
import de.metas.common.util.CoalesceUtil;
import de.metas.document.location.DocumentLocation;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.RenderedAddressAndCapturedLocation;
import de.metas.document.location.adapter.IDocumentBillLocationAdapter;
import de.metas.document.location.adapter.IDocumentDeliveryLocationAdapter;
import de.metas.document.location.adapter.IDocumentHandOverLocationAdapter;
import de.metas.document.location.adapter.IDocumentLocationAdapter;
import de.metas.location.AddressDisplaySequence;
import de.metas.location.LocationId;
import de.metas.location.impl.AddressBuilder;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
public class DocumentLocationBL implements IDocumentLocationBL
{
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	public DocumentLocationBL(@NonNull final IBPartnerBL bpartnerBL)
	{
	}

	@Override
	public RenderedAddressAndCapturedLocation computeRenderedAddress(@NonNull final DocumentLocation location)
	{
		return computeRenderedAddress(location, null);
	}

	@Override
	public RenderedAddressAndCapturedLocation computeRenderedAddress(@NonNull final DocumentLocation location, @Nullable AddressDisplaySequence displaySequence)
	{
		final BPartnerId bpartnerId = location.getBpartnerId();
		final I_C_BPartner bpartner = bpartnerId != null ? bpartnerDAO.getById(bpartnerId) : null;
		if (bpartner == null)
		{
			return RenderedAddressAndCapturedLocation.NONE;
		}

		final BPartnerLocationId bpLocationId = location.getBpartnerLocationId();
		final I_C_BPartner_Location bpLocation = bpLocationId != null ? bpartnerDAO.getBPartnerLocationByIdEvenInactive(bpLocationId) : null;
		final LocationId locationId = CoalesceUtil.coalesceSuppliers(
				location::getLocationId,
				() -> bpLocation != null ? LocationId.ofRepoIdOrNull(bpLocation.getC_Location_ID()) : null);

		final BPartnerContactId bpContactId = location.getContactId();
		final I_AD_User bpContact = bpContactId != null ? bpartnerDAO.getContactById(bpContactId) : null;

		final String renderedAddress = AddressBuilder.builder()
				.orgId(OrgId.ofRepoId(bpartner.getAD_Org_ID()))
				.adLanguage(bpartner.getAD_Language())
				.build()
				.buildBPartnerFullAddressString(bpartner, bpLocation, locationId, bpContact, displaySequence);

		return RenderedAddressAndCapturedLocation.of(renderedAddress, locationId);
	}

	@Override
	public String computeRenderedAddressString(@NonNull final DocumentLocation location)
	{
		return computeRenderedAddress(location).getRenderedAddress();
	}

	private DocumentLocation withUpdatedLocationId(final DocumentLocation location)
	{
		final BPartnerLocationId bpLocationId = location.getBpartnerLocationId();
		final I_C_BPartner_Location bpLocation = bpLocationId != null ? bpartnerDAO.getBPartnerLocationByIdEvenInactive(bpLocationId) : null;
		final LocationId locationId = bpLocation != null ? LocationId.ofRepoIdOrNull(bpLocation.getC_Location_ID()) : null;
		return location.toBuilder()
				.locationId(locationId)
				.build();
	}

	@Override
	public Optional<DocumentLocation> toPlainDocumentLocation(@NonNull final IDocumentLocationAdapter locationAdapter)
	{
		final BPartnerLocationId bpLocationId = BPartnerLocationId.ofRepoIdOrNull(locationAdapter.getC_BPartner_ID(), locationAdapter.getC_BPartner_Location_ID());
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
							.locationId(LocationId.ofRepoIdOrNull(locationAdapter.getC_BPartner_Location_Value_ID()))
							.contactId(BPartnerContactId.ofRepoIdOrNull(bpLocationId.getBpartnerId(), locationAdapter.getAD_User_ID()))
							.bpartnerAddress(locationAdapter.getBPartnerAddress())
							.build());
		}
	}

	@Override
	public Optional<DocumentLocation> toPlainDocumentLocation(@NonNull final IDocumentBillLocationAdapter locationAdapter)
	{
		final BPartnerLocationId bpLocationId = BPartnerLocationId.ofRepoIdOrNull(locationAdapter.getBill_BPartner_ID(), locationAdapter.getBill_Location_ID());
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
							.locationId(LocationId.ofRepoIdOrNull(locationAdapter.getBill_Location_Value_ID()))
							.contactId(BPartnerContactId.ofRepoIdOrNull(bpLocationId.getBpartnerId(), locationAdapter.getBill_User_ID()))
							.bpartnerAddress(locationAdapter.getBillToAddress())
							.build());
		}
	}

	@Override
	public Optional<DocumentLocation> toPlainDocumentLocation(@NonNull final IDocumentDeliveryLocationAdapter locationAdapter)
	{
		if (!locationAdapter.isDropShip())
		{
			final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(locationAdapter.getM_Warehouse_ID());
			return warehouseId != null
					? Optional.of(warehouseBL.getPlainDocumentLocation(warehouseId))
					: Optional.empty();
		}
		else
		{
			final BPartnerLocationId bpLocationId = BPartnerLocationId.ofRepoIdOrNull(locationAdapter.getDropShip_BPartner_ID(), locationAdapter.getDropShip_Location_ID());
			if (bpLocationId == null)
			{
				return Optional.empty();
			}

			return Optional.of(
					DocumentLocation.builder()
							.bpartnerId(bpLocationId.getBpartnerId())
							.bpartnerLocationId(bpLocationId)
							.locationId(LocationId.ofRepoIdOrNull(locationAdapter.getDropShip_Location_Value_ID()))
							.contactId(BPartnerContactId.ofRepoIdOrNull(bpLocationId.getBpartnerId(), locationAdapter.getDropShip_User_ID()))
							.bpartnerAddress(locationAdapter.getDeliveryToAddress())
							.build());
		}
	}

	@Override
	public Optional<DocumentLocation> toPlainDocumentLocation(@NonNull final IDocumentHandOverLocationAdapter locationAdapter)
	{
		if (!locationAdapter.isUseHandOver_Location())
		{
			return Optional.empty();
		}

		final BPartnerLocationId bpLocationId = BPartnerLocationId.ofRepoIdOrNull(locationAdapter.getHandOver_Partner_ID(), locationAdapter.getHandOver_Location_ID());
		if (bpLocationId == null)
		{
			return Optional.empty();
		}

		return Optional.of(
				DocumentLocation.builder()
						.bpartnerId(bpLocationId.getBpartnerId())
						.bpartnerLocationId(bpLocationId)
						.locationId(LocationId.ofRepoIdOrNull(locationAdapter.getHandOver_Location_Value_ID()))
						.contactId(BPartnerContactId.ofRepoIdOrNull(bpLocationId.getBpartnerId(), locationAdapter.getHandOver_User_ID()))
						.bpartnerAddress(locationAdapter.getHandOverAddress())
						.build());
	}

	@Override
	public void updateRenderedAddressAndCapturedLocation(final IDocumentLocationAdapter locationAdapter)
	{
		toPlainDocumentLocation(locationAdapter)
				.map(this::computeRenderedAddress)
				.ifPresent(locationAdapter::setRenderedAddressAndCapturedLocation);
	}

	@Override
	public void updateCapturedLocation(final IDocumentLocationAdapter locationAdapter)
	{
		toPlainDocumentLocation(locationAdapter)
				.map(this::withUpdatedLocationId)
				.ifPresent(location -> locationAdapter.setC_BPartner_Location_Value_ID(LocationId.toRepoId(location.getLocationId())));
	}

	@Override
	public void updateRenderedAddressAndCapturedLocation(final IDocumentBillLocationAdapter locationAdapter)
	{
		toPlainDocumentLocation(locationAdapter)
				.map(this::computeRenderedAddress)
				.ifPresent(locationAdapter::setRenderedAddressAndCapturedLocation);
	}

	@Override
	public void updateCapturedLocation(final IDocumentBillLocationAdapter locationAdapter)
	{
		toPlainDocumentLocation(locationAdapter)
				.map(this::withUpdatedLocationId)
				.ifPresent(location -> locationAdapter.setBill_Location_Value_ID(LocationId.toRepoId(location.getLocationId())));
	}

	@Override
	public void updateRenderedAddressAndCapturedLocation(final IDocumentDeliveryLocationAdapter locationAdapter)
	{
		toPlainDocumentLocation(locationAdapter)
				.map(this::computeRenderedAddress)
				.ifPresent(locationAdapter::setRenderedAddressAndCapturedLocation);
	}

	@Override
	public void updateCapturedLocation(final IDocumentDeliveryLocationAdapter locationAdapter)
	{
		toPlainDocumentLocation(locationAdapter)
				.map(this::withUpdatedLocationId)
				.ifPresent(location -> locationAdapter.setDropShip_Location_Value_ID(LocationId.toRepoId(location.getLocationId())));
	}

	@Override
	public void updateRenderedAddressAndCapturedLocation(final IDocumentHandOverLocationAdapter locationAdapter)
	{
		toPlainDocumentLocation(locationAdapter)
				.map(this::computeRenderedAddress)
				.ifPresent(locationAdapter::setRenderedAddressAndCapturedLocation);
	}

	@Override
	public void updateCapturedLocation(final IDocumentHandOverLocationAdapter locationAdapter)
	{
		toPlainDocumentLocation(locationAdapter)
				.map(this::withUpdatedLocationId)
				.ifPresent(location -> locationAdapter.setHandOver_Location_Value_ID(LocationId.toRepoId(location.getLocationId())));
	}
}
