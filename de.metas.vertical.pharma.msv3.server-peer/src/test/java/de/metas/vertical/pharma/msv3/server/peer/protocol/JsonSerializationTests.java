package de.metas.vertical.pharma.msv3.server.peer.protocol;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.Id;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.protocol.types.Quantity;

/*
 * #%L
 * metasfresh-pharma.msv3.server-peer
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

public class JsonSerializationTests
{
	private JSONTestHelper jsonTestHelper;

	@Before
	public void init()
	{
		jsonTestHelper = new JSONTestHelper();
	}

	@Test
	public void test_MSV3ServerRequest() throws Exception
	{
		jsonTestHelper.testSerializeDeserialize(MSV3ServerRequest.requestAll());
	}

	@Test
	public void test_MSV3StockAvailabilityUpdatedEvent() throws Exception
	{
		jsonTestHelper.testSerializeDeserialize(MSV3StockAvailabilityUpdatedEvent.builder()
				.item(MSV3StockAvailability.builder()
						.pzn(1234567891)
						.qty(111)
						.build())
				.item(MSV3StockAvailability.builder()
						.pzn(1234567892)
						.qty(222)
						.build())
				.build());
	}

	@Test
	public void test_MSV3ProductExcludesUpdateEvent() throws Exception
	{
		jsonTestHelper.testSerializeDeserialize(MSV3ProductExcludesUpdateEvent.builder()
				.item(MSV3ProductExclude.builder()
						.pzn(PZN.of(1234567891))
						.bpartnerId(1234)
						.build())
				.build());
	}

	@Test
	public void test_MSV3UserChangedBatchEvent() throws Exception
	{
		jsonTestHelper.testSerializeDeserialize(MSV3UserChangedBatchEvent.builder()
				.event(MSV3UserChangedEvent.prepareCreatedOrUpdatedEvent()
						.username("u1")
						.password("p1")
						.bpartnerId(1234567)
						.bpartnerLocationId(7654321)
						.build())
				.event(MSV3UserChangedEvent.deletedEvent("u2"))
				.deleteAllOtherUsers(true)
				.build());
	}

	@Test
	public void test_MSV3OrderSyncRequest() throws IOException
	{
		jsonTestHelper.testSerializeDeserialize(MSV3OrderSyncRequest.builder()
				.orderId(Id.of("12345"))
				.bpartner(BPartnerId.of(1234, 5678))
				.orderPackage(MSV3OrderSyncRequestPackage.builder()
						.item(MSV3OrderSyncRequestPackageItem.builder()
								.id(Id.of("11111"))
								.pzn(PZN.of(666777888))
								.qty(Quantity.of(333))
								.build())
						.build())
				.build());
	}

	@Test
	public void test_MSV3OrderSyncResponse_OK() throws IOException
	{
		jsonTestHelper.testSerializeDeserialize(MSV3OrderSyncResponse.ok(Id.of("123"), BPartnerId.of(1, 2))
				.item(MSV3OrderSyncResponseItem.builder()
						.orderPackageItemId(Id.of("1234"))
						.olCandId(555)
						.build())
				.build());
	}

	@Test
	public void test_MSV3OrderSyncResponse_Error() throws IOException
	{
		jsonTestHelper.testSerializeDeserialize(MSV3OrderSyncResponse.error(Id.of("123"), BPartnerId.of(1, 2), new RuntimeException()));
	}

}
