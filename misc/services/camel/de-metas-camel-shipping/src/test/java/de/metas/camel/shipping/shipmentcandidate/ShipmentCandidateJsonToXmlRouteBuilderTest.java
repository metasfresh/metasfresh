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

package de.metas.camel.shipping.shipmentcandidate;

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
import static org.assertj.core.api.Assertions.assertThat;

class ShipmentCandidateJsonToXmlRouteBuilderTest extends CamelTestSupport
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
	void test_emptyResult() throws Exception
	{
		context.start();

		mockEndpoint.whenAnyExchangeReceived(new EmptyResult());

		context.stop();
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

	@Test
	void test() throws Exception
	{
		context.start();

		mockEndpoint.whenExchangeReceived(0, new NormalResult());
		mockEndpoint.whenExchangeReceived(1, new EmptyResult());

		// final NotifyBuilder notify = new NotifyBuilder(context).whenDone(2).create(); // instead of waiting, go on whenever component one is ready
		// assertThat(notify.matchesWaitTime()).isTrue();

		context.stop();
	}

	private static class NormalResult implements Processor
	{
		@Override
		public void process(final Exchange exchange)
		{
			exchange.getIn().setBody("{\n"
					+ "    \"transactionKey\": \"9b31991d-df88-44cd-97a8-8447c9071132\",\n"
					+ "    \"items\": [\n"
					+ "        {\n"
					+ "            \"id\": 1000000,\n"
					+ "            \"orgCode\": \"001\",\n"
					+ "            \"orderDocumentNo\": \"0001\",\n"
					+ "            \"dateOrdered\": \"2020-06-23T00:00:00\",\n"
					+ "            \"product\": {\n"
					+ "                \"productNo\": \"P002737\",\n"
					+ "                \"name\": \"Convenience Salat 250g\",\n"
					+ "                \"description\": \"\",\n"
					+ "                \"weight\": 0.250000000000\n"
					+ "            },\n"
					+ "            \"customer\": {\n"
					+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
					+ "                \"street\": \"teststraße\",\n"
					+ "                \"streetNo\": \"996\",\n"
					+ "                \"postal\": \"45678\",\n"
					+ "                \"city\": \"teststadt 3\",\n"
					+ "                \"countryCode\": \"DE\",\n"
					+ "                \"language\": \"de_DE\"\n"
					+ "            },\n"
					+ "            \"quantities\": [\n"
					+ "                {\n"
					+ "                    \"qty\": 12,\n"
					+ "                    \"uomCode\": \"Stk\"\n"
					+ "                }\n"
					+ "            ]\n"
					+ "        },\n"
					+ "        {\n"
					+ "            \"id\": 1000001,\n"
					+ "            \"orgCode\": \"001\",\n"
					+ "            \"orderDocumentNo\": \"0002\",\n"
					+ "            \"dateOrdered\": \"2020-06-23T00:00:00\",\n"
					+ "            \"product\": {\n"
					+ "                \"productNo\": \"P002737\",\n"
					+ "                \"name\": \"Convenience Salat 250g\",\n"
					+ "                \"description\": \"\",\n"
					+ "                \"weight\": 0.250000000000\n"
					+ "            },\n"
					+ "            \"customer\": {\n"
					+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
					+ "                \"street\": \"teststraße\",\n"
					+ "                \"streetNo\": \"996\",\n"
					+ "                \"postal\": \"45678\",\n"
					+ "                \"city\": \"teststadt 3\",\n"
					+ "                \"countryCode\": \"DE\",\n"
					+ "                \"language\": \"de_DE\"\n"
					+ "            },\n"
					+ "            \"quantities\": [\n"
					+ "                {\n"
					+ "                    \"qty\": 12,\n"
					+ "                    \"uomCode\": \"Stk\"\n"
					+ "                }\n"
					+ "            ]\n"
					+ "        },\n"
					+ "        {\n"
					+ "            \"id\": 1000002,\n"
					+ "            \"orgCode\": \"001\",\n"
					+ "            \"orderDocumentNo\": \"0003\",\n"
					+ "            \"dateOrdered\": \"2020-06-23T00:00:00\",\n"
					+ "            \"product\": {\n"
					+ "                \"productNo\": \"P002737\",\n"
					+ "                \"name\": \"Convenience Salat 250g\",\n"
					+ "                \"description\": \"\",\n"
					+ "                \"weight\": 0.250000000000\n"
					+ "            },\n"
					+ "            \"customer\": {\n"
					+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
					+ "                \"street\": \"teststraße\",\n"
					+ "                \"streetNo\": \"996\",\n"
					+ "                \"postal\": \"45678\",\n"
					+ "                \"city\": \"teststadt 3\",\n"
					+ "                \"countryCode\": \"DE\",\n"
					+ "                \"language\": \"de_DE\"\n"
					+ "            },\n"
					+ "            \"quantities\": [\n"
					+ "                {\n"
					+ "                    \"qty\": 12,\n"
					+ "                    \"uomCode\": \"Stk\"\n"
					+ "                }\n"
					+ "            ]\n"
					+ "        },\n"
					+ "        {\n"
					+ "            \"id\": 1000003,\n"
					+ "            \"orgCode\": \"001\",\n"
					+ "            \"orderDocumentNo\": \"0004\",\n"
					+ "            \"dateOrdered\": \"2020-06-23T00:00:00\",\n"
					+ "            \"product\": {\n"
					+ "                \"productNo\": \"P002737\",\n"
					+ "                \"name\": \"Convenience Salat 250g\",\n"
					+ "                \"description\": \"\",\n"
					+ "                \"weight\": 0.250000000000\n"
					+ "            },\n"
					+ "            \"customer\": {\n"
					+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
					+ "                \"street\": \"teststraße\",\n"
					+ "                \"streetNo\": \"996\",\n"
					+ "                \"postal\": \"45678\",\n"
					+ "                \"city\": \"teststadt 3\",\n"
					+ "                \"countryCode\": \"DE\",\n"
					+ "                \"language\": \"de_DE\"\n"
					+ "            },\n"
					+ "            \"quantities\": [\n"
					+ "                {\n"
					+ "                    \"qty\": 12,\n"
					+ "                    \"uomCode\": \"Stk\"\n"
					+ "                }\n"
					+ "            ]\n"
					+ "        },\n"
					+ "        {\n"
					+ "            \"id\": 1000004,\n"
					+ "            \"orgCode\": \"001\",\n"
					+ "            \"orderDocumentNo\": \"0005\",\n"
					+ "            \"dateOrdered\": \"2020-06-23T00:00:00\",\n"
					+ "            \"product\": {\n"
					+ "                \"productNo\": \"P002737\",\n"
					+ "                \"name\": \"Convenience Salat 250g\",\n"
					+ "                \"description\": \"\",\n"
					+ "                \"weight\": 0.250000000000\n"
					+ "            },\n"
					+ "            \"customer\": {\n"
					+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
					+ "                \"street\": \"teststraße\",\n"
					+ "                \"streetNo\": \"996\",\n"
					+ "                \"postal\": \"45678\",\n"
					+ "                \"city\": \"teststadt 3\",\n"
					+ "                \"countryCode\": \"DE\",\n"
					+ "                \"language\": \"de_DE\"\n"
					+ "            },\n"
					+ "            \"quantities\": [\n"
					+ "                {\n"
					+ "                    \"qty\": 12,\n"
					+ "                    \"uomCode\": \"Stk\"\n"
					+ "                }\n"
					+ "            ]\n"
					+ "        },\n"
					+ "        {\n"
					+ "            \"id\": 1000005,\n"
					+ "            \"orgCode\": \"001\",\n"
					+ "            \"orderDocumentNo\": \"0006\",\n"
					+ "            \"dateOrdered\": \"2020-06-23T00:00:00\",\n"
					+ "            \"product\": {\n"
					+ "                \"productNo\": \"P002737\",\n"
					+ "                \"name\": \"Convenience Salat 250g\",\n"
					+ "                \"description\": \"\",\n"
					+ "                \"weight\": 0.250000000000\n"
					+ "            },\n"
					+ "            \"customer\": {\n"
					+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
					+ "                \"street\": \"teststraße\",\n"
					+ "                \"streetNo\": \"996\",\n"
					+ "                \"postal\": \"45678\",\n"
					+ "                \"city\": \"teststadt 3\",\n"
					+ "                \"countryCode\": \"DE\",\n"
					+ "                \"language\": \"de_DE\"\n"
					+ "            },\n"
					+ "            \"quantities\": [\n"
					+ "                {\n"
					+ "                    \"qty\": 12,\n"
					+ "                    \"uomCode\": \"Stk\"\n"
					+ "                }\n"
					+ "            ]\n"
					+ "        },\n"
					+ "        {\n"
					+ "            \"id\": 1000006,\n"
					+ "            \"orgCode\": \"001\",\n"
					+ "            \"orderDocumentNo\": \"0007\",\n"
					+ "            \"dateOrdered\": \"2020-06-25T00:00:00\",\n"
					+ "            \"product\": {\n"
					+ "                \"productNo\": \"P002737\",\n"
					+ "                \"name\": \"Convenience Salat 250g\",\n"
					+ "                \"description\": \"\",\n"
					+ "                \"weight\": 0.250000000000\n"
					+ "            },\n"
					+ "            \"customer\": {\n"
					+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
					+ "                \"street\": \"teststraße\",\n"
					+ "                \"streetNo\": \"996\",\n"
					+ "                \"postal\": \"45678\",\n"
					+ "                \"city\": \"teststadt 3\",\n"
					+ "                \"countryCode\": \"DE\",\n"
					+ "                \"language\": \"de_DE\"\n"
					+ "            },\n"
					+ "            \"quantities\": [\n"
					+ "                {\n"
					+ "                    \"qty\": 12,\n"
					+ "                    \"uomCode\": \"Stk\"\n"
					+ "                }\n"
					+ "            ]\n"
					+ "        },\n"
					+ "        {\n"
					+ "            \"id\": 1000007,\n"
					+ "            \"orgCode\": \"001\",\n"
					+ "            \"orderDocumentNo\": \"0008\",\n"
					+ "            \"dateOrdered\": \"2020-06-25T00:00:00\",\n"
					+ "            \"product\": {\n"
					+ "                \"productNo\": \"P002737\",\n"
					+ "                \"name\": \"Convenience Salat 250g\",\n"
					+ "                \"description\": \"\",\n"
					+ "                \"weight\": 0.250000000000\n"
					+ "            },\n"
					+ "            \"customer\": {\n"
					+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
					+ "                \"street\": \"teststraße\",\n"
					+ "                \"streetNo\": \"996\",\n"
					+ "                \"postal\": \"45678\",\n"
					+ "                \"city\": \"teststadt 3\",\n"
					+ "                \"countryCode\": \"DE\",\n"
					+ "                \"language\": \"de_DE\"\n"
					+ "            },\n"
					+ "            \"quantities\": [\n"
					+ "                {\n"
					+ "                    \"qty\": 12,\n"
					+ "                    \"uomCode\": \"Stk\"\n"
					+ "                }\n"
					+ "            ]\n"
					+ "        },\n"
					+ "        {\n"
					+ "            \"id\": 1000008,\n"
					+ "            \"orgCode\": \"001\",\n"
					+ "            \"orderDocumentNo\": \"0009\",\n"
					+ "            \"dateOrdered\": \"2020-06-25T00:00:00\",\n"
					+ "            \"product\": {\n"
					+ "                \"productNo\": \"P002737\",\n"
					+ "                \"name\": \"Convenience Salat 250g\",\n"
					+ "                \"description\": \"\",\n"
					+ "                \"weight\": 0.250000000000\n"
					+ "            },\n"
					+ "            \"customer\": {\n"
					+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
					+ "                \"street\": \"teststraße\",\n"
					+ "                \"streetNo\": \"996\",\n"
					+ "                \"postal\": \"45678\",\n"
					+ "                \"city\": \"teststadt 3\",\n"
					+ "                \"countryCode\": \"DE\",\n"
					+ "                \"language\": \"de_DE\"\n"
					+ "            },\n"
					+ "            \"quantities\": [\n"
					+ "                {\n"
					+ "                    \"qty\": 12,\n"
					+ "                    \"uomCode\": \"Stk\"\n"
					+ "                }\n"
					+ "            ]\n"
					+ "        },\n"
					+ "        {\n"
					+ "            \"id\": 1000009,\n"
					+ "            \"orgCode\": \"001\",\n"
					+ "            \"orderDocumentNo\": \"0010\",\n"
					+ "            \"dateOrdered\": \"2020-06-25T00:00:00\",\n"
					+ "            \"product\": {\n"
					+ "                \"productNo\": \"P002737\",\n"
					+ "                \"name\": \"Convenience Salat 250g\",\n"
					+ "                \"description\": \"\",\n"
					+ "                \"weight\": 0.250000000000\n"
					+ "            },\n"
					+ "            \"customer\": {\n"
					+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
					+ "                \"street\": \"teststraße\",\n"
					+ "                \"streetNo\": \"996\",\n"
					+ "                \"postal\": \"45678\",\n"
					+ "                \"city\": \"teststadt 3\",\n"
					+ "                \"countryCode\": \"DE\",\n"
					+ "                \"language\": \"de_DE\"\n"
					+ "            },\n"
					+ "            \"quantities\": [\n"
					+ "                {\n"
					+ "                    \"qty\": 12,\n"
					+ "                    \"uomCode\": \"Stk\"\n"
					+ "                }\n"
					+ "            ]\n"
					+ "        },\n"
					+ "        {\n"
					+ "            \"id\": 1000011,\n"
					+ "            \"orgCode\": \"001\",\n"
					+ "            \"orderDocumentNo\": \"0012\",\n"
					+ "            \"dateOrdered\": \"2020-07-17T00:00:00\",\n"
					+ "            \"product\": {\n"
					+ "                \"productNo\": \"P002737\",\n"
					+ "                \"name\": \"Convenience Salat 250g\",\n"
					+ "                \"description\": \"\",\n"
					+ "                \"weight\": 0.250000000000\n"
					+ "            },\n"
					+ "            \"customer\": {\n"
					+ "                \"companyName\": \"Testkunde Kunde 1\",\n"
					+ "                \"street\": \"teststraße\",\n"
					+ "                \"streetNo\": \"996\",\n"
					+ "                \"postal\": \"45678\",\n"
					+ "                \"city\": \"teststadt 3\",\n"
					+ "                \"countryCode\": \"DE\",\n"
					+ "                \"language\": \"de_DE\"\n"
					+ "            },\n"
					+ "            \"quantities\": [\n"
					+ "                {\n"
					+ "                    \"qty\": 21,\n"
					+ "                    \"uomCode\": \"Stk\"\n"
					+ "                }\n"
					+ "            ]\n"
					+ "        }\n"
					+ "    ],\n"
					+ "    \"hasMoreItems\": false\n"
					+ "}");
		}
	}

}