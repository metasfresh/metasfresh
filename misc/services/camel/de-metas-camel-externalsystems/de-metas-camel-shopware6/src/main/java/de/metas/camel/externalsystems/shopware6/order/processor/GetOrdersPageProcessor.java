/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.order.processor;

import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient.GetOrdersResponse;
import de.metas.camel.externalsystems.shopware6.api.model.MultiQueryRequest;
import de.metas.camel.externalsystems.shopware6.order.ImportOrdersRouteContext;
import de.metas.camel.externalsystems.shopware6.order.query.OrderQueryHelper;
import de.metas.camel.externalsystems.shopware6.order.query.PageAndLimit;
import de.metas.common.externalsystem.ExternalSystemConstants;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;

public class GetOrdersPageProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final ImportOrdersRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, ImportOrdersRouteContext.class);

		final String salesRepJSONPath = routeContext.getExternalSystemRequest().getParameters().get(ExternalSystemConstants.PARAM_JSON_PATH_SALES_REP_ID);

		final PageAndLimit pageAndLimitValues = PageAndLimit.of(routeContext.getOrdersResponsePageIndex(), routeContext.getPageLimit());

		final MultiQueryRequest shopware6QueryRequest = OrderQueryHelper.buildShopware6QueryRequest(routeContext.getExternalSystemRequest(), pageAndLimitValues);

		final GetOrdersResponse ordersToProcess = routeContext.getShopwareClient().getOrders(shopware6QueryRequest, salesRepJSONPath);

		exchange.getIn().setBody(ordersToProcess.getOrderCandidates());

		routeContext.setMoreOrdersAvailable(ordersToProcess.getRawSize() >= pageAndLimitValues.getLimit());
		routeContext.incrementPageIndex();
	}
}
