/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.workplace;

import com.google.common.collect.ImmutableSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Workplace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WorkplaceRepositoryTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void test()
	{
		final Workplace workplace = WorkplaceRepository.newInstanceForUnitTesting().create(WorkplaceCreateRequest.builder()
						.name("Test")
						.warehouseId(WarehouseId.ofRepoId(1))
				.build());
		assertNotNull(workplace);

		final Workplace workplace2 = WorkplaceRepository.newInstanceForUnitTesting().create(WorkplaceCreateRequest.builder()
				.name("Test2")
				.warehouseId(WarehouseId.ofRepoId(1))
				.build());
		assertNotNull(workplace2);
	}

	@Test
	public void getPackingPlacePickFromLocatorIds_returnsOnlyPackingPlacesWithLocator()
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoId(1);
		final LocatorId L1 = LocatorId.ofRepoId(warehouseId, 101);
		final LocatorId L2 = LocatorId.ofRepoId(warehouseId, 102);
		final LocatorId L3 = LocatorId.ofRepoId(warehouseId, 103);

		// WP-A: isPackingPlace=Y, locator L1
		final I_C_Workplace wpA = InterfaceWrapperHelper.newInstance(I_C_Workplace.class);
		wpA.setName("WP-A");
		wpA.setM_Warehouse_ID(warehouseId.getRepoId());
		wpA.setIsPackingPlace(true);
		wpA.setPickFrom_Locator_ID(L1.getRepoId());
		InterfaceWrapperHelper.saveRecord(wpA);

		// WP-B: isPackingPlace=Y, locator L2
		final I_C_Workplace wpB = InterfaceWrapperHelper.newInstance(I_C_Workplace.class);
		wpB.setName("WP-B");
		wpB.setM_Warehouse_ID(warehouseId.getRepoId());
		wpB.setIsPackingPlace(true);
		wpB.setPickFrom_Locator_ID(L2.getRepoId());
		InterfaceWrapperHelper.saveRecord(wpB);

		// WP-C: isPackingPlace=N, locator L3 — must NOT appear in result
		final I_C_Workplace wpC = InterfaceWrapperHelper.newInstance(I_C_Workplace.class);
		wpC.setName("WP-C");
		wpC.setM_Warehouse_ID(warehouseId.getRepoId());
		wpC.setIsPackingPlace(false);
		wpC.setPickFrom_Locator_ID(L3.getRepoId());
		InterfaceWrapperHelper.saveRecord(wpC);

		// WP-D: isPackingPlace=Y but NO locator — contributes nothing
		final I_C_Workplace wpD = InterfaceWrapperHelper.newInstance(I_C_Workplace.class);
		wpD.setName("WP-D");
		wpD.setM_Warehouse_ID(warehouseId.getRepoId());
		wpD.setIsPackingPlace(true);
		// PickFrom_Locator_ID intentionally not set (defaults to 0 = null)
		InterfaceWrapperHelper.saveRecord(wpD);

		final WorkplaceRepository repo = WorkplaceRepository.newInstanceForUnitTesting();
		final ImmutableSet<LocatorId> result = repo.getPackingPlacePickFromLocatorIds();

		assertThat(result).containsExactlyInAnyOrder(L1, L2);
	}
}
