/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_rest-api
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

package de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.xml_to_olcands;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import de.metas.common.bpartner.v1.request.JsonRequestBPartner;
import de.metas.common.bpartner.v1.request.JsonRequestLocation;
import de.metas.common.ordercandidates.v1.request.JsonOLCandCreateBulkRequest;
import de.metas.common.ordercandidates.v1.request.JsonOLCandCreateRequest;
import de.metas.common.ordercandidates.v1.request.JsonRequestBPartnerLocationAndContact;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.v1.SyncAdvise;
import de.metas.common.util.pair.ImmutablePair;
import de.metas.rest_api.v1.bpartner.BpartnerRestController;
import de.metas.rest_api.v1.ordercandidates.OrderCandidatesRestEndpoint;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.HealthCareInvoiceDocSubType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config.ImportConfigRepository;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RequestType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.JaxbUtil;
import lombok.NonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({SnapshotExtension.class, MockitoExtension.class})
public class XmlToOLCandsServiceTest
{
	private Expect expect;

	@Mock
	OrderCandidatesRestEndpoint orderCandidatesRestEndpoint; // needed by mockito

	@Mock
	BpartnerRestController bpartnerRestController; // needed by mockito

	@Mock
	ImportConfigRepository importConfigRepository; // needed by mockito

	@InjectMocks
	XmlToOLCandsService xmlToOLCandsService;

	@Test
	public void createJsonOLCandCreateBulkRequest_KV()
	{
		final InputStream inputStream = getClass().getResourceAsStream("/public_examples/md_440_tp_kvg_de.xml");
		final RequestType xmlInvoice = JaxbUtil.unmarshalToJaxbElement(inputStream, RequestType.class).getValue();

		final JsonOLCandCreateBulkRequest result = performTest_KV(xmlInvoice);
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	public void extractBPartnerExternalId_KV_2()
	{
		final InputStream inputStream = getClass().getResourceAsStream("/public_examples/md_440_tp_kvg_de.xml");
		final RequestType xmlInvoice = JaxbUtil.unmarshalToJaxbElement(inputStream, RequestType.class).getValue();

		final String invoiceRecipientEAN = xmlToOLCandsService.extractRecipientEAN(xmlInvoice);

		assertThat(invoiceRecipientEAN).isEqualTo("7634567890000");
	}

	@Test
	public void createBPartnerExternalId_KV_2()
	{
		final InputStream inputStream = getClass().getResourceAsStream("/public_examples/md_440_tp_kvg_de.xml");
		final RequestType xmlInvoice = JaxbUtil.unmarshalToJaxbElement(inputStream, RequestType.class).getValue();

		final JsonExternalId billerOrgCode = xmlToOLCandsService.createBPartnerExternalId(xmlToOLCandsService.getBiller(xmlInvoice.getPayload().getBody()));
	
		assertThat(billerOrgCode.getValue()).isEqualTo("EAN-2011234567890");
	}

	@Test
	public void createJsonOLCandCreateBulkRequest_KV_2()
	{
		final InputStream inputStream = getClass().getResourceAsStream("/public_examples/md_440_tp_kvg_de.xml");
		final RequestType xmlInvoice = JaxbUtil.unmarshalToJaxbElement(inputStream, RequestType.class).getValue();
		xmlInvoice.getPayload().getInvoice().setRequestId("KV_" + "2009_01:001"); // the XML invoice'S ID might have a prepended "KV_" which we return

		final JsonOLCandCreateBulkRequest result = performTest_KV(xmlInvoice);
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	private JsonOLCandCreateBulkRequest performTest_KV(@NonNull final RequestType xmlInvoice)
	{
		// given
		final SyncAdvise billerSyncAdvise = SyncAdvise.builder()
				.ifExists(SyncAdvise.IfExists.DONT_UPDATE)
				.ifNotExists(SyncAdvise.IfNotExists.CREATE)
				.build();

		final SyncAdvise debitorSyncAdvise = SyncAdvise.builder()
				.ifExists(SyncAdvise.IfExists.DONT_UPDATE)
				.ifNotExists(SyncAdvise.IfNotExists.FAIL)
				.build();

		final SyncAdvise productSyncAdvise = SyncAdvise.builder()
				.ifExists(SyncAdvise.IfExists.DONT_UPDATE)
				.ifNotExists(SyncAdvise.IfNotExists.CREATE)
				.build();

		// extractRecipientEAN has its own dedicated unittest
		final String invoiceRecipientEAN = xmlToOLCandsService.extractRecipientEAN(xmlInvoice);
		
		// createBPartnerExternalId has its own dedicated unittest
		final JsonExternalId billerOrgCode = xmlToOLCandsService.createBPartnerExternalId(xmlToOLCandsService.getBiller(xmlInvoice.getPayload().getBody()));
		
		final JsonRequestBPartner bpartner = new JsonRequestBPartner();
		bpartner.setExternalId(JsonExternalId.of(invoiceRecipientEAN));

		final JsonRequestLocation location = new JsonRequestLocation();
		location.setExternalId(JsonExternalId.of("abc"));

		final XmlToOLCandsService.HighLevelContext context = XmlToOLCandsService.HighLevelContext.builder()
				.targetDocType(HealthCareInvoiceDocSubType.KV)
				.billerSyncAdvise(billerSyncAdvise)
				.debitorSyncAdvise(debitorSyncAdvise)
				.productsSyncAdvise(productSyncAdvise)
				.invoiceRecipientEAN(invoiceRecipientEAN)
				.billerOrgCode(billerOrgCode)
				.patientWithPossibleBillToLocation(ImmutablePair.of(JsonRequestBPartnerLocationAndContact.builder()
						.bpartner(bpartner)
						.location(location)
						.build(), null))
				.build();

		// when
		final JsonOLCandCreateBulkRequest result = xmlToOLCandsService
				.createJsonOLCandCreateBulkRequest(xmlInvoice, context);

		// then
		assertThat(result).isNotNull();
		final List<JsonOLCandCreateRequest> requests = result.getRequests();

		assertThat(requests).hasSize(21); // the XML file has 21 services
		assertThat(requests).allSatisfy(r -> assertThat(r.getOrg().getSyncAdvise()).isEqualTo(billerSyncAdvise));
		assertThat(requests).allSatisfy(r -> assertThat(r.getOrg().getBpartner().getSyncAdvise()).isEqualTo(billerSyncAdvise));
		assertThat(requests).allSatisfy(r -> assertThat(r.getProduct().getSyncAdvise()).isEqualTo(productSyncAdvise));
		assertThat(requests).allSatisfy(r -> assertThat(r.getBpartner().getSyncAdvise()).isEqualTo(debitorSyncAdvise));

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
