/*
 * #%L
 * de.metas.swat.base
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

package de.metas.order.createFrom.po_from_so.impl;

import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.createFrom.po_from_so.IC_Order_CreatePOFromSOsBL;
import de.metas.order.createFrom.po_from_so.PurchaseTypeEnum;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Shipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests that {@link CreatePOLineFromSOLinesAggregator} propagates the SO line's M_Shipper_ID
 * to the generated dropship PO line when the shipper has {@code IsCreateDeliveryPlanning='Y'}.
 *
 * <p>Cases:
 * <ol>
 *   <li>(a) DROPSHIP + SO shipper with IsCreateDeliveryPlanning=Y → PO line inherits SO shipper</li>
 *   <li>(b) DROPSHIP + SO shipper with IsCreateDeliveryPlanning=N → PO line does NOT take SO shipper</li>
 *   <li>(c) DROPSHIP + no SO shipper set → PO line unchanged (M_Shipper_ID=0)</li>
 * </ol>
 */
class CreatePOLineDropshipShipperTest
{
	private static final int SO_SHIPPER_REPO_ID = 42;
	private static final ShipperId SO_SHIPPER_ID = ShipperId.ofRepoId(SO_SHIPPER_REPO_ID);

	private IC_Order_CreatePOFromSOsBL orderCreatePOFromSOsBL;
	private IShipperDAO shipperDAO;
	private IOrderLineBL orderLineBL;
	private IOrderDAO orderDAO;
	private IOrderBL orderBL;
	private IAttributeSetInstanceBL asiBL;

	private I_C_Order purchaseOrder;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// Register mocks BEFORE creating the aggregator — field initializers call Services.get(...).
		orderCreatePOFromSOsBL = mock(IC_Order_CreatePOFromSOsBL.class);
		shipperDAO = mock(IShipperDAO.class);
		orderLineBL = mock(IOrderLineBL.class);
		orderDAO = mock(IOrderDAO.class);
		orderBL = mock(IOrderBL.class);
		asiBL = mock(IAttributeSetInstanceBL.class);

		Services.registerService(IC_Order_CreatePOFromSOsBL.class, orderCreatePOFromSOsBL);
		Services.registerService(IShipperDAO.class, shipperDAO);
		Services.registerService(IOrderLineBL.class, orderLineBL);
		Services.registerService(IOrderDAO.class, orderDAO);
		Services.registerService(IOrderBL.class, orderBL);
		Services.registerService(IAttributeSetInstanceBL.class, asiBL);

		// IC_Order_CreatePOFromSOsBL.getCompositeListener() returns no-op by default
		when(orderCreatePOFromSOsBL.getCompositeListener())
				.thenReturn((poLine, soLine, po) -> { /* no-op */ });

		// IAttributeSetInstanceBL.buildDescription returns empty string (used by the aggregation key builder)
		when(asiBL.buildDescription(any(), org.mockito.ArgumentMatchers.anyBoolean())).thenReturn("");

		// IOrderLineBL.createOrderLine returns a fresh in-memory PO line each time.
		// We must use de.metas.interfaces.I_C_OrderLine (which extends org.compiere.model.I_C_OrderLine)
		// so that InterfaceWrapperHelper.create(line, de.metas.interfaces.I_C_OrderLine.class)
		// inside copyUserIdFromSalesToPurchaseOrderLine() succeeds without a ClassCastException.
		when(orderLineBL.createOrderLine(any(I_C_Order.class)))
				.thenAnswer(invocation -> {
					final de.metas.interfaces.I_C_OrderLine line = newInstance(de.metas.interfaces.I_C_OrderLine.class);
					saveRecord(line);
					return line;
				});

		// Build the purchase order (not SO)
		purchaseOrder = newInstance(I_C_Order.class);
		purchaseOrder.setIsSOTrx(false);
		saveRecord(purchaseOrder);
	}

	// -----------------------------------------------------------------------
	// Case (a): DROPSHIP + SO shipper with IsCreateDeliveryPlanning=Y
	// -----------------------------------------------------------------------

	@Test
	void dropship_dpShipper_poLineInheritsSoShipper()
	{
		// Given: a DP shipper
		final I_M_Shipper dpShipper = newInstance(I_M_Shipper.class);
		dpShipper.setIsCreateDeliveryPlanning(true);
		when(shipperDAO.getById(SO_SHIPPER_ID)).thenReturn(dpShipper);

		// and a SO line with that shipper
		final I_C_OrderLine soLine = buildSoLine(SO_SHIPPER_REPO_ID);

		final CreatePOLineFromSOLinesAggregator agg = newAggregator(PurchaseTypeEnum.DROPSHIP);

		// When
		agg.add(soLine);
		agg.closeAllGroups();

		// Then: the generated PO line carries the SO's M_Shipper_ID
		final I_C_OrderLine poLine = capturedPoLine(agg);
		assertThat(poLine.getM_Shipper_ID())
				.as("DROPSHIP + DP-shipper: PO line must carry the SO shipper")
				.isEqualTo(SO_SHIPPER_REPO_ID);
	}

	// -----------------------------------------------------------------------
	// Case (b): DROPSHIP + SO shipper with IsCreateDeliveryPlanning=N
	// -----------------------------------------------------------------------

	@Test
	void dropship_nonDpShipper_poLineDoesNotInheritSoShipper()
	{
		// Given: a non-DP shipper
		final I_M_Shipper nonDpShipper = newInstance(I_M_Shipper.class);
		nonDpShipper.setIsCreateDeliveryPlanning(false);
		when(shipperDAO.getById(SO_SHIPPER_ID)).thenReturn(nonDpShipper);

		// and a SO line with that shipper
		final I_C_OrderLine soLine = buildSoLine(SO_SHIPPER_REPO_ID);

		final CreatePOLineFromSOLinesAggregator agg = newAggregator(PurchaseTypeEnum.DROPSHIP);

		// When
		agg.add(soLine);
		agg.closeAllGroups();

		// Then: PO line does NOT take the SO shipper
		final I_C_OrderLine poLine = capturedPoLine(agg);
		assertThat(poLine.getM_Shipper_ID())
				.as("DROPSHIP + non-DP-shipper: PO line must NOT inherit the SO shipper")
				.isNotEqualTo(SO_SHIPPER_REPO_ID);
	}

	// -----------------------------------------------------------------------
	// Case (c): DROPSHIP + no SO shipper set
	// -----------------------------------------------------------------------

	@Test
	void dropship_noSoShipper_poLineUnchanged()
	{
		// Given: SO line with no shipper
		final I_C_OrderLine soLine = buildSoLine(/* shipperId= */ 0);

		final CreatePOLineFromSOLinesAggregator agg = newAggregator(PurchaseTypeEnum.DROPSHIP);

		// When
		agg.add(soLine);
		agg.closeAllGroups();

		// Then: PO line has no shipper set (stays 0)
		final I_C_OrderLine poLine = capturedPoLine(agg);
		assertThat(poLine.getM_Shipper_ID())
				.as("DROPSHIP + no SO shipper: PO line M_Shipper_ID must stay 0")
				.isEqualTo(0);
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	private I_C_OrderLine buildSoLine(final int shipperId)
	{
		final I_C_Order salesOrder = newInstance(I_C_Order.class);
		salesOrder.setIsSOTrx(true);
		saveRecord(salesOrder);

		final I_C_OrderLine soLine = newInstance(I_C_OrderLine.class);
		soLine.setC_Order(salesOrder);
		soLine.setM_Shipper_ID(shipperId);
		soLine.setQtyOrdered(java.math.BigDecimal.ONE);
		saveRecord(soLine);
		return soLine;
	}

	private CreatePOLineFromSOLinesAggregator newAggregator(final PurchaseTypeEnum purchaseType)
	{
		final CreatePOLineFromSOLinesAggregator agg = new CreatePOLineFromSOLinesAggregator(
				purchaseOrder,
				I_C_OrderLine.COLUMNNAME_QtyOrdered,
				purchaseType);
		agg.setItemAggregationKeyBuilder(CreatePOLineFromSOLinesAggregationKeyBuilder.INSTANCE);
		agg.setGroupsBufferSize(100);
		return agg;
	}

	/**
	 * Returns the first (and only) PO line that the aggregator created.
	 * The aggregator's {@code purchaseOrderLine2saleOrderLines} map is keyed by PO line instance;
	 * we grab the first entry's key.
	 */
	private I_C_OrderLine capturedPoLine(final CreatePOLineFromSOLinesAggregator agg)
	{
		assertThat(agg.purchaseOrderLine2saleOrderLines)
				.as("Aggregator must have created exactly one PO line group")
				.hasSize(1);
		return agg.purchaseOrderLine2saleOrderLines.keySet().iterator().next();
	}
}
