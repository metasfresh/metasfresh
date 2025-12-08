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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.inout.InOutAndLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantitys;
import de.metas.servicerepair.customerreturns.WarrantyCase;
import de.metas.servicerepair.project.model.ServiceRepairProjectTask;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskId;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskStatus;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskType;
import de.metas.servicerepair.project.repository.requests.CreateRepairProjectTaskRequest;
import de.metas.servicerepair.project.repository.requests.CreateSparePartsProjectTaskRequest;
import de.metas.servicerepair.repository.model.I_C_Project_Repair_Task;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;

@Repository
public
class ServiceRepairProjectTaskRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ServiceRepairProjectTaskId createNew(@NonNull final CreateRepairProjectTaskRequest request)
	{
		final I_C_Project_Repair_Task record = InterfaceWrapperHelper.newInstance(I_C_Project_Repair_Task.class);
		record.setC_Project_ID(request.getProjectId().getRepoId());
		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setType(ServiceRepairProjectTaskType.REPAIR_ORDER.getCode());
		record.setStatus(ServiceRepairProjectTaskStatus.NOT_STARTED.getCode());

		record.setCustomerReturn_InOut_ID(request.getCustomerReturnLineId().getInOutId().getRepoId());
		record.setCustomerReturn_InOutLine_ID(request.getCustomerReturnLineId().getInOutLineId().getRepoId());

		record.setM_Product_ID(request.getProductId().getRepoId());
		record.setM_AttributeSetInstance_ID(request.getAsiId().getRepoId());
		record.setIsWarrantyCase(request.getWarrantyCase().toBoolean());
		record.setC_UOM_ID(request.getQtyRequired().getUomId().getRepoId());
		record.setQtyRequired(request.getQtyRequired().toBigDecimal());
		record.setQtyReserved(BigDecimal.ZERO);
		record.setQtyConsumed(BigDecimal.ZERO);

		record.setRepair_VHU_ID(request.getRepairVhuId().getRepoId());

		saveRecord(record);

		return extractTaskId(record);
	}

	private void saveRecord(final I_C_Project_Repair_Task record)
	{
		InterfaceWrapperHelper.saveRecord(record);
	}

	public ServiceRepairProjectTask createNew(@NonNull final CreateSparePartsProjectTaskRequest request)
	{
		final I_C_Project_Repair_Task record = InterfaceWrapperHelper.newInstance(I_C_Project_Repair_Task.class);
		record.setC_Project_ID(request.getProjectId().getRepoId());
		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setType(ServiceRepairProjectTaskType.SPARE_PARTS.getCode());
		record.setStatus(ServiceRepairProjectTaskStatus.NOT_STARTED.getCode());

		record.setM_Product_ID(request.getProductId().getRepoId());
		record.setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
		record.setC_UOM_ID(request.getQtyRequired().getUomId().getRepoId());
		record.setQtyRequired(request.getQtyRequired().toBigDecimal());
		record.setQtyReserved(BigDecimal.ZERO);
		record.setQtyConsumed(BigDecimal.ZERO);

		saveRecord(record);

		return fromRecord(record);
	}

	public ServiceRepairProjectTask getById(@NonNull final ServiceRepairProjectTaskId taskId)
	{
		final I_C_Project_Repair_Task record = getRecordById(taskId);
		return fromRecord(record);
	}

	private I_C_Project_Repair_Task getRecordById(@NonNull final ServiceRepairProjectTaskId taskId)
	{
		return InterfaceWrapperHelper.load(taskId, I_C_Project_Repair_Task.class);
	}

	public List<ServiceRepairProjectTask> getByIds(@NonNull final Set<ServiceRepairProjectTaskId> taskIds)
	{
		if (taskIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<I_C_Project_Repair_Task> records = InterfaceWrapperHelper.loadByRepoIdAwares(taskIds, I_C_Project_Repair_Task.class);
		return records.stream()
				.map(ServiceRepairProjectTaskRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public List<ServiceRepairProjectTask> getByProjectId(@NonNull final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_Project_Repair_Task.class)
				.addEqualsFilter(I_C_Project_Repair_Task.COLUMNNAME_C_Project_ID, projectId)
				.create()
				.stream()
				.map(ServiceRepairProjectTaskRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	private static ServiceRepairProjectTask fromRecord(final I_C_Project_Repair_Task record)
	{
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());

		return ServiceRepairProjectTask.builder()
				.id(extractTaskId(record))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(record.getAD_Client_ID(), record.getAD_Org_ID()))
				.type(ServiceRepairProjectTaskType.ofCode(record.getType()))
				.status(ServiceRepairProjectTaskStatus.ofCode(record.getStatus()))
				//
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.asiId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()))
				.warrantyCase(WarrantyCase.ofBoolean(record.isWarrantyCase()))
				.qtyRequired(Quantitys.of(record.getQtyRequired(), uomId))
				.qtyReserved(Quantitys.of(record.getQtyReserved(), uomId))
				.qtyConsumed(Quantitys.of(record.getQtyConsumed(), uomId))
				//
				.customerReturnLineId(InOutAndLineId.ofRepoIdOrNull(record.getCustomerReturn_InOut_ID(), record.getCustomerReturn_InOutLine_ID()))
				.repairOrderId(PPOrderId.ofRepoIdOrNull(record.getRepair_Order_ID()))
				.isRepairOrderDone(record.isRepairOrderDone())
				.repairOrderSummary(StringUtils.trimBlankToNull(record.getRepairOrderSummary()))
				.repairServicePerformedId(ProductId.ofRepoIdOrNull(record.getRepairServicePerformed_Product_ID()))
				.repairVhuId(HuId.ofRepoIdOrNull(record.getRepair_VHU_ID()))
				//
				.build();
	}

	private static ServiceRepairProjectTaskId extractTaskId(final I_C_Project_Repair_Task record)
	{
		return ServiceRepairProjectTaskId.ofRepoId(record.getC_Project_ID(), record.getC_Project_Repair_Task_ID());
	}

	private static void updateRecord(
			@NonNull final I_C_Project_Repair_Task record,
			@NonNull final ServiceRepairProjectTask from)
	{
		record.setAD_Org_ID(from.getClientAndOrgId().getOrgId().getRepoId());

		record.setType(from.getType().getCode());
		record.setStatus(from.getStatus().getCode());

		final InOutAndLineId customerReturnLineId = from.getCustomerReturnLineId();
		record.setCustomerReturn_InOut_ID(customerReturnLineId != null ? customerReturnLineId.getInOutId().getRepoId() : -1);
		record.setCustomerReturn_InOutLine_ID(customerReturnLineId != null ? customerReturnLineId.getInOutLineId().getRepoId() : -1);

		record.setM_Product_ID(from.getProductId().getRepoId());
		record.setM_AttributeSetInstance_ID(from.getAsiId().getRepoId());
		record.setC_UOM_ID(from.getUomId().getRepoId());
		record.setQtyRequired(from.getQtyRequired().toBigDecimal());
		record.setQtyReserved(from.getQtyReserved().toBigDecimal());
		record.setQtyConsumed(from.getQtyConsumed().toBigDecimal());

		record.setRepair_Order_ID(PPOrderId.toRepoId(from.getRepairOrderId()));
		record.setRepairOrderSummary(from.getRepairOrderSummary());
		record.setRepairServicePerformed_Product_ID(ProductId.toRepoId(from.getRepairServicePerformedId()));
		record.setIsRepairOrderDone(from.isRepairOrderDone());
	}

	public void changeById(
			@NonNull final ServiceRepairProjectTaskId taskId,
			@NonNull final UnaryOperator<ServiceRepairProjectTask> mapper)
	{
		final I_C_Project_Repair_Task record = getRecordById(taskId);
		final ServiceRepairProjectTask task = fromRecord(record);

		final ServiceRepairProjectTask changedTask = mapper.apply(task);
		if (Objects.equals(task, changedTask))
		{
			return;
		}

		updateRecord(record, changedTask);
		saveRecord(record);
	}

	public void save(@NonNull final ServiceRepairProjectTask task)
	{
		final I_C_Project_Repair_Task record = getRecordById(task.getId());
		updateRecord(record, task);
		saveRecord(record);
	}

	public ImmutableSet<ServiceRepairProjectTaskId> retainIdsOfType(
			@NonNull final ImmutableSet<ServiceRepairProjectTaskId> taskIds,
			@NonNull final ServiceRepairProjectTaskType type)
	{
		if (taskIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final List<Integer> eligibleRepoIds = queryBL.createQueryBuilder(I_C_Project_Repair_Task.class)
				.addInArrayFilter(I_C_Project_Repair_Task.COLUMNNAME_C_Project_Repair_Task_ID, taskIds)
				.addEqualsFilter(I_C_Project_Repair_Task.COLUMNNAME_Type, type.getCode())
				.create()
				.listIds();

		return taskIds.stream()
				.filter(taskId -> eligibleRepoIds.contains(taskId.getRepoId()))
				.collect(ImmutableSet.toImmutableSet());
	}

	public Optional<ServiceRepairProjectTask> getTaskByRepairOrderId(
			@NonNull final ProjectId projectId,
			@NonNull final PPOrderId repairOrderId)
	{
		return getTaskIdByRepairOrderId(projectId, repairOrderId).map(this::getById);
	}

	public Optional<ServiceRepairProjectTaskId> getTaskIdByRepairOrderId(
			@NonNull final ProjectId projectId,
			@NonNull final PPOrderId repairOrderId)
	{
		return queryBL.createQueryBuilder(I_C_Project_Repair_Task.class)
				.addEqualsFilter(I_C_Project_Repair_Task.COLUMNNAME_Type, ServiceRepairProjectTaskType.REPAIR_ORDER.getCode())
				.addEqualsFilter(I_C_Project_Repair_Task.COLUMNNAME_C_Project_ID, projectId)
				.addEqualsFilter(I_C_Project_Repair_Task.COLUMNNAME_Repair_Order_ID, repairOrderId)
				.create()
				.firstOnlyOptional(I_C_Project_Repair_Task.class)
				.map(ServiceRepairProjectTaskRepository::extractTaskId);
	}
}
