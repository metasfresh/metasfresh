package de.metas.pos.invoice_settlement.card;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
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
import de.metas.pos.payment_gateway.POSPaymentProcessorService;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class POSInvoiceCardSettlementServiceTest
{
	private POSInvoiceCardSettlementRepository repository;
	private POSInvoiceCardSettlementBooking booking;
	private SumUpService sumUpService;
	private POSInvoiceCardSettlementService service;

	private IQueryBL queryBL;
	private IErrorManager errorManager;
	private IMsgBL msgBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		queryBL = Services.get(IQueryBL.class);
		errorManager = Services.get(IErrorManager.class);
		msgBL = Services.get(IMsgBL.class);
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

	@Nested
	class release
	{
		private static final String NOW = "2026-09-24T08:00:00+02:00";

		private final ClientAndOrgId settlementClientAndOrg = ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, OrgId.ofRepoId(1000000));
		private final ClientAndOrgId cashierLoginClientAndOrg = ClientAndOrgId.ofClientAndOrg(ClientId.SYSTEM, OrgId.ANY);

		@BeforeEach
		void beforeEach()
		{
			SystemTime.setFixedTimeSource(NOW);
		}

		@AfterEach
		void afterEach()
		{
			SystemTime.resetTimeSource();
		}

		/**
		 * Creates a PENDING settlement in {@link #settlementClientAndOrg}, created the given number of seconds ago, and
		 * switches the context to {@link #cashierLoginClientAndOrg} (another client and org).
		 */
		private POSInvoiceCardSettlement pendingSettlementCreatedSecondsAgo(final int secondsAgo)
		{
			final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
			invoice.setDocumentNo("INV-1");
			InterfaceWrapperHelper.saveRecord(invoice);

			Env.setClientAndOrgId(Env.getCtx(), settlementClientAndOrg);
			SystemTime.setFixedTimeSource(ZonedDateTime.parse(NOW).minusSeconds(secondsAgo));
			final POSInvoiceCardSettlement settlement = repository.create(
					settlementClientAndOrg.getOrgId(),
					POSTerminalId.ofRepoId(1),
					InvoiceId.ofRepoId(invoice.getC_Invoice_ID()),
					BPartnerId.ofRepoId(2),
					UserId.ofRepoId(3),
					Money.of(new BigDecimal("16.07"), CurrencyId.ofRepoId(102)));
			SystemTime.setFixedTimeSource(NOW);

			Env.setClientAndOrgId(Env.getCtx(), cashierLoginClientAndOrg);
			return settlement;
		}

		private POSInvoiceCardSettlementReleaseRequest confirmRequest(
				@NonNull final POSInvoiceCardSettlementId id,
				@Nullable final POSInvoiceCardSettlementStatus expectedStatus)
		{
			return POSInvoiceCardSettlementReleaseRequest.builder()
					.id(id)
					.cashierId(UserId.ofRepoId(3))
					.confirmNotCharged(true)
					.expectedStatus(expectedStatus)
					.build();
		}

		private List<I_AD_Issue> issuesOf(@NonNull final POSInvoiceCardSettlementId id)
		{
			final TableRecordReference recordRef = id.toRecordRef();
			return queryBL.createQueryBuilder(I_AD_Issue.class)
					.addEqualsFilter(I_AD_Issue.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
					.addEqualsFilter(I_AD_Issue.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
					.create()
					.list();
		}

		@Test
		void confirm_writesTheReleaseIssueInTheSettlementsClientAndOrg_notInTheCashiersLogin()
		{
			final POSInvoiceCardSettlement settlement = pendingSettlementCreatedSecondsAgo(200);

			final POSInvoiceCardSettlementReleaseResult result = service.release(confirmRequest(settlement.getId(), POSInvoiceCardSettlementStatus.PENDING));

			assertThat(result.getSettlement().getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.ABANDONED);
			assertThat(result.getSettlement().isAbandoned()).isTrue();
			final List<I_AD_Issue> issues = issuesOf(settlement.getId());
			assertThat(issues).hasSize(1);
			assertThat(ClientAndOrgId.ofClientAndOrg(issues.get(0).getAD_Client_ID(), issues.get(0).getAD_Org_ID()))
					.isEqualTo(settlementClientAndOrg);
			assertThat(Env.getClientAndOrgId()).as("the cashier's context is restored").isEqualTo(cashierLoginClientAndOrg);
		}

		@Test
		void confirm_beforeTheWait_isRefusedWithReleaseNotYetAllowed_andReleasesNothing()
		{
			final POSInvoiceCardSettlement settlement = pendingSettlementCreatedSecondsAgo(100);

			final POSInvoiceCardSettlementReleaseResult result = service.release(confirmRequest(settlement.getId(), POSInvoiceCardSettlementStatus.PENDING));

			assertThat(result.getRefusedMessageKey()).isEqualTo(AdMessageKey.of("de.metas.pos.InvoiceCardSettlement.ReleaseNotYetAllowed"));
			assertThat(result.isConfirmationRequired()).isFalse();
			assertThat(repository.getById(settlement.getId()).getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.PENDING);
			assertThat(repository.getById(settlement.getId()).isAbandoned()).isFalse();
			assertThat(issuesOf(settlement.getId())).isEmpty();
		}

		@Test
		void confirm_withoutExpectedStatus_isRefused_andReleasesNothing()
		{
			final POSInvoiceCardSettlement settlement = pendingSettlementCreatedSecondsAgo(200);

			assertThatThrownBy(() -> service.release(confirmRequest(settlement.getId(), null)))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("expected status");
			assertThat(repository.getById(settlement.getId()).getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.PENDING);
			assertThat(repository.getById(settlement.getId()).isAbandoned()).isFalse();
			assertThat(issuesOf(settlement.getId())).isEmpty();
		}
	}

	@Nested
	class backOffice
	{
		private final ClientAndOrgId settlementClientAndOrg = ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, OrgId.ofRepoId(1000000));
		private final ClientAndOrgId backOfficeLoginClientAndOrg = ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, OrgId.ANY);

		/**
		 * Creates a settlement in {@link #settlementClientAndOrg} with the given state, and switches the context to
		 * {@link #backOfficeLoginClientAndOrg} (another org).
		 */
		private POSInvoiceCardSettlement settlement(
				@NonNull final POSInvoiceCardSettlementStatus status,
				final boolean isAbandoned,
				@Nullable final PaymentId paymentId,
				final int bookingAttempts)
		{
			Env.setClientAndOrgId(Env.getCtx(), settlementClientAndOrg);
			final POSInvoiceCardSettlement created = repository.create(
					settlementClientAndOrg.getOrgId(),
					POSTerminalId.ofRepoId(1),
					InvoiceId.ofRepoId(100),
					BPartnerId.ofRepoId(2),
					UserId.ofRepoId(3),
					Money.of(new BigDecimal("16.07"), CurrencyId.ofRepoId(102)));
			final POSInvoiceCardSettlement settlement = repository.save(created.toBuilder()
					.status(status)
					.isAbandoned(isAbandoned)
					.paymentId(paymentId)
					.bookingAttempts(bookingAttempts)
					.errorMsg("booking failed")
					.build());
			Env.setClientAndOrgId(Env.getCtx(), backOfficeLoginClientAndOrg);
			return settlement;
		}

		@Test
		void retryBooking_bookingErrorWithPayment_isApprovedWithAFreshAttemptBudget_andBookedInTheSettlementsClientAndOrg()
		{
			final POSInvoiceCardSettlement settlement = settlement(POSInvoiceCardSettlementStatus.BOOKING_ERROR, false, PaymentId.ofRepoId(7), 5);
			final List<POSInvoiceCardSettlement> settlementsWhenBooked = new ArrayList<>();
			final List<ClientAndOrgId> contextsWhenBooked = new ArrayList<>();
			doAnswer(invocation -> {
				settlementsWhenBooked.add(repository.getById(settlement.getId()));
				contextsWhenBooked.add(Env.getClientAndOrgId());
				return null;
			}).when(booking).book(settlement.getId(), false);

			service.retryBooking(settlement.getId());

			assertThat(settlementsWhenBooked).hasSize(1);
			assertThat(settlementsWhenBooked.get(0).getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.APPROVED);
			assertThat(settlementsWhenBooked.get(0).getBookingAttempts()).isZero();
			assertThat(settlementsWhenBooked.get(0).getPaymentId()).isEqualTo(PaymentId.ofRepoId(7));
			assertThat(settlementsWhenBooked.get(0).getErrorMsg()).isNull();
			assertThat(contextsWhenBooked).containsExactly(settlementClientAndOrg);
			assertThat(Env.getClientAndOrgId()).as("back office's context is restored").isEqualTo(backOfficeLoginClientAndOrg);
		}

		@Test
		void retryBooking_bookingErrorWithoutPayment_isUnknownSoThatThePaymentIsCreatedFirst()
		{
			final POSInvoiceCardSettlement settlement = settlement(POSInvoiceCardSettlementStatus.BOOKING_ERROR, false, null, 1);
			final List<POSInvoiceCardSettlement> settlementsWhenBooked = new ArrayList<>();
			doAnswer(invocation -> settlementsWhenBooked.add(repository.getById(settlement.getId()))).when(booking).book(settlement.getId(), true);

			service.retryBooking(settlement.getId());

			assertThat(settlementsWhenBooked).hasSize(1);
			assertThat(settlementsWhenBooked.get(0).getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.UNKNOWN);
			assertThat(settlementsWhenBooked.get(0).isAbandoned()).isFalse();
			assertThat(settlementsWhenBooked.get(0).getBookingAttempts()).isZero();
		}

		@Test
		void retryBooking_abandonedBookingErrorWithoutPayment_isAbandonedSoThatThePaymentIsCreatedFirst()
		{
			final POSInvoiceCardSettlement settlement = settlement(POSInvoiceCardSettlementStatus.BOOKING_ERROR, true, null, 1);
			final List<POSInvoiceCardSettlement> settlementsWhenBooked = new ArrayList<>();
			doAnswer(invocation -> settlementsWhenBooked.add(repository.getById(settlement.getId()))).when(booking).book(settlement.getId(), true);

			service.retryBooking(settlement.getId());

			assertThat(settlementsWhenBooked).hasSize(1);
			assertThat(settlementsWhenBooked.get(0).getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.ABANDONED);
			assertThat(settlementsWhenBooked.get(0).isAbandoned()).isTrue();
		}

		@Test
		void retryBooking_bookingErrorWithoutPayment_refreshesTheSumUpTransactionBeforeBooking()
		{
			final POSInvoiceCardSettlement settlement = settlement(POSInvoiceCardSettlementStatus.BOOKING_ERROR, false, null, 1);
			when(booking.findSumUpTransaction(settlement.getId())).thenReturn(Optional.of(successfulSumUpTransaction()));

			service.retryBooking(settlement.getId());

			// the booking's step A sees a charge refunded meanwhile and books nothing
			final InOrder inOrder = inOrder(sumUpService, booking);
			inOrder.verify(sumUpService).updateTransactionFromRemote(successfulSumUpTransaction().getClientTransactionId());
			inOrder.verify(booking).book(settlement.getId(), true); // refreshed just before: step B does not ask SumUp again
		}

		@Test
		void retryBooking_sumUpLookupWritesItsIssueInTheSettlementsClientAndOrg_notInBackOfficesLogin()
		{
			final POSInvoiceCardSettlement settlement = settlement(POSInvoiceCardSettlementStatus.BOOKING_ERROR, false, null, 1);
			when(booking.findSumUpTransaction(settlement.getId())).thenReturn(Optional.of(successfulSumUpTransaction()));
			// as SumUpService does when the lookup fails: it records an AD_Issue in the current context
			doAnswer(invocation -> errorManager.createIssue(new AdempiereException("SumUp lookup failed").setRecord(settlement.getId().toRecordRef())))
					.when(sumUpService).updateTransactionFromRemote(any());

			service.retryBooking(settlement.getId());

			final List<I_AD_Issue> issues = issuesOf(settlement.getId());
			assertThat(issues).hasSize(1);
			assertThat(ClientAndOrgId.ofClientAndOrg(issues.get(0).getAD_Client_ID(), issues.get(0).getAD_Org_ID()))
					.isEqualTo(settlementClientAndOrg);
			assertThat(Env.getClientAndOrgId()).as("back office's context is restored").isEqualTo(backOfficeLoginClientAndOrg);
		}

		@Test
		void retryBooking_bookingErrorWithoutPayment_sumUpCannotBeAsked_isRefusedWithoutBooking()
		{
			final POSInvoiceCardSettlement settlement = settlement(POSInvoiceCardSettlementStatus.BOOKING_ERROR, false, null, 1);
			when(booking.findSumUpTransaction(settlement.getId())).thenReturn(Optional.of(successfulSumUpTransaction()));
			// e.g. the terminal's SumUp config was deactivated: the refresh fails before SumUp is called
			doThrow(new AdempiereException("No SumUp config found")).when(sumUpService).updateTransactionFromRemote(any());

			assertRetryRefusedBecauseSumUpCannotBeAsked(settlement);
		}

		@Test
		void retryBooking_bookingErrorWithoutPayment_sumUpLookupFails_isRefusedWithoutBooking()
		{
			final POSInvoiceCardSettlement settlement = settlement(POSInvoiceCardSettlementStatus.BOOKING_ERROR, false, null, 1);
			// a failed lookup (network, HTTP error) is caught by SumUpService and recorded on the transaction
			when(booking.findSumUpTransaction(settlement.getId())).thenReturn(Optional.of(successfulSumUpTransaction().toBuilder()
					.lastSync(SumUpTransaction.LastSync.error(AdIssueId.ofRepoId(1)))
					.build()));

			assertRetryRefusedBecauseSumUpCannotBeAsked(settlement);
		}

		private void assertRetryRefusedBecauseSumUpCannotBeAsked(@NonNull final POSInvoiceCardSettlement settlement)
		{
			assertThatThrownBy(() -> service.retryBooking(settlement.getId()))
					.isInstanceOfSatisfying(AdempiereException.class, ex -> {
						assertThat(ex.getErrorCode()).isEqualTo(errorCodeOf("de.metas.pos.InvoiceCardSettlement.RetryRefusedSumUpNotReachable"));
						assertThat(ex.isUserValidationError()).isTrue();
					});

			assertThat(repository.getById(settlement.getId()).getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.BOOKING_ERROR);
			assertThat(repository.getById(settlement.getId()).getBookingAttempts()).isEqualTo(1);
			verify(booking, never()).book(any(), anyBoolean());
		}

		private List<I_AD_Issue> issuesOf(@NonNull final POSInvoiceCardSettlementId id)
		{
			final TableRecordReference recordRef = id.toRecordRef();
			return queryBL.createQueryBuilder(I_AD_Issue.class)
					.addEqualsFilter(I_AD_Issue.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
					.addEqualsFilter(I_AD_Issue.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
					.create()
					.list();
		}

		@Test
		void retryBooking_approved_keepsItsErrorMsg_andGetsAFreshAttemptBudget()
		{
			final POSInvoiceCardSettlement settlement = settlement(POSInvoiceCardSettlementStatus.APPROVED, false, PaymentId.ofRepoId(7), 3);
			final List<POSInvoiceCardSettlement> settlementsWhenBooked = new ArrayList<>();
			doAnswer(invocation -> settlementsWhenBooked.add(repository.getById(settlement.getId()))).when(booking).book(settlement.getId(), false);

			service.retryBooking(settlement.getId());

			assertThat(settlementsWhenBooked).hasSize(1);
			assertThat(settlementsWhenBooked.get(0).getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.APPROVED);
			assertThat(settlementsWhenBooked.get(0).getBookingAttempts()).isZero();
			assertThat(settlementsWhenBooked.get(0).getErrorMsg()).isEqualTo("booking failed");
		}

		@Test
		void retryBooking_otherStatus_isRefused()
		{
			final POSInvoiceCardSettlement settlement = settlement(POSInvoiceCardSettlementStatus.SUCCESSFUL, false, PaymentId.ofRepoId(7), 0);

			assertThatThrownBy(() -> service.retryBooking(settlement.getId()))
					.isInstanceOfSatisfying(AdempiereException.class,
							ex -> assertThat(ex.getErrorCode()).isEqualTo(errorCodeOf("de.metas.pos.InvoiceCardSettlement.NotPossibleForStatus")));
			verify(booking, never()).book(any(), anyBoolean());
		}

		@Test
		void markRefunded_storesTheTrimmedNote()
		{
			final POSInvoiceCardSettlement settlement = settlement(POSInvoiceCardSettlementStatus.CREDIT_REFUND_PENDING, false, PaymentId.ofRepoId(7), 0);

			final POSInvoiceCardSettlement result = service.markRefunded(settlement.getId(), "  refunded in cash ");

			assertThat(result.getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.REFUNDED);
			assertThat(repository.getById(settlement.getId()).getNote()).isEqualTo("refunded in cash");
		}

		@Test
		void markRefunded_blankNote_isRefused()
		{
			final POSInvoiceCardSettlement settlement = settlement(POSInvoiceCardSettlementStatus.CREDIT_REFUND_PENDING, false, PaymentId.ofRepoId(7), 0);

			assertThatThrownBy(() -> service.markRefunded(settlement.getId(), " ")).isInstanceOf(FillMandatoryException.class);
			assertThat(repository.getById(settlement.getId()).getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.CREDIT_REFUND_PENDING);
		}

		@Test
		void markRefunded_otherStatus_isRefused()
		{
			final POSInvoiceCardSettlement settlement = settlement(POSInvoiceCardSettlementStatus.BOOKING_ERROR, false, PaymentId.ofRepoId(7), 0);

			assertThatThrownBy(() -> service.markRefunded(settlement.getId(), "refunded")).isInstanceOf(AdempiereException.class);
			assertThat(repository.getById(settlement.getId()).getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.BOOKING_ERROR);
		}

		@Test
		void markResolvedManually_blankNote_isRefused()
		{
			final POSInvoiceCardSettlement settlement = settlement(POSInvoiceCardSettlementStatus.BOOKING_ERROR, false, PaymentId.ofRepoId(7), 0);

			assertThatThrownBy(() -> service.markResolvedManually(settlement.getId(), null)).isInstanceOf(FillMandatoryException.class);
			assertThat(repository.getById(settlement.getId()).getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.BOOKING_ERROR);
		}
	}

	private static SumUpTransaction successfulSumUpTransaction()
	{
		return pendingSumUpTransaction().toBuilder().status(SumUpTransactionStatus.SUCCESSFUL).build();
	}

	private String errorCodeOf(@NonNull final String adMessage)
	{
		final AdMessageKey key = AdMessageKey.of(adMessage);
		return Optional.ofNullable(msgBL.getErrorCode(key)).orElseGet(key::toAD_Message);
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
