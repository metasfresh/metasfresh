package de.metas.pos;

import de.metas.common.util.time.SystemTime;
import de.metas.payment.sumup.repository.model.I_SUMUP_Transaction;
import de.metas.pos.repository.model.I_C_POS_Order;
import de.metas.pos.repository.model.I_C_POS_Payment;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Issue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class POSOrdersRepositoryTest
{
	private static final POSTerminalId TERMINAL_1 = POSTerminalId.ofRepoId(1);
	private static final POSTerminalId TERMINAL_2 = POSTerminalId.ofRepoId(2);

	private POSOrdersRepository repository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new POSOrdersRepository(new POSOrderEventDispatcher(Optional.empty()));
	}

	private int newOrder(final POSTerminalId terminalId)
	{
		final I_C_POS_Order order = InterfaceWrapperHelper.newInstance(I_C_POS_Order.class);
		order.setC_POS_ID(terminalId.getRepoId());
		InterfaceWrapperHelper.saveRecord(order);
		return order.getC_POS_Order_ID();
	}

	private POSPaymentId newPayment(final int orderId, final POSPaymentMethod method, final String status)
	{
		return newPayment(0, orderId, method, status);
	}

	/** @param forcedId explicit C_POS_Payment_ID (to decouple insertion order from id order), or 0 for the next one */
	private POSPaymentId newPayment(final int forcedId, final int orderId, final POSPaymentMethod method, final String status)
	{
		final I_C_POS_Payment payment = InterfaceWrapperHelper.newInstance(I_C_POS_Payment.class);
		if (forcedId > 0)
		{
			payment.setC_POS_Payment_ID(forcedId);
		}
		payment.setC_POS_Order_ID(orderId);
		payment.setPOSPaymentMethod(method.getCode());
		payment.setPOSPaymentProcessingStatus(status);
		InterfaceWrapperHelper.saveRecord(payment);
		return POSPaymentId.ofRepoId(payment.getC_POS_Payment_ID());
	}

	private void newIssue(final String tableName, final int recordId, final String errorCode)
	{
		final I_AD_Issue issue = InterfaceWrapperHelper.newInstance(I_AD_Issue.class);
		issue.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(tableName));
		issue.setRecord_ID(recordId);
		issue.setErrorCode(errorCode);
		InterfaceWrapperHelper.saveRecord(issue);
	}

	@Test
	void listCardPaymentStatuses_isScopedToTerminal_cardOnly_andOldestFirst()
	{
		final int order1 = newOrder(TERMINAL_1);
		final int order1b = newOrder(TERMINAL_1);
		final int order2 = newOrder(TERMINAL_2);

		final POSPaymentId first = newPayment(order1, POSPaymentMethod.CARD, "UNKNOWN");
		newPayment(order1, POSPaymentMethod.CASH, "SUCCESSFUL");
		newPayment(order2, POSPaymentMethod.CARD, "SUCCESSFUL");
		final POSPaymentId second = newPayment(order1b, POSPaymentMethod.CARD, "SUCCESSFUL");

		final List<POSCardPaymentStatus> result = repository.listCardPaymentStatusesByTerminalId(TERMINAL_1);

		assertThat(result).containsExactly(
				new POSCardPaymentStatus(first, "UNKNOWN"),
				new POSCardPaymentStatus(second, "SUCCESSFUL"));
		assertThat(repository.listCardPaymentStatusesByTerminalId(POSTerminalId.ofRepoId(99))).isEmpty();
	}

	@Test
	void listCardPaymentStatuses_orderIsByPaymentId_notByInsertionOrder()
	{
		final int order = newOrder(TERMINAL_1);
		// inserted first, but with the HIGHER id
		final POSPaymentId newer = newPayment(900, order, POSPaymentMethod.CARD, "SUCCESSFUL");
		final POSPaymentId older = newPayment(800, order, POSPaymentMethod.CARD, "UNKNOWN");

		assertThat(repository.listCardPaymentStatusesByTerminalId(TERMINAL_1)).containsExactly(
				new POSCardPaymentStatus(older, "UNKNOWN"),
				new POSCardPaymentStatus(newer, "SUCCESSFUL"));
	}

	@Test
	void listIssueErrorCodes_matchesByPaymentRecord_only_andKeepsIssuesWithoutErrorCode()
	{
		final int order = newOrder(TERMINAL_1);
		final POSPaymentId payment = newPayment(order, POSPaymentMethod.CARD, "UNKNOWN");
		final POSPaymentId otherPayment = newPayment(order, POSPaymentMethod.CARD, "UNKNOWN");

		newIssue(I_C_POS_Payment.Table_Name, payment.getRepoId(), "CodeA");
		newIssue(I_C_POS_Payment.Table_Name, payment.getRepoId(), null);
		newIssue(I_C_POS_Payment.Table_Name, otherPayment.getRepoId(), "CodeOther");
		// same record id, different table: must not match
		newIssue(I_C_POS_Order.Table_Name, payment.getRepoId(), "CodeWrongTable");

		assertThat(repository.listIssueErrorCodesByPaymentId(payment)).containsExactly("CodeA", POSOrdersRepository.NO_ERROR_CODE);
		assertThat(repository.listIssueErrorCodesByPaymentId(otherPayment)).containsExactly("CodeOther");
	}

	/**
	 * A card payment with an unknown result naming a SumUp transaction that metasfresh does not have, saved at the given time
	 *
	 * @param checkoutStartedAt the start of its checkout, or null if none was recorded
	 */
	private POSPaymentId newUnknownCardPaymentToImport(final String savedAt, @Nullable final String checkoutStartedAt, final String clientTransactionId)
	{
		SystemTime.setFixedTimeSource(savedAt);
		final I_C_POS_Payment payment = InterfaceWrapperHelper.newInstance(I_C_POS_Payment.class);
		payment.setC_POS_Order_ID(newOrder(TERMINAL_1));
		payment.setPOSPaymentMethod(POSPaymentMethod.CARD.getCode());
		payment.setPOSPaymentProcessingStatus(POSPaymentProcessingStatus.UNKNOWN.getCode());
		payment.setSUMUP_ClientTransactionID(clientTransactionId);
		payment.setCardCheckoutStartedAt(checkoutStartedAt != null ? Timestamp.from(Instant.parse(checkoutStartedAt)) : null);
		InterfaceWrapperHelper.saveRecord(payment);
		return POSPaymentId.ofRepoId(payment.getC_POS_Payment_ID());
	}

	private List<POSPaymentId> streamCardPaymentsToImport(final String checkoutStartedAfter)
	{
		try (final Stream<POSOrderAndPaymentId> stream = repository.streamCardPaymentsToImport(Instant.parse(checkoutStartedAfter)))
		{
			return stream.map(POSOrderAndPaymentId::getPaymentId).collect(Collectors.toList());
		}
	}

	@Test
	void streamCardPaymentsToImport_onlyThoseWhoseCheckoutStartedAfterTheGivenTime()
	{
		try
		{
			final POSPaymentId startedBefore = newUnknownCardPaymentToImport("2026-11-16T09:00:00Z", "2026-11-16T09:00:00Z", "ctid-started-before");
			final POSPaymentId startedAfter = newUnknownCardPaymentToImport("2026-11-16T09:00:00Z", "2026-11-16T11:00:00Z", "ctid-started-after");
			// without a recorded checkout start: its creation counts
			final POSPaymentId createdBefore = newUnknownCardPaymentToImport("2026-11-16T09:00:00Z", null, "ctid-created-before");
			final POSPaymentId createdAfter = newUnknownCardPaymentToImport("2026-11-16T11:00:00Z", null, "ctid-created-after");

			assertThat(streamCardPaymentsToImport("2026-11-16T10:00:00Z"))
					.containsExactly(startedAfter, createdAfter)
					.doesNotContain(startedBefore, createdBefore);
		}
		finally
		{
			SystemTime.resetTimeSource();
		}
	}

	/**
	 * A payment whose SumUp lookups are failing (an outage recorded on the payment) is still looked up, however long ago its
	 * checkout started: only a lookup that SumUp answered ends the background lookups after the cutoff, so a charge SumUp
	 * approved during the outage is still imported.
	 */
	@Test
	void streamCardPaymentsToImport_alsoThoseCheckedOutBeforeTheGivenTime_whoseLookupsAreFailing()
	{
		try
		{
			final POSPaymentId failing = newUnknownCardPaymentToImport("2026-11-16T09:00:00Z", "2026-11-16T09:00:00Z", "ctid-failing");
			final I_C_POS_Payment failingRecord = InterfaceWrapperHelper.load(failing, I_C_POS_Payment.class);
			failingRecord.setCardLookupFailingSince(Timestamp.from(Instant.parse("2026-11-16T09:01:00Z")));
			InterfaceWrapperHelper.saveRecord(failingRecord);
			final POSPaymentId answered = newUnknownCardPaymentToImport("2026-11-16T09:00:00Z", "2026-11-16T09:00:00Z", "ctid-answered");

			assertThat(streamCardPaymentsToImport("2026-11-16T10:00:00Z"))
					.containsExactly(failing)
					.doesNotContain(answered);
		}
		finally
		{
			SystemTime.resetTimeSource();
		}
	}

	@Test
	void streamCardPaymentsToImport_notThoseWhoseSumUpTransactionMetasfreshHas()
	{
		try
		{
			final POSPaymentId withoutTransaction = newUnknownCardPaymentToImport("2026-11-16T11:00:00Z", "2026-11-16T11:00:00Z", "ctid-missing");
			newUnknownCardPaymentToImport("2026-11-16T11:00:00Z", "2026-11-16T11:00:00Z", "ctid-stored");
			final I_SUMUP_Transaction trx = InterfaceWrapperHelper.newInstance(I_SUMUP_Transaction.class);
			trx.setSUMUP_ClientTransactionId("ctid-stored");
			InterfaceWrapperHelper.saveRecord(trx);

			assertThat(streamCardPaymentsToImport("2026-11-16T10:00:00Z")).containsExactly(withoutTransaction);
		}
		finally
		{
			SystemTime.resetTimeSource();
		}
	}
}
