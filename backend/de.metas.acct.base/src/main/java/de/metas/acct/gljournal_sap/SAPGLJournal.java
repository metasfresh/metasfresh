package de.metas.acct.gljournal_sap;

import com.google.common.collect.ImmutableList;
import de.metas.acct.Account;
import de.metas.acct.GLCategoryId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.PostingType;
import de.metas.acct.gljournal_sap.service.SAPGLJournalCreateRequest;
import de.metas.acct.gljournal_sap.service.SAPGLJournalCurrencyConverter;
import de.metas.acct.gljournal_sap.service.SAPGLJournalLineCreateRequest;
import de.metas.acct.gljournal_sap.service.SAPGLJournalTaxProvider;
import de.metas.document.DocTypeId;
import de.metas.document.dimension.Dimension;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
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
import lombok.Singular;
import lombok.ToString;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
public class SAPGLJournal
{
	@NonNull @Getter private final SAPGLJournalId id;
	@NonNull @Getter private final SAPGLJournalCurrencyConversionCtx conversionCtx;

	@NonNull @Getter private final DocTypeId docTypeId;

	@NonNull @Getter private final AcctSchemaId acctSchemaId;
	@NonNull @Getter private final PostingType postingType;
	@NonNull @Getter @With private final DocStatus docStatus;
	@NonNull private final ArrayList<SAPGLJournalLine> lines;

	@NonNull @Getter private Money totalAcctDR;
	@NonNull @Getter private Money totalAcctCR;

	@NonNull @Getter private final OrgId orgId;
	@NonNull @Getter private final Dimension dimension;
	@NonNull @Getter private final String description;
	@NonNull @Getter private final GLCategoryId glCategoryId;

	@Builder
	private SAPGLJournal(
			@NonNull final SAPGLJournalId id,
			@NonNull final SAPGLJournalCurrencyConversionCtx conversionCtx,
			@NonNull final DocTypeId docTypeId,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final PostingType postingType,
			@NonNull final DocStatus docStatus,
			@NonNull @Singular final List<SAPGLJournalLine> lines,
			@NonNull final Money totalAcctDR,
			@NonNull final Money totalAcctCR,
			@NonNull final OrgId orgId,
			@NonNull final Dimension dimension,
			@NonNull final String description,
			@NonNull final GLCategoryId glCategoryId)
	{
		this.id = id;
		this.conversionCtx = conversionCtx;
		this.docTypeId = docTypeId;
		this.acctSchemaId = acctSchemaId;
		this.postingType = postingType;
		this.docStatus = docStatus;
		this.lines = new ArrayList<>(lines);
		this.totalAcctDR = totalAcctDR;
		this.totalAcctCR = totalAcctCR;
		this.orgId = orgId;
		this.dimension = dimension;
		this.description = description;
		this.glCategoryId = glCategoryId;
	}

	public CurrencyId getCurrencyId() {return conversionCtx.getCurrencyId();}

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

	public void addLines(
			@NonNull final List<SAPGLJournalLineCreateRequest> requests,
			@NonNull final SAPGLJournalCurrencyConverter currencyConverter)
	{
		addLines(requests.stream()
				.map(request -> createLine(request, currencyConverter))
				.collect(Collectors.toList()));
	}

	public Supplier<SAPGLJournalLineId> addLine(
			@NonNull final SAPGLJournalLineCreateRequest request,
			@NonNull final SAPGLJournalCurrencyConverter currencyConverter)
	{
		final SAPGLJournalLine line = createLine(request, currencyConverter);
		addLines(ImmutableList.of(line));
		return line::getIdNotNull;
	}

	private void addLines(final List<SAPGLJournalLine> linesToAdd)
	{
		if (linesToAdd.isEmpty())
		{
			return;
		}

		this.lines.addAll(linesToAdd);
		updateTotals();
	}

	private SAPGLJournalLine createLine(final @NonNull SAPGLJournalLineCreateRequest request, final @NonNull SAPGLJournalCurrencyConverter currencyConverter)
	{
		final CurrencyId currencyId = conversionCtx.getCurrencyId();
		if (request.getExpectedCurrencyId() != null && !CurrencyId.equals(request.getExpectedCurrencyId(), currencyId))
		{
			throw new AdempiereException("Expected GL Journal to have currency " + request.getExpectedCurrencyId() + " but it has " + currencyId);
		}

		final Money amount = Money.of(request.getAmount(), currencyId);
		final Money amountAcct = currencyConverter.convertToAcctCurrency(amount, conversionCtx);

		return SAPGLJournalLine.builder()
				.id(request.getAlreadyReservedId())
				.line(getNextLineNo())
				.description(request.getDescription())
				.account(request.getAccount())
				.postingSign(request.getPostingSign())
				.amount(amount)
				.amountAcct(amountAcct)
				.taxId(request.getTaxId())
				.orgId(orgId)
				.bpartnerId(request.getBpartnerId())
				.dimension(request.getDimension().getSectionCodeId() != null
						? request.getDimension()
						: request.getDimension().withSectionCodeId(dimension.getSectionCodeId()))
				.determineTaxBaseSAP(request.isDetermineTaxBaseSAP())
				.openItemTrxInfo(request.getOpenItemTrxInfo())
				.isFieldsReadOnlyInUI(request.isFieldsReadOnlyInUI())
				.build();
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

	public void setProcessed(final boolean processed)
	{
		lines.forEach(line -> line.setProcessed(processed));
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
			if (line.isGeneratedTaxLine())
			{
				// remove old tax lines, we will generate them if needed
				it.remove();
				hasChanges = true;
			}
			else if (line.isBaseTaxLine())
			{
				final List<SAPGLJournalLine> taxLines = createTaxLines(line, taxProvider, currencyConverter);
				if (!taxLines.isEmpty())
				{
					taxLines.forEach(it::add);
					hasChanges = true;
				}
			}
		}

		if (hasChanges)
		{
			renumberLines();
			updateTotals();
		}
	}

	private List<SAPGLJournalLine> createTaxLines(
			@NonNull final SAPGLJournalLine baseLine,
			@NonNull final SAPGLJournalTaxProvider taxProvider,
			@NonNull final SAPGLJournalCurrencyConverter currencyConverter)
	{
		final TaxId taxId = Check.assumeNotNull(baseLine.getTaxId(), "line shall have the tax set: {}", baseLine);
		final PostingSign taxPostingSign = baseLine.getPostingSign();
		final Account taxAccount = taxProvider.getTaxAccount(taxId, acctSchemaId, taxPostingSign);
		final Money taxAmt = taxProvider.calculateTaxAmt(baseLine.getAmount(), taxId);
		final Money taxAmtAcct = currencyConverter.convertToAcctCurrency(taxAmt, conversionCtx);

		final ArrayList<SAPGLJournalLine> result = new ArrayList<>();
		result.add(
				SAPGLJournalLine.builder()
						.parentId(baseLine.getIdNotNull())
						.line(baseLine.getLine()) // will be updated later
						.account(taxAccount)
						.postingSign(taxPostingSign)
						.amount(taxAmt)
						.amountAcct(taxAmtAcct)
						.taxId(taxId)
						.orgId(baseLine.getOrgId())
						.dimension(baseLine.getDimension())
						.build()
		);

		if (taxProvider.isReverseCharge(taxId))
		{
			result.add(
					SAPGLJournalLine.builder()
							.parentId(baseLine.getIdNotNull())
							.line(baseLine.getLine()) // will be updated later
							.account(taxProvider.getTaxAccount(taxId, acctSchemaId, taxPostingSign.reverse()))
							.postingSign(taxPostingSign.reverse())
							.amount(taxAmt)
							.amountAcct(taxAmtAcct)
							.taxId(taxId)
							.orgId(baseLine.getOrgId())
							.dimension(baseLine.getDimension())
							.build()
			);
		}

		return result;
	}

	public void removeLinesIf(final Predicate<SAPGLJournalLine> predicate)
	{
		lines.removeIf(predicate);
		updateTotals();
	}

	@NonNull
	public SAPGLJournalCreateRequest getReversal(@NonNull final Instant reversalDocDate)
	{
		return SAPGLJournalCreateRequest.builder()
				.docTypeId(docTypeId)
				.conversionCtx(conversionCtx)
				.dateDoc(reversalDocDate)
				.acctSchemaId(acctSchemaId)
				.postingType(postingType)
				.reversalId(id)
				.totalAcctDR(getTotalAcctCR())
				.totalAcctCR(getTotalAcctDR())
				.orgId(orgId)
				.dimension(dimension)
				.description(description)
				.glCategoryId(glCategoryId)
				//
				.lines(getLines()
						.stream()
						.map(line -> SAPGLJournalLineCreateRequest.builder()
								.postingSign(line.getPostingSign())
								.account(line.getAccount())
								.postingSign(line.getPostingSign().reverse())
								.amount(line.getAmount().toBigDecimal())
								.dimension(line.getDimension())
								.description(line.getDescription())
								.taxId(line.getTaxId())
								.determineTaxBaseSAP(line.isDetermineTaxBaseSAP())
								.build())
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}
