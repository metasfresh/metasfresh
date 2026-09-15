/*
 * #%L
 * de-metas-camel-scriptedadapter
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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf;

import de.metas.camel.externalsystems.common.CamelRoutesGroup;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorService;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.processor.ScriptedImportConversionProcessor;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ProducerTemplate;

import java.util.Optional;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.PROPERTY_SCRIPTED_IMPORT_ORIGINAL_PAYLOAD;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

public class ScriptedImportConversionSftpDynamicRouteBuilder extends AbstractScriptedImportConversionArchivingRouteBuilder
{
	@NonNull private final String sftpUri;

	/**
	 * Stable per-child route id. Keyed on the child config id (never on the endpoint host/{@code Value}),
	 * so enable/disable can find and tear down the running poller even after the child's endpoint changed.
	 */
	@NonNull private final String routeKey;

	public ScriptedImportConversionSftpDynamicRouteBuilder(
			@NonNull final String routeKey,
			@NonNull final String endpointName,
			@NonNull final String sftpUri,
			@NonNull final String scriptIdentifier,
			@NonNull final JavaScriptRepo javaScriptRepo,
			@NonNull final JavaScriptExecutorService javaScriptExecutorService,
			@NonNull final ProducerTemplate producerTemplate,
			@NonNull final String processedDir,
			@NonNull final String errorDir)
	{
		super(endpointName, scriptIdentifier, javaScriptRepo, javaScriptExecutorService, producerTemplate, processedDir, errorDir);
		this.routeKey = routeKey;
		this.sftpUri = sftpUri;
	}

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		// handled(true): the remote file is consumed (deleted, via the sftpUri's delete=true option)
		// regardless of whether the transform succeeds or fails — marking the exception handled here
		// keeps Camel's file-consumer "commit" path (which performs the delete) on the failure path too,
		// instead of leaving the file in place to be re-polled forever.
		onException(Exception.class)
				.handled(true)
				.process(this::archiveLocallyOnError)
				.to(direct(MF_ERROR_ROUTE_ID));

		//@formatter:off
		from(sftpUri)
				.routeId(routeKey)
				.group(CamelRoutesGroup.START_ON_DEMAND.getCode())
				.log("SFTP file received: ${header.CamelFileName}")
				.convertBodyTo(String.class)
				.setProperty(PROPERTY_SCRIPTED_IMPORT_ORIGINAL_PAYLOAD, body())
				.process(new ScriptedImportConversionProcessor(javaScriptExecutorService, scriptIdentifier, javaScriptRepo))
				.choice()
					.when(body().isNull())
						.log(LoggingLevel.INFO, "Nothing to process for ${header.CamelFileName}")
					.otherwise()
						.split(body(), new ResponseAggregationStrategy())
							.stopOnException()
							.process(this::handleItemInList)
						.end()
					.endChoice()
				.end()
				.process(this::archiveLocallyOnSuccess);
		//@formatter:on
	}

	@Override
	protected String archiveFileName(@NonNull final Exchange exchange)
	{
		return Optional.ofNullable(exchange.getIn().getHeader(Exchange.FILE_NAME, String.class))
				.orElseGet(() -> endpointName + "_" + System.currentTimeMillis());
	}
}
