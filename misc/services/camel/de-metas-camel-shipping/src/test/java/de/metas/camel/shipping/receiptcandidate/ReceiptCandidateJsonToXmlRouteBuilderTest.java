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

import de.metas.camel.shipping.RouteBuilderCommonUtil;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFileOperationFailedException;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class ReceiptCandidateJsonToXmlRouteBuilderTest extends CamelTestSupport
{
	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new ReceiptCandidateJsonToXmlRouteBuilder();
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
		AdviceWithRouteBuilder.adviceWith(context, ReceiptCandidateJsonToXmlRouteBuilder.MF_RECEIPT_CANDIDATE_JSON_TO_FILEMAKER_XML,
				a -> a.interceptSendToEndpoint("http://baseURL/receipts/receiptCandidates")
						.skipSendToOriginalEndpoint()
						.process(emptyHttpResult));

		final var uploadEndpoint = new ResultUploadEndpoint();
		AdviceWithRouteBuilder.adviceWith(context, RouteBuilderCommonUtil.FILEMAKER_UPLOAD_ROUTE,
				a -> a.interceptSendToEndpoint("log:upload-dummy")
						.skipSendToOriginalEndpoint()
						.process(uploadEndpoint));

		final var postEndpoint = new FeedbackHttpPOSTEndpoint();
		AdviceWithRouteBuilder.adviceWith(context, ReceiptCandidateJsonToXmlRouteBuilder.RECEIPT_CANDIDATE_FEEDBACK_TO_MF,
				a -> a.interceptSendToEndpoint("http://baseURL/receipts/receiptCandidatesResult")
						.skipSendToOriginalEndpoint()
						.process(postEndpoint));

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
		AdviceWithRouteBuilder.adviceWith(context, ReceiptCandidateJsonToXmlRouteBuilder.MF_RECEIPT_CANDIDATE_JSON_TO_FILEMAKER_XML,
				a -> a.interceptSendToEndpoint("http://baseURL/receipts/receiptCandidates")
						.skipSendToOriginalEndpoint()
						.process(normalHttpResult));

		final var uploadEndpoint = new ResultUploadEndpoint();
		AdviceWithRouteBuilder.adviceWith(context, RouteBuilderCommonUtil.FILEMAKER_UPLOAD_ROUTE,
				a -> a.interceptSendToEndpoint("log:upload-dummy")
						.skipSendToOriginalEndpoint()
						.process(uploadEndpoint));

		final var feedbackHttpPOSTEndpoint = new FeedbackHttpPOSTEndpoint();
		AdviceWithRouteBuilder.adviceWith(context, ReceiptCandidateJsonToXmlRouteBuilder.RECEIPT_CANDIDATE_FEEDBACK_TO_MF,
				a -> a.interceptSendToEndpoint("http://baseURL/receipts/receiptCandidatesResult")
						.skipSendToOriginalEndpoint()
						.process(feedbackHttpPOSTEndpoint));

		final NotifyBuilder notify = new NotifyBuilder(context)
				.wereSentTo("http://baseURL/receipts/receiptCandidatesResult")
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
		AdviceWithRouteBuilder.adviceWith(context, ReceiptCandidateJsonToXmlRouteBuilder.MF_RECEIPT_CANDIDATE_JSON_TO_FILEMAKER_XML,
				a -> a.interceptSendToEndpoint("http://baseURL/receipts/receiptCandidates")
						.skipSendToOriginalEndpoint()
						.process(normalHttpResult));

		final var uploadWithExceptionEndpoint = new ResultUploadEndpointWithException();
		AdviceWithRouteBuilder.adviceWith(context, RouteBuilderCommonUtil.FILEMAKER_UPLOAD_ROUTE,
				a -> a.interceptSendToEndpoint("log:upload-dummy")
						.skipSendToOriginalEndpoint()
						.process(uploadWithExceptionEndpoint));

		final var feedbackHttpPOSTEndpoint = new FeedbackHttpPOSTEndpoint();
		AdviceWithRouteBuilder.adviceWith(context, ReceiptCandidateJsonToXmlRouteBuilder.RECEIPT_CANDIDATE_FEEDBACK_TO_MF,
				a -> a.interceptSendToEndpoint("http://baseURL/receipts/receiptCandidatesResult")
						.skipSendToOriginalEndpoint()
						.process(feedbackHttpPOSTEndpoint));

		final NotifyBuilder notify = new NotifyBuilder(context)
				.wereSentTo("http://baseURL/receipts/receiptCandidatesResult")
				.whenDone(1).create();

		context.start();
		assertThat(notify.matchesWaitTime()).isTrue();

		assertThat(normalHttpResult.called).as("normalHttpResult shall be called once").isEqualTo(1);
		assertThat(feedbackHttpPOSTEndpoint.called).isEqualTo(1);
		assertThat(feedbackHttpPOSTEndpoint.messageBody).contains("GenericFileOperationFailedException: Simulated Exception");
		assertThat(uploadWithExceptionEndpoint.called).isEqualTo(1);
	}

	private static class JsonGETEndpoint implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;

			exchange.getIn().setBody("{\n"
					+ "    \"transactionKey\": \"92a88885-c4a8-4150-ba74-408a8428c62e\",\n"
					+ "    \"items\": [\n"
					+ "        {\n"
					+ "            \"id\": 1000000,\n"
					+ "            \"orgCode\": \"001\",\n"
					+ "            \"orderDocumentNo\": \"820464\",\n"
					+ "            \"dateOrdered\": \"2020-07-20T00:00:00\",\n"
					+ "            \"product\": {\n"
					+ "                \"productNo\": \"P002737\",\n"
					+ "                \"name\": \"Convenience Salat 250g\",\n"
					+ "                \"description\": \"\",\n"
					+ "                \"weight\": 0.250000000000\n"
					+ "            },\n"
					+ "            \"quantities\": [\n"
					+ "                {\n"
					+ "                    \"qty\": 1,\n"
					+ "                    \"uomCode\": \"Stk\"\n"
					+ "                }\n"
					+ "            ]\n"
					+ "        },\n"
					+ "        {\n"
					+ "            \"id\": 1000001,\n"
					+ "            \"orgCode\": \"001\",\n"
					+ "            \"orderDocumentNo\": \"820465\",\n"
					+ "            \"dateOrdered\": \"2020-07-21T00:00:00\",\n"
					+ "            \"product\": {\n"
					+ "                \"productNo\": \"P002737\",\n"
					+ "                \"name\": \"Convenience Salat 250g\",\n"
					+ "                \"description\": \"\",\n"
					+ "                \"weight\": 0.250000000000\n"
					+ "            },\n"
					+ "            \"quantities\": [\n"
					+ "                {\n"
					+ "                    \"qty\": 2,\n"
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