/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.project.workorder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class WOProjectRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	public WOProject getById(@NonNull final ProjectId projectId)
	{
		final I_C_Project record = InterfaceWrapperHelper.load(projectId, I_C_Project.class);
		if (record == null)
		{
			throw new AdempiereException("No project found for " + projectId);
		}

		return fromRecord(record)
				.orElseThrow(() -> new AdempiereException("Not a Work Order project: " + record));
	}

	@Nullable
	private IQueryBuilder<I_C_Project> toSqlQuery(@NonNull final WOProjectQuery query)
	{
		if (query.isNone())
		{
			return null;
		}

		final IQueryBuilder<I_C_Project> queryBuilder = queryBL
				.createQueryBuilder(I_C_Project.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project.COLUMNNAME_ProjectCategory, ProjectCategory.WorkOrderJob)
				.addInArrayFilter(I_C_Project.COLUMNNAME_C_Project_ID, query.getProjectIds());

		if (query.getOnlyCustomerId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_Project.COLUMNNAME_C_BPartner_ID, query.getOnlyCustomerId());
		}

		return queryBuilder;
	}

	public ImmutableSet<ProjectId> getAllActiveProjectIds()
	{
		return getActiveProjectIds(WOProjectQuery.ANY);
	}

	public ImmutableSet<ProjectId> getActiveProjectIds(@NonNull final WOProjectQuery query)
	{
		final IQueryBuilder<I_C_Project> sqlQuery = toSqlQuery(query);
		if (sqlQuery == null)
		{
			return ImmutableSet.of();
		}

		return sqlQuery.create().listIds(ProjectId::ofRepoId);
	}

	public List<WOProject> getAllActiveProjects(@NonNull final WOProjectQuery query)
	{
		final IQueryBuilder<I_C_Project> sqlQuery = toSqlQuery(query);
		if (sqlQuery == null)
		{
			return ImmutableList.of();
		}

		return sqlQuery
				.orderBy(I_C_Project.COLUMNNAME_C_Project_ID)
				.stream()
				.map(record -> fromRecord(record).orElse(null))
				.filter(Objects::nonNull) // shall not happen
				.collect(ImmutableList.toImmutableList());
	}

	private static Optional<WOProject> fromRecord(@NonNull final I_C_Project record)
	{
		if (!ProjectCategory.ofNullableCodeOrGeneral(record.getProjectCategory()).isWorkOrder())
		{
			return Optional.empty();
		}

		return Optional.of(
				WOProject.builder()
						.projectId(ProjectId.ofRepoId(record.getC_Project_ID()))
						.name(record.getName())
						.parentProjectId(ProjectId.ofRepoIdOrNull(record.getC_Project_Parent_ID()))
						.customerId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))
						.build());
	}
}
