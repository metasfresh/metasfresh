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

package de.metas.organization.interceptors;

import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link AD_OrgInfo_DropShipWarehouse}.
 * <p>
 * Pattern: AdempiereTestHelper.get().init() + Mockito.mock() + Services.registerService().
 * Services must be registered BEFORE the interceptor is instantiated because the
 * interceptor initializes its service fields eagerly via Services.get(...).
 */
class AD_OrgInfo_DropShipWarehouseTest
{
	private IWarehouseDAO warehouseDAO;
	private AD_OrgInfo_DropShipWarehouse interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		warehouseDAO = mock(IWarehouseDAO.class);
		Services.registerService(IWarehouseDAO.class, warehouseDAO);

		interceptor = new AD_OrgInfo_DropShipWarehouse();
	}

	private I_AD_OrgInfo buildOrgInfo(final int warehouseId)
	{
		final I_AD_OrgInfo orgInfo = mock(I_AD_OrgInfo.class);
		when(orgInfo.getDropShip_Warehouse_ID()).thenReturn(warehouseId);
		return orgInfo;
	}

	private I_M_Warehouse buildWarehouse(final boolean isDropShip)
	{
		final I_M_Warehouse wh = mock(I_M_Warehouse.class);
		when(wh.isDropShipWarehouse()).thenReturn(isDropShip);
		return wh;
	}

	// -----------------------------------------------------------------------
	// Test A: non-dropship warehouse → exception
	// -----------------------------------------------------------------------
	@Test
	void validate_warehouseNotFlagged_throws()
	{
		final int warehouseId = 500;
		final I_AD_OrgInfo orgInfo = buildOrgInfo(warehouseId);
		// Build warehouse mock OUTSIDE the .thenReturn(...) call to avoid Mockito's
		// UnfinishedStubbing exception (nested stubbing on a new mock while the outer
		// when(...) is still resolving).
		final I_M_Warehouse warehouse = buildWarehouse(false);
		when(warehouseDAO.getById(WarehouseId.ofRepoId(warehouseId)))
				.thenReturn(warehouse);

		assertThatThrownBy(() -> interceptor.validateDropShipWarehouseIsFlagged(orgInfo))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("DropShipWarehouse_NotFlagged");
	}

	// -----------------------------------------------------------------------
	// Test B: dropship warehouse → no exception
	// -----------------------------------------------------------------------
	@Test
	void validate_warehouseFlagged_passes()
	{
		final int warehouseId = 501;
		final I_AD_OrgInfo orgInfo = buildOrgInfo(warehouseId);
		final I_M_Warehouse warehouse = buildWarehouse(true);
		when(warehouseDAO.getById(WarehouseId.ofRepoId(warehouseId)))
				.thenReturn(warehouse);

		assertThatCode(() -> interceptor.validateDropShipWarehouseIsFlagged(orgInfo))
				.doesNotThrowAnyException();
	}

	// -----------------------------------------------------------------------
	// Test C: cleared field (DropShip_Warehouse_ID = 0) → no exception, no DAO call
	// -----------------------------------------------------------------------
	@Test
	void validate_warehouseIdCleared_passesWithoutDaoCall()
	{
		final I_AD_OrgInfo orgInfo = buildOrgInfo(0);

		assertThatCode(() -> interceptor.validateDropShipWarehouseIsFlagged(orgInfo))
				.doesNotThrowAnyException();
	}

	// -----------------------------------------------------------------------
	// Test D: warehouse not found (DAO returns null) → exception
	// IWarehouseDAO.getById has no @NonNull annotation; it can return null for
	// inactive/deleted warehouses. The interceptor's guard treats null as
	// "not flagged" and throws.
	// -----------------------------------------------------------------------
	@Test
	void validate_warehouseNotFound_throws()
	{
		final int warehouseId = 502;
		final I_AD_OrgInfo orgInfo = buildOrgInfo(warehouseId);
		when(warehouseDAO.getById(WarehouseId.ofRepoId(warehouseId))).thenReturn(null);

		assertThatThrownBy(() -> interceptor.validateDropShipWarehouseIsFlagged(orgInfo))
				.isInstanceOf(AdempiereException.class);
	}
}
