/*
 * #%L
 * de-metas-camel-sap-file-import
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.sap.bpartner;

import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.sap.common.ExternalIdentifierFormat;
import de.metas.camel.externalsystems.sap.mapping.model.bpartner.BPartnerRow;
import de.metas.common.bpartner.v2.request.JsonRequestBPartner;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestComposite;
import de.metas.common.bpartner.v2.request.JsonRequestLocation;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsertItem;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Optional;

public class BPartnerUpsertProcessor implements Processor
{
	@NonNull
	final JsonExternalSystemRequest externalSystemRequest;
	@NonNull
	final ProcessLogger processLogger;

	public BPartnerUpsertProcessor(
			final @NonNull JsonExternalSystemRequest externalSystemRequest,
			final @NonNull ProcessLogger processLogger)
	{
		this.externalSystemRequest = externalSystemRequest;
		this.processLogger = processLogger;
	}

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final BPartnerRow bPartner = exchange.getIn().getBody(BPartnerRow.class);

		final JsonRequestBPartner jsonRequestBPartner = buildJsonRequestBPartner(bPartner);

		final JsonRequestLocationUpsert jsonRequestLocationUpsert = buildJsonRequestLocationUpsert(bPartner);

		final BPUpsertCamelRequest bpUpsertCamelRequest = buildBPUpsertCamelRequest(bPartner, jsonRequestBPartner, jsonRequestLocationUpsert);

		exchange.getIn().setBody(bpUpsertCamelRequest);
	}

	@NonNull
	private JsonRequestBPartner buildJsonRequestBPartner(@NonNull final BPartnerRow bPartner)
	{
		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setCode(bPartner.getPartnerCode());
		jsonRequestBPartner.setCompanyName(bPartner.getName1());
		jsonRequestBPartner.setName(bPartner.getName2());
		jsonRequestBPartner.setDescription(bPartner.getSearchTerm());
		jsonRequestBPartner.setSectionCode(bPartner.getSection());
		jsonRequestBPartner.setDeliveryRule(JsonRequestBPartner.DeliveryRule.AVAILABILITY);
		jsonRequestBPartner.setDeliveryViaRule(JsonRequestBPartner.DeliveryViaRule.SHIPPER);
		jsonRequestBPartner.setSyncAdvise(SyncAdvise.CREATE_OR_MERGE);
		jsonRequestBPartner.setIncotermsCustomer(bPartner.getSalesIncoterms());
		jsonRequestBPartner.setIncotermsVendor(bPartner.getPurchaseIncoterms());
		if (Check.isNotBlank(bPartner.getPartnerCategory()))
		{
			if (bPartner.getPartnerCategory().equals("4"))
			{
				jsonRequestBPartner.setVendor(true);
				jsonRequestBPartner.setCustomer(false);
				jsonRequestBPartner.setStorageWarehouse(true);
			}
			else
			{
				jsonRequestBPartner.setVendor(true);
			}
		}

		return jsonRequestBPartner;
	}

	@NonNull
	private JsonRequestLocationUpsert buildJsonRequestLocationUpsert(@NonNull final BPartnerRow bPartner)
	{
		final JsonRequestLocation jsonRequestLocation = new JsonRequestLocation();
		jsonRequestLocation.setCountryCode(bPartner.getCountryKey());
		jsonRequestLocation.setCity(bPartner.getCity());
		jsonRequestLocation.setAddress1(bPartner.getStreet());
		jsonRequestLocation.setAddress2(bPartner.getStreet2());
		jsonRequestLocation.setAddress3(bPartner.getStreet3());
		jsonRequestLocation.setAddress4(Optional.ofNullable(bPartner.getStreet5())
												.filter(Check::isNotBlank)
												.map(street5 -> bPartner.getStreet4() + "," + street5)
												.orElseGet(bPartner::getStreet4));
		jsonRequestLocation.setPostal(bPartner.getPostalCode());
		jsonRequestLocation.setHandoverLocation(true);

		final JsonRequestLocationUpsertItem jsonRequestLocationUpsertItem = JsonRequestLocationUpsertItem.builder()
				.location(jsonRequestLocation)
				.locationIdentifier(ExternalIdentifierFormat.formatExternalId(bPartner.getSection() + "_" + bPartner.getPartnerCode()))
				.build();

		return JsonRequestLocationUpsert.builder()
				.requestItem(jsonRequestLocationUpsertItem)
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.build();
	}

	@NonNull
	private BPUpsertCamelRequest buildBPUpsertCamelRequest(
			@NonNull final BPartnerRow bPartner,
			@NonNull final JsonRequestBPartner jsonRequestBPartner,
			@NonNull final JsonRequestLocationUpsert jsonRequestLocationUpsert
	)
	{
		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.bpartner(jsonRequestBPartner)
				.locations(jsonRequestLocationUpsert)
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.orgCode(externalSystemRequest.getOrgCode())
				.build();

		final JsonRequestBPartnerUpsertItem jsonRequestBPartnerUpsertItem = JsonRequestBPartnerUpsertItem.builder()
				.bpartnerComposite(jsonRequestComposite)
				.bpartnerIdentifier(ExternalIdentifierFormat.formatExternalId(bPartner.getSection() + "_" + bPartner.getPartnerCode()))
				.externalSystemConfigId(externalSystemRequest.getExternalSystemConfigId())
				.isReadOnlyInMetasfresh(true)
				.build();

		final JsonRequestBPartnerUpsert jsonRequestBPartnerUpsert = JsonRequestBPartnerUpsert.builder()
				.requestItem(jsonRequestBPartnerUpsertItem)
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.build();

		return BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(jsonRequestBPartnerUpsert)
				.orgCode(externalSystemRequest.getOrgCode())
				.build();
	}
}
