package de.metas.pos.invoice_settlement.card;

import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.payment.sumup.repository.model.I_SUMUP_Transaction;
import de.metas.pos.POSTerminalId;
import de.metas.pos.repository.model.I_C_POS_InvoiceCardSettlement;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class POSInvoiceCardSettlementRepositoryTest
{
	private static final OrgId ORG_ID = OrgId.ofRepoId(1);
	private static final POSTerminalId TERMINAL_ID = POSTerminalId.ofRepoId(1);
	private static final BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(2);
	private static final UserId CASHIER_ID = UserId.ofRepoId(3);
	private static final CurrencyId CURRENCY_ID = CurrencyId.ofRepoId(102);

	private ITrxManager trxManager;
	private POSInvoiceCardSettlementRepository repository;
	private int nextInvoiceRepoId = 100;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		trxManager = Services.get(ITrxManager.class);
		repository = new POSInvoiceCardSettlementRepository();
	}

	private POSInvoiceCardSettlement createSettlement(final InvoiceId invoiceId)
	{
		return repository.create(
				ORG_ID, TERMINAL_ID, invoiceId, BPARTNER_ID, CASHIER_ID,
				Money.of(new BigDecimal("12.50"), CURRENCY_ID));
	}

	private InvoiceId newInvoiceId() {return InvoiceId.ofRepoId(nextInvoiceRepoId++);}

	private POSInvoiceCardSettlement createWith(final POSInvoiceCardSettlementStatus status, final boolean isAbandoned)
	{
		final POSInvoiceCardSettlement settlement = createSettlement(newInvoiceId());
		return repository.save(settlement.toBuilder().status(status).isAbandoned(isAbandoned).build());
	}

	private void linkSumUpTransaction(final POSInvoiceCardSettlement settlement, final SumUpTransactionStatus status)
	{
		final I_SUMUP_Transaction tx = InterfaceWrapperHelper.newInstance(I_SUMUP_Transaction.class);
		tx.setC_POS_InvoiceCardSettlement_ID(settlement.getId().getRepoId());
		tx.setStatus(status.getCode());
		tx.setRefundAmt(BigDecimal.ZERO);
		InterfaceWrapperHelper.save(tx);
	}

	@Test
	void create_thenGetById_roundTrips()
	{
		final InvoiceId invoiceId = newInvoiceId();
		final POSInvoiceCardSettlement created = createSettlement(invoiceId);

		final POSInvoiceCardSettlement loaded = repository.getById(created.getId());

		assertThat(loaded.getPosTerminalId()).isEqualTo(TERMINAL_ID);
		assertThat(loaded.getInvoiceId()).isEqualTo(invoiceId);
		assertThat(loaded.getBpartnerId()).isEqualTo(BPARTNER_ID);
		assertThat(loaded.getCashierId()).isEqualTo(CASHIER_ID);
		assertThat(loaded.getAmount()).isEqualTo(Money.of(new BigDecimal("12.50"), CURRENCY_ID));
		assertThat(loaded.getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.PENDING);
		assertThat(loaded.isAbandoned()).isFalse();
		assertThat(loaded.getBookingAttempts()).isZero();
		assertThat(loaded.getPaymentId()).isNull();
		assertThat(loaded.getSumUpTransactionId()).isNull();
		assertThat(trxManager.callInNewTrx(() -> repository.getByIdForUpdate(created.getId()))).isEqualTo(loaded);
	}

	@Test
	void changeStatus_onlyWhenExpected_returnsFalseOtherwise()
	{
		final POSInvoiceCardSettlement settlement = createSettlement(newInvoiceId());
		final POSInvoiceCardSettlementId id = settlement.getId();

		assertThat(changeStatusInTrx(id, POSInvoiceCardSettlementStatus.UNKNOWN, POSInvoiceCardSettlementStatus.APPROVED)).isFalse();
		assertThat(repository.getById(id).getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.PENDING);

		assertThat(changeStatusInTrx(id, POSInvoiceCardSettlementStatus.PENDING, POSInvoiceCardSettlementStatus.APPROVED)).isTrue();
		assertThat(repository.getById(id).getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.APPROVED);
	}

	@Test
	void changeStatusAndGetByIdForUpdate_outsideTransaction_throw()
	{
		final POSInvoiceCardSettlementId id = createSettlement(newInvoiceId()).getId();

		assertThatThrownBy(() -> repository.changeStatus(id, POSInvoiceCardSettlementStatus.PENDING, POSInvoiceCardSettlementStatus.APPROVED))
				.hasMessageContaining("ThreadInherited transaction");
		assertThatThrownBy(() -> repository.getByIdForUpdate(id))
				.hasMessageContaining("ThreadInherited transaction");
		assertThat(repository.getById(id).getStatus()).isEqualTo(POSInvoiceCardSettlementStatus.PENDING);
	}

	private boolean changeStatusInTrx(final POSInvoiceCardSettlementId id, final POSInvoiceCardSettlementStatus expected, final POSInvoiceCardSettlementStatus newStatus)
	{
		return trxManager.callInNewTrx(() -> repository.changeStatus(id, expected, newStatus));
	}

	static Stream<Arguments> statusAndAbandonedFlag()
	{
		return Arrays.stream(POSInvoiceCardSettlementStatus.values())
				.flatMap(status -> Stream.of(Arguments.of(status, false), Arguments.of(status, true)));
	}

	@ParameterizedTest
	@MethodSource("statusAndAbandonedFlag")
	void isBlocking_matchesIndexPredicate(final POSInvoiceCardSettlementStatus status, final boolean isAbandoned)
	{
		// same predicate as the blocking index / AD_Index_Table.WhereClause: Status IN ('PE','UN','AP','BE') AND IsAbandoned='N'
		final boolean expected = Arrays.asList("PE", "UN", "AP", "BE").contains(status.getCode()) && !isAbandoned;

		final POSInvoiceCardSettlement settlement = createWith(status, isAbandoned);

		assertThat(settlement.isBlocking()).isEqualTo(expected);
		assertThat(repository.getById(settlement.getId()).isBlocking()).isEqualTo(expected);
		assertThat(repository.findBlockingByInvoiceId(settlement.getInvoiceId()).isPresent()).isEqualTo(expected);
	}

	@Test
	void findBlockingByInvoiceId_ignoresAbandoned()
	{
		final InvoiceId invoiceId = newInvoiceId();
		final POSInvoiceCardSettlement abandoned = repository.save(createSettlement(invoiceId).toBuilder().isAbandoned(true).build());
		assertThat(repository.findBlockingByInvoiceId(invoiceId)).isEmpty();

		final POSInvoiceCardSettlement active = createSettlement(invoiceId);
		final Optional<POSInvoiceCardSettlement> found = repository.findBlockingByInvoiceId(invoiceId);
		assertThat(found).isPresent();
		assertThat(found.get().getId()).isEqualTo(active.getId()).isNotEqualTo(abandoned.getId());
	}

	@Test
	void streamSweepCandidateIds_matchesDecision9()
	{
		final POSInvoiceCardSettlement pe = createWith(POSInvoiceCardSettlementStatus.PENDING, false);
		final POSInvoiceCardSettlement un = createWith(POSInvoiceCardSettlementStatus.UNKNOWN, false);
		final POSInvoiceCardSettlement ap = createWith(POSInvoiceCardSettlementStatus.APPROVED, false);

		final POSInvoiceCardSettlement abPending = createWith(POSInvoiceCardSettlementStatus.ABANDONED, false);
		linkSumUpTransaction(abPending, SumUpTransactionStatus.PENDING);
		final POSInvoiceCardSettlement abSuccessful = createWith(POSInvoiceCardSettlementStatus.ABANDONED, false);
		linkSumUpTransaction(abSuccessful, SumUpTransactionStatus.SUCCESSFUL);
		final POSInvoiceCardSettlement abFailed = createWith(POSInvoiceCardSettlementStatus.ABANDONED, false);
		linkSumUpTransaction(abFailed, SumUpTransactionStatus.FAILED);
		final POSInvoiceCardSettlement abNoTx = createWith(POSInvoiceCardSettlementStatus.ABANDONED, false);

		final POSInvoiceCardSettlement su = createWith(POSInvoiceCardSettlementStatus.SUCCESSFUL, false);
		final POSInvoiceCardSettlement cr = createWith(POSInvoiceCardSettlementStatus.CREDIT_REFUND_PENDING, false);
		final POSInvoiceCardSettlement rf = createWith(POSInvoiceCardSettlementStatus.REFUNDED, false);
		final POSInvoiceCardSettlement fa = createWith(POSInvoiceCardSettlementStatus.FAILED, false);
		final POSInvoiceCardSettlement ca = createWith(POSInvoiceCardSettlementStatus.CANCELLED, false);
		final POSInvoiceCardSettlement be = createWith(POSInvoiceCardSettlementStatus.BOOKING_ERROR, false);
		// a SUCCESSFUL SumUp tx linked to a non-AB settlement must not matter
		linkSumUpTransaction(su, SumUpTransactionStatus.SUCCESSFUL);

		final List<POSInvoiceCardSettlementId> streamed;
		try (Stream<POSInvoiceCardSettlementId> stream = repository.streamSweepCandidateIds())
		{
			streamed = stream.collect(Collectors.toList());
		}

		assertThat(streamed).containsExactlyInAnyOrder(
				pe.getId(), un.getId(), ap.getId(), abPending.getId(), abSuccessful.getId());
		assertThat(streamed).doesNotContain(abFailed.getId(), abNoTx.getId(), su.getId(), cr.getId(), rf.getId(), fa.getId(), ca.getId(), be.getId());
	}
}
