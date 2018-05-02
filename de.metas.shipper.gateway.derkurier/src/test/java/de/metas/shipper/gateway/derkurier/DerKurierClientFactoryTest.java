package de.metas.shipper.gateway.derkurier;

import static org.assertj.core.api.Assertions.assertThat;
// import static org.springframework.test.web.client.RequestMatcher.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import de.metas.shipper.gateway.derkurier.misc.DerKurierShipperConfig;
import de.metas.shipper.gateway.derkurier.restapi.models.RequestParticipant;
import de.metas.shipper.gateway.derkurier.restapi.models.Routing;
import de.metas.shipper.gateway.derkurier.restapi.models.RoutingRequest;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class DerKurierClientFactoryTest
{
	private static final String REST_API_BASE_URL = "https://leoz.derkurier.de:13000/rs/api/v1";

	private static final ClassPathResource ROUTING_RESPONSE_JSON = new ClassPathResource("/RoutingResponse.json");

	@Before
	public void init()
	{
	}

	@Test
	public void postRoutingRequest()
	{
		final DerKurierClientFactory derKurierClientFactory = new DerKurierClientFactory();

		final DerKurierShipperConfig shipperConfig = DerKurierShipperConfig.builder()
				.restApiBaseUrl(REST_API_BASE_URL)
				.customerNumber("12345").build();

		final DerKurierClient client = derKurierClientFactory.createClient(shipperConfig);

		final MockRestServiceServer mockServer = MockRestServiceServer.createServer(client.getRestTemplate());

		final RoutingRequest routingRequest = createRoutingRequest();

		mockServer.expect(requestTo(REST_API_BASE_URL + "/routing/request"))
				.andRespond(withSuccess(ROUTING_RESPONSE_JSON, MediaType.APPLICATION_JSON));

		// invoke the method under test
		final Routing routing = client.postRoutingRequest(routingRequest);

		assertThat(routing).isNotNull();
	}

	public static RoutingRequest createRoutingRequest()
	{
		final RequestParticipant sender = RequestParticipant.builder()
				.country("DE")
				.zip("50969")
				.timeFrom(LocalTime.of(10, 20, 30, 40)) // here we provide localtime up to the nano-second, but the web-service only wants hour:minute
				.timeTo(LocalTime.of(11, 21, 31, 41))
				.build();

		final RoutingRequest routingRequest = RoutingRequest.builder()
				.sendDate(LocalDate.now())
				.sender(sender)
				.build();
		return routingRequest;
	}
}
