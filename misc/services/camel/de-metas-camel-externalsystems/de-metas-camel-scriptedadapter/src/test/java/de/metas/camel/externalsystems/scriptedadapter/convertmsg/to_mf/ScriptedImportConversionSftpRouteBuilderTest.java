/*
 * #%L
 * de-metas-camel-scriptedadapter
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.v2.ExternalStatusCreateCamelRequest;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ScriptedImportConversionSftpRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID = "mock:Core-storeExternalStatus";

	private static final String EXTERNAL_SYSTEM_REQUEST = "sftp/1_SftpExternalSystemRequest.json";
	private static final String EXTERNAL_STATUS_ACTIVE_CAMEL_REQUEST = "sftp/30_ExternalStatusCreateCamelRequestActive.json";
	private static final String EXTERNAL_STATUS_INACTIVE_CAMEL_REQUEST = "sftp/30_ExternalStatusCreateCamelRequestInactive.json";

	private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		final ProducerTemplate producerTemplate = Mockito.mock(ProducerTemplate.class);
		return new ScriptedImportConversionSftpRouteBuilder(producerTemplate);
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(ScriptedImportConversionSftpRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	void enableSftpPolling() throws Exception
	{
		final MockStoreExternalStatusEP mockStoreExternalStatusEP = new MockStoreExternalStatusEP();

		prepareEnableRouteForTesting(mockStoreExternalStatusEP);

		context.start();

		final MockEndpoint storeStatusMockEP = getMockEndpoint(MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID);
		final InputStream jsonExternalStatusReqIS = this.getClass().getResourceAsStream(EXTERNAL_STATUS_ACTIVE_CAMEL_REQUEST);
		final ExternalStatusCreateCamelRequest jsonExternalStatusReq = objectMapper.readValue(jsonExternalStatusReqIS, ExternalStatusCreateCamelRequest.class);
		storeStatusMockEP.expectedBodiesReceived(jsonExternalStatusReq);

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper
				.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final String endpointName = invokeExternalSystemRequest.getParameters().get(PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME);

		// when fire the route
		template.sendBody("direct:" + ScriptedImportConversionSftpRouteBuilder.ENABLE_SFTP_POLLING_ROUTE_ID, invokeExternalSystemRequest);

		// then
		MockEndpoint.assertIsSatisfied(context);
		assertThat(mockStoreExternalStatusEP.called).isEqualTo(1);
		// Note: dynamic SFTP route creation is replaced with no-op in test (prepareEnableRouteForTesting),
		// so we only verify the status notification was sent, not the dynamic route existence
	}

	@Test
	void disableSftpPolling() throws Exception
	{
		final MockStoreExternalStatusEP mockStoreExternalStatusEP = new MockStoreExternalStatusEP();

		prepareDisableRouteForTesting(mockStoreExternalStatusEP);

		context.start();

		final MockEndpoint storeStatusMockEP = getMockEndpoint(MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID);
		final InputStream jsonExternalStatusReqIS = this.getClass().getResourceAsStream(EXTERNAL_STATUS_INACTIVE_CAMEL_REQUEST);
		final ExternalStatusCreateCamelRequest jsonExternalStatusReq = objectMapper.readValue(jsonExternalStatusReqIS, ExternalStatusCreateCamelRequest.class);
		storeStatusMockEP.expectedBodiesReceived(jsonExternalStatusReq);

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper
				.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final String endpointName = invokeExternalSystemRequest.getParameters().get(PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME);

		// Pre-register a dynamic SFTP route so disable can stop it.
		// We use a mock "direct:" route to avoid needing an actual SFTP server.
		context.addRoutes(new RouteBuilder()
		{
			@Override
			public void configure()
			{
				from("direct:" + endpointName)
						.routeId(endpointName)
						.log("mock dynamic sftp route");
			}
		});
		context.getRouteController().startRoute(endpointName);

		// when fire the route
		template.sendBody("direct:" + ScriptedImportConversionSftpRouteBuilder.DISABLE_SFTP_POLLING_ROUTE_ID, invokeExternalSystemRequest);

		// then
		MockEndpoint.assertIsSatisfied(context);
		assertThat(mockStoreExternalStatusEP.called).isEqualTo(1);

		// route should have been removed
		assertThat(context.getRoute(endpointName)).isNull();
	}

	private void prepareEnableRouteForTesting(@NonNull final MockStoreExternalStatusEP mockStoreExternalStatusEP) throws Exception
	{
		AdviceWith.adviceWith(context, ScriptedImportConversionSftpRouteBuilder.ENABLE_SFTP_POLLING_ROUTE_ID,
				advice -> {
					// Skip the actual SFTP dynamic route creation — replace the processor with a mock
					advice.weaveById(ScriptedImportConversionSftpRouteBuilder.ENABLE_SFTP_POLLING_PROCESSOR_ID)
							.replace()
							.process(exchange -> {
								// no-op: skip dynamic route creation in test
							});

					advice.weaveById(ScriptedImportConversionSftpRouteBuilder.ENABLE_SFTP_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID)
							.after()
							.to(MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID);

					advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
							.skipSendToOriginalEndpoint()
							.process(mockStoreExternalStatusEP);
				});
	}

	private void prepareDisableRouteForTesting(@NonNull final MockStoreExternalStatusEP mockStoreExternalStatusEP) throws Exception
	{
		AdviceWith.adviceWith(context, ScriptedImportConversionSftpRouteBuilder.DISABLE_SFTP_POLLING_ROUTE_ID,
				advice -> {
					advice.weaveById(ScriptedImportConversionSftpRouteBuilder.DISABLE_SFTP_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID)
							.after()
							.to(MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID);

					advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
							.skipSendToOriginalEndpoint()
							.process(mockStoreExternalStatusEP);
				});
	}

	private static class MockStoreExternalStatusEP implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}
}
