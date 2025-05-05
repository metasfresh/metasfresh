/*
 * #%L
 * de-metas-camel-sap-file-import
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

package de.metas.camel.externalsystems.sap.creditlimit;

import com.google.common.collect.ImmutableSet;
import de.metas.camel.externalsystems.common.IdAwareRouteBuilder;
import de.metas.camel.externalsystems.common.PInstanceUtil;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.sap.config.CreditLimitFileEndpointConfig;
import de.metas.camel.externalsystems.sap.creditlimit.info.UpsertCreditLimitRequest;
import de.metas.camel.externalsystems.sap.model.creditlimit.CreditLimitRow;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;

import static de.metas.camel.externalsystems.sap.SAPConstants.ROUTE_PROPERTY_CREDIT_LIMIT_ROUTE_CONTEXT;
import static de.metas.camel.externalsystems.sap.creditlimit.UpsertCreditLimitRouteBuilder.UPSERT_CREDIT_LIMIT_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

public class GetCreditLimitFromFileRouteBuilder extends IdAwareRouteBuilder
{
	private static final String PROCESS_CREDIT_LIMIT_ROW_PROCESSOR_ID = "SAP-CreditLimit-processCreditLimitRowProcessorId";

	private static final String PROCESS_LAST_CREDIT_LIMIT_GROUP_PROCESSOR_ID = "SAP-CreditLimit-processLastGroupProcessorId";

	private static final ImmutableSet<String> ACCEPTED_CREDIT_TYPES = ImmutableSet.of("S001");

	@NonNull
	private final CreditLimitFileEndpointConfig fileEndpointConfig;

	@Getter
	@NonNull
	private final String routeId;

	@NonNull
	private final JsonExternalSystemRequest enabledByExternalSystemRequest;

	@NonNull
	private final ProcessLogger processLogger;

	@NonNull
	private final CreditLimitContext creditLimitContext;

	@Builder
	private GetCreditLimitFromFileRouteBuilder(
			@NonNull final CreditLimitFileEndpointConfig fileEndpointConfig,
			@NonNull final CamelContext camelContext,
			@NonNull final String routeId,
			@NonNull final JsonExternalSystemRequest enabledByExternalSystemRequest,
			@NonNull final ProcessLogger processLogger,
			@NonNull final CreditLimitContext creditLimitContext)
	{
		super(camelContext);
		this.fileEndpointConfig = fileEndpointConfig;
		this.routeId = routeId;
		this.enabledByExternalSystemRequest = enabledByExternalSystemRequest;
		this.processLogger = processLogger;
		this.creditLimitContext = creditLimitContext;
	}

	@Override
	public void configure() throws Exception
	{
		//@formatter:off
		from(fileEndpointConfig.getCreditLimitFileEndpoint())
				.routeId(routeId)
				.log("CreditLimit Sync Route Started")
				.process(exchange -> PInstanceUtil.setPInstanceHeader(exchange, enabledByExternalSystemRequest))
				.process(this::attachContext)
				.split(body().tokenize("\n")).streaming()
				  .unmarshal(new BindyCsvDataFormat(CreditLimitRow.class))
				  .process(new CreditLimitUpsertProcessor(enabledByExternalSystemRequest, processLogger, ACCEPTED_CREDIT_TYPES)).id(PROCESS_CREDIT_LIMIT_ROW_PROCESSOR_ID)
				  .choice()
				    .when(bodyAs(UpsertCreditLimitRequest.class).isNull())
				   	  .log(LoggingLevel.DEBUG, "Nothing to do! No credit limit to upsert!")
				    .otherwise()
					  .to(direct(UPSERT_CREDIT_LIMIT_ROUTE_ID))
					.end()
				.end()
				.process(CreditLimitUpsertProcessor::processLastCreditLimitGroup).id(PROCESS_LAST_CREDIT_LIMIT_GROUP_PROCESSOR_ID)
				.choice()
				  .when(bodyAs(UpsertCreditLimitRequest.class).isNull())
				     .log(LoggingLevel.DEBUG, "Nothing to do! No last credit limit group present!")
				  .otherwise()
				     .log(LoggingLevel.DEBUG, "Last credit limit group present and ready to upsert!")
					 .to(direct(UPSERT_CREDIT_LIMIT_ROUTE_ID))
				  .endChoice()
				.end();
		// @formatter:on
	}

	private void attachContext(@NonNull final Exchange exchange)
	{
		exchange.setProperty(ROUTE_PROPERTY_CREDIT_LIMIT_ROUTE_CONTEXT, creditLimitContext);
	}
}
