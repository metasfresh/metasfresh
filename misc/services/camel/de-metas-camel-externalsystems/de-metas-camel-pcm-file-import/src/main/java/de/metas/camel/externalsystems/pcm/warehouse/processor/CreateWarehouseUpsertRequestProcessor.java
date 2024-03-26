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
import de.metas.camel.externalsystems.common.v2.WarehouseUpsertCamelRequest;
import de.metas.camel.externalsystems.pcm.ExternalId;
import de.metas.camel.externalsystems.pcm.warehouse.GetWarehouseFromFileRouteContext;
import de.metas.camel.externalsystems.pcm.warehouse.ImportConstants;
import de.metas.camel.externalsystems.pcm.warehouse.model.WarehouseRow;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.warehouse.JsonRequestWarehouse;
import de.metas.common.rest_api.v2.warehouse.JsonRequestWarehouseUpsert;
import de.metas.common.rest_api.v2.warehouse.JsonRequestWarehouseUpsertItem;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Optional;

public class CreateWarehouseUpsertRequestProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final GetWarehouseFromFileRouteContext routeContext = ProcessorHelper
				.getPropertyOrThrowError(exchange, ImportConstants.ROUTE_PROPERTY_GET_WAREHOUSE_CONTEXT, GetWarehouseFromFileRouteContext.class);

		final WarehouseUpsertCamelRequest camelRequest = mapWarehouseToRequestItem(routeContext)
				.map(CreateWarehouseUpsertRequestProcessor::wrapUpsertItem)
				.map(upsertRequest -> WarehouseUpsertCamelRequest.builder()
						.orgCode(routeContext.getOrgCode())
						.jsonRequestWarehouseUpsert(upsertRequest)
						.build())
				.orElse(null);

		exchange.getIn().setBody(camelRequest);
	}

	@NonNull
	private Optional<JsonRequestWarehouseUpsertItem> mapWarehouseToRequestItem(@NonNull final GetWarehouseFromFileRouteContext routeContext)
	{
		final WarehouseRow warehouseRow = routeContext.getWarehouseRow();
		final PInstanceLogger pInstanceLogger = routeContext.getPInstanceLogger();
		if (hasMissingFields(warehouseRow))
		{
			pInstanceLogger.logMessage("Skipping row due to missing mandatory information, see row: " + warehouseRow);
			return Optional.empty();
		}

		final JsonRequestWarehouseUpsertItem requestWarehouseUpsertItem = JsonRequestWarehouseUpsertItem.builder()
				.warehouseIdentifier(ExternalId.ofId(warehouseRow.getWarehouseIdentifier()).toExternalIdentifierString())
				.requestWarehouse(getJsonRequestWarehouse(warehouseRow))
				.build();

		return Optional.of(requestWarehouseUpsertItem);
	}

	@NonNull
	private static JsonRequestWarehouse getJsonRequestWarehouse(@NonNull final WarehouseRow warehouseRow)
	{
		final JsonRequestWarehouse requestWarehouse = new JsonRequestWarehouse();
		requestWarehouse.setName(warehouseRow.getName());
		requestWarehouse.setCode(warehouseRow.getWarehouseValue());
		requestWarehouse.setBpartnerLocationIdentifier(ExternalId.ofId(warehouseRow.getWarehouseIdentifier()).toExternalIdentifierString());
		requestWarehouse.setActive(true);

		return requestWarehouse;
	}

	private static boolean hasMissingFields(@NonNull final WarehouseRow warehouseRow)
	{
		return Check.isBlank(warehouseRow.getWarehouseIdentifier())
				|| Check.isBlank(warehouseRow.getName())
				|| Check.isBlank(warehouseRow.getWarehouseValue());
	}

	@NonNull
	private static JsonRequestWarehouseUpsert wrapUpsertItem(@NonNull final JsonRequestWarehouseUpsertItem upsertItem)
	{
		return JsonRequestWarehouseUpsert.builder()
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.requestItems(ImmutableList.of(upsertItem))
				.build();
	}
}
