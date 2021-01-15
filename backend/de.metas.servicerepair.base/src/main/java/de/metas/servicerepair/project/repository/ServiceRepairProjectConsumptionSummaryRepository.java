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

import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.servicerepair.project.model.ServiceRepairProjectConsumptionSummary;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_Repair_Consumption_Summary;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.UnaryOperator;

@Repository
public
class ServiceRepairProjectConsumptionSummaryRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void change(
			@NonNull final ProjectId projectId,
			@NonNull final ProductId productId,
			@NonNull final UomId uomId,
			@NonNull final UnaryOperator<ServiceRepairProjectConsumptionSummary> mapper)
	{
		I_C_Project_Repair_Consumption_Summary record = getRecordByGrouppingKey(projectId, productId, uomId);
		final ServiceRepairProjectConsumptionSummary summary;
		if (record == null)
		{
			record = InterfaceWrapperHelper.newInstance(I_C_Project_Repair_Consumption_Summary.class);

			final Quantity zero = Quantitys.createZero(uomId);
			summary = ServiceRepairProjectConsumptionSummary.builder()
					.projectId(projectId)
					.productId(productId)
					.qtyReserved(zero)
					.qtyConsumed(zero)
					.build();
		}
		else
		{
			summary = toSummary(record);
		}

		final ServiceRepairProjectConsumptionSummary summaryChanged = mapper.apply(summary);
		if (Objects.equals(summary, summaryChanged))
		{
			return;
		}

		updateRecord(record, summaryChanged);
		InterfaceWrapperHelper.saveRecord(record);
	}

	private static ServiceRepairProjectConsumptionSummary toSummary(final I_C_Project_Repair_Consumption_Summary record)
	{
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());
		return ServiceRepairProjectConsumptionSummary.builder()
				.projectId(ProjectId.ofRepoId(record.getC_Project_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.qtyReserved(Quantitys.create(record.getQtyReserved(), uomId))
				.qtyConsumed(Quantitys.create(record.getQtyConsumed(), uomId))
				.build();
	}

	private static void updateRecord(
			@NonNull final I_C_Project_Repair_Consumption_Summary record,
			@NonNull final ServiceRepairProjectConsumptionSummary from)
	{
		record.setC_Project_ID(from.getProjectId().getRepoId());
		record.setM_Product_ID(from.getProductId().getRepoId());
		record.setC_UOM_ID(from.getUomId().getRepoId());
		record.setQtyReserved(from.getQtyReserved().toBigDecimal());
		record.setQtyConsumed(from.getQtyConsumed().toBigDecimal());
	}

	@Nullable
	private I_C_Project_Repair_Consumption_Summary getRecordByGrouppingKey(
			@NonNull final ProjectId projectId,
			@NonNull final ProductId productId,
			@NonNull final UomId uomId)
	{
		return queryBL.createQueryBuilder(I_C_Project_Repair_Consumption_Summary.class)
				.addEqualsFilter(I_C_Project_Repair_Consumption_Summary.COLUMNNAME_C_Project_ID, projectId)
				.addEqualsFilter(I_C_Project_Repair_Consumption_Summary.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_C_Project_Repair_Consumption_Summary.COLUMNNAME_C_UOM_ID, uomId)
				.create()
				.firstOnly(I_C_Project_Repair_Consumption_Summary.class);
	}
}
