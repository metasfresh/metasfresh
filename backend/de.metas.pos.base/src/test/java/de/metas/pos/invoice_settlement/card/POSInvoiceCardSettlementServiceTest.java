package de.metas.pos.invoice_settlement.card;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpMerchantCode;
import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.payment.sumup.SumUpTransactionExternalId;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.pos.POSTerminalId;
import de.metas.pos.POSTerminalService;
import de.metas.pos.invoice_settlement.POSInvoiceSettlementService;
import de.metas.pos.payment_gateway.POSPaymentProcessorService;
import de.metas.user.UserId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class POSInvoiceCardSettlementServiceTest
{
	private POSInvoiceCardSettlementRepository repository;
	private POSInvoiceCardSettlementBooking booking;
	private SumUpService sumUpService;
	private POSInvoiceCardSettlementService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new POSInvoiceCardSettlementRepository();
		booking = mock(POSInvoiceCardSettlementBooking.class);
		sumUpService = mock(SumUpService.class);
		service = new POSInvoiceCardSettlementService(
				repository,
				mock(POSInvoiceSettlementService.class),
				mock(POSTerminalService.class),
				mock(POSPaymentProcessorService.class),
				mock(MoneyService.class),
				booking,
				sumUpService);
	}

	@Test
	void check_refreshFailsBeforeAskingSumUp_returnsCheckFailedAndUnchangedStatus()
	{
		final POSInvoiceCardSettlementId id = repository.create(
						OrgId.ofRepoId(1),
						POSTerminalId.ofRepoId(1),
						InvoiceId.ofRepoId(100),
						BPartnerId.ofRepoId(2),
						UserId.ofRepoId(3),
						Money.of(new BigDecimal("16.07"), CurrencyId.ofRepoId(102)))
				.getId();
		when(booking.findSumUpTransaction(id)).thenReturn(Optional.of(pendingSumUpTransaction()));
		// e.g. the terminal's SumUp config was deactivated: the refresh fails before SumUp is called
		doThrow(new AdempiereException("No SumUp config found")).when(sumUpService).updateTransactionFromRemote(any());

		final POSInvoiceCardSettlementCheckResult result = service.check(id);

		assertThat(result.isCheckFailed()).isTrue();
		assertThat(result.getSettlement().getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.PENDING);
	}

	private static SumUpTransaction pendingSumUpTransaction()
	{
		return SumUpTransaction.builder()
				.configId(SumUpConfigId.ofRepoId(1))
				.externalId(SumUpTransactionExternalId.ofString("UNKNOWN-1"))
				.clientTransactionId(SumUpClientTransactionId.ofString("1"))
				.merchantCode(SumUpMerchantCode.ofString("M"))
				.timestamp(Instant.parse("2026-09-24T06:00:00Z"))
				.status(SumUpTransactionStatus.PENDING)
				.amount(Amount.of(new BigDecimal("16.07"), CurrencyCode.EUR))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, OrgId.ofRepoId(1)))
				.build();
	}
}
