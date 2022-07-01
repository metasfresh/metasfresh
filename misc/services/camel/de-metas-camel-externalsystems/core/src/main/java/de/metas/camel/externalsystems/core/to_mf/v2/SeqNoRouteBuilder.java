/*
 * #%L
 * de-metas-camel-externalsystems-core
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

package de.metas.camel.externalsystems.core.to_mf.v2;

import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.common.rest_api.v2.seqno.JsonSeqNoResponse;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_SEQ_NO_V2_URI;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class SeqNoRouteBuilder extends RouteBuilder
{
	private static final int AUDIT_AD_SEQUENCE_ID = 555613;

	@Override
	public void configure() throws Exception
	{
		errorHandler(noErrorHandler());

		from(direct(ExternalSystemCamelConstants.MF_SEQ_NO_ROUTE_ID))
				.routeId(ExternalSystemCamelConstants.MF_SEQ_NO_ROUTE_ID)
				.streamCaching()
				.log("Route invoked!")
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.GET))
				.toD("{{" + MF_SEQ_NO_V2_URI + "}}" + "/" + AUDIT_AD_SEQUENCE_ID + "/next")
				.to(direct(UNPACK_V2_API_RESPONSE))
				.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonSeqNoResponse.class));
	}
}
