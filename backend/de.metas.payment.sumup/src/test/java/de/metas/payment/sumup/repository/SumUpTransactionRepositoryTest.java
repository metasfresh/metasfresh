package de.metas.payment.sumup.repository;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpEventsDispatcher;
import de.metas.payment.sumup.SumUpMerchantCode;
import de.metas.payment.sumup.SumUpPOSRef;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.payment.sumup.SumUpTransaction.LastSync;
import de.metas.payment.sumup.SumUpTransaction.LastSyncStatus;
import de.metas.payment.sumup.SumUpTransactionExternalId;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SumUpTransactionRepositoryTest
{
	private SumUpTransactionRepository repository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new SumUpTransactionRepository(Mockito.mock(SumUpEventsDispatcher.class));
	}

	private static SumUpTransaction newTrx(final SumUpPOSRef posRef)
	{
		return SumUpTransaction.builder()
				.configId(SumUpConfigId.ofRepoId(1))
				.externalId(SumUpTransactionExternalId.ofString(UUID.randomUUID().toString()))
				.clientTransactionId(SumUpClientTransactionId.ofString(UUID.randomUUID().toString()))
				.merchantCode(SumUpMerchantCode.ofString("MC"))
				.timestamp(Instant.parse("2026-01-01T10:00:00Z"))
				.status(SumUpTransactionStatus.PENDING)
				.amount(Amount.of("10", CurrencyCode.EUR))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(Env.getClientId(), Env.getOrgId()))
				.posRef(posRef)
				.build();
	}

	@Test
	void posRef_withInvoiceCardSettlementId_survivesSaveAndReload()
	{
		final SumUpTransaction trx = newTrx(SumUpPOSRef.builder().posInvoiceCardSettlementId(123).build());
		repository.saveNew(trx);

		final SumUpTransaction reloaded = repository.getById(trx.getExternalId());
		assertThat(reloaded.getPosRef()).isNotNull();
		assertThat(reloaded.getPosRef().getPosInvoiceCardSettlementId()).isEqualTo(123);
		assertThat(reloaded.getPosRef().getPosOrderId()).isEqualTo(-1);
		assertThat(reloaded.getPosRef().getPosPaymentId()).isEqualTo(-1);
	}

	@Test
	void findIdByPOSRef_findsTheTransactionOfTheSettlement()
	{
		repository.saveNew(newTrx(SumUpPOSRef.builder().posInvoiceCardSettlementId(111).build()));
		repository.saveNew(newTrx(SumUpPOSRef.builder().posInvoiceCardSettlementId(222).build()));

		assertThat(repository.findIdByPOSRef(SumUpPOSRef.builder().posInvoiceCardSettlementId(222).build())).isNotNull();
		assertThat(repository.findIdByPOSRef(SumUpPOSRef.builder().posInvoiceCardSettlementId(333).build())).isNull();
	}

	@Test
	void findIdByPOSRef_emptyPosRef_isRejected()
	{
		repository.saveNew(newTrx(SumUpPOSRef.builder().posInvoiceCardSettlementId(111).build()));

		assertThatThrownBy(() -> repository.findIdByPOSRef(SumUpPOSRef.builder().build()))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void query_byInvoiceCardSettlementId_findsOnlyThatTransaction()
	{
		final SumUpTransaction trx1 = newTrx(SumUpPOSRef.builder().posInvoiceCardSettlementId(111).build());
		final SumUpTransaction trx2 = newTrx(SumUpPOSRef.builder().posInvoiceCardSettlementId(222).build());
		final SumUpTransaction trx3 = newTrx(SumUpPOSRef.builder().posOrderId(5).posPaymentId(6).build());
		repository.saveNew(trx1);
		repository.saveNew(trx2);
		repository.saveNew(trx3);

		final List<SumUpTransaction> result = repository.list(SumUpTransactionQuery.builder()
				.posRef(SumUpPOSRef.builder().posInvoiceCardSettlementId(111).build())
				.build());

		assertThat(result).extracting(SumUpTransaction::getExternalId).containsExactly(trx1.getExternalId());
	}

	/**
	 * Saves a PENDING transaction that metasfresh created at {@code 2026-11-16T10:00:00Z}, with the given last lookup.
	 *
	 * @param posPaymentId identifies the transaction in the assertions
	 * @param lastSync     the last lookup, null if it was never looked up
	 * @param json         SumUp's stored answer, null if SumUp never reported the transaction
	 */
	private SumUpTransaction newPendingTrxCreatedAt10(final int posPaymentId, @Nullable final LastSync lastSync, @Nullable final String json)
	{
		SystemTime.setFixedTimeSource("2026-11-16T10:00:00Z");
		final SumUpTransaction trx = newTrx(SumUpPOSRef.builder().posOrderId(5).posPaymentId(posPaymentId).build());
		repository.saveNew(trx);
		final SumUpTransaction withLastSync = trx.toBuilder().lastSync(lastSync).json(json).build();
		repository.save(withLastSync);
		return withLastSync;
	}

	private static LastSync lastSync(final LastSyncStatus status, final String timestamp)
	{
		return LastSync.builder().status(status).timestamp(Instant.parse(timestamp)).build();
	}

	private List<Integer> listPaymentIdsOfPendingNotConfirmedNotFoundAt24h()
	{
		return repository.list(SumUpTransactionQuery.builder()
						.status(SumUpTransactionStatus.PENDING)
						.excludeNotFoundConfirmedAtAge(Duration.ofHours(24))
						.build())
				.stream()
				.map(trx -> Objects.requireNonNull(trx.getPosRef()).getPosPaymentId())
				.collect(Collectors.toList());
	}

	/**
	 * Created at 10:00 on the 16th, so the 24 h mark is 10:00 on the 17th. Left out is only a transaction that SumUp never
	 * reported and whose last lookup succeeded (SumUp's "not found") at or after the 24 h mark.
	 */
	@Test
	void query_excludeNotFoundConfirmedAtAge_leavesOutOnlyThoseSumUpConfirmedNotFoundAtOrAfterThatAge()
	{
		try
		{
			newPendingTrxCreatedAt10(1, lastSync(LastSyncStatus.OK, "2026-11-17T11:00:00Z"), null); // not found, 25 h after creation
			newPendingTrxCreatedAt10(2, lastSync(LastSyncStatus.Error, "2026-11-17T11:00:00Z"), null); // lookup failed after 24 h
			newPendingTrxCreatedAt10(3, lastSync(LastSyncStatus.OK, "2026-11-16T11:00:00Z"), null); // not found only before the 24 h mark
			newPendingTrxCreatedAt10(4, null, null); // never looked up (e.g. the server was down)
			newPendingTrxCreatedAt10(5, lastSync(LastSyncStatus.OK, "2026-11-17T11:00:00Z"), "{\"status\":\"PENDING\"}"); // SumUp knows it, still PENDING
			newPendingTrxCreatedAt10(6, lastSync(LastSyncStatus.OK, "2026-11-16T10:30:00Z"), null); // young: not found 30 min after creation

			assertThat(listPaymentIdsOfPendingNotConfirmedNotFoundAt24h()).containsExactly(2, 3, 4, 5, 6);
		}
		finally
		{
			SystemTime.resetTimeSource();
		}
	}

	/**
	 * A "not found" exactly 24 h after creation is final; one second before it is not.
	 */
	@Test
	void query_excludeNotFoundConfirmedAtAge_boundary()
	{
		try
		{
			newPendingTrxCreatedAt10(1, lastSync(LastSyncStatus.OK, "2026-11-17T10:00:00Z"), null); // exactly at 24 h
			newPendingTrxCreatedAt10(2, lastSync(LastSyncStatus.OK, "2026-11-17T09:59:59Z"), null); // 1 s before

			assertThat(listPaymentIdsOfPendingNotConfirmedNotFoundAt24h()).containsExactly(2);
		}
		finally
		{
			SystemTime.resetTimeSource();
		}
	}

	/**
	 * The poller's query is limited (default 1000) and keeps transactions whose lookups keep failing, whatever their age: the
	 * newest transactions come first, so a new payment is always polled even if more old ones than the limit are waiting.
	 */
	/**
	 * Created at 10:00 on the 16th; see {@link de.metas.payment.sumup.SumUpPollSchedule}.
	 */
	@Test
	void query_pollDueAt_onlyTheTransactionsDueByThePollSchedule()
	{
		try
		{
			newPendingTrxCreatedAt10(1, null, null); // never looked up
			newPendingTrxCreatedAt10(2, lastSync(LastSyncStatus.OK, "2026-11-16T10:29:00Z"), null); // 1 min ago, 30 min old
			newPendingTrxCreatedAt10(3, lastSync(LastSyncStatus.OK, "2026-11-16T10:29:01Z"), null); // 59 s ago, 30 min old
			newPendingTrxCreatedAt10(4, lastSync(LastSyncStatus.Error, "2026-11-16T10:29:30Z"), null); // 30 s ago, failed
			newPendingTrxCreatedAt10(5, lastSync(LastSyncStatus.OK, "2026-11-16T11:00:00Z"), null); // for the slow phase

			// 30 min old: once a minute
			assertThat(listPaymentIdsDueAt("2026-11-16T10:30:00Z")).containsExactly(1, 2);
			// over 1 h old: once every 15 min
			assertThat(listPaymentIdsDueAt("2026-11-16T11:14:59Z")).containsExactly(1, 2, 3, 4);
			assertThat(listPaymentIdsDueAt("2026-11-16T11:15:00Z")).containsExactly(1, 2, 3, 4, 5);
		}
		finally
		{
			SystemTime.resetTimeSource();
		}
	}

	private List<Integer> listPaymentIdsDueAt(final String now)
	{
		return repository.list(SumUpTransactionQuery.builder()
						.status(SumUpTransactionStatus.PENDING)
						.pollDueAt(Instant.parse(now))
						.build())
				.stream()
				.map(trx -> Objects.requireNonNull(trx.getPosRef()).getPosPaymentId())
				.collect(Collectors.toList());
	}

	@Test
	void bulkUpdateByQuery_overTheLimit_updatesTheNewestTransactions()
	{
		final SumUpTransaction oldest = newTrx(SumUpPOSRef.builder().posOrderId(5).posPaymentId(1).build());
		final SumUpTransaction older = newTrx(SumUpPOSRef.builder().posOrderId(5).posPaymentId(2).build());
		final SumUpTransaction newest = newTrx(SumUpPOSRef.builder().posOrderId(5).posPaymentId(3).build());
		repository.saveNew(oldest);
		repository.saveNew(older);
		repository.saveNew(newest);

		final List<Integer> updatedPaymentIds = new ArrayList<>();
		repository.bulkUpdateByQuery(
				SumUpTransactionQuery.builder()
						.status(SumUpTransactionStatus.PENDING)
						.limit(QueryLimit.ofInt(2))
						.build(),
				false,
				trx -> {
					updatedPaymentIds.add(Objects.requireNonNull(trx.getPosRef()).getPosPaymentId());
					return trx;
				});

		assertThat(updatedPaymentIds).containsExactly(3, 2);
	}

	@Test
	void recordedAt_whenMetasfreshFirstSavedTheTransaction_alsoOnTheTransactionsOfItsEvents()
	{
		final SumUpEventsDispatcher eventsDispatcher = Mockito.mock(SumUpEventsDispatcher.class);
		final SumUpTransactionRepository repository = new SumUpTransactionRepository(eventsDispatcher);
		try
		{
			SystemTime.setFixedTimeSource("2026-11-10T10:00:00Z");
			final SumUpTransaction trx = newTrx(SumUpPOSRef.builder().posOrderId(5).posPaymentId(6).build());
			repository.saveNew(trx);

			// a later status change, e.g. SumUp's approval, does not change when metasfresh first saved it
			SystemTime.setFixedTimeSource("2026-11-10T10:05:00Z");
			repository.save(trx.toBuilder().status(SumUpTransactionStatus.SUCCESSFUL).build());

			final Instant recordedAt = Instant.parse("2026-11-10T10:00:00Z");
			assertThat(repository.getById(trx.getExternalId()).getRecordedAt()).isEqualTo(recordedAt);

			final ArgumentCaptor<SumUpTransaction> newTrx = ArgumentCaptor.forClass(SumUpTransaction.class);
			Mockito.verify(eventsDispatcher).fireNewTransaction(newTrx.capture());
			assertThat(newTrx.getValue().getRecordedAt()).as("transaction of the creation event").isEqualTo(recordedAt);

			final ArgumentCaptor<SumUpTransaction> changedTrx = ArgumentCaptor.forClass(SumUpTransaction.class);
			Mockito.verify(eventsDispatcher).fireStatusChangedIfNeeded(changedTrx.capture(), Mockito.any());
			assertThat(changedTrx.getValue().getRecordedAt()).as("transaction of the status event").isEqualTo(recordedAt);
		}
		finally
		{
			SystemTime.resetTimeSource();
		}
	}

	@Test
	void posRef_posOrderOnly_unchanged()
	{
		final SumUpTransaction trx = newTrx(SumUpPOSRef.builder().posOrderId(5).posPaymentId(6).build());
		repository.saveNew(trx);

		final SumUpTransaction reloaded = repository.getById(trx.getExternalId());
		assertThat(reloaded.getPosRef()).isEqualTo(SumUpPOSRef.builder().posOrderId(5).posPaymentId(6).build());
	}

	/**
	 * @return the transaction as a SumUp lookup reports it: successful, with or without a refund
	 */
	private static SumUpTransaction asLookedUp(final SumUpTransaction trx, final boolean isRefunded)
	{
		return trx.toBuilder()
				.status(SumUpTransactionStatus.SUCCESSFUL)
				.amountRefunded(Amount.of(isRefunded ? "10" : "0", CurrencyCode.EUR))
				.lastSync(LastSync.ok())
				.build();
	}

	@Test
	void updateById_refundStoredByAnotherLookupMeanwhile_isNotOverwrittenByTheStaleResult()
	{
		final SumUpTransaction trx = newTrx(SumUpPOSRef.builder().posInvoiceCardSettlementId(123).build()).toBuilder()
				.status(SumUpTransactionStatus.SUCCESSFUL)
				.build();
		repository.saveNew(trx);

		final AtomicInteger lookups = new AtomicInteger();
		repository.updateById(trx.getClientTransactionId(), trxAsRead -> {
			if (lookups.incrementAndGet() == 1)
			{
				// SumUp answered "not refunded"; while this lookup was running, the charge was refunded and another
				// lookup (e.g. the SumUp callback) stored the refund
				repository.save(asLookedUp(trxAsRead, true));
				return asLookedUp(trxAsRead, false);
			}
			// asked again: SumUp reports the refund
			return asLookedUp(trxAsRead, true);
		});

		assertThat(repository.getById(trx.getExternalId()).isRefunded()).as("the refund stored meanwhile is kept").isTrue();
		assertThat(lookups).as("the lookup is repeated on the transaction as changed meanwhile").hasValue(2);
	}

	@Test
	void updateById_transactionChangedMeanwhileOnEveryAttempt_givesUpWithoutOverwritingIt()
	{
		final SumUpTransaction trx = newTrx(SumUpPOSRef.builder().posInvoiceCardSettlementId(123).build()).toBuilder()
				.status(SumUpTransactionStatus.SUCCESSFUL)
				.build();
		repository.saveNew(trx);

		final AtomicInteger lookups = new AtomicInteger();
		assertThatThrownBy(() -> repository.updateById(trx.getClientTransactionId(), trxAsRead -> {
			lookups.incrementAndGet();
			repository.save(trxAsRead.toBuilder().json("changed meanwhile " + lookups.get()).build());
			return asLookedUp(trxAsRead, false);
		})).isInstanceOf(AdempiereException.class);

		assertThat(lookups).hasValue(3);
		assertThat(repository.getById(trx.getExternalId()).getJson()).isEqualTo("changed meanwhile 3");
	}
}
