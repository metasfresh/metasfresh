package de.metas.pos;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.user.UserId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class POSCashJournalLineTest
{
	private static final CurrencyId CURRENCY_ID = CurrencyId.ofRepoId(1);
	private static final UserId CASHIER_ID = UserId.ofRepoId(2);

	private static POSCashJournalLine.POSCashJournalLineBuilder cardLine()
	{
		return POSCashJournalLine.builder()
				.type(POSCashJournalLineType.CARD_PAYMENT)
				.amount(Money.of(10, CURRENCY_ID))
				.cashierId(CASHIER_ID);
	}

	@Test
	void paymentLine_requiresOrderPaymentOrSettlementRef()
	{
		final POSOrderAndPaymentId orderAndPaymentId = POSOrderAndPaymentId.of(POSOrderId.ofRepoId(1), POSPaymentId.ofRepoId(1));
		final POSInvoiceCardSettlementId settlementId = POSInvoiceCardSettlementId.ofRepoId(3);

		assertThatThrownBy(() -> cardLine().build()).isInstanceOf(IllegalArgumentException.class);
		assertThatCode(() -> cardLine().posOrderAndPaymentId(orderAndPaymentId).build()).doesNotThrowAnyException();
		assertThatCode(() -> cardLine().posInvoiceCardSettlementId(settlementId).build()).doesNotThrowAnyException();
		assertThatThrownBy(() -> cardLine().posOrderAndPaymentId(orderAndPaymentId).posInvoiceCardSettlementId(settlementId).build())
				.isInstanceOf(IllegalArgumentException.class);
	}
}
