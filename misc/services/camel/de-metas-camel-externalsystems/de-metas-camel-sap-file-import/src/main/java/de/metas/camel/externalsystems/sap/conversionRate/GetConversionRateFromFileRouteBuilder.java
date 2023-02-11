/*
 * #%L
 * de-metas-camel-sap-file-import
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.camel.externalsystems.sap.conversionRate;

import de.metas.camel.externalsystems.common.IdAwareRouteBuilder;
import de.metas.camel.externalsystems.common.PInstanceUtil;
import de.metas.camel.externalsystems.sap.config.ConversionRateFileEndpointConfig;
import de.metas.camel.externalsystems.sap.model.conversionRate.ConversionRateRow;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.v2.conversionRate.JsonCurrencyRateCreateRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_CREATE_CONVERSION_RATE_CAMEL_URI;
import static de.metas.camel.externalsystems.sap.SAPConstants.ROUTE_PROPERTY_CONVERSION_RATE_ROUTE_CONTEXT;

public class GetConversionRateFromFileRouteBuilder extends IdAwareRouteBuilder
{
	@NonNull
	private final ConversionRateFileEndpointConfig fileEndpointConfig;

	@NonNull
	private final JsonExternalSystemRequest enabledByExternalSystemRequest;

	@Getter
	@NonNull
	private final String routeId;

	@Builder
	private GetConversionRateFromFileRouteBuilder(
			@NonNull final CamelContext camelContext,
			@NonNull final ConversionRateFileEndpointConfig fileEndpointConfig,
			@NonNull final JsonExternalSystemRequest enabledByExternalSystemRequest,
			@NonNull final String routeId)
	{
		super(camelContext);
		this.fileEndpointConfig = fileEndpointConfig;
		this.enabledByExternalSystemRequest = enabledByExternalSystemRequest;
		this.routeId = routeId;
	}

	@Override
	public void configure() throws Exception
	{
		//@formatter:off
		from(fileEndpointConfig.getConversionRateFileEndpoint())
				.routeId(routeId)
				.log("ConversionRate Sync Route Started")
				.process(exchange -> PInstanceUtil.setPInstanceHeader(exchange, enabledByExternalSystemRequest))
				.process(this::attachContext)
				.split(body().tokenize("\n")).streaming()
					.unmarshal(new BindyCsvDataFormat(ConversionRateRow.class))
					.process(new ConversionRateUpsertProcessor())
					.choice()
						.when(bodyAs(JsonCurrencyRateCreateRequest.class).isNull())
							.log("Nothing to do! No Conversion rate to upsert!")
						.otherwise()
							.to("{{" + MF_CREATE_CONVERSION_RATE_CAMEL_URI + "}}")
						.end()
					.end()
				.end()
				.process(ConversionRateUpsertProcessor::processLastConversionRateGroup)
				.choice()
					.when(bodyAs(JsonCurrencyRateCreateRequest.class).isNull())
						.log(LoggingLevel.DEBUG, "Nothing to do! No last conversion rate group present!")
					.otherwise()
						.log(LoggingLevel.DEBUG, "Last conversion rate group present and ready to upsert!")
						.to("{{" + MF_CREATE_CONVERSION_RATE_CAMEL_URI + "}}")
					.endChoice()
				.end();
		// @formatter:on
	}

	private void attachContext(@NonNull final Exchange exchange)
	{
		exchange.setProperty(ROUTE_PROPERTY_CONVERSION_RATE_ROUTE_CONTEXT, ConversionRateContext.of(enabledByExternalSystemRequest.getOrgCode()));
	}
}
