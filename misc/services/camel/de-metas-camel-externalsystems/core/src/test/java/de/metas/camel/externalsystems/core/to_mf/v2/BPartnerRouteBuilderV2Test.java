/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.core.to_mf.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.v2.BPRetrieveCamelRequest;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_BPARTNER_IDENTIFIER;
import static de.metas.common.externalsystem.ExternalSystemConstants.HEADER_EXTERNALSYSTEM_CONFIG_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.core.CoreConstants.AUTHORIZATION;
import static de.metas.camel.externalsystems.core.to_mf.v2.BPartnerRouteBuilderV2.RETRIEVE_BPARTNER_ENDPOINT_ID;
import static de.metas.camel.externalsystems.core.to_mf.v2.BPartnerRouteBuilderV2.RETRIEVE_BPARTNER_ROUTE_ID;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;

public class BPartnerRouteBuilderV2Test extends CamelTestSupport
{
	private static final String EXPECTED_BP_IDENTIFIER = "bpIdentifier";
	private static final int EXPECTED_EXTERNALSYSTEM_CONFIG_ID = 1;
	private static final int EXPECTED_PINSTANCE_ID = 2;

	private static final String MOCK_BPARTNER_RETRIEVE = "mock:bPartnerRetrieve";

	private static final String JSON_TO_MF_RETRIEVE_BPARTNER_REQUEST = "10_BPRetrieveCamelRequest.json";

	@Test
	void retrieveBPartner() throws Exception
	{
		prepareRouteForTesting();

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		final InputStream retrieveBPartnerRequestIS = this.getClass().getResourceAsStream(JSON_TO_MF_RETRIEVE_BPARTNER_REQUEST);
		final BPRetrieveCamelRequest retrieveBPartnerRequest = objectMapper.readValue(retrieveBPartnerRequestIS, BPRetrieveCamelRequest.class);

		final MockEndpoint bPartnerMockEndpoint = getMockEndpoint(MOCK_BPARTNER_RETRIEVE);
		bPartnerMockEndpoint.expectedHeaderReceived(HEADER_BPARTNER_IDENTIFIER, EXPECTED_BP_IDENTIFIER);
		bPartnerMockEndpoint.expectedHeaderReceived(HEADER_PINSTANCE_ID, EXPECTED_PINSTANCE_ID);
		bPartnerMockEndpoint.expectedHeaderReceived(HEADER_EXTERNALSYSTEM_CONFIG_ID, EXPECTED_EXTERNALSYSTEM_CONFIG_ID);
		bPartnerMockEndpoint.expectedHeaderReceived(AUTHORIZATION, "<secret>");

		//fire the route
		template.sendBody("{{" + ExternalSystemCamelConstants.MF_RETRIEVE_BPARTNER_V2_CAMEL_URI + "}}", retrieveBPartnerRequest);

		assertMockEndpointsSatisfied();
	}

	private void prepareRouteForTesting() throws Exception
	{
		AdviceWith.adviceWith(context, RETRIEVE_BPARTNER_ROUTE_ID,
							  advice -> {
								  advice.weaveById(RETRIEVE_BPARTNER_ENDPOINT_ID)
										  .replace()
										  .to(MOCK_BPARTNER_RETRIEVE);

								  advice.interceptSendToEndpoint("direct:" + UNPACK_V2_API_RESPONSE)
										  .skipSendToOriginalEndpoint()
										  .process(); // do nothing
							  }
		);
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
			properties.load(BPartnerRouteBuilderV2Test.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new BPartnerRouteBuilderV2();
	}
}
