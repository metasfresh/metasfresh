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

package de.metas.project.interceptor;

import de.metas.i18n.AdMessageKey;
import de.metas.project.ProjectId;
import de.metas.project.service.ProjectRepository;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Project;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_Project.class)
public class C_Project
{
	private static final AdMessageKey MSG_CIRCULAR_REFERENCE = AdMessageKey.of("ProjectCircularReference");

	private final ProjectRepository projectRepository;

	public C_Project(@NonNull final ProjectRepository projectRepository)
	{
		this.projectRepository = projectRepository;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_Project.COLUMNNAME_C_Project_Parent_ID)
	public void assertNotCircularReference(@NonNull final I_C_Project projectRecord)
	{
		final ProjectId newParentId = ProjectId.ofRepoIdOrNull(projectRecord.getC_Project_Parent_ID());
		if (newParentId == null)
		{
			return;
		}

		final ProjectId projectId = ProjectId.ofRepoId(projectRecord.getC_Project_ID());

		if (projectRepository.getProjectIdsUpStream(newParentId).contains(projectId))
		{
			throw new AdempiereException(MSG_CIRCULAR_REFERENCE)
					.markAsUserValidationError()
					.setParameter("C_Project_Parent_ID", newParentId);
		}
	}
}
