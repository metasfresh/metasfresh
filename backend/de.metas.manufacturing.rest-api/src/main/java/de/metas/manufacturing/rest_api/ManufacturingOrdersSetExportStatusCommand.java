package de.metas.manufacturing.rest_api;

import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.model.I_PP_Order;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.common.manufacturing.JsonRequestSetOrderExportStatus;
import de.metas.common.manufacturing.JsonRequestSetOrdersExportStatusBulk;
import de.metas.common.rest_api.JsonError;
import de.metas.common.rest_api.JsonErrorItem;
import de.metas.common.util.CoalesceUtil;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.error.IssueCreateRequest;
import de.metas.logging.LogManager;
import de.metas.manufacturing.order.exportaudit.APIExportStatus;
import de.metas.manufacturing.order.exportaudit.APITransactionId;
import de.metas.manufacturing.order.exportaudit.ManufacturingOrderExportAudit;
import de.metas.manufacturing.order.exportaudit.ManufacturingOrderExportAuditItem;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.Builder;
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

final class ManufacturingOrdersSetExportStatusCommand
{
	private static final Logger logger = LogManager.getLogger(ManufacturingOrdersSetExportStatusCommand.class);
	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);
	private final IErrorManager errorManager = Services.get(IErrorManager.class);
	private final ManufacturingOrderExportAuditRepository orderAuditRepo;
	private final ObjectMapper jsonObjectMapper;

	private @NonNull JsonRequestSetOrdersExportStatusBulk request;

	@Builder
	private ManufacturingOrdersSetExportStatusCommand(
			@NonNull final ManufacturingOrderExportAuditRepository orderAuditRepo,
			@NonNull final ObjectMapper jsonObjectMapper,
			//
			@NonNull final JsonRequestSetOrdersExportStatusBulk request)
	{
		this.orderAuditRepo = orderAuditRepo;
		this.jsonObjectMapper = jsonObjectMapper;

		this.request = request;

	}

	public void execute()
	{
		final AdIssueId generalAdIssueId = createADIssue(request.getError());
		if (generalAdIssueId != null)
		{
			logger.debug("Created AD_Issue_ID={} that applies to all exported shipment schedules", generalAdIssueId.getRepoId());
		}

		if (request.getItems().isEmpty())
		{
			logger.debug("given results is empty; -> return");
			return;
		}

		final APITransactionId transactionKey = APITransactionId.ofString(request.getTransactionKey());
		final ManufacturingOrderExportAudit audit = orderAuditRepo.getByTransactionId(transactionKey);
		if (audit == null)
		{
			logger.debug("Given results.transactionKey={} does not match any audit records; -> return", transactionKey);
			return;
		}

		final Set<PPOrderId> orderIds = getOrderIds();
		final ImmutableMap<PPOrderId, I_PP_Order> ordersById = Maps.uniqueIndex(
				ppOrderDAO.getByIds(orderIds),
				order -> PPOrderId.ofRepoId(order.getPP_Order_ID()));

		for (final JsonRequestSetOrderExportStatus requestItem : request.getItems())
		{
			final PPOrderId orderId = extractPPOrderId(requestItem);
			final I_PP_Order order = ordersById.get(orderId);
			if (order == null)
			{
				continue; // also shouldn't happen, unless we do some API-testing with static JSON stuff
			}

			final ManufacturingOrderExportAuditItem auditItem = audit.computeItemIfAbsent(
					orderId,
					// should not happen, but we don't want to make a fuzz in case it does:
					k -> ManufacturingOrderExportAuditItem.builder()
							.orderId(orderId)
							.orgId(OrgId.ofRepoId(order.getAD_Org_ID()))
							.exportStatus(APIExportStatus.Pending)
							.jsonRequest(toJsonString(requestItem))
							.build());

			final APIExportStatus status;
			AdIssueId issueIdEffective;
			switch (requestItem.getOutcome())
			{
				case OK:
					status = APIExportStatus.ExportedAndForwarded;
					issueIdEffective = null;
					break;
				case ERROR:
					status = APIExportStatus.ExportedAndError;

					final AdIssueId specificAdIssueId = createADIssue(requestItem.getError());
					issueIdEffective = CoalesceUtil.coalesce(specificAdIssueId, generalAdIssueId);
					break;
				default:
					throw new AdempiereException("Item has unexpected outcome: " + requestItem.getOutcome())
							.setParameter("TransactionIdAPI", transactionKey)
							.setParameter("resultItem", requestItem);
			}

			auditItem.setJsonRequest(toJsonString(requestItem));
			auditItem.setExportStatus(status);
			auditItem.setIssueId(issueIdEffective);

			order.setExportStatus(status.getCode());
		}

		ppOrderDAO.saveAll(ordersById.values());
		orderAuditRepo.save(audit);
	}

	private ImmutableSet<PPOrderId> getOrderIds()
	{
		return request.getItems()
				.stream()
				.map(item -> extractPPOrderId(item))
				.collect(ImmutableSet.toImmutableSet());
	}

	private static PPOrderId extractPPOrderId(final JsonRequestSetOrderExportStatus item)
	{
		return PPOrderId.ofRepoId(item.getOrderId().getValue());
	}

	@Nullable
	private AdIssueId createADIssue(@Nullable final JsonError error)
	{
		if (error == null || error.getErrors().isEmpty())
		{
			return null;
		}

		final JsonErrorItem errorItem = error.getErrors().get(0);
		return errorManager.createIssue(IssueCreateRequest.builder()
				.summary(errorItem.getMessage() + "; " + errorItem.getDetail())
				.stackTrace(errorItem.getStackTrace())
				.loggerName(logger.getName())
				.build());
	}

	private String toJsonString(@NonNull final JsonRequestSetOrderExportStatus requestItem)
	{
		try
		{
			return jsonObjectMapper.writeValueAsString(requestItem);
		}
		catch (final JsonProcessingException ex)
		{
			logger.warn("Failed converting {} to JSON. Returning toString()", requestItem, ex);
			return requestItem.toString();
		}
	}
}
