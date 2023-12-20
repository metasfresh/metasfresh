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

package de.metas.project.workorder.resource;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.project.ProjectId;
import de.metas.project.workorder.step.WOProjectStepId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString(of = { "projectId", "resourcesById" })
public class WOProjectResources
{
	@Getter
	ProjectId projectId;
	private final ImmutableMap<WOProjectResourceId, WOProjectResource> resourcesById;

	@Builder
	private WOProjectResources(
			@NonNull final ProjectId projectId,
			@NonNull final List<WOProjectResource> resources)
	{
		assertResourcesAreMatchingProject(resources, projectId);

		this.projectId = projectId;
		this.resourcesById = Maps.uniqueIndex(resources, WOProjectResource::getWoProjectResourceId);
	}

	private static void assertResourcesAreMatchingProject(final @NonNull List<WOProjectResource> resources, final @NonNull ProjectId projectId)
	{
		if (resources.isEmpty())
		{
			return;
		}

		final ImmutableList<WOProjectResource> resourcesFromOtherProjects = resources.stream()
				.filter(resource -> !ProjectId.equals(resource.getProjectId(), projectId))
				.collect(ImmutableList.toImmutableList());
		if (!resourcesFromOtherProjects.isEmpty())
		{
			throw new AdempiereException("Expected all resources to be from project " + projectId + ": " + resourcesFromOtherProjects);
		}
	}

	public int size()
	{
		return resourcesById.size();
	}

	public WOProjectResource getById(@NonNull final WOProjectResourceId projectResourceId)
	{
		final WOProjectResource projectResource = resourcesById.get(projectResourceId);
		if (projectResource == null)
		{
			throw new AdempiereException("No project resource found for " + projectResourceId + " in " + this);
		}
		return projectResource;
	}

	public Stream<WOProjectResource> stream()
	{
		return resourcesById.values().stream();
	}

	public Stream<WOProjectResource> streamByStepId(@NonNull final WOProjectStepId stepId)
	{
		return stream().filter(resource -> WOProjectStepId.equals(resource.getWoProjectStepId(), stepId));
	}

	public WOProjectStepId getStepId(final WOProjectResourceId projectResourceId)
	{
		return getById(projectResourceId).getWoProjectStepId();
	}
}
