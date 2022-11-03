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

package de.metas.camel.externalsystems.sap.creditlimit;

import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.sap.api.model.creditlimit.CreditLimitRow;
import de.metas.camel.externalsystems.sap.sftp.SFTPConfig;
import de.metas.common.bpartner.v2.request.creditLimit.JsonRequestCreditLimitDelete;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;

import static de.metas.camel.externalsystems.sap.SAPConstants.ROUTE_PROPERTY_CREDIT_LIMIT_ROUTE_CONTEXT;
import static de.metas.camel.externalsystems.sap.creditlimit.UpsertCreditLimitRouteBuilder.UPSERT_CREDIT_LIMIT_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

public class GetCreditLimitsSFTPRouteBuilder extends RouteBuilder
{
	private static final String PROCESS_CREDIT_LIMIT_ROW_PROCESSOR_ID = "SAP-CreditLimit-processCreditLimitRowProcessorId";

	private static final String PROCESS_LAST_CREDIT_LIMIT_GROUP_PROCESSOR_ID = "SAP-CreditLimit-processLastGroupProcessorId";

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
	private GetCreditLimitsSFTPRouteBuilder(
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
		from(sftpConfig.getSFTPConnectionString())
				.routeId(routeId)
				.log("CreditLimit Sync Route Started")
				.process(this::prepareCreditLimitContext)
				.split(body().tokenize("\n")).streaming()
				  .unmarshal(new BindyCsvDataFormat(CreditLimitRow.class))
				  .process(new CreditLimitUpsertProcessor(enabledByExternalSystemRequest, processLogger)).id(PROCESS_CREDIT_LIMIT_ROW_PROCESSOR_ID)
				  .choice()
				    .when(bodyAs(JsonRequestCreditLimitDelete.class).isNull())
				   	  .log(LoggingLevel.INFO, "Nothing to do! No credit limit to upsert!")
				    .otherwise()
					  .to(direct(UPSERT_CREDIT_LIMIT_ROUTE_ID))
					.end()
				.end()
				.process(CreditLimitUpsertProcessor::processLastCreditLimitGroup).id(PROCESS_LAST_CREDIT_LIMIT_GROUP_PROCESSOR_ID)
				.to(direct(UPSERT_CREDIT_LIMIT_ROUTE_ID))
				.end();
		// @formatter:on
	}

	private void prepareCreditLimitContext(@NonNull final Exchange exchange)
	{
		final CreditLimitContext context = CreditLimitContext.builder()
				.orgCode(enabledByExternalSystemRequest.getOrgCode())
				.creditLimitUpsertGroupBuilder(new SyncCreditLimitRequestBuilder())
				.build();

		exchange.setProperty(ROUTE_PROPERTY_CREDIT_LIMIT_ROUTE_CONTEXT, context);
	}

	@NonNull
	public static String buildRouteId(@NonNull final String externalSystemConfigValue)
	{
		return GetCreditLimitsSFTPRouteBuilder.class.getSimpleName() + "#" + externalSystemConfigValue;
	}
}
