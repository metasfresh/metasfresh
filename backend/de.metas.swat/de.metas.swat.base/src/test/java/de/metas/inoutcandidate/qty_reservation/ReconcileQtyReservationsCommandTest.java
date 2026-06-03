package de.metas.inoutcandidate.qty_reservation;

import de.metas.business.BusinessTestHelper;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
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
