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

package de.metas.project.budget;

import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project;
import org.springframework.stereotype.Repository;

@Repository
public class BudgetProjectRepository
{
	public BudgetProject getById(@NonNull final ProjectId projectId)
	{
		final I_C_Project record = InterfaceWrapperHelper.load(projectId, I_C_Project.class);
		return fromRecord(record);
	}

	public static BudgetProject fromRecord(final I_C_Project record)
	{
		final ProjectCategory projectCategory = ProjectCategory.ofNullableCodeOrGeneral(record.getProjectCategory());
		if (!projectCategory.isBudget())
		{
			throw new AdempiereException("Project " + record + " is not budget project");
		}

		return BudgetProject.builder()
				.projectId(ProjectId.ofRepoId(record.getC_Project_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.currencyId(CurrencyId.ofRepoId(record.getC_Currency_ID()))
				.build();
	}
}
