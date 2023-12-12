/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.invoicecandidates.response;

import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.util.JSONObjectMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class JsonCheckInvoiceCandidatesStatusResponseTest
{
	@Test
	void serializeDeserialize()
	{
		//given
		final JsonCheckInvoiceCandidatesStatusResponseItem responseItem = JsonCheckInvoiceCandidatesStatusResponseItem.builder()
				.externalHeaderId(JsonExternalId.of("111"))
				.externalLineId(JsonExternalId.of("222"))
				.metasfreshId(MetasfreshId.of(10009))
				.qtyEntered(BigDecimal.valueOf(5.0))
				.qtyInvoiced(BigDecimal.ZERO)
				.qtyToInvoice(BigDecimal.valueOf(5.0))
				.dateToInvoice(LocalDate.of(2021, 12, 21))
				.processed(true)
				.build();

		final JsonCheckInvoiceCandidatesStatusResponse response = JsonCheckInvoiceCandidatesStatusResponse.builder()
				.invoiceCandidates(ImmutableList.of(responseItem))
				.build();

		//when
		final JSONObjectMapper<JsonCheckInvoiceCandidatesStatusResponse> jsonMapper = JSONObjectMapper.forClass(JsonCheckInvoiceCandidatesStatusResponse.class);

		final String responseAsString = jsonMapper.writeValueAsString(response);

		final JsonCheckInvoiceCandidatesStatusResponse deserializedResponse = jsonMapper.readValue(responseAsString);

		//then
		assertThat(deserializedResponse).isEqualTo(response);
	}
}
