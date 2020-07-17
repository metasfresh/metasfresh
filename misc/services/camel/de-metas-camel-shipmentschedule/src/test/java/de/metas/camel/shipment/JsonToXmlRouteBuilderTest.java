/*
 * #%L
 * de-metas-camel-shipmentschedule
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

package de.metas.camel.shipment;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.apache.camel.test.junit5.TestSupport.deleteDirectory;
import static org.assertj.core.api.Assertions.assertThat;

class JsonToXmlRouteBuilderTest extends CamelTestSupport
{

	private MockEndpoint mockEndpoint;

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new JsonToXmlRouteBuilder();
	}

	@BeforeEach
	public void beforeEach() throws Exception
	{
		deleteDirectory("target/xml");

		mockEndpoint = getMockEndpoint("mock:$shipmentScheduleAPI");

		AdviceWithRouteBuilder.adviceWith(context, "MF-ShipmentSchedule-JSON-To-Filemaker-XML",
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
		return properties;
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Test
	void test() throws Exception
	{
		context.start();

		mockEndpoint.whenAnyExchangeReceived(new EmptyResult());

		final NotifyBuilder notify = new NotifyBuilder(context).whenDone(1).create(); // instead of waiting, go on whenever component one is ready

		//template.sendBody("timer://pollShipmentCandidateAPI", "tick"); // this doesn't work, but the timer starts ticking all by itself
		assertThat(notify.matchesWaitTime()).isTrue();

		mockEndpoint.assertIsSatisfied();

		context.stop();
	}

	private static class ShipmentCandidateAPIProcessor implements Processor
	{
		@Override
		public void process(final Exchange exchange)
		{
			exchange.getIn().setBody("{\n"
					+ "    \"items\": [\n"
					+ "        {\n"
					+ "            \"orderDocumentNo\": \"orderDocumentNo\",\n"
					+ "            \"poReference\": \"poReference\",\n"
					+ "            \"dateOrdered\": \"2020-07-14T05:48:00\",\n"
					+ "            \"productNo\": \"productNo\"\n"
					+ "        }\n"
					+ "    ]\n"
					+ "}");
		}
	}

	private static class EmptyResult implements Processor
	{
		@Override
		public void process(final Exchange exchange)
		{
			{
				exchange.getIn().setBody("{\n"
						+ "    \"transactionKey\": \"74dcbcf6-265d-41b3-bb15-1cde4793684a\",\n"
						+ "    \"items\": [],\n"
						+ "    \"hasMoreItems\": false\n"
						+ "}");
			}
		}

	}
}