package de.metas.pos;

import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyRepository;
import de.metas.payment.sumup.SumUpService;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * The background sweep imports the SumUp transaction of a card payment with an unknown result (case (a) of
 * {@link POSCardPaymentSweeper}) only for 24 h after its checkout: a checkout whose card was never presented, which SumUp
 * keeps answering with "not found", is not looked up for ever. The payment keeps its state; the till's check still asks SumUp.
 */
class POSOrdersServiceCardPaymentImportCutoffTest
{
	private POSOrdersRepository ordersRepository;
	private POSOrdersService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		ordersRepository = spy(new POSOrdersRepository(new POSOrderEventDispatcher(Optional.empty())));
		doReturn(Stream.empty()).when(ordersRepository).streamCardPaymentsToImport(any());
		service = new POSOrdersService(mock(POSTerminalService.class), ordersRepository, mock(CurrencyRepository.class), mock(POSOrderProcessingServices.class), mock(SumUpService.class), new POSCardReleaseWait(), new POSCardReaderCheckoutTerminator(mock(POSTerminalService.class), mock(SumUpService.class)), duration -> {});
	}

	@AfterEach
	void afterEach()
	{
		SystemTime.resetTimeSource();
	}

	@Test
	void cardPaymentsToImport_onlyThoseWhoseCheckoutStartedInTheLast24h()
	{
		SystemTime.setFixedTimeSource("2026-11-17T12:00:00Z");

		service.getCardPaymentsToImport();

		verify(ordersRepository).streamCardPaymentsToImport(eq(Instant.parse("2026-11-16T12:00:00Z")));
	}
}
