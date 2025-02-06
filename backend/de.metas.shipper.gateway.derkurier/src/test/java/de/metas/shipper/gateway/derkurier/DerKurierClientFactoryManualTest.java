package de.metas.shipper.gateway.derkurier;

import de.metas.attachments.AttachmentEntryService;
import de.metas.shipper.gateway.derkurier.misc.Converters;
import de.metas.shipper.gateway.derkurier.misc.DerKurierShipperConfig;
import de.metas.shipper.gateway.derkurier.misc.DerKurierShipperConfigRepository;
import de.metas.shipper.gateway.derkurier.misc.ParcelNumberGenerator;
import de.metas.shipper.gateway.derkurier.restapi.models.Routing;
import de.metas.shipper.gateway.derkurier.restapi.models.RoutingRequest;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

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

/**
 * This test makes a real invocation on the "Der Kurier" web service. It's usually {@link Ignore}d.
 */
public class DerKurierClientFactoryManualTest
{
	@Test
	@Ignore // remove the ignore to run this test manually
	public void manualTest()
	{
		final Converters converters = new Converters();
		final DerKurierShipperConfigRepository derKurierShipperConfigRepository = new DerKurierShipperConfigRepository();

		final AttachmentEntryService attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();

		final DerKurierDeliveryOrderRepository derKurierDeliveryOrderRepository = new DerKurierDeliveryOrderRepository(converters);
		final DerKurierClientFactory derKurierClientFactory = new DerKurierClientFactory(
				derKurierShipperConfigRepository,
				new DerKurierDeliveryOrderService(derKurierDeliveryOrderRepository, attachmentEntryService),
				derKurierDeliveryOrderRepository,
				converters);

		final DerKurierShipperConfig shipperConfig = DerKurierShipperConfig.builder()
				.restApiBaseUrl("https://leoz.derkurier.de:13000/rs/api/v1")
				.customerNumber("12345")
				.parcelNumberAdSequenceId(ParcelNumberGenerator.NO_AD_SEQUENCE_ID_FOR_TESTING)
				.collectorCode("00")
				.customerCode("01")
				.desiredTimeFrom(LocalTime.now())
				.desiredTimeTo(LocalTime.now().plusHours(1))
				.build();
		final DerKurierClient client = derKurierClientFactory.createClient(shipperConfig);

		final RoutingRequest routingRequest = DerKurierTestTools.createRoutingRequest_times_with_seconds();
		final Routing routing = client.postRoutingRequest(routingRequest);
		assertThat(routing).isNotNull();
	}

}
