/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.charge;

import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.ChartOfAccountsId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.costing.ChargeId;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.cucumber.stepdefs.tax.C_TaxCategory_StepDefData;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueService;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Charge;
import org.compiere.model.I_C_ChargeType;
import org.compiere.model.I_C_Charge_Acct;
import org.compiere.model.MAccount;
import org.compiere.util.Env;

/**
 * Step definitions for {@code C_Charge}, {@code C_ChargeType} master data and the {@code C_Charge_Acct}
 * per-schema account assignment (charge expense/revenue GL accounts).
 */
@RequiredArgsConstructor
public class C_Charge_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	@NonNull private final ElementValueService elementValueService = SpringContextHolder.instance.getBean(ElementValueService.class);

	private final C_Charge_StepDefData chargeTable;
	private final C_ChargeType_StepDefData chargeTypeTable;
	private final C_TaxCategory_StepDefData taxCategoryTable;

	/**
	 * Creates one {@link I_C_ChargeType} record per data-table row.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>Value</b> — (optional) search key; auto-generated from identifier if absent<br>
	 *   <b>Name</b> — (optional) name; auto-generated from identifier if absent<br>
	 * @cucumber.depends C_ChargeType_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_ChargeType:
	 *   | Identifier  |
	 *   | chargeType1 |
	 * </pre>
	 */
	@And("metasfresh contains C_ChargeType:")
	public void createC_ChargeTypes(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createChargeType);
	}

	private void createChargeType(@NonNull final DataTableRow row)
	{
		final ValueAndName valueAndName = row.suggestValueAndName();
		final String value = row.getAsOptionalString(I_C_ChargeType.COLUMNNAME_Value).orElseGet(valueAndName::getValue);
		final String name = row.getAsOptionalString(I_C_ChargeType.COLUMNNAME_Name).orElseGet(valueAndName::getName);

		final I_C_ChargeType chargeTypeRecord = InterfaceWrapperHelper.newInstance(I_C_ChargeType.class);
		chargeTypeRecord.setValue(value);
		chargeTypeRecord.setName(name);
		InterfaceWrapperHelper.saveRecord(chargeTypeRecord);

		row.getAsOptionalIdentifier().ifPresent(identifier -> chargeTypeTable.put(identifier, chargeTypeRecord));
	}

	/**
	 * Creates one {@link I_C_Charge} record per data-table row.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>Name</b> — (optional) name; auto-generated from identifier if absent<br>
	 *   <b>C_ChargeType_ID</b> — (required, identifier-ref) the charge's type<br>
	 *   <b>OPT.C_TaxCategory_ID</b> — (optional, identifier-ref) tax category<br>
	 * @cucumber.depends C_Charge_StepDefData, C_ChargeType_StepDefData, C_TaxCategory_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_Charge:
	 *   | Identifier | Name                     | C_ChargeType_ID.Identifier |
	 *   | charge1    | Cash Withdrawal Charge 1 | chargeType1                |
	 * </pre>
	 */
	@And("metasfresh contains C_Charge:")
	public void createC_Charges(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createCharge);
	}

	private void createCharge(@NonNull final DataTableRow row)
	{
		final ValueAndName valueAndName = row.suggestValueAndName();
		final String name = row.getAsOptionalString(I_C_Charge.COLUMNNAME_Name).orElseGet(valueAndName::getName);

		final I_C_ChargeType chargeType = row.getAsIdentifier(I_C_Charge.COLUMNNAME_C_ChargeType_ID).lookupNotNullIn(chargeTypeTable);

		final I_C_Charge chargeRecord = InterfaceWrapperHelper.newInstance(I_C_Charge.class);
		chargeRecord.setName(name);
		chargeRecord.setC_ChargeType_ID(chargeType.getC_ChargeType_ID());

		row.getAsOptionalIdentifier(I_C_Charge.COLUMNNAME_C_TaxCategory_ID)
				.map(identifier -> identifier.lookupNotNullIdIn(taxCategoryTable))
				.map(TaxCategoryId::getRepoId)
				.ifPresent(chargeRecord::setC_TaxCategory_ID);

		InterfaceWrapperHelper.saveRecord(chargeRecord);

		row.getAsOptionalIdentifier().ifPresent(identifier -> chargeTable.put(identifier, chargeRecord));
	}

	/**
	 * Sets the expense and revenue GL accounts of an existing {@code C_Charge_Acct} row (materialized
	 * automatically, with empty accounts, when the {@code C_Charge} was created) for the current
	 * client's default accounting schema. The accounts are given by {@code C_ElementValue.Value} and
	 * resolved (get-or-create the natural-account {@code C_ValidCombination}) against that schema's
	 * chart of accounts.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Charge_ID</b> — (required, identifier-ref) the charge whose accounts are set<br>
	 *   <b>Ch_Expense_Acct</b> — (required) {@code C_ElementValue.Value} of the expense account (DR side)<br>
	 *   <b>Ch_Revenue_Acct</b> — (required) {@code C_ElementValue.Value} of the revenue account (CR side)<br>
	 * @cucumber.depends C_Charge_StepDefData; the {@code C_ElementValue} rows named by
	 *   {@code Ch_Expense_Acct}/{@code Ch_Revenue_Acct} must already exist (e.g. via
	 *   {@code metasfresh contains C_ElementValues:})
	 * @cucumber.example
	 * <pre>
	 * And C_Charge_Acct is set for:
	 *   | C_Charge_ID.Identifier | Ch_Expense_Acct | Ch_Revenue_Acct |
	 *   | charge1                | 600100          | 400100          |
	 * </pre>
	 */
	@And("C_Charge_Acct is set for:")
	public void setC_Charge_Acct(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::setChargeAcct);
	}

	private void setChargeAcct(@NonNull final DataTableRow row)
	{
		final ChargeId chargeId = row.getAsIdentifier(I_C_Charge_Acct.COLUMNNAME_C_Charge_ID).lookupNotNullIdIn(chargeTable);

		final AcctSchema acctSchema = acctSchemaDAO.getByClientAndOrg(Env.getCtx());
		final AcctSchemaId acctSchemaId = acctSchema.getId();
		final ChartOfAccountsId chartOfAccountsId = acctSchema.getChartOfAccountsId();

		final AccountId expenseAccountId = resolveAccountId(row.getAsString(I_C_Charge_Acct.COLUMNNAME_Ch_Expense_Acct), chartOfAccountsId, acctSchemaId);
		final AccountId revenueAccountId = resolveAccountId(row.getAsString(I_C_Charge_Acct.COLUMNNAME_Ch_Revenue_Acct), chartOfAccountsId, acctSchemaId);

		final I_C_Charge_Acct chargeAcctRecord = queryBL.createQueryBuilder(I_C_Charge_Acct.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Charge_Acct.COLUMNNAME_C_Charge_ID, chargeId)
				.addEqualsFilter(I_C_Charge_Acct.COLUMNNAME_C_AcctSchema_ID, acctSchemaId)
				.create()
				.firstOnlyOrNull(I_C_Charge_Acct.class);

		final I_C_Charge_Acct recordToSave;
		if (chargeAcctRecord != null)
		{
			recordToSave = chargeAcctRecord;
		}
		else
		{
			// The C_Charge_Acct row is normally auto-materialized (with empty accounts) when the
			// C_Charge is created; create it here as a fallback in case that materialization is absent.
			recordToSave = InterfaceWrapperHelper.newInstance(I_C_Charge_Acct.class);
			recordToSave.setC_Charge_ID(chargeId.getRepoId());
			recordToSave.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		}

		recordToSave.setCh_Expense_Acct(expenseAccountId.getRepoId());
		recordToSave.setCh_Revenue_Acct(revenueAccountId.getRepoId());
		InterfaceWrapperHelper.saveRecord(recordToSave);
	}

	private AccountId resolveAccountId(
			@NonNull final String elementValueValue,
			@NonNull final ChartOfAccountsId chartOfAccountsId,
			@NonNull final AcctSchemaId acctSchemaId)
	{
		final ElementValueId elementValueId = elementValueService.getByAccountNo(elementValueValue, chartOfAccountsId)
				.map(ElementValue::getId)
				.orElseThrow(() -> new AdempiereException("No C_ElementValue found for Value=" + elementValueValue + " in " + chartOfAccountsId));

		final AccountDimension dimension = AccountDimension.builder()
				.setAcctSchemaId(acctSchemaId)
				.setAD_Client_ID(Env.getAD_Client_ID(Env.getCtx()))
				.setAD_Org_ID(0) // natural account combination, valid for any org
				.setC_ElementValue_ID(elementValueId.getRepoId())
				.build();

		final MAccount account = MAccount.get(Env.getCtx(), dimension);
		return AccountId.ofRepoId(account.getC_ValidCombination_ID());
	}
}
