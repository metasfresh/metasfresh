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
import de.metas.common.procurement.sync.protocol.dto.SyncBPartner;
import de.metas.common.procurement.sync.protocol.dto.SyncConfirmation;
import de.metas.common.procurement.sync.protocol.dto.SyncContract;
import de.metas.common.procurement.sync.protocol.dto.SyncContractLine;
import de.metas.common.procurement.sync.protocol.dto.SyncProduct;
import de.metas.common.procurement.sync.protocol.dto.SyncProductSupply;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQ;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQCloseEvent;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQPriceChangeEvent;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQQtyChangeEvent;
import de.metas.common.procurement.sync.protocol.dto.SyncUser;
import de.metas.common.procurement.sync.protocol.dto.SyncWeeklySupply;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.GetAllBPartnersRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.GetAllProductsRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.GetInfoMessageRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutConfirmationToMetasfreshRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutProductSuppliesRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutWeeklySupplyRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutBPartnersRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutConfirmationToProcurementWebRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutInfoMessageRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutProductsRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQChangeRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQCloseEventsRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQsRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonSerializeDeserializeTests
{
	private final ObjectMapper jsonObjectMapper = Constants.PROCUREMENT_WEBUI_OBJECT_MAPPER;

	private <T> void assertRequestOK(
			final T objectOrig,
			final Class<T> valueType,
			final Class<?> requestSuperType) throws IOException
	{
		final String jsonString = jsonObjectMapper.writeValueAsString(objectOrig);
		assertThat(jsonString).isNotEmpty();

		// when
		final Object object = jsonObjectMapper.readValue(jsonString, requestSuperType);

		// then
		assertThat(object).isInstanceOf(valueType);
		assertThat(object).isEqualTo(objectOrig);
	}

	@Nested
	public class Procurement_to_Metasfresh
	{
		private <T> void assertRequestToMetasfreshOK(final T objectOrig, final Class<T> valueType) throws IOException
		{
			assertRequestOK(objectOrig, valueType, RequestToMetasfresh.class);
		}

		@Test
		void PutWeeklySupplyRequest() throws IOException
		{
			final PutWeeklySupplyRequest syncWeeklySupplyRequest = PutWeeklySupplyRequest.builder()
					.weeklySupply(SyncWeeklySupply.builder()
							.weekDay(LocalDate.parse("2021-01-05"))
							.trend("trend")
							.bpartner_uuid("bpartner_uuid")
							.product_uuid("product_uuid")
							.version(1)
							.syncConfirmationId(20L)
							.build())
					.build();

			assertRequestToMetasfreshOK(syncWeeklySupplyRequest, PutWeeklySupplyRequest.class);
		}

		@Test
		void PutProductSuppliesRequest() throws IOException
		{
			final PutProductSuppliesRequest syncProductSuppliesRequest = PutProductSuppliesRequest.of(SyncProductSupply.builder()
					.version(1)
					.qty(BigDecimal.TEN)
					.uuid("uuid")
					.syncConfirmationId(9L)
					.weekPlanning(true)
					.product_uuid("product_uuid")
					.bpartner_uuid("bpartner_uuid")
					.contractLine_uuid("contractLine_uuid")
					.build());

			assertRequestToMetasfreshOK(syncProductSuppliesRequest, PutProductSuppliesRequest.class);
		}

		@Test
		void PutRfQChangeRequest() throws IOException
		{
			final PutRfQChangeRequest syncRfQChangeRequest = PutRfQChangeRequest.builder()
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
							.day(LocalDate.now())
							.qty(BigDecimal.TEN)
							.product_uuid("product_uuid")
							.rfq_uuid("rfq_uuid")
							.uuid("uuid")
							.syncConfirmationId(9L)
							.build())
					.build();

			assertRequestToMetasfreshOK(syncRfQChangeRequest, PutRfQChangeRequest.class);
		}

		@Test
		void GetAllBPartnersRequest() throws IOException
		{
			assertRequestToMetasfreshOK(GetAllBPartnersRequest.INSTANCE, GetAllBPartnersRequest.class);
		}

		@Test
		void GetAllProductsRequest() throws IOException
		{
			assertRequestToMetasfreshOK(GetAllProductsRequest.INSTANCE, GetAllProductsRequest.class);
		}

		@Test
		void GetInfoMessageRequest() throws IOException
		{
			assertRequestToMetasfreshOK(GetInfoMessageRequest.INSTANCE, GetInfoMessageRequest.class);
		}

		@Test
		void PutConfirmationToMetasfreshRequest_1() throws IOException
		{
			assertRequestToMetasfreshOK(
					PutConfirmationToMetasfreshRequest.builder()
							.syncConfirmation(SyncConfirmation.forConfirmId(23L))
							.syncConfirmation(SyncConfirmation.forConfirmId(24L))
							.build(),
					PutConfirmationToMetasfreshRequest.class);
		}

		@Test
		void PutConfirmationToMetasfreshRequest_2() throws Exception
		{
			assertRequestToMetasfreshOK(
					PutConfirmationToMetasfreshRequest.builder()
							.syncConfirmation(SyncConfirmation.forConfirmId(23L))
							.syncConfirmation(SyncConfirmation.builder()
									.confirmId(24L)
									.dateConfirmed(new Date())
									.serverEventId("serverEventId")
									.build())
							.build(),
					PutConfirmationToMetasfreshRequest.class);
		}
	}


	@Nested
	public class Metasfresh_to_Procurement
	{
		private <T> void assertRequestToProcurementWebOK(final T objectOrig, final Class<T> valueType) throws IOException
		{
			assertRequestOK(objectOrig, valueType, RequestToProcurementWeb.class);
		}

		@Test
		void PutBPartnersRequest() throws IOException
		{
			final SyncBPartner syncBPartner = SyncBPartner.builder()
					.name("name")
					.uuid("uuid")
					.contract(SyncContract.builder()
							.dateFrom(LocalDate.now())
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
			assertRequestToProcurementWebOK(PutBPartnersRequest.of(syncBPartner), PutBPartnersRequest.class);
		}

		@Test
		void SyncProduct() throws IOException
		{
			final SyncProduct syncProduct = SyncProduct.builder()
					.packingInfo("packingInfo")
					.nameTrl("trlKey", "trl")
					.shared(true)
					.uuid("uuid")
					.build();

			assertRequestToProcurementWebOK(PutProductsRequest.of(syncProduct), PutProductsRequest.class);
		}

		@Test
		void PutInfoMessageRequest() throws IOException
		{
			final PutInfoMessageRequest syncInfoMessageRequest = PutInfoMessageRequest.of("String");

			assertRequestToProcurementWebOK(syncInfoMessageRequest, PutInfoMessageRequest.class);
		}

		@Test
		void PutConfirmationToProcurementWebRequest() throws IOException
		{
			final PutConfirmationToProcurementWebRequest syncConfirmationRequest = PutConfirmationToProcurementWebRequest.builder()
					.syncConfirmation(SyncConfirmation.forConfirmId(23L))
					.syncConfirmation(SyncConfirmation.forConfirmId(24L))
					.build();

			assertRequestToProcurementWebOK(syncConfirmationRequest, PutConfirmationToProcurementWebRequest.class);
		}

		@Test
		void PutRfQsRequest() throws IOException
		{
			final PutRfQsRequest syncRfQsRequest = PutRfQsRequest.builder().syncRfq(SyncRfQ.builder().build()).build();

			assertRequestToProcurementWebOK(syncRfQsRequest, PutRfQsRequest.class);
		}

		@Test
		void PutRfQCloseEventsRequest() throws IOException
		{
			final PutRfQCloseEventsRequest syncRfQCloseEventsRequest = PutRfQCloseEventsRequest.builder()
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

			assertRequestToProcurementWebOK(syncRfQCloseEventsRequest, PutRfQCloseEventsRequest.class);
		}
	}
}
