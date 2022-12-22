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

package de.metas.project.workorder.interceptor;

import de.metas.project.ProjectCategory;
import de.metas.project.workorder.project.WOProjectService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Project;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Project.class)
@Component
public class C_Project
{
	private final WOProjectService woProjectService;
	public C_Project( @NonNull final WOProjectService woProjectService)
	{
		this.woProjectService = woProjectService;
	}

	/**
	 * If the given work order project has no values for certain columns, then take them from the parent-project
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_Project.COLUMNNAME_C_Project_Parent_ID, I_C_Project.COLUMNNAME_ProjectCategory })
	public void updateFromParent(@NonNull final I_C_Project project)
	{
		if (!ProjectCategory.ofNullableCodeOrGeneral(project.getProjectCategory()).isWorkOrder())
		{
			return; // not our business
		}
		if (project.getC_Project_Parent_ID() <= 0)
		{
			return; // nothing for us to do
		}

		woProjectService.updateWOProjectFromParent(project);
	}
}
