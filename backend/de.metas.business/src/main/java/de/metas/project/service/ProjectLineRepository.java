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
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectLine;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ProjectLine;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectLineRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public List<ProjectLine> retrieveLines(@NonNull final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_ProjectLine.class)
				.addEqualsFilter(I_C_ProjectLine.COLUMNNAME_C_Project_ID, projectId)
				.orderBy(I_C_ProjectLine.COLUMNNAME_Line)
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
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.plannedQty(Quantitys.create(record.getPlannedQty(), uomId))
				.committedQty(Quantitys.create(record.getCommittedQty(), uomId))
				.description(record.getDescription())
				.build();
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
			@NonNull final CreateProjectRequest.ProjectLine request,
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

}
