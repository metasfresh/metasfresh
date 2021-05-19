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
import lombok.Builder;
import lombok.NonNull;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

public class JsonRemittanceAdviceLineTest
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
		testSerializeDeserialize(createJsonRemittanceAdviceLineBuilder()
										 .invoiceIdentifier("1234")
										 .remittedAmount(BigDecimal.valueOf(30))
										 .bpartnerIdentifier("bpartnerIdentifier")
										 .dateInvoiced("2019-11-22T00:00:00Z")
										 .invoiceBaseDocType("PAY")
										 .paymentDiscountAmount(BigDecimal.valueOf(20))
										 .serviceFeeAmount(BigDecimal.valueOf(10))
										 .serviceFeeVatRate(BigDecimal.valueOf(2))
										 .build());
	}

	@Builder(builderMethodName = "createJsonRemittanceAdviceLineBuilder", builderClassName = "JsonRemittanceAdviceLineBuilder")
	private JsonRemittanceAdviceLine createJsonRemittanceAdviceLine(
			@NonNull final String invoiceIdentifier,
			@NonNull final BigDecimal remittedAmount,
			final String bpartnerIdentifier,
			final String dateInvoiced,
			final String invoiceBaseDocType,
			final BigDecimal paymentDiscountAmount,
			final BigDecimal serviceFeeAmount,
			final BigDecimal serviceFeeVatRate
	)
	{
		final JsonRemittanceAdviceLine jsonRemittanceAdviceLine = JsonRemittanceAdviceLine.builder()
				.bpartnerIdentifier(bpartnerIdentifier)
				.dateInvoiced(dateInvoiced)
				.invoiceBaseDocType(invoiceBaseDocType)
				.invoiceIdentifier(invoiceIdentifier)
				.remittedAmount(remittedAmount)
				.paymentDiscountAmount(paymentDiscountAmount)
				.serviceFeeAmount(serviceFeeAmount)
				.serviceFeeVatRate(serviceFeeVatRate)
				.build();

		return jsonRemittanceAdviceLine;
	}

	private void testSerializeDeserialize(final JsonRemittanceAdviceLine obj) throws IOException
	{
		System.out.println("Object: " + obj);

		final String json = objectMapper.writeValueAsString(obj);
		System.out.println("Object->JSON: " + json);

		final JsonRemittanceAdviceLine objDeserialized = objectMapper.readValue(json, obj.getClass());
		System.out.println("Object deserialized: " + objDeserialized);
		Assertions.assertThat(objDeserialized).isEqualTo(obj);
	}
}
