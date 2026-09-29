package de.metas.pos;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.user.UserId;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class POSCashJournalTest
{
	private static final CurrencyId CURRENCY_ID = CurrencyId.ofRepoId(1);
	private static final UserId CASHIER_ID = UserId.ofRepoId(2);

	private static POSCashJournal newOpenJournal(final Money beginningBalance)
	{
		return POSCashJournal.builder()
				.id(POSCashJournalId.ofRepoId(1))
				.terminalId(POSTerminalId.ofRepoId(1))
				.dateTrx(Instant.now())
				.cashBeginningBalance(beginningBalance)
				.isClosed(false)
				.build();
	}

	@Test
	void cashOut_decreasesEndingBalance()
	{
		final POSCashJournal journal = newOpenJournal(Money.of(100, CURRENCY_ID));

		journal.addCashInOut(Money.of(-12, CURRENCY_ID), CASHIER_ID, "cash out");

		assertThat(journal.getCashEndingBalance()).isEqualTo(Money.of(88, CURRENCY_ID));
	}

	@Test
	void cashIn_increasesEndingBalance()
	{
		final POSCashJournal journal = newOpenJournal(Money.of(100, CURRENCY_ID));

		journal.addCashInOut(Money.of(25, CURRENCY_ID), CASHIER_ID, "cash in");

		assertThat(journal.getCashEndingBalance()).isEqualTo(Money.of(125, CURRENCY_ID));
	}

	@Test
	void closedJournal_rejectsCashInOut()
	{
		final POSCashJournal journal = POSCashJournal.builder()
				.id(POSCashJournalId.ofRepoId(1))
				.terminalId(POSTerminalId.ofRepoId(1))
				.dateTrx(Instant.now())
				.cashBeginningBalance(Money.of(100, CURRENCY_ID))
				.isClosed(true)
				.build();

		assertThatThrownBy(() -> journal.addCashInOut(Money.of(-12, CURRENCY_ID), CASHIER_ID, "cash out"))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void otherCurrency_rejected()
	{
		final POSCashJournal journal = newOpenJournal(Money.of(100, CURRENCY_ID));
		final CurrencyId otherCurrencyId = CurrencyId.ofRepoId(2);

		assertThatThrownBy(() -> journal.addCashInOut(Money.of(-12, otherCurrencyId), CASHIER_ID, "cash out"))
				.isInstanceOf(AdempiereException.class);
	}
}
