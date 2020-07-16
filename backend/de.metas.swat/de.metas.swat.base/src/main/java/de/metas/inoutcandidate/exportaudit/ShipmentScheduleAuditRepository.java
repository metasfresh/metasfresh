/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.exportaudit;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.error.AdIssueId;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.exportaudit.ShipmentScheduleExportAudit.ShipmentScheduleExportAuditBuilder;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit_Line;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ShipmentScheduleAuditRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<String, StagingData> cache = CCache.<String, StagingData>builder()
			.tableName(I_M_ShipmentSchedule_ExportAudit.Table_Name)
			.additionalTableNameToResetFor(I_M_ShipmentSchedule_ExportAudit_Line.Table_Name)
			.build();

	public ShipmentScheduleExportAudit getByTransactionId(@NonNull final String transactionId)
	{
		final StagingData stagingData = retrieveStagingData(transactionId);

		final I_M_ShipmentSchedule_ExportAudit auditRecord = stagingData.getExportAuditRecord();
		if (auditRecord == null)
		{
			return null;
		}

		final ShipmentScheduleExportAuditBuilder result = ShipmentScheduleExportAudit
				.builder()
				.transactionId(transactionId)
				.orgId(OrgId.ofRepoId(auditRecord.getAD_Org_ID()));

		for (final I_M_ShipmentSchedule_ExportAudit_Line auditLineRecord : stagingData.getShipmentScheduleIdToExportAuditLineRecord().values())
		{
			final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(auditLineRecord.getM_ShipmentSchedule_ID());
			result.item(shipmentScheduleId,
					ShipmentScheduleExportAuditItem.builder()
							.orgId(OrgId.ofRepoId(auditLineRecord.getAD_Org_ID()))
							.shipmentScheduleId(shipmentScheduleId)
							.exportStatus(ShipmentScheduleExportStatus.ofCode(auditLineRecord.getExportStatus()))
							.issueId(AdIssueId.ofRepoIdOrNull(auditLineRecord.getAD_Issue_ID()))
							.build());
		}
		return result.build();
	}

	public void save(@NonNull final ShipmentScheduleExportAudit shipmentScheduleExportAudit)
	{
		final StagingData stagingData = retrieveStagingData(shipmentScheduleExportAudit.getTransactionId());
		final ImmutableMap<Integer, I_M_ShipmentSchedule_ExportAudit_Line> idToRecord = stagingData.getShipmentScheduleIdToExportAuditLineRecord();

		I_M_ShipmentSchedule_ExportAudit record = stagingData.getExportAuditRecord();
		if (record == null)
		{
			record = newInstance(I_M_ShipmentSchedule_ExportAudit.class);
			record.setAD_Org_ID(OrgId.toRepoId(shipmentScheduleExportAudit.getOrgId()));
			record.setTransactionIdAPI(shipmentScheduleExportAudit.getTransactionId());
			saveRecord(record);
		}

		for (final ShipmentScheduleExportAuditItem item : shipmentScheduleExportAudit.getItems().values())
		{
			I_M_ShipmentSchedule_ExportAudit_Line lineRecord = idToRecord.get(item.getShipmentScheduleId().getRepoId());
			if (lineRecord == null)
			{
				lineRecord = newInstance(I_M_ShipmentSchedule_ExportAudit_Line.class);
				lineRecord.setM_ShipmentSchedule_ExportAudit_ID(record.getM_ShipmentSchedule_ExportAudit_ID());
			}
			lineRecord.setAD_Org_ID(OrgId.toRepoId(item.getOrgId()));
			lineRecord.setAD_Issue_ID(AdIssueId.toRepoId(item.getIssueId()));
			lineRecord.setExportStatus(item.getExportStatus().getCode());
			lineRecord.setM_ShipmentSchedule_ID(ShipmentScheduleId.toRepoId(item.getShipmentScheduleId()));
			saveRecord(lineRecord);
		}
	}

	@NonNull
	private StagingData retrieveStagingData(@NonNull final String transactionId)
	{
		return cache.getOrLoad(transactionId, this::retrieveStagingData0);
	}

	@NonNull
	private StagingData retrieveStagingData0(@NonNull final String transactionId)
	{
		final I_M_ShipmentSchedule_ExportAudit exportAuditRecord = queryBL.createQueryBuilder(I_M_ShipmentSchedule_ExportAudit.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_ExportAudit.COLUMN_TransactionIdAPI, transactionId)
				.create().firstOnly(I_M_ShipmentSchedule_ExportAudit.class);
		if (exportAuditRecord == null)
		{
			return new StagingData(null, ImmutableMap.of());
		}

		final List<I_M_ShipmentSchedule_ExportAudit_Line> scheduleExportAuditLineRecords = queryBL.createQueryBuilder(I_M_ShipmentSchedule_ExportAudit_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_ExportAudit_Line.COLUMN_M_ShipmentSchedule_ExportAudit_ID, exportAuditRecord.getM_ShipmentSchedule_ExportAudit_ID())
				.create()
				.list();

		return new StagingData(
				exportAuditRecord,
				Maps.uniqueIndex(scheduleExportAuditLineRecords, I_M_ShipmentSchedule_ExportAudit_Line::getM_ShipmentSchedule_ID));
	}

	@Value
	private static class StagingData
	{
		@Nullable
		I_M_ShipmentSchedule_ExportAudit exportAuditRecord;

		@NonNull
		ImmutableMap<Integer, I_M_ShipmentSchedule_ExportAudit_Line> shipmentScheduleIdToExportAuditLineRecord;
	}
}
