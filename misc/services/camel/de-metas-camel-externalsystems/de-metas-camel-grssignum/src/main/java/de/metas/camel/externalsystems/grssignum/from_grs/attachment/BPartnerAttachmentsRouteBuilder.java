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

package de.metas.camel.externalsystems.grssignum.from_grs.attachment;

import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.grssignum.GRSSignumConstants;
import de.metas.camel.externalsystems.grssignum.from_grs.RetrieveExternalSystemRequestBuilder;
import de.metas.camel.externalsystems.grssignum.from_grs.attachment.processor.BPartnerAttachmentsProcessor;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonBPartnerAttachment;
import de.metas.common.externalsystem.JsonExternalSystemInfo;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.RouteBuilderHelper.setupJacksonDataFormatFor;
import static de.metas.common.externalsystem.parameters.GRSSignumParameters.PARAM_BasePathForExportDirectories;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class BPartnerAttachmentsRouteBuilder extends RouteBuilder
{
	public static final String ATTACH_FILE_TO_BPARTNER_ROUTE_ID = "GRSSignum-bpartnerAttachFile";

	public static final String ATTACH_FILE_TO_BPARTNER_PROCESSOR_ID = "GRSSignum-attachFileProcessorID";

	@Override
	public void configure() throws Exception
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(ATTACH_FILE_TO_BPARTNER_ROUTE_ID))
				.routeId(ATTACH_FILE_TO_BPARTNER_ROUTE_ID)
				.log("Route invoked!")
				.unmarshal(setupJacksonDataFormatFor(getContext(), JsonBPartnerAttachment.class))
				.process(this::buildAndAttachContext)

				.process(RetrieveExternalSystemRequestBuilder::buildAndAttachRetrieveExternalSystemRequest)
				.to("{{" + ExternalSystemCamelConstants.MF_GET_EXTERNAL_SYSTEM_INFO + "}}")
				.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonExternalSystemInfo.class))
				.process(this::processExternalSystemInfo)

				.process(new BPartnerAttachmentsProcessor()).id(ATTACH_FILE_TO_BPARTNER_PROCESSOR_ID)
				.to(direct(ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID));
	}

	private void buildAndAttachContext(@NonNull final Exchange exchange)
	{
		final JsonBPartnerAttachment jsonBPartnerAttachment = exchange.getIn().getBody(JsonBPartnerAttachment.class);

		final BPartnerAttachmentsRouteContext routeContext = BPartnerAttachmentsRouteContext
				.builder()
				.jsonBPartnerAttachment(jsonBPartnerAttachment)
				.build();

		exchange.setProperty(GRSSignumConstants.ROUTE_PROPERTY_ATTACH_FILE_CONTEXT, routeContext);
	}

	private void processExternalSystemInfo(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemInfo jsonExternalSystemInfo = exchange.getIn().getBody(JsonExternalSystemInfo.class);

		if (jsonExternalSystemInfo == null)
		{
			throw new RuntimeException("Missing external system info!");
		}

		final String exportDirectoriesBasePath = jsonExternalSystemInfo.getParameters().get(PARAM_BasePathForExportDirectories);
		if (Check.isBlank(exportDirectoriesBasePath))
		{
			throw new RuntimeException(PARAM_BasePathForExportDirectories + " not set for ExternalSystem_Config_ID: " + jsonExternalSystemInfo.getExternalSystemConfigId());
		}

		final BPartnerAttachmentsRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, GRSSignumConstants.ROUTE_PROPERTY_ATTACH_FILE_CONTEXT, BPartnerAttachmentsRouteContext.class);
		context.setExportDirectoriesBasePath(exportDirectoriesBasePath);
	}
}
