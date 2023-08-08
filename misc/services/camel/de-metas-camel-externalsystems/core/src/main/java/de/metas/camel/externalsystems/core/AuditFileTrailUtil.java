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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.common.util.Check;
import de.metas.common.util.EmptyUtil;
import de.metas.common.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.converter.stream.InputStreamCache;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static de.metas.camel.externalsystems.core.CoreConstants.AUDIT_SENSITIVE_DATA_PATTERN_DEFAULT;
import static de.metas.camel.externalsystems.core.CoreConstants.AUDIT_SENSITIVE_DATA_PATTERN_DEFAULT_GROUP;
import static de.metas.camel.externalsystems.core.CoreConstants.AUDIT_SENSITIVE_DATA_PATTERN_GROUP_PROPERTY;
import static de.metas.camel.externalsystems.core.CoreConstants.AUDIT_SENSITIVE_DATA_PATTERN_PROPERTY;

@UtilityClass
public class AuditFileTrailUtil
{
	@NonNull
	public Optional<String> computeAuditLogFileContent(@NonNull final Exchange exchange, @NonNull final String message)
	{
		final StringBuilder fileContent = new StringBuilder();

		computeExchangeHeadersToString(exchange).ifPresent(fileContent::append);

		computeExchangeBodyToString(exchange, message).ifPresent(fileContent::append);

		return Optional.ofNullable(StringUtils.trimBlankToNull(fileContent.toString()))
				.map(content -> maskSensitiveData(exchange.getContext(), content));
	}

	@NonNull
	private Optional<String> computeExchangeBodyToString(@NonNull final Exchange exchange, @NonNull final String message)
	{
		final Object body = exchange.getIn().getBody(Object.class);
		if (body == null || EmptyUtil.isEmpty(body))
		{
			return Optional.empty();
		}

		try
		{
			if (body instanceof byte[])
			{
				final byte[] bodyToByteArray = (byte[])body;
				final String byteArrayToString = new String(bodyToByteArray, StandardCharsets.UTF_8);

				return getFileContent(message, byteArrayToString);
			}
			else if (body instanceof InputStreamCache)
			{
				final InputStreamCache inputStream = (InputStreamCache)body;

				final InputStream copiedStreamCache = (InputStream)inputStream.copy(exchange);

				final String streamCacheToString = new BufferedReader(new InputStreamReader(copiedStreamCache, StandardCharsets.UTF_8))
						.lines()
						.collect(Collectors.joining("\n"));

				return getFileContent(message, streamCacheToString);
			}
			else if (body instanceof Message)
			{
				final Message bodyMessage = (Message)body;
				final Object messageBody = bodyMessage.getBody();

				if (messageBody == null)
				{
					return Optional.empty();
				}

				return getFileContent(message, String.valueOf(messageBody));
			}
			else if (body instanceof String)
			{
				return getFileContent(message, (String)body);
			}
			else
			{
				final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
				final String bodyToString = objectMapper.writeValueAsString(body);

				return getFileContent(message, bodyToString);
			}
		}
		catch (final Exception e)
		{
			return Optional.of(getErrorMessage(e, body, message));
		}
	}

	@NonNull
	private Optional<String> getFileContent(@NonNull final String message, @Nullable final String bodyContent)
	{
		if (Check.isEmpty(bodyContent))
		{
			return Optional.empty();
		}

		final String fileContent = message
				+ ";\n ========================= body ======================== \n"
				+ bodyContent + "\n";

		return Optional.of(fileContent);
	}

	@NonNull
	private String getErrorMessage(@NonNull final Exception ex, @NonNull final Object body, @NonNull final String message)
	{
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);

		return message
				+ "\n Got exception while parsing exchange.body of type " + body.getClass() + " for the audit-trail"
				+ ";\n ========================= blindly converting body toString(); body: ======================== \n"
				+ body.toString()
				+ ";\n ========================= error stack trace ======================== \n"
				+ ex.getLocalizedMessage() + "\n";
	}

	@NonNull
	private String maskSensitiveData(@NonNull final CamelContext context, @NonNull final String messageToMask)
	{
		String message = messageToMask;

		final String sensitiveDataPattern = CamelRouteUtil.resolveProperty(context, AUDIT_SENSITIVE_DATA_PATTERN_PROPERTY, AUDIT_SENSITIVE_DATA_PATTERN_DEFAULT);
		final int sensitiveDataPatternGroup = Integer.parseInt(CamelRouteUtil.resolveProperty(context, AUDIT_SENSITIVE_DATA_PATTERN_GROUP_PROPERTY, AUDIT_SENSITIVE_DATA_PATTERN_DEFAULT_GROUP));

		final Pattern pattern = Pattern.compile(sensitiveDataPattern, Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(message);

		while (matcher.find())
		{
			final String segmentToBeMasked = matcher.group(sensitiveDataPatternGroup);
			final String maskedSegment = segmentToBeMasked.replaceAll(".", "x");
			message = message.replace(segmentToBeMasked, maskedSegment);
		}

		return message;
	}

	@NonNull
	private Optional<String> computeExchangeHeadersToString(@NonNull final Exchange exchange)
	{
		return getHeaders(exchange).map(AuditFileTrailUtil::auditHeaders);
	}

	@NonNull
	private Optional<Map<String, Object>> getHeaders(@NonNull final Exchange exchange)
	{
		if (exchange.getIn().getHeaders() == null)
		{
			return Optional.empty();
		}

		final Map<String, Object> headers = exchange.getIn().getHeaders().entrySet()
				.stream()
				.filter(entry -> !Exchange.HTTP_SERVLET_REQUEST.equals(entry.getKey()))
				.collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));

		if (headers.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(headers);
	}

	@NonNull
	private String auditHeaders(@NonNull final Map<String, Object> headers)
	{
		final StringBuilder headersStringCollector = new StringBuilder(" ========================= Headers ======================== \n");

		try
		{
			final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

			return headersStringCollector
					.append(objectMapper.writeValueAsString(headers))
					.append("\n")
					.toString();

		}
		catch (final Exception e)
		{
			return headersStringCollector.append(getHeadersErrorMessage(e, headers)).toString();
		}
	}

	@NonNull
	private String getHeadersErrorMessage(@NonNull final Exception exception, @NonNull final Map<String, Object> headers)
	{
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);

		return "\n Got exception while parsing request headers \n"
				+ ";\n ========================= blindly converting headers toString(); headers: ======================== \n"
				+ headers.toString()
				+ ";\n ========================= error stack trace - start ======================== \n"
				+ exception.getLocalizedMessage()
				+ ";\n ========================= error stack trace - end ======================== \n";
	}
}
