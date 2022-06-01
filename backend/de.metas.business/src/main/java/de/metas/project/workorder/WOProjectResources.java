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

package de.metas.project.workorder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.Set;

@Value
public class WOProjectResources
{
	ProjectId projectId;
	ImmutableList<WOProjectResource> resources;

	@Builder
	private WOProjectResources(
			@NonNull final ProjectId projectId,
			@NonNull final List<WOProjectResource> resources)
	{
		this.projectId = projectId;
		this.resources = ImmutableList.copyOf(resources);

		if (!resources.isEmpty())
		{
			final ImmutableList<WOProjectResource> resourcesFromOtherProjects = resources.stream()
					.filter(resource -> !ProjectId.equals(resource.getProjectId(), projectId))
					.collect(ImmutableList.toImmutableList());
			if (!resourcesFromOtherProjects.isEmpty())
			{
				throw new AdempiereException("Expected all resources to be from project " + projectId + ": " + resourcesFromOtherProjects);
			}
		}
	}

	public Set<ResourceId> getResourceIds()
	{
		return resources.stream().map(WOProjectResource::getResourceId).collect(ImmutableSet.toImmutableSet());
	}
}
