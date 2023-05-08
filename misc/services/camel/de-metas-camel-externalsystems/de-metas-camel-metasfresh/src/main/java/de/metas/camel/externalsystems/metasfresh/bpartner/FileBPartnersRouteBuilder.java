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
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.metasfresh.bpartner.processor.ParseBPartnersProcessor;
import de.metas.camel.externalsystems.metasfresh.bpartner.processor.UnwrapJsonUpsertBPartnerRequestItem;
import de.metas.camel.externalsystems.metasfresh.restapi.feedback.MassUpsertStatisticsCollector;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsert;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.IS_CONTINUE_PARSING_PROPERTY;
import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.MASS_UPLOAD_STATISTICS_COLLECTOR_PROPERTY;
import static de.metas.camel.externalsystems.metasfresh.restapi.feedback.MassUpsertFeedbackRouteBuilder.MASS_UPSERT_FEEDBACK_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class FileBPartnersRouteBuilder extends RouteBuilder
{
	public static final String PROCESS_BPARTNERS_FROM_FILE_ROUTE_ID = "metasfresh-processBPartnerFromFile";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));
		
		CamelRouteUtil.setupProperties(getContext());

		//@formatter:off
		from(direct(PROCESS_BPARTNERS_FROM_FILE_ROUTE_ID))
				.routeId(PROCESS_BPARTNERS_FROM_FILE_ROUTE_ID)
				.process(new UnwrapJsonUpsertBPartnerRequestItem())
				.process(exchange -> exchange.setProperty(IS_CONTINUE_PARSING_PROPERTY, true))
					
				.loopDoWhile(exchangeProperty(IS_CONTINUE_PARSING_PROPERTY))
					.doTry()
						.process(new ParseBPartnersProcessor())
						.choice()
							.when(bodyAs(BPUpsertCamelRequest.class).isNull())
								.log(LoggingLevel.INFO, "Nothing to do! No BPartners pulled from file!")
							.otherwise()
								.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
								.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonResponseBPartnerCompositeUpsert.class))
				        		.process(this::collectStatistics)
						.endChoice()
						.end()
					.endDoTry()
					.doCatch(Exception.class)
						.process(this::collectError)
					.end()
				.end()
				.to(direct(MASS_UPSERT_FEEDBACK_ROUTE_ID));
		//@formatter:on
	}

	private void collectError(@NonNull final Exchange exchange)
	{
		final MassUpsertStatisticsCollector massUpsertStatisticsCollector = exchange.getProperty(MASS_UPLOAD_STATISTICS_COLLECTOR_PROPERTY,
																								 MassUpsertStatisticsCollector.class);
		final Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

		if (exception instanceof HttpOperationFailedException)
		{
			final String response = ((HttpOperationFailedException)exception).getResponseBody();

			if (Check.isNotBlank(response))
			{
				massUpsertStatisticsCollector.collectError(response);
			}
			return;
		}

		massUpsertStatisticsCollector.collectError(exception.getMessage());
	}

	private void collectStatistics(@NonNull final Exchange exchange)
	{
		final JsonResponseBPartnerCompositeUpsert response = exchange.getIn().getBody(JsonResponseBPartnerCompositeUpsert.class);

		final MassUpsertStatisticsCollector massUpsertStatisticsCollector = exchange.getProperty(MASS_UPLOAD_STATISTICS_COLLECTOR_PROPERTY, MassUpsertStatisticsCollector.class);

		massUpsertStatisticsCollector.increaseProcessedItemsCount(response.getResponseItems().size());
	}
}
