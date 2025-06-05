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

package de.metas.camel.externalsystems.pcm.warehouse;

import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.pcm.warehouse.model.WarehouseRow;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@Builder
@Value
public class WarehouseRouteContextProcessor implements Processor
{
	@NonNull String orgCode;
	@NonNull JsonMetasfreshId orgBPartnerId;
	@NonNull PInstanceLogger pInstanceLogger;

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final WarehouseRow warehouseRow = exchange.getIn().getBody(WarehouseRow.class);

		final GetWarehouseFromFileRouteContext context = GetWarehouseFromFileRouteContext.builder()
				.warehouseRow(warehouseRow)
				.orgCode(orgCode)
				.pInstanceLogger(pInstanceLogger)
				.orgBPartnerId(orgBPartnerId)
				.build();

		exchange.setProperty(ImportConstants.ROUTE_PROPERTY_GET_WAREHOUSE_CONTEXT, context);
	}
}
