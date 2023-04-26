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

package de.metas.project.budget.interceptor;

import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectRepository;
import de.metas.project.budget.BudgetProjectResourceRepository;
import de.metas.project.budget.BudgetProjectService;
import de.metas.project.workorder.project.WOProjectService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Interceptor(I_C_Project.class)
public class C_Project
{
	private final BudgetProjectResourceRepository budgetProjectResourceRepository;
	private final WOProjectService woProjectService;
	private final BudgetProjectService budgetProjectService;

	public C_Project(
			@NonNull final BudgetProjectResourceRepository budgetProjectResourceRepository,
			@NonNull final WOProjectService woProjectService,
			@NonNull final BudgetProjectService budgetProjectService)
	{
		this.budgetProjectResourceRepository = budgetProjectResourceRepository;
		this.woProjectService = woProjectService;
		this.budgetProjectService = budgetProjectService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(final I_C_Project record)
	{
		if (!ProjectCategory.ofNullableCodeOrGeneral(record.getProjectCategory()).isBudget())
		{
			return;
		}

		if (InterfaceWrapperHelper.isValueChanged(record, I_C_Project.COLUMNNAME_AD_Org_ID, I_C_Project.COLUMNNAME_C_Currency_ID))
		{
			final BudgetProject project = Optional.ofNullable(BudgetProjectRepository.fromRecord(record))
					.orElseThrow(() -> new AdempiereException("Not a valid budget project"));

			budgetProjectResourceRepository.updateAllByProjectId(project.getProjectId(), project.getOrgId(), project.getCurrencyId());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged =
			{ I_C_Project.COLUMNNAME_BPartnerDepartment,
					I_C_Project.COLUMNNAME_SalesRep_ID,
					I_C_Project.COLUMNNAME_Specialist_Consultant_ID,
					I_C_Project.COLUMNNAME_C_Project_Reference_Ext,
					I_C_Project.COLUMNNAME_InternalPriority })
	public void propagateValuesToWOChildProjects(@NonNull final I_C_Project record)
	{
		if (!ProjectCategory.ofNullableCodeOrGeneral(record.getProjectCategory()).isBudget())
		{
			return;
		}

		final BudgetProject budgetProject = budgetProjectService.getById(ProjectId.ofRepoId(record.getC_Project_ID()))
				.orElseThrow(() -> new AdempiereException("No record found for C_Project_ID = " + record.getC_Project_ID()));

		woProjectService.updateWOChildProjectsFromParent(budgetProject);
	}
}
