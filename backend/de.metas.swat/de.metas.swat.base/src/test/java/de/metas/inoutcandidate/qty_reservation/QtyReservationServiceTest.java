package de.metas.inoutcandidate.qty_reservation;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.QtyTU;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderLineId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantitiesUOMNotMatchingExpection;
import org.adempiere.warehouse.WarehouseId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
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
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
	private IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL;
	private IQueryBL queryBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		queryBL = Services.get(IQueryBL.class);
		uom = BusinessTestHelper.createUOM("Kg");
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());
		productId = BusinessTestHelper.createProductId("P1", uom).getRepoId();
		warehouseId = createWarehouseId();

		final QtyReservationRepository repository = new QtyReservationRepository();
		shipmentScheduleInvalidateBL = mock(IShipmentScheduleInvalidateBL.class);
		service = new QtyReservationService(shipmentScheduleInvalidateBL, repository);
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

	// --- reduceReservation ---

	@Test
	void reduceReservation_1of4TU_3TURemain()
	{
		final OrderAndLineId orderLineId = createOrderLine(new BigDecimal("100"));
		final I_M_QtyReservation record = createBucketRow(orderLineId, SupplyType.ON_HAND, 4, BigDecimal.ZERO);

		service.reduceReservation(reduceRequest(orderLineId, SupplyType.ON_HAND, 1));

		final I_M_QtyReservation reloaded = reload(record);
		assertThat(reloaded.getQtyTU()).isEqualByComparingTo("3");
		assertThat(reloaded.getQty()).isEqualByComparingTo("30");
		verify(shipmentScheduleInvalidateBL, times(1)).flagForRecomputeStorageSegment(any());
	}

	@Test
	void reduceReservation_full4TU_bucketDeleted()
	{
		final OrderAndLineId orderLineId = createOrderLine(new BigDecimal("100"));
		createBucketRow(orderLineId, SupplyType.ON_HAND, 4, BigDecimal.ZERO);

		service.reduceReservation(reduceRequest(orderLineId, SupplyType.ON_HAND, 4));

		assertThat(allRecords(orderLineId)).isEmpty();
		verify(shipmentScheduleInvalidateBL, times(1)).flagForRecomputeStorageSegment(any());
	}

	@Test
	void reduceReservation_requestAboveReversible_throws_andChangesNothing()
	{
		final OrderAndLineId orderLineId = createOrderLine(new BigDecimal("100"));
		// 4 TU x 10 CU, 1 TU (10 CU) already delivered => only 3 TU are reversible
		final I_M_QtyReservation record = createBucketRow(orderLineId, SupplyType.ON_HAND, 4, new BigDecimal("10"));

		assertThatThrownBy(() -> service.reduceReservation(reduceRequest(orderLineId, SupplyType.ON_HAND, 4)))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("only 3 TU");

		assertThat(reload(record).getQtyTU()).isEqualByComparingTo("4");
		assertThat(reload(record).getQty()).isEqualByComparingTo("40");
	}

	@Test
	void reduceReservation_exactlyReversible_succeeds_leavingDeliveredPortion()
	{
		final OrderAndLineId orderLineId = createOrderLine(new BigDecimal("100"));
		final I_M_QtyReservation record = createBucketRow(orderLineId, SupplyType.ON_HAND, 4, new BigDecimal("10"));

		service.reduceReservation(reduceRequest(orderLineId, SupplyType.ON_HAND, 3));

		assertThat(reload(record).getQty()).isEqualByComparingTo("10");
		assertThat(reload(record).getQtyTU()).isEqualByComparingTo("1");
	}

	@Test
	void reduceReservation_nonIntegerCapacity_decrementsQtyTUExactly()
	{
		final OrderAndLineId orderLineId = createOrderLine(new BigDecimal("100"));
		// Qty 10 over 3 TU => 3.333... CU per TU
		final I_M_QtyReservation record = createBucketRow(orderLineId, SupplyType.ON_HAND, 3, BigDecimal.ZERO);
		record.setQty(new BigDecimal("10"));
		InterfaceWrapperHelper.save(record);

		service.reduceReservation(reduceRequest(orderLineId, SupplyType.ON_HAND, 1));

		final I_M_QtyReservation reloaded = reload(record);
		assertThat(reloaded.getQtyTU()).isEqualByComparingTo("2");
		assertThat(reloaded.getQty()).isBetween(new BigDecimal("6.66"), new BigDecimal("6.67"));
	}

	@Test
	void reduceReservation_multiRowBucket_reducedAcrossRows()
	{
		final OrderAndLineId orderLineId = createOrderLine(new BigDecimal("100"));
		final I_M_QtyReservation row1 = createBucketRow(orderLineId, SupplyType.ON_HAND, 2, BigDecimal.ZERO);
		final I_M_QtyReservation row2 = createBucketRow(orderLineId, SupplyType.ON_HAND, 3, BigDecimal.ZERO);
		final I_M_QtyReservation otherBucket = createBucketRow(orderLineId, SupplyType.PLANNED_SUPPLY, 2, BigDecimal.ZERO);

		service.reduceReservation(reduceRequest(orderLineId, SupplyType.ON_HAND, 3));

		// row1 (2 TU) fully consumed -> deleted; row2 gives up the remaining 1 TU -> 2 TU left; other bucket untouched
		assertThat(allRecords(orderLineId)).extracting(I_M_QtyReservation::getM_QtyReservation_ID)
				.containsExactlyInAnyOrder(row2.getM_QtyReservation_ID(), otherBucket.getM_QtyReservation_ID());
		assertThat(reload(row2).getQtyTU()).isEqualByComparingTo("2");
		assertThat(reload(row2).getQty()).isEqualByComparingTo("20");
		assertThat(reload(otherBucket).getQtyTU()).isEqualByComparingTo("2");
	}

	@Test
	void reduceReservation_overDeliveredRowInBucket_doesNotMakeHealthyRowSpuriouslyIrreversible()
	{
		final OrderAndLineId orderLineId = createOrderLine(new BigDecimal("100"));
		// delivered (15) > Qty (10): nothing reversible on this row
		final I_M_QtyReservation overDelivered = createBucketRow(orderLineId, SupplyType.ON_HAND, 1, new BigDecimal("15"));
		createBucketRow(orderLineId, SupplyType.ON_HAND, 2, BigDecimal.ZERO); // healthy row: fully reversible

		service.reduceReservation(reduceRequest(orderLineId, SupplyType.ON_HAND, 2));

		assertThat(allRecords(orderLineId)).extracting(I_M_QtyReservation::getM_QtyReservation_ID)
				.containsExactly(overDelivered.getM_QtyReservation_ID());
		assertThat(reload(overDelivered).getQty()).isEqualByComparingTo("10");
	}

	@Test
	void reduceReservation_rowWithZeroQtyTU_isSkipped()
	{
		final OrderAndLineId orderLineId = createOrderLine(new BigDecimal("100"));
		final I_M_QtyReservation zeroTU = createBucketRow(orderLineId, SupplyType.ON_HAND, 1, BigDecimal.ZERO);
		zeroTU.setQtyTU(BigDecimal.ZERO); // Qty 10, QtyTU 0
		InterfaceWrapperHelper.save(zeroTU);
		final I_M_QtyReservation healthy = createBucketRow(orderLineId, SupplyType.ON_HAND, 2, BigDecimal.ZERO);

		service.reduceReservation(reduceRequest(orderLineId, SupplyType.ON_HAND, 1));

		assertThat(reload(zeroTU).getQty()).isEqualByComparingTo("10");
		assertThat(reload(zeroTU).getQtyTU()).isEqualByComparingTo("0");
		assertThat(reload(healthy).getQtyTU()).isEqualByComparingTo("1");
	}

	@Test
	void reduceReservation_datePromisedFilter_scopesToThatDate()
	{
		final OrderAndLineId orderLineId = createOrderLine(new BigDecimal("100"));
		final Instant date1 = Instant.parse("2026-11-01T00:00:00Z");
		final Instant date2 = Instant.parse("2026-11-08T00:00:00Z");
		final I_M_QtyReservation row1 = createBucketRow(orderLineId, SupplyType.PLANNED_SUPPLY, 3, BigDecimal.ZERO);
		row1.setDatePromised(Timestamp.from(date1));
		InterfaceWrapperHelper.save(row1);
		final I_M_QtyReservation row2 = createBucketRow(orderLineId, SupplyType.PLANNED_SUPPLY, 3, BigDecimal.ZERO);
		row2.setDatePromised(Timestamp.from(date2));
		InterfaceWrapperHelper.save(row2);

		service.reduceReservation(ReduceQtyReservationRequest.builder()
				.orderAndLineId(orderLineId)
				.supplyType(SupplyType.PLANNED_SUPPLY)
				.datePromised(date2)
				.qtyToUnreserveTU(QtyTU.ofInt(1))
				.build());

		assertThat(reload(row1).getQtyTU()).isEqualByComparingTo("3");
		assertThat(reload(row2).getQtyTU()).isEqualByComparingTo("2");
	}

	@Test
	void reduceReservation_processedRowInBucket_isLeftUntouched()
	{
		final OrderAndLineId orderLineId = createOrderLine(new BigDecimal("100"));
		final I_M_QtyReservation processed = createBucketRow(orderLineId, SupplyType.ON_HAND, 2, BigDecimal.ZERO);
		processed.setProcessed(true);
		InterfaceWrapperHelper.save(processed);
		final I_M_QtyReservation open = createBucketRow(orderLineId, SupplyType.ON_HAND, 2, BigDecimal.ZERO);

		service.reduceReservation(reduceRequest(orderLineId, SupplyType.ON_HAND, 1));

		assertThat(reload(processed).getQtyTU()).isEqualByComparingTo("2");
		assertThat(reload(open).getQtyTU()).isEqualByComparingTo("1");
	}

	// --- getTotalReversibleQtyTU ---

	@Test
	void getTotalReversibleQtyTU_sumsReversibleTU_subtractingDelivered_scopedToBucket()
	{
		final OrderAndLineId orderLineId = createOrderLine(new BigDecimal("100"));
		// ON_HAND bucket: 4 TU reserved with 10 CU (= 1 TU @ 10 CU/TU) delivered -> 3 reversible; plus a healthy 2 TU -> 2 reversible
		createBucketRow(orderLineId, SupplyType.ON_HAND, 4, new BigDecimal("10"));
		createBucketRow(orderLineId, SupplyType.ON_HAND, 2, BigDecimal.ZERO);
		// a different supply-type bucket must NOT be counted towards ON_HAND
		createBucketRow(orderLineId, SupplyType.PLANNED_SUPPLY, 7, BigDecimal.ZERO);

		assertThat(service.getTotalReversibleQtyTU(orderLineId, SupplyType.ON_HAND, null).toInt()).isEqualTo(5);
		assertThat(service.getTotalReversibleQtyTU(orderLineId, SupplyType.PLANNED_SUPPLY, null).toInt()).isEqualTo(7);
	}

	// --- QtyReservationShrinker UOM guard ---

	@Test
	void shrink_reductionInForeignUOM_isRejectedByType()
	{
		// the row is in `uom`; a reduction expressed in a different UOM must throw (type-enforced invariant),
		// not be silently subtracted as a bare number
		final I_C_UOM otherUom = BusinessTestHelper.createUOM("Stk");
		final QtyReservation reservation = QtyReservation.builder()
				.id(QtyReservationId.ofRepoId(1_000_000))
				.orderLineId(OrderLineId.ofRepoId(1_000_001))
				.productId(ProductId.ofRepoId(productId))
				.warehouseId(WarehouseId.ofRepoId(warehouseId))
				.attributesKey(AttributesKey.NONE)
				.qtyTU(QtyTU.ofInt(4))
				.qty(Quantity.of(40, uom))
				.qtyDelivered(Quantity.of(0, uom))
				.build();

		assertThatThrownBy(() -> QtyReservationShrinker.shrink(reservation, Quantity.of(10, otherUom)))
				.isInstanceOf(QuantitiesUOMNotMatchingExpection.class);
	}

	private ReduceQtyReservationRequest reduceRequest(final OrderAndLineId orderLineId, final SupplyType supplyType, final int tu)
	{
		return ReduceQtyReservationRequest.builder()
				.orderAndLineId(orderLineId)
				.supplyType(supplyType)
				.qtyToUnreserveTU(QtyTU.ofInt(tu))
				.build();
	}

	/** 10 CU per TU; C_Order_ID set so the bucket query (which filters by order + line) finds it. */
	private I_M_QtyReservation createBucketRow(final OrderAndLineId orderLineId, final SupplyType supplyType, final int tu, final BigDecimal qtyDelivered)
	{
		final I_M_QtyReservation record = InterfaceWrapperHelper.newInstance(I_M_QtyReservation.class);
		record.setC_Order_ID(orderLineId.getOrderRepoId());
		record.setC_OrderLine_ID(orderLineId.getOrderLineRepoId());
		record.setM_Product_ID(productId);
		record.setM_Warehouse_ID(warehouseId);
		record.setC_UOM_ID(uom.getC_UOM_ID());
		record.setSupplyType(supplyType.getCode());
		record.setQty(BigDecimal.valueOf(tu * 10L));
		record.setQtyDelivered(qtyDelivered);
		record.setQtyTU(BigDecimal.valueOf(tu));
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private I_M_QtyReservation reload(final I_M_QtyReservation record)
	{
		InterfaceWrapperHelper.refresh(record);
		return record;
	}

	private List<I_M_QtyReservation> allRecords(final OrderAndLineId orderLineId)
	{
		return queryBL.createQueryBuilder(I_M_QtyReservation.class)
				.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_C_OrderLine_ID, orderLineId.getOrderLineRepoId())
				.create()
				.list().stream().collect(Collectors.toList());
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
