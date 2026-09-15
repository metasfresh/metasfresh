package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.FactAcctQuery;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.tax.C_Tax_StepDefData;
import de.metas.cucumber.stepdefs.tax.C_VAT_Code_StepDefData;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
import de.metas.money.MoneyService;
import de.metas.tax.api.ITaxDAO;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;

import java.util.List;

import static de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper.newFactAcctBalanceValidator;
import static de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper.newFactAcctValidator;
import static org.assertj.core.api.Assertions.assertThat;

public class Fact_Acct_StepDef
{
	@NonNull private final IdentifiersResolver identifiersResolver;
	@NonNull private final FactAcctMatchersFactory factAcctMatchersFactory;
	@NonNull private final FactAcctToTabularStringConverter factAcctTabularStringConverter;
	@NonNull private final PP_Order_StepDefData ppOrderTable;
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public Fact_Acct_StepDef(
			@NonNull final IdentifiersResolver identifiersResolver,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_Tax_StepDefData taxTable,
			@NonNull final C_VAT_Code_StepDefData vatCodeTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_Locator_StepDefData locatorTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_ElementValue_StepDefData elementValueTable,
			@NonNull final PP_Order_StepDefData ppOrderTable
	)
	{
		this.identifiersResolver = identifiersResolver;
		this.ppOrderTable = ppOrderTable;

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
				.vatCodeTable(vatCodeTable)
				.productTable(productTable)
				.locatorTable(locatorTable)
				.invoiceTable(invoiceTable)
				.elementValueTable(elementValueTable)
				.build();
		this.factAcctTabularStringConverter = FactAcctToTabularStringConverter.builder()
				.uomDAO(uomDAO)
				.taxDAO(taxDAO)
				.moneyService(moneyService)
				.bpartnerTable(bpartnerTable)
				.taxTable(taxTable)
				.productTable(productTable)
				.identifiersResolver(identifiersResolver)
				.build();
	}

	@And("^Wait until documents (.*) (is|are) posted$")
	public void waitUntilPosted(
			@NonNull final String commaSeparatedIdentifiers,
			@SuppressWarnings("unused") final String isOrAre) throws InterruptedException
	{
		final ImmutableSet<TableRecordReference> recordRefs = identifiersResolver.getTableRecordReferencesOfCommaSeparatedIdentifiers(commaSeparatedIdentifiers);
		AccountingCucumberHelper.waitUtilPosted(recordRefs);
	}

	/**
	 * Matching philosophy:
	 * not all fact_acct-records are checked, but instead, only fact accounts that match the record-ids are fetched and then matched against the given {@code table}.
	 * Therefore, for table-rows with star: it's still important to set the {@code Record_ID}.
	 */
	@And("^Fact_Acct records are matching$")
	public void validateFullyMatchingAndPartialMatchingFactAccts(
			@NonNull final DataTable table) throws Throwable
	{
		newFactAcctValidator()
				.factAcctTabularStringConverter(factAcctTabularStringConverter)
				.matchers(factAcctMatchersFactory.createLineMatchers(table))
				.validate();
	}

	@And("^no Fact_Acct records are found for documents (.*)$")
	public void assertNoFactAccts(@NonNull final String commaSeparatedIdentifiers) throws Throwable
	{
		final ImmutableSet<TableRecordReference> recordRefs = identifiersResolver.getTableRecordReferencesOfCommaSeparatedIdentifiers(commaSeparatedIdentifiers);
		AccountingCucumberHelper.waitUtilPosted(recordRefs);
		
		newFactAcctValidator()
				.factAcctTabularStringConverter(factAcctTabularStringConverter)
				.matchers(FactAcctMatchers.noRecords(recordRefs))
				.validate();
	}

	@And("^Fact_Acct records balances for documents (.*) are matching$")
	public void assertBalances(
			@NonNull final String commaSeparatedIdentifiers,
			@NonNull final DataTable table) throws Throwable
	{
		final ImmutableSet<TableRecordReference> recordRefs = identifiersResolver.getTableRecordReferencesOfCommaSeparatedIdentifiers(commaSeparatedIdentifiers);
		AccountingCucumberHelper.waitUtilPosted(recordRefs);

		newFactAcctBalanceValidator()
				.factAcctTabularStringConverter(factAcctTabularStringConverter)
				.matchers(factAcctMatchersFactory.createBalanceMatchers(table, recordRefs))
				.validate();
	}

	/**
	 * Asserts the trial balance of an entire manufacturing order across ALL of its cost collectors.
	 * <p>
	 * Gathers every {@link I_PP_Cost_Collector} of the given PP_Order (component issue, main-product
	 * receipt, co/by-product receipts, any cost-difference distribution), waits until each is posted, and:
	 * <ul>
	 *   <li>asserts the order balances overall — Σ AmtAcctDr == Σ AmtAcctCr across all its cost collectors;</li>
	 *   <li>asserts the per-account aggregated balances given in the DataTable (e.g. {@code P_WIP_Acct}
	 *   and {@code P_Asset_Acct} each net to 0 over the whole order once the co-product receipt capitalizes
	 *   its value to inventory instead of booking it to a P&amp;L variance account).</li>
	 * </ul>
	 * The DataTable uses the same columns as {@code Fact_Acct records balances for documents ... are matching}
	 * ({@code AccountConceptualName} plus any of {@code AmtAcctDr} / {@code AmtAcctCr} / {@code AcctBalance} / …).
	 * <p>
	 * Example:
	 * <pre>
	 * And Fact_Acct records balances over the whole PP_Order ppOrder are matching
	 *   | AccountConceptualName | AcctBalance |
	 *   | P_WIP_Acct            | 0           |
	 *   | P_Asset_Acct          | 0           |
	 * </pre>
	 *
	 * @param ppOrderIdentifier identifier of the PP_Order whose cost collectors are summed
	 * @param table             expected per-account aggregated balances
	 */
	@And("^Fact_Acct records balances over the whole PP_Order (.*) are matching$")
	public void assertBalancesOverWholePPOrder(
			@NonNull final String ppOrderIdentifier,
			@NonNull final DataTable table) throws Throwable
	{
		final I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);

		final ImmutableSet<TableRecordReference> recordRefs = queryBL.createQueryBuilder(I_PP_Cost_Collector.class)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID())
				.create()
				.list(I_PP_Cost_Collector.class)
				.stream()
				.map(TableRecordReference::of)
				.collect(ImmutableSet.toImmutableSet());

		AccountingCucumberHelper.waitUtilPosted(recordRefs);

		// The whole manufacturing order must balance: Σ AmtAcctDr == Σ AmtAcctCr across ALL its cost collectors.
		assertWholeOrderDebitsEqualCredits(recordRefs);

		// Per-account aggregated balances (e.g. P_WIP_Acct / P_Asset_Acct each net to 0 over the order).
		newFactAcctBalanceValidator()
				.factAcctTabularStringConverter(factAcctTabularStringConverter)
				.matchers(factAcctMatchersFactory.createBalanceMatchers(table, recordRefs))
				.validate();
	}

	private void assertWholeOrderDebitsEqualCredits(@NonNull final ImmutableSet<TableRecordReference> recordRefs)
	{
		final IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);
		final List<FactAcctQuery> queries = recordRefs.stream()
				.map(recordRef -> FactAcctQuery.builder().recordRef(recordRef).build())
				.collect(ImmutableList.toImmutableList());
		final FactAcctRecords records = FactAcctRecords.builder()
				.list(ImmutableList.copyOf(factAcctDAO.list(queries)))
				.tabularStringConverter(factAcctTabularStringConverter)
				.build();

		assertThat(records.getAmtAcctDr())
				.as("Σ AmtAcctDr must equal Σ AmtAcctCr across all cost collectors of the PP_Order")
				.isEqualByComparingTo(records.getAmtAcctCr());
	}
}
