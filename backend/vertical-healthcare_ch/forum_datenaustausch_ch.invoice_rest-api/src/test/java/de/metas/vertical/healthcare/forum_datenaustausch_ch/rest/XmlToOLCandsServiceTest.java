package de.metas.vertical.healthcare.forum_datenaustausch_ch.rest;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.metas.rest_api.common.SyncAdvise;
import de.metas.rest_api.ordercandidates.OrderCandidatesRestEndpoint;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateBulkRequest;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateRequest;
import de.metas.util.JSONObjectMapper;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.HealthCareInvoiceDocSubType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RequestType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.JaxbUtil;
import lombok.NonNull;

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
	@Mock
	OrderCandidatesRestEndpoint orderCandidatesRestEndpoint;

	@InjectMocks
	XmlToOLCandsService xmlToOLCandsService;

	@Before
	public void before()
	{
		// note: if i add mockito-junit-jupiter to the dependencies in order to do "@ExtendWith(MockitoExtension.class)",
		// then eclipse can't find my test methods anymore
		MockitoAnnotations.initMocks(this);
	}

	@BeforeClass
	public static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG, o -> JSONObjectMapper.forClass(Object.class).writeValueAsString(o));
	}

	@AfterClass
	public static void afterAll()
	{
		validateSnapshots();
	}

	@Test
	public void createJsonOLCandCreateBulkRequest()
	{
		// assertThat(orderCandidatesRestEndpoint).isNotNull();

		final InputStream inputStream = getClass().getResourceAsStream("/public_examples/md_440_tp_kvg_de.xml");
		final RequestType xmlInvoice = JaxbUtil.unmarshalToJaxbElement(inputStream, RequestType.class).getValue();

		final JsonOLCandCreateBulkRequest result = performTest(xmlInvoice);
		expect(result).toMatchSnapshot();
	}

	@Test
	public void createJsonOLCandCreateBulkRequest2()
	{
		final InputStream inputStream = getClass().getResourceAsStream("/public_examples/md_440_tp_kvg_de.xml");
		final RequestType xmlInvoice = JaxbUtil.unmarshalToJaxbElement(inputStream, RequestType.class).getValue();
		xmlInvoice.getPayload().getInvoice().setRequestId("KV_" + "2009_01:001"); // the XML invoice'S ID might have a prepended "KV_" which we return

		final JsonOLCandCreateBulkRequest result = performTest(xmlInvoice);
		expect(result).toMatchSnapshot();
	}

	private JsonOLCandCreateBulkRequest performTest(@NonNull final RequestType xmlInvoice)
	{
		final SyncAdvise orgSyncAdvise = SyncAdvise.READ_ONLY;
		final SyncAdvise bPartnersSyncAdvise = SyncAdvise.READ_ONLY;
		final SyncAdvise productsSyncAdvise = SyncAdvise.READ_ONLY;

		// invoke the merhod under test
		final JsonOLCandCreateBulkRequest result = xmlToOLCandsService
				.createJsonOLCandCreateBulkRequest(xmlInvoice, HealthCareInvoiceDocSubType.KV, orgSyncAdvise, bPartnersSyncAdvise, productsSyncAdvise);

		assertThat(result).isNotNull();
		final List<JsonOLCandCreateRequest> requests = result.getRequests();

		assertThat(requests).hasSize(21); // the XML file has 21 services
		assertThat(requests).allSatisfy(r -> assertThat(r.getProduct().getSyncAdvise()).isSameAs(productsSyncAdvise));
		assertThat(requests).allSatisfy(r -> assertThat(r.getBpartner().getSyncAdvise()).isSameAs(bPartnersSyncAdvise));

		assertThat(requests).allSatisfy(r -> assertThat(r.getExternalHeaderId()).isEqualTo("2009_01:001_EAN-2011234567890_EAN-7634567890000"));
		assertThat(requests).allSatisfy(r -> assertThat(r.getPoReference()).isEqualTo("2009_01:001")); // this is the "invoice-ID as given by the examples file

		assertThat(requests).hasSize(21); // guards
		assertThat(requests).allSatisfy(r -> assertThat(r.getBpartner().getBpartner().getExternalId().getValue()).isEqualTo("EAN-7634567890000"));
		assertThat(requests).allSatisfy(r -> assertThat(r.getBpartner().getLocation().getGln()).isEqualTo("7634567890000"));

		final List<Object> xmlServices = xmlInvoice.getPayload().getBody().getServices().getRecordTarmedOrRecordDrgOrRecordLab();
		for (int i = 1; i <= xmlServices.size(); i++)
		{
			// the externalLineId is made up of the invoice reference_id, the biller's EAN, the recipient's EAN and the service's (line-)id
			final JsonOLCandCreateRequest request = requests.get(i - 1);
			assertThat(request.getExternalLineId()).isEqualTo("2009_01:001_EAN-2011234567890_EAN-7634567890000_" + i);
		}

		return result;
	}
}
