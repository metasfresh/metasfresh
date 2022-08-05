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
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Value
@Builder
public class BudgetProject
{
	@NonNull
	ProjectId projectId;

	@NonNull
	BudgetProjectData budgetProjectData;

	@NonNull
	BudgetProjectResources projectResources;

	@NonNull
	public Optional<BudgetProjectResource> findBudgetResource(@NonNull final ResourceId resourceId)
	{
		return projectResources.findBudget(resourceId);
	}

	@NonNull
	public <T> List<T> mapResourceIds(@NonNull final Function<BudgetProjectResourceId, T> mappingFunction)
	{
		return projectResources.getBudgets()
				.stream()
				.map(BudgetProjectResource::getId)
				.map(mappingFunction)
				.collect(ImmutableList.toImmutableList());
	}
}
