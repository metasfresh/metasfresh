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

import com.google.common.collect.Maps;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.servicerepair.project.model.ServiceRepairProjectConsumptionSummary;
import de.metas.servicerepair.repository.model.I_C_Project_Repair_Consumption_Summary;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

@Repository
public
class ServiceRepairProjectConsumptionSummaryRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void change(
			@NonNull final ServiceRepairProjectConsumptionSummary.GroupingKey groupingKey,
			@NonNull final UnaryOperator<ServiceRepairProjectConsumptionSummary> mapper)
	{
		I_C_Project_Repair_Consumption_Summary record = retrieveRecordByGroupingKey(groupingKey);
		final ServiceRepairProjectConsumptionSummary summary;
		if (record == null)
		{
			record = InterfaceWrapperHelper.newInstance(I_C_Project_Repair_Consumption_Summary.class);

			final Quantity zero = Quantitys.zero(groupingKey.getUomId());
			summary = ServiceRepairProjectConsumptionSummary.builder()
					.groupingKey(groupingKey)
					.qtyReserved(zero)
					.qtyConsumed(zero)
					.build();
		}
		else
		{
			summary = fromRecord(record);
		}

		final ServiceRepairProjectConsumptionSummary summaryChanged = mapper.apply(summary);
		if (Objects.equals(summary, summaryChanged))
		{
			return;
		}

		updateRecord(record, summaryChanged);
		InterfaceWrapperHelper.saveRecord(record);
	}

	private static ServiceRepairProjectConsumptionSummary fromRecord(final I_C_Project_Repair_Consumption_Summary record)
	{
		final ServiceRepairProjectConsumptionSummary.GroupingKey groupingKey = extractGroupingKey(record);
		return ServiceRepairProjectConsumptionSummary.builder()
				.groupingKey(groupingKey)
				.qtyReserved(Quantitys.of(record.getQtyReserved(), groupingKey.getUomId()))
				.qtyConsumed(Quantitys.of(record.getQtyConsumed(), groupingKey.getUomId()))
				.build();
	}

	private static ServiceRepairProjectConsumptionSummary.GroupingKey extractGroupingKey(final I_C_Project_Repair_Consumption_Summary record)
	{
		return ServiceRepairProjectConsumptionSummary.GroupingKey.builder()
				.projectId(ProjectId.ofRepoId(record.getC_Project_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.uomId(UomId.ofRepoId(record.getC_UOM_ID()))
				.build();
	}

	private static void updateRecord(
			@NonNull final I_C_Project_Repair_Consumption_Summary record,
			@NonNull final ServiceRepairProjectConsumptionSummary from)
	{
		record.setC_Project_ID(from.getGroupingKey().getProjectId().getRepoId());
		record.setM_Product_ID(from.getGroupingKey().getProductId().getRepoId());
		record.setC_UOM_ID(from.getGroupingKey().getUomId().getRepoId());
		record.setQtyReserved(from.getQtyReserved().toBigDecimal());
		record.setQtyConsumed(from.getQtyConsumed().toBigDecimal());
	}

	@Nullable
	private I_C_Project_Repair_Consumption_Summary retrieveRecordByGroupingKey(@NonNull final ServiceRepairProjectConsumptionSummary.GroupingKey groupingKey)
	{
		return queryBL.createQueryBuilder(I_C_Project_Repair_Consumption_Summary.class)
				.addEqualsFilter(I_C_Project_Repair_Consumption_Summary.COLUMNNAME_C_Project_ID, groupingKey.getProjectId())
				.addEqualsFilter(I_C_Project_Repair_Consumption_Summary.COLUMNNAME_M_Product_ID, groupingKey.getProductId())
				.addEqualsFilter(I_C_Project_Repair_Consumption_Summary.COLUMNNAME_C_UOM_ID, groupingKey.getUomId())
				.create()
				.firstOnly(I_C_Project_Repair_Consumption_Summary.class);
	}

	private List<I_C_Project_Repair_Consumption_Summary> retrieveRecordByProjectId(@NonNull final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_Project_Repair_Consumption_Summary.class)
				.addEqualsFilter(I_C_Project_Repair_Consumption_Summary.COLUMNNAME_C_Project_ID, projectId)
				.create()
				.listImmutable(I_C_Project_Repair_Consumption_Summary.class);
	}

	public void saveProject(
			@NonNull final ProjectId projectId,
			@NonNull final Collection<ServiceRepairProjectConsumptionSummary> newValues)
	{
		if (newValues.stream().anyMatch(newValue -> !ProjectId.equals(newValue.getGroupingKey().getProjectId(), projectId)))
		{
			throw new AdempiereException("all values shall match " + projectId + ": " + newValues);
		}

		final HashMap<ServiceRepairProjectConsumptionSummary.GroupingKey, I_C_Project_Repair_Consumption_Summary> existingRecordsByGroupingKey = new HashMap<>(Maps.uniqueIndex(
				retrieveRecordByProjectId(projectId),
				ServiceRepairProjectConsumptionSummaryRepository::extractGroupingKey));

		for (final ServiceRepairProjectConsumptionSummary newValue : newValues)
		{
			final I_C_Project_Repair_Consumption_Summary existingRecord = existingRecordsByGroupingKey.remove(newValue.getGroupingKey());
			final I_C_Project_Repair_Consumption_Summary record = existingRecord != null
					? existingRecord
					: InterfaceWrapperHelper.newInstance(I_C_Project_Repair_Consumption_Summary.class);

			updateRecord(record, newValue);
			InterfaceWrapperHelper.saveRecord(record);
		}

		InterfaceWrapperHelper.deleteAll(existingRecordsByGroupingKey.values());
	}
}
