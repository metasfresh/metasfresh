/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.material.replenish;

import de.metas.business.BusinessTestHelper;
import de.metas.product.ProductId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Replenish;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class ReplenishInfoRepositoryTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void getBy()
	{
		// given
		final I_M_Product productRecord = BusinessTestHelper.createProduct("product", BusinessTestHelper.createUomKg());
		final I_M_Warehouse warehouseRecord = BusinessTestHelper.createWarehouse("warehouse");

		final I_M_Replenish replenishRecord = newInstance(I_M_Replenish.class);
		replenishRecord.setM_Product_ID(productRecord.getM_Product_ID());
		replenishRecord.setM_Warehouse_ID(warehouseRecord.getM_Warehouse_ID());
		replenishRecord.setLevel_Min(new BigDecimal("10"));
		replenishRecord.setLevel_Max(new BigDecimal("20"));
		saveRecord(replenishRecord);

		// when
		final ReplenishInfo replenishInfo = new ReplenishInfoRepository()
				.getBy(
						WarehouseId.ofRepoId(warehouseRecord.getM_Warehouse_ID()),
						ProductId.ofRepoId(productRecord.getM_Product_ID()));

		// then
		assertThat(replenishInfo.getMin().getStockQty().toBigDecimal()).isEqualByComparingTo("10");
		assertThat(replenishInfo.getMax().getStockQty().toBigDecimal()).isEqualByComparingTo("20");
	}
}