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

import com.google.common.collect.ImmutableList;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.resource.ResourceGroupAndResourceId;
import de.metas.resource.ResourceGroupId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Value
public class BudgetProjectResources
{
	ProjectId projectId;
	ImmutableList<BudgetProjectResource> budgets;

	@Builder
	private BudgetProjectResources(
			@NonNull final ProjectId projectId,
			@NonNull final List<BudgetProjectResource> budgets)
	{
		this.projectId = projectId;
		this.budgets = ImmutableList.copyOf(budgets);

		if (!budgets.isEmpty())
		{
			final ImmutableList<BudgetProjectResource> resourcesFromOtherProjects = budgets.stream()
					.filter(resource -> !ProjectId.equals(resource.getProjectId(), projectId))
					.collect(ImmutableList.toImmutableList());
			if (!resourcesFromOtherProjects.isEmpty())
			{
				throw new AdempiereException("Expected all resources to be from project " + projectId + ": " + resourcesFromOtherProjects);
			}
		}
	}

	public Optional<BudgetProjectResource> findBudgetForResource(@NonNull final ResourceGroupAndResourceId groupAndResourceId)
	{
		BudgetProjectResource matchedByResourceGroup = null;
		for (final BudgetProjectResource budget : budgets)
		{
			if (budget.getResourceId() != null)
			{
				if (ResourceId.equals(budget.getResourceId(), groupAndResourceId.getResourceId()))
				{
					return Optional.of(budget);
				}
			}
			else
			{
				if (matchedByResourceGroup == null
						&& ResourceGroupId.equals(budget.getResourceGroupId(), groupAndResourceId.getResourceGroupId()))
				{
					matchedByResourceGroup = budget;
				}
			}
		}

		return Optional.ofNullable(matchedByResourceGroup);
	}

	public Stream<BudgetProjectResource> stream()
	{
		return budgets.stream();
	}

	public BudgetProjectResource getBudgetById(@NonNull final BudgetProjectResourceId id)
	{
		return stream()
				.filter(budget -> BudgetProjectResourceId.equals(budget.getId(), id))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No budget found for " + id));
	}

	@NonNull
	public Optional<BudgetProjectResource> findBudget(@NonNull final ResourceId resourceId)
	{
		return budgets.stream()
				.filter(budgetProjectResource -> budgetProjectResource.getResourceId() != null)
				.filter(budgetProjectResource -> budgetProjectResource.getResourceId().equals(resourceId))
				.findFirst();
	}
}
