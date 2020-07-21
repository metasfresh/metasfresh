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

package de.metas.camel.shipping.receiptcandidate;

import de.metas.camel.shipping.shipmentcandidate.ShipmentCandidateJsonToXmlRouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.apache.camel.test.junit5.TestSupport.deleteDirectory;

class ReceiptCandidateJsonToXmlRouteBuilderTest extends CamelTestSupport
{
	private MockEndpoint mockEndpoint;

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new ShipmentCandidateJsonToXmlRouteBuilder();
	}

	@BeforeEach
	public void beforeEach() throws Exception
	{
		deleteDirectory("target/xml");

		mockEndpoint = getMockEndpoint("mock:$shipmentScheduleAPI");

		AdviceWithRouteBuilder
				.adviceWith(context, ShipmentCandidateJsonToXmlRouteBuilder.MF_SHIPMENT_CANDIDATE_JSON_TO_FILEMAKER_XML,
						a -> a.interceptSendToEndpoint("http://baseURL/shipments/shipmentSchedules")
								.skipSendToOriginalEndpoint()
								.to(mockEndpoint)
				);
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		properties.put("metasfresh.api.authtoken", "123");
		properties.put("metasfresh.api.baseurl", "baseURL");
		properties.put("local.file.output_path", "target/xml");
		properties.put("upload.endpoint.uri","log:upload-dummy");
		return properties;
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Test
	void test_emptyResult()
	{
		context.start();

		mockEndpoint.whenAnyExchangeReceived(new ReceiptCandidateJsonToXmlRouteBuilderTest.EmptyResult());

		context.stop();
	}

	private static class EmptyResult implements Processor
	{
		@Override
		public void process(final Exchange exchange)
		{
			{
				exchange.getIn().setBody("{\n"
						+ "    \"transactionKey\": \"617f06c2-0a87-4389-9c11-7d67f3693aec\",\n"
						+ "    \"items\": [],\n"
						+ "    \"hasMoreItems\": false\n"
						+ "}");
			}
		}
	}
}