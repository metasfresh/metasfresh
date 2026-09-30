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

package de.metas.workplace;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Workplace;
import org.compiere.model.I_M_Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers {@link WorkplaceService#getWorkplaceIdsByEffectivePickFromLocatorId(LocatorId)} — the locator → workplaces
 * direction that must stay the exact inverse of
 * {@link WorkplaceService#getPickFromLocatorIdOrWarehouseDefault(Workplace)}, fallback included.
 */
class WorkplaceServiceTest
{
	private static final WarehouseId warehouseId = WarehouseId.ofRepoId(1_000_001);
	private static final WarehouseId otherWarehouseId = WarehouseId.ofRepoId(1_000_002);

	private WorkplaceService workplaceService;
	private LocatorId configuredLocatorId;
	private LocatorId defaultLocatorId;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		workplaceService = new WorkplaceService(new WorkplaceRepository(), new WorkplaceUserAssignRepository());

		configuredLocatorId = createLocator(warehouseId, "configured", false);
		defaultLocatorId = createLocator(warehouseId, "default", true);
		// the other warehouse gets its own default locator, so a workplace of that warehouse resolves to it
		createLocator(otherWarehouseId, "default-other", true);
	}

	private static LocatorId createLocator(@NonNull final WarehouseId warehouseId, @NonNull final String value, final boolean isDefault)
	{
		final I_M_Locator record = InterfaceWrapperHelper.newInstance(I_M_Locator.class);
		record.setM_Warehouse_ID(warehouseId.getRepoId());
		record.setValue(value);
		record.setIsDefault(isDefault);
		InterfaceWrapperHelper.saveRecord(record);
		return LocatorId.ofRepoId(warehouseId, record.getM_Locator_ID());
	}

	private static WorkplaceId createWorkplace(
			@NonNull final String name,
			@NonNull final WarehouseId warehouseId,
			@Nullable final LocatorId pickFromLocatorId)
	{
		final I_C_Workplace record = InterfaceWrapperHelper.newInstance(I_C_Workplace.class);
		record.setName(name);
		record.setM_Warehouse_ID(warehouseId.getRepoId());
		record.setPickFrom_Locator_ID(LocatorId.toRepoId(pickFromLocatorId));
		InterfaceWrapperHelper.saveRecord(record);
		return WorkplaceId.ofRepoId(record.getC_Workplace_ID());
	}

	/** A workplace with an explicitly configured {@code PickFrom_Locator_ID} is found by that locator, and by no other. */
	@Test
	void getWorkplaceIdsByEffectivePickFromLocatorId_findsTheConfiguredWorkplace()
	{
		final WorkplaceId configured = createWorkplace("configured", warehouseId, configuredLocatorId);
		createWorkplace("on-the-default", warehouseId, defaultLocatorId);

		assertThat(workplaceService.getWorkplaceIdsByEffectivePickFromLocatorId(configuredLocatorId))
				.containsExactly(configured);
	}

	@Test
	void getWorkplaceIdsByEffectivePickFromLocatorId_includesWorkplacesFallingBackToTheWarehouseDefault()
	{
		final WorkplaceId fallback1 = createWorkplace("fallback-1", warehouseId, null);
		final WorkplaceId fallback2 = createWorkplace("fallback-2", warehouseId, null);
		final WorkplaceId configuredOnTheDefault = createWorkplace("configured-on-the-default", warehouseId, defaultLocatorId);
		createWorkplace("configured-elsewhere", warehouseId, configuredLocatorId);
		createWorkplace("other-warehouse-fallback", otherWarehouseId, null);

		assertThat(workplaceService.getWorkplaceIdsByEffectivePickFromLocatorId(defaultLocatorId))
				.containsExactlyInAnyOrderElementsOf(ImmutableSet.of(fallback1, fallback2, configuredOnTheDefault));
	}
}
