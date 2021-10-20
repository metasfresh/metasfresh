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

package de.metas.camel.externalsystems.core.restapi.auth;

import com.google.common.collect.ImmutableList;
import com.sun.istack.NotNull;
import de.metas.camel.externalsystems.common.auth.JsonAuthenticateRequest;
import de.metas.camel.externalsystems.common.auth.JsonExpireTokenResponse;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.REST_API_AUTHENTICATE_TOKEN;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.REST_API_EXPIRE_TOKEN;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class RestAPIAuthenticateRoute extends RouteBuilder
{
	private final TokenService tokenService;

	public RestAPIAuthenticateRoute(@NotNull final TokenService tokenService)
	{
		this.tokenService = tokenService;
	}

	@Override
	public void configure() throws Exception
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		//@formatter:off
		from(direct(REST_API_AUTHENTICATE_TOKEN))
				.routeId(REST_API_AUTHENTICATE_TOKEN)
				.streamCaching()
				.log("Route invoked!")
				.process(this::processorRegisterRoute)
				.end();

		from(direct(REST_API_EXPIRE_TOKEN))
				.routeId(REST_API_EXPIRE_TOKEN)
				.streamCaching()
				.log("Route invoked!")
				.process(this::processorExpireRoute)
				.end();
		//@formatter:on
	}

	public void processorRegisterRoute(@NotNull final Exchange exchange)
	{
		final JsonAuthenticateRequest request = exchange.getIn().getBody(JsonAuthenticateRequest.class);
		final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(request.getGrantedAuthority());

		tokenService.store(request.getAuthKey(), ImmutableList.of(authority), getTokenCredentials(request));
	}

	public void processorExpireRoute(@NotNull final Exchange exchange)
	{
		final JsonAuthenticateRequest request = exchange.getIn().getBody(JsonAuthenticateRequest.class);

		tokenService.expiryToken(request.getAuthKey());

		final int numberOfAuthenticatedTokens = tokenService.getNumberOfAuthenticatedTokens(request.getGrantedAuthority());

		exchange.getIn().setBody(JsonExpireTokenResponse.builder()
										 .numberOfAuthenticatedTokens(numberOfAuthenticatedTokens)
										 .build());
	}

	@NotNull
	private TokenCredentials getTokenCredentials(@NotNull final JsonAuthenticateRequest request)
	{
		return TokenCredentials.builder()
				.pInstance(request.getPInstance())
				.auditTrailEndpoint(request.getAuditTrailEndpoint())
				.externalSystemValue(request.getExternalSystemValue())
				.build();
	}
}
