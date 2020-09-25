package de.metas.common.manufacturing;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.metas.common.rest_api.JsonError;
import de.metas.common.rest_api.JsonErrorItem;
import de.metas.common.rest_api.JsonMetasfreshId;
import lombok.Getter;

/*
 * #%L
 * de-metas-common-manufacturing
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class JsonResponseManufacturingOrdersReportTest
{
	private ObjectMapper objectMapper;

	@BeforeEach
	public void beforeEach()
	{
		objectMapper = newJsonObjectMapper();
	}

	private static ObjectMapper newJsonObjectMapper()
	{
		return new ObjectMapper()
				.findAndRegisterModules()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);
	}

	@Test
	public void test() throws Exception
	{
		testSerializeDeserialize(JsonResponseManufacturingOrdersReport.builder()
				.transactionKey("trx1")
				.error(JsonError.builder()
						.error(JsonErrorItem.builder()
								.message("message")
								.detail("detail")
								.stackTrace("stackTrace")
								.parameter("param1", "value1")
								.adIssueId(JsonMetasfreshId.of(12345))
								.throwable(new CustomException("some error")
										.adIssueId(12345))
								.build())
						.build())
				.build());
	}

	private void testSerializeDeserialize(final JsonResponseManufacturingOrdersReport obj) throws IOException
	{
		System.out.println("Object: " + obj);

		final String json = objectMapper.writeValueAsString(obj);
		System.out.println("Object->JSON: " + json);

		final JsonResponseManufacturingOrdersReport objDeserialized = objectMapper.readValue(json, obj.getClass());
		System.out.println("Object deserialized: " + objDeserialized);
		assertThat(objDeserialized).isEqualTo(obj);
	}

	@SuppressWarnings("serial")
	private static class CustomException extends RuntimeException
	{
		@Getter
		private int adIssueId;

		public CustomException(final String message)
		{
			super(message);
		}

		public CustomException adIssueId(final int adIssueId)
		{
			this.adIssueId = adIssueId;
			return this;
		}
	}
}
