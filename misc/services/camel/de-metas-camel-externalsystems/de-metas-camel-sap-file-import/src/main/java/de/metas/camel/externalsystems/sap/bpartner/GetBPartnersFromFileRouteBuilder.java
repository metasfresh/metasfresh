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

package de.metas.camel.externalsystems.sap.bpartner;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.IdAwareRouteBuilder;
import de.metas.camel.externalsystems.common.InvokeExternalSystemParametersUtil;
import de.metas.camel.externalsystems.common.PInstanceUtil;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.sap.config.BPartnerFileEndpointConfig;
import de.metas.camel.externalsystems.sap.model.bpartner.BPartnerRow;
import de.metas.common.externalsystem.JsonExternalSAPBPartnerImportSettings;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;

import java.util.Optional;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.sap.bpartner.ProcessSkippedBPartnerRouteBuilder.PROCESS_SKIPPED_BPARTNER_ROUTE;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SAP_BPARTNER_IMPORT_SETTINGS;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

public class GetBPartnersFromFileRouteBuilder extends IdAwareRouteBuilder
{
	@VisibleForTesting
	public static final String UPSERT_BPARTNER_GROUP_ENDPOINT_ID = "SAP-BPartners-upsertBPartnerEndpointId";
	@VisibleForTesting
	public static final String UPSERT_LAST_BPARTNER_GROUP_ENDPOINT_ID = "SAP-BPartners-upsertLastBPartnerEndpointId";
	@VisibleForTesting
	public static final String PROCESS_SKIPPED_BPARTNER_ENDPOINT_ID = "SAP-BPartners-processSkippedPartnerEndpointId";
	private static final String UPSERT_BPARTNER_PROCESSOR_ID = "SAP-BPartners-upsertBPartnerProcessorId";
	public static final String ROUTE_PROPERTY_UPSERT_BPARTNERS_ROUTE_CONTEXT = "UpsertBPartnersRouteContext";

	@NonNull
	private final BPartnerFileEndpointConfig fileEndpointConfig;

	@Getter
	@NonNull
	private final String routeId;

	@NonNull
	private final JsonExternalSystemRequest enabledByExternalSystemRequest;

	@NonNull
	private final ProcessLogger processLogger;

	@Builder
	private GetBPartnersFromFileRouteBuilder(
			@NonNull final BPartnerFileEndpointConfig fileEndpointConfig,
			@NonNull final CamelContext camelContext,
			@NonNull final String routeId,
			@NonNull final JsonExternalSystemRequest enabledByExternalSystemRequest,
			@NonNull final ProcessLogger processLogger)
	{
		super(camelContext);
		this.fileEndpointConfig = fileEndpointConfig;
		this.routeId = routeId;
		this.enabledByExternalSystemRequest = enabledByExternalSystemRequest;
		this.processLogger = processLogger;
	}

	@Override
	public void configure() throws Exception
	{
		//@formatter:off
		from(fileEndpointConfig.getBPartnerFileEndpoint())
				.id(routeId)
				.streamCaching()
				.log("Business Partner Sync Route Started with Id=" + routeId)
				.process(exchange -> PInstanceUtil.setPInstanceHeader(exchange, enabledByExternalSystemRequest))
				.process(this::prepareBPartnerSyncRouteContext)
				.split(body().tokenize("\n"))
					.streaming()
				    .unmarshal(new BindyCsvDataFormat(BPartnerRow.class))
					.choice()
						.when(this::shouldSkipRow)
							.process(this::prepareProcessSkippedBPartnerRequest)
							.to(direct(PROCESS_SKIPPED_BPARTNER_ROUTE)).id(PROCESS_SKIPPED_BPARTNER_ENDPOINT_ID)
						.otherwise()
							.process(new CreateBPartnerUpsertRequestProcessor(enabledByExternalSystemRequest, processLogger)).id(UPSERT_BPARTNER_PROCESSOR_ID)
							.choice()
							   .when(bodyAs(BPUpsertCamelRequest.class).isNull())
									.log(LoggingLevel.INFO, "Nothing to do! No business partner to upsert!")
							   .otherwise()
									.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert Business Partners: ${body}")
									.to("{{" + MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}").id(UPSERT_BPARTNER_GROUP_ENDPOINT_ID)
							   .endChoice()
							.end()
						.endChoice()
					.end()
				.end()
				.process(this::processLastBPartnerGroup)
				.choice()
					.when(bodyAs(BPUpsertCamelRequest.class).isNull())
						.log(LoggingLevel.DEBUG, "No last group present! Nothing to upsert...")
				   	.otherwise()
						.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert Business Partners: ${body}")
						.to("{{" + MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}").id(UPSERT_LAST_BPARTNER_GROUP_ENDPOINT_ID)
					.endChoice()
				.end();
		//@formatter:on
	}

	private void prepareBPartnerSyncRouteContext(@NonNull final Exchange exchange)
	{
		final UpsertBPartnerRouteContext getBPartnerRouteContext = UpsertBPartnerRouteContext.builder()
				.orgCode(enabledByExternalSystemRequest.getOrgCode())
				.externalSystemConfigId(enabledByExternalSystemRequest.getExternalSystemConfigId())
				.bPartnerImportSettings(Optional.ofNullable(InvokeExternalSystemParametersUtil
																	.getDeserializedParameter(enabledByExternalSystemRequest.getParameters(),
																							  PARAM_SAP_BPARTNER_IMPORT_SETTINGS,
																							  JsonExternalSAPBPartnerImportSettings[].class))
												.map(ImmutableList::copyOf)
												.orElseGet(ImmutableList::of)
												.stream()
												.collect(ImmutableList.toImmutableList()))
				.build();

		exchange.setProperty(ROUTE_PROPERTY_UPSERT_BPARTNERS_ROUTE_CONTEXT, getBPartnerRouteContext);
	}

	private void processLastBPartnerGroup(@NonNull final Exchange exchange) throws Exception
	{
		final UpsertBPartnerRouteContext routeContext = exchange.getProperty(ROUTE_PROPERTY_UPSERT_BPARTNERS_ROUTE_CONTEXT, UpsertBPartnerRouteContext.class);

		if (routeContext.getSyncBPartnerRequestBuilder() == null)
		{
			exchange.getIn().setBody(null);
			return;
		}

		final BPUpsertCamelRequest bpUpsertCamelRequest = routeContext.getSyncBPartnerRequestBuilder().build();
		exchange.getIn().setBody(bpUpsertCamelRequest);
	}

	@NonNull
	private boolean shouldSkipRow(@NonNull final Exchange exchange)
	{
		final BPartnerRow bPartnerRow = exchange.getIn().getBody(BPartnerRow.class);
		return bPartnerRow.isDisabled();
	}

	private void prepareProcessSkippedBPartnerRequest(@NonNull final Exchange exchange)
	{
		final BPartnerRow bPartnerRow = exchange.getIn().getBody(BPartnerRow.class);

		final ProcessSkippedBPartnerRequest request = ProcessSkippedBPartnerRequest.builder()
				.bPartnerRow(bPartnerRow)
				.externalSystemConfigId(enabledByExternalSystemRequest.getExternalSystemConfigId())
				.orgCode(enabledByExternalSystemRequest.getOrgCode())
				.adPInstanceId(enabledByExternalSystemRequest.getAdPInstanceId())
				.build();

		exchange.getIn().setBody(request);
	}
}
