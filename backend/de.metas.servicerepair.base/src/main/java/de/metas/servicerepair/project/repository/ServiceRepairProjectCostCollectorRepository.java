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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.handlingunits.HuId;
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantitys;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollector;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorId;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskId;
import de.metas.servicerepair.project.repository.requests.CreateProjectCostCollectorRequest;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_Repair_CostCollector;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public
class ServiceRepairProjectCostCollectorRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void createNew(@NonNull final CreateProjectCostCollectorRequest request)
	{
		final I_C_Project_Repair_CostCollector record = InterfaceWrapperHelper.newInstance(I_C_Project_Repair_CostCollector.class);
		record.setC_Project_ID(request.getTaskId().getProjectId().getRepoId());
		record.setC_Project_Repair_Task_ID(request.getTaskId().getRepoId());
		record.setM_Product_ID(request.getProductId().getRepoId());
		record.setC_UOM_ID(request.getUomId().getRepoId());
		record.setQtyReserved(request.getQtyReserved().toBigDecimal());
		record.setQtyConsumed(request.getQtyConsumed().toBigDecimal());
		record.setVHU_ID(HuId.toRepoId(request.getReservedVhuId()));
		InterfaceWrapperHelper.saveRecord(record);
	}

	public List<ServiceRepairProjectCostCollector> getAndDeleteByTaskIds(@NonNull final Set<ServiceRepairProjectTaskId> taskIds)
	{
		if (taskIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<I_C_Project_Repair_CostCollector> records = retrieveRecordsByTaskIds(taskIds);
		final ImmutableList<ServiceRepairProjectCostCollector> result = records.stream()
				.map(ServiceRepairProjectCostCollectorRepository::toServiceRepairProjectCostCollector)
				.collect(ImmutableList.toImmutableList());

		InterfaceWrapperHelper.deleteAll(records);

		return result;
	}

	private List<I_C_Project_Repair_CostCollector> retrieveRecordsByTaskIds(@NonNull final Set<ServiceRepairProjectTaskId> taskIds)
	{
		if (taskIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_C_Project_Repair_CostCollector.class)
				.addInArrayFilter(I_C_Project_Repair_CostCollector.COLUMN_C_Project_Repair_Task_ID, taskIds)
				.create()
				.list();
	}

	private ImmutableMap<ServiceRepairProjectCostCollectorId, I_C_Project_Repair_CostCollector> retrieveRecordsByIds(@NonNull final Set<ServiceRepairProjectCostCollectorId> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableMap.of();
		}

		return Maps.uniqueIndex(
				InterfaceWrapperHelper.loadByRepoIdAwares(ids, I_C_Project_Repair_CostCollector.class),
				ServiceRepairProjectCostCollectorRepository::extractId);
	}

	private static ServiceRepairProjectCostCollectorId extractId(@NonNull final I_C_Project_Repair_CostCollector record)
	{
		return ServiceRepairProjectCostCollectorId.ofRepoId(record.getC_Project_ID(), record.getC_Project_Repair_CostCollector_ID());
	}

	private static ServiceRepairProjectCostCollector toServiceRepairProjectCostCollector(@NonNull final I_C_Project_Repair_CostCollector record)
	{
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());
		return ServiceRepairProjectCostCollector.builder()
				.id(extractId(record))
				.taskId(ServiceRepairProjectTaskId.ofRepoIdOrNull(record.getC_Project_ID(), record.getC_Project_Repair_Task_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.qtyReserved(Quantitys.create(record.getQtyReserved(), uomId))
				.qtyConsumed(Quantitys.create(record.getQtyConsumed(), uomId))
				.reservedSparePartsVHUId(HuId.ofRepoIdOrNull(record.getVHU_ID()))
				.customerQuotationLineId(OrderAndLineId.ofRepoIdsOrNull(record.getQuotation_Order_ID(), record.getQuotation_OrderLine_ID()))
				.build();
	}

	public List<ServiceRepairProjectCostCollector> getCostCollectorsByProjectButNotInProposal(@NonNull final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_Project_Repair_CostCollector.class)
				.addEqualsFilter(I_C_Project_Repair_CostCollector.COLUMNNAME_C_Project_ID, projectId)
				.addEqualsFilter(I_C_Project_Repair_CostCollector.COLUMNNAME_Quotation_Order_ID, null)
				.orderBy(I_C_Project_Repair_CostCollector.COLUMN_C_Project_Repair_CostCollector_ID)
				.create()
				.stream()
				.map(ServiceRepairProjectCostCollectorRepository::toServiceRepairProjectCostCollector)
				.collect(ImmutableList.toImmutableList());
	}

	public void setCustomerQuotationToCostCollectors(final Map<ServiceRepairProjectCostCollectorId, OrderAndLineId> map)
	{
		if (map.isEmpty())
		{
			return;
		}

		final ImmutableMap<ServiceRepairProjectCostCollectorId, I_C_Project_Repair_CostCollector> recordsById = retrieveRecordsByIds(map.keySet());

		for (final ServiceRepairProjectCostCollectorId costCollectorId : map.keySet())
		{
			final I_C_Project_Repair_CostCollector record = recordsById.get(costCollectorId);
			if (record == null)
			{
				throw new AdempiereException("No record found for " + costCollectorId);
			}

			final OrderAndLineId customerQuotationLineId = map.get(costCollectorId);

			record.setQuotation_Order_ID(customerQuotationLineId.getOrderRepoId());
			record.setQuotation_OrderLine_ID(customerQuotationLineId.getOrderLineRepoId());
			InterfaceWrapperHelper.saveRecord(record);
		}
	}

	public List<ServiceRepairProjectCostCollector> getByQuotationLineIds(final Set<OrderAndLineId> quotationLineIds)
	{
		if (quotationLineIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_C_Project_Repair_CostCollector.class)
				.addInArrayFilter(I_C_Project_Repair_CostCollector.COLUMNNAME_Quotation_OrderLine_ID, OrderAndLineId.getOrderLineIds(quotationLineIds))
				.create()
				.stream()
				.map(ServiceRepairProjectCostCollectorRepository::toServiceRepairProjectCostCollector)
				.collect(ImmutableList.toImmutableList());
	}
}
