/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.banking.importfile.log;

import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.banking.model.I_C_BankStatement_Import_File_Log;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BankStatementImportFileLogRepository
{
	public void insertLogs(@NonNull final List<BankStatementImportFileRequestLog> logEntries)
	{
		if (logEntries.isEmpty())
		{
			return;
		}

		final String sql = "INSERT INTO " + I_C_BankStatement_Import_File_Log.Table_Name + "("
				+ I_C_BankStatement_Import_File_Log.COLUMNNAME_AD_Client_ID + ","  // 1
				+ I_C_BankStatement_Import_File_Log.COLUMNNAME_AD_Org_ID + "," // 2
				+ I_C_BankStatement_Import_File_Log.COLUMNNAME_Created + "," // 3
				+ I_C_BankStatement_Import_File_Log.COLUMNNAME_CreatedBy + "," // 4
				+ I_C_BankStatement_Import_File_Log.COLUMNNAME_IsActive + "," // 5
				+ I_C_BankStatement_Import_File_Log.COLUMNNAME_Logmessage + "," // 6
				+ I_C_BankStatement_Import_File_Log.COLUMNNAME_Updated + "," // 7
				+ I_C_BankStatement_Import_File_Log.COLUMNNAME_UpdatedBy + ","  // 8
				+ I_C_BankStatement_Import_File_Log.COLUMNNAME_AD_Issue_ID + ","  // 9
				+ I_C_BankStatement_Import_File_Log.COLUMNNAME_C_BankStatement_Import_File_ID + "," // 10
				+ I_C_BankStatement_Import_File_Log.COLUMNNAME_C_BankStatement_Import_File_Log_ID // 11
				+ ")"
				+ " VALUES ("
				+ "?," // 1 - AD_Client_ID
				+ "?," // 2 - AD_Org_ID
				+ "?," // 3 - Created
				+ "?," // 4 - CreatedBy
				+ "'Y'," // 5 - IsActive
				+ "?," // 6 - LogMessage
				+ "?," // 7 - Updated
				+ "?," // 8 - UpdatedBy
				+ "?," // 9 - AD_Issue_ID
				+ "?," // 10 - C_BankStatement_Import_File_ID
				+ DB.TO_TABLESEQUENCE_NEXTVAL(I_C_BankStatement_Import_File_Log.Table_Name) // 11 - C_BankStatement_Import_File_Log_ID
				+ ")";

		PreparedStatement pstmt = null;
		try
		{
			// NOTE: always create the logs out of transaction because we want them to be persisted even if the api processing trx fails
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);

			for (final BankStatementImportFileRequestLog logEntry : logEntries)
			{
				DB.setParameters(pstmt,
								 logEntry.getClientId(), // 1 - AD_Client_ID
								 OrgId.ANY, // 2 - AD_Org_ID
								 logEntry.getTimestamp(), // 3 - Created
								 logEntry.getUserId(), // 4 - CreatedBy
								 // + "'Y'," // 5 - IsActive
								 logEntry.getMessage(), // 6 - LogMessage
								 logEntry.getTimestamp(), // 7 - Updated
								 logEntry.getUserId(), // 8 - UpdatedBy
								 logEntry.getAdIssueId(), // 9 - AD_Issue_ID
								 logEntry.getBankStatementImportFileId() // 10 - C_BankStatement_Import_File_ID
				);
				pstmt.addBatch();
			}

			pstmt.executeBatch();
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql);
		}
		finally
		{
			DB.close(pstmt);
		}
	}
}
