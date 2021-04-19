package de.metas.document.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.IDocumentLocationBL;
import de.metas.document.model.IDocumentBillLocation;
import de.metas.document.model.IDocumentDeliveryLocation;
import de.metas.document.model.IDocumentHandOverLocation;
import de.metas.document.model.IDocumentLocation;
import de.metas.document.model.PlainDocumentLocation;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Warehouse;

import java.util.Optional;

public class DocumentLocationBL implements IDocumentLocationBL
{
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	private String mkFullAddress(@NonNull final PlainDocumentLocation location)
	{
		final BPartnerId bpartnerId = location.getBpartnerId();
		final I_C_BPartner bpartner = bpartnerId != null ? bpartnerDAO.getById(bpartnerId) : null;

		final BPartnerLocationId bpLocationId = location.getBpartnerLocationId();
		final I_C_BPartner_Location bpLocation = bpLocationId != null ? bpartnerDAO.getBPartnerLocationByIdEvenInactive(bpLocationId) : null;

		final BPartnerContactId bpContactId = location.getContactId();
		final I_AD_User bpContact = bpContactId != null ? bpartnerDAO.getContactById(bpContactId) : null;

		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
		return bpartnerBL.mkFullAddress(bpartner, bpLocation, bpContact, ITrx.TRXNAME_ThreadInherited);
	}

	private static Optional<PlainDocumentLocation> toPlainDocumentLocation(@NonNull final IDocumentLocation location)
	{
		if (location instanceof PlainDocumentLocation)
		{
			return Optional.of((PlainDocumentLocation)location);
		}
		else
		{
			final BPartnerLocationId bpLocationId = BPartnerLocationId.ofRepoIdOrNull(location.getC_BPartner_ID(), location.getC_BPartner_Location_ID());
			if (bpLocationId == null)
			{
				return Optional.empty();
			}

			return Optional.of(
					PlainDocumentLocation.builder()
							.bpartnerId(bpLocationId.getBpartnerId())
							.bpartnerLocationId(bpLocationId)
							.contactId(BPartnerContactId.ofRepoIdOrNull(bpLocationId.getBpartnerId(), location.getAD_User_ID()))
							.bpartnerAddress(location.getBPartnerAddress())
							.build());
		}
	}

	private static Optional<PlainDocumentLocation> toPlainDocumentLocation(@NonNull final IDocumentBillLocation location)
	{
		final BPartnerLocationId bpLocationId = BPartnerLocationId.ofRepoIdOrNull(location.getBill_BPartner_ID(), location.getBill_Location_ID());
		if (bpLocationId == null)
		{
			return Optional.empty();
		}

		return Optional.of(
				PlainDocumentLocation.builder()
						.bpartnerId(bpLocationId.getBpartnerId())
						.bpartnerLocationId(bpLocationId)
						.contactId(BPartnerContactId.ofRepoIdOrNull(bpLocationId.getBpartnerId(), location.getBill_User_ID()))
						.bpartnerAddress(location.getBillToAddress())
						.build());
	}

	private static Optional<PlainDocumentLocation> toPlainDocumentLocation(
			@NonNull final IDocumentDeliveryLocation location,
			@NonNull final IWarehouseDAO warehouseDAO)
	{
		if (!location.isDropShip())
		{
			final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(location.getM_Warehouse_ID());
			if (warehouseId == null)
			{
				return Optional.empty();
			}

			final I_M_Warehouse warehouse = warehouseDAO.getById(warehouseId);

			final BPartnerLocationId warehouseBPLocationId = BPartnerLocationId.ofRepoIdOrNull(warehouse.getC_BPartner_ID(), warehouse.getC_BPartner_Location_ID());
			if (warehouseBPLocationId == null)
			{
				throw new AdempiereException("@NotFound@ @C_BPartner_Location_ID@ (@M_Warehouse_ID@:" + warehouse.getName() + ")");
			}

			return Optional.of(
					PlainDocumentLocation.builder()
							.bpartnerId(warehouseBPLocationId.getBpartnerId())
							.bpartnerLocationId(warehouseBPLocationId)
							.contactId(null) // N/A
							.bpartnerAddress(null) // N/A
							.build());
		}
		else
		{
			final BPartnerLocationId bpLocationId = BPartnerLocationId.ofRepoIdOrNull(location.getDropShip_BPartner_ID(), location.getDropShip_Location_ID());
			if (bpLocationId == null)
			{
				return Optional.empty();
			}

			return Optional.of(
					PlainDocumentLocation.builder()
							.bpartnerId(bpLocationId.getBpartnerId())
							.bpartnerLocationId(bpLocationId)
							.contactId(BPartnerContactId.ofRepoIdOrNull(bpLocationId.getBpartnerId(), location.getDropShip_User_ID()))
							.bpartnerAddress(location.getDeliveryToAddress())
							.build());
		}
	}

	private static Optional<PlainDocumentLocation> toPlainDocumentLocation(@NonNull final IDocumentHandOverLocation location)
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
				PlainDocumentLocation.builder()
						.bpartnerId(bpLocationId.getBpartnerId())
						.bpartnerLocationId(bpLocationId)
						.contactId(BPartnerContactId.ofRepoIdOrNull(bpLocationId.getBpartnerId(), location.getHandOver_User_ID()))
						.bpartnerAddress(location.getHandOverAddress())
						.build());
	}

	@Override
	public void setBPartnerAddress(final IDocumentLocation location)
	{
		toPlainDocumentLocation(location)
				.map(this::mkFullAddress)
				.ifPresent(location::setBPartnerAddress);
	}

	@Override
	public void setBillToAddress(final IDocumentBillLocation billLocation)
	{
		toPlainDocumentLocation(billLocation)
				.map(this::mkFullAddress)
				.ifPresent(billLocation::setBillToAddress);
	}

	@Override
	public void setDeliveryToAddress(final IDocumentDeliveryLocation deliveryLocation)
	{
		toPlainDocumentLocation(deliveryLocation, warehouseDAO)
				.map(this::mkFullAddress)
				.ifPresent(deliveryLocation::setDeliveryToAddress);
	}

	@Override
	public void setHandOverAddress(final IDocumentHandOverLocation handOverLocation)
	{
		toPlainDocumentLocation(handOverLocation)
				.map(this::mkFullAddress)
				.ifPresent(handOverLocation::setHandOverAddress);
	}
}
