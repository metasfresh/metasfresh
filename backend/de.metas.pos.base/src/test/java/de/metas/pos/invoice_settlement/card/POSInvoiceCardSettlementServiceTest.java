package de.metas.pos.invoice_settlement.card;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.i18n.AdMessageKey;
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
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
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

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
			return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Issue.class)
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
