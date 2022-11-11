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

package de.metas.camel.externalsystems.metasfresh.bpartner;

import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.metasfresh.bpartner.processor.CollectResultsProcessor;
import de.metas.camel.externalsystems.metasfresh.bpartner.processor.ParseBPartnersProcessor;
import de.metas.camel.externalsystems.metasfresh.bpartner.processor.UnwrapJsonUpsertBPartnerRequestItem;
import de.metas.camel.externalsystems.metasfresh.bpartner.processor.PrepareFeedbackRequest;
import de.metas.camel.externalsystems.metasfresh.restapi.model.JsonMassUpsertFeedbackRequest;
import de.metas.camel.externalsystems.metasfresh.restapi.processor.CloseParserProcessor;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsert;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.IS_CONTINUE_PARSING_PROPERTY;
import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.RESPONSE_URL_HEADER;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class FileBPartnersRouteBuilder extends RouteBuilder
{
	public static final String PROCESS_BPARTNERS_FROM_FILE_ROUTE_ID = "Metasfresh-processBPartnerFromFile";

	public static final String ROUTE_PROPERTY_UPSERT_BPARTNERS_CONTEXT = "UpsertBPartnersRouteContext";

	@Override
	public void configure()
	{
		//@formatter:off
		from(direct(PROCESS_BPARTNERS_FROM_FILE_ROUTE_ID))
				.routeId(PROCESS_BPARTNERS_FROM_FILE_ROUTE_ID)
				.doTry()
					.process(new UnwrapJsonUpsertBPartnerRequestItem())
					.process(exchange -> exchange.setProperty(IS_CONTINUE_PARSING_PROPERTY, true))
					
					.loopDoWhile(exchangeProperty(IS_CONTINUE_PARSING_PROPERTY))
				        .process(new ParseBPartnersProcessor())
						.doTry()
							.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
							.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonResponseBPartnerCompositeUpsert.class))
							.process(new CollectResultsProcessor())
						.endDoTry()
						.doCatch(Exception.class)
							.process((exchange) -> {
								final UpsertBPartnersRouteContext upsertBPartnersRouteContext = exchange.getProperty(ROUTE_PROPERTY_UPSERT_BPARTNERS_CONTEXT, UpsertBPartnersRouteContext.class);
								final Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
								upsertBPartnersRouteContext.collectError(cause.getMessage());
							})
						.end()
					.end()
					.choice()
						.when(this::isFeedbackConfigPresent)
							.process(new PrepareFeedbackRequest())
							.marshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonMassUpsertFeedbackRequest.class))
							.removeHeaders("CamelHttp*")
							.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
							.toD("${header." + RESPONSE_URL_HEADER + "}")
						.otherwise()
							// dev-note: do nothing
				.endDoTry()
				.doFinally()
					.process(new CloseParserProcessor())
				.end();
		//@formatter:on
	}

	private boolean isFeedbackConfigPresent(@NonNull final Exchange exchange)
	{
		final UpsertBPartnersRouteContext upsertBPartnersRouteContext = exchange.getProperty(ROUTE_PROPERTY_UPSERT_BPARTNERS_CONTEXT, UpsertBPartnersRouteContext.class);

		return upsertBPartnersRouteContext.getFeedbackConfig() != null;
	}
}
