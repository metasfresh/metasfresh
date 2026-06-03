package de.metas.inoutcandidate.qty_reservation;

import de.metas.business.BusinessTestHelper;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import de.metas.interfaces.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_QtyReservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link QtyReservationService#shrinkToFitOpenQty(OrderLineId)}.
 */
class QtyReservationServiceShrinkToFitTest
{
	private QtyReservationService service;
	private QtyReservationRepository repo;
	private I_C_UOM uom;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// Register mocks for services loaded via Services.get() inside QtyReservationService
		final IOrderLineBL orderLineBLMock = mock(IOrderLineBL.class);
		Services.registerService(IOrderLineBL.class, orderLineBLMock);

		final ISysConfigBL sysConfigBLMock = mock(ISysConfigBL.class);
		Services.registerService(ISysConfigBL.class, sysConfigBLMock);

		final IShipmentScheduleInvalidateBL invalidateBLMock = mock(IShipmentScheduleInvalidateBL.class);

		uom = BusinessTestHelper.createUOM("Kg");

		repo = new QtyReservationRepository();
		service = new QtyReservationService(invalidateBLMock, repo);
	}

	/**
	 * No-op test: when Σ reserved qty ≤ openQty, nothing should be changed.
	 */
	@Test
	void noOp_whenReservationsFit()
	{
		// Given: orderLine with QtyOrdered=100, QtyDelivered=10 → openQty=90
		final OrderLineId orderLineId = OrderLineId.ofRepoId(200);
		final I_C_OrderLine orderLine = createOrderLineRecord(new BigDecimal("100"), new BigDecimal("10"));

		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		when(orderLineBL.getOrderLineById(orderLineId)).thenReturn(orderLine);

		// And: two reservations totaling 80 (fits within 90)
		createReservationRecord(orderLineId, new BigDecimal("50"));
		createReservationRecord(orderLineId, new BigDecimal("30"));

		// When
		final BigDecimal result = service.shrinkToFitOpenQty(orderLineId);

		// Then: total should still be 80, records unchanged
		assertThat(result).isEqualByComparingTo(new BigDecimal("80"));

		final List<I_M_QtyReservation> records = loadRecords(orderLineId);
		assertThat(records).hasSize(2);
		assertThat(records.get(0).getQty()).isEqualByComparingTo(new BigDecimal("50"));
		assertThat(records.get(1).getQty()).isEqualByComparingTo(new BigDecimal("30"));
	}

	/**
	 * Shrink test: when Σ reserved qty > openQty, oldest reservation should be reduced first.
	 */
	@Test
	void shrinkOldestFirst_whenReservationsExceedOpenQty()
	{
		// Given: orderLine with QtyOrdered=60, QtyDelivered=10 → openQty=50
		final OrderLineId orderLineId = OrderLineId.ofRepoId(201);
		final I_C_OrderLine orderLine = createOrderLineRecord(new BigDecimal("60"), new BigDecimal("10"));

		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		when(orderLineBL.getOrderLineById(orderLineId)).thenReturn(orderLine);

		// And: two reservations totaling 80 (exceeds openQty=50 by 30)
		// Oldest (lower ID) reservation: 40
		// Newer reservation: 40
		createReservationRecord(orderLineId, new BigDecimal("40")); // oldest — should be reduced by 30 → 10
		createReservationRecord(orderLineId, new BigDecimal("40")); // newer — untouched

		// When
		final BigDecimal result = service.shrinkToFitOpenQty(orderLineId);

		// Then: total should be 50 (=openQty), oldest reduced from 40→10, newer untouched at 40
		assertThat(result).isEqualByComparingTo(new BigDecimal("50"));

		final List<I_M_QtyReservation> records = loadRecords(orderLineId);
		assertThat(records).hasSize(2);
		// Records ordered by M_QtyReservation_ID (oldest first)
		assertThat(records.get(0).getQty()).isEqualByComparingTo(new BigDecimal("10")); // reduced
		assertThat(records.get(1).getQty()).isEqualByComparingTo(new BigDecimal("40")); // untouched
	}

	// --- helpers ---

	private I_C_OrderLine createOrderLineRecord(
			final BigDecimal qtyOrdered,
			final BigDecimal qtyDelivered)
	{
		// The record is returned directly by the mock; no ID required.
		final I_C_OrderLine record = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		record.setQtyOrdered(qtyOrdered);
		record.setQtyDelivered(qtyDelivered);
		return record;
	}

	private I_M_QtyReservation createReservationRecord(
			final OrderLineId orderLineId,
			final BigDecimal qty)
	{
		final I_M_QtyReservation record = InterfaceWrapperHelper.newInstance(I_M_QtyReservation.class);
		record.setC_OrderLine_ID(orderLineId.getRepoId());
		record.setM_Product_ID(1);
		record.setM_Warehouse_ID(1);
		record.setC_UOM_ID(uom.getC_UOM_ID());
		record.setSupplyType(SupplyType.ON_HAND.getCode());
		record.setQty(qty);
		record.setQtyDelivered(BigDecimal.ZERO);
		record.setQtyTU(BigDecimal.ZERO);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private List<I_M_QtyReservation> loadRecords(final OrderLineId orderLineId)
	{
		return Services.get(org.adempiere.ad.dao.IQueryBL.class)
				.createQueryBuilder(I_M_QtyReservation.class)
				.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_C_OrderLine_ID, orderLineId)
				.orderBy(I_M_QtyReservation.COLUMNNAME_M_QtyReservation_ID)
				.create()
				.list();
	}
}
