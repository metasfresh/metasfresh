/*
 * #%L
 * de-metas-common-procurement
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.procurement.sync.protocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.procurement.sync.Constants;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonSerializeDeserializeTests
{
	private final ObjectMapper jsonObjectMapper = Constants.PROCUREMENT_WEBUI_OBJECT_MAPPER;

	@Test
	void syncConfirmRequest() throws Exception
	{
		assertOK(SyncConfirmationRequest.builder()
						.syncConfirmation(SyncConfirmation.forConfirmId(23L))
						.syncConfirmation(SyncConfirmation.builder()
								.confirmId(24L)
								.dateConfirmed(new Date())
								.serverEventId("serverEventId").build())
						.build(),
				SyncConfirmationRequest.class);
	}

	@Test
	void syncBPartnersRequest() throws IOException
	{
		final SyncBPartner syncBPartner = SyncBPartner.builder()
				.name("name")
				.uuid("uuid")
				.contract(SyncContract.builder()
						.dateFrom(new Date())
						.rfq_uuid("rfq_uuid")
						.contractLine(SyncContractLine.builder().build())
						.build())
				.user(SyncUser.builder()
						.email("email")
						.build())
				.rfq(SyncRfQ.builder()
						.qtyCUInfo("34")
						.build())
				.syncConfirmationId(23L)
				.build();
		assertOK(SyncBPartnersRequest.of(syncBPartner), SyncBPartnersRequest.class);
	}

	@Test
	void syncProductsRequest() throws IOException
	{
		final SyncProduct syncProduct = SyncProduct.builder()
				.packingInfo("packingInfo")
				.nameTrl("trlKey", "trl")
				.shared(true)
				.uuid("uuid")
				.build();

		assertOK(SyncProductsRequest.of(syncProduct), SyncProductsRequest.class);
	}

	@Test
	void syncInfoMessage() throws IOException
	{
		final SyncInfoMessageRequest syncInfoMessageRequest = SyncInfoMessageRequest.of("String");

		assertOK(syncInfoMessageRequest, SyncInfoMessageRequest.class);
	}

	@Test
	void confirm() throws IOException
	{
		final SyncConfirmationRequest syncConfirmationRequest = SyncConfirmationRequest.builder()
				.syncConfirmation(SyncConfirmation.forConfirmId(23L))
				.syncConfirmation(SyncConfirmation.forConfirmId(24L))
				.build();

		assertOK(syncConfirmationRequest, SyncConfirmationRequest.class);
	}

	@Test
	void syncRfQsRequest() throws IOException
	{
		final SyncRfQsRequest syncRfQsRequest = SyncRfQsRequest.builder().syncRfq(SyncRfQ.builder().build()).build();

		assertOK(syncRfQsRequest, SyncRfQsRequest.class);
	}

	@Test
	void syncRfQCloseEventsRequest() throws IOException
	{
		final SyncRfQCloseEventsRequest syncRfQCloseEventsRequest = SyncRfQCloseEventsRequest.builder()
				.syncRfQCloseEvent(SyncRfQCloseEvent.builder()
						.winnerKnown(true)
						.rfq_uuid("rfq_uuid")
						.plannedSupply(SyncProductSupply.builder()
								.qty(BigDecimal.TEN)
								.bpartner_uuid("bpartner_uuid")
								.contractLine_uuid("contractLine_uuid")
								.weekPlanning(false)
								.syncConfirmationId(33L)
								.uuid("uuid")
								.version(1)
								.build())
						.build())
				.build();

		assertOK(syncRfQCloseEventsRequest, SyncRfQCloseEventsRequest.class);
	}

	//
	//

	@Test
	void syncWeeklySupplyRequest() throws IOException
	{
		final SyncWeeklySupplyRequest syncWeeklySupplyRequest = SyncWeeklySupplyRequest.builder()
				.weeklySupply(SyncWeeklySupply.builder()
						.weekDay(new Date())
						.trend("trend")
						.bpartner_uuid("bpartner_uuid")
						.product_uuid("product_uuid")
						.version(1)
						.syncConfirmationId(20L)
						.build())
				.build();

		assertOK(syncWeeklySupplyRequest, SyncWeeklySupplyRequest.class);
	}

	@Test
	void syncProductSuppliesRequest() throws IOException
	{
		final SyncProductSuppliesRequest syncProductSuppliesRequest = SyncProductSuppliesRequest.of(SyncProductSupply.builder()
				.version(1)
				.qty(BigDecimal.TEN)
				.uuid("uuid")
				.syncConfirmationId(9L)
				.weekPlanning(true)
				.product_uuid("product_uuid")
				.bpartner_uuid("bpartner_uuid")
				.contractLine_uuid("contractLine_uuid")
				.build());

		assertOK(syncProductSuppliesRequest, SyncProductSuppliesRequest.class);
	}

	@Test
	void syncRfQChangeRequest() throws IOException
	{
		final SyncRfQChangeRequest syncRfQChangeRequest = SyncRfQChangeRequest.builder()
				.priceChangeEvent(SyncRfQPriceChangeEvent.builder()
						.product_uuid("product_uuid")
						.syncConfirmationId(10L)
						.rfq_uuid("rfq_uuid")
						.price(BigDecimal.ONE)
						.uuid("uuid")
						.product_uuid("product_uuid")
						.rfq_uuid("rfq_uuid")
						.build())
				.qtyChangeEvent(SyncRfQQtyChangeEvent.builder()
						.day(new Date())
						.qty(BigDecimal.TEN)
						.product_uuid("product_uuid")
						.rfq_uuid("rfq_uuid")
						.uuid("uuid")
						.syncConfirmationId(9L)
						.build())
				.build();

		assertOK(syncRfQChangeRequest, SyncRfQChangeRequest.class);
	}

	@Test
	void getAllBPartnersRequest() throws IOException
	{
		assertOK(GetAllBPartnersRequest.INSTANCE, GetAllBPartnersRequest.class);
	}

	@Test
	void getAllProductsRequest() throws IOException
	{
		assertOK(GetAllProductsRequest.INSTANCE, GetAllProductsRequest.class);
	}

	@Test
	void getInfoMessageRequest() throws IOException
	{
		assertOK(GetInfoMessageRequest.INSTANCE, GetInfoMessageRequest.class);
	}

	private <T> void assertOK(final T objectOrig, final Class<T> valueType) throws IOException
	{
		final String jsonString = jsonObjectMapper.writeValueAsString(objectOrig);
		assertThat(jsonString).isNotEmpty();

		// when
		final ProcurementEvent object = jsonObjectMapper.readValue(jsonString, ProcurementEvent.class);

		// then
		assertThat(object).isInstanceOf(valueType);
		assertThat(object).isEqualTo(objectOrig);
	}
}
