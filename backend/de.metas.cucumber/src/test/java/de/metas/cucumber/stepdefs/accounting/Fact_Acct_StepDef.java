package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.acctschema.C_AcctSchema_StepDefData;
import de.metas.cucumber.stepdefs.tax.C_Tax_StepDefData;
import de.metas.cucumber.stepdefs.tax.C_VAT_Code_StepDefData;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
import de.metas.money.MoneyService;
import de.metas.product.ProductId;
import de.metas.tax.api.ITaxDAO;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.acct.PostingStatus;
import org.compiere.model.I_Fact_Acct;
import org.compiere.util.DB;
import org.adempiere.ad.trx.api.ITrx;

import java.math.BigDecimal;
import java.time.LocalDate;

import static de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper.newFactAcctBalanceValidator;
import static de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper.newFactAcctValidator;
import static org.assertj.core.api.Assertions.assertThat;

public class Fact_Acct_StepDef
{
	@NonNull private final IdentifiersResolver identifiersResolver;
	@NonNull private final FactAcctMatchersFactory factAcctMatchersFactory;
	@NonNull private final FactAcctToTabularStringConverter factAcctTabularStringConverter;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final C_AcctSchema_StepDefData acctSchemaTable;

	public Fact_Acct_StepDef(
			@NonNull final IdentifiersResolver identifiersResolver,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_Tax_StepDefData taxTable,
			@NonNull final C_VAT_Code_StepDefData vatCodeTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_Locator_StepDefData locatorTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_ElementValue_StepDefData elementValueTable,
			@NonNull final C_AcctSchema_StepDefData acctSchemaTable
	)
	{
		this.identifiersResolver = identifiersResolver;
		this.productTable = productTable;
		this.acctSchemaTable = acctSchemaTable;

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
				.acctSchemaTable(acctSchemaTable)
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
	 * Posts the given documents again (forced), as the "Repost" action does; then waits until they are posted.
	 */
	@And("^the documents (.*) are reposted$")
	public void repost(@NonNull final String commaSeparatedIdentifiers) throws InterruptedException
	{
		final ImmutableSet<TableRecordReference> recordRefs = identifiersResolver.getTableRecordReferencesOfCommaSeparatedIdentifiers(commaSeparatedIdentifiers);
		AccountingCucumberHelper.repost(TableRecordReferenceSet.of(recordRefs));
		AccountingCucumberHelper.waitUtilPosted(recordRefs);
	}

	/**
	 * Unposts the given documents the way production does: calls {@code de_metas_acct.fact_acct_unpost}, which deletes their
	 * {@code Fact_Acct} records, sets {@code Posted='N'} and queues them in {@code de_metas_acct.accounting_docs_to_repost};
	 * then waits until the accounting server has reposted them from that queue (no second repost is triggered).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the documents revaluation are unposted
	 * </pre>
	 */
	@And("^the documents (.*) are unposted$")
	public void unpost(@NonNull final String commaSeparatedIdentifiers) throws InterruptedException
	{
		final ImmutableSet<TableRecordReference> recordRefs = identifiersResolver.getTableRecordReferencesOfCommaSeparatedIdentifiers(commaSeparatedIdentifiers);
		for (final TableRecordReference recordRef : recordRefs)
		{
			DB.getSQLValueStringEx(ITrx.TRXNAME_None,
					"SELECT \"de_metas_acct\".fact_acct_unpost(?, ?)",
					recordRef.getTableName(),
					recordRef.getRecord_ID());
		}
		AccountingCucumberHelper.waitUtilPosted(recordRefs);
	}

	/**
	 * Asserts the balance (debit minus credit) of the product's {@code P_Asset_Acct} fact lines up to and including {@code DateAcct},
	 * over all documents and all locators, including the fact lines without a locator (e.g. a cost revaluation's, which the
	 * inventory valuation report's warehouse rows do not contain). No fact lines means balance 0.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref)<br>
	 *   <b>DateAcct</b> — (required) the last posting date included<br>
	 *   <b>Balance</b> — (required) expected balance in the schema's currency<br>
	 *   <b>C_AcctSchema_ID</b> — (optional, identifier-ref) default: the client's primary accounting schema<br>
	 * @cucumber.example
	 * <pre>
	 * And expect P_Asset balance for product
	 *   | M_Product_ID | DateAcct   | Balance |
	 *   | product      | 2024-03-06 | 1500    |
	 * </pre>
	 */
	@And("^expect P_Asset balance for product$")
	public void assertProductAssetBalance(@NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(row -> {
			final ProductId productId = row.getAsIdentifier(I_Fact_Acct.COLUMNNAME_M_Product_ID).lookupIdIn(productTable);
			final AcctSchemaId acctSchemaId = row.getAsOptionalIdentifier(I_Fact_Acct.COLUMNNAME_C_AcctSchema_ID)
					.map(acctSchemaTable::getId)
					.orElseGet(() -> Services.get(IAcctSchemaDAO.class).getPrimaryAcctSchemaId(StepDefConstants.CLIENT_ID));
			final LocalDate dateAcct = row.getAsLocalDate(I_Fact_Acct.COLUMNNAME_DateAcct);
			final BigDecimal expectedBalance = row.getAsBigDecimal("Balance");

			final BigDecimal actualBalance = AccountingCucumberHelper.getProductAssetBalance(productId, acctSchemaId, dateAcct);
			assertThat(actualBalance)
					.as("P_Asset_Acct balance of %s up to %s", row.getAsString(I_Fact_Acct.COLUMNNAME_M_Product_ID), dateAcct)
					.isEqualByComparingTo(expectedBalance);
		});
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

	/**
	 * Posts the given documents again (forced), as the "Repost" action does, and expects each posting to fail with the given posting status.
	 *
	 * @cucumber.example
	 * <pre>
	 * When reposting the documents revaluation fails with posting status p
	 * </pre>
	 */
	@And("^reposting the documents (.*) fails with posting status (.*)$")
	public void repostExpectingPostingError(
			@NonNull final String commaSeparatedIdentifiers,
			@NonNull final String expectedPostingStatusCode) throws InterruptedException
	{
		final PostingStatus expectedPostingStatus = PostingStatus.ofCode(expectedPostingStatusCode);
		final ImmutableSet<TableRecordReference> recordRefs = identifiersResolver.getTableRecordReferencesOfCommaSeparatedIdentifiers(commaSeparatedIdentifiers);
		try
		{
			AccountingCucumberHelper.repost(TableRecordReferenceSet.of(recordRefs));
		}
		catch (final AdempiereException ignored)
		{
			// expected: a document that is posted immediately throws its posting error; its posting status is asserted below
		}

		for (final TableRecordReference recordRef : recordRefs)
		{
			assertThat(AccountingCucumberHelper.waitUntilPostingDone(recordRef)).as("posting status of %s", recordRef).isEqualTo(expectedPostingStatus);
		}
	}

	/**
	 * Unlike {@code no Fact_Acct records are found for documents}, does not wait for the documents to be posted, so it also checks a document whose posting failed.
	 *
	 * @cucumber.example
	 * <pre>
	 * Then no Fact_Acct records exist for documents revaluation
	 * </pre>
	 */
	@And("^no Fact_Acct records exist for documents (.*)$")
	public void assertNoFactAcctsWithoutWaiting(@NonNull final String commaSeparatedIdentifiers)
	{
		final ImmutableSet<TableRecordReference> recordRefs = identifiersResolver.getTableRecordReferencesOfCommaSeparatedIdentifiers(commaSeparatedIdentifiers);
		for (final TableRecordReference recordRef : recordRefs)
		{
			final int count = Services.get(IQueryBL.class).createQueryBuilder(I_Fact_Acct.class)
					.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
					.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
					.create()
					.count();
			assertThat(count).as("Fact_Acct count of %s", recordRef).isZero();
		}
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
}
