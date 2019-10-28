package de.metas.rest_api.invoicecandidates.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandidate;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandCreateResponse;
import de.metas.util.Services;
import de.metas.util.rest.ExternalHeaderAndLineId;
import de.metas.util.rest.ExternalId;
import lombok.NonNull;

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
	private InvoiceJsonConverterService jsonConverter;
	private static final String EXTERNAL_LINE_ID1 = "Test1";
	private static final String EXTERNAL_HEADER_ID1 = "1001";

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		jsonConverter = new InvoiceJsonConverterService();
	}

	@Test
	public void convertJICToExternalHeaderAndLineIds()
	{
		List<JsonInvoiceCandidate> jsonInvoiceCandidates = new ArrayList<JsonInvoiceCandidate>();
		ExternalId externalId = ExternalId.of(EXTERNAL_LINE_ID1);
		List<ExternalId> externalLineIds = new ArrayList<ExternalId>();
		externalLineIds.add(externalId);
		JsonInvoiceCandidate jic = JsonInvoiceCandidate.builder().externalHeaderId(EXTERNAL_HEADER_ID1)
				.externalLineIds(externalLineIds).build();
		jsonInvoiceCandidates.add(jic);
		List<ExternalHeaderAndLineId> headerAndLineIds = jsonConverter.convertJICToExternalHeaderAndLineIds(jsonInvoiceCandidates);
		assertThat(headerAndLineIds.size()).isEqualTo(1);
	}
}
