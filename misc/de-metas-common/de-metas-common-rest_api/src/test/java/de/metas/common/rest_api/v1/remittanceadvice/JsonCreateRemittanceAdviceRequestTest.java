/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.v1.remittanceadvice;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JsonCreateRemittanceAdviceRequestTest
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
		JsonRemittanceAdvice jsonRemittanceAdvice = createJsonRemittanceAdviceBuilder()
				.orgCode("test")
				.senderId("123")
				.recipientId("465")
				.documentNumber("0001")
				.sendDate("2019-11-22T00:00:00Z")
				.documentDate("2017-11-22T00:00:00Z")
				.type(RemittanceAdviceType.INBOUND)
				.remittedAmountSum(BigDecimal.valueOf(30))
				.remittanceAmountCurrencyISO("EUR")
				.serviceFeeAmount(BigDecimal.valueOf(0))
				.serviceFeeCurrencyISO("null")
				.additionalNotes("test additional notes")
				.build();
		final ImmutableList.Builder<JsonRemittanceAdvice> list = ImmutableList.builder();
		list.add(jsonRemittanceAdvice);

		testSerializeDeserialize(createJsonCreateRemittanceAdviceRequestBuilder()
										 .remittanceAdviceList(list.build())
										 .build());
	}

	@Builder(builderMethodName = "createJsonCreateRemittanceAdviceRequestBuilder",
			builderClassName = "JsonCreateRemittanceAdviceRequestBuilder")
	private JsonCreateRemittanceAdviceRequest createJsonCreateRemittanceAdviceRequest(
			@NonNull final List<JsonRemittanceAdvice> remittanceAdviceList
	)
	{
		final JsonCreateRemittanceAdviceRequest request = JsonCreateRemittanceAdviceRequest.builder()
				.remittanceAdviceList(remittanceAdviceList)
				.build();

		return request;
	}

	@Builder(builderMethodName = "createJsonRemittanceAdviceBuilder", builderClassName = "JsonRemittanceAdviceBuilder")
	private JsonRemittanceAdvice createJsonRemittanceAdvice(
			final String orgCode,
			@NonNull final String senderId,
			@NonNull final String recipientId,
			final String documentNumber,
			final String sendDate,
			final String documentDate,
			@NonNull final RemittanceAdviceType type,
			@NonNull final BigDecimal remittedAmountSum,
			@NonNull final String remittanceAmountCurrencyISO,
			final BigDecimal serviceFeeAmount,
			final String serviceFeeCurrencyISO,
			final String additionalNotes
	)
	{
		final JsonRemittanceAdvice jsonRemittanceAdvice = JsonRemittanceAdvice.builder()
				.orgCode(orgCode)
				.senderId(senderId)
				.recipientId(recipientId)
				.documentNumber(documentNumber)
				.sendDate(sendDate)
				.documentDate(documentDate)
				.remittanceAdviceType(type)
				.remittedAmountSum(remittedAmountSum)
				.remittanceAmountCurrencyISO(remittanceAmountCurrencyISO)
				.serviceFeeAmount(serviceFeeAmount)
				.serviceFeeCurrencyISO(serviceFeeCurrencyISO)
				.additionalNotes(additionalNotes)
				.lines(new ArrayList<>())
				.build();

		return jsonRemittanceAdvice;
	}

	private void testSerializeDeserialize(final JsonCreateRemittanceAdviceRequest obj) throws IOException
	{
		System.out.println("Object: " + obj);

		final String json = objectMapper.writeValueAsString(obj);
		System.out.println("Object->JSON: " + json);

		final JsonCreateRemittanceAdviceRequest objDeserialized = objectMapper.readValue(json, obj.getClass());
		System.out.println("Object deserialized: " + objDeserialized);
		Assertions.assertThat(objDeserialized).isEqualTo(obj);
	}
}
