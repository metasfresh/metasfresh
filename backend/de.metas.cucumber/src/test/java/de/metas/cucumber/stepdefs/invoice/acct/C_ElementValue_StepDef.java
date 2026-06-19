package de.metas.cucumber.stepdefs.invoice.acct;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Element;
import org.compiere.model.I_C_ElementValue;
import org.compiere.util.Env;

import java.util.List;

/**
 * Step definitions for creating and registering {@link I_C_ElementValue} (GL account) records.
 * Used to set up override GL accounts in cucumber accounting tests.
 */
@RequiredArgsConstructor
public class C_ElementValue_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_ElementValue_StepDefData elementValueTable;

	/**
	 * Creates or looks up a {@code C_ElementValue} (GL account) record by {@code Value},
	 * anchored to the first active {@code C_Element} (chart of accounts) for the current client.
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
						.orElse("TEST_ACCOUNT_" + System.currentTimeMillis()));

		// Check if already exists
		final List<I_C_ElementValue> existing = queryBL.createQueryBuilder(I_C_ElementValue.class)
				.addEqualsFilter(I_C_ElementValue.COLUMNNAME_Value, value)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		if (!existing.isEmpty())
		{
			return existing.get(0);
		}

		final String name = row.getAsOptionalString("Name").orElse(value);

		// Find the first C_Element for the current client
		final I_C_Element element = findDefaultElement();

		final I_C_ElementValue record = InterfaceWrapperHelper.newInstance(I_C_ElementValue.class);
		record.setAD_Org_ID(0);
		record.setValue(value);
		record.setName(name);
		record.setC_Element_ID(element.getC_Element_ID());
		record.setAccountType("E"); // Expense — suitable for P_Expense_Acct overrides
		record.setAccountSign("N"); // Natural
		record.setIsActive(true);
		record.setIsSummary(false);
		InterfaceWrapperHelper.save(record);

		return record;
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
