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

package de.metas.bpartner.impexp.blockstatus;

import de.metas.bpartner.blockstatus.BlockStatus;
import de.metas.impexp.processing.ImportRecordsSelection;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.trx.api.ITrx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_BPartner_BlockStatus;
import org.compiere.util.DB;

import static org.compiere.model.I_C_BP_Customer_Acct.COLUMNNAME_AD_Org_ID;
import static org.compiere.model.I_I_BPartner_BlockStatus.COLUMNNAME_Action;
import static org.compiere.model.I_I_BPartner_BlockStatus.COLUMNNAME_BlockStatus;
import static org.compiere.model.I_I_BPartner_BlockStatus.COLUMNNAME_I_ErrorMsg;
import static org.compiere.model.I_I_BPartner_BlockStatus.COLUMNNAME_I_IsImported;
import static org.compiere.model.I_I_BPartner_BlockStatus.COLUMNNAME_SAP_BPartnerCode;

@UtilityClass
public class BPartnerBlockStatusImportTableSqlUpdater
{
	private static final Logger logger = LogManager.getLogger(BPartnerBlockStatusImportTableSqlUpdater.class);

	public void updateAndValidateBPBlockStatusImportTable(@NonNull final ImportRecordsSelection selection)
	{
		updateBlockStatus(selection);

		validateSAPBPartnerCodeAndUpdateErrorMessageIfNeeded(selection);
		validateBlockActionAndUpdateErrorMessageIfNeeded(selection);
	}

	public int countRecordsWithErrors(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "SELECT COUNT(1) FROM " + I_I_BPartner_BlockStatus.Table_Name
				+ " WHERE I_IsImported='E' "
				+ selection.toSqlWhereClause();
		return DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql);
	}

	private void updateBlockStatus(@NonNull final ImportRecordsSelection selection)
	{
		final String sql = "UPDATE " + I_I_BPartner_BlockStatus.Table_Name + " i "
				+ " SET " + COLUMNNAME_BlockStatus + " = "
				+ " CASE "
				+ " 	WHEN i." + COLUMNNAME_Action + " = 'Block'"
				+ "     	THEN '" + BlockStatus.Blocked.getCode() + "'"
				+ "     WHEN i." + COLUMNNAME_Action + " = 'Unblock'"
				+ "         THEN '" + BlockStatus.Unblocked.getCode() + "'"
				+ "     ELSE NULL"
				+ " END "
				+ " WHERE i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");

		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);

		logger.debug(COLUMNNAME_BlockStatus + " updated for {} records!", no);
	}

	private void validateSAPBPartnerCodeAndUpdateErrorMessageIfNeeded(@NonNull final ImportRecordsSelection selection)
	{
		final String sqlNoOfBPartnersFoundForProvidedSAPBPartnerCode = "SELECT count(*)"
				+ " FROM " + I_C_BPartner.Table_Name + " bp"
				+ " WHERE bp." + COLUMNNAME_SAP_BPartnerCode + " = i." + COLUMNNAME_SAP_BPartnerCode
				+ " AND bp." + COLUMNNAME_AD_Org_ID + " IN (i." + COLUMNNAME_AD_Org_ID + ", 0)";

		final String sql = "UPDATE " + I_I_BPartner_BlockStatus.Table_Name + " i "
				+ " SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + " = " + COLUMNNAME_I_ErrorMsg + "||'ERR = Provided " + COLUMNNAME_SAP_BPartnerCode + " did not match any partner!,'"
				+ " WHERE (" + sqlNoOfBPartnersFoundForProvidedSAPBPartnerCode + ") <= 0"
				+ " AND i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");

		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.warn("No BPartner found by " + COLUMNNAME_SAP_BPartnerCode + " for {} rows", no);
		}
	}

	private void validateBlockActionAndUpdateErrorMessageIfNeeded(@NonNull final ImportRecordsSelection selection)
	{
		final String sqlUpdateImportTable = "UPDATE " + I_I_BPartner_BlockStatus.Table_Name + " i "
				+ " SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + " = " + COLUMNNAME_I_ErrorMsg + "||'ERR = Provided " + COLUMNNAME_Action + " not supported!,'"
				+ " WHERE i." + COLUMNNAME_BlockStatus + " IS NULL "
				+ " AND i." + COLUMNNAME_I_IsImported + "<>'Y'"
				+ selection.toSqlWhereClause("i");

		final int no = DB.executeUpdateAndThrowExceptionOnFail(sqlUpdateImportTable, ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.warn("No " + COLUMNNAME_BlockStatus + " found for {} rows!", no);
		}
	}
}
