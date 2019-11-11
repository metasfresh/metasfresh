package de.metas.rest_api.invoicecandidates.request;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.JsonSOTrx;
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
	void test()
	{
		final JsonCreateInvoiceCandidatesRequestItem minimalItem = JsonCreateInvoiceCandidatesRequestItem.builder()
				.billPartnerIdentifier("val-bpartner-123")
				.externalHeaderId(JsonExternalId.of("externalHeaderId"))
				.externalLineId(JsonExternalId.of("externalLineId"))
				.orgCode("orgCode")
				.productIdentifier("val-product-123")
				.qtyOrdered(TEN)
				.soTrx(JsonSOTrx.SALES)
				.build();
		final JsonCreateInvoiceCandidatesRequest request = JsonCreateInvoiceCandidatesRequest
				.builder()
				.item(minimalItem)
				.build();

		final JSONObjectMapper<JsonCreateInvoiceCandidatesRequest> jsonMapper = JSONObjectMapper.forClass(JsonCreateInvoiceCandidatesRequest.class);
		final String requestAsString = jsonMapper.writeValueAsString(request);

		final JsonCreateInvoiceCandidatesRequest deserializedRequest = jsonMapper.readValue(requestAsString);

		assertThat(deserializedRequest).isEqualTo(request);
	}

}
