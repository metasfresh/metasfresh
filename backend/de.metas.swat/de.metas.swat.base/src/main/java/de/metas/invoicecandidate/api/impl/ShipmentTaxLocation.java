/*
 * #%L
 * de.metas.swat.base
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

package de.metas.invoicecandidate.api.impl;

import de.metas.inout.location.adapter.InOutDocumentLocationAdapterFactory;
import de.metas.invoice.vatid.TaxLocation;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_InOut;

/**
 * The tax location of a shipped line: the delivery location and movement date of the shipment and the country of its warehouse.
 */
@UtilityClass
public class ShipmentTaxLocation
{
	public static TaxLocation of(@NonNull final I_M_InOut inOut, @NonNull final OrgId orgId)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(inOut.getM_Warehouse_ID());
		return new TaxLocation(
				InOutDocumentLocationAdapterFactory.locationAdapter(inOut).getBPartnerLocationAndCaptureId().getBpartnerLocationId(),
				orgId,
				inOut.getMovementDate(),
				warehouseId != null ? Services.get(IWarehouseBL.class).getCountryId(warehouseId) : null,
				warehouseId);
	}
}
