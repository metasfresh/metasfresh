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
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectLine;
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

	private ProjectLine toProjectLine(final I_C_ProjectLine record)
	{
		return ProjectLine.builder()
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.committedQty(record.getCommittedQty())
				.description(record.getDescription())
				.build();
	}
}
