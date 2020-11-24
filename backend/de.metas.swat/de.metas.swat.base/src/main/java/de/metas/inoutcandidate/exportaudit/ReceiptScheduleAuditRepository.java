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
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.exportaudit.APIExportAudit.APIExportAuditBuilder;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_ExportAudit;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ReceiptScheduleAuditRepository implements APIExportAuditRepository<ReceiptScheduleExportAuditItem>
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<String, StagingData> cache = CCache.<String, StagingData>builder()
			.tableName(I_M_ReceiptSchedule.Table_Name)
			.build();

	public APIExportAudit<ReceiptScheduleExportAuditItem> getByTransactionId(@NonNull final String transactionId)
	{
		final StagingData stagingData = retrieveStagingData(transactionId);
		final APIExportAuditBuilder<ReceiptScheduleExportAuditItem> result = APIExportAudit
				.<ReceiptScheduleExportAuditItem>builder()
				.transactionId(transactionId);
		for (final I_M_ReceiptSchedule_ExportAudit record : stagingData.getRecords())
		{
			final ReceiptScheduleId receiptScheduleId = ReceiptScheduleId.ofRepoId(record.getM_ReceiptSchedule_ID());
			result.item(receiptScheduleId,
					ReceiptScheduleExportAuditItem.builder()
							.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
							.repoIdAware(receiptScheduleId)
							.exportStatus(APIExportStatus.ofCode(record.getExportStatus()))
							.issueId(AdIssueId.ofRepoIdOrNull(record.getAD_Issue_ID()))
							.build());
		}
		return result.build();
	}

	public void save(@NonNull final APIExportAudit<ReceiptScheduleExportAuditItem> audit)
	{
		final StagingData stagingData = retrieveStagingData(audit.getTransactionId());

		for (final ReceiptScheduleExportAuditItem item : audit.getItems().values())
		{
			I_M_ReceiptSchedule_ExportAudit record = stagingData.schedIdToRecords.get(item.getRepoIdAware().getRepoId());
			if (record == null)
			{
				record = newInstance(I_M_ReceiptSchedule_ExportAudit.class);
				record.setM_ReceiptSchedule_ID(ReceiptScheduleId.toRepoId(item.getRepoIdAware()));
			}
			record.setAD_Org_ID(OrgId.toRepoId(item.getOrgId()));
			record.setTransactionIdAPI(audit.getTransactionId());
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
		final ImmutableList<I_M_ReceiptSchedule_ExportAudit> exportAuditRecord = queryBL.createQueryBuilder(I_M_ReceiptSchedule_ExportAudit.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ReceiptSchedule_ExportAudit.COLUMN_TransactionIdAPI, transactionId)
				.create()
				.listImmutable(I_M_ReceiptSchedule_ExportAudit.class);

		return new StagingData(
				Maps.uniqueIndex(exportAuditRecord, I_M_ReceiptSchedule_ExportAudit::getM_ReceiptSchedule_ID),
				exportAuditRecord);
	}

	@Value
	private static class StagingData
	{
		@NonNull
		ImmutableMap<Integer, I_M_ReceiptSchedule_ExportAudit> schedIdToRecords;

		@NonNull
		ImmutableList<I_M_ReceiptSchedule_ExportAudit> records;
	}

}
