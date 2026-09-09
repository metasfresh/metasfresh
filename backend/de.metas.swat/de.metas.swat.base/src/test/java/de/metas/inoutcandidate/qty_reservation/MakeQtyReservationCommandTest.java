package de.metas.inoutcandidate.qty_reservation;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.QtyTU;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MakeQtyReservationCommandTest
{
	/** A remaining-ordered qty large enough that the order-need cap (AC3a) never bites in a given test. */
	private static final BigDecimal REMAINING_UNBOUNDED = new BigDecimal("100000");

	private I_C_UOM uom;
	private UomId uomId;
	private ProductId productId;
	private WarehouseId warehouseId;
	private IProductBL productBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uom = BusinessTestHelper.createUOM("Each");
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());
		productId = BusinessTestHelper.createProductId("P1", uom);
		warehouseId = WarehouseId.ofRepoId(1);

		// the product's stock UOM is the same "Each" UOM used for the rowVO qty and the capacity
		productBL = mock(IProductBL.class);
		when(productBL.getStockUOMId(productId)).thenReturn(uomId);
	}

	/** Stubs the order-need remaining-qty (AC3a) on the mocked service for the standard order line/UOM. */
	private void stubRemainingOrderedQty(
			final QtyReservationService qtyReservationService,
			final OrderAndLineId salesOrderAndLineId,
			final BigDecimal remaining)
	{
		when(qtyReservationService.computeRemainingOrderedQty(salesOrderAndLineId, uomId))
				.thenReturn(Quantitys.of(remaining, uomId));
	}

	/**
	 * Planned-supply reservation: a reservation is created with
	 * {@code Qty = qtyToReserveTU x packing-capacity} (10 x 11 = 110), NOT the row's (zero) on-hand
	 * stock. Deriving the CU qty from the packing capacity rather than the cockpit row's
	 * {@code qtyStock} (0 for planned supply) is what avoids a {@code Qty=0} reservation.
	 */
	@Test
	void plannedSupply_reservesQtyTU_timesPackingCapacity()
	{
		// the PLANNED_SUPPLY cockpit row carries NO on-hand stock (qtyStock = 0)
		final Quantity qtyStockZero = Quantitys.of(BigDecimal.ZERO, uomId);
		final MaterialCockpitV2RowVO rowVO = MaterialCockpitV2RowVO.builder()
				.productId(productId)
				.warehouseId(warehouseId)
				.supplyType(SupplyType.PLANNED_SUPPLY)
				.availabilityType(AvailabilityType.AVAILABLE)
				.qtyTU(QtyTU.ofInt(10))
				.qtyStock(qtyStockZero)
				.build();

		final OrderAndLineId salesOrderAndLineId = OrderAndLineId.ofRepoIds(1, 1);

		// --- mock IOrderLineBL: packing-item capacity = 11 CU/TU ---
		// getOrderLineById(..) returns de.metas.interfaces.I_C_OrderLine, which inherits
		// getQtyItemCapacity() from org.compiere.model.I_C_OrderLine.
		final I_C_OrderLine orderLineRecord = mock(I_C_OrderLine.class);
		when(orderLineRecord.getQtyItemCapacity()).thenReturn(new BigDecimal("11"));
		when(orderLineRecord.getC_UOM_ID()).thenReturn(uomId.getRepoId());

		final IOrderLineBL orderLineBL = mock(IOrderLineBL.class);
		when(orderLineBL.getOrderLineById(salesOrderAndLineId)).thenReturn(orderLineRecord);

		// --- mock QtyReservationService: no-op makeReservation, capture the request ---
		final QtyReservationService qtyReservationService = mock(QtyReservationService.class);
		stubRemainingOrderedQty(qtyReservationService, salesOrderAndLineId, REMAINING_UNBOUNDED);
		final ArgumentCaptor<CreateQtyReservationRequest> requestCaptor =
				ArgumentCaptor.forClass(CreateQtyReservationRequest.class);

		MakeQtyReservationCommand.builder()
				.orderLineBL(orderLineBL)
				.productBL(productBL)
				.qtyReservationService(qtyReservationService)
				.rowVO(rowVO)
				.salesOrderAndLineId(salesOrderAndLineId)
				.qtyToReserveTU(QtyTU.ofInt(10))
				.build()
				.execute();

		verify(qtyReservationService).makeReservation(requestCaptor.capture());
		final CreateQtyReservationRequest request = requestCaptor.getValue();

		// 10 TU x 11 CU/TU = 110 CU (NOT capped to the planned-supply row's qtyStock of 0)
		assertThat(request.getQty().toBigDecimal()).isEqualByComparingTo(new BigDecimal("110"));
		assertThat(request.getQtyTU().toInt()).isEqualTo(10);
	}

	/**
	 * On-hand reservation: {@code qtyToReserveTU x capacity} (10 x 11 = 110) exceeds the row's
	 * on-hand stock (50), so the reserved CU qty is capped at the on-hand stock.
	 */
	@Test
	void onHand_cappedAtQtyStock()
	{
		final Quantity qtyStock = Quantitys.of(new BigDecimal("50"), uomId);
		final MaterialCockpitV2RowVO rowVO = MaterialCockpitV2RowVO.builder()
				.productId(productId)
				.warehouseId(warehouseId)
				.supplyType(SupplyType.ON_HAND)
				.availabilityType(AvailabilityType.AVAILABLE)
				.qtyTU(QtyTU.ofInt(10))
				.qtyStock(qtyStock)
				.build();

		final OrderAndLineId salesOrderAndLineId = OrderAndLineId.ofRepoIds(1, 1);

		final I_C_OrderLine orderLineRecord = mock(I_C_OrderLine.class);
		when(orderLineRecord.getQtyItemCapacity()).thenReturn(new BigDecimal("11"));
		when(orderLineRecord.getC_UOM_ID()).thenReturn(uomId.getRepoId());

		final IOrderLineBL orderLineBL = mock(IOrderLineBL.class);
		when(orderLineBL.getOrderLineById(salesOrderAndLineId)).thenReturn(orderLineRecord);

		final QtyReservationService qtyReservationService = mock(QtyReservationService.class);
		stubRemainingOrderedQty(qtyReservationService, salesOrderAndLineId, REMAINING_UNBOUNDED);
		final ArgumentCaptor<CreateQtyReservationRequest> requestCaptor =
				ArgumentCaptor.forClass(CreateQtyReservationRequest.class);

		MakeQtyReservationCommand.builder()
				.orderLineBL(orderLineBL)
				.productBL(productBL)
				.qtyReservationService(qtyReservationService)
				.rowVO(rowVO)
				.salesOrderAndLineId(salesOrderAndLineId)
				.qtyToReserveTU(QtyTU.ofInt(10))
				.build()
				.execute();

		verify(qtyReservationService).makeReservation(requestCaptor.capture());
		final CreateQtyReservationRequest request = requestCaptor.getValue();

		// 10 TU x 11 CU/TU = 110, but capped at the on-hand stock of 50
		assertThat(request.getQty().toBigDecimal()).isEqualByComparingTo(new BigDecimal("50"));
		assertThat(request.getQtyTU().toInt()).isEqualTo(10);
	}

	/**
	 * On-hand reservation: {@code qtyToReserveTU x capacity} (2 x 11 = 22) is within the row's
	 * on-hand stock (50), so the reserved CU qty is NOT reduced.
	 */
	@Test
	void onHand_notReducedWhenWithinStock()
	{
		final Quantity qtyStock = Quantitys.of(new BigDecimal("50"), uomId);
		final MaterialCockpitV2RowVO rowVO = MaterialCockpitV2RowVO.builder()
				.productId(productId)
				.warehouseId(warehouseId)
				.supplyType(SupplyType.ON_HAND)
				.availabilityType(AvailabilityType.AVAILABLE)
				.qtyTU(QtyTU.ofInt(10))
				.qtyStock(qtyStock)
				.build();

		final OrderAndLineId salesOrderAndLineId = OrderAndLineId.ofRepoIds(1, 1);

		final I_C_OrderLine orderLineRecord = mock(I_C_OrderLine.class);
		when(orderLineRecord.getQtyItemCapacity()).thenReturn(new BigDecimal("11"));
		when(orderLineRecord.getC_UOM_ID()).thenReturn(uomId.getRepoId());

		final IOrderLineBL orderLineBL = mock(IOrderLineBL.class);
		when(orderLineBL.getOrderLineById(salesOrderAndLineId)).thenReturn(orderLineRecord);

		final QtyReservationService qtyReservationService = mock(QtyReservationService.class);
		stubRemainingOrderedQty(qtyReservationService, salesOrderAndLineId, REMAINING_UNBOUNDED);
		final ArgumentCaptor<CreateQtyReservationRequest> requestCaptor =
				ArgumentCaptor.forClass(CreateQtyReservationRequest.class);

		MakeQtyReservationCommand.builder()
				.orderLineBL(orderLineBL)
				.productBL(productBL)
				.qtyReservationService(qtyReservationService)
				.rowVO(rowVO)
				.salesOrderAndLineId(salesOrderAndLineId)
				.qtyToReserveTU(QtyTU.ofInt(2))
				.build()
				.execute();

		verify(qtyReservationService).makeReservation(requestCaptor.capture());
		final CreateQtyReservationRequest request = requestCaptor.getValue();

		// 2 TU x 11 CU/TU = 22, within the on-hand stock of 50 -> not reduced
		assertThat(request.getQty().toBigDecimal()).isEqualByComparingTo(new BigDecimal("22"));
		assertThat(request.getQtyTU().toInt()).isEqualTo(2);
	}

	/**
	 * Planned-supply reservation with NO order-line {@code QtyItemCapacity}: the command falls back to
	 * the caller-supplied {@code capacityPerTUFallback} (13 CU/TU). 10 TU x 13 CU/TU = 130 CU, not
	 * capped (planned supply, and the order line still needs more than 130).
	 */
	@Test
	void plannedSupply_usesFallbackCapacity_whenNoQtyItemCapacity()
	{
		final Quantity qtyStockZero = Quantitys.of(BigDecimal.ZERO, uomId);
		final MaterialCockpitV2RowVO rowVO = MaterialCockpitV2RowVO.builder()
				.productId(productId)
				.warehouseId(warehouseId)
				.supplyType(SupplyType.PLANNED_SUPPLY)
				.availabilityType(AvailabilityType.AVAILABLE)
				.qtyTU(QtyTU.ofInt(10))
				.qtyStock(qtyStockZero)
				.build();

		final OrderAndLineId salesOrderAndLineId = OrderAndLineId.ofRepoIds(1, 1);

		// order line carries NO explicit packing-item capacity -> the fallback capacity is used
		final I_C_OrderLine orderLineRecord = mock(I_C_OrderLine.class);
		when(orderLineRecord.getQtyItemCapacity()).thenReturn(BigDecimal.ZERO);

		final IOrderLineBL orderLineBL = mock(IOrderLineBL.class);
		when(orderLineBL.getOrderLineById(salesOrderAndLineId)).thenReturn(orderLineRecord);

		final QtyReservationService qtyReservationService = mock(QtyReservationService.class);
		stubRemainingOrderedQty(qtyReservationService, salesOrderAndLineId, REMAINING_UNBOUNDED);
		final ArgumentCaptor<CreateQtyReservationRequest> requestCaptor =
				ArgumentCaptor.forClass(CreateQtyReservationRequest.class);

		// caller-resolved fallback: 13 CU/TU (in the product's stock UOM)
		final Quantity capacityPerTUFallback = Quantitys.of(new BigDecimal("13"), uomId);

		MakeQtyReservationCommand.builder()
				.orderLineBL(orderLineBL)
				.productBL(productBL)
				.qtyReservationService(qtyReservationService)
				.rowVO(rowVO)
				.salesOrderAndLineId(salesOrderAndLineId)
				.qtyToReserveTU(QtyTU.ofInt(10))
				.capacityPerTUFallback(capacityPerTUFallback)
				.build()
				.execute();

		verify(qtyReservationService).makeReservation(requestCaptor.capture());
		final CreateQtyReservationRequest request = requestCaptor.getValue();

		// 10 TU x 13 CU/TU (fallback) = 130 CU
		assertThat(request.getQty().toBigDecimal()).isEqualByComparingTo(new BigDecimal("130"));
		assertThat(request.getQtyTU().toInt()).isEqualTo(10);
	}

	/**
	 * Neither the order line's {@code QtyItemCapacity} nor a {@code capacityPerTUFallback} yields a
	 * positive capacity: the command cannot derive a CU qty and raises a user-validation error
	 * (before the order-need cap is even consulted).
	 */
	@Test
	void noCapacity_throwsUserError()
	{
		final Quantity qtyStockZero = Quantitys.of(BigDecimal.ZERO, uomId);
		final MaterialCockpitV2RowVO rowVO = MaterialCockpitV2RowVO.builder()
				.productId(productId)
				.warehouseId(warehouseId)
				.supplyType(SupplyType.PLANNED_SUPPLY)
				.availabilityType(AvailabilityType.AVAILABLE)
				.qtyTU(QtyTU.ofInt(10))
				.qtyStock(qtyStockZero)
				.build();

		final OrderAndLineId salesOrderAndLineId = OrderAndLineId.ofRepoIds(1, 1);

		// no explicit packing-item capacity AND no fallback supplied
		final I_C_OrderLine orderLineRecord = mock(I_C_OrderLine.class);
		when(orderLineRecord.getQtyItemCapacity()).thenReturn(BigDecimal.ZERO);

		final IOrderLineBL orderLineBL = mock(IOrderLineBL.class);
		when(orderLineBL.getOrderLineById(salesOrderAndLineId)).thenReturn(orderLineRecord);

		// computeRemainingOrderedQty is intentionally NOT stubbed — the capacity error fires first,
		// before the order-need cap is consulted, so the mock is never reached here.
		final QtyReservationService qtyReservationService = mock(QtyReservationService.class);

		final MakeQtyReservationCommand command = MakeQtyReservationCommand.builder()
				.orderLineBL(orderLineBL)
				.productBL(productBL)
				.qtyReservationService(qtyReservationService)
				.rowVO(rowVO)
				.salesOrderAndLineId(salesOrderAndLineId)
				.qtyToReserveTU(QtyTU.ofInt(10))
				.capacityPerTUFallback(null)
				.build();

		assertThatThrownBy(command::execute)
				.isInstanceOf(AdempiereException.class)
				.satisfies(e -> assertThat(((AdempiereException)e).isUserValidationError()).isTrue());
	}

	/**
	 * Order-need cap (AC3a), planned supply: {@code qtyToReserveTU x capacity} (10 x 10 = 100) exceeds
	 * the order line's remaining unreserved ordered qty (95), so the reserved CU qty is capped at 95.
	 * The TU count is unchanged (10) — TU reconciliation happens later at SO completion. PS is NOT
	 * exempt from the order-need cap (only from the on-hand stock cap).
	 */
	@Test
	void plannedSupply_cappedAtRemainingOrderedQty()
	{
		final Quantity qtyStockZero = Quantitys.of(BigDecimal.ZERO, uomId);
		final MaterialCockpitV2RowVO rowVO = MaterialCockpitV2RowVO.builder()
				.productId(productId)
				.warehouseId(warehouseId)
				.supplyType(SupplyType.PLANNED_SUPPLY)
				.availabilityType(AvailabilityType.AVAILABLE)
				.qtyTU(QtyTU.ofInt(10))
				.qtyStock(qtyStockZero)
				.build();

		final OrderAndLineId salesOrderAndLineId = OrderAndLineId.ofRepoIds(1, 1);

		// capacity = 10 CU/TU; raw = 10 x 10 = 100
		final I_C_OrderLine orderLineRecord = mock(I_C_OrderLine.class);
		when(orderLineRecord.getQtyItemCapacity()).thenReturn(new BigDecimal("10"));
		when(orderLineRecord.getC_UOM_ID()).thenReturn(uomId.getRepoId());

		final IOrderLineBL orderLineBL = mock(IOrderLineBL.class);
		when(orderLineBL.getOrderLineById(salesOrderAndLineId)).thenReturn(orderLineRecord);

		final QtyReservationService qtyReservationService = mock(QtyReservationService.class);
		// order line still needs only 95 CU (e.g. QtyOrdered 95, nothing reserved yet)
		stubRemainingOrderedQty(qtyReservationService, salesOrderAndLineId, new BigDecimal("95"));
		final ArgumentCaptor<CreateQtyReservationRequest> requestCaptor =
				ArgumentCaptor.forClass(CreateQtyReservationRequest.class);

		MakeQtyReservationCommand.builder()
				.orderLineBL(orderLineBL)
				.productBL(productBL)
				.qtyReservationService(qtyReservationService)
				.rowVO(rowVO)
				.salesOrderAndLineId(salesOrderAndLineId)
				.qtyToReserveTU(QtyTU.ofInt(10))
				.build()
				.execute();

		verify(qtyReservationService).makeReservation(requestCaptor.capture());
		final CreateQtyReservationRequest request = requestCaptor.getValue();

		// 10 TU x 10 CU/TU = 100, capped at the remaining ordered qty of 95
		assertThat(request.getQty().toBigDecimal()).isEqualByComparingTo(new BigDecimal("95"));
		assertThat(request.getQtyTU().toInt()).isEqualTo(10);
	}

	/**
	 * Order-need cap (AC3a), on-hand, below the stock cap: {@code qtyToReserveTU x capacity}
	 * (10 x 11 = 110) is first capped at on-hand stock (50), then further capped at the order line's
	 * remaining ordered qty (30) — the order-need cap can bite below the stock cap. Result = 30.
	 */
	@Test
	void onHand_cappedAtRemainingOrderedQty()
	{
		final Quantity qtyStock = Quantitys.of(new BigDecimal("50"), uomId);
		final MaterialCockpitV2RowVO rowVO = MaterialCockpitV2RowVO.builder()
				.productId(productId)
				.warehouseId(warehouseId)
				.supplyType(SupplyType.ON_HAND)
				.availabilityType(AvailabilityType.AVAILABLE)
				.qtyTU(QtyTU.ofInt(10))
				.qtyStock(qtyStock)
				.build();

		final OrderAndLineId salesOrderAndLineId = OrderAndLineId.ofRepoIds(1, 1);

		final I_C_OrderLine orderLineRecord = mock(I_C_OrderLine.class);
		when(orderLineRecord.getQtyItemCapacity()).thenReturn(new BigDecimal("11"));
		when(orderLineRecord.getC_UOM_ID()).thenReturn(uomId.getRepoId());

		final IOrderLineBL orderLineBL = mock(IOrderLineBL.class);
		when(orderLineBL.getOrderLineById(salesOrderAndLineId)).thenReturn(orderLineRecord);

		final QtyReservationService qtyReservationService = mock(QtyReservationService.class);
		// order line still needs only 30 CU (less than both 110 and the 50 stock cap)
		stubRemainingOrderedQty(qtyReservationService, salesOrderAndLineId, new BigDecimal("30"));
		final ArgumentCaptor<CreateQtyReservationRequest> requestCaptor =
				ArgumentCaptor.forClass(CreateQtyReservationRequest.class);

		MakeQtyReservationCommand.builder()
				.orderLineBL(orderLineBL)
				.productBL(productBL)
				.qtyReservationService(qtyReservationService)
				.rowVO(rowVO)
				.salesOrderAndLineId(salesOrderAndLineId)
				.qtyToReserveTU(QtyTU.ofInt(10))
				.build()
				.execute();

		verify(qtyReservationService).makeReservation(requestCaptor.capture());
		final CreateQtyReservationRequest request = requestCaptor.getValue();

		// min(10x11=110, stock 50, remaining 30) = 30
		assertThat(request.getQty().toBigDecimal()).isEqualByComparingTo(new BigDecimal("30"));
		assertThat(request.getQtyTU().toInt()).isEqualTo(10);
	}

	/**
	 * Order-need cap (AC3a) is slack, on-hand stock cap binds: {@code qtyToReserveTU x capacity}
	 * (10 x 11 = 110) is capped at on-hand stock (30); the remaining ordered qty (50) is larger, so it
	 * does not reduce further. Result = 30. Exercises the interaction direction where the stock cap is
	 * tighter than the order-need cap.
	 */
	@Test
	void onHand_cappedAtStock_whenStockCapTighterThanOrderNeed()
	{
		final Quantity qtyStock = Quantitys.of(new BigDecimal("30"), uomId);
		final MaterialCockpitV2RowVO rowVO = MaterialCockpitV2RowVO.builder()
				.productId(productId)
				.warehouseId(warehouseId)
				.supplyType(SupplyType.ON_HAND)
				.availabilityType(AvailabilityType.AVAILABLE)
				.qtyTU(QtyTU.ofInt(10))
				.qtyStock(qtyStock)
				.build();

		final OrderAndLineId salesOrderAndLineId = OrderAndLineId.ofRepoIds(1, 1);

		final I_C_OrderLine orderLineRecord = mock(I_C_OrderLine.class);
		when(orderLineRecord.getQtyItemCapacity()).thenReturn(new BigDecimal("11"));
		when(orderLineRecord.getC_UOM_ID()).thenReturn(uomId.getRepoId());

		final IOrderLineBL orderLineBL = mock(IOrderLineBL.class);
		when(orderLineBL.getOrderLineById(salesOrderAndLineId)).thenReturn(orderLineRecord);

		final QtyReservationService qtyReservationService = mock(QtyReservationService.class);
		// order line still needs 50 CU — larger than the 30 stock cap, so the order-need cap does not bite
		stubRemainingOrderedQty(qtyReservationService, salesOrderAndLineId, new BigDecimal("50"));
		final ArgumentCaptor<CreateQtyReservationRequest> requestCaptor =
				ArgumentCaptor.forClass(CreateQtyReservationRequest.class);

		MakeQtyReservationCommand.builder()
				.orderLineBL(orderLineBL)
				.productBL(productBL)
				.qtyReservationService(qtyReservationService)
				.rowVO(rowVO)
				.salesOrderAndLineId(salesOrderAndLineId)
				.qtyToReserveTU(QtyTU.ofInt(10))
				.build()
				.execute();

		verify(qtyReservationService).makeReservation(requestCaptor.capture());
		final CreateQtyReservationRequest request = requestCaptor.getValue();

		// min(10x11=110, stock 30, remaining 50) = 30 (stock cap binds)
		assertThat(request.getQty().toBigDecimal()).isEqualByComparingTo(new BigDecimal("30"));
		assertThat(request.getQtyTU().toInt()).isEqualTo(10);
	}

	/**
	 * Order-need cap (AC3b): when the order line is already fully reserved (remaining ordered qty = 0),
	 * the command rejects the reservation with a user-validation error rather than minting a
	 * {@code Qty = 0} reservation (the original defect this feature fixes).
	 */
	@Test
	void rejectsWhenLineFullyReserved()
	{
		final Quantity qtyStockZero = Quantitys.of(BigDecimal.ZERO, uomId);
		final MaterialCockpitV2RowVO rowVO = MaterialCockpitV2RowVO.builder()
				.productId(productId)
				.warehouseId(warehouseId)
				.supplyType(SupplyType.PLANNED_SUPPLY)
				.availabilityType(AvailabilityType.AVAILABLE)
				.qtyTU(QtyTU.ofInt(10))
				.qtyStock(qtyStockZero)
				.build();

		final OrderAndLineId salesOrderAndLineId = OrderAndLineId.ofRepoIds(1, 1);

		final I_C_OrderLine orderLineRecord = mock(I_C_OrderLine.class);
		when(orderLineRecord.getQtyItemCapacity()).thenReturn(new BigDecimal("10"));
		when(orderLineRecord.getC_UOM_ID()).thenReturn(uomId.getRepoId());

		final IOrderLineBL orderLineBL = mock(IOrderLineBL.class);
		when(orderLineBL.getOrderLineById(salesOrderAndLineId)).thenReturn(orderLineRecord);

		final QtyReservationService qtyReservationService = mock(QtyReservationService.class);
		// the line is already fully reserved -> remaining = 0
		stubRemainingOrderedQty(qtyReservationService, salesOrderAndLineId, BigDecimal.ZERO);

		final MakeQtyReservationCommand command = MakeQtyReservationCommand.builder()
				.orderLineBL(orderLineBL)
				.productBL(productBL)
				.qtyReservationService(qtyReservationService)
				.rowVO(rowVO)
				.salesOrderAndLineId(salesOrderAndLineId)
				.qtyToReserveTU(QtyTU.ofInt(10))
				.build();

		assertThatThrownBy(command::execute)
				.isInstanceOf(AdempiereException.class)
				.satisfies(e -> assertThat(((AdempiereException)e).isUserValidationError()).isTrue());
	}
}
