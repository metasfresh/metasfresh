/*
 * #%L
 * de-metas-camel-pcm-file-import
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.camel.externalsystems.pcm.bpartner;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.pcm.service.OnDemandRoutesPCMController;
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

import static de.metas.camel.externalsystems.pcm.bpartner.GetBPartnerFromFileRouteBuilder.UPSERT_BPARTNER_ENDPOINT_ID;
import static org.assertj.core.api.Assertions.*;

public class GetBPartnerFromFileRouteBuilderTest extends CamelTestSupport
{
	private static final String BPARTNER_SYNC_DIRECT_ROUTE_ENDPOINT = "ProCareManagement-mockBPartnerSyncRoute";

	private static final String MOCK_EXTERNAL_SYSTEM_STATUS_ENDPOINT = "mock:externalSystemStatusEndpoint";
	private static final String MOCK_UPSERT_BPARTNER = "mock:UpsertBPartner";

	private static final String JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE = "0_JsonStartExternalSystemRequestBPartner_LocalFile.json";
	private static final String JSON_STOP_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE = "0_JsonStopExternalSystemRequestBPartner_LocalFile.json";
	private static final String BPARTNER_IMPORT_FILE_CSV = "10_BPartner_ImportFile.csv";
	private static final String JSON_UPSERT_BPARTNER_REQUEST_20 = "20_CamelUpsertBPartnerRequest.json";
	private static final String JSON_UPSERT_BPARTNER_REQUEST_30 = "30_CamelUpsertBPartnerRequest.json";

	private static final String BPARTNER_SAMPLE_RESOURCE_PATH = "/de/metas/camel/externalsystems/pcm/bpartner/" + BPARTNER_IMPORT_FILE_CSV;

	private final LocalFileBPartnerSyncServicePCMRouteBuilder bpartnerServiceRouteBuilder =
			new LocalFileBPartnerSyncServicePCMRouteBuilder(Mockito.mock(ProcessLogger.class));

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected RouteBuilder[] createRouteBuilders()
	{
		return new RouteBuilder[] { bpartnerServiceRouteBuilder, new OnDemandRoutesPCMController() };
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(GetBPartnerFromFileRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	public void happyFlow_SyncBPartners_LocalFile() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest externalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();
		final MockUpsertBPartnerProcessor mockUpsertBPartnerProcessor = new MockUpsertBPartnerProcessor();

		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, bpartnerServiceRouteBuilder.getStartBPartnerRouteId());

		context.start();

		//when
		template.sendBody("direct:" + bpartnerServiceRouteBuilder.getStartBPartnerRouteId(), externalSystemRequest);

		prepareSyncRouteForTesting(mockUpsertBPartnerProcessor, LocalFileBPartnerSyncServicePCMRouteBuilder.getBPartnersFromLocalFileRouteId(externalSystemRequest));

		final InputStream expectedUpsertBPartnerRequest_20 = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST_20);
		final BPUpsertCamelRequest bpartnerUpsertCamelRequest_20 = objectMapper.readValue(expectedUpsertBPartnerRequest_20, BPUpsertCamelRequest.class);

		final InputStream expectedUpsertBPartnerRequest_30 = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST_30);
		final BPUpsertCamelRequest bpartnerUpsertCamelRequest_30 = objectMapper.readValue(expectedUpsertBPartnerRequest_30, BPUpsertCamelRequest.class);

		final MockEndpoint bpartnerSyncMockEndpoint = getMockEndpoint(MOCK_UPSERT_BPARTNER);
		bpartnerSyncMockEndpoint.expectedBodiesReceived(bpartnerUpsertCamelRequest_20, bpartnerUpsertCamelRequest_30);

		final InputStream bpartnerImportFile = this.getClass().getResourceAsStream(BPARTNER_SAMPLE_RESOURCE_PATH);

		//and
		template.sendBodyAndHeader("direct:" + BPARTNER_SYNC_DIRECT_ROUTE_ENDPOINT, bpartnerImportFile, Exchange.FILE_NAME_ONLY, BPARTNER_IMPORT_FILE_CSV);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockUpsertBPartnerProcessor.called).isEqualTo(2);
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(1);
	}

	@Test
	public void disable_SyncBPartner_LocalFile() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();

		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, bpartnerServiceRouteBuilder.getStartBPartnerRouteId());
		prepareStartStopRouteForTesting(mockExternalSystemStatusProcessor, bpartnerServiceRouteBuilder.getStopBPartnerRouteId());

		final InputStream invokeStartExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest startExternalSystemRequest = objectMapper.readValue(invokeStartExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final InputStream invokeStopExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_STOP_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest stopExternalSystemRequest = objectMapper.readValue(invokeStopExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final String routeId = LocalFileBPartnerSyncServicePCMRouteBuilder.getBPartnersFromLocalFileRouteId(stopExternalSystemRequest);

		context.start();

		//when
		template.sendBody("direct:" + bpartnerServiceRouteBuilder.getStartBPartnerRouteId(), startExternalSystemRequest);

		assertThat(context.getRoute(routeId)).isNotNull();
		assertThat(context.getRouteController().getRouteStatus(routeId).isStarted()).isEqualTo(true);

		//and
		template.sendBody("direct:" + bpartnerServiceRouteBuilder.getStopBPartnerRouteId(), stopExternalSystemRequest);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(2);

		assertThat(context.getRoute(routeId)).isNull();
	}

	private void prepareStartStopRouteForTesting(
			@NonNull final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor,
			@NonNull final String routeId) throws Exception
	{
		AdviceWith.adviceWith(context, routeId,
							  advice -> advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
									  .skipSendToOriginalEndpoint()
									  .to(MOCK_EXTERNAL_SYSTEM_STATUS_ENDPOINT)
									  .process(mockExternalSystemStatusProcessor));
	}

	private void prepareSyncRouteForTesting(
			@NonNull final MockUpsertBPartnerProcessor mockUpsertBPartnerProcessor,
			@NonNull final String bpartnerSyncRouteId) throws Exception
	{
		AdviceWith.adviceWith(context, bpartnerSyncRouteId,
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

	@Getter
	private static class MockUpsertBPartnerProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}
}
