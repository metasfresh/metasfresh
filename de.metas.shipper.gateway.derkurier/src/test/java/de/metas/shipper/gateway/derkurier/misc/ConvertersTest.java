package de.metas.shipper.gateway.derkurier.misc;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Test;

import de.metas.shipper.gateway.derkurier.DerKurierTestTools;
import de.metas.shipper.gateway.derkurier.restapi.models.RequestParticipant;
import de.metas.shipper.gateway.derkurier.restapi.models.RoutingRequest;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;

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
		assertThat(csv.get(0)).isEqualTo("2018-01-08;customerNumber-12345;to company;;DE;54321;Köln;street 1 - street 2;1;030;2018-01-09;09:00;17:30;1;1;;;;parcelnumber1;some info for customer;1234;;;;;;;5;");
		assertThat(csv.get(1)).isEqualTo("2018-01-08;customerNumber-12345;to company;;DE;54321;Köln;street 1 - street 2;1;030;2018-01-09;09:00;17:30;2;2;;;;parcelnumber2;some info for customer;1234;;;;;;;1;");
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

	@Test public void test() throws UnsupportedEncodingException
	{
		byte[] data = {123, 34, 99, 111, 100, 101, 34, 58, 34, 82, 79, 85, 84, 69, 95, 78, 79, 84, 95, 65, 86, 65, 73, 76, 65, 66, 76, 69, 95, 70, 79, 82, 95, 71, 73, 86, 69, 78, 95, 80, 65, 82, 65, 77, 69, 84, 69, 82, 34, 44, 34, 109, 101, 115, 115, 97, 103, 101, 34, 58, 34, 67, 111, 110, 115, 105, 103, 110, 101, 101, 58, 32, 32, 110, 111, 32, 82, 111, 117, 116, 101, 32, 102, 111, 117, 110, 100, 34, 125};
		String str = new String(data, "ISO-8859-1");
		System.out.println(str);
	}
}
