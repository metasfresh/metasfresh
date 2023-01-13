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

package de.metas.camel.externalsystems.sap.bpartner;

import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.v2.BPRetrieveCamelRequest;
import de.metas.camel.externalsystems.sap.error.LoggableException;
import de.metas.camel.externalsystems.sap.model.bpartner.BPartnerRow;
import de.metas.common.bpartner.v2.response.JsonResponseBPartner;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.ERROR_WRITE_TO_ADISSUE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class ProcessSkippedBPartnerRouteBuilder extends RouteBuilder
{
	public static final String PROCESS_SKIPPED_BPARTNER_ROUTE = "SAP-BPartner-processSkippedBPartnerRoute";
	public static final String PROCESS_SKIPPED_BPARTNER_ROUTE_ID = "SAP-BPartner-processSkippedBPartnerRouteId";

	private static final String ROUTE_PROPERTY_PROCESS_SKIPPED_BPARTNER_ROUTE_CONTEXT = "ProcessSkippedBPartnerRouteContext";

	@Override
	public void configure() throws Exception
	{
		//@formatter:off
		from(direct(PROCESS_SKIPPED_BPARTNER_ROUTE))
				.id(PROCESS_SKIPPED_BPARTNER_ROUTE_ID)
				.streamCaching()
				.process(this::buildRouteContext)
				.process(this::buildBPRetrieveCamelRequest)
				.doTry()
					.to("{{" + ExternalSystemCamelConstants.MF_RETRIEVE_BPARTNER_V2_CAMEL_URI + "}}")
					.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonResponseComposite.class))
					.process(this::processRetrieveBPartnerResponse)
				.doCatch(LoggableException.class)
					.to(direct(ERROR_WRITE_TO_ADISSUE))
				.doCatch(Throwable.class)
					.log(LoggingLevel.WARN,"${exception.message}")
				.endDoTry()
				.end();
		//@formatter:on
	}

	private void buildRouteContext(@NonNull final Exchange exchange)
	{
		final ProcessSkippedBPartnerRequest request = exchange.getIn().getBody(ProcessSkippedBPartnerRequest.class);

		final ProcessSkippedBPartnerRouteContext context = ProcessSkippedBPartnerRouteContext
				.builder()
				.bPartnerRow(request.getBPartnerRow())
				.adPInstanceId(request.getAdPInstanceId())
				.build();

		exchange.setProperty(ROUTE_PROPERTY_PROCESS_SKIPPED_BPARTNER_ROUTE_CONTEXT, context);
	}

	private void buildBPRetrieveCamelRequest(@NonNull final Exchange exchange)
	{
		final ProcessSkippedBPartnerRequest request = exchange.getIn().getBody(ProcessSkippedBPartnerRequest.class);

		final BPartnerRow bPartnerRow = request.getBPartnerRow();

		final String bPartnerIdentifier = UpsertBPartnerRequestBuilder.getBPartnerExternalIdentifier(bPartnerRow);

		final BPRetrieveCamelRequest retrieveCamelRequest = BPRetrieveCamelRequest.builder()
				.bPartnerIdentifier(bPartnerIdentifier)
				.externalSystemConfigId(request.getExternalSystemConfigId())
				.adPInstanceId(request.getAdPInstanceId())
				.orgCode(request.getOrgCode())
				.build();

		exchange.getIn().setBody(retrieveCamelRequest);
	}

	private void processRetrieveBPartnerResponse(@NonNull final Exchange exchange) throws LoggableException
	{
		final JsonResponseComposite jsonResponseComposite = exchange.getIn().getBody(JsonResponseComposite.class);

		final JsonResponseBPartner bPartnerResponse = jsonResponseComposite.getBpartner();

		if (!bPartnerResponse.isActive())
		{
			return;
		}

		final ProcessSkippedBPartnerRouteContext context = exchange
				.getProperty(ROUTE_PROPERTY_PROCESS_SKIPPED_BPARTNER_ROUTE_CONTEXT,
							 ProcessSkippedBPartnerRouteContext.class);

		final BPartnerRow bPartnerRow = context.getBPartnerRow();

		final String bpartnerErrorMessage = "Business partner " + bPartnerRow.getPartnerCode().getRawPartnerCode() + " section code " + bPartnerRow.getSection() + " has deletion flag in SAP.";

		if (context.getAdPInstanceId() != null)
		{
			exchange.getIn().setHeader(HEADER_PINSTANCE_ID, context.getAdPInstanceId().getValue());
		}

		throw new LoggableException(bpartnerErrorMessage);
	}

	@Value
	@Builder
	private static class ProcessSkippedBPartnerRouteContext
	{
		@NonNull
		BPartnerRow bPartnerRow;

		@Nullable
		JsonMetasfreshId adPInstanceId;
	}
}
