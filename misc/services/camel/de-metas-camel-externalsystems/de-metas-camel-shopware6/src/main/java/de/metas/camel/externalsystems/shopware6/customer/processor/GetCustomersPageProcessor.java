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

package de.metas.camel.externalsystems.shopware6.customer.processor;

import de.metas.camel.externalsystems.shopware6.api.model.MultiQueryRequest;
import de.metas.camel.externalsystems.shopware6.api.model.QueryHelper;
import de.metas.camel.externalsystems.shopware6.api.model.order.Customer;
import de.metas.camel.externalsystems.shopware6.customer.ImportCustomersRouteContext;
import de.metas.camel.externalsystems.shopware6.order.query.PageAndLimit;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;

import static de.metas.camel.externalsystems.common.ProcessorHelper.getPropertyOrThrowError;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_CUSTOMERS_CONTEXT;

public class GetCustomersPageProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final ImportCustomersRouteContext routeContext = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_CUSTOMERS_CONTEXT, ImportCustomersRouteContext.class);

		final PageAndLimit pageAndLimitValues = PageAndLimit.of(routeContext.getResponsePageIndex(), routeContext.getPageLimit());
		final MultiQueryRequest getCustomerQueryRequest = QueryHelper.buildShopware6GetCustomersQueryRequest(routeContext.getUpdatedAfter(),pageAndLimitValues);

		final List<Customer> customerCandidateList = routeContext.getShopwareClient().getCustomerCandidates(getCustomerQueryRequest);

		exchange.getIn().setBody(customerCandidateList);

		routeContext.setMoreCustomersAvailable(customerCandidateList.size() >= pageAndLimitValues.getLimit());
		routeContext.incrementPageIndex();
	}
}
