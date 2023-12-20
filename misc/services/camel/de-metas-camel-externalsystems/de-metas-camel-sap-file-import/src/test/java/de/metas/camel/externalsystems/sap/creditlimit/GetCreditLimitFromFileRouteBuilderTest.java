/*
 * #%L
 * de-metas-camel-sap-file-import
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

package de.metas.camel.externalsystems.sap.creditlimit;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.common.v2.CreditLimitDeleteRequest;
import de.metas.camel.externalsystems.sap.service.OnDemandRoutesController;
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

import static de.metas.camel.externalsystems.sap.creditlimit.LocalFileCreditLimitSyncServiceRouteBuilder.START_CREDIT_LIMIT_SYNC_LOCAL_FILE_ROUTE_ID;
import static de.metas.camel.externalsystems.sap.creditlimit.LocalFileCreditLimitSyncServiceRouteBuilder.STOP_CREDIT_LIMIT_SYNC_LOCAL_FILE_ROUTE_ID;
import static de.metas.camel.externalsystems.sap.creditlimit.SFTPCreditLimitSyncServiceRouteBuilder.START_CREDIT_LIMIT_SYNC_SFTP_ROUTE_ID;
import static de.metas.camel.externalsystems.sap.creditlimit.SFTPCreditLimitSyncServiceRouteBuilder.STOP_CREDIT_LIMIT_SYNC_SFTP_ROUTE_ID;
import static de.metas.camel.externalsystems.sap.creditlimit.UpsertCreditLimitRouteBuilder.UPSERT_CREDIT_LIMIT_PROCESSOR_ID;
import static de.metas.camel.externalsystems.sap.creditlimit.UpsertCreditLimitRouteBuilder.UPSERT_CREDIT_LIMIT_ROUTE_ID;
import static org.assertj.core.api.Assertions.*;

public class GetCreditLimitFromFileRouteBuilderTest extends CamelTestSupport
{
	private static final String CREDIT_LIMIT_SYNC_DIRECT_ROUTE_ENDPOINT = "SAP-mockCreditLimitSyncRoute";

	private static final String MOCK_EXTERNAL_SYSTEM_STATUS_ENDPOINT = "mock:externalSystemStatusEndpoint";
	private static final String MOCK_DELETE_CREDIT_LIMIT_ENDPOINT = "mock:deleteCreditLimitEndpoint";
	private static final String MOCK_UPSERT_CREDIT_LIMIT_ENDPOINT = "mock:upsertCreditLimitEndpoint";

	private static final String JSON_START_EXTERNAL_SYSTEM_REQUEST_SFTP = "0_JsonStartExternalSystemRequestCreditLimit_SFTP.json";
	private static final String JSON_STOP_EXTERNAL_SYSTEM_REQUEST_SFTP = "0_JsonStopExternalSystemRequestCreditLimit_SFTP.json";
	private static final String JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE = "0_JsonStartExternalSystemRequestCreditLimit_LocalFile.json";
	private static final String JSON_STOP_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE = "0_JsonStopExternalSystemRequestCreditLimit_LocalFile.json";
	private static final String CREDIT_LIMIT_SAMPLE_DATA_FILE = "10_CreditLimitSample.dat";
	private static final String JSON_DELETE_CREDIT_LIMIT_REQUEST = "20_JsonRequestCreditLimitDelete.json";
	private static final String JSON_UPSERT_BPARTNER_REQUEST = "30_BPUpsertCamelRequest.json";

	private static final String CREDIT_LIMIT_SAMPLE_RESOURCE_PATH = "/de/metas/camel/externalsystems/sap/creditlimit/" + CREDIT_LIMIT_SAMPLE_DATA_FILE;

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected RouteBuilder[] createRouteBuilders()
	{
		return new RouteBuilder[] {
				new SFTPCreditLimitSyncServiceRouteBuilder(Mockito.mock(ProcessLogger.class)),
				new LocalFileCreditLimitSyncServiceRouteBuilder(Mockito.mock(ProcessLogger.class)),
				new UpsertCreditLimitRouteBuilder(),
				new OnDemandRoutesController()
		};
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(GetCreditLimitFromFileRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	public void happyFlow_SyncCreditLimits_SFTP() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_SFTP);
		final JsonExternalSystemRequest externalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();
		final MockUpsertCreditLimitProcessor mockUpsertCreditLimitProcessor = new MockUpsertCreditLimitProcessor();
		final MockDeleteCreditLimitProcessor mockDeleteCreditLimitProcessor = new MockDeleteCreditLimitProcessor();

		prepareEnableRouteForTesting(mockExternalSystemStatusProcessor);

		context.start();

		template.sendBody("direct:" + START_CREDIT_LIMIT_SYNC_SFTP_ROUTE_ID, externalSystemRequest);

		assertThat(context.getRoute(getSFTPCreditLimitsSyncRouteId(externalSystemRequest))).isNotNull();

		prepareSyncRouteForTesting(mockUpsertCreditLimitProcessor, mockDeleteCreditLimitProcessor, getSFTPCreditLimitsSyncRouteId(externalSystemRequest));

		final InputStream expectedDeleteCreditLimitRequest = this.getClass().getResourceAsStream(JSON_DELETE_CREDIT_LIMIT_REQUEST);
		final MockEndpoint creditLimitDeleteMockEndpoint = getMockEndpoint(MOCK_DELETE_CREDIT_LIMIT_ENDPOINT);
		creditLimitDeleteMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedDeleteCreditLimitRequest, CreditLimitDeleteRequest.class));

		final InputStream expectedUpsertBPRequest = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST);
		final MockEndpoint creditLimitSyncMockEndpoint = getMockEndpoint(MOCK_UPSERT_CREDIT_LIMIT_ENDPOINT);
		creditLimitSyncMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedUpsertBPRequest, BPUpsertCamelRequest.class));

		final InputStream creditLimitSampleInputStream = this.getClass().getResourceAsStream(CREDIT_LIMIT_SAMPLE_RESOURCE_PATH);

		//when
		template.sendBodyAndHeader("direct:" + CREDIT_LIMIT_SYNC_DIRECT_ROUTE_ENDPOINT, creditLimitSampleInputStream, Exchange.FILE_NAME_ONLY, CREDIT_LIMIT_SAMPLE_DATA_FILE);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockDeleteCreditLimitProcessor.called).isEqualTo(1);
		assertThat(mockUpsertCreditLimitProcessor.called).isEqualTo(1);
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(1);
	}

	@Test
	public void happyFlow_SyncCreditLimits_LocalFile() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest externalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();
		final MockUpsertCreditLimitProcessor mockUpsertCreditLimitProcessor = new MockUpsertCreditLimitProcessor();
		final MockDeleteCreditLimitProcessor mockDeleteCreditLimitProcessor = new MockDeleteCreditLimitProcessor();

		prepareEnableRouteForTesting(mockExternalSystemStatusProcessor);

		context.start();

		template.sendBody("direct:" + START_CREDIT_LIMIT_SYNC_LOCAL_FILE_ROUTE_ID, externalSystemRequest);

		assertThat(context.getRoute(getLocalFileCreditLimitsSyncRouteId(externalSystemRequest))).isNotNull();

		prepareSyncRouteForTesting(mockUpsertCreditLimitProcessor, mockDeleteCreditLimitProcessor, getLocalFileCreditLimitsSyncRouteId(externalSystemRequest));

		final InputStream expectedDeleteCreditLimitRequest = this.getClass().getResourceAsStream(JSON_DELETE_CREDIT_LIMIT_REQUEST);
		final MockEndpoint creditLimitDeleteMockEndpoint = getMockEndpoint(MOCK_DELETE_CREDIT_LIMIT_ENDPOINT);
		creditLimitDeleteMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedDeleteCreditLimitRequest, CreditLimitDeleteRequest.class));

		final InputStream expectedUpsertBPRequest = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST);
		final MockEndpoint creditLimitSyncMockEndpoint = getMockEndpoint(MOCK_UPSERT_CREDIT_LIMIT_ENDPOINT);
		creditLimitSyncMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedUpsertBPRequest, BPUpsertCamelRequest.class));

		final InputStream creditLimitSampleInputStream = this.getClass().getResourceAsStream(CREDIT_LIMIT_SAMPLE_RESOURCE_PATH);

		//when
		template.sendBodyAndHeader("direct:" + CREDIT_LIMIT_SYNC_DIRECT_ROUTE_ENDPOINT, creditLimitSampleInputStream, Exchange.FILE_NAME_ONLY, CREDIT_LIMIT_SAMPLE_DATA_FILE);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockDeleteCreditLimitProcessor.called).isEqualTo(1);
		assertThat(mockUpsertCreditLimitProcessor.called).isEqualTo(1);
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(1);
	}

	@Test
	public void enable_and_disable_SyncCreditLimits_SFTP() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final GetCreditLimitFromFileRouteBuilderTest.MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new GetCreditLimitFromFileRouteBuilderTest.MockExternalSystemStatusProcessor();

		final InputStream invokeStartExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_SFTP);
		final JsonExternalSystemRequest startExternalSystemRequest = objectMapper.readValue(invokeStartExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final InputStream invokeStopExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_STOP_EXTERNAL_SYSTEM_REQUEST_SFTP);
		final JsonExternalSystemRequest stopExternalSystemRequest = objectMapper.readValue(invokeStopExternalSystemRequestIS, JsonExternalSystemRequest.class);

		context.start();

		prepareEnableRouteForTesting(mockExternalSystemStatusProcessor);

		//when
		template.sendBody("direct:" + START_CREDIT_LIMIT_SYNC_SFTP_ROUTE_ID, startExternalSystemRequest);

		//then
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(1);
		assertThat(context.getRoute(getSFTPCreditLimitsSyncRouteId(stopExternalSystemRequest))).isNotNull();
		assertThat(context.getRouteController().getRouteStatus(getSFTPCreditLimitsSyncRouteId(stopExternalSystemRequest)).isStarted()).isNotNull();

		//when
		prepareDisableRouteForTesting(mockExternalSystemStatusProcessor);

		template.sendBody("direct:" + STOP_CREDIT_LIMIT_SYNC_SFTP_ROUTE_ID, stopExternalSystemRequest);

		//then
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(2);
		assertThat(context.getRoute(getSFTPCreditLimitsSyncRouteId(stopExternalSystemRequest))).isNull();
	}

	@Test
	public void enable_and_disable_SyncCreditLimits_LocalFile() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final GetCreditLimitFromFileRouteBuilderTest.MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new GetCreditLimitFromFileRouteBuilderTest.MockExternalSystemStatusProcessor();

		final InputStream invokeStartExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest startExternalSystemRequest = objectMapper.readValue(invokeStartExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final InputStream invokeStopExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_STOP_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest stopExternalSystemRequest = objectMapper.readValue(invokeStopExternalSystemRequestIS, JsonExternalSystemRequest.class);

		context.start();

		prepareEnableRouteForTesting(mockExternalSystemStatusProcessor);

		//when
		template.sendBody("direct:" + START_CREDIT_LIMIT_SYNC_LOCAL_FILE_ROUTE_ID, startExternalSystemRequest);

		//then
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(1);
		assertThat(context.getRoute(getLocalFileCreditLimitsSyncRouteId(stopExternalSystemRequest))).isNotNull();
		assertThat(context.getRouteController().getRouteStatus(getLocalFileCreditLimitsSyncRouteId(stopExternalSystemRequest)).isStarted()).isNotNull();

		//when
		prepareDisableRouteForTesting(mockExternalSystemStatusProcessor);

		template.sendBody("direct:" + STOP_CREDIT_LIMIT_SYNC_LOCAL_FILE_ROUTE_ID, stopExternalSystemRequest);

		//then
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(2);
		assertThat(context.getRoute(getLocalFileCreditLimitsSyncRouteId(stopExternalSystemRequest))).isNull();
	}

	@NonNull
	private String getSFTPCreditLimitsSyncRouteId(@NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		return SFTPCreditLimitSyncServiceRouteBuilder.getCreditLimitFromSFTPRouteId(externalSystemRequest);
	}

	@NonNull
	private String getLocalFileCreditLimitsSyncRouteId(@NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		return LocalFileCreditLimitSyncServiceRouteBuilder.getCreditLimitFromLocalFileRouteId(externalSystemRequest);
	}

	private void prepareSyncRouteForTesting(
			@NonNull final MockUpsertCreditLimitProcessor mockUpsertCreditLimitProcessor,
			@NonNull final MockDeleteCreditLimitProcessor mockDeleteCreditLimitProcessor,
			@NonNull final String creditLimitSyncRouteId) throws Exception
	{
		AdviceWith.adviceWith(context, creditLimitSyncRouteId,
							  advice -> advice.replaceFromWith("direct:" + CREDIT_LIMIT_SYNC_DIRECT_ROUTE_ENDPOINT));

		AdviceWith.adviceWith(context, UPSERT_CREDIT_LIMIT_ROUTE_ID,
							  advice -> {
								  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_DELETE_BPARTNER_CREDIT_LIMIT_CAMEL_URI + "}}")
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_DELETE_CREDIT_LIMIT_ENDPOINT)
										  .process(mockDeleteCreditLimitProcessor);

								  advice.weaveById(UPSERT_CREDIT_LIMIT_PROCESSOR_ID)
										  .after()
										  .to(MOCK_UPSERT_CREDIT_LIMIT_ENDPOINT);

								  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
										  .skipSendToOriginalEndpoint()
										  .process(mockUpsertCreditLimitProcessor);
							  });
	}

	private void prepareEnableRouteForTesting(
			@NonNull final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, OnDemandRoutesController.START_HANDLE_ON_DEMAND_ROUTE_ID,
							  advice -> advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
									  .skipSendToOriginalEndpoint()
									  .to(MOCK_EXTERNAL_SYSTEM_STATUS_ENDPOINT)
									  .process(mockExternalSystemStatusProcessor));
	}

	private void prepareDisableRouteForTesting(
			@NonNull final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, OnDemandRoutesController.STOP_HANDLE_ON_DEMAND_ROUTE_ID,
							  advice -> advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
									  .skipSendToOriginalEndpoint()
									  .to(MOCK_EXTERNAL_SYSTEM_STATUS_ENDPOINT)
									  .process(mockExternalSystemStatusProcessor));
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

	private static class MockDeleteCreditLimitProcessor implements Processor
	{
		@Getter
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	private static class MockUpsertCreditLimitProcessor implements Processor
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
