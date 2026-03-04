package de.metas.inoutcandidate.qty_reservation;

import de.metas.business.BusinessTestHelper;
import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_QtyReservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QtyReservationRepositoryTest
{
	private QtyReservationRepository repo;
	private I_C_UOM uom;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repo = new QtyReservationRepository();
		uom = BusinessTestHelper.createUOM("Kg");
	}

	@Test
	void updateByOrderLineIds_twoRecords_spreadCorrectly()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(100);

		createReservationRecord(orderLineId, "OH", new BigDecimal("60"));
		createReservationRecord(orderLineId, "PS", new BigDecimal("40"));

		// Spread 80 delivered across the two records
		repo.updateByOrderLineIds(
				Collections.singleton(orderLineId),
				buildSpreadUpdater(orderLineId, new BigDecimal("80")));

		final List<I_M_QtyReservation> records = loadRecords(orderLineId);
		assertThat(records).hasSize(2);

		// OH record (sorted first): min(80, 60) = 60
		assertThat(records.get(0).getSupplyType()).isEqualTo("OH");
		assertThat(records.get(0).getQtyDelivered()).isEqualByComparingTo(new BigDecimal("60"));

		// PS record (sorted second): min(20, 40) = 20
		assertThat(records.get(1).getSupplyType()).isEqualTo("PS");
		assertThat(records.get(1).getQtyDelivered()).isEqualByComparingTo(new BigDecimal("20"));
	}

	@Test
	void updateByOrderLineIds_deliveredLessThanFirstRecord()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(101);

		createReservationRecord(orderLineId, "OH", new BigDecimal("60"));
		createReservationRecord(orderLineId, "PS", new BigDecimal("40"));

		repo.updateByOrderLineIds(
				Collections.singleton(orderLineId),
				buildSpreadUpdater(orderLineId, new BigDecimal("30")));

		final List<I_M_QtyReservation> records = loadRecords(orderLineId);
		assertThat(records.get(0).getQtyDelivered()).isEqualByComparingTo(new BigDecimal("30"));
		assertThat(records.get(1).getQtyDelivered()).isEqualByComparingTo(BigDecimal.ZERO);
	}

	@Test
	void updateByOrderLineIds_deliveredExceedsTotal_cappedAtRecordQty()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(102);

		createReservationRecord(orderLineId, "OH", new BigDecimal("60"));
		createReservationRecord(orderLineId, "PS", new BigDecimal("40"));

		repo.updateByOrderLineIds(
				Collections.singleton(orderLineId),
				buildSpreadUpdater(orderLineId, new BigDecimal("120")));

		final List<I_M_QtyReservation> records = loadRecords(orderLineId);
		assertThat(records.get(0).getQtyDelivered()).isEqualByComparingTo(new BigDecimal("60"));
		assertThat(records.get(1).getQtyDelivered()).isEqualByComparingTo(new BigDecimal("40"));
	}

	@Test
	void updateByOrderLineIds_zeroDelivered_allQtyDeliveredSetToZero()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(103);

		final I_M_QtyReservation rec = createReservationRecord(orderLineId, "OH", new BigDecimal("60"));
		rec.setQtyDelivered(new BigDecimal("50")); // pre-existing value
		InterfaceWrapperHelper.save(rec);

		repo.updateByOrderLineIds(
				Collections.singleton(orderLineId),
				buildSpreadUpdater(orderLineId, BigDecimal.ZERO));

		final List<I_M_QtyReservation> records = loadRecords(orderLineId);
		assertThat(records.get(0).getQtyDelivered()).isEqualByComparingTo(BigDecimal.ZERO);
	}

	@Test
	void updateByOrderLineIds_operatorCalledInCorrectOrder_OHbeforePS()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(104);

		createReservationRecord(orderLineId, "PS", new BigDecimal("40"));
		createReservationRecord(orderLineId, "OH", new BigDecimal("60"));

		final List<String> supplyTypeOrder = new ArrayList<>();

		repo.updateByOrderLineIds(
				Collections.singleton(orderLineId),
				reservation -> {
					supplyTypeOrder.add(reservation.getQty().toBigDecimal().toPlainString());
					return reservation;
				});

		// OH (qty=60) should be processed before PS (qty=40), regardless of insertion order
		assertThat(supplyTypeOrder).containsExactly("60", "40");
	}

	// --- helpers ---

	private java.util.function.UnaryOperator<QtyReservation> buildSpreadUpdater(
			final OrderLineId orderLineId,
			final BigDecimal totalDelivered)
	{
		final BigDecimal[] remaining = { totalDelivered };

		return reservation -> {
			final BigDecimal recordQty = reservation.getQty().toBigDecimal();
			final BigDecimal allocated = remaining[0].min(recordQty).max(BigDecimal.ZERO);
			remaining[0] = remaining[0].subtract(allocated);

			final Quantity allocatedQty = reservation.getQty().toZero().add(allocated);
			return reservation.withQtyDelivered(allocatedQty);
		};
	}

	private I_M_QtyReservation createReservationRecord(
			final OrderLineId orderLineId,
			final String supplyType,
			final BigDecimal qty)
	{
		final I_M_QtyReservation record = InterfaceWrapperHelper.newInstance(I_M_QtyReservation.class);
		record.setC_OrderLine_ID(orderLineId.getRepoId());
		record.setM_Product_ID(1);
		record.setM_Warehouse_ID(1);
		record.setC_UOM_ID(uom.getC_UOM_ID());
		record.setSupplyType(supplyType);
		record.setQty(qty);
		record.setQtyDelivered(BigDecimal.ZERO);
		record.setQtyTU(BigDecimal.ZERO);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private java.util.List<I_M_QtyReservation> loadRecords(final OrderLineId orderLineId)
	{
		return de.metas.util.Services.get(org.adempiere.ad.dao.IQueryBL.class)
				.createQueryBuilder(I_M_QtyReservation.class)
				.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_C_OrderLine_ID, orderLineId)
				.orderBy(I_M_QtyReservation.COLUMNNAME_SupplyType)
				.create()
				.list();
	}
}
