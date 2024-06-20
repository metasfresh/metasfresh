/*
 * #%L
 * de.metas.workflow.rest-api
 * %%
 * Copyright (C) 2024 metas GmbH
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
import de.metas.i18n.TranslatableStrings;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacet;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroup;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupId;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class JsonWorkflowLaunchersFacetGroupTest
{
	@Test
	void of_assert_is_correctly_sorted()
	{
		final WorkflowLaunchersFacetGroupId groupId = WorkflowLaunchersFacetGroupId.ofString("groupId");
		final WorkflowLaunchersFacetGroup group = WorkflowLaunchersFacetGroup.builder()
				.id(groupId)
				.caption(TranslatableStrings.anyLanguage("group name"))
				.facets(ImmutableList.of(
						WorkflowLaunchersFacet.builder()
								.facetId(WorkflowLaunchersFacetId.ofString(groupId, "1"))
								.caption(TranslatableStrings.date(LocalDate.parse("2023-11-22")))
								.sortNo(1700604000000L)
								.build(),
						WorkflowLaunchersFacet.builder()
								.facetId(WorkflowLaunchersFacetId.ofString(groupId, "2"))
								.caption(TranslatableStrings.date(LocalDate.parse("2024-01-30")))
								.sortNo(1706565600000L)
								.build()
				))
				.build();
		final JsonOpts jsonOpts = JsonOpts.builder()
				.adLanguage("en_US")
				.build();

		assertThat(JsonWorkflowLaunchersFacetGroup.of(group, jsonOpts))
				.isEqualTo(JsonWorkflowLaunchersFacetGroup.builder()
						.groupId(groupId)
						.caption("group name")
						.facets(ImmutableList.of(
								JsonWorkflowLaunchersFacet.builder()
										.groupId(groupId)
										.facetId(WorkflowLaunchersFacetId.ofString(groupId, "1"))
										.caption("11/22/2023")
										.sortNo(1700604000000L)
										.build(),
								JsonWorkflowLaunchersFacet.builder()
										.groupId(groupId)
										.facetId(WorkflowLaunchersFacetId.ofString(groupId, "2"))
										.caption("01/30/2024")
										.sortNo(1706565600000L)
										.build()
						))
						.build());
	}
}