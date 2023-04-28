/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.from_grs.hu;

import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonHUClear;
import de.metas.common.handlingunits.JsonClearanceStatus;
import de.metas.common.handlingunits.JsonSetClearanceStatusRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_CLEAR_HU_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.RouteBuilderHelper.setupJacksonDataFormatFor;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class ClearHURouteBuilder extends RouteBuilder
{
	public static final String CLEAR_HU_ROUTE_ID = "GRSSignum-clearHU";

	public static final String CLEAR_HU_PROCESSOR_ID = "GRSSignum-clearHUProcessorID";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(CLEAR_HU_ROUTE_ID))
				.routeId(CLEAR_HU_ROUTE_ID)
				.log("Route invoked!")
				.unmarshal(setupJacksonDataFormatFor(getContext(), JsonHUClear.class))
				.process(this::getAndAttachClearanceStatusRequest).id(CLEAR_HU_PROCESSOR_ID)
				.to(direct(MF_CLEAR_HU_V2_CAMEL_ROUTE_ID));
	}

	private void getAndAttachClearanceStatusRequest(@NonNull final Exchange exchange)
	{
		final JsonHUClear requestBody = exchange.getIn().getBody(JsonHUClear.class);

		final JsonMetasfreshId metasfreshId = JsonMetasfreshId.of(Integer.parseInt(requestBody.getMetasfreshId()));

		final JsonSetClearanceStatusRequest clearanceStatusRequest = JsonSetClearanceStatusRequest.builder()
				.huIdentifier(JsonSetClearanceStatusRequest.JsonHUIdentifier.ofJsonMetasfreshId(metasfreshId))
				.clearanceStatus(toJsonClearanceStatus(requestBody.getClearanceStatus()))
				.clearanceNote(requestBody.getClearanceNote())
				.build();

		exchange.getIn().setBody(clearanceStatusRequest, JsonSetClearanceStatusRequest.class);
	}

	@NonNull
	private static JsonClearanceStatus toJsonClearanceStatus(@NonNull final String status)
	{
		try
		{
			return JsonClearanceStatus.valueOf(status);
		}
		catch (final Exception e)
		{
			throw new RuntimeCamelException("Unknown status " + status + ". Clearance status needs to be value of JsonClearanceStatus.");
		}
	}
}
