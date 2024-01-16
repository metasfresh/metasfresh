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

package de.metas.project.workorder.callout;

import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectResource;
import de.metas.project.budget.BudgetProjectService;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Optional;

import static de.metas.project.ProjectConstants.DEFAULT_DURATION;

@Callout(I_C_Project_WO_Resource.class)
@Component
public class C_Project_WO_Resource
{
	private final WOProjectService woProjectService;
	private final BudgetProjectService budgetProjectService;

	public C_Project_WO_Resource(
			final WOProjectService woProjectService,
			final BudgetProjectService budgetProjectService)
	{
		this.woProjectService = woProjectService;
		this.budgetProjectService = budgetProjectService;
	}

	@PostConstruct
	public void postConstruct()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_Project_WO_Resource.COLUMNNAME_S_Resource_ID)
	public void onS_Resource_ID(@NonNull final I_C_Project_WO_Resource woResource)
	{
		updateBudget(woResource);
	}

	@CalloutMethod(columnNames = I_C_Project_WO_Resource.COLUMNNAME_AssignDateFrom)
	public void onAssignDateFrom(@NonNull final I_C_Project_WO_Resource woResource)
	{
		final Instant assignDateFrom = TimeUtil.asInstant(woResource.getAssignDateFrom());
		if (assignDateFrom != null)
		{
			final Instant assignDateTo = TimeUtil.asInstant(woResource.getAssignDateTo());
			if (assignDateTo == null || assignDateFrom.compareTo(assignDateTo) >= 0)
			{
				woResource.setAssignDateTo(TimeUtil.asTimestamp(assignDateFrom.plus(DEFAULT_DURATION)));
			}
		}
	}

	@CalloutMethod(columnNames = I_C_Project_WO_Resource.COLUMNNAME_AssignDateTo)
	public void onAssignDateTo(@NonNull final I_C_Project_WO_Resource woResource)
	{
		final Instant assignDateTo = TimeUtil.asInstant(woResource.getAssignDateTo());
		if (assignDateTo != null)
		{
			final Instant assignDateFrom = TimeUtil.asInstant(woResource.getAssignDateFrom());
			if (assignDateFrom == null || assignDateFrom.compareTo(assignDateTo) >= 0)
			{
				woResource.setAssignDateFrom(TimeUtil.asTimestamp(assignDateTo.minus(DEFAULT_DURATION)));
			}
		}
	}

	private void updateBudget(final I_C_Project_WO_Resource woResource)
	{
		final BudgetProjectResource budget = computeBudget(woResource).orElse(null);
		woResource.setBudget_Project_ID(budget != null ? budget.getProjectId().getRepoId() : -1);
		woResource.setC_Project_Resource_Budget_ID(budget != null ? budget.getId().getRepoId() : -1);
	}

	private Optional<BudgetProjectResource> computeBudget(final I_C_Project_WO_Resource woResource)
	{
		final ResourceId resourceId = ResourceId.ofRepoIdOrNull(woResource.getS_Resource_ID());
		if (resourceId == null)
		{
			return Optional.empty();
		}

		final ProjectId woProjectId = ProjectId.ofRepoId(woResource.getC_Project_ID());
		final WOProject woProject = woProjectService.getById(woProjectId);
		if (woProject.getProjectParentId() == null)
		{
			return Optional.empty();
		}

		final BudgetProject budgetProject = budgetProjectService.getById(woProject.getProjectParentId()).orElse(null);
		if (budgetProject == null)
		{
			return Optional.empty();
		}

		return budgetProjectService.findBudgetForResource(budgetProject.getProjectId(), resourceId);
	}
}
