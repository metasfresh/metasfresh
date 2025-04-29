package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableListMultimap;
import de.metas.acct.AccountConceptualName;
import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Tax_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.money.MoneyService;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.TaxId;
import de.metas.uom.IUOMDAO;
import io.cucumber.datatable.DataTable;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_Fact_Acct;

import java.util.ArrayList;
import java.util.Optional;

@Builder
public class FactAcctMatchersFactory
{
	@NonNull private final IUOMDAO uomDAO;
	@NonNull private final ITaxDAO taxDAO;
	@NonNull private final MoneyService moneyService;
	@NonNull private final IdentifiersResolver identifiersResolver;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final C_Tax_StepDefData taxTable;

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
				.taxId(extractTaxId(row))
				.bpartnerId(extractBPartnerId(row))
				.build();
	}

	@SuppressWarnings("OptionalAssignedToNull")
	private Optional<BPartnerId> extractBPartnerId(final @NonNull DataTableRow row)
	{
		final StepDefDataIdentifier identifier = row.getAsOptionalIdentifier("C_BPartner_ID").orElse(null);
		return identifier == null ? null : Optional.ofNullable(identifier.lookupIdIn(bpartnerTable));
	}

	@SuppressWarnings("OptionalAssignedToNull")
	private Optional<TaxId> extractTaxId(final @NonNull DataTableRow row)
	{
		final StepDefDataIdentifier identifier = row.getAsOptionalIdentifier("C_Tax_ID").orElse(null);
		if (identifier == null)
		{
			return null;
		}

		if (identifier.isNullPlaceholder())
		{
			return Optional.empty();
		}

		TaxId taxId = taxTable.getIdOptional(identifier).orElse(null);
		if (taxId != null)
		{
			return Optional.of(taxId);
		}

		taxId = taxDAO.getIdByName(identifier.getAsString(), StepDefConstants.CLIENT_ID).orElse(null);
		if (taxId != null)
		{
			return Optional.of(taxId);
		}

		taxId = identifier.getAsId(TaxId.class);
		return Optional.of(taxId);
	}

}
