/*
 * #%L
 * metasfresh-material-dispo-commons
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

package de.metas.material.dispo.commons.process;

import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.PInstanceId;
import de.metas.util.Loggables;
import org.compiere.util.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MD_Candidate_Rebuild extends JavaProcess
{
	@Param(parameterName = "IsBackupBeforeDelete", mandatory = true)
	private boolean isBackupBeforeDelete;

	@Override
	protected String doIt() throws Exception
	{
		final int adPInstanceId = PInstanceId.toRepoId(getPinstanceId());
		final String backupFlag = isBackupBeforeDelete ? "Y" : "N";

		Loggables.get().addLog("Starting MD_Candidate_Rebuild (AD_PInstance_ID={}, Backup={})", adPInstanceId, backupFlag);

		final String sql = "SELECT action, business_case, record_count FROM de_metas_material.MD_Candidate_Rebuild(?, ?)";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, adPInstanceId);
			pstmt.setString(2, backupFlag);
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				final String action = rs.getString("action");
				final String businessCase = rs.getString("business_case");
				final long recordCount = rs.getLong("record_count");
				Loggables.get().addLog("{}: {} = {}", action, businessCase, recordCount);
			}
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		Loggables.get().addLog("MD_Candidate_Rebuild completed successfully.");
		return MSG_OK;
	}
}
