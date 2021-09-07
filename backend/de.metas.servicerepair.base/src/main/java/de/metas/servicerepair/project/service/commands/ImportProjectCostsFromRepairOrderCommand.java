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

package de.metas.servicerepair.project.service.commands;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.product.ProductId;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorType;
import de.metas.servicerepair.project.model.ServiceRepairProjectTask;
import de.metas.servicerepair.project.repository.ServiceRepairProjectCostCollectorRepository;
import de.metas.servicerepair.project.repository.ServiceRepairProjectTaskRepository;
import de.metas.servicerepair.project.repository.requests.CreateProjectCostCollectorRequest;
import de.metas.servicerepair.project.service.ServiceRepairProjectService;
import de.metas.servicerepair.repair_order.model.RepairManufacturingCostCollector;
import de.metas.servicerepair.repair_order.model.RepairManufacturingOrderInfo;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.eevolution.api.PPOrderAndCostCollectorId;

import java.util.Objects;
import java.util.Set;

public class ImportProjectCostsFromRepairOrderCommand
{
	private final ServiceRepairProjectTaskRepository projectTaskRepository;
	private final RepairManufacturingOrderInfo repairOrder;
	private final ServiceRepairProjectCostCollectorRepository projectCostCollectorRepository;
	private final ServiceRepairProjectService serviceRepairProjectService;
	private final IHandlingUnitsBL handlingUnitsBL;

	@Builder
	private ImportProjectCostsFromRepairOrderCommand(
			@NonNull final ServiceRepairProjectTaskRepository projectTaskRepository,
			@NonNull final ServiceRepairProjectCostCollectorRepository projectCostCollectorRepository,
			@NonNull final ServiceRepairProjectService serviceRepairProjectService,
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			//
			@NonNull final RepairManufacturingOrderInfo repairOrder)
	{
		this.projectTaskRepository = projectTaskRepository;
		this.projectCostCollectorRepository = projectCostCollectorRepository;
		this.serviceRepairProjectService = serviceRepairProjectService;
		this.handlingUnitsBL = handlingUnitsBL;

		this.repairOrder = repairOrder;
	}

	public void execute()
	{
		ServiceRepairProjectTask task = projectTaskRepository
				.getTaskByRepairOrderId(repairOrder.getProjectId(), repairOrder.getId())
				.orElseThrow(() -> new AdempiereException("No task found for " + repairOrder));

		final Set<PPOrderAndCostCollectorId> alreadyImportedRepairCostCollectorIds = projectCostCollectorRepository.retainExistingPPCostCollectorIds(
				repairOrder.getProjectId(),
				repairOrder.getCostCollectorIds());

		for (final RepairManufacturingCostCollector mfgCostCollector : repairOrder.getCostCollectors())
		{
			// skip already imported cost collectors
			if (alreadyImportedRepairCostCollectorIds.contains(mfgCostCollector.getId()))
			{
				continue;
			}

			createCostCollector_RepairingConsumption(task, mfgCostCollector);
		}

		if (!projectCostCollectorRepository.matchesByTaskAndProduct(task.getId(), repairOrder.getRepairedProductId()))
		{
			createCostCollector_RepairedProductToReturn(task);
		}

		task = task.withRepairOrderDone(repairOrder.getSummary(), repairOrder.getServicePerformedId());
		projectTaskRepository.save(task);

	}

	private void createCostCollector_RepairingConsumption(
			@NonNull final ServiceRepairProjectTask task,
			@NonNull final RepairManufacturingCostCollector mfgCostCollector)
	{
		serviceRepairProjectService.createCostCollector(CreateProjectCostCollectorRequest.builder()
				.taskId(task.getId())
				.type(ServiceRepairProjectCostCollectorType.RepairingConsumption)
				.productId(mfgCostCollector.getProductId())
				.qtyConsumed(mfgCostCollector.getQtyConsumed())
				.repairOrderCostCollectorId(mfgCostCollector.getId())
				.warrantyCase(task.getWarrantyCase())
				.build());
	}

	private void createCostCollector_RepairedProductToReturn(
			@NonNull final ServiceRepairProjectTask task)
	{
		final ProductId repairedProductId = repairOrder.getRepairedProductId();
		final HuId repairedVhuId = Objects.requireNonNull(task.getRepairVhuId());
		final AttributeSetInstanceId asiId = handlingUnitsBL.createASIFromHUAttributes(repairedProductId, repairedVhuId);

		serviceRepairProjectService.createCostCollector(CreateProjectCostCollectorRequest.builder()
				.taskId(task.getId())
				.type(ServiceRepairProjectCostCollectorType.RepairedProductToReturn)
				.productId(repairedProductId)
				.asiId(asiId)
				.warrantyCase(task.getWarrantyCase())
				.qtyReserved(repairOrder.getRepairedQty())
				.reservedVhuId(repairedVhuId)
				.build());
	}
}
