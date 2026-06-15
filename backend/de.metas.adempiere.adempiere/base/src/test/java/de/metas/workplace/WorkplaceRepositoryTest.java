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
		assertThat(workplace).isNotNull();

		final Workplace workplace2 = WorkplaceRepository.newInstanceForUnitTesting().create(WorkplaceCreateRequest.builder()
				.name("Test2")
				.warehouseId(WarehouseId.ofRepoId(1))
				.build());
		assertThat(workplace2).isNotNull();
	}

	@Test
	public void create_defaultsToPackingPlace()
	{
		// A workplace created without an explicit IsPackingPlace must default to a packing place ('Y'),
		// matching the C_Workplace.IsPackingPlace DB column default and preserving the pre-change launcher
		// behaviour (me03 #30429 AC2). A 'N' default would silently flip every request-created workplace
		// into the replenishment role.
		final Workplace workplace = WorkplaceRepository.newInstanceForUnitTesting().create(WorkplaceCreateRequest.builder()
				.name("default-role")
				.warehouseId(WarehouseId.ofRepoId(1))
				.build());
		assertThat(workplace.isPackingPlace()).isTrue();

		final Workplace replenishment = WorkplaceRepository.newInstanceForUnitTesting().create(WorkplaceCreateRequest.builder()
				.name("explicit-replenishment")
				.warehouseId(WarehouseId.ofRepoId(1))
				.isPackingPlace(false)
				.build());
		assertThat(replenishment.isPackingPlace()).isFalse();
	}

	@Test
	public void getPackingPlacePickFromLocatorIds_returnsOnlyPackingPlacesWithLocator()
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoId(1);
		final LocatorId L1 = LocatorId.ofRepoId(warehouseId, 101);
		final LocatorId L2 = LocatorId.ofRepoId(warehouseId, 102);
		final LocatorId L3 = LocatorId.ofRepoId(warehouseId, 103);

		// WP-A: isPackingPlace=Y, locator L1
		saveWorkplace("WP-A", warehouseId, true, L1.getRepoId());
		// WP-B: isPackingPlace=Y, locator L2
		saveWorkplace("WP-B", warehouseId, true, L2.getRepoId());
		// WP-C: isPackingPlace=N, locator L3 — must NOT appear in result
		saveWorkplace("WP-C", warehouseId, false, L3.getRepoId());
		// WP-D: isPackingPlace=Y but NO locator — contributes nothing
		saveWorkplace("WP-D", warehouseId, true, 0);

		final ImmutableSet<LocatorId> result = WorkplaceRepository.newInstanceForUnitTesting().getPackingPlacePickFromLocatorIds();

		assertThat(result).containsExactlyInAnyOrder(L1, L2);
	}

	private static void saveWorkplace(
			final String name,
			final WarehouseId warehouseId,
			final boolean isPackingPlace,
			final int pickFromLocatorRepoId)
	{
		final I_C_Workplace record = InterfaceWrapperHelper.newInstance(I_C_Workplace.class);
		record.setName(name);
		record.setM_Warehouse_ID(warehouseId.getRepoId());
		record.setIsPackingPlace(isPackingPlace);
		record.setPickFrom_Locator_ID(pickFromLocatorRepoId);
		InterfaceWrapperHelper.saveRecord(record);
	}
}
