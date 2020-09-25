package de.metas.manufacturing.rest_api;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.eevolution.model.I_PP_Order_ExportAudit;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.error.AdIssueId;
import de.metas.manufacturing.order.exportaudit.APIExportStatus;
import de.metas.manufacturing.order.exportaudit.APITransactionId;
import de.metas.manufacturing.order.exportaudit.ManufacturingOrderExportAudit;
import de.metas.manufacturing.order.exportaudit.ManufacturingOrderExportAuditItem;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.manufacturing.rest-api
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

@Repository
public class ManufacturingOrderExportAuditRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void save(@NonNull final ManufacturingOrderExportAudit audit)
	{
		final APITransactionId transactionId = audit.getTransactionId();
		final StagingData stagingData = retrieveStagingData(transactionId);

		for (final ManufacturingOrderExportAuditItem item : audit.getItems())
		{
			I_PP_Order_ExportAudit record = stagingData.getByOrderIdOrNull(item.getOrderId());
			if (record == null)
			{
				record = newInstance(I_PP_Order_ExportAudit.class);
				record.setPP_Order_ID(item.getOrderId().getRepoId());
			}

			record.setTransactionIdAPI(transactionId.toJson());

			record.setAD_Org_ID(OrgId.toRepoId(item.getOrgId()));
			record.setAD_Issue_ID(AdIssueId.toRepoId(item.getIssueId()));
			record.setExportStatus(item.getExportStatus().getCode());
			record.setJsonRequest(item.getJsonRequest());

			saveRecord(record);
		}
	}

	public ManufacturingOrderExportAudit getByTransactionId(@NonNull final APITransactionId transactionId)
	{
		final StagingData stagingData = retrieveStagingData(transactionId);
		return toExportAudit(stagingData);
	}

	private static ManufacturingOrderExportAudit toExportAudit(@NonNull final StagingData stagingData)
	{
		return ManufacturingOrderExportAudit.builder()
				.transactionId(stagingData.getTransactionId())
				.items(stagingData.getRecords()
						.stream()
						.map(record -> toExportAuditItem(record))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private static ManufacturingOrderExportAuditItem toExportAuditItem(final I_PP_Order_ExportAudit record)
	{
		return ManufacturingOrderExportAuditItem.builder()
				.orderId(extactPPOrderId(record))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.exportStatus(APIExportStatus.ofCode(record.getExportStatus()))
				.issueId(AdIssueId.ofRepoIdOrNull(record.getAD_Issue_ID()))
				.jsonRequest(record.getJsonRequest())
				.build();
	}

	@NonNull
	private StagingData retrieveStagingData(@NonNull final APITransactionId transactionId)
	{
		final ImmutableList<I_PP_Order_ExportAudit> records = queryBL.createQueryBuilder(I_PP_Order_ExportAudit.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Order_ExportAudit.COLUMNNAME_TransactionIdAPI, transactionId.toJson())
				.create()
				.listImmutable(I_PP_Order_ExportAudit.class);

		return StagingData.builder()
				.transactionId(transactionId)
				.records(records)
				.build();
	}

	private static PPOrderId extactPPOrderId(@NonNull final I_PP_Order_ExportAudit record)
	{
		return PPOrderId.ofRepoId(record.getPP_Order_ID());
	}

	@Value
	private static class StagingData
	{
		private final APITransactionId transactionId;
		private final ImmutableList<I_PP_Order_ExportAudit> records;

		@Getter(AccessLevel.NONE)
		ImmutableMap<PPOrderId, I_PP_Order_ExportAudit> recordsByOrderId;

		@Builder
		private StagingData(
				@NonNull final APITransactionId transactionId,
				@NonNull final List<I_PP_Order_ExportAudit> records)
		{
			this.transactionId = transactionId;
			this.recordsByOrderId = Maps.uniqueIndex(records, record -> extactPPOrderId(record));
			this.records = ImmutableList.copyOf(records);
		}

		@Nullable
		public I_PP_Order_ExportAudit getByOrderIdOrNull(@NonNull final PPOrderId orderId)
		{
			return recordsByOrderId.get(orderId);
		}

	}
}
