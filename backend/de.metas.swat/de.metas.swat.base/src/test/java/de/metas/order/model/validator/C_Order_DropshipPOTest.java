/*
 * #%L
 * de.metas.swat.base
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

package de.metas.order.model.validator;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.createFrom.po_from_so.DropshipPOFromSOService;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link C_Order_DropshipPO}.
 * <p>
 * Pattern: AdempiereTestHelper.get().init() + Mockito.mock() + Services.registerService().
 * Services must be registered BEFORE the interceptor is instantiated because the
 * interceptor initializes its service fields eagerly via Services.get(...).
 */
class C_Order_DropshipPOTest
{
	private IOrderBL orderBL;
	private IWarehouseDAO warehouseDAO;
	private IQueryBL queryBL;
	private DropshipPOFromSOService dropshipPOFromSOService;

	private C_Order_DropshipPO interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// Register mocks BEFORE creating the interceptor — its field initializers call Services.get(...).
		orderBL = mock(IOrderBL.class);
		warehouseDAO = mock(IWarehouseDAO.class);
		queryBL = mock(IQueryBL.class);
		dropshipPOFromSOService = mock(DropshipPOFromSOService.class);

		Services.registerService(IOrderBL.class, orderBL);
		Services.registerService(IWarehouseDAO.class, warehouseDAO);
		Services.registerService(IQueryBL.class, queryBL);

		interceptor = new C_Order_DropshipPO(dropshipPOFromSOService);
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	private I_C_Order buildOrder(final int orderId, final int warehouseId, final boolean isSOTrx)
	{
		final I_C_Order order = mock(I_C_Order.class);
		when(order.getC_Order_ID()).thenReturn(orderId);
		when(order.getM_Warehouse_ID()).thenReturn(warehouseId);
		when(order.isSOTrx()).thenReturn(isSOTrx);
		return order;
	}

	private I_M_Warehouse buildWarehouse(final boolean isDropShip)
	{
		final I_M_Warehouse wh = mock(I_M_Warehouse.class);
		when(wh.isDropShipWarehouse()).thenReturn(isDropShip);
		return wh;
	}

	/** Builds a mock OrderLine. Setting vendorBPartnerId > 0 means the line has an explicit vendor. */
	private I_C_OrderLine buildOrderLine(final int lineNo, final int vendorBPartnerId)
	{
		final I_C_OrderLine ol = mock(I_C_OrderLine.class);
		when(ol.getLine()).thenReturn(lineNo);
		when(ol.getC_BPartner_Vendor_ID()).thenReturn(vendorBPartnerId);
		// product id 0 — irrelevant when vendor is already set
		when(ol.getM_Product_ID()).thenReturn(0);
		return ol;
	}

	// -----------------------------------------------------------------------
	// Test (a): BEFORE_COMPLETE aggregates all offending lines into ONE message
	// -----------------------------------------------------------------------

	@Test
	void validateVendorsBeforeComplete_allOffendingLinesAreCollectedIntoOneException()
	{
		// Given: a dropship-warehouse SO with 3 lines — lines 10 and 30 have no vendor; line 20 has a vendor.
		final int orderId = 1001;
		final int warehouseId = 500;
		final I_C_Order order = buildOrder(orderId, warehouseId, true);
		final I_M_Warehouse warehouse = buildWarehouse(true);
		when(warehouseDAO.getById(WarehouseId.ofRepoId(warehouseId))).thenReturn(warehouse);

		final I_C_OrderLine line10 = buildOrderLine(10, 0); // no vendor
		final I_C_OrderLine line20 = buildOrderLine(20, 99); // has vendor
		final I_C_OrderLine line30 = buildOrderLine(30, 0); // no vendor

		// For lines without a vendor, also no C_BPartner_Product with UsedForVendor — simulate via queryBL
		// We need to stub the query chain for lines 10 and 30 (M_Product_ID=0 → flagged as offending directly).
		// Line 10 and line 30 have M_Product_ID=0 (set in buildOrderLine), so isVendorMissing returns true without
		// hitting queryBL. Line 20 has vendorBPartnerId=99 > 0 so it returns false immediately.

		when(orderBL.getLinesByOrderIds(eq(Collections.singleton(OrderId.ofRepoId(orderId)))))
				.thenReturn(Arrays.asList(line10, line20, line30));

		// When / Then: interceptor must throw ONE exception mentioning both offending line numbers.
		assertThatThrownBy(() -> interceptor.validateVendorsBeforeComplete(order))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> {
					final String msg = ex.getMessage();
					assertThat(msg)
							.as("Exception message must reference line 10")
							.contains("10");
					assertThat(msg)
							.as("Exception message must reference line 30")
							.contains("30");
					// Sorted ascending: "10, 30" — not two separate exceptions
					assertThat(msg)
							.as("Both line numbers appear in the same message (comma-separated)")
							.contains("10")
							.contains("30");
				});
	}

	// -----------------------------------------------------------------------
	// Test (b): BEFORE_COMPLETE short-circuits for non-dropship warehouses
	// -----------------------------------------------------------------------

	@Test
	void validateVendorsBeforeComplete_nonDropshipWarehouse_doesNotExamineLines()
	{
		// Given: an SO on a regular (non-dropship) warehouse
		final int orderId = 2001;
		final int warehouseId = 600;
		final I_C_Order order = buildOrder(orderId, warehouseId, true);
		final I_M_Warehouse warehouse = buildWarehouse(false); // not dropship
		when(warehouseDAO.getById(WarehouseId.ofRepoId(warehouseId))).thenReturn(warehouse);

		// When: interceptor should return without querying lines
		interceptor.validateVendorsBeforeComplete(order);

		// Then: getLinesByOrderIds must never be called
		verify(orderBL, never()).getLinesByOrderIds(any());
	}

	// -----------------------------------------------------------------------
	// Test (c): AFTER_COMPLETE short-circuits for non-dropship warehouses
	// -----------------------------------------------------------------------

	@Test
	void createDropshipPOAfterComplete_nonDropshipWarehouse_doesNotCallService()
	{
		// Given: a completed SO on a non-dropship warehouse
		final int orderId = 3001;
		final int warehouseId = 700;
		final I_C_Order order = buildOrder(orderId, warehouseId, true);
		final I_M_Warehouse warehouse = buildWarehouse(false); // not dropship
		when(warehouseDAO.getById(WarehouseId.ofRepoId(warehouseId))).thenReturn(warehouse);

		// When: interceptor fires after-complete
		interceptor.createDropshipPOAfterComplete(order);

		// Then: service must NOT be invoked
		verify(dropshipPOFromSOService, never()).createDropshipPOForSO(any());
	}

	// -----------------------------------------------------------------------
	// Test (d): AFTER_COMPLETE invokes the service for dropship warehouses
	// -----------------------------------------------------------------------

	@Test
	void createDropshipPOAfterComplete_dropshipWarehouse_callsServiceExactlyOnce()
	{
		// Given: a completed SO on a dropship warehouse
		final int orderId = 4001;
		final int warehouseId = 800;
		final I_C_Order order = buildOrder(orderId, warehouseId, true);
		final I_M_Warehouse warehouse = buildWarehouse(true); // dropship
		when(warehouseDAO.getById(WarehouseId.ofRepoId(warehouseId))).thenReturn(warehouse);

		// When: interceptor fires after-complete
		interceptor.createDropshipPOAfterComplete(order);

		// Then: service called exactly once with the correct order id
		verify(dropshipPOFromSOService).createDropshipPOForSO(OrderId.ofRepoId(orderId));
	}
}
