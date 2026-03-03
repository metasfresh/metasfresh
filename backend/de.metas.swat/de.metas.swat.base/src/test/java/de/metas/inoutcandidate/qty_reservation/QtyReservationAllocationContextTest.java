package de.metas.inoutcandidate.qty_reservation;

import de.metas.business.BusinessTestHelper;
import de.metas.inoutcandidate.qty_reservation.QtyReservationAllocationContext.ReservationDetail;
import de.metas.inoutcandidate.qty_reservation.QtyReservationAllocationContext.StockMatchingKey;
import de.metas.material.event.commons.AttributesKey;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class QtyReservationAllocationContextTest
{
	private static final ProductId PRODUCT_1 = ProductId.ofRepoId(100);
	private static final WarehouseId WAREHOUSE_1 = WarehouseId.ofRepoId(200);
	private static final StockMatchingKey KEY_1 = StockMatchingKey.of(PRODUCT_1, WAREHOUSE_1, AttributesKey.NONE);

	private static final OrderLineId OL_RESERVED_1 = OrderLineId.ofRepoId(10);
	private static final OrderLineId OL_RESERVED_2 = OrderLineId.ofRepoId(20);
	private static final OrderLineId OL_NOT_RESERVED = OrderLineId.ofRepoId(30);

	private static I_C_UOM uom;

	@BeforeAll
	static void beforeAll()
	{
		AdempiereTestHelper.get().init();
		uom = BusinessTestHelper.createUOM("testUOM");
	}

	@Test
	void emptyContext_returnsZeroForEverything()
	{
		final QtyReservationAllocationContext ctx = buildContext();

		assertThat(ctx.isEmpty()).isTrue();
		assertThat(ctx.getMyReservedQty(OL_RESERVED_1)).isEqualByComparingTo(BigDecimal.ZERO);
		assertThat(ctx.getReservedByOthers(OL_RESERVED_1, KEY_1)).isEqualByComparingTo(BigDecimal.ZERO);
	}

	@Test
	void reservedByOthers_forNonReservedSchedule()
	{
		final QtyReservationAllocationContext ctx = buildContext(
				reservation(OL_RESERVED_1, KEY_1, new BigDecimal("50")),
				reservation(OL_RESERVED_2, KEY_1, new BigDecimal("30"))
		);

		// non-reserved schedule should see all 80 reserved by others
		assertThat(ctx.getReservedByOthers(OL_NOT_RESERVED, KEY_1))
				.isEqualByComparingTo(new BigDecimal("80"));
	}

	@Test
	void reservedByOthers_forReservedSchedule()
	{
		final QtyReservationAllocationContext ctx = buildContext(
				reservation(OL_RESERVED_1, KEY_1, new BigDecimal("50")),
				reservation(OL_RESERVED_2, KEY_1, new BigDecimal("30"))
		);

		// OL_RESERVED_1 sees only OL_RESERVED_2's 30 as "others"
		assertThat(ctx.getReservedByOthers(OL_RESERVED_1, KEY_1))
				.isEqualByComparingTo(new BigDecimal("30"));

		// OL_RESERVED_2 sees only OL_RESERVED_1's 50 as "others"
		assertThat(ctx.getReservedByOthers(OL_RESERVED_2, KEY_1))
				.isEqualByComparingTo(new BigDecimal("50"));
	}

	@Test
	void consumeReservation_decreasesRemainingForOthers()
	{
		final QtyReservationAllocationContext ctx = buildContext(
				reservation(OL_RESERVED_1, KEY_1, new BigDecimal("50")),
				reservation(OL_RESERVED_2, KEY_1, new BigDecimal("30"))
		);

		// OL_RESERVED_1 consumes 40 of its 50
		ctx.consumeReservation(OL_RESERVED_1, new BigDecimal("40"));

		// OL_RESERVED_1 now has only 10 remaining
		assertThat(ctx.getMyReservedQty(OL_RESERVED_1))
				.isEqualByComparingTo(new BigDecimal("10"));

		// Total remaining = 10 + 30 = 40. OL_RESERVED_2 sees 40 - 30 = 10 reserved by others
		assertThat(ctx.getReservedByOthers(OL_RESERVED_2, KEY_1))
				.isEqualByComparingTo(new BigDecimal("10"));

		// Non-reserved sees all 40 remaining
		assertThat(ctx.getReservedByOthers(OL_NOT_RESERVED, KEY_1))
				.isEqualByComparingTo(new BigDecimal("40"));
	}

	@Test
	void consumeReservation_clampedToReservedQty()
	{
		final QtyReservationAllocationContext ctx = buildContext(
				reservation(OL_RESERVED_1, KEY_1, new BigDecimal("50"))
		);

		// Try to consume more than reserved
		ctx.consumeReservation(OL_RESERVED_1, new BigDecimal("999"));

		assertThat(ctx.getMyReservedQty(OL_RESERVED_1))
				.isEqualByComparingTo(BigDecimal.ZERO);

		assertThat(ctx.getReservedByOthers(OL_NOT_RESERVED, KEY_1))
				.isEqualByComparingTo(BigDecimal.ZERO);
	}

	@Test
	void consumeReservation_noOpForNonReservedOrderLine()
	{
		final QtyReservationAllocationContext ctx = buildContext(
				reservation(OL_RESERVED_1, KEY_1, new BigDecimal("50"))
		);

		// Consuming for a non-reserved order line is a no-op
		ctx.consumeReservation(OL_NOT_RESERVED, new BigDecimal("10"));

		assertThat(ctx.getReservedByOthers(OL_NOT_RESERVED, KEY_1))
				.isEqualByComparingTo(new BigDecimal("50"));
	}

	@Test
	void reservedByOthers_nullOrderLineId_treatedAsNonReserved()
	{
		final QtyReservationAllocationContext ctx = buildContext(
				reservation(OL_RESERVED_1, KEY_1, new BigDecimal("50"))
		);

		assertThat(ctx.getReservedByOthers(null, KEY_1))
				.isEqualByComparingTo(new BigDecimal("50"));
	}

	@Test
	void differentKeys_doNotInterfere()
	{
		final StockMatchingKey key2 = StockMatchingKey.of(ProductId.ofRepoId(999), WAREHOUSE_1, AttributesKey.NONE);

		final QtyReservationAllocationContext ctx = buildContext(
				reservation(OL_RESERVED_1, KEY_1, new BigDecimal("50")),
				reservation(OL_RESERVED_2, key2, new BigDecimal("30"))
		);

		// OL_NOT_RESERVED sees 50 for KEY_1 (not 80)
		assertThat(ctx.getReservedByOthers(OL_NOT_RESERVED, KEY_1))
				.isEqualByComparingTo(new BigDecimal("50"));

		// OL_NOT_RESERVED sees 30 for key2
		assertThat(ctx.getReservedByOthers(OL_NOT_RESERVED, key2))
				.isEqualByComparingTo(new BigDecimal("30"));
	}

	// --------------------------------------------------------------------------------------------
	// Helpers
	// --------------------------------------------------------------------------------------------

	@lombok.Value
	private static class ReservationSpec
	{
		OrderLineId orderLineId;
		StockMatchingKey key;
		Quantity qty;
	}

	private static ReservationSpec reservation(final OrderLineId orderLineId, final StockMatchingKey key, final BigDecimal qty)
	{
		return new ReservationSpec(orderLineId, key, Quantity.of(qty, uom));
	}

	private static QtyReservationAllocationContext buildContext(final ReservationSpec... specs)
	{
		if (specs.length == 0)
		{
			return QtyReservationAllocationContext.createForTesting(
					com.google.common.collect.ImmutableMap.of(),
					new java.util.HashMap<>()
			);
		}

		final com.google.common.collect.ImmutableMap.Builder<OrderLineId, ReservationDetail> byOrderLine = com.google.common.collect.ImmutableMap.builder();
		final java.util.Map<StockMatchingKey, Quantity> remainingByKey = new java.util.HashMap<>();

		final java.util.Map<OrderLineId, Quantity> qtyByOL = new java.util.HashMap<>();
		final java.util.Map<OrderLineId, StockMatchingKey> keyByOL = new java.util.HashMap<>();

		for (final ReservationSpec spec : specs)
		{
			qtyByOL.merge(spec.getOrderLineId(), spec.getQty(), Quantity::add);
			keyByOL.put(spec.getOrderLineId(), spec.getKey());
			remainingByKey.merge(spec.getKey(), spec.getQty(), Quantity::add);
		}

		for (final java.util.Map.Entry<OrderLineId, Quantity> entry : qtyByOL.entrySet())
		{
			byOrderLine.put(entry.getKey(), new ReservationDetail(keyByOL.get(entry.getKey()), entry.getValue()));
		}

		return QtyReservationAllocationContext.createForTesting(byOrderLine.build(), remainingByKey);
	}
}
