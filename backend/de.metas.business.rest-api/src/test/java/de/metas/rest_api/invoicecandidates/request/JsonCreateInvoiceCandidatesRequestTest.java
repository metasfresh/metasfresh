package de.metas.rest_api.invoicecandidates.request;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import de.metas.rest_api.common.JsonDocTypeInfo;
import de.metas.common.rest_api.JsonExternalId;
import de.metas.common.rest_api.JsonInvoiceRule;
import de.metas.rest_api.common.JsonPrice;
import de.metas.rest_api.common.JsonSOTrx;
import de.metas.util.JSONObjectMapper;

/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

class JsonCreateInvoiceCandidatesRequestTest
{

	@Test
	void serializeDeserialize()
	{
		final JsonCreateInvoiceCandidatesRequestItem item = JsonCreateInvoiceCandidatesRequestItem.builder()
				.billContactIdentifier("billContactIdentifier")
				.billLocationIdentifier("billLocationIdentifier")
				.billPartnerIdentifier("val-bpartner-123")
				.dateOrdered(LocalDate.of(2020, 03, 24))
				.discountOverride(TEN)
				.externalHeaderId(JsonExternalId.of("externalHeaderId"))
				.externalLineId(JsonExternalId.of("externalLineId"))
				.invoiceDetailItem(JSONInvoiceDetailItem.builder().description("description1").label("label1").seqNo(10).build())
				.invoiceDetailItem(JSONInvoiceDetailItem.builder().description("description2").label("label2").seqNo(10).build())
				.invoiceDocType(JsonDocTypeInfo.builder().docBaseType("docBaseType").docSubType("docSubType").build())
				.invoiceRuleOverride(JsonInvoiceRule.CustomerScheduleAfterDelivery)
				.lineDescription("lineDescription")
				.orgCode("orgCode")
				.poReference("poReference")
				.presetDateInvoiced(LocalDate.of(2020, 03, 25))
				.priceEnteredOverride(JsonPrice.builder().currencyCode("currencyCode").priceUomCode("priceUomCode").value(TEN).build())
				.productIdentifier("val-product-123")
				.qtyDelivered(TEN)
				.qtyOrdered(TEN)
				.uomCode("uomCode")
				.soTrx(JsonSOTrx.SALES)
				.build();
		final JsonCreateInvoiceCandidatesRequest request = JsonCreateInvoiceCandidatesRequest
				.builder()
				.item(item)
				.build();

		final JSONObjectMapper<JsonCreateInvoiceCandidatesRequest> jsonMapper = JSONObjectMapper.forClass(JsonCreateInvoiceCandidatesRequest.class);
		final String requestAsString = jsonMapper.writeValueAsString(request);

		final JsonCreateInvoiceCandidatesRequest deserializedRequest = jsonMapper.readValue(requestAsString);

		assertThat(deserializedRequest).isEqualTo(request);
	}
}
