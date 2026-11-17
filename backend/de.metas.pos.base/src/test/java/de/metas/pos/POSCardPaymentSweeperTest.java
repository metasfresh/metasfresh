package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.organization.ClientAndOrgId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Case (a) of {@link POSCardPaymentSweeper}, importing the SumUp transaction of a card payment with an unknown result, asks
 * SumUp on the same schedule as the SumUp poller ({@link de.metas.payment.sumup.SumUpPollSchedule}), counted from the
 * payment's checkout start: in every cycle during its first 5 min, then at most once a minute up to 1 h, then at most once
 * every 15 min. So a card that is never presented does not cost a SumUp call in every cycle for 24 h.
 */
class POSCardPaymentSweeperTest
{
	private static final POSOrderAndPaymentId PAYMENT = POSOrderAndPaymentId.of(POSOrderId.ofRepoId(1), POSPaymentId.ofRepoId(2));

	private POSOrdersService ordersService;
	private POSCardPaymentSweeper sweeper;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		ordersService = mock(POSOrdersService.class);
		when(ordersService.getCardPaymentsToImport())
				.thenReturn(ImmutableList.of(POSCardPaymentToImport.of(PAYMENT, Instant.parse("2026-11-16T10:00:00Z"))));
		when(ordersService.getOrphanCardPaymentIds()).thenReturn(ImmutableList.of());
		when(ordersService.getCardPaymentIdsToReconcile()).thenReturn(ImmutableList.of());
		when(ordersService.getClientAndOrgId(any())).thenReturn(ClientAndOrgId.ofClientAndOrg(Env.getClientId(), Env.getOrgId()));
		sweeper = new POSCardPaymentSweeper(ordersService);
	}

	@AfterEach
	void afterEach()
	{
		SystemTime.resetTimeSource();
	}

	/**
	 * Runs one sweep at the given time and checks whether it asked SumUp for the payment.
	 */
	private void sweepAt(final String time, final boolean expectLookup)
	{
		SystemTime.setFixedTimeSource(time);
		clearInvocations(ordersService);

		sweeper.afterPollCycle();

		verify(ordersService, expectLookup ? times(1) : never()).importCardPaymentSumUpTransaction(PAYMENT);
	}

	@Test
	void importLookups_everyCycleFor5Min_thenOnceAMinuteUpTo1h_thenEvery15Min()
	{
		sweepAt("2026-11-16T10:00:02Z", true);
		sweepAt("2026-11-16T10:00:04Z", true);
		sweepAt("2026-11-16T10:04:59Z", true);

		sweepAt("2026-11-16T10:05:00Z", false); // 1 s after the last lookup
		sweepAt("2026-11-16T10:05:58Z", false);
		sweepAt("2026-11-16T10:05:59Z", true);

		sweepAt("2026-11-16T11:00:00Z", true);
		sweepAt("2026-11-16T11:14:59Z", false);
		sweepAt("2026-11-16T11:15:00Z", true);
	}

	/**
	 * The last lookup is kept in memory only: after a restart (a new sweeper) a payment is looked up at once.
	 */
	@Test
	void afterARestart_thePaymentIsLookedUpAtOnce()
	{
		sweepAt("2026-11-16T11:00:00Z", true);
		sweeper = new POSCardPaymentSweeper(ordersService);
		sweepAt("2026-11-16T11:00:02Z", true);
	}
}
