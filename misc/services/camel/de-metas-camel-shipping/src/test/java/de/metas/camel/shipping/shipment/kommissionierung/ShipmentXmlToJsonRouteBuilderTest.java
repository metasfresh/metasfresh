/*
 * #%L
 * de-metas-camel-shipping
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

package de.metas.camel.shipping.shipment.kommissionierung;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.io.Resources;
import de.metas.camel.shipping.shipment.SiroShipmentConstants;
import de.metas.common.shipment.JsonCreateShipmentRequest;
import de.metas.common.util.time.SystemTime;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static de.metas.camel.inventory.InventoryXmlToMetasfreshRouteBuilder.ROUTE_ID_FROM_JSON;
import static de.metas.camel.shipping.RouteBuilderCommonUtil.NUMBER_OF_ITEMS;
import static org.assertj.core.api.Assertions.assertThat;

public class ShipmentXmlToJsonRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_FROM_ENDPOINT = "direct:mockInput";
	private static final String MOCK_XML_TO_JSON_ENDPOINT = "mock:xmlToJsonResult";
	private static final String MOCK_XML_TO_JSON_INVENTORY_ENDPOINT = "mock:xmlToJsonInventoryResult";
	public static final String RESOURCE_PATH = "/de/metas/camel/shipping/shipment/kommissionierung/";

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new ShipmentXmlToJsonRouteBuilder();
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Test
	void emptyFile_test() throws Exception
	{
		final MockEmptyProcessor localStorageEndpoint = new MockEmptyProcessor();
		final MockEmptyProcessor importInventoryLinesEndpoint = new MockEmptyProcessor();
		final String mockResponse = readResourceAsString(RESOURCE_PATH + "JsonCreateShipmentResponse.json");
		final MockSuccessfullyCreatedShipmentProcessor createdShipmentEndpoint = new MockSuccessfullyCreatedShipmentProcessor(mockResponse); // acually we don't expect this mocked EP to be called

		prepareRouteForTesting(localStorageEndpoint, createdShipmentEndpoint, importInventoryLinesEndpoint);

		context.start();

		final MockEndpoint shipmentMockEndpoint = getMockEndpoint(MOCK_XML_TO_JSON_ENDPOINT);
		shipmentMockEndpoint.expectedHeaderReceived(NUMBER_OF_ITEMS, 0);

		final MockEndpoint inventoryMockEndpoint = getMockEndpoint(MOCK_XML_TO_JSON_INVENTORY_ENDPOINT);
		inventoryMockEndpoint.expectedHeaderReceived(NUMBER_OF_ITEMS, 0);

		final InputStream createShipmentFile = this.getClass().getResourceAsStream(RESOURCE_PATH + "kommissionierung_empty.xml");

		template.sendBody(MOCK_FROM_ENDPOINT, createShipmentFile);

		assertThat(localStorageEndpoint.called).isEqualTo(1);
		assertThat(createdShipmentEndpoint.called).isEqualTo(0);
		assertThat(importInventoryLinesEndpoint.called).isEqualTo(0);

		assertMockEndpointsSatisfied();
	}

	@Test
	void validFile_kommissionieren_test() throws Exception
	{
		//given
		SystemTime.setFixedTimeSource(ZonedDateTime.of(2020, 9, 24, 12, 0, 0, 0, ZoneId.of("Europe/Paris")));
		//set up shipping route
		final MockEmptyProcessor localStorageEndpoint = new MockEmptyProcessor();
		final String mockResponse = readResourceAsString(RESOURCE_PATH + "JsonCreateShipmentResponse.json");
		final MockSuccessfullyCreatedShipmentProcessor createdShipmentEndpoint = new MockSuccessfullyCreatedShipmentProcessor(mockResponse); // be sure to fail now in case mockResponse is null
		final MockEmptyProcessor importInventoryLinesEndpoint = new MockEmptyProcessor();

		prepareRouteForTesting(localStorageEndpoint, createdShipmentEndpoint, importInventoryLinesEndpoint);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		//set up shipment expectations
		final InputStream expectedCreateShipmentRequest = this.getClass().getResourceAsStream(RESOURCE_PATH + "kommissionierung_noBestBeforeAndLot_JsonCreateShipmentRequest.json");
		assertThat(expectedCreateShipmentRequest).isNotNull(); //guard

		final MockEndpoint createShipmentMockEP = getMockEndpoint(MOCK_XML_TO_JSON_ENDPOINT);
		createShipmentMockEP.expectedBodiesReceived(objectMapper.readValue(expectedCreateShipmentRequest, JsonCreateShipmentRequest.class));

		//when
		final InputStream createShipmentFile = this.getClass().getResourceAsStream(RESOURCE_PATH + "kommissionierung_noBestBeforeAndLot.xml");
		assertThat(createShipmentFile).isNotNull(); //guard

		template.sendBody(MOCK_FROM_ENDPOINT, createShipmentFile);

		//then
		assertThat(localStorageEndpoint.called).isEqualTo(1);
		assertThat(createdShipmentEndpoint.called).isEqualTo(1);
		assertThat(importInventoryLinesEndpoint.called).isEqualTo(0); // not called, bc were have only one line which is not "out_of_stock"

		assertMockEndpointsSatisfied();
	}

	@Test
	void validFile_kommissionieren_split() throws Exception
	{
		//given
		SystemTime.setFixedTimeSource(ZonedDateTime.of(2020, 9, 24, 12, 0, 0, 0, ZoneId.of("Europe/Paris")));
		//set up shipping route
		final MockEmptyProcessor localStorageEndpoint = new MockEmptyProcessor();
		final String mockResponse = readResourceAsString("JsonCreateShipmentResponse.json");
		final MockSuccessfullyCreatedShipmentProcessor createdShipmentEndpoint = new MockSuccessfullyCreatedShipmentProcessor(mockResponse); // be sure to fail now in case mockResponse is null
		final MockEmptyProcessor importInventoryLinesEndpoint = new MockEmptyProcessor();

		prepareRouteForTesting(localStorageEndpoint, createdShipmentEndpoint, importInventoryLinesEndpoint);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		//set up shipment expectations
		final InputStream expectedCreateShipmentRequest1 = this.getClass().getResourceAsStream(RESOURCE_PATH + "kommissionierung_needs_splitting_JsonCreateShipmentRequest_1.json");
		assertThat(expectedCreateShipmentRequest1).isNotNull(); //guard
		final InputStream expectedCreateShipmentRequest2 = this.getClass().getResourceAsStream(RESOURCE_PATH + "kommissionierung_needs_splitting_JsonCreateShipmentRequest_2.json");
		assertThat(expectedCreateShipmentRequest2).isNotNull(); //guard


		final MockEndpoint createShipmentMockEP = getMockEndpoint(MOCK_XML_TO_JSON_ENDPOINT);
		createShipmentMockEP.expectedBodiesReceived(
				objectMapper.readValue(expectedCreateShipmentRequest1, JsonCreateShipmentRequest.class),
				objectMapper.readValue(expectedCreateShipmentRequest2, JsonCreateShipmentRequest.class));

		//when
		final InputStream createShipmentFile = this.getClass().getResourceAsStream(RESOURCE_PATH + "kommissionierung_needs_splitting.xml");
		assertThat(createShipmentFile).isNotNull(); //guard

		template.sendBody(MOCK_FROM_ENDPOINT, createShipmentFile);

		//then
		assertThat(localStorageEndpoint.called).isEqualTo(1);
		assertThat(createdShipmentEndpoint.called).isEqualTo(2);
		assertThat(importInventoryLinesEndpoint.called).isEqualTo(0); // not called, bc were have only one line which is not "out_of_stock"

		assertMockEndpointsSatisfied();
	}

	private String readResourceAsString(final String fileName) throws IOException
	{
		final var url = this.getClass().getResource(RESOURCE_PATH + fileName);
		return Resources.toString(url, StandardCharsets.UTF_8);
	}

	private void prepareRouteForTesting(final MockEmptyProcessor localStorageEndpoint,
			final MockSuccessfullyCreatedShipmentProcessor createdShipmentEndpoint,
			final MockEmptyProcessor importInventoryLinesEndpoint) throws Exception
	{
		AdviceWithRouteBuilder.adviceWith(context, ShipmentXmlToJsonRouteBuilder.MF_SHIPMENT_FILEMAKER_XML_TO_JSON,
				advice -> {
					advice.replaceFromWith(MOCK_FROM_ENDPOINT);

					advice.interceptSendToEndpoint(SiroShipmentConstants.LOCAL_STORAGE_URL)
							.skipSendToOriginalEndpoint()
							.process(localStorageEndpoint);
				});

		AdviceWithRouteBuilder.adviceWith(context, ShipmentXmlToJsonRouteBuilder.MF_GENERATE_SHIPMENTS,
				advice -> {
					advice.weaveById(SiroShipmentConstants.SHIPMENT_XML_TO_JSON_PROCESSOR)
							.after()
							.to(MOCK_XML_TO_JSON_ENDPOINT);

					advice.interceptSendToEndpoint("http://" + SiroShipmentConstants.CREATE_SHIPMENT_MF_URL)
							.skipSendToOriginalEndpoint()
							.process(createdShipmentEndpoint);
				});

		AdviceWithRouteBuilder.adviceWith(context, ShipmentXmlToJsonRouteBuilder.MF_SHIPMENT_INVENTORY_CORRECTION,
				advice -> {

					advice.weaveById(SiroShipmentConstants.INVENTORY_CORRECTION_XML_TO_JSON_PROCESSOR)
							.after()
							.to(MOCK_XML_TO_JSON_INVENTORY_ENDPOINT);

					advice.interceptSendToEndpoint("direct://" + ROUTE_ID_FROM_JSON)
							.skipSendToOriginalEndpoint()
							.process(importInventoryLinesEndpoint);
				});
	}

	private static class MockEmptyProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	private static class MockSuccessfullyCreatedShipmentProcessor implements Processor
	{
		private final String response;
		private int called = 0;

		private MockSuccessfullyCreatedShipmentProcessor(@NonNull final String response)
		{
			this.response = response;
		}

		@Override
		public void process(final Exchange exchange)
		{
			exchange.getIn().setBody(response);
			called++;
		}
	}
}
