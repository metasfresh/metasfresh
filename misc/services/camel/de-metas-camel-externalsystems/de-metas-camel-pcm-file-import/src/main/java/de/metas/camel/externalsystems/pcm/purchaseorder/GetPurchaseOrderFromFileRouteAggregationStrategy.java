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

package de.metas.camel.externalsystems.pcm.purchaseorder;

import de.metas.camel.externalsystems.common.ProcessorHelper;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import static de.metas.camel.externalsystems.pcm.purchaseorder.ImportConstants.PROPERTY_IMPORT_ORDERS_CONTEXT;

/**
 * Makes sure that after having split the CSV files and after having created the individual C_OLCands, we are able to process all cands that have no problem.
 */
public class GetPurchaseOrderFromFileRouteAggregationStrategy implements AggregationStrategy
{
	@Override
	public Exchange aggregate(final Exchange oldExchange, final Exchange newExchange)
	{
		if (oldExchange == null) {
			// the first time we aggregate we only have the new exchange,
			// so we just return it
			return newExchange;
		}
		
		final ImportOrdersRouteContext newImportOrdersRouteContext =
				ProcessorHelper.getPropertyOrThrowError(newExchange, PROPERTY_IMPORT_ORDERS_CONTEXT, ImportOrdersRouteContext.class);

		final ImportOrdersRouteContext oldImportOrdersRouteContext = ImportUtil.getOrCreateImportOrdersRouteContext(oldExchange);
		oldImportOrdersRouteContext.addAll(newImportOrdersRouteContext);
		
		return oldExchange;
	}
}
