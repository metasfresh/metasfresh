/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.banking.impexp;

import de.metas.banking.model.I_I_BP_BankAccount;
import de.metas.impexp.processing.ImportRecordsSelection;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Currency;
import org.compiere.util.DB;

@UtilityClass
public class BPBankAccountImportTableSqlUpdater
{
	public void updateAndValidateBPBankAccountImportTable(@NonNull final ImportRecordsSelection selection)
	{
		updateBPartnerID(selection);
		updateCurrencyID(selection);

		validateBPartnerId(selection);
		validateCurrencyId(selection);
	}

	public int countRecordsWithErrors(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "SELECT COUNT(1) FROM " + I_I_BP_BankAccount.Table_Name
				+ " WHERE I_IsImported='E' "
				+ selection.toSqlWhereClause();
		return DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql);
	}

	private void updateBPartnerID(@NonNull final ImportRecordsSelection selection)
	{
		final String bpartnerIdMatchingValueAndOrg = "SELECT bp." + I_C_BPartner.COLUMNNAME_C_BPartner_ID
				+ " FROM " + I_C_BPartner.Table_Name + " bp"
				+ " WHERE bp." + I_C_BPartner.COLUMNNAME_Value + " = i." + I_I_BP_BankAccount.COLUMNNAME_BPartnerValue
				+ " AND bp." + I_C_BPartner.COLUMNNAME_AD_Org_ID + " IN (i." + I_I_BP_BankAccount.COLUMNNAME_AD_Org_ID + ", 0)";

		final String sql = "UPDATE " + I_I_BP_BankAccount.Table_Name + " i "
				+ " SET " + I_I_BP_BankAccount.COLUMNNAME_C_BPartner_ID + " = (" + bpartnerIdMatchingValueAndOrg + ")"
				+ " WHERE i." + I_I_BP_BankAccount.COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void updateCurrencyID(@NonNull final ImportRecordsSelection selection)
	{
		final String currencyIdMatchingCodeAndOrg = "SELECT curr." + I_C_Currency.COLUMNNAME_C_Currency_ID
				+ " FROM " + I_C_Currency.Table_Name + " curr"
				+ " WHERE curr." + I_C_Currency.COLUMNNAME_ISO_Code + " = i." + I_I_BP_BankAccount.COLUMNNAME_CurrencyCode
				+ " AND curr." + I_C_Currency.COLUMNNAME_AD_Org_ID + " IN (i." + I_I_BP_BankAccount.COLUMNNAME_AD_Org_ID + ", 0)";

		final String sql = "UPDATE " + I_I_BP_BankAccount.Table_Name + " i "
				+ " SET " + I_I_BP_BankAccount.COLUMNNAME_C_Currency_ID + " = (" + currencyIdMatchingCodeAndOrg + ")"
				+ " WHERE i." + I_I_BP_BankAccount.COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void validateBPartnerId(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + I_I_BP_BankAccount.Table_Name + " i "
				+ " SET "
				+ I_I_BP_BankAccount.COLUMNNAME_I_IsImported + "='E', "
				+ I_I_BP_BankAccount.COLUMNNAME_I_ErrorMsg + " = " + I_I_BP_BankAccount.COLUMNNAME_I_ErrorMsg + "||'ERR = Provided " + I_I_BP_BankAccount.COLUMNNAME_BPartnerValue + " did not match any partner!,'"
				+ " WHERE " + I_I_BP_BankAccount.COLUMNNAME_C_BPartner_ID + " is null "
				+ " AND i." + I_I_BP_BankAccount.COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private void validateCurrencyId(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + I_I_BP_BankAccount.Table_Name + " i "
				+ " SET "
				+ I_I_BP_BankAccount.COLUMNNAME_I_IsImported + "='E', "
				+ I_I_BP_BankAccount.COLUMNNAME_I_ErrorMsg + " = " + I_I_BP_BankAccount.COLUMNNAME_I_ErrorMsg + "||'ERR = Provided " + I_I_BP_BankAccount.COLUMNNAME_CurrencyCode + " did not match any currency!,'"
				+ " WHERE " + I_I_BP_BankAccount.COLUMNNAME_C_Currency_ID + " is null "
				+ " AND i." + I_I_BP_BankAccount.COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");

		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}
}
