package de.metas.rest_api.invoicecandidates.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandidate;
import de.metas.util.rest.ExternalHeaderAndLineId;
import de.metas.util.rest.ExternalId;

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
	private static final ExternalId EXTERNAL_HEADER_ID1 = ExternalId.of("HEADER_1");
	private static final ExternalId EXTERNAL_LINE_ID1 = ExternalId.of("LINE_1");

	private static final ExternalId EXTERNAL_HEADER_ID2 = ExternalId.of("HEADER_2");
	private static final ExternalId EXTERNAL_LINE_ID2 = ExternalId.of("LINE_2");

	private InvoiceJsonConverterService jsonConverter;

	@BeforeEach
	void init()
	{
		jsonConverter = new InvoiceJsonConverterService();
	}

	@Test
	void checkInvoiceCandidateSelection()
	{
		final JsonInvoiceCandidate jic1 = JsonInvoiceCandidate.builder()
				.externalHeaderId(EXTERNAL_HEADER_ID1)
				.build();

		final JsonInvoiceCandidate jic2 = JsonInvoiceCandidate.builder()
				.externalHeaderId(EXTERNAL_HEADER_ID2)
				.externalLineId(EXTERNAL_LINE_ID1)
				.externalLineId(EXTERNAL_LINE_ID2)
				.build();

		// invoke the method under test
		final List<ExternalHeaderAndLineId> headerAndLineIds = jsonConverter.convertJICToExternalHeaderAndLineIds(ImmutableList.of(jic1, jic2));

		assertThat(headerAndLineIds).hasSize(2);
		assertThat(headerAndLineIds.get(0).getExternalHeaderId()).isEqualTo(EXTERNAL_HEADER_ID1);
		assertThat(headerAndLineIds.get(0).getExternalLineIds()).isEmpty();

		assertThat(headerAndLineIds.get(1).getExternalHeaderId()).isEqualTo(EXTERNAL_HEADER_ID2);
		assertThat(headerAndLineIds.get(1).getExternalLineIds()).containsExactly(EXTERNAL_LINE_ID1, EXTERNAL_LINE_ID2);
	}
}
