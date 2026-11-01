package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import de.metas.handlingunits.model.I_M_Warehouse;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HUWarehouseDAOTest
{
	private HUWarehouseDAO huWarehouseDAO;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		huWarehouseDAO = new HUWarehouseDAO();
	}

	@Test
	public void retrieveQualityReturnWarehouseIdIfExists_empty_whenNoneConfigured()
	{
		assertThat(huWarehouseDAO.retrieveQualityReturnWarehouseIdIfExists()).isEmpty();
	}

	@Test
	public void retrieveFirstQualityReturnWarehouseId_throws_whenNoneConfigured()
	{
		assertThatThrownBy(() -> huWarehouseDAO.retrieveFirstQualityReturnWarehouseId())
				.isInstanceOf(org.adempiere.exceptions.AdempiereException.class);
	}

	@Test
	public void retrieveQualityReturnWarehouseIdIfExists_returnsIt_whenOneIsConfigured()
	{
		final WarehouseId qualityWarehouseId = createWarehouse(true);
		createWarehouse(false); // a non-quality warehouse must not be picked up

		final Optional<WarehouseId> result = huWarehouseDAO.retrieveQualityReturnWarehouseIdIfExists();

		assertThat(result).contains(qualityWarehouseId);
		assertThat(huWarehouseDAO.retrieveFirstQualityReturnWarehouseId()).isEqualTo(qualityWarehouseId);
	}

	@Test
	public void qualityReturnWarehouse_lowestIdWins_whenSeveralAreConfigured()
	{
		// the higher ID is stored first, so an unordered scan would return it first
		createWarehouse(true, 200);
		final WarehouseId lowerId = createWarehouse(true, 100);

		assertThat(huWarehouseDAO.retrieveQualityReturnWarehouseIdIfExists()).contains(lowerId);
		assertThat(huWarehouseDAO.retrieveFirstQualityReturnWarehouseId()).isEqualTo(lowerId);
	}

	private static WarehouseId createWarehouse(final boolean isQualityReturnWarehouse, final int warehouseRepoId)
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setM_Warehouse_ID(warehouseRepoId);
		warehouse.setIsQualityReturnWarehouse(isQualityReturnWarehouse);
		saveRecord(warehouse);
		return WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
	}

	private static WarehouseId createWarehouse(final boolean isQualityReturnWarehouse)
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setIsQualityReturnWarehouse(isQualityReturnWarehouse);
		saveRecord(warehouse);
		return WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
	}
}
