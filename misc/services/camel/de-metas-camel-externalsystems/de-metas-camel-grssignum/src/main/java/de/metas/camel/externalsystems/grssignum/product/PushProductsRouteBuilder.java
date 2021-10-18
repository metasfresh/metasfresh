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

package de.metas.camel.externalsystems.grssignum.product;

import de.metas.camel.externalsystems.grssignum.api.model.JsonProduct;
import de.metas.camel.externalsystems.grssignum.product.processor.PushProductsProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_PRODUCT_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.RouteBuilderHelper.setupJacksonDataFormatFor;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class PushProductsRouteBuilder extends RouteBuilder
{
	public static final String PUSH_PRODUCTS_ROUTE_ID = "GRSSignum-pushProducts";

	public static final String PUSH_PRODUCTS_PROCESSOR_ID = "GRSSignum-pushProductsProcessorID";

	@Override
	public void configure() throws Exception
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(PUSH_PRODUCTS_ROUTE_ID))
				.routeId(PUSH_PRODUCTS_ROUTE_ID)
				.log("Route invoked!")
				.unmarshal(setupJacksonDataFormatFor(getContext(), JsonProduct.class))
				.process(new PushProductsProcessor()).id(PUSH_PRODUCTS_PROCESSOR_ID)
				.to(direct(MF_UPSERT_PRODUCT_V2_CAMEL_URI));
	}
}
