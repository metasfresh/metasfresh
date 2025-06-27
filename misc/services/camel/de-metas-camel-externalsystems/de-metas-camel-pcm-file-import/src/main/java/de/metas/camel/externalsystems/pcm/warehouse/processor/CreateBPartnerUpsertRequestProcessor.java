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

package de.metas.camel.externalsystems.pcm.warehouse.processor;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.pcm.ExternalId;
import de.metas.camel.externalsystems.pcm.warehouse.GetWarehouseFromFileRouteContext;
import de.metas.camel.externalsystems.pcm.warehouse.ImportConstants;
import de.metas.camel.externalsystems.pcm.warehouse.model.WarehouseRow;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestComposite;
import de.metas.common.bpartner.v2.request.JsonRequestLocation;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsertItem;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.Check;
import de.metas.common.util.StringUtils;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Optional;

import static de.metas.camel.externalsystems.pcm.bpartner.ImportConstants.DEFAULT_COUNTRY_CODE;

public class CreateBPartnerUpsertRequestProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final GetWarehouseFromFileRouteContext routeContext = ProcessorHelper
				.getPropertyOrThrowError(exchange, ImportConstants.ROUTE_PROPERTY_GET_WAREHOUSE_CONTEXT, GetWarehouseFromFileRouteContext.class);

		final BPUpsertCamelRequest camelRequest = mapLocationToRequestItem(routeContext)
				.map(CreateBPartnerUpsertRequestProcessor::wrapUpsertItem)
				.map(upsertRequest -> BPUpsertCamelRequest.builder()
						.jsonRequestBPartnerUpsert(upsertRequest)
						.orgCode(routeContext.getOrgCode())
						.build())
				.orElse(null);

		exchange.getIn().setBody(camelRequest);
	}

	@NonNull
	private Optional<JsonRequestBPartnerUpsertItem> mapLocationToRequestItem(@NonNull final GetWarehouseFromFileRouteContext routeContext)
	{
		return mapLocationToJsonRequest(routeContext)
				.map(jsonRequestComposite -> JsonRequestBPartnerUpsertItem.builder()
						.bpartnerIdentifier(JsonMetasfreshId.toValueStr(routeContext.getOrgBPartnerId()))
						.bpartnerComposite(jsonRequestComposite)
						.build());
	}

	@NonNull
	private Optional<JsonRequestComposite> mapLocationToJsonRequest(@NonNull final GetWarehouseFromFileRouteContext routeContext)
	{
		final WarehouseRow warehouseRow = routeContext.getWarehouseRow();
		final PInstanceLogger pInstanceLogger = routeContext.getPInstanceLogger();

		if (hasMissingFields(warehouseRow))
		{
			pInstanceLogger.logMessage("Skipping row due to missing mandatory information, see row: " + warehouseRow);
			return Optional.empty();
		}

		return Optional.of(JsonRequestComposite.builder()
								   .locations(getBPartnerLocationRequest(warehouseRow))
								   .build());
	}

	@NonNull
	private static JsonRequestLocationUpsert getBPartnerLocationRequest(@NonNull final WarehouseRow warehouseRow)
	{
		final JsonRequestLocation location = new JsonRequestLocation();
		location.setName(StringUtils.trimBlankToNull(warehouseRow.getName()));
		location.setAddress1(StringUtils.trimBlankToNull(warehouseRow.getAddress1()));
		location.setPostal(StringUtils.trimBlankToNull(warehouseRow.getPostal()));
		location.setCity(StringUtils.trimBlankToNull(warehouseRow.getCity()));
		location.setGln(StringUtils.trimBlankToNull(warehouseRow.getGln()));
		location.setCountryCode(DEFAULT_COUNTRY_CODE);

		return JsonRequestLocationUpsert.builder()
				.requestItem(JsonRequestLocationUpsertItem.builder()
									 .locationIdentifier(ExternalId.ofId(warehouseRow.getWarehouseIdentifier()).toExternalIdentifierString())
									 .location(location)
									 .build())
				.build();
	}

	private static boolean hasMissingFields(@NonNull final WarehouseRow warehouseRow)
	{
		return Check.isBlank(warehouseRow.getWarehouseIdentifier())
				|| Check.isBlank(warehouseRow.getName())
				|| Check.isBlank(warehouseRow.getWarehouseValue());
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
