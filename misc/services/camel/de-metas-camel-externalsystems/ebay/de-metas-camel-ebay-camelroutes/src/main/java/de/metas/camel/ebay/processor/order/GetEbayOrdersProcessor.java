/*
 * #%L
 * de-metas-camel-ebay-camelroutes
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

package de.metas.camel.ebay.processor.order;

import static de.metas.camel.ebay.EbayConstants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;

import java.time.Instant;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ebay.api.client.auth.oauth2.OAuth2Api;
import com.ebay.api.client.auth.oauth2.model.Environment;
import com.ebay.api.client.auth.oauth2.model.OAuthResponse;

import de.metas.camel.ebay.EbayConstants;
import de.metas.camel.ebay.EbayImportOrdersRouteContext;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.ebay.api.OrderApi;
import de.metas.camel.externalsystems.ebay.api.invoker.ApiClient;
import de.metas.camel.externalsystems.ebay.api.invoker.Configuration;
import de.metas.camel.externalsystems.ebay.api.invoker.auth.OAuth;
import de.metas.camel.externalsystems.ebay.api.model.Order;
import de.metas.camel.externalsystems.ebay.api.model.OrderSearchPagedCollection;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;

/**
 * Processor to load orders from the eBay fulfilment api.
 *
 */
public class GetEbayOrdersProcessor implements Processor
{

	protected Logger log = LoggerFactory.getLogger(getClass());
	
	private final ProcessLogger processLogger;
	
	public GetEbayOrdersProcessor(final ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}
	

	@Override
	public void process(Exchange exchange) throws Exception
	{

		log.debug("Execute ebay order request");

		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		exchange.getIn().setHeader(HEADER_ORG_CODE, request.getOrgCode());
		if (request.getAdPInstanceId() != null)
		{
			exchange.getIn().setHeader(HEADER_PINSTANCE_ID, request.getAdPInstanceId().getValue());

			processLogger.logMessage("Ebay:GetOrders process started!" + Instant.now(), request.getAdPInstanceId().getValue());
		}

		final String updatedAfter = request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER);

		// prepare api call
		final String authCode = request.getParameters().get(EbayConstants.PARAM_API_AUTH_CODE);
		final String apiMode = request.getParameters().get(EbayConstants.PARAM_API_MODE);

		// env default to sandbox.
		Environment ebayExecutionEnv;
		if (Environment.PRODUCTION.toString().equalsIgnoreCase(apiMode))
		{
			ebayExecutionEnv = Environment.PRODUCTION;
		}
		else
		{
			ebayExecutionEnv = Environment.SANDBOX;

		}

		// get api auth token
		final OAuthResponse oauth2Response;
		if (exchange.getIn().getHeader(EbayConstants.ROUTE_PROPERTY_EBAY_AUTH_CLIENT) == null)
		{

			log.debug("Getting ebay access token");
			OAuth2Api auth2Api = new OAuth2Api();
			oauth2Response = auth2Api.exchangeCodeForAccessToken(ebayExecutionEnv, authCode);

		}
		else
		{

			log.debug("Using provided ebay auth client");
			OAuth2Api auth2Api = (OAuth2Api)exchange.getIn().getHeader(EbayConstants.ROUTE_PROPERTY_EBAY_AUTH_CLIENT);;
			oauth2Response = auth2Api.exchangeCodeForAccessToken(ebayExecutionEnv, authCode);
		}

		// execut api call
		if (oauth2Response.getAccessToken().isPresent())
		{

			// construct api client or use provided one.
			final OrderApi orderApi;
			if (exchange.getIn().getHeader(EbayConstants.ROUTE_PROPERTY_EBAY_CLIENT) == null)
			{
				log.debug("Constructing ebay api client");

				ApiClient defaultClient = Configuration.getDefaultApiClient();
				defaultClient.setBasePath(ebayExecutionEnv.getApiEndpoint());

				// Configure OAuth2 access token for authorization: api_auth
				OAuth api_auth = (OAuth)defaultClient.getAuthentication("api_auth");
				api_auth.setAccessToken(oauth2Response.getAccessToken().get().getToken());

				orderApi = new OrderApi(defaultClient);

			}
			else
			{

				log.debug("Using provided ebay api client");
				orderApi = (OrderApi)exchange.getIn().getHeader(EbayConstants.ROUTE_PROPERTY_EBAY_CLIENT);
			}

			String fieldGroups = null;
			String filter = "lastmodifieddate:".concat(updatedAfter);
			String limit = "50";
			String offset = null;
			String orderIds = null;
			OrderSearchPagedCollection response = orderApi.getOrders(fieldGroups, filter, limit, offset, orderIds);

			List<Order> orders = response.getOrders();

			// add orders to exchange
			exchange.getIn().setBody(orders);

			// add order context to exchange.
			final EbayImportOrdersRouteContext ordersContext = EbayImportOrdersRouteContext.builder()
					.orgCode(request.getOrgCode())
					.externalSystemRequest(request)
					.build();

			exchange.setProperty(ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, ordersContext);

		}
		else
		{
			throw new RuntimeException("Ebay:Failed to aquire access token! " + Instant.now());
		}

	}

}
