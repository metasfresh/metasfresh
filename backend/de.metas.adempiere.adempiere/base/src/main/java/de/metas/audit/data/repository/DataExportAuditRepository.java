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

import de.metas.audit.data.model.CreateDataExportAuditRequest;
import de.metas.audit.data.model.DataExportAudit;
import de.metas.audit.data.model.DataExportAuditId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_Data_Export_Audit;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class DataExportAuditRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public DataExportAudit save(@NonNull final CreateDataExportAuditRequest createDataExportAuditRequest)
	{
		final I_Data_Export_Audit record = InterfaceWrapperHelper.loadOrNew(createDataExportAuditRequest.getDataExportAuditId(), I_Data_Export_Audit.class);

		record.setAD_Table_ID(createDataExportAuditRequest.getTableRecordReference().getAD_Table_ID());
		record.setRecord_ID(createDataExportAuditRequest.getTableRecordReference().getRecord_ID());

		if (createDataExportAuditRequest.getParentId() != null)
		{
			record.setData_Export_Audit_Parent_ID(createDataExportAuditRequest.getParentId().getRepoId());
		}

		saveRecord(record);

		return toDataExportAudit(record);
	}

	@NonNull
	public Optional<DataExportAudit> getByTableRecordReference(@NonNull final TableRecordReference tableRecordReference)
	{
		return queryBL.createQueryBuilder(I_Data_Export_Audit.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_Data_Export_Audit.COLUMNNAME_AD_Table_ID, tableRecordReference.getAD_Table_ID())
				.addEqualsFilter(I_Data_Export_Audit.COLUMNNAME_Record_ID, tableRecordReference.getRecord_ID())
				.create()
				.firstOnlyOptional(I_Data_Export_Audit.class)
				.map(DataExportAuditRepository::toDataExportAudit);
	}

	@NonNull
	private static DataExportAudit toDataExportAudit(@NonNull final I_Data_Export_Audit record)
	{
		return DataExportAudit.builder()
				.id(DataExportAuditId.ofRepoId(record.getData_Export_Audit_ID()))
				.tableRecordReference(TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID()))
				.parentId(DataExportAuditId.ofRepoIdOrNull(record.getData_Export_Audit_Parent_ID()))
				.build();
	}
}
