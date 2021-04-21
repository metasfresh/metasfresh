/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.elasticsearch.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import de.metas.JsonObjectMapperHolder;
import de.metas.elasticsearch.config.ESModelIndexerId;
import de.metas.elasticsearch.config.ESModelIndexerProfile;
import de.metas.elasticsearch.indexer.queue.ESModelIndexEvent;
import de.metas.elasticsearch.indexer.queue.ESModelIndexEventType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ESModelIndexEventTest
{
	@Test
	public void testSerializeDeserialize() throws JsonProcessingException
	{
		testSerializeDeserialize(ESModelIndexEvent.builder()
				.type(ESModelIndexEventType.ADD)
				.modelIndexerId(ESModelIndexerId.builder()
						.indexName("indexName")
						.indexType("indexType")
						.profile(ESModelIndexerProfile.KPI)
						.includedAttributeName("includedAttributeName")
						.build())
				.modelTableName("modelTableName")
				.modelIds(ImmutableSet.of(1, 2, 3))
				.build());
	}

	private void testSerializeDeserialize(final ESModelIndexEvent event) throws JsonProcessingException
	{
		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = jsonObjectMapper.writeValueAsString(event);

		final ESModelIndexEvent eventDeserialized = jsonObjectMapper.readValue(json, event.getClass());
		Assertions.assertEquals(event, eventDeserialized);
	}
}
