package de.metas.inoutcandidate.qty_reservation;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.QtyTU;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
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
	private I_C_UOM uom;
	private UomId uomId;
	private ProductId productId;
	private WarehouseId warehouseId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uom = BusinessTestHelper.createUOM("Each");
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());
		productId = BusinessTestHelper.createProductId("P1", uom);
		warehouseId = WarehouseId.ofRepoId(1);
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

		// --- mock IOrderLineBL: packing-item capacity = 11 CU/TU, QtyOrdered = 110 Each ---
		// getOrderLineById(..) returns de.metas.interfaces.I_C_OrderLine, which inherits
		// getQtyItemCapacity() from org.compiere.model.I_C_OrderLine.
		final I_C_OrderLine orderLineRecord = mock(I_C_OrderLine.class);
		when(orderLineRecord.getQtyItemCapacity()).thenReturn(new BigDecimal("11"));
		when(orderLineRecord.getC_UOM_ID()).thenReturn(uomId.getRepoId());

		final Quantity qtyOrdered = Quantitys.of(new BigDecimal("110"), uomId);

		final IOrderLineBL orderLineBL = mock(IOrderLineBL.class);
		when(orderLineBL.getOrderLineById(salesOrderAndLineId)).thenReturn(orderLineRecord);
		when(orderLineBL.getQtyOrdered(salesOrderAndLineId)).thenReturn(qtyOrdered);

		// --- mock QtyReservationService: no-op makeReservation, capture the request ---
		final QtyReservationService qtyReservationService = mock(QtyReservationService.class);
		final ArgumentCaptor<CreateQtyReservationRequest> requestCaptor =
				ArgumentCaptor.forClass(CreateQtyReservationRequest.class);

		MakeQtyReservationCommand.builder()
				.orderLineBL(orderLineBL)
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
		final ArgumentCaptor<CreateQtyReservationRequest> requestCaptor =
				ArgumentCaptor.forClass(CreateQtyReservationRequest.class);

		MakeQtyReservationCommand.builder()
				.orderLineBL(orderLineBL)
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
		final ArgumentCaptor<CreateQtyReservationRequest> requestCaptor =
				ArgumentCaptor.forClass(CreateQtyReservationRequest.class);

		MakeQtyReservationCommand.builder()
				.orderLineBL(orderLineBL)
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
	 * capped (planned supply).
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
		final ArgumentCaptor<CreateQtyReservationRequest> requestCaptor =
				ArgumentCaptor.forClass(CreateQtyReservationRequest.class);

		// caller-resolved fallback: 13 CU/TU (in the product's stock UOM)
		final Quantity capacityPerTUFallback = Quantitys.of(new BigDecimal("13"), uomId);

		MakeQtyReservationCommand.builder()
				.orderLineBL(orderLineBL)
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
	 * positive capacity: the command cannot derive a CU qty and raises a user-validation error.
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

		final QtyReservationService qtyReservationService = mock(QtyReservationService.class);

		final MakeQtyReservationCommand command = MakeQtyReservationCommand.builder()
				.orderLineBL(orderLineBL)
				.qtyReservationService(qtyReservationService)
				.rowVO(rowVO)
				.salesOrderAndLineId(salesOrderAndLineId)
				.qtyToReserveTU(QtyTU.ofInt(10))
				.capacityPerTUFallback(null)
				.build();

		assertThatThrownBy(command::execute)
				.isInstanceOf(AdempiereException.class);
	}
}
