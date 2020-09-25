package de.metas.manufacturing.rest_api;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.model.I_PP_Cost_Collector_ImportAudit;
import org.eevolution.model.I_PP_Cost_Collector_ImportAuditItem;
import org.springframework.stereotype.Repository;

import de.metas.error.AdIssueId;
import de.metas.manufacturing.order.exportaudit.APITransactionId;
import de.metas.manufacturing.order.importaudit.ManufacturingOrderReportAudit;
import de.metas.manufacturing.order.importaudit.ManufacturingOrderReportAudit.ManufacturingOrderReportAuditBuilder;
import de.metas.manufacturing.order.importaudit.ManufacturingOrderReportAuditItem;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.util.Services;
import lombok.NonNull;

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
public class ManufacturingOrderReportAuditRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void saveNew(@NonNull final ManufacturingOrderReportAudit audit)
	{
		final I_PP_Cost_Collector_ImportAudit record = newInstance(I_PP_Cost_Collector_ImportAudit.class);
		record.setTransactionIdAPI(audit.getTransactionId() != null ? audit.getTransactionId().toJson() : null);
		record.setJsonRequest(audit.getJsonRequest());
		record.setJsonResponse(audit.getJsonResponse());
		record.setImportStatus(audit.getImportStatus() != null ? audit.getImportStatus().name() : null);
		record.setErrorMsg(audit.getErrorMsg());
		record.setAD_Issue_ID(audit.getAdIssueId() != null ? audit.getAdIssueId().getRepoId() : -1);
		saveRecord(record);

		final int auditRepoId = record.getPP_Cost_Collector_ImportAudit_ID();
		for (final ManufacturingOrderReportAuditItem auditItem : audit.getItems())
		{
			saveNew(auditItem, auditRepoId);
		}
	}

	private void saveNew(
			@NonNull final ManufacturingOrderReportAuditItem auditItem,
			final int auditRepoId)
	{
		final I_PP_Cost_Collector_ImportAuditItem record = newInstance(I_PP_Cost_Collector_ImportAuditItem.class);
		record.setPP_Cost_Collector_ImportAudit_ID(auditRepoId);
		record.setPP_Order_ID(auditItem.getOrderId() != null ? auditItem.getOrderId().getRepoId() : -1);
		record.setPP_Cost_Collector_ID(auditItem.getCostCollectorId() != null ? auditItem.getCostCollectorId().getRepoId() : -1);
		record.setJsonRequest(auditItem.getJsonRequest());
		record.setJsonResponse(auditItem.getJsonResponse());
		record.setImportStatus(auditItem.getImportStatus() != null ? auditItem.getImportStatus().name() : null);
		record.setErrorMsg(auditItem.getErrorMsg());
		record.setAD_Issue_ID(auditItem.getAdIssueId() != null ? auditItem.getAdIssueId().getRepoId() : -1);
		saveRecord(record);
	}

	public ManufacturingOrderReportAudit getByTransactionId(@NonNull final APITransactionId transactionId)
	{
		final I_PP_Cost_Collector_ImportAudit record = queryBL.createQueryBuilder(I_PP_Cost_Collector_ImportAudit.class)
				.addEqualsFilter(I_PP_Cost_Collector_ImportAudit.COLUMNNAME_TransactionIdAPI, transactionId.toJson())
				.create()
				.firstOnly(I_PP_Cost_Collector_ImportAudit.class);
		if (record == null)
		{
			throw new AdempiereException("No audit info found for " + transactionId);
		}

		final ArrayList<ManufacturingOrderReportAuditItem> items = queryBL
				.createQueryBuilder(I_PP_Cost_Collector_ImportAuditItem.class)
				.addEqualsFilter(I_PP_Cost_Collector_ImportAuditItem.COLUMNNAME_PP_Cost_Collector_ImportAudit_ID, record.getPP_Cost_Collector_ImportAudit_ID())
				.orderBy(I_PP_Cost_Collector_ImportAuditItem.COLUMN_PP_Cost_Collector_ImportAuditItem_ID)
				.create()
				.stream()
				.map(itemRecord -> toManufacturingOrderReportAuditItem(itemRecord))
				.collect(Collectors.toCollection(ArrayList::new));

		return toManufacturingOrderReportAudit(record)
				.items(items)
				.build();
	}

	private static ManufacturingOrderReportAuditBuilder toManufacturingOrderReportAudit(final I_PP_Cost_Collector_ImportAudit record)
	{
		return ManufacturingOrderReportAudit.builder()
				.transactionId(APITransactionId.ofString(record.getTransactionIdAPI()))
				.jsonRequest(record.getJsonRequest())
				.jsonResponse(record.getJsonResponse())
				.importStatus(ManufacturingOrderReportAudit.ImportStatus.valueOf(record.getImportStatus()))
				.errorMsg(record.getErrorMsg())
				.adIssueId(AdIssueId.ofRepoIdOrNull(record.getAD_Issue_ID()));
	}

	private static ManufacturingOrderReportAuditItem toManufacturingOrderReportAuditItem(final I_PP_Cost_Collector_ImportAuditItem record)
	{
		return ManufacturingOrderReportAuditItem.builder()
				.orderId(PPOrderId.ofRepoIdOrNull(record.getPP_Order_ID()))
				.costCollectorId(PPCostCollectorId.ofRepoIdOrNull(record.getPP_Cost_Collector_ID()))
				.jsonRequest(record.getJsonRequest())
				.jsonResponse(record.getJsonResponse())
				.importStatus(ManufacturingOrderReportAuditItem.ImportStatus.valueOf(record.getImportStatus()))
				.errorMsg(record.getErrorMsg())
				.adIssueId(AdIssueId.ofRepoIdOrNull(record.getAD_Issue_ID()))
				.build();
	}
}
