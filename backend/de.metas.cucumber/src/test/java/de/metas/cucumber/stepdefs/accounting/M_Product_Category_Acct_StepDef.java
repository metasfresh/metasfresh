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

package de.metas.cucumber.stepdefs.accounting;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.IAccountBL;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.productCategory.M_Product_Category_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Product_Category_Acct;
import org.compiere.model.MAccount;
import org.compiere.util.Env;

/**
 * Step definitions for overriding the revenue/expense accounts a {@code M_Product_Category}
 * posts to, on top of its accounting-schema-auto-provisioned {@code M_Product_Category_Acct} row.
 */
@RequiredArgsConstructor
public class M_Product_Category_Acct_StepDef
{
	private final M_Product_Category_StepDefData productCategoryTable;
	private final C_ElementValue_StepDefData elementValueTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	private final IAccountBL accountBL = Services.get(IAccountBL.class);

	/**
	 * Overrides the {@code P_Revenue_Acct} / {@code P_Expense_Acct} of a product category's
	 * auto-provisioned {@code M_Product_Category_Acct} row (created for the client's primary
	 * accounting schema as soon as the category exists) with the given {@code C_ElementValue}
	 * (GL account) records — so a category can post its own, distinct accounts instead of the
	 * schema's default ones.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_Category_ID</b> — (required, identifier-ref) the category to override<br>
	 *   <b>OPT.P_Revenue_Acct</b> — (optional, identifier-ref to a {@code C_ElementValue}) overrides the revenue account<br>
	 *   <b>OPT.P_Expense_Acct</b> — (optional, identifier-ref to a {@code C_ElementValue}) overrides the expense account<br>
	 * @cucumber.depends StepDefData: M_Product_Category_StepDefData, C_ElementValue_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains M_Product_Category_Acct overrides:
	 *   | M_Product_Category_ID | OPT.P_Revenue_Acct  | OPT.P_Expense_Acct  |
	 *   | discountCategory      | discountRevenueAcct | discountExpenseAcct |
	 * </pre>
	 */
	@And("metasfresh contains M_Product_Category_Acct overrides:")
	public void overrideProductCategoryAcct(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_M_Product_Category category = row.getAsIdentifier("M_Product_Category_ID")
					.lookupNotNullIn(productCategoryTable);

			final AcctSchema acctSchema = acctSchemaDAO.getByClientAndOrg(Env.getCtx());

			final I_M_Product_Category_Acct acctRecord = queryBL.createQueryBuilder(I_M_Product_Category_Acct.class)
					.addEqualsFilter(I_M_Product_Category_Acct.COLUMNNAME_M_Product_Category_ID, category.getM_Product_Category_ID())
					.addEqualsFilter(I_M_Product_Category_Acct.COLUMNNAME_C_AcctSchema_ID, acctSchema.getId().getRepoId())
					.create()
					.firstOnlyNotNull(I_M_Product_Category_Acct.class);

			row.getAsOptionalIdentifier("P_Revenue_Acct")
					.ifPresent(identifier -> acctRecord.setP_Revenue_Acct(resolveValidCombinationId(identifier, acctSchema)));
			row.getAsOptionalIdentifier("P_Expense_Acct")
					.ifPresent(identifier -> acctRecord.setP_Expense_Acct(resolveValidCombinationId(identifier, acctSchema)));

			InterfaceWrapperHelper.saveRecord(acctRecord);
		});
	}

	private int resolveValidCombinationId(@NonNull final StepDefDataIdentifier identifier, @NonNull final AcctSchema acctSchema)
	{
		final I_C_ElementValue elementValue = identifier.lookupNotNullIn(elementValueTable);
		final AccountDimension dimension = accountBL.createAccountDimension(elementValue, acctSchema.getId());
		return MAccount.get(Env.getCtx(), dimension).getC_ValidCombination_ID();
	}
}
