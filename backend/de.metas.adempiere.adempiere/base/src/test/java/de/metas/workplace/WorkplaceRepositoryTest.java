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

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	public void isWarnShelfLifeUndercut_roundTrip()
	{
		final WorkplaceRepository repo = WorkplaceRepository.newInstanceForUnitTesting();

		final Workplace withFlagTrue = repo.create(WorkplaceCreateRequest.builder()
				.name("WarnTrue")
				.warehouseId(WarehouseId.ofRepoId(1))
				.warnShelfLifeUndercut(true)
				.build());
		assertTrue(withFlagTrue.isWarnShelfLifeUndercut(), "Workplace created with warnShelfLifeUndercut=true must load back as true");

		final Workplace withFlagFalse = repo.create(WorkplaceCreateRequest.builder()
				.name("WarnFalse")
				.warehouseId(WarehouseId.ofRepoId(1))
				.warnShelfLifeUndercut(false)
				.build());
		assertFalse(withFlagFalse.isWarnShelfLifeUndercut(), "Workplace created with warnShelfLifeUndercut=false must load back as false");
	}
}
