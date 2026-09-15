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

import de.metas.project.ProjectId;
import de.metas.project.ProjectValue;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
public class ProjectRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public static ProjectRepository newInstanceForUnitTesting()
	{
		return new ProjectRepository();
	}

	public I_C_Project getById(@NonNull final ProjectId id)
	{
		return InterfaceWrapperHelper.load(id, I_C_Project.class);
	}

	@Nullable
	public ProjectId getIdByValueOrNull(@NonNull final String value)
	{
		return queryBL.createQueryBuilder(I_C_Project.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project.COLUMNNAME_Value, value)
				.create()
				.firstId(ProjectId::ofRepoIdOrNull);
	}

	@Nullable
	public ProjectId getIdByValueOrNull(@NonNull final ProjectValue value)
	{
		return queryBL.createQueryBuilder(I_C_Project.class)
				.addEqualsFilter(I_C_Project.COLUMNNAME_Value, value.getAsString())
				.orderByDescending(I_C_Project.COLUMNNAME_IsActive)
				.orderByDescending(I_C_Project.COLUMNNAME_C_Project_ID)
				.create()
				.firstId(ProjectId::ofRepoIdOrNull);
	}

	public void save(@NonNull final I_C_Project project)
	{
		InterfaceWrapperHelper.saveRecord(project);
	}
}
