package de.metas.shipper.gateway.derkurier.misc;

import de.metas.shipper.gateway.derkurier.DerKurierTestTools;
import de.metas.shipper.gateway.derkurier.restapi.models.RequestParticipant;
import de.metas.shipper.gateway.derkurier.restapi.models.RoutingRequest;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import org.junit.jupiter.api.Test;

import java.util.List;

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

public class ConvertersTest
{
	@Test
	public void createCsv()
	{
		final DeliveryOrder deliveryOrder = DerKurierTestTools.createTestDeliveryOrder();

		//
		// invoke the method under test
		final List<String> csv = new Converters().createCsv(deliveryOrder);

		assertThat(csv).hasSize(2);
		assertThat(csv.getFirst()).isEqualTo("2018-01-08;customerNumber-12345;to company;;;DE;54321;Köln;street 1 - street 2;1;030;2018-01-09;09:00;17:30;1;1;;;;parcelnumbe;some info for customer;1234;;;;;;;5;test@testmail.org;");
		assertThat(csv.get(1)).isEqualTo("2018-01-08;customerNumber-12345;to company;;;DE;54321;Köln;street 1 - street 2;1;030;2018-01-09;09:00;17:30;2;2;;;;parcelnumbe;some info for customer;1234;;;;;;;1;test@testmail.org;");
	}

	@Test
	public void createConsignee_not_deliverydate()
	{
		final DeliveryOrder deliveryOrder = DerKurierTestTools.createTestDeliveryOrder();
		final DeliveryOrder deliveryOrderWithoutDeliveryDate = deliveryOrder.toBuilder().deliveryDate(null).build();

		//
		// invoke the method under test
		final RequestParticipant consignee = new Converters().createConsignee(deliveryOrderWithoutDeliveryDate);

		assertThat(consignee.getTimeFrom()).isNull();
		assertThat(consignee.getTimeTo()).isNull();
	}

	@Test
	public void createRoutingRequestFrom()
	{
		final DeliveryOrder deliveryOrder = DerKurierTestTools.createTestDeliveryOrder();

		//
		// invoke the method under test
		final RoutingRequest routingRequest = new Converters().createRoutingRequestFrom(deliveryOrder);

		assertThat(routingRequest.getSendDate()).isEqualTo(deliveryOrder.getPickupDate().getDate());
	}
}
