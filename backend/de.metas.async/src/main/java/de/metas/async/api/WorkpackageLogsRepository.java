package de.metas.async.api;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

import de.metas.async.QueueWorkPackageId;
import de.metas.async.model.I_C_Queue_WorkPackage_Log;
import de.metas.organization.OrgId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Repository
public class WorkpackageLogsRepository implements IWorkpackageLogsRepository
{
	@Override
	public void saveLogs(@NonNull final List<WorkpackageLogEntry> logEntries)
	{
		if (logEntries.isEmpty())
		{
			return;
		}

		final String sql = "INSERT INTO " + I_C_Queue_WorkPackage_Log.Table_Name + "("
				+ I_C_Queue_WorkPackage_Log.COLUMNNAME_AD_Client_ID + ","  // 1
				+ I_C_Queue_WorkPackage_Log.COLUMNNAME_AD_Org_ID + "," // 2
				+ I_C_Queue_WorkPackage_Log.COLUMNNAME_C_Queue_WorkPackage_ID + "," // 3
				+ I_C_Queue_WorkPackage_Log.COLUMNNAME_C_Queue_WorkPackage_Log_ID + "," // 4
				+ I_C_Queue_WorkPackage_Log.COLUMNNAME_Created + "," // 5
				+ I_C_Queue_WorkPackage_Log.COLUMNNAME_CreatedBy + "," // 6
				+ I_C_Queue_WorkPackage_Log.COLUMNNAME_IsActive + "," // 7
				+ I_C_Queue_WorkPackage_Log.COLUMNNAME_MsgText + "," // 8
				+ I_C_Queue_WorkPackage_Log.COLUMNNAME_Updated + "," // 9
				+ I_C_Queue_WorkPackage_Log.COLUMNNAME_UpdatedBy + ","  // 10
				+ I_C_Queue_WorkPackage_Log.COLUMNNAME_AD_Issue_ID  // 11
				+ ")"
				+ " VALUES ("
				+ "?," // 1 - AD_Client_ID
				+ "?," // 2 - AD_Org_ID
				+ "?," // 3 - C_Queue_WorkPackage_ID
				+ DB.TO_TABLESEQUENCE_NEXTVAL(I_C_Queue_WorkPackage_Log.Table_Name) + "," // 4 - C_Queue_WorkPackage_Log_ID
				+ "?," // 5 - Created
				+ "?," // 6 - CreatedBy
				+ "'Y'," // 7 - IsActive
				+ "?," // 8 - MsgText
				+ "?," // 9 - Updated
				+ "?," // 10 - UpdatedBy
				+ "?" // 11 - AD_Issue_ID
				+ ")";

		PreparedStatement pstmt = null;
		try
		{
			// NOTE: always create the logs out of transaction because we want them to be persisted even if the workpackage processing fails
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);

			for (final WorkpackageLogEntry logEntry : logEntries)
			{
				DB.setParameters(pstmt, new Object[] {
						logEntry.getAdClientId(), // 1 - AD_Client_ID
						OrgId.ANY, // 2 - AD_Org_ID
						logEntry.getWorkpackageId(), // 3 - C_Queue_WorkPackage_ID
						// + DB.TO_TABLESEQUENCE_NEXTVAL(I_C_Queue_WorkPackage_Log.Table_Name) + "," // 4 - C_Queue_WorkPackage_Log_ID
						logEntry.getTimestamp(), // 5 - Created
						logEntry.getUserId(), // 6 - CreatedBy
						// + "'Y'," // 7 - IsActive
						logEntry.getMessage(), // 8 - MsgText
						logEntry.getTimestamp(), // 9 - Updated
						logEntry.getUserId(), // 10 - UpdatedBy
						logEntry.getAdIssueId(), // 11 - AD_Issue_ID
				});
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

	@Override
	public void deleteLogsInTrx(@NonNull final QueueWorkPackageId workpackageId)
	{
		final String sql = "DELETE FROM " + I_C_Queue_WorkPackage_Log.Table_Name + " WHERE " + I_C_Queue_WorkPackage_Log.COLUMNNAME_C_Queue_WorkPackage_ID + "=?";
		DB.executeUpdateAndThrowExceptionOnFail(sql, new Object[] { workpackageId }, ITrx.TRXNAME_ThreadInherited);
	}
}
