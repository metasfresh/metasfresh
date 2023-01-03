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

package de.metas.camel.externalsystems.shopware6.api.model.order;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.*;

public class TestPOJOs
{
	private final ObjectMapper objectMapper = new ObjectMapper()
			.registerModule(new JavaTimeModule())
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

	@Test
	public void givenJsonOrders_whenSerializeDeserialize_thenSuccess() throws IOException
	{
		testSerializeDeserializeObject(getMockJsonOrders());
	}

	@Test
	public void givenJsonOrderLine_whenSerializeDeserialize_thenSuccess() throws IOException
	{
		testSerializeDeserializeObject(getMockJsonOrderLine());
	}

	@Test
	public void givenJsonOrderTransactions_whenSerializeDeserialize_thenSuccess() throws IOException
	{
		testSerializeDeserializeObject(getMockOrderTransactions());
	}

	@Test
	public void givenJsonPaymentMethod_whenSerializeDeserialize_thenSuccess() throws IOException
	{
		testSerializeDeserializeObject(getJsonPaymentMethod());
	}

	private void testSerializeDeserializeObject(final Object value) throws IOException
	{
		final Class<?> valueClass = value.getClass();
		final String json = objectMapper.writeValueAsString(value);
		final Object value2 = objectMapper.readValue(json, valueClass);
		assertThat(value2).isEqualTo(value);
	}

	private JsonOrders getMockJsonOrders()
	{
		return JsonOrders.builder()
				.data(ImmutableList.of(
						JsonOrder.builder()
								.id("orderId")
								.billingAddressId("billingAddressId")
								.orderNumber("orderNumber")
								.currencyId("currencyId")
								.orderDate(ZonedDateTime.now(ZoneId.of("UTC")))
								.createdAt(ZonedDateTime.now(ZoneId.of("UTC")))
								.stateMachine(JsonStateMachine.builder()
													  .technicalName("open")
													  .build()
								)
								.orderCustomer(JsonOrderCustomer.builder()
													   .customerId("customerId")
													   .firstName("firstName")
													   .lastName("lastName")
													   .email("email")
													   .company("company")
													   .customerNumber("customerNumber")
													   .vatIds(ImmutableList.of("vatId"))
													   .build())
								.build()
				))
				.build();
	}

	private JsonOrderLine getMockJsonOrderLine()
	{
		return JsonOrderLine.builder()
				.updatedAt(ZonedDateTime.now(ZoneId.of("UTC")))
				.createdAt(ZonedDateTime.now(ZoneId.of("UTC")))
				.description("description")
				.id("id")
				.parentId("parentId")
				.payload(JsonOrderLinePayload.builder()
								 .isBundle(true)
								 .build())
				.position(1)
				.productId("productId")
				.quantity(BigDecimal.ONE)
				.unitPrice(BigDecimal.TEN)
				.build();
	}

	private JsonOrderTransactions getMockOrderTransactions()
	{
		return JsonOrderTransactions.builder()
				.transactionList(
						ImmutableList.of(JsonOrderTransaction.builder()
												 .id("id")
												 .paymentMethodId("paymentMethodId")
												 .amount(JsonAmount.builder()
																 .totalPrice(BigDecimal.valueOf(100))
																 .build()
												 )
												 .createdAt(ZonedDateTime.now(ZoneId.of("UTC")))
												 .stateMachine(JsonStateMachine.builder()
																	   .technicalName("paid")
																	   .build()
												 ).build()
						)
				)
				.build();
	}

	private JsonPaymentMethod getJsonPaymentMethod()
	{
		return JsonPaymentMethod.builder()
				.id("id")
				.shortName("shortName")
				.build();
	}
}

