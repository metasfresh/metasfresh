package de.metas.inventory.impexp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_I_Inventory;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.business.BusinessTestHelper;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class MInventoryImportTableSqlUpdaterTest
{
	private IWarehouseDAO warehouseDAO;

	private OrgId orgId1;
	@SuppressWarnings("unused")
	private WarehouseId org1_warehouseId;

	private OrgId orgId2;
	private WarehouseId org2_warehouseId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		warehouseDAO = Services.get(IWarehouseDAO.class);

		orgId1 = BusinessTestHelper.createOrgWithTimeZone();
		org1_warehouseId = createWarehouse(orgId1);

		orgId2 = BusinessTestHelper.createOrgWithTimeZone();
		org2_warehouseId = createWarehouse(orgId2);
	}

	private WarehouseId createWarehouse(@NonNull final OrgId orgId)
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setAD_Org_ID(orgId.getRepoId());
		saveRecord(warehouse);
		return WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
	}

	@Nested
	public class getCreateNewMLocator
	{
		@Test
		public void test()
		{
			final I_I_Inventory importRecord = newInstance(I_I_Inventory.class);
			importRecord.setM_Warehouse_ID(org2_warehouseId.getRepoId());
			importRecord.setLocatorValue("org2_warehouseId_locator");
			importRecord.setX("x");
			importRecord.setY("y");
			importRecord.setZ("z");
			importRecord.setX1("x1");

			final LocatorId locatorId = MInventoryImportTableSqlUpdater.getCreateNewMLocator(importRecord);
			final I_M_Locator locator = warehouseDAO.getLocatorById(locatorId);
			assertThat(locator.getAD_Org_ID()).isEqualTo(orgId2.getRepoId());
			assertThat(locator.getM_Warehouse_ID()).isEqualTo(org2_warehouseId.getRepoId());
			assertThat(locator.getX()).isEqualTo("x");
			assertThat(locator.getY()).isEqualTo("y");
			assertThat(locator.getZ()).isEqualTo("z");
			assertThat(locator.getX1()).isEqualTo("x1");
		}
	}
}
