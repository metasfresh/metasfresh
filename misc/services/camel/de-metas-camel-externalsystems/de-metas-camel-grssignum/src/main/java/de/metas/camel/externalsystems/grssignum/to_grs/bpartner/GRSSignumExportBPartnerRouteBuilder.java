/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.to_grs.bpartner;

import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.v2.BPRetrieveCamelRequest;
import de.metas.camel.externalsystems.grssignum.GRSSignumConstants;
import de.metas.camel.externalsystems.grssignum.to_grs.bpartner.processor.ExportBPartnerRequestBuilder;
import de.metas.camel.externalsystems.grssignum.to_grs.bpartner.processor.ExportCustomerProcessor;
import de.metas.camel.externalsystems.grssignum.to_grs.bpartner.processor.ExportVendorProcessor;
import de.metas.camel.externalsystems.grssignum.to_grs.client.GRSSignumDispatcherRouteBuilder;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupResponse;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.product.v2.response.JsonResponseProductBPartner;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_GET_BPARTNER_PRODUCTS_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_LOOKUP_EXTERNAL_REFERENCE_v2_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class GRSSignumExportBPartnerRouteBuilder extends RouteBuilder
{
	@VisibleForTesting
	public static final String EXPORT_BPARTNER_ROUTE_ID = "GRSSignum-exportBPartner";
	public static final String PROCESS_VENDOR_ROUTE_ID = "GRSSignum-processVendor";
	public static final String PROCESS_CUSTOMER_ROUTE_ID = "GRSSignum-processCustomer";

	@Override
	public void configure() throws Exception
	{
		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(EXPORT_BPARTNER_ROUTE_ID))
				.routeId(EXPORT_BPARTNER_ROUTE_ID)
				.streamCaching()
				.process(this::buildAndAttachContext)

				.process(this::buildBPRetrieveCamelRequest)
				.to("{{" + ExternalSystemCamelConstants.MF_RETRIEVE_BPARTNER_V2_CAMEL_URI + "}}")
				.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonResponseComposite.class))

				.process(this::processRetrieveBPartnerResponse)
				.multicast()
					.to(direct(PROCESS_VENDOR_ROUTE_ID), direct(PROCESS_CUSTOMER_ROUTE_ID))
				.end();

		from(direct(PROCESS_VENDOR_ROUTE_ID))
				.routeId(PROCESS_VENDOR_ROUTE_ID)
				.choice()
					.when(simple("${body.bpartner.vendor} == true"))
						.process(new ExportVendorProcessor())
						.to(direct(GRSSignumDispatcherRouteBuilder.GRS_DISPATCHER_ROUTE_ID))
				.end();

		from(direct(PROCESS_CUSTOMER_ROUTE_ID))
				.routeId(PROCESS_CUSTOMER_ROUTE_ID)
				.choice()
					.when(simple("${body.bpartner.customer} == true"))
						.process(ExportBPartnerRequestBuilder::prepareRetrieveBPartnerProductRequest)
						.to(direct(MF_GET_BPARTNER_PRODUCTS_ROUTE_ID))
						.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonResponseProductBPartner.class))

						.process(ExportBPartnerRequestBuilder::prepareExternalReferenceLookupRequest)
						.choice()
							.when(body().isNull())
								.log(LoggingLevel.INFO, "Nothing to do! No bpartner products found!")
							.otherwise()
								.to(direct(MF_LOOKUP_EXTERNAL_REFERENCE_v2_ROUTE_ID))
								.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonExternalReferenceLookupResponse.class))
								.process(ExportCustomerProcessor::collectProductExternalReferences)
							.end()
						.process(new ExportCustomerProcessor())
						.to(direct(GRSSignumDispatcherRouteBuilder.GRS_DISPATCHER_ROUTE_ID))
				.end();
		//@formatter:on
	}

	private void buildAndAttachContext(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final String remoteUrl = request.getParameters().get(ExternalSystemConstants.PARAM_EXTERNAL_SYSTEM_HTTP_URL);

		if (Check.isBlank(remoteUrl))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_EXTERNAL_SYSTEM_HTTP_URL);
		}

		final String tenantId = request.getParameters().get(ExternalSystemConstants.PARAM_TENANT_ID);

		if (Check.isBlank(tenantId))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_TENANT_ID);
		}

		final String authToken = request.getParameters().get(ExternalSystemConstants.PARAM_EXTERNAL_SYSTEM_AUTH_TOKEN);

		final ExportBPartnerRouteContext context = ExportBPartnerRouteContext.builder()
				.remoteUrl(remoteUrl)
				.authToken(authToken)
				.tenantId(tenantId)
				.orgCode(request.getOrgCode())
				.build();

		exchange.setProperty(GRSSignumConstants.ROUTE_PROPERTY_EXPORT_BPARTNER_CONTEXT, context);
	}

	private void buildBPRetrieveCamelRequest(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final String bPartnerIdentifier = request.getParameters().get(ExternalSystemConstants.PARAM_BPARTNER_ID);

		if (Check.isBlank(bPartnerIdentifier))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_BPARTNER_ID);
		}

		final JsonMetasfreshId externalSystemConfigId = request.getExternalSystemConfigId();
		final JsonMetasfreshId adPInstanceId = request.getAdPInstanceId();

		final BPRetrieveCamelRequest retrieveCamelRequest = BPRetrieveCamelRequest.builder()
				.bPartnerIdentifier(bPartnerIdentifier)
				.externalSystemConfigId(externalSystemConfigId)
				.adPInstanceId(adPInstanceId)
				.build();

		exchange.getIn().setBody(retrieveCamelRequest);
	}

	private void processRetrieveBPartnerResponse(@NonNull final Exchange exchange)
	{
		final JsonResponseComposite jsonResponseComposite = exchange.getIn().getBody(JsonResponseComposite.class);

		final ExportBPartnerRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, GRSSignumConstants.ROUTE_PROPERTY_EXPORT_BPARTNER_CONTEXT, ExportBPartnerRouteContext.class);

		routeContext.setJsonResponseComposite(jsonResponseComposite);
	}
}
