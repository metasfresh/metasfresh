/*
 * #%L
 * de-metas-camel-woocommerce
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

package de.metas.camel.externalsystems.woocommerce.restapi;

import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.LogMessageRequest;
import de.metas.camel.externalsystems.common.auth.JsonAuthenticateRequest;
import de.metas.camel.externalsystems.common.auth.JsonExpireTokenResponse;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.RouteController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_LOG_MESSAGE_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.REST_API_AUTHENTICATE_TOKEN;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.REST_API_EXPIRE_TOKEN;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.REST_WOOCOMMERCE_PATH;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class RestAPIRouteBuilder extends RouteBuilder
{
	public static final String REST_API_ROUTE_ID = "WOO-restAPI";
	public static final String ENABLE_RESOURCE_ROUTE_ID = "WOO-enableRestAPI";
	public static final String DISABLE_RESOURCE_ROUTE_ID = "WOO-disableRestAPI";

	public static final String ENABLE_RESOURCE_ROUTE_PROCESSOR_ID = "WOO-enableRestAPIProcessor";
	public static final String DISABLE_RESOURCE_ROUTE_PROCESSOR_ID = "WOO-disableRestAPIProcessor";
	public static final String ENABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID = "WOO-ER-AttachAuthenticateReqProcessorId";
	public static final String DISABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID = "WOO-DR-AttachAuthenticateReqProcessorId";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		//@formatter:off
		from(direct(ENABLE_RESOURCE_ROUTE_ID))
				.routeId(ENABLE_RESOURCE_ROUTE_ID)
				.streamCaching()
				.log("Route invoked!")
				.process(this::enableRestAPIProcessor).id(ENABLE_RESOURCE_ROUTE_PROCESSOR_ID)
				.process(this::attachAuthenticateRequest).id(ENABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID)
				.to(direct(REST_API_AUTHENTICATE_TOKEN))
				.end();

		from(direct(DISABLE_RESOURCE_ROUTE_ID))
				.routeId(DISABLE_RESOURCE_ROUTE_ID)
				.streamCaching()
				.log("Route invoked!")
				.process(this::attachAuthenticateRequest).id(DISABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID)
				.to(direct(REST_API_EXPIRE_TOKEN))
				.process(this::disableRestAPIProcessor).id(DISABLE_RESOURCE_ROUTE_PROCESSOR_ID)
				.end();

		rest().path(REST_WOOCOMMERCE_PATH)
				.post()
				.route()
				.routeId(REST_API_ROUTE_ID)
				.autoStartup(false)
				.process(this::restAPIProcessor)
				.to(direct(MF_LOG_MESSAGE_ROUTE_ID))
				.end();

		//@formatter:on

	}

	private void enableRestAPIProcessor(@NonNull final Exchange exchange) throws Exception
	{
		final RouteController routeController = getContext().getRouteController();

		routeController.resumeRoute(REST_API_ROUTE_ID);
	}

	private void disableRestAPIProcessor(@NonNull final Exchange exchange) throws Exception
	{
		final JsonExpireTokenResponse response = exchange.getIn().getBody(JsonExpireTokenResponse.class);

		if (response != null && response.getNumberOfAuthenticatedTokens() == 0)
		{
			getContext().getRouteController().suspendRoute(REST_API_ROUTE_ID);
		}
	}

	private void restAPIProcessor(@NonNull final Exchange exchange)
	{
		final TokenCredentials credentials = (TokenCredentials)SecurityContextHolder.getContext().getAuthentication().getCredentials();

		if (credentials == null)
		{
			throw new RuntimeCamelException("Missing credentials!");
		}

		final String requestBody = exchange.getIn().getBody(String.class);

		final String logMessage = REST_API_ROUTE_ID + " has been called with requestBody:" + requestBody;

		final LogMessageRequest logMessageRequest = LogMessageRequest.builder()
				.logMessage(logMessage)
				.pInstanceId(credentials.getPInstance())
				.build();

		exchange.getIn().setBody(logMessageRequest);
	}

	private void attachAuthenticateRequest(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final JsonAuthenticateRequest jsonAuthenticateRequest = getJsonAuthenticateRequest(request);

		exchange.getIn().setBody(jsonAuthenticateRequest);
	}

	@NonNull
	private JsonAuthenticateRequest getJsonAuthenticateRequest(@NonNull final JsonExternalSystemRequest request)
	{
		if (request.getAdPInstanceId() == null)
		{
			throw new RuntimeCamelException("Missing pInstance identifier!");
		}

		final String authKey = request.getParameters().get(ExternalSystemConstants.PARAM_CAMEL_HTTP_RESOURCE_AUTH_KEY);

		if (authKey == null)
		{
			throw new RuntimeCamelException("Missing authKey from request!");
		}

		final String externalSystemValue = request.getParameters().get(ExternalSystemConstants.PARAM_CHILD_CONFIG_VALUE);
		final String auditTrailEndpoint = request.getWriteAuditEndpoint();

		return JsonAuthenticateRequest.builder()
				.grantedAuthority(ExternalSystemCamelConstants.WOOCOMMERCE_AUTHORITY)
				.authKey(authKey)
				.pInstance(request.getAdPInstanceId())
				.externalSystemValue(externalSystemValue)
				.auditTrailEndpoint(auditTrailEndpoint)
				.build();
	}
}
