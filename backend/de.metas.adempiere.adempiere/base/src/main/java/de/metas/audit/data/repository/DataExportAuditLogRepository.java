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

package de.metas.audit.data.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.audit.data.Action;
import de.metas.audit.data.ExternalSystemParentConfigId;
import de.metas.audit.data.model.CreateDataExportAuditLogRequest;
import de.metas.audit.data.model.DataExportAuditId;
import de.metas.audit.data.model.DataExportAuditLog;
import de.metas.audit.data.model.DataExportAuditLogId;
import de.metas.process.PInstanceId;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Data_Export_Audit_Log;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Note that so far we didn't have a use-case of loading an {@link de.metas.audit.data.model.DataExportAudit} together with (all) its audit-logs.
 * We rather just need the logs stand-alone. Hence this stand-alone repo.
 */
@Repository
public class DataExportAuditLogRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public DataExportAuditLog createNew(@NonNull final CreateDataExportAuditLogRequest logEntry)
	{
		final I_Data_Export_Audit_Log record = InterfaceWrapperHelper.newInstance(I_Data_Export_Audit_Log.class);

		record.setData_Export_Audit_ID(logEntry.getDataExportAuditId().getRepoId());
		record.setData_Export_Action(logEntry.getAction().getCode());
		record.setExternalSystem_Config_ID(NumberUtils.asInteger(logEntry.getExternalSystemConfigId(), -1));
		record.setAD_PInstance_ID(NumberUtils.asInteger(logEntry.getAdPInstanceId(), -1));

		saveRecord(record);

		return toDataExportAuditLog(record);
	}

	@NonNull
	public ImmutableSet<Integer> getExternalSystemConfigIds(@NonNull final DataExportAuditId dataExportAuditId)
	{
		return queryBL.createQueryBuilder(I_Data_Export_Audit_Log.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_Data_Export_Audit_Log.COLUMNNAME_Data_Export_Audit_ID, dataExportAuditId.getRepoId())
				.create()
				.listDistinct(I_Data_Export_Audit_Log.COLUMNNAME_ExternalSystem_Config_ID, Integer.class)
				.stream()
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public ImmutableList<DataExportAuditLog> getByDataExportAuditId(@NonNull final DataExportAuditId dataExportAuditId)
	{
		return queryBL.createQueryBuilder(I_Data_Export_Audit_Log.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_Data_Export_Audit_Log.COLUMNNAME_Data_Export_Audit_ID, dataExportAuditId.getRepoId())
				.create()
				.stream()
				.map(DataExportAuditLogRepository::toDataExportAuditLog)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private static DataExportAuditLog toDataExportAuditLog(@NonNull final I_Data_Export_Audit_Log record)
	{
		return DataExportAuditLog.builder()
				.dataExportAuditLogId(DataExportAuditLogId.ofRepoId(DataExportAuditId.ofRepoId(record.getData_Export_Audit_ID()), record.getData_Export_Audit_Log_ID()))
				.action(Action.ofCode(record.getData_Export_Action()))
				.externalSystemConfigId(ExternalSystemParentConfigId.ofRepoIdOrNull(record.getExternalSystem_Config_ID()))
				.adPInstanceId(PInstanceId.ofRepoIdOrNull(record.getAD_PInstance_ID()))
				.build();
	}
}
