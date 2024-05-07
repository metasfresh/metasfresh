/*
 * #%L
 * de.metas.business
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

package de.metas.project.service;

import com.google.common.collect.ImmutableList;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.project.ProjectAndLineId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectLine;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.servicerepair.project.CreateServiceOrRepairProjectRequest;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_ProjectLine;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Consumer;

@Repository
public class ProjectLineRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public List<ProjectLine> retrieveLines(@NonNull final ProjectId projectId)
	{
		return queryLineRecordsByProjectId(projectId)
				.stream()
				.map(this::toProjectLine)
				.collect(ImmutableList.toImmutableList());
	}

	private IQuery<I_C_ProjectLine> queryLineRecordsByProjectId(final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_ProjectLine.class)
				.addEqualsFilter(I_C_ProjectLine.COLUMNNAME_C_Project_ID, projectId)
				.orderBy(I_C_ProjectLine.COLUMNNAME_Line)
				.orderBy(I_C_ProjectLine.COLUMNNAME_C_ProjectLine_ID)
				.create();
	}

	public ProjectLine retrieveLineById(@NonNull final ProjectAndLineId projectLineId)
	{
		final I_C_ProjectLine record = retrieveLineRecordById(projectLineId);
		return toProjectLine(record);
	}

	private I_C_ProjectLine retrieveLineRecordById(@NonNull final ProjectAndLineId projectLineId)
	{
		return queryBL.createQueryBuilder(I_C_ProjectLine.class)
				.addEqualsFilter(I_C_ProjectLine.COLUMNNAME_C_Project_ID, projectLineId.getProjectId())
				.addEqualsFilter(I_C_ProjectLine.COLUMNNAME_C_ProjectLine_ID, projectLineId.getProjectLineRepoId())
				.create()
				.firstOnlyNotNull(I_C_ProjectLine.class);
	}

	public List<ProjectLine> retrieveLinesByOrderId(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_C_ProjectLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_ProjectLine.COLUMNNAME_C_Order_ID, orderId)
				.orderBy(I_C_ProjectLine.COLUMNNAME_C_ProjectLine_ID)
				.create()
				.stream()
				.map(this::toProjectLine)
				.collect(ImmutableList.toImmutableList());
	}

	private void save(final I_C_ProjectLine projectLine)
	{
		InterfaceWrapperHelper.saveRecord(projectLine);
	}

	private ProjectLine toProjectLine(final I_C_ProjectLine record)
	{
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());

		return ProjectLine.builder()
				.id(ProjectAndLineId.ofRepoId(record.getC_Project_ID(), record.getC_ProjectLine_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.plannedQty(Quantitys.of(record.getPlannedQty(), uomId))
				.committedQty(Quantitys.of(record.getCommittedQty(), uomId))
				.description(record.getDescription())
				.salesOrderLineId(OrderAndLineId.ofRepoIdsOrNull(record.getC_Order_ID(), record.getC_OrderLine_ID()))
				.build();
	}

	private void updateRecord(final I_C_ProjectLine record, final ProjectLine from)
	{
		final UomId uom = Quantity.getCommonUomIdOfAll(from.getPlannedQty(), from.getCommittedQty());
		record.setM_Product_ID(from.getProductId().getRepoId());
		record.setPlannedQty(from.getPlannedQty().toBigDecimal());
		record.setCommittedQty(from.getCommittedQty().toBigDecimal());
		record.setC_UOM_ID(uom.getRepoId());
		record.setDescription(from.getDescription());
	}

	public void createProjectLine(@NonNull final CreateProjectLineRequest request)
	{
		final I_C_ProjectLine record = InterfaceWrapperHelper.newInstance(I_C_ProjectLine.class);
		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setC_Project_ID(request.getProjectId().getRepoId());

		record.setC_ProjectIssue_ID(request.getProjectIssueId());
		record.setM_Product_ID(request.getProductId().getRepoId());
		record.setCommittedQty(request.getCommittedQty());
		record.setDescription(request.getDescription());

		save(record);
	}

	public void createProjectLine(
			@NonNull final CreateServiceOrRepairProjectRequest.ProjectLine request,
			@NonNull final ProjectId projectId,
			@NonNull final OrgId orgId)
	{
		final I_C_ProjectLine record = InterfaceWrapperHelper.newInstance(I_C_ProjectLine.class);
		record.setAD_Org_ID(orgId.getRepoId());
		record.setC_Project_ID(projectId.getRepoId());

		record.setM_Product_ID(request.getProductId().getRepoId());
		record.setPlannedQty(request.getPlannedQty().toBigDecimal());
		record.setC_UOM_ID(request.getPlannedQty().getUomId().getRepoId());

		save(record);
	}

	public ProjectLine changeProjectLine(
			@NonNull final ProjectAndLineId projectLineId,
			@NonNull final Consumer<ProjectLine> updater)
	{
		final I_C_ProjectLine record = retrieveLineRecordById(projectLineId);
		final ProjectLine line = toProjectLine(record);
		updater.accept(line);
		updateRecord(record, line);
		save(record);
		return line;
	}

	public void linkToOrderLine(
			@NonNull final ProjectAndLineId projectLineId,
			@NonNull final OrderAndLineId orderLineId)
	{
		final I_C_ProjectLine record = retrieveLineRecordById(projectLineId);
		record.setC_Order_ID(orderLineId.getOrderRepoId());
		record.setC_OrderLine_ID(orderLineId.getOrderLineRepoId());
		save(record);
	}

	public void markLinesAsProcessed(@NonNull final ProjectId projectId)
	{
		for (final I_C_ProjectLine lineRecord : queryLineRecordsByProjectId(projectId).list())
		{
			lineRecord.setProcessed(true);
			InterfaceWrapperHelper.saveRecord(lineRecord);
		}
	}

	public void markLinesAsNotProcessed(@NonNull final ProjectId projectId)
	{
		for (final I_C_ProjectLine lineRecord : queryLineRecordsByProjectId(projectId).list())
		{
			lineRecord.setProcessed(false);
			InterfaceWrapperHelper.saveRecord(lineRecord);
		}
	}

}
