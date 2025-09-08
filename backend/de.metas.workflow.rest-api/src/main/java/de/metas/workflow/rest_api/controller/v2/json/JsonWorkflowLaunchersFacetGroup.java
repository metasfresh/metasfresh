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

import com.google.common.collect.ImmutableList;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroup;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Comparator;

@Value
@Builder
@Jacksonized
public class JsonWorkflowLaunchersFacetGroup
{
	@NonNull WorkflowLaunchersFacetGroupId groupId;
	@NonNull String caption;
	@NonNull ImmutableList<JsonWorkflowLaunchersFacet> facets;

	public static JsonWorkflowLaunchersFacetGroup of(final WorkflowLaunchersFacetGroup group, final JsonOpts jsonOpts)
	{
		return builder()
				.groupId(group.getId())
				.caption(group.getCaption().translate(jsonOpts.getAdLanguage()))
				.facets(group.getFacets().stream()
						.map(facet -> JsonWorkflowLaunchersFacet.of(facet, group.getId(), jsonOpts))
						.sorted(Comparator.comparing(JsonWorkflowLaunchersFacet::getSortNo)
								.thenComparing(JsonWorkflowLaunchersFacet::getCaption))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}
