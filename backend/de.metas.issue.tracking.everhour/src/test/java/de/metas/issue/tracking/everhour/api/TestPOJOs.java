/*
 * #%L
 * de.metas.issue.tracking.everhour
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.issue.tracking.everhour.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.issue.tracking.everhour.api.model.Task;
import de.metas.issue.tracking.everhour.api.model.TimeRecord;
import org.junit.Test;

import java.io.IOException;

import static de.metas.issue.tracking.everhour.api.TestConstants.MOCK_DATE;
import static de.metas.issue.tracking.everhour.api.TestConstants.MOCK_ID;
import static de.metas.issue.tracking.everhour.api.TestConstants.MOCK_TIME;
import static de.metas.issue.tracking.everhour.api.TestConstants.MOCK_URL;
import static de.metas.issue.tracking.everhour.api.TestConstants.MOCK_USER_ID;
import static org.assertj.core.api.Assertions.assertThat;

public class TestPOJOs
{
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void task_serialize_deserialize() throws IOException
	{
		testSerializeDeserializeObject(getMockTask());
	}

	@Test
	public void timeRecord_serialize_deserialize() throws IOException
	{
		testSerializeDeserializeObject(getMockTimeRecord());
	}

	private TimeRecord getMockTimeRecord()
	{
		return TimeRecord.builder()
				.id(MOCK_ID)
				.date(MOCK_DATE)
				.userId(MOCK_USER_ID)
				.task(getMockTask())
				.time(MOCK_TIME)
				.build();
	}

	private Task getMockTask()
	{
		return Task.builder()
				.id(MOCK_ID)
				.url(MOCK_URL)
				.build();
	}

	private void testSerializeDeserializeObject(final Object value) throws IOException
	{
		final Class<?> valueClass = value.getClass();
		final String json = objectMapper.writeValueAsString(value);
		final Object value2 = objectMapper.readValue(json, valueClass);
		assertThat(value2).isEqualTo(value);
	}
}
