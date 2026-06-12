/*
 * #%L
 * de.metas.salescandidate.base
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

package de.metas.ordercandidate.api;

import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.order.BPartnerOrderParamsRepository;
import de.metas.ordercandidate.api.impl.OLCandBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.user.UserRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the 3-level warehouse priority: OLCand record → BP picking warehouse → processor default.
 * Regression: before the fix, the BP picking warehouse level was missing,
 * so orders with no OLCand-level warehouse always fell through to the processor default (Stö2)
 * instead of picking up the customer BP's picking warehouse (Ind9).
 */
class OLCandBLGetWarehouseIdTest
{
	private OLCandBL olCandBL;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		olCandBL = new OLCandBL(
				new BPartnerBL(new UserRepository()),
				BPartnerOrderParamsRepository.newInstanceForUnitTesting()
		);
	}

	@Test
	void olCandHasExplicitWarehouse_returnsOLCandWarehouse()
	{
		final WarehouseId processorDefaultId = createWarehouse("Stoe2", false);
		final WarehouseId bpPickingId = createWarehouse("Ind9", true);
		final WarehouseId olCandSpecificId = createWarehouse("Specific", false);

		final I_C_BPartner bp = newInstanceOutOfTrx(I_C_BPartner.class);
		bp.setIsCustomer(true);
		bp.setM_Warehouse_ID(bpPickingId.getRepoId());
		saveRecord(bp);

		final I_C_OLCand olCand = newInstanceOutOfTrx(I_C_OLCand.class);
		olCand.setC_BPartner_ID(bp.getC_BPartner_ID());
		olCand.setM_Warehouse_ID(olCandSpecificId.getRepoId());
		saveRecord(olCand);

		final OLCandOrderDefaults orderDefaults = OLCandOrderDefaults.builder().warehouseId(processorDefaultId).build();

		assertThat(olCandBL.getWarehouseId(olCand, orderDefaults)).isEqualTo(olCandSpecificId);
	}

	@Test
	void olCandNoWarehouse_bpCustomerWithPickingWarehouse_returnsBPPickingWarehouse()
	{
		final WarehouseId processorDefaultId = createWarehouse("Stoe2", false);
		final WarehouseId bpPickingId = createWarehouse("Ind9", true);

		final I_C_BPartner bp = newInstanceOutOfTrx(I_C_BPartner.class);
		bp.setIsCustomer(true);
		bp.setM_Warehouse_ID(bpPickingId.getRepoId());
		saveRecord(bp);

		final I_C_OLCand olCand = newInstanceOutOfTrx(I_C_OLCand.class);
		olCand.setC_BPartner_ID(bp.getC_BPartner_ID());
		// M_Warehouse_ID deliberately not set
		saveRecord(olCand);

		final OLCandOrderDefaults orderDefaults = OLCandOrderDefaults.builder().warehouseId(processorDefaultId).build();

		assertThat(olCandBL.getWarehouseId(olCand, orderDefaults)).isEqualTo(bpPickingId);
	}

	@Test
	void olCandNoWarehouse_bpWarehouseNotPicking_returnsProcessorDefault()
	{
		final WarehouseId processorDefaultId = createWarehouse("Stoe2", false);
		final WarehouseId nonPickingBpWarehouseId = createWarehouse("NonPicking", false);

		final I_C_BPartner bp = newInstanceOutOfTrx(I_C_BPartner.class);
		bp.setIsCustomer(true);
		bp.setM_Warehouse_ID(nonPickingBpWarehouseId.getRepoId());
		saveRecord(bp);

		final I_C_OLCand olCand = newInstanceOutOfTrx(I_C_OLCand.class);
		olCand.setC_BPartner_ID(bp.getC_BPartner_ID());
		saveRecord(olCand);

		final OLCandOrderDefaults orderDefaults = OLCandOrderDefaults.builder().warehouseId(processorDefaultId).build();

		assertThat(olCandBL.getWarehouseId(olCand, orderDefaults)).isEqualTo(processorDefaultId);
	}

	@Test
	void olCandNoWarehouse_bpNotCustomer_returnsProcessorDefault()
	{
		final WarehouseId processorDefaultId = createWarehouse("Stoe2", false);
		final WarehouseId bpPickingId = createWarehouse("Ind9", true);

		final I_C_BPartner bp = newInstanceOutOfTrx(I_C_BPartner.class);
		bp.setIsCustomer(false);
		bp.setM_Warehouse_ID(bpPickingId.getRepoId());
		saveRecord(bp);

		final I_C_OLCand olCand = newInstanceOutOfTrx(I_C_OLCand.class);
		olCand.setC_BPartner_ID(bp.getC_BPartner_ID());
		saveRecord(olCand);

		final OLCandOrderDefaults orderDefaults = OLCandOrderDefaults.builder().warehouseId(processorDefaultId).build();

		assertThat(olCandBL.getWarehouseId(olCand, orderDefaults)).isEqualTo(processorDefaultId);
	}

	@Test
	void olCandNoWarehouse_noBP_returnsProcessorDefault()
	{
		final WarehouseId processorDefaultId = createWarehouse("Stoe2", false);

		final I_C_OLCand olCand = newInstanceOutOfTrx(I_C_OLCand.class);
		// C_BPartner_ID deliberately not set
		saveRecord(olCand);

		final OLCandOrderDefaults orderDefaults = OLCandOrderDefaults.builder().warehouseId(processorDefaultId).build();

		assertThat(olCandBL.getWarehouseId(olCand, orderDefaults)).isEqualTo(processorDefaultId);
	}

	@Test
	void olCandNoWarehouse_bpCustomerWithNoWarehouseSet_returnsProcessorDefault()
	{
		final WarehouseId processorDefaultId = createWarehouse("Stoe2", false);

		final I_C_BPartner bp = newInstanceOutOfTrx(I_C_BPartner.class);
		bp.setIsCustomer(true);
		// M_Warehouse_ID deliberately not set on the BP
		saveRecord(bp);

		final I_C_OLCand olCand = newInstanceOutOfTrx(I_C_OLCand.class);
		olCand.setC_BPartner_ID(bp.getC_BPartner_ID());
		saveRecord(olCand);

		final OLCandOrderDefaults orderDefaults = OLCandOrderDefaults.builder().warehouseId(processorDefaultId).build();

		assertThat(olCandBL.getWarehouseId(olCand, orderDefaults)).isEqualTo(processorDefaultId);
	}

	@Test
	void olCandNoWarehouse_nullOrderDefaults_returnsNull()
	{
		final I_C_OLCand olCand = newInstanceOutOfTrx(I_C_OLCand.class);
		saveRecord(olCand);

		assertThat(olCandBL.getWarehouseId(olCand, null)).isNull();
	}

	private WarehouseId createWarehouse(final String name, final boolean isPickingWarehouse)
	{
		final I_M_Warehouse warehouse = newInstanceOutOfTrx(I_M_Warehouse.class);
		warehouse.setName(name);
		warehouse.setValue(name);
		warehouse.setIsPickingWarehouse(isPickingWarehouse);
		saveRecord(warehouse);
		return WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
	}
}
