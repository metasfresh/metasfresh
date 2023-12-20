/*
 * #%L
 * de-metas-camel-sap-file-import
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.camel.externalsystems.sap.conversionRate;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.sap.conversionRate.routecontroller.LocalFileConversionRateSyncServiceRouteBuilder;
import de.metas.camel.externalsystems.sap.conversionRate.routecontroller.SFTPConversionRateSyncServiceRouteBuilder;
import de.metas.camel.externalsystems.sap.service.OnDemandRoutesController;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.v2.conversionRate.JsonCurrencyRateCreateRequest;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static de.metas.camel.externalsystems.sap.conversionRate.routecontroller.LocalFileConversionRateSyncServiceRouteBuilder.START_CONVERSION_RATE_SYNC_LOCAL_FILE_ROUTE_ID;
import static de.metas.camel.externalsystems.sap.conversionRate.routecontroller.LocalFileConversionRateSyncServiceRouteBuilder.STOP_CONVERSION_RATE_SYNC_LOCAL_FILE_ROUTE_ID;
import static de.metas.camel.externalsystems.sap.conversionRate.routecontroller.SFTPConversionRateSyncServiceRouteBuilder.START_CONVERSION_RATE_SYNC_SFTP_ROUTE_ID;
import static de.metas.camel.externalsystems.sap.conversionRate.routecontroller.SFTPConversionRateSyncServiceRouteBuilder.STOP_CONVERSION_RATE_SYNC_SFTP_ROUTE_ID;
import static org.assertj.core.api.Assertions.*;

public class GetConversionRateFromFileRouteBuilderTest extends CamelTestSupport
{
	private static final String CONVERSION_RATE_SYNC_DIRECT_ROUTE_ENDPOINT = "SAP-mockConversionRateSyncRoute";

	private static final String JSON_START_EXTERNAL_SYSTEM_REQUEST_SFTP = "0_JsonStartExternalSystemRequestConversionRate_SFTP.json";
	private static final String JSON_STOP_EXTERNAL_SYSTEM_REQUEST_SFTP = "0_JsonStopExternalSystemRequestConversionRate_SFTP.json";
	private static final String JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE = "0_JsonStartExternalSystemRequestConversionRate_LocalFile.json";
	private static final String JSON_STOP_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE = "0_JsonStopExternalSystemRequestConversionRate_LocalFile.json";

	private static final String CONVERSION_RATE_SAMPLE_DATA_FILE = "10_ConversionRateSample.dat";

	private static final String JSON_CURRENCY_RATE_CREATE_REQUEST = "20_JsonCurrencyRateCreateRequest.json";

	private static final String CONVERSION_RATE_SAMPLE_RESOURCE_PATH = "/de/metas/camel/externalsystems/sap/conversionRate/" + CONVERSION_RATE_SAMPLE_DATA_FILE;

	private static final String MOCK_EXTERNAL_SYSTEM_STATUS_ENDPOINT = "mock:externalSystemStatusEndpoint";
	private static final String MOCK_UPSERT_CONVERSION_RATE_ENDPOINT = "mock:upsertConversionRateEndpoint";

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected RouteBuilder[] createRouteBuilders()
	{
		return new RouteBuilder[] {
				new SFTPConversionRateSyncServiceRouteBuilder(),
				new LocalFileConversionRateSyncServiceRouteBuilder(),
				new OnDemandRoutesController()
		};
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(GetConversionRateFromFileRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	public void enable_and_disable_SyncConversionRate_SFTP() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();

		final InputStream invokeStartExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_SFTP);
		final JsonExternalSystemRequest startExternalSystemRequest = objectMapper.readValue(invokeStartExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final InputStream invokeStopExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_STOP_EXTERNAL_SYSTEM_REQUEST_SFTP);
		final JsonExternalSystemRequest stopExternalSystemRequest = objectMapper.readValue(invokeStopExternalSystemRequestIS, JsonExternalSystemRequest.class);

		context.start();

		prepareEnableRouteForTesting(mockExternalSystemStatusProcessor);

		//when
		template.sendBody("direct:" + START_CONVERSION_RATE_SYNC_SFTP_ROUTE_ID, startExternalSystemRequest);

		//then
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(1);
		assertThat(context.getRoute(getSFTPConversionRateSyncRouteId(stopExternalSystemRequest))).isNotNull();
		assertThat(context.getRouteController().getRouteStatus(getSFTPConversionRateSyncRouteId(stopExternalSystemRequest)).isStarted()).isTrue();

		//when
		prepareDisableRouteForTesting(mockExternalSystemStatusProcessor);

		template.sendBody("direct:" + STOP_CONVERSION_RATE_SYNC_SFTP_ROUTE_ID, stopExternalSystemRequest);

		//then
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(2);
		assertThat(context.getRoute(getSFTPConversionRateSyncRouteId(stopExternalSystemRequest))).isNull();
	}

	@Test
	public void enable_and_disable_SyncConversionRate_LocalFile() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final GetConversionRateFromFileRouteBuilderTest.MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new GetConversionRateFromFileRouteBuilderTest.MockExternalSystemStatusProcessor();

		final InputStream invokeStartExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest startExternalSystemRequest = objectMapper.readValue(invokeStartExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final InputStream invokeStopExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_STOP_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest stopExternalSystemRequest = objectMapper.readValue(invokeStopExternalSystemRequestIS, JsonExternalSystemRequest.class);

		context.start();

		prepareEnableRouteForTesting(mockExternalSystemStatusProcessor);

		//when
		template.sendBody("direct:" + START_CONVERSION_RATE_SYNC_LOCAL_FILE_ROUTE_ID, startExternalSystemRequest);

		//then
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(1);
		assertThat(context.getRoute(getLocalFileConversionRateSyncRouteId(stopExternalSystemRequest))).isNotNull();
		assertThat(context.getRouteController().getRouteStatus(getLocalFileConversionRateSyncRouteId(stopExternalSystemRequest)).isStarted()).isTrue();

		//when
		prepareDisableRouteForTesting(mockExternalSystemStatusProcessor);

		template.sendBody("direct:" + STOP_CONVERSION_RATE_SYNC_LOCAL_FILE_ROUTE_ID, stopExternalSystemRequest);

		//then
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(2);
		assertThat(context.getRoute(getLocalFileConversionRateSyncRouteId(stopExternalSystemRequest))).isNull();
	}

	@Test
	public void happyFlow_SyncConversionRate_SFTP() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		//given
		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_SFTP);
		final JsonExternalSystemRequest externalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();
		final MockCreateConversionRateProcessor mockCreateConversionRateProcessor = new MockCreateConversionRateProcessor();

		prepareEnableRouteForTesting(mockExternalSystemStatusProcessor);

		context.start();

		template.sendBody("direct:" + START_CONVERSION_RATE_SYNC_SFTP_ROUTE_ID, externalSystemRequest);

		assertThat(context.getRoute(getSFTPConversionRateSyncRouteId(externalSystemRequest))).isNotNull();

		prepareSyncRouteForTesting(mockCreateConversionRateProcessor, getSFTPConversionRateSyncRouteId(externalSystemRequest));

		final InputStream expectedUpsertConversionRateRequest = this.getClass().getResourceAsStream(JSON_CURRENCY_RATE_CREATE_REQUEST);
		final MockEndpoint conversionRateSyncMockEndpoint = getMockEndpoint(MOCK_UPSERT_CONVERSION_RATE_ENDPOINT);
		conversionRateSyncMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedUpsertConversionRateRequest, JsonCurrencyRateCreateRequest.class));

		final InputStream conversionRateSampleInputStream = this.getClass().getResourceAsStream(CONVERSION_RATE_SAMPLE_RESOURCE_PATH);

		//when
		template.sendBodyAndHeader("direct:" + CONVERSION_RATE_SYNC_DIRECT_ROUTE_ENDPOINT, conversionRateSampleInputStream, Exchange.FILE_NAME_ONLY, CONVERSION_RATE_SAMPLE_DATA_FILE);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockCreateConversionRateProcessor.called).isEqualTo(1);
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(1);
	}

	@Test
	public void happyFlow_SyncConversionRate_LocalFile() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		//given
		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_START_EXTERNAL_SYSTEM_REQUEST_LOCAL_FILE);
		final JsonExternalSystemRequest externalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final MockExternalSystemStatusProcessor mockExternalSystemStatusProcessor = new MockExternalSystemStatusProcessor();
		final MockCreateConversionRateProcessor mockUpsertConversionRateProcessor = new MockCreateConversionRateProcessor();

		prepareEnableRouteForTesting(mockExternalSystemStatusProcessor);

		context.start();

		template.sendBody("direct:" + START_CONVERSION_RATE_SYNC_LOCAL_FILE_ROUTE_ID, externalSystemRequest);

		assertThat(context.getRoute(getLocalFileConversionRateSyncRouteId(externalSystemRequest))).isNotNull();

		prepareSyncRouteForTesting(mockUpsertConversionRateProcessor, getLocalFileConversionRateSyncRouteId(externalSystemRequest));

		final InputStream expectedUpsertConversionRateRequest = this.getClass().getResourceAsStream(JSON_CURRENCY_RATE_CREATE_REQUEST);
		final MockEndpoint conversionRateSyncMockEndpoint = getMockEndpoint(MOCK_UPSERT_CONVERSION_RATE_ENDPOINT);
		conversionRateSyncMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedUpsertConversionRateRequest, JsonCurrencyRateCreateRequest.class));

		final InputStream conversionRateSampleInputStream = this.getClass().getResourceAsStream(CONVERSION_RATE_SAMPLE_RESOURCE_PATH);

		//when
		template.sendBodyAndHeader("direct:" + CONVERSION_RATE_SYNC_DIRECT_ROUTE_ENDPOINT, conversionRateSampleInputStream, Exchange.FILE_NAME_ONLY, CONVERSION_RATE_SAMPLE_DATA_FILE);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockUpsertConversionRateProcessor.called).isEqualTo(1);
		assertThat(mockExternalSystemStatusProcessor.called).isEqualTo(1);
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

	private void prepareSyncRouteForTesting(
			@NonNull final GetConversionRateFromFileRouteBuilderTest.MockCreateConversionRateProcessor mockUpsertConversionRateProcessor,
			@NonNull final String conversionRateSyncRouteId) throws Exception
	{
		AdviceWith.adviceWith(context, conversionRateSyncRouteId,
							  advice -> {
								  advice.replaceFromWith("direct:" + CONVERSION_RATE_SYNC_DIRECT_ROUTE_ENDPOINT);

								  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_CONVERSION_RATE_CAMEL_URI + "}}")
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_UPSERT_CONVERSION_RATE_ENDPOINT)
										  .process(mockUpsertConversionRateProcessor);
							  });
	}

	@NonNull
	private static String getSFTPConversionRateSyncRouteId(@NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		return SFTPConversionRateSyncServiceRouteBuilder.getConversionRateFromSFTPRouteId(externalSystemRequest);
	}

	@NonNull
	private String getLocalFileConversionRateSyncRouteId(@NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		return LocalFileConversionRateSyncServiceRouteBuilder.getConversionRateFromLocalFileRouteId(externalSystemRequest);
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

	private static class MockCreateConversionRateProcessor implements Processor
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
