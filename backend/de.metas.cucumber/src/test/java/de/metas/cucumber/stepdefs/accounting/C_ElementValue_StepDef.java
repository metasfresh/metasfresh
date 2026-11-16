package de.metas.cucumber.stepdefs.accounting;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.ChartOfAccountsId;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueCreateOrUpdateRequest;
import de.metas.elementvalue.ElementValueService;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ElementValue;
import org.compiere.util.Env;

/**
 * Step definitions for creating and registering {@link I_C_ElementValue} (GL account) records.
 */
@RequiredArgsConstructor
public class C_ElementValue_StepDef
{
	private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	@NonNull private final IAccountDAO accountDAO = Services.get(IAccountDAO.class);
	private final ElementValueService elementValueService = SpringContextHolder.instance.getBean(ElementValueService.class);

	private final C_ElementValue_StepDefData elementValueTable;

	/**
	 * Creates (or looks up by {@code Value}) a {@code C_ElementValue} (GL account) record in the
	 * chart of accounts of the current client's primary accounting schema — so the account is a
	 * valid posting account for that schema (a GL-account override only resolves at posting when
	 * the account belongs to the posting schema's chart of accounts).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>Value</b> — (optional) account number/code; auto-generated from identifier if absent<br>
	 *   <b>Name</b> — (optional) account name; auto-generated from identifier if absent<br>
	 * @cucumber.depends C_ElementValue_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_ElementValues:
	 *   | Identifier      |
	 *   | overrideAccount |
	 * </pre>
	 */
	@And("metasfresh contains C_ElementValues:")
	public void createC_ElementValues(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_ElementValue record = createOrLoad(row);
			row.getAsOptionalIdentifier()
					.ifPresent(identifier -> elementValueTable.putOrReplace(identifier, record));
		});
	}

	/**
	 * Allows posting with posting types Actual and Statistical on the account ({@code C_ElementValue.PostActual} /
	 * {@code C_ElementValue.PostStatistical}), which a GL journal with {@code PostingType=A} / {@code S} requires for every account it books.
	 */
	@Given("C_ElementValue {string} allows posting of actual and statistical amounts")
	public void allowStatisticalPosting(@NonNull final String identifier)
	{
		setPostActualAndStatistical(elementValueTable.getId(StepDefDataIdentifier.ofString(identifier)));
	}

	/**
	 * Same as above, for the income summary account of the primary accounting schema (the account the year-end process books to).
	 */
	@Given("the income summary account of the primary accounting schema allows posting of actual and statistical amounts")
	public void allowStatisticalPostingOnIncomeSummaryAccount()
	{
		setPostActualAndStatistical(getIncomeSummaryElementValueId());
	}

	/**
	 * Registers the income summary account of the primary accounting schema under the given identifier, so {@code Fact_Acct records are matching} can match it by {@code Account_ID}.
	 */
	@Given("the income summary account of the primary accounting schema is registered as {string}")
	public void registerIncomeSummaryAccount(@NonNull final String identifier)
	{
		final ElementValueId elementValueId = getIncomeSummaryElementValueId();
		elementValueTable.putOrReplace(StepDefDataIdentifier.ofString(identifier), InterfaceWrapperHelper.load(elementValueId.getRepoId(), I_C_ElementValue.class));
	}

	private ElementValueId getIncomeSummaryElementValueId()
	{
		final AcctSchema acctSchema = acctSchemaDAO.getByClientAndOrg(Env.getCtx());
		final AccountId incomeSummaryAccountId = acctSchema.getGeneralLedger().getIncomeSummaryAcct().getAccountId();
		return accountDAO.getElementValueIdByAccountId(incomeSummaryAccountId);
	}

	private void setPostActualAndStatistical(@NonNull final ElementValueId elementValueId)
	{
		final I_C_ElementValue elementValue = InterfaceWrapperHelper.load(elementValueId.getRepoId(), I_C_ElementValue.class);
		elementValue.setPostActual(true);
		elementValue.setPostStatistical(true);
		InterfaceWrapperHelper.saveRecord(elementValue);
	}

	private I_C_ElementValue createOrLoad(@NonNull final DataTableRow row)
	{
		final String value = row.getAsOptionalString("Value")
				.orElseGet(() -> row.getAsOptionalIdentifier()
						.map(id -> "TEST_" + id.getAsString())
						.orElse("TEST_ACCOUNT"));

		// Anchor the override account to the posting schema's chart of accounts, otherwise the
		// override does not resolve at posting (InvoiceAcctRuleMatcher resolves per acct schema and
		// the account must be valid in that schema's chart).
		final AcctSchema acctSchema = acctSchemaDAO.getByClientAndOrg(Env.getCtx());
		final ChartOfAccountsId chartOfAccountsId = acctSchema.getChartOfAccountsId();

		final ElementValueId elementValueId = elementValueService.getByAccountNo(value, chartOfAccountsId)
				.map(ElementValue::getId)
				.orElseGet(() -> {
					final String name = row.getAsOptionalString("Name").orElse(value);
					return elementValueService.createOrUpdate(ElementValueCreateOrUpdateRequest.builder()
							.orgId(OrgId.ANY)
							.chartOfAccountsId(chartOfAccountsId)
							.value(value)
							.name(name)
							.accountSign("N") // Natural
							.accountType("E") // Expense — suitable for P_Expense_Acct overrides
							.isSummary(false)
							.build())
							.getId();
				});

		return InterfaceWrapperHelper.load(elementValueId.getRepoId(), I_C_ElementValue.class);
	}
}
