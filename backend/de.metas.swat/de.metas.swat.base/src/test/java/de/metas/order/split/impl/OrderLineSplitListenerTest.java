package de.metas.order.split.impl;

import de.metas.inoutcandidate.qty_reservation.QtyReservationService;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
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
	private IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL;
	private OrderLineSplitListener listener;

	@BeforeEach
	void setup()
	{
		AdempiereTestHelper.get().init();
		qtyReservationService = Mockito.mock(QtyReservationService.class);
		invoiceCandidateHandlerBL = Mockito.mock(IInvoiceCandidateHandlerBL.class);
		Services.registerService(IInvoiceCandidateHandlerBL.class, invoiceCandidateHandlerBL);
		listener = new OrderLineSplitListener(qtyReservationService);
	}

	@Test
	void onOriginalLineReduced_callsShrinkToFitOpenQty()
	{
		final OrderLineId id = OrderLineId.ofRepoId(42);
		listener.onOriginalLineReduced(id);
		verify(qtyReservationService).shrinkToFitOpenQty(id);
	}

	@Test
	void onNewLineSaved_invalidatesInvoiceCandidate()
	{
		final I_C_OrderLine line = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		InterfaceWrapperHelper.save(line);
		listener.onNewLineSaved(line);
		verify(invoiceCandidateHandlerBL).invalidateCandidatesFor(line);
		// CreateMissingShipmentSchedulesWorkpackageProcessor.scheduleIfNotPostponed is a static call
		// that requires async workpackage infrastructure not available in a plain unit-test context.
		// The WP scheduling behaviour is verified at integration level (cucumber on CI).
	}
}
