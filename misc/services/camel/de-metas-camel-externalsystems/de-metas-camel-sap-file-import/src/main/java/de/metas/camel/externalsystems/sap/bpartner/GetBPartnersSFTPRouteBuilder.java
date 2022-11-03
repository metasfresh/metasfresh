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
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.common.IdAwareRouteBuilder;
import de.metas.camel.externalsystems.sap.model.bpartner.BPartnerRow;
import de.metas.camel.externalsystems.sap.sftp.SFTPConfig;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI;

public class GetBPartnersSFTPRouteBuilder extends IdAwareRouteBuilder
{
	@VisibleForTesting
	public static final String UPSERT_BPARTNER_ENDPOINT_ID = "SAP-BPartners-upsertBPartnerEndpointId";
	private static final String UPSERT_BPARTNER_PROCESSOR_ID = "SAP-BPartners-upsertBPartnerProcessorId";

	public static final String ROUTE_PROPERTY_GET_BPARTNERS_ROUTE_CONTEXT = "GetBpartnersRouteContext";

	@NonNull
	private final SFTPConfig sftpConfig;

	@Getter
	@NonNull
	private final String routeId;

	@NonNull
	private final JsonExternalSystemRequest enabledByExternalSystemRequest;

	@NonNull
	private final ProcessLogger processLogger;

	@Builder
	private GetBPartnersSFTPRouteBuilder(
			@NonNull final SFTPConfig sftpConfig,
			@NonNull final CamelContext camelContext,
			@NonNull final String routeId,
			@NonNull final JsonExternalSystemRequest enabledByExternalSystemRequest,
			@NonNull final ProcessLogger processLogger)
	{
		super(camelContext);
		this.sftpConfig = sftpConfig;
		this.routeId = routeId;
		this.enabledByExternalSystemRequest = enabledByExternalSystemRequest;
		this.processLogger = processLogger;
	}

	@Override
	public void configure() throws Exception
	{
		//@formatter:off
		from(sftpConfig.getSFTPConnectionStringBPartner())
				.id(routeId)
				.log("Business Partner Sync Route Started")
				.process(this::prepareBPartnerSyncRouteContext)
				.split(body().tokenize("\n"))
					.streaming()
				    .unmarshal(new BindyCsvDataFormat(BPartnerRow.class))
					.process(new BPartnerUpsertProcessor(enabledByExternalSystemRequest, processLogger)).id(UPSERT_BPARTNER_PROCESSOR_ID)
					.choice()
				       .when(bodyAs(BPUpsertCamelRequest.class).isNull())
				         .log(LoggingLevel.INFO, "Nothing to do! No business partner to upsert!")
					   .otherwise()
				         .log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert Business Partners: ${body}")
				         .to("{{" + MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}").id(UPSERT_BPARTNER_ENDPOINT_ID)
					   .endChoice()
				    .end()
				.process(this::processLastBPartnerGroup)
				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert Business Partners: ${body}")
				.to("{{" + MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}").id(UPSERT_BPARTNER_ENDPOINT_ID)
				.end();
		//@formatter:on
	}

	@NonNull
	public static String buildRouteId(@NonNull final String externalSystemConfigValue)
	{
		return GetBPartnersSFTPRouteBuilder.class.getSimpleName() + "#" + externalSystemConfigValue;
	}

	private void prepareBPartnerSyncRouteContext(@NonNull final Exchange exchange)
	{
		final GetBPartnerRouteContext getBPartnerRouteContext = GetBPartnerRouteContext.builder()
				.orgCode(enabledByExternalSystemRequest.getOrgCode())
				.externalSystemConfigId(enabledByExternalSystemRequest.getExternalSystemConfigId())
				.build();

		exchange.setProperty(ROUTE_PROPERTY_GET_BPARTNERS_ROUTE_CONTEXT, getBPartnerRouteContext);
	}

	private void processLastBPartnerGroup(@NonNull final Exchange exchange) throws Exception
	{
		final GetBPartnerRouteContext getBPartnerRouteContext = exchange.getProperty(ROUTE_PROPERTY_GET_BPARTNERS_ROUTE_CONTEXT, GetBPartnerRouteContext.class);

		Check.assumeNotNull(getBPartnerRouteContext.getSyncBPartnerRequestBuilder(), "SyncBPartnerRequestBuilder from context should not be null at this point!");

		final BPUpsertCamelRequest bpUpsertCamelRequest = getBPartnerRouteContext.getSyncBPartnerRequestBuilder().build();
		exchange.getIn().setBody(bpUpsertCamelRequest);
	}
}
