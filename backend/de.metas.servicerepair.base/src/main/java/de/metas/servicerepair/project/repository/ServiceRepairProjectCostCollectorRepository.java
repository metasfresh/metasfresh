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
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.handlingunits.HuId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantitys;
import de.metas.servicerepair.customerreturns.WarrantyCase;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollector;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorId;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorType;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskId;
import de.metas.servicerepair.project.repository.requests.CreateProjectCostCollectorRequest;
import de.metas.servicerepair.project.service.commands.createQuotationFromProjectCommand.QuotationLineIdsByCostCollectorIdIndex;
import de.metas.servicerepair.repository.model.I_C_Project_Repair_CostCollector;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderAndCostCollectorId;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public
class ServiceRepairProjectCostCollectorRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ServiceRepairProjectCostCollector createNew(@NonNull final CreateProjectCostCollectorRequest request)
	{
		final I_C_Project_Repair_CostCollector record = InterfaceWrapperHelper.newInstance(I_C_Project_Repair_CostCollector.class);
		record.setC_Project_ID(request.getTaskId().getProjectId().getRepoId());
		record.setC_Project_Repair_Task_ID(request.getTaskId().getRepoId());
		record.setType(request.getType().getCode());
		record.setM_Product_ID(request.getProductId().getRepoId());
		record.setM_AttributeSetInstance_ID(request.getAsiId().getRepoId());
		record.setIsWarrantyCase(request.getWarrantyCase().toBoolean());
		record.setC_UOM_ID(request.getUomId().getRepoId());
		record.setQtyReserved(request.getQtyReserved().toBigDecimal());
		record.setQtyConsumed(request.getQtyConsumed().toBigDecimal());
		record.setVHU_ID(HuId.toRepoId(request.getReservedVhuId()));
		if (request.getRepairOrderCostCollectorId() != null)
		{
			record.setFrom_Rapair_Order_ID(request.getRepairOrderCostCollectorId().getOrderId().getRepoId());
			record.setFrom_Repair_Cost_Collector_ID(request.getRepairOrderCostCollectorId().getCostCollectorId().getRepoId());
		}

		InterfaceWrapperHelper.saveRecord(record);

		return fromRecord(record);
	}

	public List<ServiceRepairProjectCostCollector> getByTaskId(@NonNull final ServiceRepairProjectTaskId taskId)
	{
		return getByTaskIds(ImmutableSet.of(taskId));
	}

	public List<ServiceRepairProjectCostCollector> getByTaskIds(@NonNull final Set<ServiceRepairProjectTaskId> taskIds)
	{
		if (taskIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_C_Project_Repair_CostCollector.class)
				.addInArrayFilter(I_C_Project_Repair_CostCollector.COLUMNNAME_C_Project_Repair_Task_ID, taskIds)
				.create()
				.stream()
				.map(ServiceRepairProjectCostCollectorRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
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

	private static ServiceRepairProjectCostCollector fromRecord(@NonNull final I_C_Project_Repair_CostCollector record)
	{
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());
		return ServiceRepairProjectCostCollector.builder()
				.id(extractId(record))
				.taskId(ServiceRepairProjectTaskId.ofRepoId(record.getC_Project_ID(), record.getC_Project_Repair_Task_ID()))
				.type(ServiceRepairProjectCostCollectorType.ofCode(record.getType()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.asiId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()))
				.warrantyCase(WarrantyCase.ofBoolean(record.isWarrantyCase()))
				.qtyReserved(Quantitys.of(record.getQtyReserved(), uomId))
				.qtyConsumed(Quantitys.of(record.getQtyConsumed(), uomId))
				.vhuId(HuId.ofRepoIdOrNull(record.getVHU_ID()))
				.customerQuotationLineId(OrderAndLineId.ofRepoIdsOrNull(record.getQuotation_Order_ID(), record.getQuotation_OrderLine_ID()))
				.build();
	}

	public List<ServiceRepairProjectCostCollector> getByProjectIdButNotIncludedInCustomerQuotation(@NonNull final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_Project_Repair_CostCollector.class)
				.addEqualsFilter(I_C_Project_Repair_CostCollector.COLUMNNAME_C_Project_ID, projectId)
				.addEqualsFilter(I_C_Project_Repair_CostCollector.COLUMNNAME_Quotation_Order_ID, null) // not already included in customer proposal/quotation
				.orderBy(I_C_Project_Repair_CostCollector.COLUMNNAME_C_Project_Repair_CostCollector_ID)
				.create()
				.stream()
				.map(ServiceRepairProjectCostCollectorRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public List<ServiceRepairProjectCostCollector> getByProjectId(@NonNull final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_Project_Repair_CostCollector.class)
				.addEqualsFilter(I_C_Project_Repair_CostCollector.COLUMNNAME_C_Project_ID, projectId)
				.orderBy(I_C_Project_Repair_CostCollector.COLUMNNAME_C_Project_Repair_CostCollector_ID)
				.create()
				.stream()
				.map(ServiceRepairProjectCostCollectorRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public void setCustomerQuotation(@NonNull final QuotationLineIdsByCostCollectorIdIndex map)
	{
		if (map.isEmpty())
		{
			return;
		}

		final ImmutableMap<ServiceRepairProjectCostCollectorId, I_C_Project_Repair_CostCollector> recordsById = retrieveRecordsByIds(map.getCostCollectorIds());

		for (final ServiceRepairProjectCostCollectorId costCollectorId : map.getCostCollectorIds())
		{
			final I_C_Project_Repair_CostCollector record = recordsById.get(costCollectorId);
			if (record == null)
			{
				throw new AdempiereException("No record found for " + costCollectorId);
			}

			final OrderAndLineId customerQuotationLineId = map.getFirstOrderAndLineId(costCollectorId);

			record.setQuotation_Order_ID(customerQuotationLineId.getOrderRepoId());
			record.setQuotation_OrderLine_ID(customerQuotationLineId.getOrderLineRepoId());
			InterfaceWrapperHelper.saveRecord(record);
		}
	}

	public void unsetCustomerQuotation(
			@NonNull final ProjectId projectId,
			@NonNull final OrderId quotationId)
	{
		final List<I_C_Project_Repair_CostCollector> records = retrieveRecordsByQuotationId(projectId, quotationId);
		records.forEach(this::unsetQuotationLineAndSave);
	}

	private List<I_C_Project_Repair_CostCollector> retrieveRecordsByQuotationId(
			@NonNull final ProjectId projectId,
			@NonNull final OrderId quotationId)
	{
		return queryBL.createQueryBuilder(I_C_Project_Repair_CostCollector.class)
				.addInArrayFilter(I_C_Project_Repair_CostCollector.COLUMNNAME_C_Project_ID, projectId)
				.addInArrayFilter(I_C_Project_Repair_CostCollector.COLUMNNAME_Quotation_Order_ID, quotationId)
				.create()
				.list();
	}

	private void unsetQuotationLineAndSave(final I_C_Project_Repair_CostCollector record)
	{
		record.setQuotation_Order_ID(-1);
		record.setQuotation_OrderLine_ID(-1);
		InterfaceWrapperHelper.saveRecord(record);
	}

	public void unsetCustomerQuotationLine(@NonNull final OrderAndLineId quotationLineId)
	{
		final List<I_C_Project_Repair_CostCollector> records = retrieveRecordsByQuotationLineId(quotationLineId);
		records.forEach(this::unsetQuotationLineAndSave);
	}

	private List<I_C_Project_Repair_CostCollector> retrieveRecordsByQuotationLineId(final OrderAndLineId quotationLineId)
	{
		return queryBL.createQueryBuilder(I_C_Project_Repair_CostCollector.class)
				.addInArrayFilter(I_C_Project_Repair_CostCollector.COLUMNNAME_Quotation_Order_ID, quotationLineId.getOrderId())
				.addInArrayFilter(I_C_Project_Repair_CostCollector.COLUMNNAME_Quotation_OrderLine_ID, quotationLineId.getOrderLineId())
				.create()
				.list();
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
				.map(ServiceRepairProjectCostCollectorRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public Set<PPOrderAndCostCollectorId> retainExistingPPCostCollectorIds(
			@NonNull final ProjectId projectId,
			@NonNull final Set<PPOrderAndCostCollectorId> orderAndCostCollectorIds)
	{
		if (orderAndCostCollectorIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final Set<PPCostCollectorId> costCollectorIds = orderAndCostCollectorIds.stream().map(PPOrderAndCostCollectorId::getCostCollectorId).collect(Collectors.toSet());

		return queryBL.createQueryBuilder(I_C_Project_Repair_CostCollector.class)
				.addInArrayFilter(I_C_Project_Repair_CostCollector.COLUMNNAME_C_Project_ID, projectId)
				.addInArrayFilter(I_C_Project_Repair_CostCollector.COLUMNNAME_From_Repair_Cost_Collector_ID, costCollectorIds)
				.create()
				.stream()
				.map(record -> PPOrderAndCostCollectorId.ofRepoId(record.getFrom_Rapair_Order_ID(), record.getFrom_Repair_Cost_Collector_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	public boolean matchesByTaskAndProduct(
			@NonNull final ServiceRepairProjectTaskId taskId,
			@NonNull final ProductId productId)
	{
		return queryBL.createQueryBuilder(I_C_Project_Repair_CostCollector.class)
				.addInArrayFilter(I_C_Project_Repair_CostCollector.COLUMNNAME_C_Project_ID, taskId.getProjectId())
				.addInArrayFilter(I_C_Project_Repair_CostCollector.COLUMNNAME_C_Project_Repair_Task_ID, taskId)
				.addInArrayFilter(I_C_Project_Repair_CostCollector.COLUMNNAME_M_Product_ID, productId)
				.create()
				.anyMatch();
	}

	public void deleteByIds(@NonNull final Collection<ServiceRepairProjectCostCollectorId> ids)
	{
		if (ids.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_C_Project_Repair_CostCollector.class)
				.addInArrayFilter(I_C_Project_Repair_CostCollector.COLUMNNAME_C_Project_Repair_CostCollector_ID, ids)
				.create()
				.delete();
	}
}
