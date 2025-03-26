package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableListMultimap;
import de.metas.acct.AccountConceptualName;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.money.MoneyService;
import de.metas.uom.IUOMDAO;
import io.cucumber.datatable.DataTable;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_Fact_Acct;

import java.util.ArrayList;

@Builder
public class FactAcctMatchersFactory
{
	@NonNull private final IUOMDAO uomDAO;
	@NonNull private final MoneyService moneyService;
	@NonNull private final IdentifiersResolver identifiersResolver;

	public FactAcctMatchers ofDataTable(@NonNull final DataTable table)
	{
		final ImmutableListMultimap<TableRecordReference, FactAcctLineMatcher> lineMatchersByDocumentRef = toFactAcctLineMatchers(table);

		final ArrayList<FactAcctDocMatcher> docMatchers = new ArrayList<>();
		lineMatchersByDocumentRef.asMap()
				.forEach((documentRef, lineMatchers) -> docMatchers.add(
						FactAcctDocMatcher.builder()
								.documentRef(documentRef)
								.matchers(lineMatchers)
								.build()
				));

		return new FactAcctMatchers(docMatchers);
	}

	public ImmutableListMultimap<TableRecordReference, FactAcctLineMatcher> toFactAcctLineMatchers(@NonNull final DataTable table)
	{
		return DataTableRows.of(table)
				.stream()
				.map(this::toFactAcctLineMatcher)
				.collect(ImmutableListMultimap.toImmutableListMultimap(FactAcctLineMatcher::getDocumentRef, matcher -> matcher));
	}

	private FactAcctLineMatcher toFactAcctLineMatcher(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier documentRefIdentifier = row.getAsIdentifier("Record_ID");
		final TableRecordReference documentRef = identifiersResolver.getTableRecordReference(documentRefIdentifier);

		final String accountConceptualNameStr = row.getAsString(I_Fact_Acct.COLUMNNAME_AccountConceptualName);
		final AccountConceptualName accountConceptualName = "*".equals(accountConceptualNameStr) ? null : AccountConceptualName.ofString(accountConceptualNameStr);

		return FactAcctLineMatcher.builder()
				.row(row)
				.accountConceptualName(accountConceptualName)
				.amtAcctDr(row.getAsOptionalBigDecimal(I_Fact_Acct.COLUMNNAME_AmtAcctDr).orElse(null))
				.amtAcctCr(row.getAsOptionalBigDecimal(I_Fact_Acct.COLUMNNAME_AmtAcctCr).orElse(null))
				.amtSourceDr(row.getAsOptionalMoney(I_Fact_Acct.COLUMNNAME_AmtSourceDr, moneyService::getCurrencyIdByCurrencyCode).orElse(null))
				.amtSourceCr(row.getAsOptionalMoney(I_Fact_Acct.COLUMNNAME_AmtSourceCr, moneyService::getCurrencyIdByCurrencyCode).orElse(null))
				.qty(row.getAsOptionalQuantity(I_Fact_Acct.COLUMNNAME_Qty, uomDAO::getByX12DE355).orElse(null))
				.documentRef(documentRef)
				.build();
	}

}
