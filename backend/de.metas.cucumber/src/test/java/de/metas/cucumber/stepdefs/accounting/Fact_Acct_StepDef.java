package de.metas.cucumber.stepdefs.accounting;

import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.money.MoneyService;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.util.Set;

import static de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper.newFactAcctValidator;

public class Fact_Acct_StepDef
{
	@NonNull private final IdentifiersResolver identifiersResolver;
	@NonNull private final FactAcctMatchersFactory factAcctMatchersFactory;
	@NonNull private final FactAcctToTabularStringConverter factAcctTabularStringConverter;

	public Fact_Acct_StepDef(
			@NonNull final IdentifiersResolver identifiersResolver,
			@NonNull final C_BPartner_StepDefData bpartnerTable)
	{
		this.identifiersResolver = identifiersResolver;

		@NonNull final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
		@NonNull final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);
		this.factAcctMatchersFactory = FactAcctMatchersFactory.builder()
				.uomDAO(uomDAO)
				.moneyService(moneyService)
				.identifiersResolver(identifiersResolver)
				.build();
		this.factAcctTabularStringConverter = FactAcctToTabularStringConverter.builder()
				.uomDAO(uomDAO)
				.moneyService(moneyService)
				.bpartnerTable(bpartnerTable)
				.build();
	}

	@And("^Fact_Acct records of (.*) are fully matching, Fact_Acct records of (.*) are partial matching$")
	public void validateFullyMatchingAndPartialMatchingFactAccts(
			@Nullable final String fullyMatchingIdentifiers,
			@Nullable final String partialMatchingIdentifiers,
			@Nullable final DataTable table) throws Throwable
	{
		final Set<TableRecordReference> documentRefsToFullyMatch = identifiersResolver.getTableRecordReferencesOfCommaSeparatedIdentifiers(fullyMatchingIdentifiers);
		final Set<TableRecordReference> documentRefsToPartialMatch = identifiersResolver.getTableRecordReferencesOfCommaSeparatedIdentifiers(partialMatchingIdentifiers);
		final FactAcctMatchers matchers = factAcctMatchersFactory.ofDataTable(table, documentRefsToFullyMatch, documentRefsToPartialMatch);

		newFactAcctValidator()
				.factAcctTabularStringConverter(factAcctTabularStringConverter)
				.matchers(matchers)
				.validate();
	}

	@And("^Fact_Acct records of (.*) are fully matching exactly$")
	public void validateFullyMatchingFactAccts(
			@Nullable final String fullyMatchingIdentifiers,
			@Nullable final DataTable table) throws Throwable
	{
		validateFullyMatchingAndPartialMatchingFactAccts(fullyMatchingIdentifiers, null, table);
	}

	@And("^Fact_Acct records of (.*) are matching partially$")
	public void validatePartialMatchingFactAccts(
			@Nullable final String partialMatchingIdentifiers,
			@Nullable final DataTable table) throws Throwable
	{
		validateFullyMatchingAndPartialMatchingFactAccts(null, partialMatchingIdentifiers, table);
	}

	@And("^Fact_Acct records are found for payment allocation (.*)$")
	@Deprecated
	public void validatePaymentAllocationFactAccts(
			@NonNull final String commaSeparatedIdentifiers,
			@Nullable final DataTable table) throws Throwable
	{
		validateFullyMatchingAndPartialMatchingFactAccts(commaSeparatedIdentifiers, null, table);
	}

	@And("^no Fact_Acct records are found for documents (.*)$")
	@And("^no Fact_Acct records are found for payment allocation (.*)$")
	public void assertNoFactAccts(@NonNull final String identifiersStr) throws Throwable
	{
		newFactAcctValidator()
				.factAcctTabularStringConverter(factAcctTabularStringConverter)
				.matchers(FactAcctMatchers.noRecords(identifiersResolver.getTableRecordReferencesOfCommaSeparatedIdentifiers(identifiersStr)))
				.validate();
	}
}
