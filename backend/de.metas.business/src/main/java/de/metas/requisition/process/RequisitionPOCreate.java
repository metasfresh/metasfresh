/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package de.metas.requisition.process;

import de.metas.bpartner.BPGroupId;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessInfoParameter;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.requisition.RequisitionId;
import de.metas.requisition.order_aggregation.OrderCandidate;
import de.metas.requisition.order_aggregation.OrderCandidateQuery;
import de.metas.requisition.order_aggregation.OrderCandidateService;
import de.metas.requisition.order_aggregation.POFromRequisitionAggregator;
import de.metas.user.UserId;
import de.metas.util.StringUtils;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;

import java.util.List;
import java.util.stream.Stream;

/**
 * Aggregate Requisitions and Create Purchase Orders
 */
public class RequisitionPOCreate extends JavaProcess
{
	private final OrderCandidateService orderCandidateService = SpringContextHolder.instance.getBean(OrderCandidateService.class);

	//
	// Parameters
	@Param(parameterName = "M_Requisition_ID")
	private RequisitionId p_M_Requisition_ID;
	@Param(parameterName = "C_BP_Group_ID")
	private BPGroupId p_C_BP_Group_ID;
	private static final String PARAM_ConsolidateDocument = "ConsolidateDocument";
	@Param(parameterName = PARAM_ConsolidateDocument)
	private boolean p_ConsolidateDocument = false;

	private static final String PARAM_GroupByRequestor = "GroupByRequestor";
	@Param(parameterName = PARAM_GroupByRequestor)
	private boolean p_GroupByRequestor = false;

	@Override
	protected String doIt()
	{
		final POFromRequisitionAggregator aggregator = POFromRequisitionAggregator.builder()
				.consolidateDocument(p_ConsolidateDocument)
				.groupByRequestor(p_GroupByRequestor)
				.onlyVendorGroupId(p_C_BP_Group_ID)
				.build();

		streamCandidates().forEach(aggregator::aggregate);

		aggregator.done();

		return MSG_OK;
	}

	private Stream<OrderCandidate> streamCandidates()
	{
		return p_M_Requisition_ID != null
				? orderCandidateService.streamByRequisitionId(p_M_Requisition_ID)
				: orderCandidateService.stream(toOrderCandidateQuery(getParameters()));
	}

	private static OrderCandidateQuery toOrderCandidateQuery(final List<ProcessInfoParameter> parameters)
	{
		final OrderCandidateQuery.OrderCandidateQueryBuilder queryBuilder = OrderCandidateQuery.builder();

		for (final ProcessInfoParameter processInfoParameter : parameters)
		{
			String name = processInfoParameter.getParameterName();
			switch (name)
			{
				case "AD_Org_ID" -> processInfoParameter.getParameterAsRepoId(OrgId::ofRepoIdOrAny).ifRegular(queryBuilder::orgId);
				case "M_Warehouse_ID" -> WarehouseId.optionalOfRepoId(processInfoParameter.getParameterAsInt()).ifPresent(queryBuilder::warehouseId);
				case "DateDoc" ->
				{
					queryBuilder.dateDocFrom(processInfoParameter.getParameterAsInstant());
					queryBuilder.dateDocTo(processInfoParameter.getParameter_ToAsInstant());
				}
				case "DateRequired" ->
				{
					queryBuilder.dateRequiredFrom(processInfoParameter.getParameterAsInstant());
					queryBuilder.dateRequiredTo(processInfoParameter.getParameter_ToAsInstant());
				}
				case "PriorityRule" -> StringUtils.trimBlankToOptional(processInfoParameter.getParameterAsString()).ifPresent(queryBuilder::priorityRule);
				case "AD_User_ID" -> UserId.ofRepoIdOrSystem(processInfoParameter.getParameterAsInt()).ifRegularUser(queryBuilder::requestorId);
				case "M_Product_ID" -> ProductId.optionalOfRepoId(processInfoParameter.getParameterAsInt()).ifPresent(queryBuilder::productId);
				case "M_Product_Category_ID" -> ProductCategoryId.optionalOfRepoId(processInfoParameter.getParameterAsInt()).ifPresent(queryBuilder::productCategoryId);
				case "C_BP_Group_ID" -> BPGroupId.optionalOfRepoId(processInfoParameter.getParameterAsInt()).ifPresent(queryBuilder::bpGroupId);
				case PARAM_ConsolidateDocument -> queryBuilder.orderByRequisitionId(!processInfoParameter.getParameterAsBoolean());
				case PARAM_GroupByRequestor -> queryBuilder.orderByRequestor(processInfoParameter.getParameterAsBoolean());
			}
		}

		return queryBuilder.build();
	}

}
