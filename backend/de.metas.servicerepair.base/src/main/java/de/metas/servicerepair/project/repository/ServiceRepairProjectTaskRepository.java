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

package de.metas.servicerepair.project.repository;

import com.google.common.collect.ImmutableSet;
import de.metas.inout.InOutAndLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.servicerepair.project.model.ServiceRepairProjectTask;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskId;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskStatus;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskType;
import de.metas.servicerepair.project.repository.requests.CreateProjectTaskRequest;
import de.metas.servicerepair.repository.model.I_C_Project_Repair_Task;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

@Repository
public
class ServiceRepairProjectTaskRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void createNew(@NonNull final CreateProjectTaskRequest request)
	{
		final I_C_Project_Repair_Task record = InterfaceWrapperHelper.newInstance(I_C_Project_Repair_Task.class);
		record.setC_Project_ID(request.getProjectId().getRepoId());
		record.setType(request.getType().getCode());
		record.setStatus(ServiceRepairProjectTaskStatus.NOT_STARTED.getCode());

		if (request.getCustomerReturnLineId() != null)
		{
			record.setCustomerReturn_InOut_ID(request.getCustomerReturnLineId().getInOutId().getRepoId());
			record.setCustomerReturn_InOutLine_ID(request.getCustomerReturnLineId().getInOutLineId().getRepoId());
		}

		record.setM_Product_ID(request.getProductId().getRepoId());
		record.setC_UOM_ID(request.getQtyRequired().getUomId().getRepoId());
		record.setQtyRequired(request.getQtyRequired().toBigDecimal());
		record.setQtyReserved(BigDecimal.ZERO);
		record.setQtyConsumed(BigDecimal.ZERO);

		InterfaceWrapperHelper.save(record);
	}

	public ServiceRepairProjectTask getById(@NonNull final ServiceRepairProjectTaskId taskId)
	{
		final I_C_Project_Repair_Task record = getRecordById(taskId);
		return toServiceRepairProjectTask(record);
	}

	private I_C_Project_Repair_Task getRecordById(@NonNull final ServiceRepairProjectTaskId taskId)
	{
		return InterfaceWrapperHelper.load(taskId, I_C_Project_Repair_Task.class);
	}

	private static ServiceRepairProjectTask toServiceRepairProjectTask(final I_C_Project_Repair_Task record)
	{
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());

		return ServiceRepairProjectTask.builder()
				.id(ServiceRepairProjectTaskId.ofRepoId(record.getC_Project_ID(), record.getC_Project_Repair_Task_ID()))
				.type(ServiceRepairProjectTaskType.ofCode(record.getType()))
				.status(ServiceRepairProjectTaskStatus.ofCode(record.getStatus()))
				//
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.qtyRequired(Quantitys.create(record.getQtyRequired(), uomId))
				.qtyReserved(Quantitys.create(record.getQtyReserved(), uomId))
				.qtyConsumed(Quantitys.create(record.getQtyConsumed(), uomId))
				//
				.customerReturnLineId(InOutAndLineId.ofRepoIdOrNull(record.getCustomerReturn_InOut_ID(), record.getCustomerReturn_InOutLine_ID()))
				.repairOrderId(PPOrderId.ofRepoIdOrNull(record.getRepair_Order_ID()))
				//
				.build();
	}

	private static void updateRecord(
			@NonNull final I_C_Project_Repair_Task record,
			@NonNull final ServiceRepairProjectTask from)
	{
		record.setType(from.getType().getCode());
		record.setStatus(from.getStatus().getCode());

		final InOutAndLineId customerReturnLineId = from.getCustomerReturnLineId();
		record.setCustomerReturn_InOut_ID(customerReturnLineId != null ? customerReturnLineId.getInOutId().getRepoId() : -1);
		record.setCustomerReturn_InOutLine_ID(customerReturnLineId != null ? customerReturnLineId.getInOutLineId().getRepoId() : -1);

		record.setM_Product_ID(from.getProductId().getRepoId());
		record.setC_UOM_ID(from.getUomId().getRepoId());
		record.setQtyRequired(from.getQtyRequired().toBigDecimal());
		record.setQtyReserved(from.getQtyReserved().toBigDecimal());
		record.setQtyConsumed(from.getQtyConsumed().toBigDecimal());
	}

	public ServiceRepairProjectTask changeById(
			@NonNull final ServiceRepairProjectTaskId taskId,
			@NonNull final UnaryOperator<ServiceRepairProjectTask> mapper)
	{
		final I_C_Project_Repair_Task record = getRecordById(taskId);
		final ServiceRepairProjectTask task = toServiceRepairProjectTask(record);

		final ServiceRepairProjectTask changedTask = mapper.apply(task);
		if (Objects.equals(task, changedTask))
		{
			return task;
		}

		updateRecord(record, changedTask);
		InterfaceWrapperHelper.saveRecord(record);

		return changedTask;
	}

	public ImmutableSet<ServiceRepairProjectTaskId> retainIdsOfTypeSpareParts(@NonNull final ImmutableSet<ServiceRepairProjectTaskId> taskIds)
	{
		if (taskIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final List<Integer> eligibleRepoIds = queryBL.createQueryBuilder(I_C_Project_Repair_Task.class)
				.addInArrayFilter(I_C_Project_Repair_Task.COLUMNNAME_C_Project_Repair_Task_ID, taskIds)
				.addEqualsFilter(I_C_Project_Repair_Task.COLUMNNAME_Type, ServiceRepairProjectTaskType.SPARE_PARTS.getCode())
				.create()
				.listIds();

		return taskIds.stream()
				.filter(taskId -> eligibleRepoIds.contains(taskId.getRepoId()))
				.collect(ImmutableSet.toImmutableSet());
	}
}
