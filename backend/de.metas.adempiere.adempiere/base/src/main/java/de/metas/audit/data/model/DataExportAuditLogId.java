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

package de.metas.audit.data.model;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_Data_Export_Audit_Log;

@Value
public class DataExportAuditLogId implements RepoIdAware
{
	int repoId;

	@NonNull
	DataExportAuditId dataExportAuditId;

	@NonNull
	public static DataExportAuditLogId ofRepoId(@NonNull final DataExportAuditId dataExportAuditId, final int repoId)
	{
		return new DataExportAuditLogId(dataExportAuditId, repoId);
	}

	private DataExportAuditLogId(@NonNull final DataExportAuditId dataExportAuditId, final int repoId)
	{
		this.dataExportAuditId = dataExportAuditId;
		this.repoId = Check.assumeGreaterThanZero(repoId, I_Data_Export_Audit_Log.COLUMNNAME_Data_Export_Audit_Log_ID);
	}
}
