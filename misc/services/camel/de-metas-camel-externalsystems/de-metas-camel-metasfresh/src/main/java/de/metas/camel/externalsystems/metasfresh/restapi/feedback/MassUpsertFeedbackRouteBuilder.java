/*
 * #%L
 * de-metas-camel-metasfresh
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

package de.metas.camel.externalsystems.metasfresh.restapi.feedback;

import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.metasfresh.restapi.model.JsonMassUpsertFeedbackRequest;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.FEEDBACK_RESOURCE_URL_HEADER;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class MassUpsertFeedbackRouteBuilder extends RouteBuilder
{
	public static final String MASS_UPSERT_FEEDBACK_ROUTE_ID = "metasfresh-massUpsertFeedbackRouteId";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));
		
		//@formatter:off
		from(direct(MASS_UPSERT_FEEDBACK_ROUTE_ID))
			.process(new PrepareFeedbackRequest())
			.choice()
				.when(bodyAs(JsonMassUpsertFeedbackRequest.class).isNotNull())
					.marshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonMassUpsertFeedbackRequest.class))
					.removeHeaders("CamelHttp*")
					.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
					.toD("${header." + FEEDBACK_RESOURCE_URL_HEADER + "}")
				.otherwise()
					.log(LoggingLevel.DEBUG, "Skipped sending feedback due to missing FeedbackConfig! Feedback=${body}");
		//@formatter:on
	}
}
