package de.metas.acct.gljournal_sap;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.PostingType;
import de.metas.acct.gljournal_sap.service.SAPGLJournalCurrencyConverter;
import de.metas.acct.gljournal_sap.service.SAPGLJournalTaxProvider;
import de.metas.document.dimension.Dimension;
import de.metas.document.engine.DocStatus;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.tax.api.TaxId;
import de.metas.util.Check;
import de.metas.util.lang.SeqNo;
import de.metas.util.lang.SeqNoProvider;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.function.Predicate;
import java.util.function.Supplier;

@EqualsAndHashCode
@ToString
@Builder
public class SAPGLJournal
{
	@NonNull @Getter private final SAPGLJournalId id;
	@NonNull @Getter private final SAPGLJournalCurrencyConversionCtx conversionCtx;

	@NonNull @Getter private final AcctSchemaId acctSchemaId;
	@NonNull @Getter private final PostingType postingType;
	@NonNull private final ArrayList<SAPGLJournalLine> lines;

	@NonNull @Getter private Money totalAcctDR;
	@NonNull @Getter private Money totalAcctCR;
	@NonNull @Getter private final DocStatus docStatus;

	@NonNull @Getter private final OrgId orgId;
	@NonNull @Getter private final Dimension dimension;

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

	public Supplier<SAPGLJournalLineId> addLine(
			@NonNull PostingSign postingSign,
			@NonNull AccountId accountId,
			@NonNull BigDecimal amountBD,
			@Nullable TaxId taxId,
			@NonNull SAPGLJournalCurrencyConverter currencyConverter)
	{
		final Money amount = Money.of(amountBD, conversionCtx.getCurrencyId());
		final Money amountAcct = currencyConverter.convertToAcctCurrency(amount, conversionCtx);

		final SAPGLJournalLine line = SAPGLJournalLine.builder()
				.line(getNextLineNo())
				.accountId(accountId)
				.postingSign(postingSign)
				.amount(amount)
				.amountAcct(amountAcct)
				.taxId(taxId)
				.orgId(orgId)
				.dimension(dimension)
				.build();
		lines.add(line);

		updateTotals();

		return line::getIdNotNull;
	}

	private SeqNo getNextLineNo()
	{
		final SeqNo lastSeqNo = lines.stream()
				.map(SAPGLJournalLine::getLine)
				.max(Comparator.naturalOrder())
				.orElse(SeqNo.ofInt(0));

		return lastSeqNo.next();
	}

	private void renumberLines()
	{
		SeqNoProvider lineNoProvider = SeqNoProvider.ofInt(10);
		for (SAPGLJournalLine line : lines)
		{
			line.setLine(lineNoProvider.getAndIncrement());
		}
	}

	public void regenerateTaxLines(
			@NonNull final SAPGLJournalTaxProvider taxProvider,
			@NonNull final SAPGLJournalCurrencyConverter currencyConverter)
	{
		if (!docStatus.isDraftedOrInProgress())
		{
			throw new AdempiereException("GL Journal cannot  be changed");
		}

		boolean hasChanges = false;
		for (ListIterator<SAPGLJournalLine> it = lines.listIterator(); it.hasNext(); )
		{
			final SAPGLJournalLine line = it.next();
			if (line.isTaxLine())
			{
				// remove old tax lines, we will generate them if needed
				it.remove();
				hasChanges = true;
			}
			else if (line.isBaseTaxLine())
			{
				final SAPGLJournalLine taxLine = createTaxLine(line, taxProvider, currencyConverter);
				it.add(taxLine);
				hasChanges = true;
			}
		}

		if (hasChanges)
		{
			renumberLines();
			updateTotals();
		}
	}

	private SAPGLJournalLine createTaxLine(
			@NonNull final SAPGLJournalLine baseLine,
			@NonNull final SAPGLJournalTaxProvider taxProvider,
			@NonNull final SAPGLJournalCurrencyConverter currencyConverter)
	{
		final TaxId taxId = Check.assumeNotNull(baseLine.getTaxId(), "line shall have the tax set: {}", baseLine);
		final PostingSign taxPostingSign = baseLine.getPostingSign();
		final AccountId taxAccountId = taxProvider.getTaxAccountId(taxId, acctSchemaId, taxPostingSign);
		final Money taxAmt = taxProvider.calculateTaxAmt(baseLine.getAmount(), taxId);
		final Money taxAmtAcct = currencyConverter.convertToAcctCurrency(taxAmt, conversionCtx);

		return SAPGLJournalLine.builder()
				.parentId(baseLine.getIdNotNull())
				.line(baseLine.getLine()) // will be updated later
				.accountId(taxAccountId)
				.postingSign(taxPostingSign)
				.amount(taxAmt)
				.amountAcct(taxAmtAcct)
				.taxId(taxId)
				.orgId(baseLine.getOrgId())
				.dimension(baseLine.getDimension())
				.build();
	}

	public void removeLinesIf(final Predicate<SAPGLJournalLine> predicate)
	{
		lines.removeIf(predicate);
		updateTotals();
	}
}
