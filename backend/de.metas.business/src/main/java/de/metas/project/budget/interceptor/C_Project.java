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
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectData;
import de.metas.project.budget.BudgetProjectRepository;
import de.metas.project.budget.BudgetProjectResourceRepository;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_Project.class)
public class C_Project
{
	private final BudgetProjectResourceRepository budgetProjectResourceRepository;
	private final BudgetProjectRepository budgetProjectRepository;

	public C_Project(
			@NonNull final BudgetProjectResourceRepository budgetProjectResourceRepository,
			@NonNull final BudgetProjectRepository budgetProjectRepository)
	{
		this.budgetProjectResourceRepository = budgetProjectResourceRepository;
		this.budgetProjectRepository = budgetProjectRepository;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_C_Project record)
	{
		if (!ProjectCategory.ofNullableCodeOrGeneral(record.getProjectCategory()).isBudget())
		{
			return;
		}

		if (InterfaceWrapperHelper.isValueChanged(record, I_C_Project.COLUMNNAME_AD_Org_ID, I_C_Project.COLUMNNAME_C_Currency_ID))
		{
			final BudgetProject project = budgetProjectRepository.fromRecord(record)
					.orElseThrow(() -> new AdempiereException("Not a valid budget project"));

			final BudgetProjectData budgetProjectData = project.getBudgetProjectData();
			budgetProjectResourceRepository.updateAllByProjectId(project.getProjectId(), budgetProjectData.getOrgId(), budgetProjectData.getCurrencyId());
		}
	}

}
