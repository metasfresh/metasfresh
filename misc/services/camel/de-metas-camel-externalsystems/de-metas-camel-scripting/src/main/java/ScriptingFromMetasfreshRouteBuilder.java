/*
 * #%L
 * de-metas-camel-scripting
 * %%
 * Copyright (C) 2025 metas GmbH
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

import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

/**
 * Invokes a given script on a given request-body and forwards the result to an an API-request.
 */
@Component
public class ScriptingFromMetasfreshRouteBuilder extends RouteBuilder
{
	public static final String Scripting_ROUTE_ID = "Spript-Conversion";

	private final ProcessLogger processLogger;

	public ScriptingFromMetasfreshRouteBuilder(@NonNull final ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void configure()
	{
		CamelRouteUtil.setupProperties(getContext());

		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(Scripting_ROUTE_ID))
				.routeId(Scripting_ROUTE_ID)
				.log("Route invoked!")
				.streamCache("true")

		.process(this::prepareSpriptingRequest);			
	}
	
	private void prepareSpriptingRequest(@NonNull final Exchange exchange){

		// the (json-)string to be converted
		final String scriptingRequestBody = ProcessorHelper.getPropertyOrThrowError(exchange,
				"requestBody",
				String.class);
		final String scriptIdentifier = ProcessorHelper.getPropertyOrThrowError(exchange,
				"scriptIdentifier",
				String.class); 
		
	}
}
