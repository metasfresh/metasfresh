/*
 * #%L
 * de-metas-camel-externalsystems-core
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

package de.metas.camel.externalsystems.core;

import com.sun.istack.Nullable;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.EmptyUtil;
import de.metas.common.util.FileUtil;
import lombok.NonNull;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.spi.CamelEvent;
import org.apache.camel.support.EventNotifierSupport;
import org.apache.camel.support.ExchangeHelper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_AUDIT_TRAIL;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_EXTERNAL_SYSTEM_VALUE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_TRACE_ID;
import static de.metas.camel.externalsystems.core.to_mf.ErrorReportRouteBuilder.ERROR_WRITE_TO_ADISSUE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.file;

public class AuditEventNotifier extends EventNotifierSupport
{
	private static final Logger logger = Logger.getLogger(AuditEventNotifier.class.getName());

	private final static String EP_INVOKED_PREFIX = "Endpoint invoked: ";
	private final static String EP_INVOCATION_DONE_PREFIX = "Endpoint invocation done: ";

	private final ProducerTemplate producerTemplate;

	public AuditEventNotifier(@NonNull final ProducerTemplate producerTemplate)
	{
		this.producerTemplate = producerTemplate;
	}

	@Override
	public void notify(@NonNull final CamelEvent event)
	{
		try
		{
			if (event instanceof CamelEvent.ExchangeSendingEvent)
			{
				final CamelEvent.ExchangeSendingEvent sendingEvent = (CamelEvent.ExchangeSendingEvent)event;
				final Exchange exchange = ExchangeHelper.createCopy(sendingEvent.getExchange(), false);

				handleEvent(exchange, EP_INVOKED_PREFIX + extractEndpointURI(sendingEvent.getEndpoint()));
			}
			else if (event instanceof CamelEvent.ExchangeSentEvent)
			{
				final CamelEvent.ExchangeSentEvent sentEvent = (CamelEvent.ExchangeSentEvent)event;
				final Exchange exchange = ExchangeHelper.createCopy(sentEvent.getExchange(), false);

				handleEvent(exchange, EP_INVOCATION_DONE_PREFIX + extractEndpointURI(sentEvent.getEndpoint()));
			}
		}
		catch (final Exception exception)
		{
			final Exchange sourceExchange = ((CamelEvent.ExchangeEvent)event).getExchange();

			if (sourceExchange == null
					|| sourceExchange.getIn() == null
					|| sourceExchange.getIn().getHeader(HEADER_PINSTANCE_ID) == null)
			{
				logger.log(Level.SEVERE, "Audit failed! and no pInstance could be obtained from source exchange!");
				return;
			}

			final Map<String, Object> headers = new HashMap<>();
			headers.put(HEADER_PINSTANCE_ID, sourceExchange.getIn().getHeader(HEADER_PINSTANCE_ID, Object.class));
			headers.put(Exchange.EXCEPTION_CAUGHT, exception);

			producerTemplate.sendBodyAndHeaders("direct:" + ERROR_WRITE_TO_ADISSUE, null, headers);
		}
	}

	private void handleEvent(@NonNull final Exchange exchange, @NonNull final String message)
	{
		final String auditTrailEndpoint = exchange.getIn().getHeader(HEADER_AUDIT_TRAIL, String.class);
		if (EmptyUtil.isEmpty(auditTrailEndpoint))
		{
			return;
		}

		final Optional<String> auditLogFileContent = AuditFileTrailUtil.computeAuditLogFileContent(exchange, message);
		if (auditLogFileContent.isEmpty())
		{
			return;
		}

		producerTemplate.sendBody(getAuditEndpoint(exchange), auditLogFileContent.get());
	}

	@NonNull
	private static String extractEndpointURI(@Nullable final Endpoint endpoint)
	{
		return endpoint != null && Check.isNotBlank(endpoint.getEndpointUri())
				? endpoint.getEndpointUri()
				: "[Could not obtain endpoint information]";
	}

	@NonNull
	private static String getAuditEndpoint(@NonNull final Exchange exchange)
	{
		final String auditTrailEndpoint = exchange.getIn().getHeader(HEADER_AUDIT_TRAIL, String.class);
		if (EmptyUtil.isEmpty(auditTrailEndpoint))
		{
			throw new RuntimeCamelException("auditTrailEndpoint cannot be empty at this point!");

		}

		final String externalSystemValue = exchange.getIn().getHeader(HEADER_EXTERNAL_SYSTEM_VALUE, String.class);

		final String value = FileUtil.stripIllegalCharacters(externalSystemValue);

		final String traceId = CoalesceUtil.coalesceNotNull(exchange.getIn().getHeader(HEADER_TRACE_ID, String.class), UUID.randomUUID().toString());

		final String auditFolderName = DateTimeFormatter.ofPattern("yyyy-MM-dd")
				.withZone(ZoneId.systemDefault())
				.format(LocalDate.now());

		final String auditFileNameTimeStamp = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmssSSS")
				.withZone(ZoneId.systemDefault())
				.format(Instant.now());

		final String auditFileName = traceId + "_" + value + "_" + auditFileNameTimeStamp + ".txt";

		return file(auditTrailEndpoint + "/" + auditFolderName + "/?fileName=" + auditFileName).getUri();
	}
}
