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
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFileOperationFailedException;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import static de.metas.camel.shipping.shipmentcandidate.ShipmentCandidateJsonToXmlRouteBuilder.SHIPMENT_CANDIDATE_UPLOAD_ROUTE;
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
		AdviceWithRouteBuilder.adviceWith(context, SHIPMENT_CANDIDATE_UPLOAD_ROUTE,
				a -> a.interceptSendToEndpoint("log:shipment-candidate-upload-dummy")
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
		AdviceWithRouteBuilder.adviceWith(context, SHIPMENT_CANDIDATE_UPLOAD_ROUTE,
				a -> a.interceptSendToEndpoint("log:shipment-candidate-upload-dummy")
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
		AdviceWithRouteBuilder.adviceWith(context, SHIPMENT_CANDIDATE_UPLOAD_ROUTE,
				a -> a.interceptSendToEndpoint("log:shipment-candidate-upload-dummy")
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
			final var jsonResponse = this.getClass().getResourceAsStream("/de/metas/camel/shipping/shipmentcandidate/ShipmentCandicateApiResponse.json");
			assertThat(jsonResponse).isNotNull();
			exchange.getIn().setBody(jsonResponse);

			called++;
		}
	}

}
