package de.metas.inoutcandidate.invalidation.impl;

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

import com.google.common.collect.ImmutableSetMultimap;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleSegments;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.inoutcandidate.picking_bom.PickingBOMsReversedIndex;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Regression guard for {@link ShipmentScheduleInvalidateBL#explodeByPickingBOMs(IShipmentScheduleSegment)}.
 * <p>
 * Since the warehouse-identity model (a segment carries a {@code warehouseId} instead of enumerating the
 * warehouse's locators), the {@code notifySegmentChangedFor*} callers build <b>warehouse-only</b> segments
 * (empty {@code locatorIds}). When such a segment's product has a Picking-BOM parent, {@code explodeByPickingBOMs}
 * must reconstruct the BOM-parent segment carrying the <b>same warehouse scope</b> — otherwise the reconstructed
 * segment has both {@code locatorIds} and {@code warehouseIds} empty, is {@link IShipmentScheduleSegment#isInvalid()},
 * and is silently dropped from the recompute WHERE clause: the BOM-parent product's shipment schedules in that
 * warehouse are then never invalidated (silent invalidation loss).
 */
class ShipmentScheduleInvalidateBLTest
{
	private static final ProductId COMPONENT_PRODUCT_ID = ProductId.ofRepoId(1_000_001);
	private static final ProductId BOM_PARENT_PRODUCT_ID = ProductId.ofRepoId(1_000_002);
	private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(2_000_001);
	private static final int LOCATOR_ID = 3_000_001;

	private ShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final PickingBOMService pickingBOMService = mock(PickingBOMService.class);
		final PickingBOMsReversedIndex reversedIndex = PickingBOMsReversedIndex.ofBOMProductIdsByComponentId(
				ImmutableSetMultimap.of(COMPONENT_PRODUCT_ID, BOM_PARENT_PRODUCT_ID));
		when(pickingBOMService.getPickingBOMsReversedIndex()).thenReturn(reversedIndex);

		shipmentScheduleInvalidateBL = new ShipmentScheduleInvalidateBL(pickingBOMService);
	}

	@Nested
	class ExplodeByPickingBOMs
	{
		@Test
		void warehouseDerivedSegment_bomParentSegmentKeepsWarehouseScope()
		{
			// A warehouse-derived source segment, exactly as createSegmentForShipmentSchedule builds it:
			// warehouse identity set, no locators.
			final IShipmentScheduleSegment warehouseSegment = ShipmentScheduleSegments.builder()
					.bpartnerId(0)
					.productId(COMPONENT_PRODUCT_ID)
					.warehouseId(WAREHOUSE_ID)
					.attributeSetInstanceId(0)
					.build();

			final List<IShipmentScheduleSegment> exploded = shipmentScheduleInvalidateBL
					.explodeByPickingBOMs(warehouseSegment)
					.collect(Collectors.toList());

			// The BOM-parent segment (the exploded one carrying the BOM-parent product) must remain a VALID,
			// warehouse-scoped segment — otherwise it is silently dropped and the BOM parent is never invalidated.
			final IShipmentScheduleSegment bomParentSegment = exploded.stream()
					.filter(s -> s.getProductIds().contains(BOM_PARENT_PRODUCT_ID.getRepoId()))
					.findFirst()
					.orElseThrow(() -> new AssertionError("expected an exploded segment for the picking-BOM parent product"));

			assertThat(bomParentSegment.isInvalid())
					.as("BOM-parent segment must not be invalid (both locatorIds and warehouseIds empty ⇒ dropped)")
					.isFalse();
			assertThat(bomParentSegment.getWarehouseIds())
					.as("BOM-parent segment must inherit the source segment's warehouse scope")
					.containsExactly(WAREHOUSE_ID.getRepoId());
		}

		@Test
		void locatorDerivedSegment_bomParentSegmentKeepsLocatorScope()
		{
			// Symmetrical guard for the locator-scoped branch (pre-existing behaviour): a locator-derived
			// source segment must explode into a valid, locator-scoped BOM-parent segment.
			final IShipmentScheduleSegment locatorSegment = ShipmentScheduleSegments.builder()
					.bpartnerId(0)
					.productId(COMPONENT_PRODUCT_ID)
					.locatorId(LOCATOR_ID)
					.attributeSetInstanceId(0)
					.build();

			final IShipmentScheduleSegment bomParentSegment = shipmentScheduleInvalidateBL
					.explodeByPickingBOMs(locatorSegment)
					.collect(Collectors.toList())
					.stream()
					.filter(s -> s.getProductIds().contains(BOM_PARENT_PRODUCT_ID.getRepoId()))
					.findFirst()
					.orElseThrow(() -> new AssertionError("expected an exploded segment for the picking-BOM parent product"));

			assertThat(bomParentSegment.isInvalid()).isFalse();
			assertThat(bomParentSegment.getLocatorIds()).containsExactly(LOCATOR_ID);
			assertThat(bomParentSegment.getWarehouseIds()).isEmpty();
		}
	}

	/**
	 * RED guard for #31050's durable fix (revised approach "Option A"): the three product-specific
	 * {@code notifySegmentChangedFor*} entry points must route a <b>non-stocked, non-picking-BOM-component</b>
	 * product change to the already-existing self-by-id invalidation ({@link ShipmentScheduleInvalidateBL#invalidateJustForOrderLine},
	 * {@link ShipmentScheduleInvalidateBL#flagForRecompute(ShipmentScheduleId)}, {@link ShipmentScheduleInvalidateBL#flagForRecompute(I_M_InOutLine)})
	 * instead of the broad product+warehouse segment ({@link ShipmentScheduleInvalidateBL#notifySegmentChanged(IShipmentScheduleSegment)}).
	 * A non-stocked product that IS a picking-BOM component must keep the broad path (so the BOM-parent's
	 * schedules are still reached via {@code explodeByPickingBOMs}).
	 * <p>
	 * Discriminator under test: {@code !IProductBL.isStocked(productId) && pickingBOMsReversedIndex.getBOMProductIdsByComponentId(productId).isEmpty()}
	 * (AC-D3: the Item+IsStocked composite via {@code IProductBL}, never the raw {@code M_Product.IsStocked} column alone).
	 * <p>
	 * These tests verify ROUTING (which internal method fires), not the raw SQL side effects: {@code ShipmentScheduleInvalidateBL}'s
	 * only real collaborator boundary in this test class is {@link PickingBOMService} (mocked, as in {@link ExplodeByPickingBOMs}
	 * above); the broad/narrow terminal methods are stubbed out on a Mockito spy of the real instance so the guard's decision
	 * is pinned without depending on the (non-hermetic, raw-SQL-backed) {@code IShipmentScheduleInvalidateRepository}.
	 * <p>
	 * FAILS on current code for the non-stocked (non-BOM-component) cases: {@code notifySegmentChangedFor*} unconditionally
	 * takes the broad segment path today, regardless of {@code IsStocked}.
	 */
	@Nested
	class NonStockedNarrowing
	{
		private final PickingBOMsReversedIndex NO_BOM_COMPONENTS =
				PickingBOMsReversedIndex.ofBOMProductIdsByComponentId(ImmutableSetMultimap.of());

		@BeforeEach
		void registerNotRunningUpdater()
		{
			// notifySegmentChangedForShipmentSchedule early-exits when the shipment-schedule updater is running
			// (ShipmentScheduleInvalidateBL#isShipmentScheduleUpdaterRunning, production line ~214). Stub it as
			// NOT running so the routing under test (broad vs. self-by-id) is actually reached.
			final IShipmentScheduleUpdater updater = mock(IShipmentScheduleUpdater.class);
			when(updater.isRunning()).thenReturn(false);
			Services.registerService(IShipmentScheduleUpdater.class, updater);
		}

		private I_M_Product createProduct(final String name, final boolean isStocked, final String productType)
		{
			final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
			product.setValue(name);
			product.setName(name);
			product.setProductType(productType);
			product.setIsStocked(isStocked);
			InterfaceWrapperHelper.save(product);
			return product;
		}

		private I_M_Warehouse createWarehouse(final String name)
		{
			final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
			warehouse.setValue(name);
			warehouse.setName(name);
			InterfaceWrapperHelper.save(warehouse);
			return warehouse;
		}

		private I_M_Locator createLocator(final I_M_Warehouse warehouse)
		{
			final I_M_Locator locator = InterfaceWrapperHelper.newInstance(I_M_Locator.class);
			locator.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
			locator.setValue(warehouse.getName() + "_Locator");
			locator.setX("X");
			locator.setY("Y");
			locator.setZ("Z");
			InterfaceWrapperHelper.save(locator);
			return locator;
		}

		private I_C_OrderLine createOrderLine(final I_M_Product product, final I_M_Warehouse warehouse)
		{
			final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
			order.setIsSOTrx(true);
			InterfaceWrapperHelper.save(order);

			final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
			orderLine.setC_Order(order);
			orderLine.setM_Product_ID(product.getM_Product_ID());
			orderLine.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
			InterfaceWrapperHelper.save(orderLine);
			return orderLine;
		}

		private I_M_ShipmentSchedule createShipmentSchedule(final I_M_Product product, final I_M_Warehouse warehouse)
		{
			final I_M_ShipmentSchedule schedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
			schedule.setM_Product_ID(product.getM_Product_ID());
			schedule.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
			InterfaceWrapperHelper.save(schedule);
			return schedule;
		}

		private I_M_InOutLine createInOutLine(final I_M_Product product, final I_M_Locator locator)
		{
			final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
			inout.setIsSOTrx(true);
			InterfaceWrapperHelper.save(inout);

			final I_M_InOutLine inoutLine = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class);
			inoutLine.setM_InOut(inout);
			inoutLine.setM_Product_ID(product.getM_Product_ID());
			inoutLine.setM_Locator_ID(locator.getM_Locator_ID());
			InterfaceWrapperHelper.save(inoutLine);
			return inoutLine;
		}

		/** Fresh {@link ShipmentScheduleInvalidateBL} spy wired with the given picking-BOM reversed index. */
		private ShipmentScheduleInvalidateBL newInvalidateBLSpy(final PickingBOMsReversedIndex reversedIndex)
		{
			final PickingBOMService pickingBOMService = mock(PickingBOMService.class);
			when(pickingBOMService.getPickingBOMsReversedIndex()).thenReturn(reversedIndex);
			return spy(new ShipmentScheduleInvalidateBL(pickingBOMService));
		}

		@Test
		void nonStocked_orderLineChange_invalidatesOnlyOwnSchedule()
		{
			// IsStocked=false on an Item product: exercises the composite IProductBL#isStocked (AC-D3), not the raw column alone.
			final I_M_Product nonStockedProduct = createProduct("NonStockedItem-OL", false, X_M_Product.PRODUCTTYPE_Item);
			final I_M_Warehouse warehouse = createWarehouse("WH-NS-OL");
			final I_C_OrderLine orderLine = createOrderLine(nonStockedProduct, warehouse);

			final ShipmentScheduleInvalidateBL bl = newInvalidateBLSpy(NO_BOM_COMPONENTS);
			doNothing().when(bl).invalidateJustForOrderLine(any());
			doNothing().when(bl).notifySegmentChanged(any());

			bl.notifySegmentChangedForOrderLine(orderLine);

			verify(bl, times(1))
					.invalidateJustForOrderLine(orderLine);
			verify(bl, never())
					.notifySegmentChanged(any());
		}

		@Test
		void nonStocked_shipmentScheduleChange_invalidatesOnlyOwnSchedule()
		{
			final I_M_Product nonStockedProduct = createProduct("NonStockedItem-Sched", false, X_M_Product.PRODUCTTYPE_Item);
			final I_M_Warehouse warehouse = createWarehouse("WH-NS-Sched");
			final I_M_ShipmentSchedule schedule = createShipmentSchedule(nonStockedProduct, warehouse);
			final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());

			final ShipmentScheduleInvalidateBL bl = newInvalidateBLSpy(NO_BOM_COMPONENTS);
			doNothing().when(bl).flagForRecompute(any(ShipmentScheduleId.class));
			doNothing().when(bl).notifySegmentChanged(any());

			bl.notifySegmentChangedForShipmentSchedule(schedule);

			verify(bl, times(1))
					.flagForRecompute(scheduleId);
			verify(bl, never())
					.notifySegmentChanged(any());
		}

		@Test
		void nonStocked_shipmentLineChange_invalidatesOnlyOwnSchedule()
		{
			final I_M_Product nonStockedProduct = createProduct("NonStockedItem-Line", false, X_M_Product.PRODUCTTYPE_Item);
			final I_M_Warehouse warehouse = createWarehouse("WH-NS-Line");
			final I_M_Locator locator = createLocator(warehouse);
			final I_M_InOutLine inoutLine = createInOutLine(nonStockedProduct, locator);

			final ShipmentScheduleInvalidateBL bl = newInvalidateBLSpy(NO_BOM_COMPONENTS);
			doNothing().when(bl).flagForRecompute(any(I_M_InOutLine.class));
			doNothing().when(bl).notifySegmentChanged(any());

			bl.notifySegmentChangedForShipmentLine(inoutLine);

			verify(bl, times(1))
					.flagForRecompute(inoutLine);
			verify(bl, never())
					.notifySegmentChanged(any());
		}

		/** Control: a STOCKED product must keep the broad segment path (pre-existing behaviour, unchanged by Option A). */
		@Test
		void stocked_orderLineChange_invalidatesFullSegment()
		{
			final I_M_Product stockedProduct = createProduct("StockedItem-OL", true, X_M_Product.PRODUCTTYPE_Item);
			final I_M_Warehouse warehouse = createWarehouse("WH-Stocked-OL");
			final I_C_OrderLine orderLine = createOrderLine(stockedProduct, warehouse);

			final ShipmentScheduleInvalidateBL bl = newInvalidateBLSpy(NO_BOM_COMPONENTS);
			doNothing().when(bl).invalidateJustForOrderLine(any());
			doNothing().when(bl).notifySegmentChanged(any());

			bl.notifySegmentChangedForOrderLine(orderLine);

			verify(bl, times(1))
					.notifySegmentChanged(any());
			verify(bl, never())
					.invalidateJustForOrderLine(any());
		}

		/**
		 * Option-A carve-out guard: a non-stocked product that IS a picking-BOM component must KEEP the broad
		 * segment path (NOT narrow to self) — otherwise the BOM-parent's shipment schedules would never be
		 * re-invalidated via {@code explodeByPickingBOMs}.
		 */
		@Test
		void nonStocked_butPickingBOMComponent_orderLineChange_invalidatesFullSegment()
		{
			final I_M_Product nonStockedBomComponent = createProduct("NonStockedBomComponent-OL", false, X_M_Product.PRODUCTTYPE_Item);
			final ProductId componentProductId = ProductId.ofRepoId(nonStockedBomComponent.getM_Product_ID());
			final I_M_Warehouse warehouse = createWarehouse("WH-NS-BOM-OL");
			final I_C_OrderLine orderLine = createOrderLine(nonStockedBomComponent, warehouse);

			final PickingBOMsReversedIndex reversedIndexWithComponent = PickingBOMsReversedIndex.ofBOMProductIdsByComponentId(
					ImmutableSetMultimap.of(componentProductId, ProductId.ofRepoId(999_999)));
			final ShipmentScheduleInvalidateBL bl = newInvalidateBLSpy(reversedIndexWithComponent);
			doNothing().when(bl).invalidateJustForOrderLine(any());
			doNothing().when(bl).notifySegmentChanged(any());

			bl.notifySegmentChangedForOrderLine(orderLine);

			verify(bl, times(1))
					.notifySegmentChanged(any());
			verify(bl, never())
					.invalidateJustForOrderLine(any());
		}

		/**
		 * {@code notifySegmentsChangedForShipment} builds one segment per shipment line and notifies them as a
		 * batch; a mixed shipment can have both stocked and non-stocked lines, so the narrowing decision is
		 * per-line: the non-stocked line must be routed via {@link ShipmentScheduleInvalidateBL#flagForRecompute(I_M_InOutLine)}
		 * and excluded from the broad batch, while the stocked line must still go through the broad
		 * {@link ShipmentScheduleInvalidateBL#notifySegmentsChanged(Collection)} path.
		 */
		@Test
		void mixedShipment_nonStockedLineNarrowedPerLine_stockedLineKeepsSegment()
		{
			final I_M_Product stockedProduct = createProduct("StockedItem-Ship", true, X_M_Product.PRODUCTTYPE_Item);
			final I_M_Product nonStockedProduct = createProduct("NonStockedItem-Ship", false, X_M_Product.PRODUCTTYPE_Item);

			final I_M_InOut shipment = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
			shipment.setIsSOTrx(true);
			InterfaceWrapperHelper.save(shipment);

			final I_M_InOutLine stockedLine = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class);
			stockedLine.setM_InOut(shipment);
			stockedLine.setM_Product_ID(stockedProduct.getM_Product_ID());
			InterfaceWrapperHelper.save(stockedLine);

			final I_M_InOutLine nonStockedLine = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class);
			nonStockedLine.setM_InOut(shipment);
			nonStockedLine.setM_Product_ID(nonStockedProduct.getM_Product_ID());
			InterfaceWrapperHelper.save(nonStockedLine);

			final ShipmentScheduleInvalidateBL bl = newInvalidateBLSpy(NO_BOM_COMPONENTS);
			doNothing().when(bl).flagForRecompute(any(I_M_InOutLine.class));
			doNothing().when(bl).notifySegmentsChanged(any());

			bl.notifySegmentsChangedForShipment(shipment);

			verify(bl, times(1))
					.flagForRecompute(nonStockedLine);
			verify(bl, never())
					.flagForRecompute(stockedLine);

			@SuppressWarnings("unchecked")
			final ArgumentCaptor<Collection<IShipmentScheduleSegment>> segmentsCaptor = ArgumentCaptor.forClass(Collection.class);
			verify(bl, times(1)).notifySegmentsChanged(segmentsCaptor.capture());
			assertThat(segmentsCaptor.getValue())
					.as("only the stocked line's segment must reach the broad batch")
					.hasSize(1);
		}
	}
}
