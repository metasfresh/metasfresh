/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.api.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.shopware6.order.processor.GetOrdersProcessor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class TestPOJOs
{
	private final ObjectMapper objectMapper = new ObjectMapper()
			.registerModule(new JavaTimeModule())
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

	@Test
	void jsonQuery() throws IOException
	{
		final QueryRequest queryRequest = GetOrdersProcessor.buildQueryOrdersRequest("updatedAfter");
		final String json = objectMapper.writeValueAsString(queryRequest);

		Assertions.assertThat(json).isEqualToIgnoringWhitespace("{\"filter\":[{\"type\":\"multi\",\"operator\":\"or\",\"queries\":[{\"field\":\"updatedAt\",\"type\":\"range\",\"parameters\":{\"gte\":\"updatedAfter\"}},{\"field\":\"createdAt\",\"type\":\"range\",\"parameters\":{\"gte\":\"updatedAfter\"}}]}]}");
	}
}