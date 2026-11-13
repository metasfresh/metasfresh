package de.metas.pos.invoice_settlement.card;

import de.metas.payment.sumup.SumUpTransaction;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * Stubs the package-private lookups of a mocked {@link POSInvoiceCardSettlementBooking} for tests in other packages
 * (e.g. the back-office processes).
 */
@UtilityClass
public class POSInvoiceCardSettlementBookingStubs
{
	public static void stubSumUpTransaction(
			@NonNull final POSInvoiceCardSettlementBooking bookingMock,
			@NonNull final POSInvoiceCardSettlementId id,
			@NonNull final SumUpTransaction sumUpTrx)
	{
		when(bookingMock.findSumUpTransaction(id)).thenReturn(Optional.of(sumUpTrx));
	}
}
