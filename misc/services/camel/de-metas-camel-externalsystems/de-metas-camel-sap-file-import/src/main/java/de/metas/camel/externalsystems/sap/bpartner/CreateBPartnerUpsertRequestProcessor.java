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
import de.metas.camel.externalsystems.sap.model.bpartner.BPartnerRow;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import static de.metas.camel.externalsystems.sap.bpartner.GetBPartnersFromFileRouteBuilder.ROUTE_PROPERTY_UPSERT_BPARTNERS_ROUTE_CONTEXT;

public class CreateBPartnerUpsertRequestProcessor implements Processor
{
	@NonNull
	final JsonExternalSystemRequest externalSystemRequest;
	@NonNull
	final ProcessLogger processLogger;

	public CreateBPartnerUpsertRequestProcessor(
			final @NonNull JsonExternalSystemRequest externalSystemRequest,
			final @NonNull ProcessLogger processLogger)
	{
		this.externalSystemRequest = externalSystemRequest;
		this.processLogger = processLogger;
	}

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final BPartnerRow bPartnerRow = exchange.getIn().getBody(BPartnerRow.class);

		final UpsertBPartnerRouteContext getBPartnerRouteContext = exchange.getProperty(ROUTE_PROPERTY_UPSERT_BPARTNERS_ROUTE_CONTEXT, UpsertBPartnerRouteContext.class);

		if (getBPartnerRouteContext.getSyncBPartnerRequestBuilder() == null)
		{
			getBPartnerRouteContext.initUpsertBPartnerRequestBuilder(bPartnerRow);

			exchange.getIn().setBody(null);
			return;
		}

		final boolean added = getBPartnerRouteContext.getSyncBPartnerRequestBuilder().add(bPartnerRow);

		if (added)
		{
			exchange.getIn().setBody(null);
			return;
		}

		final BPUpsertCamelRequest bpUpsertCamelRequest = getBPartnerRouteContext.getSyncBPartnerRequestBuilder().build();
		exchange.getIn().setBody(bpUpsertCamelRequest);

		getBPartnerRouteContext.initUpsertBPartnerRequestBuilder(bPartnerRow);
	}
}
