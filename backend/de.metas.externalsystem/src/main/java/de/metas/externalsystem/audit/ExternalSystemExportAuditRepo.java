/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.audit;

import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.model.I_ExternalSystem_ExportAudit;
import de.metas.process.PInstanceId;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ExternalSystemExportAuditRepo
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public Optional<ExternalSystemExportAudit> getMostRecentByTableReferenceAndSystem(
			@NonNull final TableRecordReference tableRecordReference,
			@NonNull final ExternalSystemType externalSystemType)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_ExportAudit.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_ExportAudit.COLUMNNAME_Record_ID, tableRecordReference.getRecord_ID())
				.addEqualsFilter(I_ExternalSystem_ExportAudit.COLUMNNAME_AD_Table_ID, tableRecordReference.getAD_Table_ID())
				.addEqualsFilter(I_ExternalSystem_ExportAudit.COLUMNNAME_ExternalSystemType, externalSystemType)
				.orderByDescending(I_ExternalSystem_ExportAudit.COLUMN_ExportTime)
				.create()
				.firstOptional(I_ExternalSystem_ExportAudit.class)
				.map(this::recordToModel);
	}

	@NonNull
	public ExternalSystemExportAudit createESExportAudit(@NonNull final CreateExportAuditRequest request)
	{
		final I_ExternalSystem_ExportAudit record = InterfaceWrapperHelper.newInstance(I_ExternalSystem_ExportAudit.class);

		record.setRecord_ID(request.getTableRecordReference().getRecord_ID());
		record.setAD_Table_ID(request.getTableRecordReference().getAD_Table_ID());
		record.setExportTime(TimeUtil.asTimestamp(request.getExportTime()));
		record.setExportUser_ID(request.getExportUserId().getRepoId());
		record.setExportRole_ID(request.getExportRoleId().getRepoId());
		record.setExportParameters(request.getExportParameters());

		if (request.getPInstanceId() != null)
		{
			record.setAD_PInstance_ID(request.getPInstanceId().getRepoId());
		}

		record.setExternalSystemType(request.getExternalSystemType() != null
											 ? request.getExternalSystemType().getCode()
											 : null);

		InterfaceWrapperHelper.saveRecord(record);

		return recordToModel(record);
	}

	@NonNull
	private ExternalSystemExportAudit recordToModel(@NonNull final I_ExternalSystem_ExportAudit exportAudit)
	{
		return ExternalSystemExportAudit.builder()
				.externalSystemExportAuditId(ExternalSystemExportAuditId.ofRepoId(exportAudit.getExternalSystem_ExportAudit_ID()))
				.pInstanceId(PInstanceId.ofRepoIdOrNull(exportAudit.getAD_PInstance_ID()))
				.exportRoleId(RoleId.ofRepoId(exportAudit.getExportRole_ID()))
				.exportUserId(UserId.ofRepoId(exportAudit.getExportUser_ID()))
				.tableRecordReference(TableRecordReference.of(exportAudit.getAD_Table_ID(), exportAudit.getRecord_ID()))
				.exportTime(TimeUtil.asInstant(exportAudit.getExportTime()))
				.exportParameters(exportAudit.getExportParameters())
				.externalSystemType(ExternalSystemType.ofNullableCode(exportAudit.getExternalSystemType()))
				.build();
	}
}
