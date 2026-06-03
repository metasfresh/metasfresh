package de.metas.order.split.impl;

import de.metas.inoutcandidate.qty_reservation.QtyReservationService;
import de.metas.order.OrderLineId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link OrderLineSplitListener}.
 *
 * me03 #29261 — Order Line Split.
 */
class OrderLineSplitListenerTest
{
	private QtyReservationService qtyReservationService;
	private OrderLineSplitListener listener;

	@BeforeEach
	void setup()
	{
		AdempiereTestHelper.get().init();
		qtyReservationService = Mockito.mock(QtyReservationService.class);
		listener = new OrderLineSplitListener(qtyReservationService);
	}

	@Test
	void onOriginalLineReduced_callsShrinkToFitOpenQty()
	{
		final OrderLineId id = OrderLineId.ofRepoId(42);
		listener.onOriginalLineReduced(id);
		verify(qtyReservationService).shrinkToFitOpenQty(id);
	}
}
