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
import de.metas.camel.externalsystems.sap.mapping.model.bpartner.BPartnerRow;
import de.metas.camel.externalsystems.sap.sftp.SFTPConfig;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI;

public class GetBPartnersSFTPRouteBuilder extends RouteBuilder
{
	@VisibleForTesting
	public static final String UPSERT_BPARTNER_ENDPOINT_ID = "SAP-BPartners-upsertBPartnerEndpointId";
	private static final String UPSERT_BPARTNER_PROCESSOR_ID = "SAP-BPartners-upsertBPartnerProcessorId";

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
				.unmarshal(new BindyCsvDataFormat(BPartnerRow.class))
				.split(body())
					.process(new BPartnerUpsertProcessor(enabledByExternalSystemRequest, processLogger)).id(UPSERT_BPARTNER_PROCESSOR_ID)
					.choice()
						.when(body().isNull())
						.log(LoggingLevel.INFO, "Nothing to do! No business partner to upsert!")
					.otherwise()
						.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert Business Partner: ${body}")
						.to("{{" + MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}").id(UPSERT_BPARTNER_ENDPOINT_ID)
					.endChoice()
					.end();
		//@formatter:on
	}

	@NonNull
	public static String buildRouteId(@NonNull final String externalSystemConfigValue)
	{
		return GetBPartnersSFTPRouteBuilder.class.getSimpleName() + "#" + externalSystemConfigValue;
	}
}
