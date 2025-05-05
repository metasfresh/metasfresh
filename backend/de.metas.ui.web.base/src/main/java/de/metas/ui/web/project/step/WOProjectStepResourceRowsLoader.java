/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.ui.web.project.step;

import com.google.common.collect.ImmutableList;
import de.metas.project.ProjectId;
import de.metas.project.workorder.WOProjectStepResource;
import de.metas.project.workorder.stepresource.WOProjectStepResourceService;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class WOProjectStepResourceRowsLoader
{
	@NonNull WOProjectStepResourceService woProjectStepResourceService;

	@NonNull WOProjectStepRowInvalidateService woProjectStepRowInvalidateService;

	@NonNull ProjectId projectId;

	@NonNull
	public WOProjectStepResourceRows loadRows()
	{
		final ImmutableList<WOProjectStepResource> unresolvedWOStepsForWOProject = woProjectStepResourceService.getUnresolvedStepResourcesForWOProject(projectId);

		final ImmutableList<WOProjectStepResourceRow> woProjectStepResourceRows = unresolvedWOStepsForWOProject.stream()
				.map(WOProjectStepResourceRowsLoader::buildFromWOStepResource)
				.collect(ImmutableList.toImmutableList());

		return WOProjectStepResourceRows.builder()
				.woProjectStepRowInvalidateService(woProjectStepRowInvalidateService)
				.rows(woProjectStepResourceRows)
				.build();
	}

	@NonNull
	static WOProjectStepResourceRow buildFromWOStepResource(@NonNull final WOProjectStepResource woProjectStepResource)
	{
		return WOProjectStepResourceRow.builder()
				.name(woProjectStepResource.getStepName())
				.stepId(woProjectStepResource.getStepId())
				.projectId(woProjectStepResource.getProjectId())
				.resourceId(woProjectStepResource.getResourceId())
				.resolvedHours(woProjectStepResource.getResolvedHours().toHours())
				.reservedHours(woProjectStepResource.getTotalHours().toHours())
				.rowId(DocumentId.of(woProjectStepResource.getResourceId()))
				.build();
	}
}
