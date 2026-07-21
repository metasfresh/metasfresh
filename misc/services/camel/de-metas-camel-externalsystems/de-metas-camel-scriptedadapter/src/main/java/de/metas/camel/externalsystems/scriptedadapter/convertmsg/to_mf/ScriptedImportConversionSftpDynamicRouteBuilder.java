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
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorService;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.model.CamelServiceRouteIdWithRequestType;
import de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.model.ScriptedImportedConversionToMfRequest;
import de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.processor.ScriptedImportConversionProcessor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.base.HttpOperationFailedException;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.EXCEPTION_PREFIX;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.PROPERTY_SCRIPTED_IMPORT_ORIGINAL_PAYLOAD;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Slf4j
@RequiredArgsConstructor
public class ScriptedImportConversionSftpDynamicRouteBuilder extends RouteBuilder
{

	@NonNull private final String endpointName;
	@NonNull private final String sftpUri;
	@NonNull private final String scriptIdentifier;
	@NonNull private final JavaScriptRepo javaScriptRepo;
	@NonNull private final JavaScriptExecutorService javaScriptExecutorService;
	@NonNull private final ProducerTemplate producerTemplate;

	/** LOCAL, transport-agnostic archive folder for the payload on success. Never a remote path. */
	@NonNull private final String processedDir;
	/** LOCAL, transport-agnostic archive folder for the payload on failure. Never a remote path. */
	@NonNull private final String errorDir;

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
				.routeId(endpointName)
				.group(CamelRoutesGroup.START_ON_DEMAND.getCode())
				.log("SFTP file received: ${header.CamelFileName}")
				.convertBodyTo(String.class)
				.setProperty(PROPERTY_SCRIPTED_IMPORT_ORIGINAL_PAYLOAD, body())
				.process(new ScriptedImportConversionProcessor(javaScriptExecutorService, scriptIdentifier, javaScriptRepo))
				.choice()
					.when(body().isNull())
						.log(LoggingLevel.INFO, "Nothing to process for ${header.CamelFileName}")
					.otherwise()
						.split(body(), new ScriptedImportConversionDynamicRouteBuilder.ResponseAggregationStrategy())
							.stopOnException()
							.process(this::handleItemInList)
						.end()
					.endChoice()
				.end()
				.process(this::archiveLocallyOnSuccess);
		//@formatter:on
	}

	private void archiveLocallyOnSuccess(@NonNull final Exchange exchange)
	{
		archiveLocally(exchange, processedDir);
	}

	private void archiveLocallyOnError(@NonNull final Exchange exchange)
	{
		archiveLocally(exchange, errorDir);
	}

	private void archiveLocally(@NonNull final Exchange exchange, @NonNull final String directory)
	{
		final String payload = exchange.getProperty(PROPERTY_SCRIPTED_IMPORT_ORIGINAL_PAYLOAD, String.class);
		if (payload == null)
		{
			// nothing was ever read from the remote file (failure occurred before the body was captured)
			return;
		}

		final String fileName = Optional.ofNullable(exchange.getIn().getHeader(Exchange.FILE_NAME, String.class))
				.orElseGet(() -> endpointName + "_" + System.currentTimeMillis());

		ScriptedImportConversionLocalArchiver.archive(directory, fileName, payload);
	}

	private void handleItemInList(@NonNull final Exchange exchange)
	{
		final ScriptedImportedConversionToMfRequest request = exchange.getIn().getBody(ScriptedImportedConversionToMfRequest.class);

		try
		{
			final CamelServiceRouteIdWithRequestType camelRouteIdWithRequestType = CamelServiceRouteIdWithRequestType.ofRouteId(request.getCamelServiceRouteID());
			final Object payload = JsonObjectMapperHolder.sharedJsonObjectMapper()
					.readValue(request.getRequestBody(), camelRouteIdWithRequestType.getRequestType());

			final String response = producerTemplate.requestBody(resolveCamelEndpointUri(camelRouteIdWithRequestType), payload, String.class);
			exchange.getMessage().setBody(response);
		}
		catch (final Exception e)
		{
			log.warn("Exception caught when handling request: {}", request, e);
			exchange.getMessage().setBody(getErrorMessage(e));
		}
	}

	@NonNull
	private String getErrorMessage(@NonNull final Exception e)
	{
		return Optional.ofNullable(e.getCause())
				.map(root -> {
					if (root instanceof HttpOperationFailedException httpOperationFailedException)
					{
						return httpOperationFailedException.getResponseBody();
					}
					return EXCEPTION_PREFIX + root.getMessage();
				})
				.orElse(EXCEPTION_PREFIX + e.getMessage());
	}

	@NonNull
	private String resolveCamelEndpointUri(@NonNull final CamelServiceRouteIdWithRequestType camelRouteIdWithRequestType)
	{
		if (camelRouteIdWithRequestType.isProperty())
		{
			return Optional.ofNullable(getCamelContext().resolvePropertyPlaceholders("{{" + camelRouteIdWithRequestType.getRouteId() + "}}"))
					.orElseThrow(() -> new RuntimeCamelException("Missing property: " + camelRouteIdWithRequestType.getRouteId()));
		}
		else
		{
			return "direct:" + camelRouteIdWithRequestType.getRouteId();
		}
	}
}
