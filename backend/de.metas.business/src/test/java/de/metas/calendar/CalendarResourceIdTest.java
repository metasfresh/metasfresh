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

package de.metas.calendar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.product.ResourceId;
import de.metas.resource.ResourceGroupId;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CalendarResourceIdTest
{
	@Test
	void testSerializeDeserialize() throws JsonProcessingException
	{
		final CalendarResourceId id = CalendarResourceId.ofRepoId(ResourceId.ofRepoId(123456));
		assertThat(id.getAsString()).isEqualTo("ResourceId-123456");

		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String jsonString = objectMapper.writeValueAsString(id);

		final CalendarResourceId idDeserialized = objectMapper.readValue(jsonString, CalendarResourceId.class);

		assertThat(idDeserialized).isEqualTo(id);
	}

	@Nested
	class ofRepoId_toRepoId
	{
		@Test
		void standardCase()
		{
			final ResourceId resourceId = ResourceId.ofRepoId(123456);
			final CalendarResourceId calendarResourceId = CalendarResourceId.ofRepoId(resourceId);
			assertThat(calendarResourceId.toRepoId(ResourceId.class)).isEqualTo(resourceId);
		}

		@Test
		void wrong_toRepoId_param()
		{
			final ResourceId resourceId = ResourceId.ofRepoId(123456);
			final CalendarResourceId calendarResourceId = CalendarResourceId.ofRepoId(resourceId);
			assertThatThrownBy(() -> calendarResourceId.toRepoId(ResourceGroupId.class))
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith("Cannot convert ");
		}
	}

}