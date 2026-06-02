/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package org.adempiere.warehouse.api.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the precedence rule of {@link WarehouseBL#isIgnoreInMaterialDispo(WarehouseId)}:
 * an explicit {@code MRP_Exclude} value (Y/N) wins over {@code IsDropShipWarehouse};
 * a null/empty {@code MRP_Exclude} falls back to {@code IsDropShipWarehouse}.
 */
class WarehouseBLTest
{
	private IWarehouseBL warehouseBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		warehouseBL = new WarehouseBL();
	}

	@Test
	void nullWarehouseId_returnsFalse()
	{
		assertThat(warehouseBL.isIgnoreInMaterialDispo(null)).isFalse();
	}

	@Test
	void mrpExcludeY_isDropShipN_returnsTrue()
	{
		final WarehouseId id = createWarehouse("Y", false);
		assertThat(warehouseBL.isIgnoreInMaterialDispo(id)).isTrue();
	}

	@Test
	void mrpExcludeY_isDropShipY_returnsTrue()
	{
		final WarehouseId id = createWarehouse("Y", true);
		assertThat(warehouseBL.isIgnoreInMaterialDispo(id)).isTrue();
	}

	/**
	 * Guard against a future "regression-fix" that flips the precedence into a pure OR:
	 * an explicit {@code MRP_Exclude=N} MUST override {@code IsDropShipWarehouse=Y}.
	 */
	@Test
	void mrpExcludeN_isDropShipY_returnsFalse_overrideCase()
	{
		final WarehouseId id = createWarehouse("N", true);
		assertThat(warehouseBL.isIgnoreInMaterialDispo(id)).isFalse();
	}

	@Test
	void mrpExcludeN_isDropShipN_returnsFalse()
	{
		final WarehouseId id = createWarehouse("N", false);
		assertThat(warehouseBL.isIgnoreInMaterialDispo(id)).isFalse();
	}

	@Test
	void mrpExcludeNull_isDropShipY_fallsBackToDropShip_returnsTrue()
	{
		final WarehouseId id = createWarehouse(null, true);
		assertThat(warehouseBL.isIgnoreInMaterialDispo(id)).isTrue();
	}

	@Test
	void mrpExcludeNull_isDropShipN_returnsFalse()
	{
		final WarehouseId id = createWarehouse(null, false);
		assertThat(warehouseBL.isIgnoreInMaterialDispo(id)).isFalse();
	}

	private WarehouseId createWarehouse(@Nullable final String mrpExclude, final boolean isDropShipWarehouse)
	{
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		warehouse.setMRP_Exclude(mrpExclude);
		warehouse.setIsDropShipWarehouse(isDropShipWarehouse);
		InterfaceWrapperHelper.saveRecord(warehouse);
		return WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
	}
}
