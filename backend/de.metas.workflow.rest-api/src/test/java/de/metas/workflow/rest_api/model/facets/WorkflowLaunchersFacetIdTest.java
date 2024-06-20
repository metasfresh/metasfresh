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

package de.metas.workflow.rest_api.model.facets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.bpartner.BPartnerId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class WorkflowLaunchersFacetIdTest
{
	@Test
	void testSerializeDeserialize() throws JsonProcessingException
	{
		testSerializeDeserialize(WorkflowLaunchersFacetId.ofString(WorkflowLaunchersFacetGroupId.ofString("g1"), "facet1"));
		testSerializeDeserialize(WorkflowLaunchersFacetId.ofString(WorkflowLaunchersFacetGroupId.ofString("g1"), "a_b_c"));
		testSerializeDeserialize(WorkflowLaunchersFacetId.ofId(WorkflowLaunchersFacetGroupId.ofString("g1"), BPartnerId.ofRepoId(123)));
		testSerializeDeserialize(WorkflowLaunchersFacetId.ofLocalDate(WorkflowLaunchersFacetGroupId.ofString("g1"), LocalDate.parse("2023-04-05")));
	}

	private static void testSerializeDeserialize(final WorkflowLaunchersFacetId facetId) throws JsonProcessingException
	{
		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = jsonObjectMapper.writeValueAsString(facetId);
		final WorkflowLaunchersFacetId facetId2 = jsonObjectMapper.readValue(json, WorkflowLaunchersFacetId.class);
		assertThat(facetId2).isEqualTo(facetId);
	}

	@Test
	void getAsId()
	{
		final WorkflowLaunchersFacetId facetId = WorkflowLaunchersFacetId.ofId(WorkflowLaunchersFacetGroupId.ofString("g1"), BPartnerId.ofRepoId(123));
		assertThat(facetId.getAsId(BPartnerId.class)).isEqualTo(BPartnerId.ofRepoId(123));
	}

	@Test
	void getAsLocalDate()
	{
		final WorkflowLaunchersFacetId facetId = WorkflowLaunchersFacetId.ofLocalDate(WorkflowLaunchersFacetGroupId.ofString("g1"), LocalDate.parse("2023-04-05"));
		assertThat(facetId.getAsLocalDate()).isEqualTo("2023-04-05");
	}
}