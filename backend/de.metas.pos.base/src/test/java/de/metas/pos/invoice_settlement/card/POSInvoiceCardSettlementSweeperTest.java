package de.metas.pos.invoice_settlement.card;

import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.pos.POSTerminalId;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Issue;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.InOrder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

class POSInvoiceCardSettlementSweeperTest
{
	private POSInvoiceCardSettlementRepository repository;
	private POSInvoiceCardSettlementService service;
	private POSInvoiceCardSettlementSweeper sweeper;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new POSInvoiceCardSettlementRepository();
		service = mock(POSInvoiceCardSettlementService.class);
		sweeper = new POSInvoiceCardSettlementSweeper(repository, service);
	}

	private POSInvoiceCardSettlementId createPendingSettlement(final int invoiceRepoId)
	{
		return repository.create(
						OrgId.ofRepoId(1),
						POSTerminalId.ofRepoId(1),
						InvoiceId.ofRepoId(invoiceRepoId),
						BPartnerId.ofRepoId(2),
						UserId.ofRepoId(3),
						Money.of(new BigDecimal("16.07"), CurrencyId.ofRepoId(102)))
				.getId();
	}

	@Test
	void aFailingSettlementDoesNotStopTheNextOne()
	{
		final POSInvoiceCardSettlementId failing = createPendingSettlement(100);
		final POSInvoiceCardSettlementId next = createPendingSettlement(101);
		doThrow(new AdempiereException("simulated failure")).when(service).reconcileLocally(failing);

		sweeper.afterPollCycle();

		final InOrder inOrder = inOrder(service);
		inOrder.verify(service).reconcileLocally(failing);
		inOrder.verify(service).reconcileLocally(next);
	}

	@Test
	@Timeout(60)
	void aFailureWithACyclicCauseChainDoesNotStopTheNextOne()
	{
		final POSInvoiceCardSettlementId failing = createPendingSettlement(100);
		final POSInvoiceCardSettlementId next = createPendingSettlement(101);
		final RuntimeException failure = new RuntimeException("outer");
		final RuntimeException cause = new RuntimeException("inner", failure);
		failure.initCause(cause); // outer -> inner -> outer
		doThrow(failure).when(service).reconcileLocally(failing);

		sweeper.afterPollCycle();

		final InOrder inOrder = inOrder(service);
		inOrder.verify(service).reconcileLocally(failing);
		inOrder.verify(service).reconcileLocally(next);
		assertThat(countIssues(failing)).as("AD_Issues of the failing settlement").isEqualTo(1);
	}

	@Test
	void theSameFailureInEveryCycleIsRecordedOnce()
	{
		final POSInvoiceCardSettlementId failing = createPendingSettlement(100);
		doThrow(new AdempiereException("simulated failure")).when(service).reconcileLocally(failing);

		sweeper.afterPollCycle();
		sweeper.afterPollCycle();
		sweeper.afterPollCycle();

		assertThat(countIssues(failing)).isEqualTo(1);
	}

	@Test
	void aDifferentFailureIsRecordedAgain()
	{
		final POSInvoiceCardSettlementId failing = createPendingSettlement(100);
		doThrow(new AdempiereException("first failure"))
				.doThrow(new IllegalStateException("second failure"))
				.when(service).reconcileLocally(failing);

		sweeper.afterPollCycle();
		sweeper.afterPollCycle();

		assertThat(countIssues(failing)).isEqualTo(2);
	}

	@Test
	void theSameFailureWithAVaryingMessageIsRecordedOnce()
	{
		final POSInvoiceCardSettlementId failing = createPendingSettlement(100);
		doThrow(failureWithMessage("failure at 08:00:01"))
				.doThrow(failureWithMessage("failure at 08:00:03"))
				.when(service).reconcileLocally(failing);

		sweeper.afterPollCycle();
		sweeper.afterPollCycle();

		assertThat(countIssues(failing)).isEqualTo(1);
	}

	/**
	 * @return a failure created at always the same place, with the given message
	 */
	private static AdempiereException failureWithMessage(final String message)
	{
		return new AdempiereException(message);
	}

	@Test
	void theFailureIsRecordedInTheClientOfTheSettlement()
	{
		final ClientId tillClientId = ClientId.ofRepoId(1000000);
		final POSInvoiceCardSettlementId failing;
		try (final IAutoCloseable ignored = Env.switchContext(ctxOfClient(tillClientId)))
		{
			failing = createPendingSettlement(100);
		}
		doThrow(new AdempiereException("simulated failure")).when(service).reconcileLocally(failing);

		// the poller thread has no login context
		try (final IAutoCloseable ignored = Env.switchContext(ctxOfClient(ClientId.SYSTEM)))
		{
			sweeper.afterPollCycle();
		}

		final List<I_AD_Issue> issues = queryIssues(failing).list();
		assertThat(issues).hasSize(1);
		assertThat(issues.get(0).getAD_Client_ID()).as("AD_Client_ID of the AD_Issue").isEqualTo(tillClientId.getRepoId());
	}

	private static Properties ctxOfClient(final ClientId clientId)
	{
		final Properties ctx = Env.newTemporaryCtx();
		Env.setClientId(ctx, clientId);
		return ctx;
	}

	@Test
	void aFailureAfterASuccessfulCycleIsRecordedAgain()
	{
		final POSInvoiceCardSettlementId failing = createPendingSettlement(100);
		doThrow(new AdempiereException("simulated failure"))
				.doNothing()
				.doThrow(new AdempiereException("simulated failure"))
				.when(service).reconcileLocally(failing);

		sweeper.afterPollCycle();
		sweeper.afterPollCycle();
		sweeper.afterPollCycle();

		assertThat(countIssues(failing)).isEqualTo(2);
	}

	private static int countIssues(final POSInvoiceCardSettlementId id)
	{
		return queryIssues(id).count();
	}

	private static IQuery<I_AD_Issue> queryIssues(final POSInvoiceCardSettlementId id)
	{
		final TableRecordReference recordRef = id.toRecordRef();
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Issue.class)
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
				.create();
	}
}
