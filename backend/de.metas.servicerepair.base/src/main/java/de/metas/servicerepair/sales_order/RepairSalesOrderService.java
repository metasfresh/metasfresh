/*
 * #%L
 * de.metas.servicerepair.base
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

package de.metas.servicerepair.sales_order;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.project.ProjectId;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollector;
import de.metas.servicerepair.project.service.ServiceRepairProjectService;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RepairSalesOrderService
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final ServiceRepairProjectService projectService;

	public RepairSalesOrderService(
			@NonNull final ServiceRepairProjectService projectService)
	{
		this.projectService = projectService;
	}

	public Optional<RepairSalesOrderInfo> extractSalesOrderInfo(final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			return Optional.empty();
		}

		final ProjectId projectId = ProjectId.ofRepoIdOrNull(order.getC_Project_ID());
		if (projectId == null)
		{
			return Optional.empty();
		}

		final OrderId proposalId = OrderId.ofRepoIdOrNull(order.getRef_Proposal_ID());
		if (proposalId == null)
		{
			return Optional.empty();
		}

		final OrderId salesOrderId = OrderId.ofRepoId(order.getC_Order_ID());

		//
		// Fetch info for sales order lines
		final LinkedHashSet<OrderAndLineId> proposalLineIds = new LinkedHashSet<>();
		final LinkedHashMap<OrderAndLineId, OrderAndLineId> salesOrderLineIdsByProposalLineId = new LinkedHashMap<>();
		for (final I_C_OrderLine salesOrderLine : orderDAO.retrieveOrderLines(salesOrderId))
		{
			final OrderAndLineId proposalLineId = OrderAndLineId.ofRepoIdsOrNull(proposalId, salesOrderLine.getRef_ProposalLine_ID());
			if (proposalLineId == null)
			{
				// this sales order line was not created from a proposal line.
				// it might be created manually by the user.
				// => skipping it
				continue;
			}

			final OrderAndLineId salesOrderLineId = OrderAndLineId.ofRepoIdsOrNull(salesOrderLine.getC_Order_ID(), salesOrderLine.getC_OrderLine_ID());

			proposalLineIds.add(proposalLineId);
			salesOrderLineIdsByProposalLineId.put(proposalLineId, salesOrderLineId);
		}

		//
		// Build sales order lines infos
		final ArrayList<RepairSalesOrderLineInfo> repairSalesOrderLines = new ArrayList<>();
		final ImmutableSetMultimap<OrderAndLineId, HuId> vhuIdsByProposalLineId = getVhuIdsByProposalLineId(proposalLineIds);
		for (final OrderAndLineId proposalLineId : proposalLineIds)
		{
			final ImmutableSet<HuId> sparePartsVHUIds = vhuIdsByProposalLineId.get(proposalLineId);
			final OrderAndLineId salesOrderLineId = salesOrderLineIdsByProposalLineId.get(proposalLineId);

			repairSalesOrderLines.add(RepairSalesOrderLineInfo.builder()
					.salesOrderLineId(salesOrderLineId)
					.proposalLineId(proposalLineId)
					.sparePartsVHUIds(sparePartsVHUIds)
					.build());
		}

		//
		return Optional.of(
				RepairSalesOrderInfo.builder()
						.salesOrderId(salesOrderId)
						.proposalId(proposalId)
						.projectId(projectId)
						.lines(repairSalesOrderLines)
						.build());
	}

	private ImmutableSetMultimap<OrderAndLineId, HuId> getVhuIdsByProposalLineId(final Set<OrderAndLineId> proposalLineIds)
	{
		return projectService
				.getCostCollectorsByQuotationLineIds(proposalLineIds)
				.stream()
				.filter(costCollector -> costCollector.getVhuId() != null)
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						ServiceRepairProjectCostCollector::getCustomerQuotationLineId,
						ServiceRepairProjectCostCollector::getVhuId));
	}

	public Optional<RepairSalesProposalInfo> extractSalesProposalInfo(@NonNull final I_C_Order orderRecord)
	{
		final ProjectId projectId = ProjectId.ofRepoIdOrNull(orderRecord.getC_Project_ID());
		if (projectId == null)
		{
			return Optional.empty();
		}

		if (!orderBL.isSalesProposalOrQuotation(orderRecord))
		{
			return Optional.empty();
		}

		return Optional.of(RepairSalesProposalInfo.builder()
				.proposalId(OrderId.ofRepoId(orderRecord.getC_Order_ID()))
				.projectId(projectId)
				.build());
	}

	public void transferVHUsFromProjectToSalesOrderLine(@NonNull final RepairSalesOrderInfo salesOrderInfo)
	{
		for (final RepairSalesOrderLineInfo line : salesOrderInfo.getLines())
		{
			if (line.getSparePartsVHUIds().isEmpty())
			{
				continue;
			}

			projectService.transferVHUsFromProjectToSalesOrderLine(
					salesOrderInfo.getProjectId(),
					line.getSalesOrderLineId(),
					line.getSparePartsVHUIds());
		}
	}

	public void transferVHUsFromSalesOrderToProject(final RepairSalesOrderInfo salesOrderInfo)
	{
		projectService.transferVHUsFromSalesOrderToProject(
				salesOrderInfo.getSalesOrderLineIds(),
				salesOrderInfo.getProjectId());
	}

	public void unlinkProposalFromProject(@NonNull final RepairSalesProposalInfo proposalInfo)
	{
		projectService.unlinkQuotationFromProject(proposalInfo.getProjectId(), proposalInfo.getProposalId());
	}

	public void unlinkProposalLineFromProject(@NonNull final OrderAndLineId proposalLineId)
	{
		projectService.unlinkProposalLineFromProject(proposalLineId);
	}
}
