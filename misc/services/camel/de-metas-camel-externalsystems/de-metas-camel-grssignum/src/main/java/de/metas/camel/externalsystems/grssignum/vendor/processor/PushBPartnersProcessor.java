/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.vendor.processor;

import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.grssignum.ExternalIdentifierFormat;
import de.metas.camel.externalsystems.grssignum.api.model.JsonBPartner;
import de.metas.common.bpartner.v2.request.JsonRequestBPartner;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestComposite;
import de.metas.common.rest_api.v2.SyncAdvise;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.security.core.context.SecurityContextHolder;

public class PushBPartnersProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final JsonBPartner jsonBPartner = exchange.getIn().getBody(JsonBPartner.class);

		final BPUpsertCamelRequest bpUpsertCamelRequest = getBPUpsertCamelRequest(jsonBPartner);

		exchange.getIn().setBody(bpUpsertCamelRequest, JsonRequestBPartnerUpsert.class);
	}

	@NonNull
	private BPUpsertCamelRequest getBPUpsertCamelRequest(@NonNull final JsonBPartner jsonBPartner)
	{
		final TokenCredentials credentials = (TokenCredentials)SecurityContextHolder.getContext().getAuthentication().getCredentials();

		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		jsonRequestBPartner.setName(jsonBPartner.getName());
		jsonRequestBPartner.setCompanyName(jsonBPartner.getName());
		jsonRequestBPartner.setActive(jsonBPartner.isActive());
		jsonRequestBPartner.setVendor(true);

		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.orgCode(credentials.getOrgCode())
				.bpartner(jsonRequestBPartner)
				.build();

		final JsonRequestBPartnerUpsertItem jsonRequestBPartnerUpsertItem = JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier(ExternalIdentifierFormat.asExternalIdentifier(jsonBPartner.getId()))
				.bpartnerComposite(jsonRequestComposite)
				.build();

		final JsonRequestBPartnerUpsert jsonRequestBPartnerUpsert = JsonRequestBPartnerUpsert.builder()
				.requestItem(jsonRequestBPartnerUpsertItem)
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.build();

		return BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(jsonRequestBPartnerUpsert)
				.orgCode(credentials.getOrgCode())
				.build();
	}
}
