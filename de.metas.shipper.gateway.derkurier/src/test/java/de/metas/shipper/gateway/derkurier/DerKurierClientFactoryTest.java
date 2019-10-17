package de.metas.shipper.gateway.derkurier;

import static org.assertj.core.api.Assertions.assertThat;
// import static org.springframework.test.web.client.RequestMatcher.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.time.LocalTime;
import java.util.Optional;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import de.metas.attachments.AttachmentEntryService;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.DocumentNoBuilderFactory;
import de.metas.shipper.gateway.derkurier.misc.Converters;
import de.metas.shipper.gateway.derkurier.misc.DerKurierDeliveryOrderService;
import de.metas.shipper.gateway.derkurier.misc.DerKurierShipperConfig;
import de.metas.shipper.gateway.derkurier.misc.DerKurierShipperConfigRepository;
import de.metas.shipper.gateway.derkurier.misc.ParcelNumberGenerator;
import de.metas.shipper.gateway.derkurier.restapi.models.Routing;
import de.metas.shipper.gateway.derkurier.restapi.models.RoutingRequest;
import de.metas.util.Services;

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

	@Test
	public void postRoutingRequest()
	{
		final Converters converters = new Converters();
		final DerKurierShipperConfigRepository derKurierShipperConfigRepository = new DerKurierShipperConfigRepository();

		final AttachmentEntryService attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();

		final IDocumentNoBuilderFactory documentNoBuilderFactory = new DocumentNoBuilderFactory(Optional.empty());
		Services.registerService(IDocumentNoBuilderFactory.class, documentNoBuilderFactory);

		final DerKurierClientFactory derKurierClientFactory = new DerKurierClientFactory(
				derKurierShipperConfigRepository,
				new DerKurierDeliveryOrderService(attachmentEntryService),
				new DerKurierDeliveryOrderRepository(converters),
				converters);

		final DerKurierShipperConfig shipperConfig = DerKurierShipperConfig.builder()
				.restApiBaseUrl(REST_API_BASE_URL)
				.customerNumber("12345")
				.parcelNumberAdSequenceId(ParcelNumberGenerator.NO_AD_SEQUENCE_ID_FOR_TESTING)
				.collectorCode("01")
				.customerCode("02")
				.desiredTimeFrom(LocalTime.of(9, 0))
				.desiredTimeTo(LocalTime.of(17, 0))
				.build();

		final DerKurierClient client = derKurierClientFactory.createClient(shipperConfig);

		final MockRestServiceServer mockServer = MockRestServiceServer.createServer(client.getRestTemplate());

		final RoutingRequest routingRequest = DerKurierTestTools.createRoutingRequest_times_with_seconds();

		mockServer.expect(requestTo(REST_API_BASE_URL + "/routing/request"))
				.andRespond(withSuccess(ROUTING_RESPONSE_JSON, MediaType.APPLICATION_JSON));

		// invoke the method under test
		final Routing routing = client.postRoutingRequest(routingRequest);

		assertThat(routing).isNotNull();
	}

}
