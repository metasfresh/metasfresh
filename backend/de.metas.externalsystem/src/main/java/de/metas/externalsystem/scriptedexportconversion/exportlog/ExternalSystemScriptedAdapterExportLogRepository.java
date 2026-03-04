/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.scriptedexportconversion.exportlog;

import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfigId;
import de.metas.inout.InOutId;
import de.metas.process.PInstanceId;
import lombok.NonNull;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExternalSystemScriptedAdapterExportLogRepository
{
	private static final String TABLE_NAME = "M_InOut_ExternalSystem_ScriptedAdapter_ExportLog";
	private static final String PK_COL = TABLE_NAME + "_ID";

	@NonNull
	public ExternalSystemScriptedAdapterExportLogId createPendingLogEntry(
			@NonNull final InOutId inOutId,
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@Nullable final PInstanceId pInstanceId)
	{
		final String sql = "INSERT INTO " + TABLE_NAME + " ("
				+ "AD_Client_ID, AD_Org_ID, CreatedBy, UpdatedBy, IsActive, "
				+ "M_InOut_ID, ExternalSystem_Config_ScriptedExportConversion_ID, "
				+ "ExternalSystemScriptedAdapterExportStatus, AD_PInstance_ID"
				+ ") VALUES (?, ?, ?, ?, 'Y', ?, ?, 'P', ?)"
				+ " RETURNING " + PK_COL;

		final int clientId = Env.getAD_Client_ID(Env.getCtx());
		final int orgId = Env.getAD_Org_ID(Env.getCtx());
		final int userId = Env.getAD_User_ID(Env.getCtx());

		final int id = DB.getSQLValueEx(null, sql,
				clientId, orgId, userId, userId,
				inOutId.getRepoId(), configId.getRepoId(),
				pInstanceId != null ? pInstanceId.getRepoId() : null);

		return ExternalSystemScriptedAdapterExportLogId.ofRepoId(id);
	}

	public void markSent(@NonNull final ExternalSystemScriptedAdapterExportLogId logId)
	{
		final Timestamp now = new Timestamp(System.currentTimeMillis());
		DB.executeUpdateAndThrowExceptionOnFail(
				"UPDATE " + TABLE_NAME
						+ " SET ExternalSystemScriptedAdapterExportStatus='S', SendDate=?, Updated=?, UpdatedBy=?"
						+ " WHERE " + PK_COL + "=?",
				new Object[]{ now, now, Env.getAD_User_ID(Env.getCtx()), logId.getRepoId() },
				null);
	}

	public void markError(@NonNull final ExternalSystemScriptedAdapterExportLogId logId, @NonNull final String errorMsg)
	{
		final Timestamp now = new Timestamp(System.currentTimeMillis());
		DB.executeUpdateAndThrowExceptionOnFail(
				"UPDATE " + TABLE_NAME
						+ " SET ExternalSystemScriptedAdapterExportStatus='E', ErrorMsg=?, Updated=?, UpdatedBy=?"
						+ " WHERE " + PK_COL + "=?",
				new Object[]{ errorMsg, now, Env.getAD_User_ID(Env.getCtx()), logId.getRepoId() },
				null);
	}

	@NonNull
	public ImmutableList<ExternalSystemScriptedAdapterExportLogId> getByPInstanceId(@NonNull final PInstanceId pInstanceId)
	{
		final String sql = "SELECT " + PK_COL + " FROM " + TABLE_NAME + " WHERE AD_PInstance_ID=?";
		final List<ExternalSystemScriptedAdapterExportLogId> result = new ArrayList<>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, pInstanceId.getRepoId());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				result.add(ExternalSystemScriptedAdapterExportLogId.ofRepoId(rs.getInt(1)));
			}
		}
		catch (final Exception e)
		{
			throw new org.adempiere.exceptions.DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return ImmutableList.copyOf(result);
	}
}
