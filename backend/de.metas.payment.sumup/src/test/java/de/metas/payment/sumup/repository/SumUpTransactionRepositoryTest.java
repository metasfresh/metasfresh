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
import de.metas.payment.sumup.SumUpTransactionExternalId;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.common.util.time.SystemTime;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

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
