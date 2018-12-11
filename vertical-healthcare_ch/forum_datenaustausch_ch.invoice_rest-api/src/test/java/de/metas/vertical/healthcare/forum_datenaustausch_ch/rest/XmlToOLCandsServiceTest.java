package de.metas.vertical.healthcare.forum_datenaustausch_ch.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;

import javax.xml.bind.JAXBElement;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.ordercandidate.rest.JsonOLCandCreateBulkRequest;
import de.metas.ordercandidate.rest.JsonOLCandUtil;
import de.metas.ordercandidate.rest.OrderCandidatesRestEndpoint;
import de.metas.ordercandidate.rest.SyncAdvise;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RequestType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.JaxbUtil;
import mockit.Mocked;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_rest-api
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

public class XmlToOLCandsServiceTest
{
	@Mocked
	OrderCandidatesRestEndpoint orderCandidatesRestEndpoint;

	private ObjectMapper jsonObjectMapper;

	@Before
	public void init()
	{
		jsonObjectMapper = new ObjectMapper();
		jsonObjectMapper.findAndRegisterModules();
	}

	@Test
	public void createJsonOLCandCreateBulkRequest()
	{
		final InputStream inputStream = getClass().getResourceAsStream("/public_examples/md_440_tp_kvg_de.xml");
		final JAXBElement<RequestType> request = JaxbUtil.unmarshalToJaxbElement(inputStream, RequestType.class);
		final RequestType xmlInvoice = request.getValue();

		final SyncAdvise bPartnersSyncAdvise = SyncAdvise.createDefaultAdvise();
		final SyncAdvise productsSyncAdvise = SyncAdvise.createDefaultAdvise();

		final JsonOLCandCreateBulkRequest result = new XmlToOLCandsService(orderCandidatesRestEndpoint)
				.createJsonOLCandCreateBulkRequest(xmlInvoice, bPartnersSyncAdvise, productsSyncAdvise);

		assertThat(result).isNotNull();
		assertThat(result.getRequests()).hasSize(21); // the XML file has 21 services
		assertThat(result.getRequests()).allSatisfy(r -> assertThat(r.getProduct().getSyncAdvise()).isSameAs(productsSyncAdvise));
		assertThat(result.getRequests()).allSatisfy(r -> assertThat(r.getBpartner().getSyncAdvise()).isSameAs(bPartnersSyncAdvise));

		JsonOLCandUtil.printJsonString(result);
	}
}
