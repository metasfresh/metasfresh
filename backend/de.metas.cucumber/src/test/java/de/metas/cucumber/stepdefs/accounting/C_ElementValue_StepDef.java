package de.metas.cucumber.stepdefs.accounting;

import de.metas.acct.api.ChartOfAccountsId;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueCreateOrUpdateRequest;
import de.metas.elementvalue.ElementValueService;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Element;
import org.compiere.model.I_C_ElementValue;
import org.compiere.util.Env;

/**
 * Step definitions for creating and registering {@link I_C_ElementValue} (GL account) records.
 */
@RequiredArgsConstructor
public class C_ElementValue_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ElementValueService elementValueService = SpringContextHolder.instance.getBean(ElementValueService.class);

	private final C_ElementValue_StepDefData elementValueTable;

	/**
	 * Creates (or looks up by {@code Value}) a {@code C_ElementValue} (GL account) record,
	 * anchored to the first active {@code C_Element} (chart of accounts) of the current client.
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

	private I_C_ElementValue createOrLoad(@NonNull final DataTableRow row)
	{
		final String value = row.getAsOptionalString("Value")
				.orElseGet(() -> row.getAsOptionalIdentifier()
						.map(id -> "TEST_" + id.getAsString())
						.orElse("TEST_ACCOUNT"));

		final I_C_Element element = findDefaultElement();
		final ChartOfAccountsId chartOfAccountsId = ChartOfAccountsId.ofRepoId(element.getC_Element_ID());

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

	private I_C_Element findDefaultElement()
	{
		// Find the first natural-account element (ElementType='A') for the current client (Chart of Accounts).
		// Use .first() rather than .firstOnly() — a client may have more than one element; we pick the lowest ID.
		final int clientId = Env.getAD_Client_ID(Env.getCtx());

		final I_C_Element element = queryBL.createQueryBuilder(I_C_Element.class)
				.addEqualsFilter(I_C_Element.COLUMNNAME_AD_Client_ID, clientId)
				.addEqualsFilter(I_C_Element.COLUMNNAME_ElementType, "A") // Account
				.addOnlyActiveRecordsFilter()
				.orderBy().addColumnAscending(I_C_Element.COLUMNNAME_C_Element_ID).endOrderBy()
				.create()
				.first(I_C_Element.class);

		if (element == null)
		{
			throw new AdempiereException("No active C_Element with ElementType='A' found for client " + clientId);
		}

		return element;
	}
}
