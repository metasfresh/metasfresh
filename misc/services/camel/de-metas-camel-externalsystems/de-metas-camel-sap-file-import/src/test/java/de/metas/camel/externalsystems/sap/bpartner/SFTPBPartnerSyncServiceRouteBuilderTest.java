/*
 * #%L
 * de-metas-camel-sap
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

package de.metas.camel.externalsystems.sap.bpartner;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static de.metas.camel.externalsystems.sap.bpartner.GetBPartnersSFTPRouteBuilder.UPSERT_BPARTNER_ENDPOINT_ID;
import static de.metas.camel.externalsystems.sap.bpartner.SFTPBPartnerSyncServiceRouteBuilder.START_BPARTNERS_SYNC_ROUTE_ID;
import static de.metas.camel.externalsystems.sap.bpartner.SFTPBPartnerSyncServiceRouteBuilder.STOP_BPARTNERS_SYNC_ROUTE_ID;
import static org.assertj.core.api.Assertions.*;

public class SFTPBPartnerSyncServiceRouteBuilderTest extends CamelTestSupport
{
	private static final String BPARTNER_SYNC_DIRECT_ROUTE_ENDPOINT = "SAP-mockBPartnerSyncRoute";

	private static final String MOCK_EXTERNAL_SYSTEM_STATUS_ENDPOINT = "mock:externalSystemStatusEndpoint";
	private static final String MOCK_UPSERT_BPARTNER = "mock:UpsertBPartner";

	private static final String JSON_START_EXTERNAL_SYSTEM_REQUEST = "0_JsonStartExternalSystemRequestBPartner.json";
	private static final String JSON_STOP_EXTERNAL_SYSTEM_REQUEST = "0_JsonStopExternalSystemRequestBPartner.json";
	private static final String BPARTNER_SAMPLE_DAT_FILE = "10_BPartnerSample.dat";
	private static final String JSON_UPSERT_BPARTNER_REQUEST = "20_CamelUpsertBPartnerCompositeRequest.json";

	private static final String BPARTNER_SAMPLE_RESOURCE_PATH = "/de/metas/camel/externalsystems/sap/bpartner/" + BPARTNER_SAMPLE_DAT_FILE;

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new SFTPBPartnerSyncServiceRouteBuilder(Mockito.mock(ProcessLogger.class));
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(SFTPBPartnerSyncServiceRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	public void happyFlow_SyncBPartners() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest externalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();
		final MockUpsertBPartnerProcessor mockUpsertBPartnerProcessor = new MockUpsertBPartnerProcessor();

		prepareEnableRouteForTesting(mockExternalSystemStatusProcessor);

		context.start();

		//when
		template.sendBody("direct:" + START_BPARTNERS_SYNC_ROUTE_ID, externalSystemRequest);

		prepareSyncRouteForTesting(mockUpsertBPartnerProcessor, GetBPartnersSFTPRouteBuilder.buildRouteId(externalSystemRequest.getExternalSystemChildConfigValue()));

		final InputStream expectedUpsertProductRequest = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST);
		final MockEndpoint productSyncMockEndpoint = getMockEndpoint(MOCK_UPSERT_BPARTNER);
		productSyncMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedUpsertProductRequest, BPUpsertCamelRequest.class));

		final InputStream materialSampleInputStream = this.getClass().getResourceAsStream(BPARTNER_SAMPLE_RESOURCE_PATH);

		//and
		template.sendBodyAndHeader("direct:" + BPARTNER_SYNC_DIRECT_ROUTE_ENDPOINT, materialSampleInputStream, Exchange.FILE_NAME_ONLY, BPARTNER_SAMPLE_DAT_FILE);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockUpsertBPartnerProcessor.called).isEqualTo(1);
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(1);
	}

	@Test
	public void disable_SyncBPartners() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();

		prepareEnableRouteForTesting(mockExternalSystemStatusProcessor);
		prepareDisableRouteForTesting(mockExternalSystemStatusProcessor);

		final InputStream invokeStartExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest startExternalSystemRequest = objectMapper.readValue(invokeStartExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final InputStream invokeStopExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_STOP_EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest stopExternalSystemRequest = objectMapper.readValue(invokeStopExternalSystemRequestIS, JsonExternalSystemRequest.class);

		context.start();

		//when
		template.sendBody("direct:" + START_BPARTNERS_SYNC_ROUTE_ID, startExternalSystemRequest);

		//and
		template.sendBody("direct:" + STOP_BPARTNERS_SYNC_ROUTE_ID, stopExternalSystemRequest);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(2);

		assertThat(context.getRoute(GetBPartnersSFTPRouteBuilder.buildRouteId(stopExternalSystemRequest.getExternalSystemChildConfigValue()))).isNull();
	}

	private void prepareEnableRouteForTesting(
			@NonNull final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, START_BPARTNERS_SYNC_ROUTE_ID,
							  advice -> advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
									  .skipSendToOriginalEndpoint()
									  .to(MOCK_EXTERNAL_SYSTEM_STATUS_ENDPOINT)
									  .process(mockExternalSystemStatusProcessor));
	}

	private void prepareDisableRouteForTesting(
			@NonNull final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, STOP_BPARTNERS_SYNC_ROUTE_ID,
							  advice -> advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
									  .skipSendToOriginalEndpoint()
									  .to(MOCK_EXTERNAL_SYSTEM_STATUS_ENDPOINT)
									  .process(mockExternalSystemStatusProcessor));
	}

	private void prepareSyncRouteForTesting(
			@NonNull final SFTPBPartnerSyncServiceRouteBuilderTest.MockUpsertBPartnerProcessor mockUpsertBPartnerProcessor,
			@NonNull final String bPartnerSyncRouteId) throws Exception
	{
		AdviceWith.adviceWith(context, bPartnerSyncRouteId,
										  advice -> {
								  advice.replaceFromWith("direct:" + BPARTNER_SYNC_DIRECT_ROUTE_ENDPOINT);

								  advice.weaveById(UPSERT_BPARTNER_ENDPOINT_ID)
										  .replace()
										  .to(MOCK_UPSERT_BPARTNER)
										  .process(mockUpsertBPartnerProcessor);
							  });
	}

	private static class MockExternalSystemStatusProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	private static class MockUpsertBPartnerProcessor implements Processor
	{
		@Getter
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}
}
