package de.metas.rest_api.invoicecandidates.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.common.rest_api.JsonExternalId;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandidateReference;
import de.metas.rest_api.utils.JsonExternalIds;
import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;

/*
 * #%L
 * de.metas.business.rest-api-impl
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

public class InvoiceJsonConverterServiceTest
{
	private static final JsonExternalId EXTERNAL_HEADER_ID1 = JsonExternalId.of("HEADER_1");
	private static final JsonExternalId EXTERNAL_LINE_ID1 = JsonExternalId.of("LINE_1");

	private static final JsonExternalId EXTERNAL_HEADER_ID2 = JsonExternalId.of("HEADER_2");
	private static final JsonExternalId EXTERNAL_LINE_ID2 = JsonExternalId.of("LINE_2");

	@Test
	void checkInvoiceCandidateSelection()
	{
		final JsonInvoiceCandidateReference jic1 = JsonInvoiceCandidateReference.builder()
				.externalHeaderId(EXTERNAL_HEADER_ID1)
				.build();

		final JsonInvoiceCandidateReference jic2 = JsonInvoiceCandidateReference.builder()
				.externalHeaderId(EXTERNAL_HEADER_ID2)
				.externalLineId(EXTERNAL_LINE_ID1)
				.externalLineId(EXTERNAL_LINE_ID2)
				.build();

		// invoke the method under test
		final List<ExternalHeaderIdWithExternalLineIds> headerAndLineIds = InvoiceJsonConverters.fromJson(ImmutableList.of(jic1, jic2));

		assertThat(headerAndLineIds).hasSize(2);
		assertThat(headerAndLineIds.get(0).getExternalHeaderId()).isEqualTo(JsonExternalIds.toExternalId(EXTERNAL_HEADER_ID1));
		assertThat(headerAndLineIds.get(0).getExternalLineIds()).isEmpty();

		assertThat(headerAndLineIds.get(1).getExternalHeaderId()).isEqualTo(JsonExternalIds.toExternalId(EXTERNAL_HEADER_ID2));
		assertThat(headerAndLineIds.get(1).getExternalLineIds()).containsExactly(JsonExternalIds.toExternalId(EXTERNAL_LINE_ID1), JsonExternalIds.toExternalId(EXTERNAL_LINE_ID2));
	}
}
