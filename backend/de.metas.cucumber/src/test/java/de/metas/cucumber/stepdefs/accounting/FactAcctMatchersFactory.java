package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ArrayListMultimap;
import de.metas.acct.AccountConceptualName;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.money.MoneyService;
import de.metas.uom.IUOMDAO;
import io.cucumber.datatable.DataTable;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_Fact_Acct;
import org.elasticsearch.common.util.set.Sets;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Set;

@Builder
public class FactAcctMatchersFactory
{
	@NonNull private final IUOMDAO uomDAO;
	@NonNull private final MoneyService moneyService;
	@NonNull private final IdentifiersResolver identifiersResolver;

	public FactAcctMatchers ofDataTable(
			@Nullable final DataTable table,
			@NonNull final Set<TableRecordReference> documentRefsToFullyMatch,
			@NonNull final Set<TableRecordReference> documentRefsToPartialMatch)
	{
		if (!Sets.intersection(documentRefsToFullyMatch, documentRefsToPartialMatch).isEmpty())
		{
			throw new AdempiereException("documentRefsToFullyMatch and documentRefsToPartialMatch must be disjoint")
					.appendParametersToMessage()
					.setParameter("documentRefsToFullyMatch", documentRefsToFullyMatch)
					.setParameter("documentRefsToPartialMatch", documentRefsToPartialMatch);
		}

		final Set<TableRecordReference> documentRefs = Sets.union(documentRefsToFullyMatch, documentRefsToPartialMatch);
		@Nullable final TableRecordReference singleDocumentRef = documentRefs.size() == 1 ? documentRefs.iterator().next() : null;

		final ArrayListMultimap<TableRecordReference, FactAcctLineMatcher> lineMatchersByDocumentRef = toFactAcctLineMatchers(table, singleDocumentRef);

		final ArrayList<FactAcctDocMatcher> docMatchers = new ArrayList<>();
		for (final TableRecordReference documentRef : documentRefs)
		{
			docMatchers.add(
					FactAcctDocMatcher.builder()
							.documentRef(documentRef)
							.partialMatch(documentRefsToPartialMatch.contains(documentRef))
							.matchers(lineMatchersByDocumentRef.removeAll(documentRef))
							.build()
			);
		}

		lineMatchersByDocumentRef.asMap()
				.forEach((documentRef, lineMatchers) -> docMatchers.add(
						FactAcctDocMatcher.builder()
								.documentRef(documentRef)
								.partialMatch(documentRefsToPartialMatch.contains(documentRef))
								.matchers(lineMatchers)
								.build()
				));

		return new FactAcctMatchers(docMatchers);
	}

	public ArrayListMultimap<TableRecordReference, FactAcctLineMatcher> toFactAcctLineMatchers(
			@Nullable final DataTable table,
			@Nullable final TableRecordReference documentRef)
	{
		final ArrayListMultimap<TableRecordReference, FactAcctLineMatcher> result = ArrayListMultimap.create();

		if (table != null && !table.isEmpty())
		{
			DataTableRows.of(table)
					.stream()
					.map(row -> toFactAcctLineMatcher(row, documentRef))
					.forEach(matcher -> result.put(matcher.getDocumentRef(), matcher));
		}

		return result;
	}

	private FactAcctLineMatcher toFactAcctLineMatcher(
			@NonNull final DataTableRow row,
			@Nullable final TableRecordReference defaultDocumentRef)
	{
		final TableRecordReference documentRef = row.getAsOptionalIdentifier("Record_ID").map(identifiersResolver::getTableRecordReference).orElse(defaultDocumentRef);
		if (documentRef == null)
		{
			throw new AdempiereException("No documentRef found for row " + row + " and no single document defined");
		}

		return FactAcctLineMatcher.builder()
				.row(row)
				.accountConceptualName(row.getAsOptionalString(I_Fact_Acct.COLUMNNAME_AccountConceptualName).map(AccountConceptualName::ofString).orElse(null))
				.amtAcctDr(row.getAsOptionalBigDecimal(I_Fact_Acct.COLUMNNAME_AmtAcctDr).orElse(null))
				.amtAcctCr(row.getAsOptionalBigDecimal(I_Fact_Acct.COLUMNNAME_AmtAcctCr).orElse(null))
				.amtSourceDr(row.getAsOptionalMoney(I_Fact_Acct.COLUMNNAME_AmtSourceDr, moneyService::getCurrencyIdByCurrencyCode).orElse(null))
				.amtSourceCr(row.getAsOptionalMoney(I_Fact_Acct.COLUMNNAME_AmtSourceCr, moneyService::getCurrencyIdByCurrencyCode).orElse(null))
				.qty(row.getAsOptionalQuantity(I_Fact_Acct.COLUMNNAME_Qty, uomDAO::getByX12DE355).orElse(null))
				.documentRef(documentRef)
				.build();
	}

}
