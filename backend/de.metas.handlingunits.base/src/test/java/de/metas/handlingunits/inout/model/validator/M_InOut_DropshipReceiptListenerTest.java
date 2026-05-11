package de.metas.handlingunits.inout.model.validator;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.LUTUCUPair;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.inout.IInOutBL;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_PO_OrderLine_Alloc;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link M_InOut_DropshipReceiptListener}.
 * <p>
 * Style: AdempiereTestHelper.get().init() (provides POJO-backed IQueryBL) + Mockito for BL services.
 * Services must be registered BEFORE instantiating the listener, because the listener
 * initialises its service fields eagerly via {@link Services#get(Class)}.
 */
class M_InOut_DropshipReceiptListenerTest
{
	// --- mocked BL services ---
	private IInOutBL inOutBL;
	private IHUAssignmentDAO huAssignmentDAO;
	private IHandlingUnitsBL handlingUnitsBL;
	private IHUShipmentScheduleBL huShipmentScheduleBL;
	private IShipmentScheduleBL shipmentScheduleBL;

	// Additional services used transitively by CatchWeightHelper.extractQtys
	private IProductBL productBL;
	private IUOMConversionBL uomConversionBL;

	// The instance under test
	private M_InOut_DropshipReceiptListener listener;

	// Shared UOM / product IDs used across tests
	private static final int PRODUCT_REPO_ID = 101;
	private static final int UOM_REPO_ID = 200;
	private static final int PO_ORDER_LINE_REPO_ID = 300;
	private static final int SO_ORDER_LINE_REPO_ID = 400;
	private static final int SCHEDULE_REPO_ID = 500;

	@BeforeEach
	void beforeEach()
	{
		// Init the in-memory ADempiere environment (provides real POJO-backed IQueryBL, etc.)
		AdempiereTestHelper.get().init();

		// Register mocks BEFORE creating the listener — its field initializers call Services.get(...).
		inOutBL = mock(IInOutBL.class);
		huAssignmentDAO = mock(IHUAssignmentDAO.class);
		handlingUnitsBL = mock(IHandlingUnitsBL.class);
		huShipmentScheduleBL = mock(IHUShipmentScheduleBL.class);
		shipmentScheduleBL = mock(IShipmentScheduleBL.class);
		productBL = mock(IProductBL.class);
		uomConversionBL = mock(IUOMConversionBL.class);

		Services.registerService(IInOutBL.class, inOutBL);
		Services.registerService(IHUAssignmentDAO.class, huAssignmentDAO);
		Services.registerService(IHandlingUnitsBL.class, handlingUnitsBL);
		Services.registerService(IHUShipmentScheduleBL.class, huShipmentScheduleBL);
		Services.registerService(IShipmentScheduleBL.class, shipmentScheduleBL);
		Services.registerService(IProductBL.class, productBL);
		Services.registerService(IUOMConversionBL.class, uomConversionBL);

		listener = new M_InOut_DropshipReceiptListener();
	}

	// ---------------------------------------------------------------------------
	// Test 1 — shipments (isSOTrx=true) are ignored immediately
	// ---------------------------------------------------------------------------

	/**
	 * Gate 1: when the InOut is a shipment (isSOTrx=true), the listener must return
	 * immediately without touching any downstream service.
	 */
	@Test
	void onAfterComplete_ignoresShipment()
	{
		// Given
		final I_M_InOut inout = newInstance(I_M_InOut.class);
		inout.setIsSOTrx(true); // it's a shipment, not a receipt
		saveRecord(inout);

		// When
		listener.onAfterComplete(inout);

		// Then: no service was consulted beyond the initial gate check
		verify(inOutBL, never()).retrieveLines(any(), any());
		verify(huAssignmentDAO, never()).retrieveTopLevelHUsForModel(any());
		verify(huShipmentScheduleBL, never()).addQtyPickedAndUpdateHU(any());
	}

	// ---------------------------------------------------------------------------
	// Test 2 — non-dropship PO receipts are ignored immediately
	// ---------------------------------------------------------------------------

	/**
	 * Gate 2: a PO receipt (isSOTrx=false) whose source order is NOT a dropship
	 * order must be ignored without any HU/schedule processing.
	 */
	@Test
	void onAfterComplete_ignoresNonDropshipPO()
	{
		// Given: a receipt whose linked PO is NOT a dropship order
		final I_C_Order nonDropshipOrder = newInstance(I_C_Order.class);
		nonDropshipOrder.setIsDropShip(false);
		saveRecord(nonDropshipOrder);

		final I_M_InOut inout = newInstance(I_M_InOut.class);
		inout.setIsSOTrx(false); // it's a receipt
		inout.setC_Order_ID(nonDropshipOrder.getC_Order_ID());
		saveRecord(inout);

		// When
		listener.onAfterComplete(inout);

		// Then: no lines were processed
		verify(inOutBL, never()).retrieveLines(any(), any());
		verify(huAssignmentDAO, never()).retrieveTopLevelHUsForModel(any());
		verify(huShipmentScheduleBL, never()).addQtyPickedAndUpdateHU(any());
	}

	// ---------------------------------------------------------------------------
	// Test 3 — dropship PO receipt calls addQtyPickedAndUpdateHU per (alloc, HU)
	// ---------------------------------------------------------------------------

	/**
	 * Happy path: a dropship receipt whose PO line has one C_PO_OrderLine_Alloc row
	 * and one assigned HU must result in exactly one call to
	 * {@link IHUShipmentScheduleBL#addQtyPickedAndUpdateHU}.
	 */
	@Test
	void onAfterComplete_dropshipReceipt_callsAddQtyPickedForEachAllocAndHU()
	{
		// Given: a dropship PO
		final I_C_Order dropshipOrder = newInstance(I_C_Order.class);
		dropshipOrder.setIsDropShip(true);
		saveRecord(dropshipOrder);

		final I_M_InOut inout = newInstance(I_M_InOut.class);
		inout.setIsSOTrx(false);
		inout.setC_Order_ID(dropshipOrder.getC_Order_ID());
		saveRecord(inout);

		// Receipt line linked to the PO order line
		final org.compiere.model.I_M_InOutLine baseLine = newInstance(org.compiere.model.I_M_InOutLine.class);
		baseLine.setC_OrderLine_ID(PO_ORDER_LINE_REPO_ID);
		baseLine.setM_Product_ID(PRODUCT_REPO_ID);
		saveRecord(baseLine);

		// Alloc row: PO line → SO line
		final I_C_PO_OrderLine_Alloc allocRecord = newInstance(I_C_PO_OrderLine_Alloc.class);
		allocRecord.setC_PO_OrderLine_ID(PO_ORDER_LINE_REPO_ID);
		allocRecord.setC_SO_OrderLine_ID(SO_ORDER_LINE_REPO_ID);
		allocRecord.setIsActive(true);
		saveRecord(allocRecord);

		// Shipment schedule for the SO line
		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		saveRecord(schedule); // assigned ID by POJO helper

		// Mocked HU with a real VHU so the idempotency check yields false (no prior QtyPicked row)
		final I_M_HU hu = newInstance(I_M_HU.class);
		saveRecord(hu);

		final I_M_HU vhu = newInstance(I_M_HU.class);
		saveRecord(vhu);

		// Stub BL behaviour
		when(inOutBL.retrieveLines(eq(inout), eq(org.compiere.model.I_M_InOutLine.class)))
				.thenReturn(Collections.singletonList(baseLine));

		when(shipmentScheduleBL.getByOrderLineId(OrderLineId.ofRepoId(SO_ORDER_LINE_REPO_ID)))
				.thenReturn(schedule);

		// inOutLine cast (InterfaceWrapperHelper.create is used internally; our POJO baseLine
		// already has the product ID, so huAssignmentDAO just needs the model)
		when(huAssignmentDAO.retrieveTopLevelHUsForModel(any(I_M_InOutLine.class)))
				.thenReturn(Collections.singletonList(hu));

		// Set up HU context + storage chain for CatchWeightHelper.extractQtys
		final IMutableHUContext huContext = mock(IMutableHUContext.class);
		when(handlingUnitsBL.createMutableHUContext(any())).thenReturn(huContext);

		final IHUStorageFactory storageFactory = mock(IHUStorageFactory.class);
		final IHUStorage huStorage = mock(IHUStorage.class);
		final IHUProductStorage productStorage = mock(IHUProductStorage.class);
		when(huContext.getHUStorageFactory()).thenReturn(storageFactory);
		when(storageFactory.getStorage(hu)).thenReturn(huStorage);
		when(huStorage.getProductStorage(ProductId.ofRepoId(PRODUCT_REPO_ID))).thenReturn(productStorage);

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		saveRecord(uom);
		final Quantity tenEach = Quantity.of(BigDecimal.TEN, uom);
		when(productStorage.getQty()).thenReturn(tenEach);

		// ProductBL + conversion stubs
		final UomId stockUomId = UomId.ofRepoId(UOM_REPO_ID);
		when(productBL.getStockUOMId(ProductId.ofRepoId(PRODUCT_REPO_ID))).thenReturn(stockUomId);
		when(uomConversionBL.convertQuantityTo(eq(tenEach), any(UOMConversionContext.class), eq(stockUomId)))
				.thenReturn(tenEach);
		when(productBL.getCatchUOMId(ProductId.ofRepoId(PRODUCT_REPO_ID))).thenReturn(Optional.empty());

		// VHU pair — valid VHU so idempotency check proceeds
		when(handlingUnitsBL.getTopLevelParentAsLUTUCUPair(hu))
				.thenReturn(LUTUCUPair.ofVHU(vhu, null, null));

		// When
		listener.onAfterComplete(inout);

		// Then: one allocation + one HU → exactly one addQtyPickedAndUpdateHU call
		verify(huShipmentScheduleBL).addQtyPickedAndUpdateHU(any());
	}

	// ---------------------------------------------------------------------------
	// Test 4 — idempotency: duplicate (schedule, VHU) is skipped
	// ---------------------------------------------------------------------------

	/**
	 * If a {@code M_ShipmentSchedule_QtyPicked} row already exists for the same
	 * (M_ShipmentSchedule_ID, VHU_ID) pair, the listener must skip the HU and
	 * NOT call {@link IHUShipmentScheduleBL#addQtyPickedAndUpdateHU} again.
	 */
	@Test
	void onAfterComplete_idempotent_skipsDuplicateQtyPickedRow()
	{
		// Given: a dropship PO
		final I_C_Order dropshipOrder = newInstance(I_C_Order.class);
		dropshipOrder.setIsDropShip(true);
		saveRecord(dropshipOrder);

		final I_M_InOut inout = newInstance(I_M_InOut.class);
		inout.setIsSOTrx(false);
		inout.setC_Order_ID(dropshipOrder.getC_Order_ID());
		saveRecord(inout);

		final org.compiere.model.I_M_InOutLine baseLine = newInstance(org.compiere.model.I_M_InOutLine.class);
		baseLine.setC_OrderLine_ID(PO_ORDER_LINE_REPO_ID);
		baseLine.setM_Product_ID(PRODUCT_REPO_ID);
		saveRecord(baseLine);

		final I_C_PO_OrderLine_Alloc allocRecord = newInstance(I_C_PO_OrderLine_Alloc.class);
		allocRecord.setC_PO_OrderLine_ID(PO_ORDER_LINE_REPO_ID);
		allocRecord.setC_SO_OrderLine_ID(SO_ORDER_LINE_REPO_ID);
		allocRecord.setIsActive(true);
		saveRecord(allocRecord);

		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		saveRecord(schedule);

		final I_M_HU hu = newInstance(I_M_HU.class);
		saveRecord(hu);

		final I_M_HU vhu = newInstance(I_M_HU.class);
		saveRecord(vhu);

		// Pre-existing QtyPicked row for the same (schedule, VHU) pair
		final I_M_ShipmentSchedule_QtyPicked existingQtyPicked = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		existingQtyPicked.setM_ShipmentSchedule_ID(schedule.getM_ShipmentSchedule_ID());
		existingQtyPicked.setVHU_ID(vhu.getM_HU_ID());
		existingQtyPicked.setIsActive(true);
		saveRecord(existingQtyPicked);

		// Stub BL behaviour
		when(inOutBL.retrieveLines(eq(inout), eq(org.compiere.model.I_M_InOutLine.class)))
				.thenReturn(Collections.singletonList(baseLine));
		when(shipmentScheduleBL.getByOrderLineId(OrderLineId.ofRepoId(SO_ORDER_LINE_REPO_ID)))
				.thenReturn(schedule);
		when(huAssignmentDAO.retrieveTopLevelHUsForModel(any(I_M_InOutLine.class)))
				.thenReturn(Collections.singletonList(hu));

		final IMutableHUContext huContext = mock(IMutableHUContext.class);
		when(handlingUnitsBL.createMutableHUContext(any())).thenReturn(huContext);

		when(handlingUnitsBL.getTopLevelParentAsLUTUCUPair(hu))
				.thenReturn(LUTUCUPair.ofVHU(vhu, null, null));

		// When
		listener.onAfterComplete(inout);

		// Then: the existing row was detected; addQtyPickedAndUpdateHU must NOT be called
		verify(huShipmentScheduleBL, never()).addQtyPickedAndUpdateHU(any());
	}

	// ---------------------------------------------------------------------------
	// Test 5 — VHU=null skip (C3 fix): bare LU has no VHU → skip, no call
	// ---------------------------------------------------------------------------

	/**
	 * When {@code IHandlingUnitsBL.getTopLevelParentAsLUTUCUPair(hu)} returns a pair
	 * with {@code getVHU() == null} (e.g., a bare LU without an inner VHU), the
	 * listener must log a warning and skip that HU — no call to
	 * {@link IHUShipmentScheduleBL#addQtyPickedAndUpdateHU}.
	 */
	@Test
	void onAfterComplete_vhuIsNull_skipsHuWithoutCallingAddQtyPicked()
	{
		// Given: a dropship PO
		final I_C_Order dropshipOrder = newInstance(I_C_Order.class);
		dropshipOrder.setIsDropShip(true);
		saveRecord(dropshipOrder);

		final I_M_InOut inout = newInstance(I_M_InOut.class);
		inout.setIsSOTrx(false);
		inout.setC_Order_ID(dropshipOrder.getC_Order_ID());
		saveRecord(inout);

		final org.compiere.model.I_M_InOutLine baseLine = newInstance(org.compiere.model.I_M_InOutLine.class);
		baseLine.setC_OrderLine_ID(PO_ORDER_LINE_REPO_ID);
		baseLine.setM_Product_ID(PRODUCT_REPO_ID);
		saveRecord(baseLine);

		final I_C_PO_OrderLine_Alloc allocRecord = newInstance(I_C_PO_OrderLine_Alloc.class);
		allocRecord.setC_PO_OrderLine_ID(PO_ORDER_LINE_REPO_ID);
		allocRecord.setC_SO_OrderLine_ID(SO_ORDER_LINE_REPO_ID);
		allocRecord.setIsActive(true);
		saveRecord(allocRecord);

		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		saveRecord(schedule);

		// The bare LU — no VHU inside
		final I_M_HU bareHU = newInstance(I_M_HU.class);
		saveRecord(bareHU);

		// Stub BL behaviour
		when(inOutBL.retrieveLines(eq(inout), eq(org.compiere.model.I_M_InOutLine.class)))
				.thenReturn(Collections.singletonList(baseLine));
		when(shipmentScheduleBL.getByOrderLineId(OrderLineId.ofRepoId(SO_ORDER_LINE_REPO_ID)))
				.thenReturn(schedule);
		when(huAssignmentDAO.retrieveTopLevelHUsForModel(any(I_M_InOutLine.class)))
				.thenReturn(Collections.singletonList(bareHU));

		final IMutableHUContext huContext = mock(IMutableHUContext.class);
		when(handlingUnitsBL.createMutableHUContext(any())).thenReturn(huContext);

		// Key stub: return a pair with getVHU() == null (bare LU with no VHU)
		when(handlingUnitsBL.getTopLevelParentAsLUTUCUPair(bareHU))
				.thenReturn(LUTUCUPair.ofLU(bareHU)); // ofLU sets VHU = null

		// When
		listener.onAfterComplete(inout);

		// Then: VHU was null → skip → addQtyPickedAndUpdateHU never called
		verify(huShipmentScheduleBL, never()).addQtyPickedAndUpdateHU(any());
	}
}
