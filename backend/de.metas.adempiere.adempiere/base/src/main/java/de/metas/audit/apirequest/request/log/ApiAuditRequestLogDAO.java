/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.audit.apirequest.request.log;

import de.metas.audit.apirequest.request.ApiRequestAuditId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.model.I_API_Request_Audit_Log;
import org.compiere.util.DB;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class ApiAuditRequestLogDAO
{
	public void insertLogs(@NonNull final List<ApiRequestAuditLog> logEntries)
	{
		if (logEntries.isEmpty())
		{
			return;
		}

		final String sql = "INSERT INTO " + I_API_Request_Audit_Log.Table_Name + "("
				+ I_API_Request_Audit_Log.COLUMNNAME_AD_Client_ID + ","  // 1
				+ I_API_Request_Audit_Log.COLUMNNAME_AD_Org_ID + "," // 2
				+ I_API_Request_Audit_Log.COLUMNNAME_API_Request_Audit_ID + "," // 3
				+ I_API_Request_Audit_Log.COLUMNNAME_API_Request_Audit_Log_ID + "," // 4
				+ I_API_Request_Audit_Log.COLUMNNAME_Created + "," // 5
				+ I_API_Request_Audit_Log.COLUMNNAME_CreatedBy + "," // 6
				+ I_API_Request_Audit_Log.COLUMNNAME_IsActive + "," // 7
				+ I_API_Request_Audit_Log.COLUMNNAME_Logmessage + "," // 8
				+ I_API_Request_Audit_Log.COLUMNNAME_Updated + "," // 9
				+ I_API_Request_Audit_Log.COLUMNNAME_UpdatedBy + ","  // 10
				+ I_API_Request_Audit_Log.COLUMNNAME_AD_Issue_ID + ","  // 11
				+ I_API_Request_Audit_Log.COLUMNNAME_AD_Table_ID + "," // 12
				+ I_API_Request_Audit_Log.COLUMNNAME_Record_ID + "," // 13
				+ I_API_Request_Audit_Log.COLUMNNAME_Type + "," // 14
				+ I_API_Request_Audit_Log.COLUMNNAME_TrxName // 15
				+ ")"
				+ " VALUES ("
				+ "?," // 1 - AD_Client_ID
				+ "?," // 2 - AD_Org_ID
				+ "?," // 3 - API_Request_Audit_ID
				+ DB.TO_TABLESEQUENCE_NEXTVAL(I_API_Request_Audit_Log.Table_Name) + "," // 4 - API_Request_Audit_Log_ID
				+ "?," // 5 - Created
				+ "?," // 6 - CreatedBy
				+ "'Y'," // 7 - IsActive
				+ "?," // 8 - LogMessage
				+ "?," // 9 - Updated
				+ "?," // 10 - UpdatedBy
				+ "?," // 11 - AD_Issue_ID
				+ "?," // 12 - AD_Table_ID
				+ "?," // 13 - Record_ID
				+ "?," // 14 - Type
				+ "?" // 15 - TrxName
				+ ")";

		PreparedStatement pstmt = null;
		try
		{
			// NOTE: always create the logs out of transaction because we want them to be persisted even if the api processing trx fails
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);

			for (final ApiRequestAuditLog logEntry : logEntries)
			{
				DB.setParameters(pstmt,
								 logEntry.getAdClientId(), // 1 - AD_Client_ID
								 OrgId.ANY, // 2 - AD_Org_ID
								 logEntry.getApiRequestAuditId(), // 3 - API_Request_Audit_ID
								 // + DB.TO_TABLESEQUENCE_NEXTVAL(I_API_Request_Audit_Log.Table_Name) + "," // 4 - API_Request_Audit_Log_ID
								 logEntry.getTimestamp(), // 5 - Created
								 logEntry.getUserId(), // 6 - CreatedBy
								 // + "'Y'," // 7 - IsActive
								 logEntry.getMessage(), // 8 - LogMessage
								 logEntry.getTimestamp(), // 9 - Updated
								 logEntry.getUserId(), // 10 - UpdatedBy
								 logEntry.getAdIssueId(), // 11 - AD_Issue_ID
								 logEntry.getRecordReference() != null ? logEntry.getRecordReference().getAD_Table_ID() : null, // 12 - AD_Table_ID
								 logEntry.getRecordReference() != null ? logEntry.getRecordReference().getRecord_ID() : null, // 13 - Record_ID
								 logEntry.getType(), // 14 - Type
								 logEntry.getTrxName() // 15 - TrxName
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

	public void deleteLogsByApiRequestId(@NonNull final ApiRequestAuditId apiRequestAuditId)
	{

		final String sql =
				"DELETE FROM " + I_API_Request_Audit_Log.Table_Name
				+ " WHERE " + I_API_Request_Audit_Log.COLUMNNAME_API_Request_Audit_ID + " = ?";

		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);

			DB.setParameters(pstmt,apiRequestAuditId.getRepoId());

			pstmt.execute();
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
