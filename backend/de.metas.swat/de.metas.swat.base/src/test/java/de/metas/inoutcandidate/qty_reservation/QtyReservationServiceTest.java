package de.metas.inoutcandidate.qty_reservation;

import de.metas.business.BusinessTestHelper;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for {@link QtyReservationService#computeRemainingOrderedQty(OrderAndLineId, UomId)} — the
 * order-need cap bound (REQUIREMENTS AC3a): {@code QtyOrdered - Σ(active, unprocessed reservations' Qty)},
 * floored at 0, expressed in the requested (stock) UOM.
 */
class QtyReservationServiceTest
{
	private QtyReservationService service;
	private I_C_UOM uom;
	private UomId uomId;
	private int productId;
	private int warehouseId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uom = BusinessTestHelper.createUOM("Kg");
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());
		productId = BusinessTestHelper.createProductId("P1", uom).getRepoId();
		warehouseId = createWarehouseId();

		final QtyReservationRepository repository = new QtyReservationRepository();
		service = new QtyReservationService(mock(IShipmentScheduleInvalidateBL.class), repository);
	}

	@Test
	void remainingEqualsOrdered_whenNoReservations()
	{
		final OrderAndLineId orderLineId = createOrderLine(new BigDecimal("20"));

		final Quantity remaining = service.computeRemainingOrderedQty(orderLineId, uomId);

		assertThat(remaining.getUomId()).isEqualTo(uomId);
		assertThat(remaining.toBigDecimal()).isEqualByComparingTo(new BigDecimal("20"));
	}

	@Test
	void remainingIsOrderedMinusReserved_singleReservation()
	{
		final OrderAndLineId orderLineId = createOrderLine(new BigDecimal("75"));
		createReservationRecord(orderLineId.getOrderLineId(), SupplyType.ON_HAND, new BigDecimal("30"));

		final Quantity remaining = service.computeRemainingOrderedQty(orderLineId, uomId);

		assertThat(remaining.toBigDecimal()).isEqualByComparingTo(new BigDecimal("45"));
	}

	@Test
	void remainingSubtractsAllReservations_multiReservation()
	{
		final OrderAndLineId orderLineId = createOrderLine(new BigDecimal("100"));
		createReservationRecord(orderLineId.getOrderLineId(), SupplyType.ON_HAND, new BigDecimal("30"));
		createReservationRecord(orderLineId.getOrderLineId(), SupplyType.PLANNED_SUPPLY, new BigDecimal("40"));

		final Quantity remaining = service.computeRemainingOrderedQty(orderLineId, uomId);

		// 100 - (30 + 40) = 30
		assertThat(remaining.toBigDecimal()).isEqualByComparingTo(new BigDecimal("30"));
	}

	@Test
	void remainingFlooredAtZero_whenOverReserved()
	{
		final OrderAndLineId orderLineId = createOrderLine(new BigDecimal("20"));
		createReservationRecord(orderLineId.getOrderLineId(), SupplyType.ON_HAND, new BigDecimal("30"));

		final Quantity remaining = service.computeRemainingOrderedQty(orderLineId, uomId);

		// 20 - 30 = -10 -> floored to 0
		assertThat(remaining.toBigDecimal()).isEqualByComparingTo(BigDecimal.ZERO);
	}

	@Test
	void convertsReservationUomToTargetUom()
	{
		// reservation held in "Box", 1 Box = 5 Kg; order line / target UOM = Kg
		final I_C_UOM boxUom = BusinessTestHelper.createUOM("Box");
		Services.get(IUOMConversionDAO.class).createUOMConversion(CreateUOMConversionRequest.builder()
				.fromUomId(UomId.ofRepoId(boxUom.getC_UOM_ID()))
				.toUomId(uomId)
				.fromToMultiplier(new BigDecimal("5"))
				.build());

		final OrderAndLineId orderLineId = createOrderLine(new BigDecimal("50")); // 50 Kg
		final I_M_QtyReservation record = createReservationRecord(orderLineId.getOrderLineId(), SupplyType.ON_HAND, new BigDecimal("6"));
		record.setC_UOM_ID(boxUom.getC_UOM_ID()); // 6 Box = 30 Kg
		InterfaceWrapperHelper.save(record);

		final Quantity remaining = service.computeRemainingOrderedQty(orderLineId, uomId);

		// 50 Kg - 30 Kg = 20 Kg
		assertThat(remaining.getUomId()).isEqualTo(uomId);
		assertThat(remaining.toBigDecimal()).isEqualByComparingTo(new BigDecimal("20"));
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

	private OrderAndLineId createOrderLine(final BigDecimal qtyOrdered)
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setIsSOTrx(true);
		InterfaceWrapperHelper.save(order);

		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setM_Product_ID(productId);
		orderLine.setM_Warehouse_ID(warehouseId);
		orderLine.setC_UOM_ID(uom.getC_UOM_ID());
		orderLine.setQtyOrdered(qtyOrdered);
		orderLine.setQtyEntered(qtyOrdered);
		InterfaceWrapperHelper.save(orderLine);

		return OrderAndLineId.ofRepoIds(order.getC_Order_ID(), orderLine.getC_OrderLine_ID());
	}

	private I_M_QtyReservation createReservationRecord(
			final OrderLineId orderLineId,
			final SupplyType supplyType,
			final BigDecimal qty)
	{
		final I_M_QtyReservation record = InterfaceWrapperHelper.newInstance(I_M_QtyReservation.class);
		record.setC_OrderLine_ID(orderLineId.getRepoId());
		record.setM_Product_ID(productId);
		record.setM_Warehouse_ID(warehouseId);
		record.setC_UOM_ID(uom.getC_UOM_ID());
		record.setSupplyType(supplyType.getCode());
		record.setQty(qty);
		record.setQtyDelivered(BigDecimal.ZERO);
		record.setQtyTU(qty);
		InterfaceWrapperHelper.save(record);
		return record;
	}
}
