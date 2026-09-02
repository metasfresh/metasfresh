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

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.PROPERTY_SCRIPTED_IMPORT_ORIGINAL_PAYLOAD;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

public class ScriptedImportConversionDynamicRouteBuilder extends AbstractScriptedImportConversionArchivingRouteBuilder
{
	private static final DateTimeFormatter ARCHIVE_FILE_TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmssSSS");

	public static final String SCRIPTED_IMPORT_CONVERSION_PROCESSOR_ID = "ScriptedImportConversionProcessorId";

	public ScriptedImportConversionDynamicRouteBuilder(
			@NonNull final String endpointName,
			@NonNull final String scriptIdentifier,
			@NonNull final JavaScriptRepo javaScriptRepo,
			@NonNull final JavaScriptExecutorService javaScriptExecutorService,
			@NonNull final ProducerTemplate producerTemplate,
			@NonNull final String processedDir,
			@NonNull final String errorDir)
	{
		super(endpointName, scriptIdentifier, javaScriptRepo, javaScriptExecutorService, producerTemplate, processedDir, errorDir);
	}

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.process(this::archiveLocallyOnError)
				.to(direct(MF_ERROR_ROUTE_ID));

		//@formatter:off
		from("direct:" + getRouteId())
				.routeId(getRouteId())
				.group(CamelRoutesGroup.START_ON_DEMAND.getCode())
				.convertBodyTo(String.class)
				.setProperty(PROPERTY_SCRIPTED_IMPORT_ORIGINAL_PAYLOAD, body())
				.process(new ScriptedImportConversionProcessor(javaScriptExecutorService, scriptIdentifier, javaScriptRepo)).id(SCRIPTED_IMPORT_CONVERSION_PROCESSOR_ID)
				.choice()
					.when(body().isNull())
						.log(LoggingLevel.INFO, "Nothing to process for ${routeId}")
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
		return ARCHIVE_FILE_TIMESTAMP_FORMATTER.format(ZonedDateTime.now()) + "_" + endpointName + ".json";
	}

	@NonNull
	private String getRouteId()
	{
		return endpointName;
	}
}
