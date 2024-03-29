/*
 * #%L
 * de-metas-camel-pcm-file-import
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.camel.externalsystems.pcm.bpartner;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.pcm.ExternalId;
import de.metas.camel.externalsystems.pcm.bpartner.model.BPartnerRow;
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
import de.metas.common.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Optional;

import static de.metas.camel.externalsystems.pcm.bpartner.ImportConstants.DEFAULT_COUNTRY_CODE;

@Value
@Builder
public class BPartnerUpsertProcessor implements Processor
{
	@NonNull JsonExternalSystemRequest externalSystemRequest;
	@NonNull PInstanceLogger pInstanceLogger;

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final BPartnerRow bPartnerRow = exchange.getIn().getBody(BPartnerRow.class);

		final BPUpsertCamelRequest partnerUpsertCamelRequest = mapPartnerToRequestItem(bPartnerRow)
				.map(BPartnerUpsertProcessor::wrapUpsertItem)
				.map(upsertRequest -> BPUpsertCamelRequest.builder()
						.jsonRequestBPartnerUpsert(upsertRequest)
						.orgCode(externalSystemRequest.getOrgCode())
						.build())
				.orElse(null);

		exchange.getIn().setBody(partnerUpsertCamelRequest);
	}

	@NonNull
	private Optional<JsonRequestBPartnerUpsertItem> mapPartnerToRequestItem(@NonNull final BPartnerRow partnerRow)
	{
		return mapPartnerToJsonRequest(partnerRow)
				.map(jsonRequestComposite -> JsonRequestBPartnerUpsertItem.builder()
						.bpartnerIdentifier(ExternalId.ofId(partnerRow.getBpartnerIdentifier()).toExternalIdentifierString())
						.bpartnerComposite(jsonRequestComposite)
						.build());
	}

	@NonNull
	private Optional<JsonRequestComposite> mapPartnerToJsonRequest(@NonNull final BPartnerRow partnerRow)
	{
		if (hasMissingFields(partnerRow))
		{
			pInstanceLogger.logMessage("Skipping row due to missing mandatory information, see row: " + partnerRow);
			return Optional.empty();
		}

		final JsonRequestComposite composite = JsonRequestComposite.builder()
				.orgCode(externalSystemRequest.getOrgCode())
				.bpartner(getBPartnerRequest(partnerRow))
				.locations(getBPartnerLocationRequest(partnerRow))
				.build();
		return Optional.of(composite);
	}

	@NonNull
	private JsonRequestBPartner getBPartnerRequest(@NonNull final BPartnerRow partnerRow)
	{
		final JsonRequestBPartner bPartner = new JsonRequestBPartner();
		bPartner.setCode(partnerRow.getValue());
		bPartner.setName(partnerRow.getName());
		bPartner.setCompanyName(partnerRow.getName());
		return bPartner;
	}

	@NonNull
	private JsonRequestLocationUpsert getBPartnerLocationRequest(@NonNull final BPartnerRow partnerRow)
	{
		final JsonRequestLocation location = new JsonRequestLocation();
		location.setAddress1(StringUtils.trimBlankToNull(partnerRow.getAddress1()));
		location.setCity(StringUtils.trimBlankToNull(partnerRow.getCity()));
		location.setPostal(StringUtils.trimBlankToNull(partnerRow.getPostalCode()));
		location.setCountryCode(DEFAULT_COUNTRY_CODE);
		location.setBillToDefault(true);
		location.setShipToDefault(true);

		return JsonRequestLocationUpsert.builder()
				.requestItem(JsonRequestLocationUpsertItem.builder()
									 .locationIdentifier(ExternalId.ofId(partnerRow.getBpartnerIdentifier()).toExternalIdentifierString())
									 .location(location)
									 .build())
				.build();
	}

	private static boolean hasMissingFields(@NonNull final BPartnerRow row)
	{
		return Check.isBlank(row.getBpartnerIdentifier())
				|| Check.isBlank(row.getName())
				|| Check.isBlank(row.getValue());
	}

	@NonNull
	private static JsonRequestBPartnerUpsert wrapUpsertItem(@NonNull final JsonRequestBPartnerUpsertItem upsertItem)
	{
		return JsonRequestBPartnerUpsert.builder()
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.requestItems(ImmutableList.of(upsertItem))
				.build();
	}
}
