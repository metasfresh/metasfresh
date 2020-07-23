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

import de.metas.camel.shipping.RouteBuilderCommonUtil;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFileOperationFailedException;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShipmentCandidateJsonToXmlRouteBuilderTest extends CamelTestSupport
{
	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new ShipmentCandidateJsonToXmlRouteBuilder();
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Test
	void test_emptyResult() throws Exception
	{
		final var emptyHttpResult = new EmptyResult();
		AdviceWithRouteBuilder.adviceWith(context, ShipmentCandidateJsonToXmlRouteBuilder.MF_SHIPMENT_CANDIDATE_JSON_TO_FILEMAKER_XML,
				a -> a.interceptSendToEndpoint("http://baseURL/shipments/shipmentCandidates")
						.skipSendToOriginalEndpoint()
						.process(emptyHttpResult));

		final var postEndpoint = new FeedbackHttpPOSTEndpoint();
		AdviceWithRouteBuilder.adviceWith(context, ShipmentCandidateJsonToXmlRouteBuilder.SHIPMENT_CANDIDATE_FEEDBACK_TO_MF,
				a -> a.interceptSendToEndpoint("http://baseURL/shipments/shipmentCandidatesResult")
						.skipSendToOriginalEndpoint()
						.process(postEndpoint));

		final var uploadEndpoint = new ResultUploadEndpoint();
		AdviceWithRouteBuilder.adviceWith(context, RouteBuilderCommonUtil.FILEMAKER_UPLOAD_ROUTE,
				a -> a.interceptSendToEndpoint("log:upload-dummy")
						.skipSendToOriginalEndpoint()
						.process(uploadEndpoint));

		final NotifyBuilder notify = new NotifyBuilder(context)
				.whenDone(1).create();

		context.start();
		assertThat(notify.matchesWaitTime()).isTrue();

		assertThat(emptyHttpResult.called).as("emptyHttpResult shall be called once").isEqualTo(1);
		assertThat(uploadEndpoint.called).isEqualTo(0);
		assertThat(postEndpoint.called).isEqualTo(0);
	}

	private static class EmptyResult implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			exchange.getIn().setBody("{\n"
					+ "    \"transactionKey\": \"74dcbcf6-265d-41b3-bb15-1cde4793684a\",\n"
					+ "    \"items\": [],\n"
					+ "    \"hasMoreItems\": false\n"
					+ "}");
			called++;
		}
	}

	private static class ResultUploadEndpoint implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	private static class ResultUploadEndpointWithException implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			try
			{
				throw new GenericFileOperationFailedException("Simulated Exception");
			}
			finally
			{
				called++;
			}
		}
	}

	private static class FeedbackHttpPOSTEndpoint implements Processor
	{
		private int called = 0;
		private String messageBody;

		@Override
		public void process(final Exchange exchange)
		{
			messageBody = exchange.getIn().getBody(String.class);
			called++;
		}
	}

	@Test
	void test_oneResultItem() throws Exception
	{
		final var normalHttpResult = new JsonGETEndpoint();
		AdviceWithRouteBuilder.adviceWith(context, ShipmentCandidateJsonToXmlRouteBuilder.MF_SHIPMENT_CANDIDATE_JSON_TO_FILEMAKER_XML,
				a -> a.interceptSendToEndpoint("http://baseURL/shipments/shipmentCandidates")
						.skipSendToOriginalEndpoint()
						.process(normalHttpResult));

		final var uploadEndpoint = new ResultUploadEndpoint();
		AdviceWithRouteBuilder.adviceWith(context, RouteBuilderCommonUtil.FILEMAKER_UPLOAD_ROUTE,
				a -> a.interceptSendToEndpoint("log:upload-dummy")
						.skipSendToOriginalEndpoint()
						.process(uploadEndpoint));

		final var feedbackHttpPOSTEndpoint = new FeedbackHttpPOSTEndpoint();
		AdviceWithRouteBuilder.adviceWith(context, ShipmentCandidateJsonToXmlRouteBuilder.SHIPMENT_CANDIDATE_FEEDBACK_TO_MF,
				a -> a.interceptSendToEndpoint("http://baseURL/shipments/shipmentCandidatesResult")
						.skipSendToOriginalEndpoint()
						.process(feedbackHttpPOSTEndpoint));

		final NotifyBuilder notify = new NotifyBuilder(context)
				.wereSentTo("http://baseURL/shipments/shipmentCandidatesResult")
				.whenDone(1).create();

		context.start();
		assertThat(notify.matchesWaitTime()).isTrue();

		assertThat(normalHttpResult.called).as("normalHttpResult shall be called once").isEqualTo(1);
		assertThat(uploadEndpoint.called).isEqualTo(1);
		assertThat(feedbackHttpPOSTEndpoint.called).as("postEndpoint shall be called once").isEqualTo(1);
		assertThat(feedbackHttpPOSTEndpoint.messageBody).containsSequence("\"outcome\":\"OK\"");
	}

	@Test
	void test_uploadFail() throws Exception
	{
		final var normalHttpResult = new JsonGETEndpoint();
		AdviceWithRouteBuilder.adviceWith(context, ShipmentCandidateJsonToXmlRouteBuilder.MF_SHIPMENT_CANDIDATE_JSON_TO_FILEMAKER_XML,
				a -> a.interceptSendToEndpoint("http://baseURL/shipments/shipmentCandidates")
						.skipSendToOriginalEndpoint()
						.process(normalHttpResult));

		final var uploadWithExceptionEndpoint = new ResultUploadEndpointWithException();
		AdviceWithRouteBuilder.adviceWith(context, RouteBuilderCommonUtil.FILEMAKER_UPLOAD_ROUTE,
				a -> a.interceptSendToEndpoint("log:upload-dummy")
						.skipSendToOriginalEndpoint()
						.process(uploadWithExceptionEndpoint));

		final var feedbackHttpPOSTEndpoint = new FeedbackHttpPOSTEndpoint();
		AdviceWithRouteBuilder.adviceWith(context, ShipmentCandidateJsonToXmlRouteBuilder.SHIPMENT_CANDIDATE_FEEDBACK_TO_MF,
				a -> a.interceptSendToEndpoint("http://baseURL/shipments/shipmentCandidatesResult")
						.skipSendToOriginalEndpoint()
						.process(feedbackHttpPOSTEndpoint));

		final NotifyBuilder notify = new NotifyBuilder(context)
				.wereSentTo("http://baseURL/shipments/shipmentCandidatesResult")
				.whenDone(1)
				.create();

		context.start();
		assertThat(notify.matchesWaitTime()).isTrue();

		assertThat(normalHttpResult.called).as("normalHttpResult shall be called once").isEqualTo(1);
		assertThat(uploadWithExceptionEndpoint.called).isEqualTo(1);
		assertThat(feedbackHttpPOSTEndpoint.called).isEqualTo(1);
		assertThat(feedbackHttpPOSTEndpoint.messageBody).contains("GenericFileOperationFailedException: Simulated Exception");
	}

	private static class JsonGETEndpoint implements Processor
	{
		private int called = 0;

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

			called++;
		}
	}

}