package org.adempiere.warehouse.spi.impl;

import de.metas.bpartner.BPartnerId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
public class WarehouseAdvisorTest
{
	private WarehouseAdvisor warehouseAdvisor;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		warehouseAdvisor = new WarehouseAdvisor();
	}

	@Nested
	class evaluateCustomerPickingWarehouse
	{
		@Test
		void customerBP_pickingWarehouse_returnsWarehouse()
		{
			// given
			final I_M_Warehouse warehouse = newInstanceOutOfTrx(I_M_Warehouse.class);
			warehouse.setName("PickingWH");
			warehouse.setValue("PickingWH");
			warehouse.setIsPickingWarehouse(true);
			saveRecord(warehouse);
			final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());

			final I_C_BPartner bp = newInstanceOutOfTrx(I_C_BPartner.class);
			bp.setName("CustomerBP");
			bp.setValue("CustomerBP");
			bp.setIsCustomer(true);
			bp.setM_Warehouse_ID(warehouseId.getRepoId());
			saveRecord(bp);
			final BPartnerId bpartnerId = BPartnerId.ofRepoId(bp.getC_BPartner_ID());

			// when
			final WarehouseId result = warehouseAdvisor.evaluateCustomerPickingWarehouse(bpartnerId);

			// then
			assertThat(result).isEqualTo(warehouseId);
		}

		@Test
		void customerBP_nonPickingWarehouse_returnsNull()
		{
			// given
			final I_M_Warehouse warehouse = newInstanceOutOfTrx(I_M_Warehouse.class);
			warehouse.setName("NonPickingWH");
			warehouse.setValue("NonPickingWH");
			warehouse.setIsPickingWarehouse(false);
			saveRecord(warehouse);
			final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());

			final I_C_BPartner bp = newInstanceOutOfTrx(I_C_BPartner.class);
			bp.setName("CustomerBP2");
			bp.setValue("CustomerBP2");
			bp.setIsCustomer(true);
			bp.setM_Warehouse_ID(warehouseId.getRepoId());
			saveRecord(bp);
			final BPartnerId bpartnerId = BPartnerId.ofRepoId(bp.getC_BPartner_ID());

			// when
			final WarehouseId result = warehouseAdvisor.evaluateCustomerPickingWarehouse(bpartnerId);

			// then
			assertThat(result).isNull();
		}

		@Test
		void nonCustomerBP_pickingWarehouse_returnsNull()
		{
			// given
			final I_M_Warehouse warehouse = newInstanceOutOfTrx(I_M_Warehouse.class);
			warehouse.setName("PickingWH2");
			warehouse.setValue("PickingWH2");
			warehouse.setIsPickingWarehouse(true);
			saveRecord(warehouse);
			final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());

			final I_C_BPartner bp = newInstanceOutOfTrx(I_C_BPartner.class);
			bp.setName("NonCustomerBP");
			bp.setValue("NonCustomerBP");
			bp.setIsCustomer(false);
			bp.setM_Warehouse_ID(warehouseId.getRepoId());
			saveRecord(bp);
			final BPartnerId bpartnerId = BPartnerId.ofRepoId(bp.getC_BPartner_ID());

			// when
			final WarehouseId result = warehouseAdvisor.evaluateCustomerPickingWarehouse(bpartnerId);

			// then
			assertThat(result).isNull();
		}

		@Test
		void customerBP_noWarehouse_returnsNull()
		{
			// given
			final I_C_BPartner bp = newInstanceOutOfTrx(I_C_BPartner.class);
			bp.setName("CustomerBP3");
			bp.setValue("CustomerBP3");
			bp.setIsCustomer(true);
			// no M_Warehouse_ID set
			saveRecord(bp);
			final BPartnerId bpartnerId = BPartnerId.ofRepoId(bp.getC_BPartner_ID());

			// when
			final WarehouseId result = warehouseAdvisor.evaluateCustomerPickingWarehouse(bpartnerId);

			// then
			assertThat(result).isNull();
		}
	}
}
