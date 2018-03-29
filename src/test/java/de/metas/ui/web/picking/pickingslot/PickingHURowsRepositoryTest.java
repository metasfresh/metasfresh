package de.metas.ui.web.picking.pickingslot;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.sourcehu.SourceHUsService.MatchingSourceHusQuery;

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
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testRetrieveActiveSourceHusQuery_fromShipmentSchedules()
	{
		final I_M_Warehouse wh = newInstance(I_M_Warehouse.class);
		save(wh);

		final I_M_Product product = newInstance(I_M_Product.class);
		save(product);

		final I_M_ShipmentSchedule shipmentSchedule1 = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule1.setM_Warehouse(wh);
		shipmentSchedule1.setM_Product(product);
		save(shipmentSchedule1);

		final I_M_ShipmentSchedule shipmentSchedule2 = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule2.setM_Warehouse(wh);
		shipmentSchedule2.setM_Product(product);
		save(shipmentSchedule2);

		final MatchingSourceHusQuery query = PickingHURowsRepository.createMatchingSourceHusQuery(PickingSlotRepoQuery.builder()
				.currentShipmentScheduleId(shipmentSchedule1.getM_ShipmentSchedule_ID())
				.shipmentScheduleId(shipmentSchedule1.getM_ShipmentSchedule_ID())
				.shipmentScheduleId(shipmentSchedule2.getM_ShipmentSchedule_ID())
				.build());

		assertThat(query.getWarehouseId()).isEqualTo(wh.getM_Warehouse_ID());
		assertThat(query.getProductIds()).containsExactly(product.getM_Product_ID());
	}
}
