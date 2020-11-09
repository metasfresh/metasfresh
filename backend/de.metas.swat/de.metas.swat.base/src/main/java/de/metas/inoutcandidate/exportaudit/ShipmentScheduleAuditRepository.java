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
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit_Item;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ShipmentScheduleAuditRepository implements APIExportAuditRepository<ShipmentScheduleExportAuditItem>
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<String, StagingData> cache = CCache.<String, StagingData>builder()
			.tableName(I_M_ShipmentSchedule_ExportAudit.Table_Name)
			.build();

	@Nullable
	public APIExportAudit<ShipmentScheduleExportAuditItem> getByTransactionId(@NonNull final String transactionId)
	{
		final StagingData stagingData = retrieveStagingData(transactionId);

		final I_M_ShipmentSchedule_ExportAudit record = stagingData.getRecord();
		if (record == null)
		{
			return null;
		}

		final APIExportAudit.APIExportAuditBuilder result = APIExportAudit.<ShipmentScheduleExportAuditItem>builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.transactionId(transactionId)
				.exportSequenceNumber(record.getExportSequenceNumber())
				.exportStatus(APIExportStatus.ofCode(record.getExportStatus()))
				.issueId(AdIssueId.ofRepoIdOrNull(record.getAD_Issue_ID()))
				.forwardedData(record.getForwardedData());

		for (final I_M_ShipmentSchedule_ExportAudit_Item itemRecord : stagingData.getItemRecords())
		{
			final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(itemRecord.getM_ShipmentSchedule_ID());
			result.item(
					shipmentScheduleId,
					ShipmentScheduleExportAuditItem.builder()
							.orgId(OrgId.ofRepoId(itemRecord.getAD_Org_ID()))
							.repoIdAware(shipmentScheduleId)
							.exportStatus(APIExportStatus.ofCode(itemRecord.getExportStatus()))
							.issueId(AdIssueId.ofRepoIdOrNull(itemRecord.getAD_Issue_ID()))
							.build());
		}
		return result.build();
	}

	public void save(@NonNull final APIExportAudit<ShipmentScheduleExportAuditItem> audit)
	{
		final StagingData stagingData = retrieveStagingData(audit.getTransactionId());
		I_M_ShipmentSchedule_ExportAudit record = stagingData.getRecord();
		if (record == null)
		{
			record = newInstance(I_M_ShipmentSchedule_ExportAudit.class);
			record.setTransactionIdAPI(audit.getTransactionId());
		}
		record.setExportSequenceNumber(audit.getExportSequenceNumber());
		record.setAD_Org_ID(audit.getOrgId().getRepoId());
		record.setAD_Issue_ID(AdIssueId.toRepoId(audit.getIssueId()));
		record.setExportStatus(audit.getExportStatus().getCode());
		record.setForwardedData(audit.getForwardedData());
		saveRecord(record);

		for (final ShipmentScheduleExportAuditItem item : audit.getItems())
		{
			I_M_ShipmentSchedule_ExportAudit_Item itemRecord = stagingData.schedIdToRecords.get(item.getRepoIdAware().getRepoId());
			if (itemRecord == null)
			{
				itemRecord = newInstance(I_M_ShipmentSchedule_ExportAudit_Item.class);
				itemRecord.setM_ShipmentSchedule_ID(ShipmentScheduleId.toRepoId(item.getRepoIdAware()));
			}
			itemRecord.setM_ShipmentSchedule_ExportAudit_ID(record.getM_ShipmentSchedule_ExportAudit_ID());
			itemRecord.setAD_Org_ID(OrgId.toRepoId(item.getOrgId()));
			itemRecord.setAD_Issue_ID(AdIssueId.toRepoId(item.getIssueId()));
			itemRecord.setExportStatus(item.getExportStatus().getCode());

			saveRecord(itemRecord);
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
				.create()
				.firstOnly(I_M_ShipmentSchedule_ExportAudit.class); // we have a UC on TransactionIdAPI
		if (exportAuditRecord == null)
		{
			return new StagingData(ImmutableMap.of(), null, ImmutableList.of());
		}

		final ImmutableList<I_M_ShipmentSchedule_ExportAudit_Item> exportAuditItemRecords = queryBL.createQueryBuilder(I_M_ShipmentSchedule_ExportAudit_Item.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_ExportAudit_Item.COLUMN_M_ShipmentSchedule_ExportAudit_ID, exportAuditRecord.getM_ShipmentSchedule_ExportAudit_ID())
				.create()
				.listImmutable(I_M_ShipmentSchedule_ExportAudit_Item.class);

		return new StagingData(
				Maps.uniqueIndex(exportAuditItemRecords, I_M_ShipmentSchedule_ExportAudit_Item::getM_ShipmentSchedule_ID),
				exportAuditRecord,
				exportAuditItemRecords);
	}

	@Value
	private static class StagingData
	{
		@NonNull
		ImmutableMap<Integer, I_M_ShipmentSchedule_ExportAudit_Item> schedIdToRecords;

		@Nullable
		I_M_ShipmentSchedule_ExportAudit record;

		@NonNull
		ImmutableList<I_M_ShipmentSchedule_ExportAudit_Item> itemRecords;
	}

}
