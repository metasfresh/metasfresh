package de.metas.ui.web.picking.pickingslot;

import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.sourcehu.SourceHUsService.MatchingSourceHusQuery;
import de.metas.inout.ShipmentScheduleId;
import de.metas.product.ProductId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PickingHURowsRepositoryTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testRetrieveActiveSourceHusQuery_fromShipmentSchedules()
	{
		final WarehouseId warehouseId = createWarehouse();
		final ProductId productId = createProduct();

		final ShipmentScheduleId shipmentScheduleId1 = createShipmentSchedule(warehouseId, productId);
		final ShipmentScheduleId shipmentScheduleId2 = createShipmentSchedule(warehouseId, productId);

		final MatchingSourceHusQuery query = PickingHURowsRepository.createMatchingSourceHusQuery(PickingSlotRepoQuery.builder()
				.currentShipmentScheduleId(shipmentScheduleId1)
				.shipmentScheduleId(shipmentScheduleId1)
				.shipmentScheduleId(shipmentScheduleId2)
				.build());

		assertThat(query.getWarehouseIds()).containsExactly(warehouseId);
		assertThat(query.getProductIds()).containsExactly(productId);
	}

	private WarehouseId createWarehouse()
	{
		final I_M_Warehouse wh = newInstance(I_M_Warehouse.class);
		save(wh);
		return WarehouseId.ofRepoId(wh.getM_Warehouse_ID());
	}

	private ShipmentScheduleId createShipmentSchedule(final WarehouseId warehouseId, final ProductId productId)
	{
		final I_M_ShipmentSchedule shipmentSchedule1 = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule1.setM_Warehouse_ID(warehouseId.getRepoId());
		shipmentSchedule1.setM_Product_ID(productId.getRepoId());
		save(shipmentSchedule1);
		return ShipmentScheduleId.ofRepoId(shipmentSchedule1.getM_ShipmentSchedule_ID());
	}

	private ProductId createProduct()
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		save(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}
}
