package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
public class POSCashJournal
{
	@NonNull @Getter private final POSCashJournalId id;

	@NonNull @Getter private final POSTerminalId terminalId;
	@NonNull @Getter private final Instant dateTrx;
	@NonNull @Getter private final Money cashBeginningBalance;
	@NonNull @Getter private Money cashEndingBalance;
	@NonNull @Getter private final CurrencyId currencyId;
	@Getter private boolean isClosed;

	@Nullable @Getter String openingNote;
	@Nullable @Getter String closingNote;

	@NonNull private final ArrayList<POSCashJournalLine> lines;

	@Builder
	private POSCashJournal(
			@NonNull final POSCashJournalId id,
			@NonNull final POSTerminalId terminalId,
			@NonNull final Instant dateTrx,
			@NonNull final Money cashBeginningBalance,
			@Nullable final Money cashEndingBalance,
			final boolean isClosed,
			@Nullable final String openingNote,
			@Nullable final String closingNote,
			@Nullable final List<POSCashJournalLine> lines)
	{
		this.id = id;
		this.terminalId = terminalId;
		this.dateTrx = dateTrx;
		this.cashBeginningBalance = cashBeginningBalance;
		this.cashEndingBalance = cashEndingBalance != null ? cashEndingBalance : cashBeginningBalance;
		this.currencyId = Money.getCommonCurrencyIdOfAll(this.cashBeginningBalance, this.cashEndingBalance);
		this.isClosed = isClosed;
		this.openingNote = openingNote;
		this.closingNote = closingNote;
		this.lines = lines != null ? new ArrayList<>(lines) : new ArrayList<>();
	}

	public ImmutableList<POSCashJournalLine> getLines() {return ImmutableList.copyOf(lines);}

	public void close(
			@NonNull final Money cashClosingBalance,
			@Nullable final String closingNote,
			@NonNull final UserId cashierId)
	{
		cashClosingBalance.assertCurrencyId(currencyId);

		if (isClosed)
		{
			throw new AdempiereException("Already closed");
		}

		final Money closingDifferenceAmt = cashClosingBalance.subtract(this.cashEndingBalance);
		if (!closingDifferenceAmt.isZero())
		{
			addClosingDifference(closingDifferenceAmt, cashierId, closingNote);
		}

		this.closingNote = closingNote;
		this.isClosed = true;
	}

	private void addClosingDifference(@NonNull final Money closingDifferenceAmt, @NonNull final UserId cashierId, @Nullable final String description)
	{
		add(POSCashJournalLine.builder()
				.type(POSCashJournalLineType.CASH_CLOSING_DIFFERENCE)
				.amount(closingDifferenceAmt)
				.cashierId(cashierId)
				.description(description)
				.build());
	}

	private void add(@NonNull final POSCashJournalLine line)
	{
		lines.add(line);
		updateTotals();
	}

	private void updateTotals()
	{
		Money cashEndingBalance = this.cashBeginningBalance;
		for (final POSCashJournalLine line : lines)
		{
			if (line.isCash())
			{
				cashEndingBalance = cashEndingBalance.add(line.getAmount());
			}
		}

		this.cashEndingBalance = cashEndingBalance;
	}

	public void addPayments(final POSOrder posOrder)
	{
		final POSOrderId posOrderId = posOrder.getLocalIdNotNull();
		final UserId cashierId = posOrder.getCashierId();
		posOrder.getPayments().forEach(posPayment -> addPayment(posPayment, posOrderId, cashierId));
	}

	public void addPayment(final POSPayment posPayment, POSOrderId posOrderId, UserId cashierId)
	{
		add(
				POSCashJournalLine.builder()
						.type(POSCashJournalLineType.ofPaymentMethod(posPayment.getPaymentMethod()))
						.amount(posPayment.getAmount())
						.cashierId(cashierId)
						.posOrderAndPaymentId(POSOrderAndPaymentId.of(posOrderId, posPayment.getLocalIdNotNull()))
						.build()
		);
	}
}
