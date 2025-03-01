/*
 * #%L
 * de.metas.workflow.rest-api
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

package de.metas.workflow.rest_api.controller.v2.json;

import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacet;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupId;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonWorkflowLaunchersFacet
{
	@NonNull WorkflowLaunchersFacetGroupId groupId;
	@NonNull WorkflowLaunchersFacetId facetId;
	@NonNull String caption;
	long sortNo;
	boolean active;
	@Nullable Integer hitCount;

	public static JsonWorkflowLaunchersFacet of(
			@NonNull final WorkflowLaunchersFacet facet,
			@NonNull final WorkflowLaunchersFacetGroupId groupId,
			@NonNull final JsonOpts jsonOpts)
	{
		return JsonWorkflowLaunchersFacet.builder()
				.groupId(groupId)
				.facetId(facet.getFacetId())
				.caption(facet.getCaption().translate(jsonOpts.getAdLanguage()))
				.sortNo(facet.getSortNo())
				.active(facet.isActive())
				.hitCount(facet.getHitCount())
				.build();
	}
}
