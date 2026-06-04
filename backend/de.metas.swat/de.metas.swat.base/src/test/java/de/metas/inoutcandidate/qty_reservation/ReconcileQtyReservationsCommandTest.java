package de.metas.inoutcandidate.qty_reservation;

import de.metas.business.BusinessTestHelper;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_QtyReservation;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ReconcileQtyReservationsCommandTest
{
	private QtyReservationService service;
	private I_C_UOM uom;
	private int productId;
	private int warehouseId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uom = BusinessTestHelper.createUOM("Kg");
		productId = BusinessTestHelper.createProductId("P1", uom).getRepoId();
		warehouseId = createWarehouseId();

		final QtyReservationRepository repository = new QtyReservationRepository();
		service = new QtyReservationService(mock(IShipmentScheduleInvalidateBL.class), repository);
	}

	@Test
	void shrinksSingleReservation_whenLineReduced()
	{
		final OrderId orderId = createSalesOrder();
		final OrderLineId orderLineId = createOrderLine(orderId, new BigDecimal("75"));

		createReservationRecord(orderLineId, SupplyType.ON_HAND, new BigDecimal("100"), new BigDecimal("100"));

		service.reconcileToOrderedQty(orderId);

		final List<I_M_QtyReservation> records = loadRecords(orderLineId);
		assertThat(records).hasSize(1);
		assertThat(records.get(0).getQty()).isEqualByComparingTo(new BigDecimal("75"));
		assertThat(records.get(0).getQtyTU()).isEqualByComparingTo(new BigDecimal("75"));
	}

	@Test
	void roundsQtyTU_up_whenPartiallyFilled()
	{
		final OrderId orderId = createSalesOrder();
		final OrderLineId orderLineId = createOrderLine(orderId, new BigDecimal("71"));

		// Qty=100 in 10 TUs (10 PCE per TU). Shrinking to 71 leaves 7.1 TU worth of CUs.
		createReservationRecord(orderLineId, SupplyType.ON_HAND, new BigDecimal("100"), new BigDecimal("10"));

		service.reconcileToOrderedQty(orderId);

		final List<I_M_QtyReservation> records = loadRecords(orderLineId);
		assertThat(records).hasSize(1);
		// CU Qty is the exact reduced value
		assertThat(records.get(0).getQty()).isEqualByComparingTo(new BigDecimal("71"));
		// QtyTU rounds UP: 10 * 71 / 100 = 7.1 TU -> CEILING = 8 (a partially-filled TU still occupies a whole TU)
		assertThat(records.get(0).getQtyTU()).isEqualByComparingTo(new BigDecimal("8"));
	}

	@Test
	void doesNotShrinkBelowDelivered()
	{
		final OrderId orderId = createSalesOrder();
		final OrderLineId orderLineId = createOrderLine(orderId, new BigDecimal("40"));

		final I_M_QtyReservation record = createReservationRecord(orderLineId, SupplyType.ON_HAND, new BigDecimal("100"), new BigDecimal("100"));
		record.setQtyDelivered(new BigDecimal("50"));
		InterfaceWrapperHelper.save(record);

		service.reconcileToOrderedQty(orderId);

		final List<I_M_QtyReservation> records = loadRecords(orderLineId);
		assertThat(records).hasSize(1);
		// clamped to delivered (50), NOT down to QtyOrdered (40)
		assertThat(records.get(0).getQty()).isEqualByComparingTo(new BigDecimal("50"));
		assertThat(records.get(0).getQtyDelivered()).isEqualByComparingTo(new BigDecimal("50"));
	}

	@Test
	void noChange_whenReservedEqualsOrdered()
	{
		final OrderId orderId = createSalesOrder();
		final OrderLineId orderLineId = createOrderLine(orderId, new BigDecimal("75"));

		createReservationRecord(orderLineId, SupplyType.ON_HAND, new BigDecimal("75"), new BigDecimal("75"));

		service.reconcileToOrderedQty(orderId);

		final List<I_M_QtyReservation> records = loadRecords(orderLineId);
		assertThat(records).hasSize(1);
		assertThat(records.get(0).getQty()).isEqualByComparingTo(new BigDecimal("75"));
	}

	@Test
	void noChange_whenReservedBelowOrdered()
	{
		final OrderId orderId = createSalesOrder();
		final OrderLineId orderLineId = createOrderLine(orderId, new BigDecimal("75"));

		createReservationRecord(orderLineId, SupplyType.ON_HAND, new BigDecimal("50"), new BigDecimal("50"));

		service.reconcileToOrderedQty(orderId);

		final List<I_M_QtyReservation> records = loadRecords(orderLineId);
		assertThat(records).hasSize(1);
		assertThat(records.get(0).getQty()).isEqualByComparingTo(new BigDecimal("50"));
	}

	@Test
	void multiReservation_reducesToTotalOrdered()
	{
		final OrderId orderId = createSalesOrder();
		final OrderLineId orderLineId = createOrderLine(orderId, new BigDecimal("75"));

		createReservationRecord(orderLineId, SupplyType.ON_HAND, new BigDecimal("60"), new BigDecimal("60"));
		createReservationRecord(orderLineId, SupplyType.PLANNED_SUPPLY, new BigDecimal("40"), new BigDecimal("40"));

		service.reconcileToOrderedQty(orderId);

		final List<I_M_QtyReservation> records = loadRecords(orderLineId);
		assertThat(records).hasSize(2);

		final BigDecimal total = records.stream()
				.map(I_M_QtyReservation::getQty)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		assertThat(total).isEqualByComparingTo(new BigDecimal("75"));

		// PS-before-OH: PS reduced first (40 -> 15), OH untouched (60)
		final I_M_QtyReservation oh = records.stream()
				.filter(r -> SupplyType.ON_HAND.getCode().equals(r.getSupplyType()))
				.findFirst().get();
		final I_M_QtyReservation ps = records.stream()
				.filter(r -> SupplyType.PLANNED_SUPPLY.getCode().equals(r.getSupplyType()))
				.findFirst().get();
		assertThat(oh.getQty()).isEqualByComparingTo(new BigDecimal("60"));
		assertThat(ps.getQty()).isEqualByComparingTo(new BigDecimal("15"));
	}

	@Test
	void doesNotTouchProcessedReservations()
	{
		final OrderId orderId = createSalesOrder();
		final OrderLineId orderLineId = createOrderLine(orderId, new BigDecimal("50"));

		// UNPROCESSED reservation: Qty=70, QtyTU=70, QtyDelivered=0, Processed=false
		final I_M_QtyReservation unprocessed = createReservationRecord(orderLineId, SupplyType.ON_HAND, new BigDecimal("70"), new BigDecimal("70"));
		final int unprocessedId = unprocessed.getM_QtyReservation_ID();

		// PROCESSED reservation on the SAME line: Qty=30, QtyTU=30, QtyDelivered=30, Processed=true
		final I_M_QtyReservation processed = createReservationRecord(orderLineId, SupplyType.ON_HAND, new BigDecimal("30"), new BigDecimal("30"));
		processed.setQtyDelivered(new BigDecimal("30"));
		processed.setProcessed(true);
		InterfaceWrapperHelper.save(processed);
		final int processedId = processed.getM_QtyReservation_ID();

		service.reconcileToOrderedQty(orderId);

		// reload from the embedded DB to catch any silent save by the command
		final I_M_QtyReservation processedReloaded = InterfaceWrapperHelper.load(processedId, I_M_QtyReservation.class);
		final I_M_QtyReservation unprocessedReloaded = InterfaceWrapperHelper.load(unprocessedId, I_M_QtyReservation.class);

		// the PROCESSED reservation is UNCHANGED — Qty/QtyTU/QtyDelivered untouched and Processed not overwritten
		assertThat(processedReloaded.getQty()).isEqualByComparingTo(new BigDecimal("30"));
		assertThat(processedReloaded.getQtyTU()).isEqualByComparingTo(new BigDecimal("30"));
		assertThat(processedReloaded.getQtyDelivered()).isEqualByComparingTo(new BigDecimal("30"));
		assertThat(processedReloaded.isProcessed()).isTrue();

		// getActiveByOrderLineId() filters Processed=false, so only the unprocessed 70 is visible to the command.
		// excess = 70 (total unprocessed reserved) - 50 (QtyOrdered) = 20; 70 -> 50.
		// The processed 30 is intentionally excluded from the excess computation (not (70+30)-50).
		assertThat(unprocessedReloaded.getQty()).isEqualByComparingTo(new BigDecimal("50"));
		assertThat(unprocessedReloaded.getQtyTU()).isEqualByComparingTo(new BigDecimal("50"));
	}

	@Test
	void shrinksCorrectly_whenReservationUomDiffersFromOrderLine()
	{
		// product stocking UOM is "Kg" (the order-line / qtyOrdered UOM).
		// the reservation is held in a different UOM "Box", with conversion 1 Box = 5 Kg.
		final I_C_UOM boxUom = BusinessTestHelper.createUOM("Box");
		Services.get(IUOMConversionDAO.class).createUOMConversion(CreateUOMConversionRequest.builder()
				.fromUomId(UomId.ofRepoId(boxUom.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(uom.getC_UOM_ID()))
				.fromToMultiplier(new BigDecimal("5"))
				.build());

		final OrderId orderId = createSalesOrder();
		// QtyOrdered = 50 Kg
		final OrderLineId orderLineId = createOrderLine(orderId, new BigDecimal("50"));

		// reservation = 20 Box = 100 Kg → 50 Kg excess → must shrink to 10 Box (= 50 Kg)
		final I_M_QtyReservation record = createReservationRecord(orderLineId, SupplyType.ON_HAND, new BigDecimal("20"), new BigDecimal("20"));
		record.setC_UOM_ID(boxUom.getC_UOM_ID());
		InterfaceWrapperHelper.save(record);

		service.reconcileToOrderedQty(orderId);

		final List<I_M_QtyReservation> records = loadRecords(orderLineId);
		assertThat(records).hasSize(1);
		// shrunk to the order-line qty (50 Kg) expressed in the reservation's own UOM (10 Box)
		assertThat(records.get(0).getQty()).isEqualByComparingTo(new BigDecimal("10"));
	}

	@Test
	void setsProcessed_whenShrunkToDelivered()
	{
		final OrderId orderId = createSalesOrder();
		final OrderLineId orderLineId = createOrderLine(orderId, new BigDecimal("40"));

		// Qty=100, QtyDelivered=40, Processed=false → shrinks to 40, then Qty==QtyDelivered ⇒ Processed=true
		final I_M_QtyReservation record = createReservationRecord(orderLineId, SupplyType.ON_HAND, new BigDecimal("100"), new BigDecimal("100"));
		record.setQtyDelivered(new BigDecimal("40"));
		record.setProcessed(false);
		InterfaceWrapperHelper.save(record);
		final int recordId = record.getM_QtyReservation_ID();

		service.reconcileToOrderedQty(orderId);

		final I_M_QtyReservation reloaded = InterfaceWrapperHelper.load(recordId, I_M_QtyReservation.class);
		assertThat(reloaded.getQty()).isEqualByComparingTo(new BigDecimal("40"));
		assertThat(reloaded.isProcessed()).isTrue();
	}

	@Test
	void skipsLine_whenQtyOrderedIsZero()
	{
		final OrderId orderId = createSalesOrder();
		final OrderLineId orderLineId = createOrderLine(orderId, BigDecimal.ZERO);

		createReservationRecord(orderLineId, SupplyType.ON_HAND, new BigDecimal("10"), new BigDecimal("10"));

		service.reconcileToOrderedQty(orderId);

		// QtyOrdered=0 → no over-reservation shrink runs; the reservation has positive Qty (10) so the
		// zero-phantom scan finds nothing to delete → reservation untouched
		final List<I_M_QtyReservation> records = loadRecords(orderLineId);
		assertThat(records).hasSize(1);
		assertThat(records.get(0).getQty()).isEqualByComparingTo(new BigDecimal("10"));
	}

	@Test
	void reconcilesEachLineIndependently()
	{
		final OrderId orderId = createSalesOrder();

		// lineA: over-reserved (80 reserved vs 50 ordered) → must shrink to 50
		final OrderLineId lineA = createOrderLine(orderId, new BigDecimal("50"));
		createReservationRecord(lineA, SupplyType.ON_HAND, new BigDecimal("80"), new BigDecimal("80"));

		// lineB: exactly reserved (100 reserved vs 100 ordered) → must stay 100
		final OrderLineId lineB = createOrderLine(orderId, new BigDecimal("100"));
		createReservationRecord(lineB, SupplyType.ON_HAND, new BigDecimal("100"), new BigDecimal("100"));

		service.reconcileToOrderedQty(orderId);

		final List<I_M_QtyReservation> recordsA = loadRecords(lineA);
		assertThat(recordsA).hasSize(1);
		assertThat(recordsA.get(0).getQty()).isEqualByComparingTo(new BigDecimal("50"));

		final List<I_M_QtyReservation> recordsB = loadRecords(lineB);
		assertThat(recordsB).hasSize(1);
		assertThat(recordsB.get(0).getQty()).isEqualByComparingTo(new BigDecimal("100"));
	}

	@Test
	void deletesPreExistingZeroQtyReservation_keepingReservedTuCorrect()
	{
		// Scenario: a planned-supply reservation was minted with Qty(CU)=0 but QtyTU=3
		// (planned supply has no on-hand stock, so computeQtyCUToReserve returns 0). On re-completion the line
		// was reduced to 77, so the OH row (110/10) shrinks to 77/7. The phantom PS row (0/3) must be DELETED,
		// otherwise the reserved QtyTU total stays 10 (7+3) instead of 7.
		final OrderId orderId = createSalesOrder();
		final OrderLineId orderLineId = createOrderLine(orderId, new BigDecimal("77"));

		createReservationRecord(orderLineId, SupplyType.ON_HAND, new BigDecimal("110"), new BigDecimal("10"));
		createReservationRecord(orderLineId, SupplyType.PLANNED_SUPPLY, BigDecimal.ZERO, new BigDecimal("3"));

		service.reconcileToOrderedQty(orderId);

		final List<I_M_QtyReservation> records = loadRecords(orderLineId);
		// phantom PS row deleted; only the shrunk OH row remains
		assertThat(records).hasSize(1);
		assertThat(records.get(0).getSupplyType()).isEqualTo(SupplyType.ON_HAND.getCode());
		assertThat(records.get(0).getQty()).isEqualByComparingTo(new BigDecimal("77"));
		assertThat(records.get(0).getQtyTU()).isEqualByComparingTo(new BigDecimal("7"));

		// reserved QtyTU total is 7, not 10
		final BigDecimal totalTU = records.stream()
				.map(I_M_QtyReservation::getQtyTU)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		assertThat(totalTU).isEqualByComparingTo(new BigDecimal("7"));
	}

	@Test
	void deletesReservation_whenShrunkToZero()
	{
		// Multi-reservation line reduced below the OH portion: PS (40) is fully removed (shrunk to 0) and
		// must be DELETED, not left as a Qty=0 row. OH (60) is kept and equals the reduced QtyOrdered.
		final OrderId orderId = createSalesOrder();
		final OrderLineId orderLineId = createOrderLine(orderId, new BigDecimal("60"));

		createReservationRecord(orderLineId, SupplyType.ON_HAND, new BigDecimal("60"), new BigDecimal("60"));
		createReservationRecord(orderLineId, SupplyType.PLANNED_SUPPLY, new BigDecimal("40"), new BigDecimal("40"));

		service.reconcileToOrderedQty(orderId);

		final List<I_M_QtyReservation> records = loadRecords(orderLineId);
		assertThat(records).hasSize(1);
		assertThat(records.get(0).getSupplyType()).isEqualTo(SupplyType.ON_HAND.getCode());
		assertThat(records.get(0).getQty()).isEqualByComparingTo(new BigDecimal("60"));
	}

	// --- helpers ---

	private int createWarehouseId()
	{
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		warehouse.setValue("WH");
		warehouse.setName("WH");
		InterfaceWrapperHelper.save(warehouse);
		return warehouse.getM_Warehouse_ID();
	}

	private OrderId createSalesOrder()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setIsSOTrx(true);
		InterfaceWrapperHelper.save(order);
		return OrderId.ofRepoId(order.getC_Order_ID());
	}

	private OrderLineId createOrderLine(final OrderId orderId, final BigDecimal qtyOrdered)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(orderId.getRepoId());
		orderLine.setM_Product_ID(productId);
		orderLine.setM_Warehouse_ID(warehouseId);
		orderLine.setC_UOM_ID(uom.getC_UOM_ID());
		orderLine.setQtyOrdered(qtyOrdered);
		orderLine.setQtyEntered(qtyOrdered);
		InterfaceWrapperHelper.save(orderLine);
		return OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID());
	}

	private I_M_QtyReservation createReservationRecord(
			final OrderLineId orderLineId,
			final SupplyType supplyType,
			final BigDecimal qty,
			final BigDecimal qtyTU)
	{
		final I_M_QtyReservation record = InterfaceWrapperHelper.newInstance(I_M_QtyReservation.class);
		record.setC_OrderLine_ID(orderLineId.getRepoId());
		record.setM_Product_ID(productId);
		record.setM_Warehouse_ID(1);
		record.setC_UOM_ID(uom.getC_UOM_ID());
		record.setSupplyType(supplyType.getCode());
		record.setQty(qty);
		record.setQtyDelivered(BigDecimal.ZERO);
		record.setQtyTU(qtyTU);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private List<I_M_QtyReservation> loadRecords(final OrderLineId orderLineId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_QtyReservation.class)
				.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_C_OrderLine_ID, orderLineId)
				.orderBy(I_M_QtyReservation.COLUMNNAME_SupplyType)
				.create()
				.list();
	}
}
