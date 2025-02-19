package de.metas.ui.web.shipment_candidates_editor;

import java.util.Set;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;

import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class ShipmentCandidateRowsRepository
{
	private final IShipmentScheduleBL shipmentScheduleBL;
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

	private final LookupDataSource salesOrdersLookup;
	private final LookupDataSource customersLookup;
	private final LookupDataSource warehousesLookup;
	private final LookupDataSource productsLookup;
	private final LookupDataSource asiLookup;
	private final LookupDataSource catchUOMsLookup;

	@Builder
	private ShipmentCandidateRowsRepository(
			@NonNull final IShipmentScheduleBL shipmentScheduleBL,
			@NonNull final LookupDataSourceFactory lookupDataSourceFactory)
	{
		this.shipmentScheduleBL = shipmentScheduleBL;

		salesOrdersLookup = lookupDataSourceFactory.searchInTableLookup(I_C_Order.Table_Name);
		customersLookup = lookupDataSourceFactory.searchInTableLookup(I_C_BPartner.Table_Name);
		warehousesLookup = lookupDataSourceFactory.searchInTableLookup(I_M_Warehouse.Table_Name);
		productsLookup = lookupDataSourceFactory.searchInTableLookup(I_M_Product.Table_Name);
		asiLookup = lookupDataSourceFactory.productAttributes();
		catchUOMsLookup = lookupDataSourceFactory.searchInTableLookup(I_C_UOM.Table_Name);
	}

	public ShipmentCandidateRows getByShipmentScheduleIds(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		return ShipmentCandidateRowsLoader.builder()
				.shipmentScheduleBL(shipmentScheduleBL)
				.shipmentScheduleEffectiveBL(shipmentScheduleEffectiveBL)
				.salesOrdersLookup(salesOrdersLookup)
				.customersLookup(customersLookup)
				.warehousesLookup(warehousesLookup)
				.productsLookup(productsLookup)
				.asiLookup(asiLookup)
				.catchUOMsLookup(catchUOMsLookup)
				//
				.shipmentScheduleIds(shipmentScheduleIds)
				//
				.load();
	}
}
