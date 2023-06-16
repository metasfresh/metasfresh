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

package de.metas.camel.externalsystems.shopware6.customer;

import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.customer.processor.CreateCustomerUpsertReqProcessor;
import de.metas.camel.externalsystems.shopware6.customer.processor.GetCustomersPageProcessor;
import de.metas.camel.externalsystems.shopware6.customer.processor.RuntimeParametersProcessor;
import de.metas.camel.externalsystems.shopware6.product.GetProductsRouteHelper;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ProcessorHelper.getPropertyOrThrowError;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_CUSTOMERS_CONTEXT;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class GetCustomersRouteBuilder extends RouteBuilder
{
	public static final String GET_CUSTOMERS_ROUTE_ID = "Shopware6-getCustomers";
	public static final String PROCESS_CUSTOMER_ROUTE_ID = "Shopware6-processCustomer";
	public static final String PROCESS_CUSTOMER_PAGE_ROUTE_ID = "Shopware6-processCustomerPage";
	public static final String GET_CUSTOMERS_PAGE_PROCESSOR_ID = "Shopware6-GetCustomersPageProcessorId";
	public static final String UPSERT_RUNTIME_PARAMS_ROUTE_ID = "Shopware6-upsertRuntimeParamsCustomers";

	public static final String PREPARE_CONTEXT_PROCESSOR_ID = "Shopware6-prepareContextProcessorId";

	private final ProcessLogger processLogger;

	public GetCustomersRouteBuilder(@NonNull final ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void configure() throws Exception
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		//@formatter:off
		from(direct(GET_CUSTOMERS_ROUTE_ID))
				.routeId(GET_CUSTOMERS_ROUTE_ID)
				.log("Route invoked")
				.streamCaching()
				.process(this::prepareContext).id(PREPARE_CONTEXT_PROCESSOR_ID)
				.to(direct(PROCESS_CUSTOMER_PAGE_ROUTE_ID))
				.to(direct(UPSERT_RUNTIME_PARAMS_ROUTE_ID))
				.process((exchange) -> processLogger.logMessage("Shopware6:GetCustomers process ended!" + Instant.now(),
																exchange.getIn().getHeader(HEADER_PINSTANCE_ID, Integer.class)));

		from (direct(PROCESS_CUSTOMER_PAGE_ROUTE_ID))
				.routeId(PROCESS_CUSTOMER_PAGE_ROUTE_ID)
						.log("Route invoked")
								.end()
				.process(new GetCustomersPageProcessor()).id(GET_CUSTOMERS_PAGE_PROCESSOR_ID)
				.split(body())
					.to(direct(PROCESS_CUSTOMER_ROUTE_ID))
				.end()
				.choice()
				.when(areMoreCustomersLeftToBeRetrieved())
				.to(direct(PROCESS_CUSTOMER_PAGE_ROUTE_ID))
				.otherwise()
				.log(LoggingLevel.INFO, "Nothing to do! No additional customers to retrieve!")
				.end();

		from(direct(PROCESS_CUSTOMER_ROUTE_ID))
				.routeId(PROCESS_CUSTOMER_ROUTE_ID)
				.log("Route invoked")
				.doTry()
					.process(new CreateCustomerUpsertReqProcessor()).id(PROCESS_CUSTOMER_ROUTE_ID)
					.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert BPartners: ${body}")
					.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
				.doCatch(Exception.class)
					.to(direct(MF_ERROR_ROUTE_ID))
					.process(this::processError)
				.endDoTry()
				.end();

		from(direct(UPSERT_RUNTIME_PARAMS_ROUTE_ID))
				.routeId(UPSERT_RUNTIME_PARAMS_ROUTE_ID)
				.log("Route invoked")
				.process(new RuntimeParametersProcessor()).id(UPSERT_RUNTIME_PARAMS_ROUTE_ID)
				.choice()
					.when(body().isNull())
						.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert runtime parameters: ${body}")
					.otherwise()
						.to(direct(ExternalSystemCamelConstants.MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID))
				.endChoice()
				.end();
		//@formatter:on
	}

	private void prepareContext(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		exchange.getIn().setHeader(HEADER_ORG_CODE, request.getOrgCode());

		if (request.getAdPInstanceId() != null)
		{
			exchange.getIn().setHeader(HEADER_PINSTANCE_ID, request.getAdPInstanceId().getValue());

			processLogger.logMessage("Shopware6:GetCustomers process started!" + Instant.now(), request.getAdPInstanceId().getValue());
		}

		final String clientSecret = request.getParameters().get(ExternalSystemConstants.PARAM_CLIENT_SECRET);
		final String clientId = request.getParameters().get(ExternalSystemConstants.PARAM_CLIENT_ID);
		final String basePath = request.getParameters().get(ExternalSystemConstants.PARAM_BASE_PATH);

		final PInstanceLogger pInstanceLogger = PInstanceLogger.builder()
				.processLogger(processLogger)
				.pInstanceId(request.getAdPInstanceId())
				.build();

		final ShopwareClient shopwareClient = ShopwareClient.of(clientId, clientSecret, basePath, pInstanceLogger);

		final String updatedAfterOverride = request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER_OVERRIDE);

		final String updatedAfter = CoalesceUtil.coalesceNotNull(
				updatedAfterOverride,
				request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER),
				Instant.ofEpochSecond(0).toString());

		final boolean skipNextImportTimestamp = Optional.ofNullable(updatedAfterOverride).isPresent();

		final ImportCustomersRouteContext customersRouteContext = ImportCustomersRouteContext.builder()
				.orgCode(request.getOrgCode())
				.shopwareClient(shopwareClient)
				.request(request)
				.updatedAfter(updatedAfter)
				.priceListBasicInfo(GetProductsRouteHelper.getTargetPriceListInfo(request))
				.skipNextImportTimestamp(skipNextImportTimestamp)
				.responsePageIndex(1)
				.pageLimit(ExternalSystemConstants.DEFAULT_SW6_ORDER_PAGE_SIZE)
				.build();

		exchange.setProperty(ROUTE_PROPERTY_IMPORT_CUSTOMERS_CONTEXT, customersRouteContext);
	}

	private void processError(@NonNull final Exchange exchange)
	{
		final ImportCustomersRouteContext routeContext = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_CUSTOMERS_CONTEXT, ImportCustomersRouteContext.class);
		routeContext.recomputeOldestFailingCustomer(routeContext.getCustomerUpdatedAt());
	}

	private Predicate areMoreCustomersLeftToBeRetrieved()
	{
		return (exchange) -> {
			final ImportCustomersRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_CUSTOMERS_CONTEXT, ImportCustomersRouteContext.class);
			return routeContext.isMoreCustomersAvailable();
		};
	}
}
