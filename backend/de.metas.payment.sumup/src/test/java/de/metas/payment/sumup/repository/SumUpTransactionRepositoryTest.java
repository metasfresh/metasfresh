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
import de.metas.payment.sumup.SumUpTransactionExternalId;
import de.metas.payment.sumup.SumUpTransactionStatus;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

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
	void posRef_posOrderOnly_unchanged()
	{
		final SumUpTransaction trx = newTrx(SumUpPOSRef.builder().posOrderId(5).posPaymentId(6).build());
		repository.saveNew(trx);

		final SumUpTransaction reloaded = repository.getById(trx.getExternalId());
		assertThat(reloaded.getPosRef()).isEqualTo(SumUpPOSRef.builder().posOrderId(5).posPaymentId(6).build());
	}
}
