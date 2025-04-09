package de.metas.cucumber.stepdefs.accounting;

import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Tax_StepDefData;
import de.metas.money.MoneyService;
import de.metas.tax.api.ITaxDAO;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import static de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper.newFactAcctValidator;

public class Fact_Acct_StepDef
{
	@NonNull private final IdentifiersResolver identifiersResolver;
	@NonNull private final FactAcctMatchersFactory factAcctMatchersFactory;
	@NonNull private final FactAcctToTabularStringConverter factAcctTabularStringConverter;

	public Fact_Acct_StepDef(
			@NonNull final IdentifiersResolver identifiersResolver,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_Tax_StepDefData taxTable)
	{
		this.identifiersResolver = identifiersResolver;

		@NonNull final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
		@NonNull final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
		@NonNull final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);
		this.factAcctMatchersFactory = FactAcctMatchersFactory.builder()
				.uomDAO(uomDAO)
				.taxDAO(taxDAO)
				.moneyService(moneyService)
				.identifiersResolver(identifiersResolver)
				.bpartnerTable(bpartnerTable)
				.taxTable(taxTable)
				.build();
		this.factAcctTabularStringConverter = FactAcctToTabularStringConverter.builder()
				.uomDAO(uomDAO)
				.moneyService(moneyService)
				.bpartnerTable(bpartnerTable)
				.taxTable(taxTable)
				.identifiersResolver(identifiersResolver)
				.build();
	}

	@And("^Fact_Acct records are matching$")
	public void validateFullyMatchingAndPartialMatchingFactAccts(
			@NonNull final DataTable table) throws Throwable
	{
		newFactAcctValidator()
				.factAcctTabularStringConverter(factAcctTabularStringConverter)
				.matchers(factAcctMatchersFactory.ofDataTable(table))
				.validate();
	}

	@And("^no Fact_Acct records are found for documents (.*)$")
	public void assertNoFactAccts(@NonNull final String identifiersStr) throws Throwable
	{
		newFactAcctValidator()
				.factAcctTabularStringConverter(factAcctTabularStringConverter)
				.matchers(FactAcctMatchers.noRecords(identifiersResolver.getTableRecordReferencesOfCommaSeparatedIdentifiers(identifiersStr)))
				.validate();
	}
}
