/*
 * #%L
 * de.metas.purchasecandidate.base
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

package de.metas.order.model.validator;

import de.metas.order.IOrderBL;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.createFrom.po_from_so.DropshipPOFromSOService;
import de.metas.order.createFrom.po_from_so.IC_Order_CreatePOFromSOsBL;
import de.metas.order.createFrom.po_from_so.impl.C_Order_CreatePOFromSOsBL;
import de.metas.purchasecandidate.VendorProductInfoService;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The SysConfig {@code de.metas.order.C_Order_CreatePOFromSOs.CreateDropshipPO} (default {@code N}) gates whether
 * {@link C_Order_DropshipPO} creates a purchase order for a sales order on a drop-ship warehouse.
 */
class C_Order_DropshipPO_CreateFlagTest
{
	private static final int ORDER_ID = 1001;
	private static final int WAREHOUSE_ID = 500;

	private IC_Order_CreatePOFromSOsBL orderCreatePOFromSOsBL;
	private DropshipPOFromSOService dropshipPOFromSOService;
	private OrderGroupRepository orderGroupRepository;
	private IOrderBL orderBL;
	private I_C_Order order;

	private C_Order_DropshipPO interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		orderBL = mock(IOrderBL.class);
		final IWarehouseDAO warehouseDAO = mock(IWarehouseDAO.class);
		orderCreatePOFromSOsBL = mock(IC_Order_CreatePOFromSOsBL.class);
		dropshipPOFromSOService = mock(DropshipPOFromSOService.class);
		orderGroupRepository = mock(OrderGroupRepository.class);
		Services.registerService(IOrderBL.class, orderBL);
		Services.registerService(IWarehouseDAO.class, warehouseDAO);
		Services.registerService(IC_Order_CreatePOFromSOsBL.class, orderCreatePOFromSOsBL);

		final I_M_Warehouse dropshipWarehouse = mock(I_M_Warehouse.class);
		when(dropshipWarehouse.isDropShipWarehouse()).thenReturn(true);
		when(warehouseDAO.getById(WarehouseId.ofRepoId(WAREHOUSE_ID))).thenReturn(dropshipWarehouse);

		order = mock(I_C_Order.class);
		when(order.getC_Order_ID()).thenReturn(ORDER_ID);
		when(order.getM_Warehouse_ID()).thenReturn(WAREHOUSE_ID);
		when(order.isSOTrx()).thenReturn(true);

		interceptor = new C_Order_DropshipPO(dropshipPOFromSOService, mock(VendorProductInfoService.class), orderGroupRepository);
	}

	@Test
	void createFlagOff_createDropshipPOAfterComplete_createsNoPurchaseOrder()
	{
		when(orderCreatePOFromSOsBL.isCreateDropshipPO()).thenReturn(false);

		interceptor.createDropshipPOAfterComplete(order);

		verify(dropshipPOFromSOService, never()).createDropshipPOForSO(any());
	}

	@Test
	void createFlagOff_validateVendorsBeforeComplete_isNoOp()
	{
		when(orderCreatePOFromSOsBL.isCreateDropshipPO()).thenReturn(false);

		interceptor.validateVendorsBeforeComplete(order);

		verify(orderBL, never()).getLinesByOrderIds(any());
	}

	@Test
	void createFlagOn_createDropshipPOAfterComplete_createsPurchaseOrder()
	{
		when(orderCreatePOFromSOsBL.isCreateDropshipPO()).thenReturn(true);

		interceptor.createDropshipPOAfterComplete(order);

		verify(dropshipPOFromSOService).createDropshipPOForSO(order);
	}

	@Test
	void createFlag_absentSysConfig_resolvesToN()
	{
		AdempiereTestHelper.get().init(); // clean DB and services: no AD_SysConfig row exists
		assertThat(Services.get(ISysConfigBL.class).getBooleanValue("de.metas.order.C_Order_CreatePOFromSOs.CreateDropshipPO", true))
				.as("sanity: the key is absent, so the passed default applies")
				.isTrue();

		assertThat(new C_Order_CreatePOFromSOsBL().isCreateDropshipPO()).isFalse();
	}
}
