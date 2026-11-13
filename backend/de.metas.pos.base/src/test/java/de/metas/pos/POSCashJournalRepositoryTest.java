package de.metas.pos;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.pos.repository.model.I_C_POS_JournalLine;
import de.metas.user.UserId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class POSCashJournalRepositoryTest
{
	private static final CurrencyId CURRENCY_ID = CurrencyId.ofRepoId(1);
	private static final UserId CASHIER_ID = UserId.ofRepoId(2);
	private static final POSInvoiceCardSettlementId SETTLEMENT_ID = POSInvoiceCardSettlementId.ofRepoId(7);

	private POSCashJournalRepository repository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new POSCashJournalRepository();
	}

	@Test
	void settlementReference_roundTrips_andOrderPaymentLineHasNone()
	{
		final POSCashJournal newJournal = repository.newJournal(POSCashJournalCreateRequest.builder()
				.orgId(OrgId.ofRepoId(1))
				.terminalId(POSTerminalId.ofRepoId(1))
				.dateTrx(Instant.now())
				.cashBeginningBalance(Money.of(100, CURRENCY_ID))
				.build());

		// an order-payment line, as written by the POS order checkout
		final I_C_POS_JournalLine orderPaymentRecord = InterfaceWrapperHelper.newInstance(I_C_POS_JournalLine.class);
		orderPaymentRecord.setC_POS_Journal_ID(newJournal.getId().getRepoId());
		orderPaymentRecord.setType(POSCashJournalLineType.CASH_PAYMENT.getCode());
		orderPaymentRecord.setAmount(new BigDecimal("15"));
		orderPaymentRecord.setCashier_ID(CASHIER_ID.getRepoId());
		orderPaymentRecord.setC_POS_Order_ID(3);
		orderPaymentRecord.setC_POS_Payment_ID(4);
		InterfaceWrapperHelper.save(orderPaymentRecord);

		repository.changeJournalById(newJournal.getId(),
				journal -> journal.addCardSettlementPayment(Money.of(40, CURRENCY_ID), CASHIER_ID, SETTLEMENT_ID, "invoice"));

		final POSCashJournal reloaded = repository.getById(newJournal.getId());
		final List<POSCashJournalLine> lines = reloaded.getLines();
		assertThat(lines).hasSize(2);
		assertThat(lines.get(0).getPosInvoiceCardSettlementId()).isNull();
		assertThat(lines.get(0).getPosOrderAndPaymentId()).isNotNull();
		assertThat(lines.get(1).getType()).isEqualTo(POSCashJournalLineType.CARD_PAYMENT);
		assertThat(lines.get(1).getPosInvoiceCardSettlementId()).isEqualTo(SETTLEMENT_ID);
		assertThat(lines.get(1).getPosOrderAndPaymentId()).isNull();
		assertThat(reloaded.getCashEndingBalance()).isEqualTo(Money.of(115, CURRENCY_ID));
	}
}
