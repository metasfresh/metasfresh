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

package de.metas.project.shared.interceptor;

import de.metas.project.ProjectCategory;
import de.metas.project.ProjectTypeId;
import de.metas.project.service.ProjectService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Project;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Interceptor(I_C_Project.class)
public class C_Project
{
	private final ProjectService projectService;

	public C_Project(@NonNull final ProjectService projectService)
	{
		this.projectService = projectService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW }, ifColumnsChanged = I_C_Project.COLUMNNAME_C_ProjectType_ID)
	public void setProjectCategoryFromProjectType(@NonNull final I_C_Project projectRecord)
	{
		final ProjectTypeId projectTypeId = ProjectTypeId.ofRepoIdOrNull(projectRecord.getC_ProjectType_ID());

		if (projectTypeId == null)
		{
			return;
		}

		final String projectCategoryCode = Optional.ofNullable(projectService.getProjectCategoryFromProjectType(projectTypeId))
				.map(ProjectCategory::getCode)
				.orElse(null);

		projectRecord.setProjectCategory(projectCategoryCode);
	}
}
