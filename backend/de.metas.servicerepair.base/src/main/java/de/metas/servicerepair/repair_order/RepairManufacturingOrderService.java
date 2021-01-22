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

package de.metas.servicerepair.repair_order;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.pporder.api.CreateReceiptCandidateRequest;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.material.planning.ProductPlanningService;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.material.planning.pporder.PPRoutingType;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.servicerepair.project.model.ServiceRepairProjectInfo;
import de.metas.servicerepair.project.model.ServiceRepairProjectTask;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskStatus;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskType;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderAndCostCollectorId;
import org.eevolution.api.PPOrderCreateRequest;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class RepairManufacturingOrderService
{
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
	private final IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	private final ProductPlanningService productPlanningService;

	public RepairManufacturingOrderService(
			@NonNull final ProductPlanningService productPlanningService)
	{
		this.productPlanningService = productPlanningService;
	}

	public Optional<RepairManufacturingOrderInfo> getById(@NonNull final PPOrderId ppOrderId)
	{
		final I_PP_Order repairOrder = ppOrderBL.getById(ppOrderId);
		return extractFromRecord(repairOrder);
	}

	public Optional<RepairManufacturingOrderInfo> extractFromRecord(@NonNull final I_PP_Order record)
	{
		final PPOrderDocBaseType docBaseType = PPOrderDocBaseType.ofNullableCode(record.getDocBaseType());
		if (docBaseType == null || !docBaseType.isRepairOrder())
		{
			return Optional.empty();
		}

		final ProjectId projectId = ProjectId.ofRepoIdOrNull(record.getC_Project_ID());
		if (projectId == null)
		{
			return Optional.empty();
		}

		final PPOrderId repairOrderId = PPOrderId.ofRepoId(record.getPP_Order_ID());

		return Optional.of(RepairManufacturingOrderInfo.builder()
				.id(repairOrderId)
				.projectId(projectId)
				.build());
	}

	public List<RepairManufacturingCostCollector> getCostCollectors(final PPOrderId repairOrderId)
	{
		return ppCostCollectorBL.getByOrderId(repairOrderId)
				.stream()
				.filter(ppCostCollectorBL::isAnyComponentIssue)
				.map(this::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	private RepairManufacturingCostCollector fromRecord(@NonNull final I_PP_Cost_Collector ppCostCollector)
	{
		return RepairManufacturingCostCollector.builder()
				.id(PPOrderAndCostCollectorId.ofRepoId(ppCostCollector.getPP_Order_ID(), ppCostCollector.getPP_Cost_Collector_ID()))
				.productId(ProductId.ofRepoId(ppCostCollector.getM_Product_ID()))
				.qtyConsumed(ppCostCollectorBL.getMovementQty(ppCostCollector))
				.build();
	}

	public PPOrderId createRepairOrder(
			@NonNull final ServiceRepairProjectInfo project,
			@NonNull final ServiceRepairProjectTask task)
	{
		if (!ServiceRepairProjectTaskType.REPAIR_ORDER.equals(task.getType()))
		{
			throw new AdempiereException("Not an repair order task: " + task);
		}
		if (!ServiceRepairProjectTaskStatus.NOT_STARTED.equals(task.getStatus()))
		{
			throw new AdempiereException("Task already started: " + task);
		}
		if (task.getRepairOrderId() != null)
		{
			throw new AdempiereException("A Repair Order was already created: " + task);
		}

		final WarehouseId warehouseId = project.getWarehouseId();
		if (warehouseId == null)
		{
			throw new AdempiereException("No warehouse for " + project);
		}
		final ResourceId plantId = productPlanningService.getPlantOfWarehouse(warehouseId)
				.orElseThrow(() -> new AdempiereException("No plant for warehouse " + warehouseId));

		final PPRoutingId routingId = productPlanningService.getDefaultRoutingId(PPRoutingType.Repair)
				.orElse(PPRoutingId.NONE);

		final Instant now = SystemTime.asInstant();
		final I_PP_Order repairOrder = ppOrderBL.createOrder(PPOrderCreateRequest.builder()
				.docBaseType(PPOrderDocBaseType.REPAIR_ORDER)
				.clientAndOrgId(task.getClientAndOrgId())
				.warehouseId(warehouseId)
				.plantId(plantId)
				.routingId(routingId)
				//
				.productId(task.getProductId())
				.attributeSetInstanceId(task.getAsiId())
				.qtyRequired(task.getQtyRequired())
				//
				.dateOrdered(now)
				.datePromised(now)
				.dateStartSchedule(now)
				//
				.projectId(project.getProjectId())
				//
				.completeDocument(true)
				//
				.build());
		final PPOrderId repairOrderId = PPOrderId.ofRepoId(repairOrder.getPP_Order_ID());

		huPPOrderQtyDAO.save(CreateReceiptCandidateRequest.builder()
				.orderId(repairOrderId)
				.orgId(OrgId.ofRepoId(repairOrder.getAD_Org_ID()))
				.date(TimeUtil.asZonedDateTime(now))
				.locatorId(LocatorId.ofRepoId(repairOrder.getM_Warehouse_ID(), repairOrder.getM_Locator_ID()))
				.topLevelHUId(task.getRepairVhuId())
				.productId(task.getProductId())
				.qtyToReceive(task.getQtyRequired())
				.alreadyProcessed(true)
				.build());

		return repairOrderId;
	}

}
