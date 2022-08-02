/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.from_grs.vendor;

import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.grssignum.from_grs.vendor.processor.PushBPartnersProcessor;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonVendor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.RouteBuilderHelper.setupJacksonDataFormatFor;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class PushBPartnerRouteBuilder extends RouteBuilder
{
	public static final String PUSH_BPARTNERS_ROUTE_ID = "GRSSignum-pushBPartners";

	public static final String PUSH_BPARTNERS_PROCESSOR_ID = "GRSSignum-PushBPartnersProcessorID";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(PUSH_BPARTNERS_ROUTE_ID))
				.routeId(PUSH_BPARTNERS_ROUTE_ID)
				.log("Route invoked!")
				.unmarshal(setupJacksonDataFormatFor(getContext(), JsonVendor.class))
				.process(new PushBPartnersProcessor()).id(PUSH_BPARTNERS_PROCESSOR_ID)
				.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}");
	}
}
