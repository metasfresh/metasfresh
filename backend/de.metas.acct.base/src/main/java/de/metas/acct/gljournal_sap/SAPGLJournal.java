package de.metas.acct.gljournal_sap;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.PostingType;
import de.metas.acct.gljournal_sap.service.SAPGLJournalCurrencyConverter;
import de.metas.money.Money;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.ArrayList;

@EqualsAndHashCode
@ToString
@Builder
public class SAPGLJournal
{
	@NonNull @Getter private final SAPGLJournalId id;
	@NonNull private final SAPGLJournalCurrencyConversionCtx conversionCtx;

	@NonNull private final PostingType postingType;
	@NonNull private final ArrayList<SAPGLJournalLine> lines;

	@NonNull @Getter private Money totalAcctDR;
	@NonNull @Getter private Money totalAcctCR;

	public void updateLineAcctAmounts(@NonNull final SAPGLJournalCurrencyConverter currencyConverter)
	{
		lines.forEach(line -> {
			final Money amountAcct = currencyConverter.convertToAcctCurrency(line.getAmount(), conversionCtx);
			line.setAmountAcct(amountAcct);
		});

		updateTotals();
	}

	public void updateTotals()
	{
		Money totalAcctDR = Money.zero(conversionCtx.getAcctCurrencyId());
		Money totalAcctCR = Money.zero(conversionCtx.getAcctCurrencyId());
		for (final SAPGLJournalLine line : lines)
		{
			final PostingSign postingSign = line.getPostingSign();
			if (postingSign.isDebit())
			{
				totalAcctDR = totalAcctDR.add(line.getAmountAcct());
			}
			else if (postingSign.isCredit())
			{
				totalAcctCR = totalAcctCR.add(line.getAmountAcct());
			}
		}

		this.totalAcctDR = totalAcctDR;
		this.totalAcctCR = totalAcctCR;
	}

	public void assertTotalsBalanced()
	{
		final Money totalsBalance = totalAcctDR.subtract(totalAcctCR);
		if (totalsBalance.signum() != 0)
		{
			throw new AdempiereException("Debit and Credit totals are not balanced"); // TODO trl
		}
	}

	public ImmutableList<SAPGLJournalLine> getLines() {return ImmutableList.copyOf(lines);}

	public void assertHasLines()
	{
		if (lines.isEmpty())
		{
			throw new AdempiereException("@NoLines@");
		}
	}

	public void removeAllLines()
	{
		lines.clear();
	}
}
