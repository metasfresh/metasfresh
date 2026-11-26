/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.invoice.service;

import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.inout.location.adapter.InOutDocumentLocationAdapterFactory;
import de.metas.location.CountryId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_InOut;

import javax.annotation.Nullable;
import java.sql.Timestamp;

/**
 * The inputs of the tax query with which an invoice line is (re-)taxed.
 */
@Value
@Builder
public class InvoiceLineReTaxInput
{
	@NonNull OrgId orgId;
	@NonNull Timestamp taxDate;
	@NonNull CountryId countryFromId;
	@Nullable WarehouseId warehouseId;
	@NonNull BPartnerLocationAndCaptureId taxLocation;
	boolean isSOTrx;

	/**
	 * The inputs of a line of the given shipment / receipt: its org, movement date, partner location and SO flag.
	 */
	public static InvoiceLineReTaxInput ofShipment(
			@NonNull final I_M_InOut shipment,
			@NonNull final CountryId countryFromId,
			@Nullable final WarehouseId warehouseId)
	{
		return builder()
				.orgId(OrgId.ofRepoId(shipment.getAD_Org_ID()))
				.taxDate(shipment.getMovementDate())
				.countryFromId(countryFromId)
				.warehouseId(warehouseId)
				.taxLocation(InOutDocumentLocationAdapterFactory.locationAdapter(shipment).getBPartnerLocationAndCaptureId())
				.isSOTrx(shipment.isSOTrx())
				.build();
	}
}
