package de.metas.inoutcandidate.qty_reservation;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.QtyTU;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
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
	 * Planned-supply reservation: the CU qty must be {@code qtyToReserveTU x packing-capacity},
	 * NOT the (zero) on-hand stock of the planned-supply cockpit row.
	 * <p>
	 * RED: today {@link MakeQtyReservationCommand#execute()} derives CU from the cockpit row's
	 * {@code qtyStock} (0 for planned supply) → {@code Qty=0}. Target: 10 TU x 11 CU/TU = 110.
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
		final de.metas.interfaces.I_C_OrderLine orderLineRecord = mock(de.metas.interfaces.I_C_OrderLine.class);
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
}
