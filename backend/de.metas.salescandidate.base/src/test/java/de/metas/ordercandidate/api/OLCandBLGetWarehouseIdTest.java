/*
 * #%L
 * de.metas.salescandidate.base
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

package de.metas.ordercandidate.api;

import de.metas.bpartner.BPartnerId;
import de.metas.ordercandidate.api.impl.OLCandBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.adempiere.warehouse.spi.impl.WarehouseAdvisor;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link IOLCandBL#getWarehouseId(I_C_OLCand, OLCandOrderDefaults)}.
 * <p>
 * Precedence:
 * 1. OLCand's own M_Warehouse_ID
 * 2. Buyer BP's customer picking warehouse (via WarehouseAdvisor)
 * 3. Processor default (orderDefaults.warehouseId)
 */
@ExtendWith(AdempiereTestWatcher.class)
class OLCandBLGetWarehouseIdTest
{
	private OLCandBL olCandBL;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		// Register the real WarehouseAdvisor so evaluateCustomerPickingWarehouse works
		Services.registerService(IWarehouseAdvisor.class, new WarehouseAdvisor());
		olCandBL = OLCandBL.newInstanceForUnitTesting();
	}

	@Nested
	class getWarehouseId
	{
		/**
		 * OLCand has an explicit M_Warehouse_ID → that warehouse is returned, regardless of BP or defaults.
		 */
		@Test
		void olCandHasExplicitWarehouse_returnsIt()
		{
			final WarehouseId explicitWarehouseId = createWarehouse(false);
			final WarehouseId bpPickingWarehouseId = createWarehouse(true);
			// BP has a picking warehouse — it must be ignored because the OLCand carries its own warehouse.
			final BPartnerId customerBPId = createCustomerBP(true, bpPickingWarehouseId);

			final I_C_OLCand olCand = createOLCand(explicitWarehouseId, customerBPId);

			final OLCandOrderDefaults defaults = OLCandOrderDefaults.builder()
					.warehouseId(createWarehouse(false))
					.build();

			final WarehouseId result = olCandBL.getWarehouseId(olCand, defaults);

			assertThat(result).isEqualTo(explicitWarehouseId);
		}

		/**
		 * OLCand has no warehouse, buyer BP is a customer with a picking warehouse
		 * → the BP's picking warehouse is returned.
		 */
		@Test
		void noOLCandWarehouse_customerBPWithPickingWarehouse_returnsBPWarehouse()
		{
			final WarehouseId bpPickingWarehouseId = createWarehouse(true);
			final BPartnerId customerBPId = createCustomerBP(true, bpPickingWarehouseId);
			final I_C_OLCand olCand = createOLCandNoWarehouse(customerBPId);

			final WarehouseId processorDefault = createWarehouse(false);
			final OLCandOrderDefaults defaults = OLCandOrderDefaults.builder()
					.warehouseId(processorDefault)
					.build();

			final WarehouseId result = olCandBL.getWarehouseId(olCand, defaults);

			assertThat(result).isEqualTo(bpPickingWarehouseId);
		}

		/**
		 * OLCand has no warehouse, BP's warehouse exists but is NOT a picking warehouse
		 * → processor default is returned.
		 */
		@Test
		void bpWarehouseNotPicking_returnsProcessorDefault()
		{
			final WarehouseId nonPickingWarehouseId = createWarehouse(false);
			final BPartnerId customerBPId = createCustomerBP(true, nonPickingWarehouseId);
			final I_C_OLCand olCand = createOLCandNoWarehouse(customerBPId);

			final WarehouseId processorDefault = createWarehouse(false);
			final OLCandOrderDefaults defaults = OLCandOrderDefaults.builder()
					.warehouseId(processorDefault)
					.build();

			final WarehouseId result = olCandBL.getWarehouseId(olCand, defaults);

			assertThat(result).isEqualTo(processorDefault);
		}

		/**
		 * OLCand has no warehouse, BP is NOT a customer → processor default is returned.
		 */
		@Test
		void bpNotCustomer_returnsProcessorDefault()
		{
			final BPartnerId nonCustomerBPId = createCustomerBP(false, null);
			final I_C_OLCand olCand = createOLCandNoWarehouse(nonCustomerBPId);

			final WarehouseId processorDefault = createWarehouse(false);
			final OLCandOrderDefaults defaults = OLCandOrderDefaults.builder()
					.warehouseId(processorDefault)
					.build();

			final WarehouseId result = olCandBL.getWarehouseId(olCand, defaults);

			assertThat(result).isEqualTo(processorDefault);
		}
	}

	// ---- helpers ----

	private WarehouseId createWarehouse(final boolean isPickingWarehouse)
	{
		final I_M_Warehouse warehouse = newInstanceOutOfTrx(I_M_Warehouse.class);
		warehouse.setName("Warehouse-" + isPickingWarehouse);
		warehouse.setIsPickingWarehouse(isPickingWarehouse);
		saveRecord(warehouse);
		return WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
	}

	/**
	 * Creates a BP with the given customer flag and warehouse.
	 */
	private BPartnerId createCustomerBP(final boolean isCustomer, @Nullable final WarehouseId warehouseId)
	{
		final I_C_BPartner bp = newInstanceOutOfTrx(I_C_BPartner.class);
		bp.setIsCustomer(isCustomer);
		if (warehouseId != null)
		{
			bp.setM_Warehouse_ID(warehouseId.getRepoId());
		}
		saveRecord(bp);
		return BPartnerId.ofRepoId(bp.getC_BPartner_ID());
	}

	/**
	 * Creates an OLCand with an explicit warehouse and the given buyer BP (with a location).
	 */
	private I_C_OLCand createOLCand(final WarehouseId warehouseId, final BPartnerId bPartnerId)
	{
		final I_C_BPartner_Location bpLocation = newInstanceOutOfTrx(I_C_BPartner_Location.class);
		bpLocation.setC_BPartner_ID(bPartnerId.getRepoId());
		saveRecord(bpLocation);

		final I_C_OLCand olCand = newInstanceOutOfTrx(I_C_OLCand.class);
		olCand.setM_Warehouse_ID(warehouseId.getRepoId());
		olCand.setC_BPartner_ID(bPartnerId.getRepoId());
		olCand.setC_BPartner_Location_ID(bpLocation.getC_BPartner_Location_ID());
		saveRecord(olCand);
		return olCand;
	}

	/**
	 * Creates an OLCand with no warehouse (M_Warehouse_ID=0) and the given buyer BP with a location.
	 * effectiveValuesBL.getBuyerPartnerInfo uses the C_BPartner_ID field (no override) as the buyer.
	 */
	private I_C_OLCand createOLCandNoWarehouse(final BPartnerId bPartnerId)
	{
		final I_C_BPartner_Location bpLocation = newInstanceOutOfTrx(I_C_BPartner_Location.class);
		bpLocation.setC_BPartner_ID(bPartnerId.getRepoId());
		saveRecord(bpLocation);

		final I_C_OLCand olCand = newInstanceOutOfTrx(I_C_OLCand.class);
		olCand.setM_Warehouse_ID(0);
		olCand.setC_BPartner_ID(bPartnerId.getRepoId());
		olCand.setC_BPartner_Location_ID(bpLocation.getC_BPartner_Location_ID());
		saveRecord(olCand);
		return olCand;
	}
}
