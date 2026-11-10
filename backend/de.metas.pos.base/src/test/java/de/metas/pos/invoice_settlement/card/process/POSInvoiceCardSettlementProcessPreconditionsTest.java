package de.metas.pos.invoice_settlement.card.process;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.payment.sumup.SumUpService;
import de.metas.pos.POSCardReleaseWait;
import de.metas.pos.POSTerminalId;
import de.metas.pos.POSTerminalService;
import de.metas.pos.invoice_settlement.POSInvoiceSettlementService;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlement;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementBooking;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementRepository;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementService;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStatus;
import de.metas.pos.payment_gateway.POSPaymentProcessorService;
import de.metas.pos.repository.model.I_C_POS_InvoiceCardSettlement;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.SelectionSize;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Each back-office process of the card settlement window is offered only for the statuses it applies to.
 */
class POSInvoiceCardSettlementProcessPreconditionsTest
{
	private POSInvoiceCardSettlementRepository repository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new POSInvoiceCardSettlementRepository();
		SpringContextHolder.registerJUnitBean(new POSInvoiceCardSettlementService(
				repository,
				mock(POSInvoiceSettlementService.class),
				mock(POSTerminalService.class),
				mock(POSPaymentProcessorService.class),
				mock(MoneyService.class),
				mock(POSInvoiceCardSettlementBooking.class),
				mock(SumUpService.class),
				new POSCardReleaseWait()));
	}

	private POSInvoiceCardSettlementId settlementWithStatus(@NonNull final POSInvoiceCardSettlementStatus status)
	{
		final POSInvoiceCardSettlement created = repository.create(
				OrgId.ofRepoId(1),
				POSTerminalId.ofRepoId(1),
				InvoiceId.ofRepoId(100),
				BPartnerId.ofRepoId(2),
				UserId.ofRepoId(3),
				Money.of(new BigDecimal("16.07"), CurrencyId.ofRepoId(102)));
		return repository.save(created.toBuilder().status(status).build()).getId();
	}

	private static ProcessPreconditionsResolution checkPreconditions(@NonNull final Supplier<IProcessPrecondition> process, @NonNull final POSInvoiceCardSettlementId id)
	{
		final IProcessPreconditionsContext context = mock(IProcessPreconditionsContext.class);
		when(context.getTableName()).thenReturn(I_C_POS_InvoiceCardSettlement.Table_Name);
		when(context.getSelectionSize()).thenReturn(SelectionSize.ofSize(1));
		when(context.isSingleSelection()).thenReturn(true);
		when(context.getSingleSelectedRecordId()).thenReturn(id.getRepoId());
		return process.get().checkPreconditionsApplicable(context);
	}

	/**
	 * Asserts that the process is offered for the settlement's status, or else hidden (an internal rejection, so back
	 * office does not see it for that row) with the reason {@code NotPossibleForStatus}.
	 */
	private void assertOfferedOnlyIf(
			final boolean expectedOffered,
			@NonNull final Supplier<IProcessPrecondition> process,
			@NonNull final POSInvoiceCardSettlementStatus status)
	{
		final ProcessPreconditionsResolution resolution = checkPreconditions(process, settlementWithStatus(status));
		if (expectedOffered)
		{
			assertThat(resolution.isAccepted()).as("offered for status %s", status).isTrue();
		}
		else
		{
			assertThat(resolution.isRejected()).as("rejected for status %s", status).isTrue();
			assertThat(resolution.isInternal()).as("hidden for status %s", status).isTrue();
			assertThat(resolution.getRejectReason().translate("en_US"))
					.isEqualTo(TranslatableStrings.adMessage(POSInvoiceCardSettlementService.MSG_NotPossibleForStatus).translate("en_US"));
		}
	}

	@ParameterizedTest
	@EnumSource(POSInvoiceCardSettlementStatus.class)
	void retryBooking_isOfferedForBookingErrorAndApprovedOnly(final POSInvoiceCardSettlementStatus status)
	{
		assertOfferedOnlyIf(
				ImmutableSet.of(POSInvoiceCardSettlementStatus.BOOKING_ERROR, POSInvoiceCardSettlementStatus.APPROVED).contains(status),
				C_POS_InvoiceCardSettlement_RetryBooking::new,
				status);
	}

	@ParameterizedTest
	@EnumSource(POSInvoiceCardSettlementStatus.class)
	void markResolvedManually_isOfferedForBookingErrorAndApprovedOnly(final POSInvoiceCardSettlementStatus status)
	{
		assertOfferedOnlyIf(
				ImmutableSet.of(POSInvoiceCardSettlementStatus.BOOKING_ERROR, POSInvoiceCardSettlementStatus.APPROVED).contains(status),
				C_POS_InvoiceCardSettlement_MarkResolvedManually::new,
				status);
	}

	@ParameterizedTest
	@EnumSource(POSInvoiceCardSettlementStatus.class)
	void markRefunded_isOfferedForCreditRefundPendingOnly(final POSInvoiceCardSettlementStatus status)
	{
		assertOfferedOnlyIf(status == POSInvoiceCardSettlementStatus.CREDIT_REFUND_PENDING, C_POS_InvoiceCardSettlement_MarkRefunded::new, status);
	}

	@Test
	void twoSelectedSettlements_noProcessIsOffered()
	{
		settlementWithStatus(POSInvoiceCardSettlementStatus.BOOKING_ERROR);
		settlementWithStatus(POSInvoiceCardSettlementStatus.CREDIT_REFUND_PENDING);
		final IProcessPreconditionsContext context = mock(IProcessPreconditionsContext.class);
		when(context.getTableName()).thenReturn(I_C_POS_InvoiceCardSettlement.Table_Name);
		when(context.getSelectionSize()).thenReturn(SelectionSize.ofSize(2));
		when(context.isSingleSelection()).thenReturn(false);

		assertThat(new C_POS_InvoiceCardSettlement_RetryBooking().checkPreconditionsApplicable(context).isRejected()).isTrue();
		assertThat(new C_POS_InvoiceCardSettlement_MarkResolvedManually().checkPreconditionsApplicable(context).isRejected()).isTrue();
		assertThat(new C_POS_InvoiceCardSettlement_MarkRefunded().checkPreconditionsApplicable(context).isRejected()).isTrue();
	}
}
