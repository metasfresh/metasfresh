package de.metas.pos.invoice_settlement.card.process;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
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
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlement;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementBooking;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementBookingStubs;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementRepository;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementService;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStatus;
import de.metas.pos.payment_gateway.POSPaymentProcessorService;
import de.metas.process.JavaProcess;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

/**
 * What back office is told after "retry booking", with the booking itself mocked (it leaves the settlement as the
 * retry prepared it): booked or ended → OK; still to be booked by the background sweep → "retried automatically";
 * failed again → an error.
 */
class C_POS_InvoiceCardSettlement_RetryBookingTest
{
	private static final String MSG_WillRetry = "@de.metas.pos.InvoiceCardSettlement.RetryBookingWillRetry@";

	private POSInvoiceCardSettlementRepository repository;
	private POSInvoiceCardSettlementBooking booking;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new POSInvoiceCardSettlementRepository();
		booking = mock(POSInvoiceCardSettlementBooking.class); // book() does nothing: the settlement stays as the retry left it
		SpringContextHolder.registerJUnitBean(new POSInvoiceCardSettlementService(
				repository,
				mock(POSInvoiceSettlementService.class),
				mock(POSTerminalService.class),
				mock(POSPaymentProcessorService.class),
				mock(MoneyService.class),
				booking,
				mock(SumUpService.class)));
	}

	private POSInvoiceCardSettlementId settlement(
			@NonNull final POSInvoiceCardSettlementStatus status,
			final boolean isAbandoned,
			@Nullable final PaymentId paymentId,
			@Nullable final String errorMsg)
	{
		final POSInvoiceCardSettlement created = repository.create(
				OrgId.ofRepoId(1),
				POSTerminalId.ofRepoId(1),
				InvoiceId.ofRepoId(100),
				BPartnerId.ofRepoId(2),
				UserId.ofRepoId(3),
				Money.of(new BigDecimal("16.07"), CurrencyId.ofRepoId(102)));
		return repository.save(created.toBuilder()
						.status(status)
						.isAbandoned(isAbandoned)
						.paymentId(paymentId)
						.errorMsg(errorMsg)
						.build())
				.getId();
	}

	private void sumUpReports(@NonNull final POSInvoiceCardSettlementId id, @Nullable final BigDecimal refundedAmount)
	{
		POSInvoiceCardSettlementBookingStubs.stubSumUpTransaction(booking, id, SumUpTransaction.builder()
				.configId(SumUpConfigId.ofRepoId(1))
				.externalId(SumUpTransactionExternalId.ofString("EXT-1"))
				.clientTransactionId(SumUpClientTransactionId.ofString("1"))
				.merchantCode(SumUpMerchantCode.ofString("M"))
				.timestamp(Instant.parse("2026-09-24T06:00:00Z"))
				.status(SumUpTransactionStatus.SUCCESSFUL)
				.amount(Amount.of(new BigDecimal("16.07"), CurrencyCode.EUR))
				.amountRefunded(refundedAmount != null ? Amount.of(refundedAmount, CurrencyCode.EUR) : null)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, OrgId.ofRepoId(1)))
				.build());
	}

	private static String retry(@NonNull final POSInvoiceCardSettlementId id)
	{
		return new C_POS_InvoiceCardSettlement_RetryBooking().retryBooking(id);
	}

	@Test
	void leftUnknown_paymentNotCreatedYet_isRetriedAutomatically()
	{
		final POSInvoiceCardSettlementId id = settlement(POSInvoiceCardSettlementStatus.BOOKING_ERROR, false, null, "booking failed");
		sumUpReports(id, null);

		assertThat(retry(id)).isEqualTo(MSG_WillRetry);
		assertThat(repository.getById(id).getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.UNKNOWN);
	}

	@Test
	void leftAbandoned_chargeApprovedAndNotRefunded_isRetriedAutomatically()
	{
		final POSInvoiceCardSettlementId id = settlement(POSInvoiceCardSettlementStatus.BOOKING_ERROR, true, null, "booking failed");
		sumUpReports(id, null);

		assertThat(retry(id)).isEqualTo(MSG_WillRetry);
		assertThat(repository.getById(id).getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.ABANDONED);
	}

	@Test
	void leftAbandoned_chargeRefunded_isOK()
	{
		final POSInvoiceCardSettlementId id = settlement(POSInvoiceCardSettlementStatus.BOOKING_ERROR, true, null, "booking failed");
		sumUpReports(id, new BigDecimal("16.07"));

		assertThat(retry(id)).isEqualTo(JavaProcess.MSG_OK);
	}

	@Test
	void leftApprovedWithoutError_isRetriedAutomatically()
	{
		final POSInvoiceCardSettlementId id = settlement(POSInvoiceCardSettlementStatus.BOOKING_ERROR, false, PaymentId.ofRepoId(7), "booking failed");

		assertThat(retry(id)).isEqualTo(MSG_WillRetry);
		assertThat(repository.getById(id).getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.APPROVED);
	}

	@Test
	void leftApprovedWithSumUpNotReachable_isRetriedAutomatically()
	{
		final POSInvoiceCardSettlementId id = settlement(POSInvoiceCardSettlementStatus.APPROVED, false, PaymentId.ofRepoId(7), POSInvoiceCardSettlementBooking.ERRORMSG_SumUpNotReachable);

		assertThat(SpringContextHolder.instance.getBean(POSInvoiceCardSettlementService.class).isAwaitingAutomaticBooking(repository.getById(id))).isTrue();
		assertThat(retry(id)).isEqualTo(MSG_WillRetry);
		assertThat(repository.getById(id).getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.APPROVED);
	}

	@Test
	void leftApprovedWithError_isNotCompleted()
	{
		final POSInvoiceCardSettlementId id = settlement(POSInvoiceCardSettlementStatus.APPROVED, false, PaymentId.ofRepoId(7), "no open cash journal");

		assertThatThrownBy(() -> retry(id))
				.isInstanceOfSatisfying(AdempiereException.class, ex -> assertThat(ex.isUserValidationError()).isTrue());
	}
}
