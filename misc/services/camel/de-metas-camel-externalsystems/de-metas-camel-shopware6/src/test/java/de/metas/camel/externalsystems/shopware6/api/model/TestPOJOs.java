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

class TestPOJOs
{
	private final ObjectMapper objectMapper = new ObjectMapper()
			.registerModule(new JavaTimeModule())
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

	@Test
	void jsonQuery_multi() throws IOException
	{
		final MultiQueryRequest queryRequest = GetOrdersProcessor.buildUpdatedAfterQueryRequest("2020-10-26T06:32:45Z");
		final String json = objectMapper.writeValueAsString(queryRequest);

		Assertions.assertThat(json).isEqualToIgnoringWhitespace("{\n"
																		+ "  \"filter\": [\n"
																		+ "    {\n"
																		+ "      \"type\": \"multi\",\n"
																		+ "      \"operator\": \"or\",\n"
																		+ "      \"queries\": [\n"
																		+ "        {\n"
																		+ "          \"field\": \"updatedAt\",\n"
																		+ "          \"type\": \"range\",\n"
																		+ "          \"parameters\": {\n"
																		+ "            \"gte\": \"2020-10-26T06:32:45Z\"\n"
																		+ "          }\n"
																		+ "        },\n"
																		+ "        {\n"
																		+ "          \"field\": \"createdAt\",\n"
																		+ "          \"type\": \"range\",\n"
																		+ "          \"parameters\": {\n"
																		+ "            \"gte\": \"2020-10-26T06:32:45Z\"\n"
																		+ "          }\n"
																		+ "        }\n"
																		+ "      ]\n"
																		+ "    }\n"
																		+ "  ]\n"
																		+ "}");
	}

	@Test
	void jsonQuery_single() throws IOException
	{
		final QueryRequest queryRequest = GetOrdersProcessor.buildOrderNoQueryRequest("1234");
		final String json = objectMapper.writeValueAsString(queryRequest);

		Assertions.assertThat(json).isEqualToIgnoringWhitespace("{\n"
																		+ "    \"filter\": [\n"
																		+ "        {\n"
																		+ "            \"field\": \"orderNumber\",\n"
																		+ "            \"type\": \"equals\",\n"
																		+ "            \"value\": \"1234\"\n"
																		+ "        }\n"
																		+ "    ]\n"
																		+ "}");
	}
}