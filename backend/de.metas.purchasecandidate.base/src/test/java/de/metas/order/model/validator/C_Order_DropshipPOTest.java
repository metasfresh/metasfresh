/*
 * #%L
 * de.metas.purchasecandidate.base
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

import de.metas.bpartner.BPartnerId;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.createFrom.po_from_so.DropshipPOFromSOService;
import de.metas.organization.OrgId;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.purchasecandidate.VendorProductInfoService;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

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
	private static final int ORG_ID = 10;

	private IOrderBL orderBL;
	private IWarehouseDAO warehouseDAO;
	private VendorProductInfoService vendorProductInfoService;
	private DropshipPOFromSOService dropshipPOFromSOService;

	private C_Order_DropshipPO interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// Register mocks BEFORE creating the interceptor — its field initializers call Services.get(...).
		orderBL = mock(IOrderBL.class);
		warehouseDAO = mock(IWarehouseDAO.class);
		vendorProductInfoService = mock(VendorProductInfoService.class);
		dropshipPOFromSOService = mock(DropshipPOFromSOService.class);

		Services.registerService(IOrderBL.class, orderBL);
		Services.registerService(IWarehouseDAO.class, warehouseDAO);

		interceptor = new C_Order_DropshipPO(dropshipPOFromSOService, vendorProductInfoService);
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
		when(order.getAD_Org_ID()).thenReturn(ORG_ID);
		return order;
	}

	private I_M_Warehouse buildWarehouse(final boolean isDropShip)
	{
		final I_M_Warehouse wh = mock(I_M_Warehouse.class);
		when(wh.isDropShipWarehouse()).thenReturn(isDropShip);
		return wh;
	}

	/**
	 * Builds a mock OrderLine with an explicit vendor already set.
	 * Setting vendorBPartnerId > 0 means the line has a pre-set vendor.
	 */
	private I_C_OrderLine buildOrderLineWithVendor(final int lineNo, final int vendorBPartnerId)
	{
		final I_C_OrderLine ol = mock(I_C_OrderLine.class);
		when(ol.getLine()).thenReturn(lineNo);
		when(ol.getC_BPartner_Vendor_ID()).thenReturn(vendorBPartnerId);
		when(ol.getM_Product_ID()).thenReturn(0);
		return ol;
	}

	/**
	 * Builds a mock OrderLine without a vendor, but with a real product (productId > 0).
	 * The VendorProductInfoService lookup can then either succeed or return empty.
	 */
	private I_C_OrderLine buildOrderLineWithProduct(final int lineNo, final int productId)
	{
		final I_C_OrderLine ol = mock(I_C_OrderLine.class);
		when(ol.getLine()).thenReturn(lineNo);
		when(ol.getC_BPartner_Vendor_ID()).thenReturn(0); // no explicit vendor
		when(ol.getM_Product_ID()).thenReturn(productId);
		return ol;
	}

	/**
	 * Builds a minimal real {@link VendorProductInfo} exposing only a vendor ID.
	 * <p>
	 * {@link VendorProductInfo} is a Lombok {@code @Value} (final) class and cannot be Mockito-mocked.
	 * We construct a real instance with dummy values for the fields we don't care about in these tests.
	 */
	private VendorProductInfo buildVendorProductInfo(final int vendorBPartnerId, final int productId)
	{
		final PricingConditions dummyPricingConditions = PricingConditions.builder()
				.validFrom(Instant.EPOCH)
				.build();

		return VendorProductInfo.builder()
				.vendorId(BPartnerId.ofRepoId(vendorBPartnerId))
				.defaultVendor(true)
				.product(ProductAndCategoryAndManufacturerId.of(productId, 1 /* dummy category */, 0))
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.vendorProductNo("TEST-VENDOR-PRODUCT-NO")
				.vendorProductName("Test Vendor Product")
				.pricingConditions(dummyPricingConditions)
				.build();
	}

	// -----------------------------------------------------------------------
	// Test (a): BEFORE_COMPLETE — line with explicit vendor passes unchanged
	// -----------------------------------------------------------------------

	@Test
	void validateVendorsBeforeComplete_lineWithExplicitVendor_passesWithoutLookup()
	{
		// Given: a dropship-warehouse SO with one line that already has a vendor set
		final int orderId = 1001;
		final int warehouseId = 500;
		final I_C_Order order = buildOrder(orderId, warehouseId, true);
		final I_M_Warehouse warehouse = buildWarehouse(true);
		when(warehouseDAO.getById(WarehouseId.ofRepoId(warehouseId))).thenReturn(warehouse);

		final I_C_OrderLine line10 = buildOrderLineWithVendor(10, 99); // has explicit vendor
		when(orderBL.getLinesByOrderIds(eq(Collections.singleton(OrderId.ofRepoId(orderId)))))
				.thenReturn(Collections.singletonList(line10));

		// When: validation runs — must not throw
		interceptor.validateVendorsBeforeComplete(order);

		// Then: vendor lookup service must NOT be called for this line
		verify(vendorProductInfoService, never()).getDefaultVendorProductInfo(any(), any());
	}

	// -----------------------------------------------------------------------
	// Test (b): BEFORE_COMPLETE — no vendor + lookup returns present → auto-fill the line
	// -----------------------------------------------------------------------

	@Test
	void validateVendorsBeforeComplete_noVendorAndLookupSucceeds_setsVendorOnLine()
	{
		// Given: a dropship-warehouse SO with one line that has no vendor but a resolvable product
		final int orderId = 2001;
		final int warehouseId = 500;
		final int productId = 42;
		final int resolvedVendorId = 77;

		final I_C_Order order = buildOrder(orderId, warehouseId, true);
		final I_M_Warehouse warehouse = buildWarehouse(true);
		when(warehouseDAO.getById(WarehouseId.ofRepoId(warehouseId))).thenReturn(warehouse);

		final I_C_OrderLine line10 = buildOrderLineWithProduct(10, productId);
		when(orderBL.getLinesByOrderIds(eq(Collections.singleton(OrderId.ofRepoId(orderId)))))
				.thenReturn(Collections.singletonList(line10));

		final VendorProductInfo vendorInfo = buildVendorProductInfo(resolvedVendorId, productId);
		when(vendorProductInfoService.getDefaultVendorProductInfo(
				ProductId.ofRepoId(productId),
				OrgId.ofRepoId(ORG_ID)))
				.thenReturn(Optional.of(vendorInfo));

		// When: validation runs — must not throw (lookup succeeded)
		interceptor.validateVendorsBeforeComplete(order);

		// Then: the vendor must be written back to the line
		verify(line10).setC_BPartner_Vendor_ID(resolvedVendorId);
	}

	// -----------------------------------------------------------------------
	// Test (c): BEFORE_COMPLETE — no vendor + lookup returns empty → collect as offending
	// -----------------------------------------------------------------------

	@Test
	void validateVendorsBeforeComplete_noVendorAndLookupEmpty_throwsWithOffendingLines()
	{
		// Given: a dropship-warehouse SO with 3 lines — lines 10 and 30 have no vendor and lookup returns empty;
		//        line 20 has an explicit vendor.
		final int orderId = 3001;
		final int warehouseId = 500;
		final int productId10 = 11;
		final int productId30 = 33;

		final I_C_Order order = buildOrder(orderId, warehouseId, true);
		final I_M_Warehouse warehouse = buildWarehouse(true);
		when(warehouseDAO.getById(WarehouseId.ofRepoId(warehouseId))).thenReturn(warehouse);

		final I_C_OrderLine line10 = buildOrderLineWithProduct(10, productId10); // no vendor, lookup empty
		final I_C_OrderLine line20 = buildOrderLineWithVendor(20, 99);           // explicit vendor
		final I_C_OrderLine line30 = buildOrderLineWithProduct(30, productId30); // no vendor, lookup empty

		when(orderBL.getLinesByOrderIds(eq(Collections.singleton(OrderId.ofRepoId(orderId)))))
				.thenReturn(Arrays.asList(line10, line20, line30));

		when(vendorProductInfoService.getDefaultVendorProductInfo(
				ProductId.ofRepoId(productId10),
				OrgId.ofRepoId(ORG_ID)))
				.thenReturn(Optional.empty());
		when(vendorProductInfoService.getDefaultVendorProductInfo(
				ProductId.ofRepoId(productId30),
				OrgId.ofRepoId(ORG_ID)))
				.thenReturn(Optional.empty());

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
					assertThat(msg)
							.as("Both line numbers appear in the same message (comma-separated, sorted ascending)")
							.contains("10")
							.contains("30");
				});
	}

	// -----------------------------------------------------------------------
	// Test (d): BEFORE_COMPLETE short-circuits for non-dropship warehouses
	// -----------------------------------------------------------------------

	@Test
	void validateVendorsBeforeComplete_nonDropshipWarehouse_doesNotExamineLines()
	{
		// Given: an SO on a regular (non-dropship) warehouse
		final int orderId = 4001;
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
	// Test (d2): BEFORE_COMPLETE short-circuits for purchase orders (IsSOTrx=N)
	// -----------------------------------------------------------------------

	@Test
	void validateVendorsBeforeComplete_purchaseOrder_skipsEntirely()
	{
		// Given: a PO (IsSOTrx=N) on a dropship-flagged warehouse — the BEFORE_COMPLETE
		// vendor-validation logic must NOT engage at all (it is SO-only).
		final int orderId = 4501;
		final int warehouseId = 550;
		final I_C_Order order = buildOrder(orderId, warehouseId, false /* isSOTrx */);

		// When: validation runs — must not throw
		interceptor.validateVendorsBeforeComplete(order);

		// Then: no DAO / service interaction beyond the SOTrx check.
		// Specifically: warehouse lookup must NOT happen (the isDropshipWarehouseOrder
		// branch is gated behind the SOTrx check), lines are not fetched, and the
		// vendor-info service is not consulted.
		verify(warehouseDAO, never()).getById(any());
		verify(orderBL, never()).getLinesByOrderIds(any());
		verify(vendorProductInfoService, never()).getDefaultVendorProductInfo(any(), any());
	}

	// -----------------------------------------------------------------------
	// Test (e): AFTER_COMPLETE short-circuits for non-dropship warehouses
	// -----------------------------------------------------------------------

	@Test
	void createDropshipPOAfterComplete_nonDropshipWarehouse_doesNotCallService()
	{
		// Given: a completed SO on a non-dropship warehouse
		final int orderId = 5001;
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
	// Test (f): AFTER_COMPLETE invokes the service for dropship warehouses
	// -----------------------------------------------------------------------

	@Test
	void createDropshipPOAfterComplete_dropshipWarehouse_callsServiceExactlyOnce()
	{
		// Given: a completed SO on a dropship warehouse
		final int orderId = 6001;
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
