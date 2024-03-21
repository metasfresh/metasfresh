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

import de.metas.organization.OrgId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectRepository;
import de.metas.project.budget.BudgetProjectService;
import de.metas.project.workorder.project.BudgetParentLinkResolver;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Project;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Project.class)
@Component
@RequiredArgsConstructor
public class C_Project
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final WOProjectService woProjectService;
	private final BudgetProjectService budgetProjectService;
	private final BudgetProjectRepository budgetProjectRepository;

	/**
	 * If the given work order project has no values for certain columns, then take them from the parent-project
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_C_Project.COLUMNNAME_C_Project_Parent_ID, I_C_Project.COLUMNNAME_ProjectCategory })
	public void updateFromParent(@NonNull final I_C_Project woProjectToBeUpdated)
	{
		if (!ProjectCategory.ofNullableCodeOrGeneral(woProjectToBeUpdated.getProjectCategory()).isWorkOrder())
		{
			return; // not our business
		}

		if (woProjectToBeUpdated.getC_Project_Parent_ID() <= 0)
		{
			return; // nothing for us to do
		}

		final BudgetProject parentProject = budgetProjectService.getById(ProjectId.ofRepoId(woProjectToBeUpdated.getC_Project_Parent_ID()))
				.orElseThrow(() -> new AdempiereException("No budget project record found for C_Project_ID = " + woProjectToBeUpdated.getC_Project_Parent_ID()));

		final WOProject woProject = woProjectService.getById(ProjectId.ofRepoId(woProjectToBeUpdated.getC_Project_ID()));

		woProjectService.syncWithParentAndUpdate(woProject, parentProject);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_Project.COLUMNNAME_C_Project_Reference_Ext })
	public void setParentLink(@NonNull final I_C_Project woProjectToBeUpdated)
	{
		if (!ProjectCategory.ofNullableCodeOrGeneral(woProjectToBeUpdated.getProjectCategory()).isWorkOrder())
		{
			return;
		}

		if (Check.isBlank(woProjectToBeUpdated.getC_Project_Reference_Ext()) || woProjectToBeUpdated.getC_Project_Parent_ID() > 0)
		{
			return;
		}

		BudgetParentLinkResolver.builder()
				.budgetProjectRepository(budgetProjectRepository)
				.sysConfigBL(sysConfigBL)
				.woProjectReferenceExt(woProjectToBeUpdated.getC_Project_Reference_Ext())
				.orgId(OrgId.ofRepoId(woProjectToBeUpdated.getAD_Org_ID()))
				.build()
				.resolve()
				.map(ProjectId::getRepoId)
				.ifPresent(woProjectToBeUpdated::setC_Project_Parent_ID);
	}
}
