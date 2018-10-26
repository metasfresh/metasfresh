package de.metas.order.rest.model;

import lombok.NonNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.attachments.AttachmentEntry;

/*
 * #%L
 * de.metas.order.rest-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class JsonSerializationDeserializationTest
{
	private ObjectMapper jsonObjectMapper;

	@Before
	public void init()
	{
		jsonObjectMapper = new ObjectMapper();
		jsonObjectMapper.findAndRegisterModules();
	}

	private void testSerializeDeserialize(@NonNull final Object obj) throws IOException
	{
		System.out.println("object: " + obj);
		final String json = jsonObjectMapper.writeValueAsString(obj);
		System.out.println("json: " + json);

		final Object obj2 = jsonObjectMapper.readValue(json, obj.getClass());
		System.out.println("object deserialized: " + obj2);

		Assert.assertEquals(obj, obj2);
	}

	@Test
	public void test_SalesOrderCreateRequest() throws Exception
	{
		testSerializeDeserialize(JsonSalesOrderCreateRequest.builder()
				.shipBPartnerCode("123")
				.datePromised(LocalDateTime.now())
				.line(JsonSalesOrderLine.builder()
						.productCode("01")
						.qty(new BigDecimal("12.34"))
						.build())
				.build());
	}

	@Test
	public void test_SalesOrder() throws Exception
	{
		testSerializeDeserialize(JsonSalesOrder.builder()
				.salesOrderId("12345")
				.build());
	}

	@Test
	public void test_SalesOrderAttachment() throws Exception
	{
		testSerializeDeserialize(JsonSalesOrderAttachment.builder()
				.id(444)
				.type(AttachmentEntry.Type.Data)
				.filename("file.pdf")
				.mimeType("application/pdf")
				.build());
	}
}
