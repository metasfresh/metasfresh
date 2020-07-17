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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.error.AdIssueId;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.exportaudit.ShipmentScheduleExportAudit.ShipmentScheduleExportAuditBuilder;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ShipmentScheduleAuditRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<String, StagingData> cache = CCache.<String, StagingData>builder()
			.tableName(I_M_ShipmentSchedule_ExportAudit.Table_Name)
			.build();

	public ShipmentScheduleExportAudit getByTransactionId(@NonNull final String transactionId)
	{
		final StagingData stagingData = retrieveStagingData(transactionId);
		final ShipmentScheduleExportAuditBuilder result = ShipmentScheduleExportAudit
				.builder()
				.transactionId(transactionId);
		for (final I_M_ShipmentSchedule_ExportAudit record : stagingData.getRecords())
		{
			final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID());
			result.item(shipmentScheduleId,
					ShipmentScheduleExportAuditItem.builder()
							.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
							.shipmentScheduleId(shipmentScheduleId)
							.exportStatus(ShipmentScheduleExportStatus.ofCode(record.getExportStatus()))
							.issueId(AdIssueId.ofRepoIdOrNull(record.getAD_Issue_ID()))
							.build());
		}
		return result.build();
	}

	public void save(@NonNull final ShipmentScheduleExportAudit shipmentScheduleExportAudit)
	{
		final StagingData stagingData = retrieveStagingData(shipmentScheduleExportAudit.getTransactionId());

		for (final ShipmentScheduleExportAuditItem item : shipmentScheduleExportAudit.getItems().values())
		{
			I_M_ShipmentSchedule_ExportAudit record = stagingData.schedIdToRecords.get(item.getShipmentScheduleId().getRepoId());
			if (record == null)
			{
				record = newInstance(I_M_ShipmentSchedule_ExportAudit.class);
				record.setM_ShipmentSchedule_ID(ShipmentScheduleId.toRepoId(item.getShipmentScheduleId()));
			}
			record.setAD_Org_ID(OrgId.toRepoId(item.getOrgId()));
			record.setTransactionIdAPI(shipmentScheduleExportAudit.getTransactionId());
			record.setAD_Issue_ID(AdIssueId.toRepoId(item.getIssueId()));
			record.setExportStatus(item.getExportStatus().getCode());
			saveRecord(record);

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
		final ImmutableList<I_M_ShipmentSchedule_ExportAudit> exportAuditRecord = queryBL.createQueryBuilder(I_M_ShipmentSchedule_ExportAudit.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_ExportAudit.COLUMN_TransactionIdAPI, transactionId)
				.create()
				.listImmutable(I_M_ShipmentSchedule_ExportAudit.class);

		return new StagingData(
				Maps.uniqueIndex(exportAuditRecord, I_M_ShipmentSchedule_ExportAudit::getM_ShipmentSchedule_ID),
				exportAuditRecord);
	}

	@Value
	private static class StagingData
	{
		@NonNull
		ImmutableMap<Integer, I_M_ShipmentSchedule_ExportAudit> schedIdToRecords;

		@NonNull
		ImmutableList<I_M_ShipmentSchedule_ExportAudit> records;
	}

}
