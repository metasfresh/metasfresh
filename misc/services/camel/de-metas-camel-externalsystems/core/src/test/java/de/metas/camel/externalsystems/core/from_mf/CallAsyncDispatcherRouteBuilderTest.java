/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.core.from_mf;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessLogger;
import lombok.NonNull;
import lombok.Value;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.SEDA_CONCURRENT_CONSUMERS_PROPERTY;
import static de.metas.camel.externalsystems.core.from_mf.CallDispatcherRouteBuilder.DISPATCH_ASYNC_ROUTE;
import static de.metas.camel.externalsystems.core.from_mf.CallDispatcherRouteBuilder.FROM_MF_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;
import static org.assertj.core.api.Assertions.*;

public class CallAsyncDispatcherRouteBuilderTest extends CamelTestSupport
{
	private static final String CUSTOM_HEADER = "customHeader";

	private static final String JSON_EXTERNAL_SYSTEM_REQ = "JsonExternalSystemRequest.json";
	private static final String COMMAND = "myExternalSystem-myCommand";

	private final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new CallDispatcherRouteBuilder(Mockito.mock(ProcessLogger.class));
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(CallAsyncDispatcherRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	void callEndpoint() throws Exception
	{
		//given
		final int noMessages = 10;
		final int concurrentConsumers = Integer.parseInt(context().getPropertiesComponent().loadProperties().getProperty(SEDA_CONCURRENT_CONSUMERS_PROPERTY));

		final MockSleepingEP mockSleepingEP = new MockSleepingEP(noMessages);

		preparePushRouteForTesting(mockSleepingEP);

		final InputStream jsonExternalSysRequest = CallAsyncDispatcherRouteBuilderTest.class.getResourceAsStream(JSON_EXTERNAL_SYSTEM_REQ);
		final String req = objectMapper.readValue(jsonExternalSysRequest, String.class);

		//when
		for (int messageIndex = 0; messageIndex < noMessages; messageIndex++)
		{
			template.sendBodyAndHeader(direct(DISPATCH_ASYNC_ROUTE).getUri(), req, CUSTOM_HEADER, messageIndex);
		}

		mockSleepingEP.completableFuture.get();

		//then
		assertThat(mockSleepingEP.currentCount.get()).isEqualTo(noMessages);
		assertThat(mockSleepingEP.threadId2MessageIndex.keySet().size()).isEqualTo(concurrentConsumers);

		final ArrayList<Integer> allValues = new ArrayList<>();
		for (final Integer threadId : mockSleepingEP.threadId2MessageIndex.keySet())
		{
			final List<Integer> values = mockSleepingEP.threadId2MessageIndex.get(threadId);
			assertThat(values).isNotEmpty();

			allValues.addAll(values);
		}
		assertThat(allValues.size()).isEqualTo(noMessages);
	}

	private void preparePushRouteForTesting(
			@NonNull final MockSleepingEP mockSleepingEP) throws Exception
	{
		AdviceWith.adviceWith(context,
							  CallDispatcherRouteBuilder.DISPATCH_ASYNC_ROUTE_ID,
							  advice -> advice.interceptSendToEndpoint("direct:" + COMMAND)
									  .skipSendToOriginalEndpoint()
									  .process(mockSleepingEP)
		);

		//dev-note: mock rabbitMQ route
		AdviceWith.adviceWith(context,
							  FROM_MF_ROUTE_ID,
							  advice -> advice.replaceFromWith("direct:notImportant"));
	}

	@Value
	private static class MockSleepingEP implements Processor
	{
		Map<Integer, List<Integer>> threadId2MessageIndex = new ConcurrentHashMap<>();
		AtomicInteger currentCount = new AtomicInteger(0);
		CompletableFuture<Void> completableFuture = new CompletableFuture<>();

		int expectedCount;

		public MockSleepingEP(final int expectedCount)
		{
			this.expectedCount = expectedCount;
		}

		@Override
		public void process(final Exchange exchange) throws Exception
		{
			currentCount.incrementAndGet();

			Thread.sleep(1000);

			final int currentThreadId = (int)Thread.currentThread().getId();

			final List<Integer> currentMessageList = Optional.ofNullable(threadId2MessageIndex.get(currentThreadId))
					.orElseGet(ArrayList::new);

			currentMessageList.add(exchange.getIn().getHeader(CUSTOM_HEADER, Integer.class));
			threadId2MessageIndex.put(currentThreadId, currentMessageList);

			if (currentCount.get() == expectedCount)
			{
				completableFuture.complete(null);
			}
		}
	}
}
