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

package de.metas.camel.externalsystems.core.restapi.auth;

import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.core.restapi.auth.request.RequestWrapper;
import de.metas.common.rest_api.v2.seqno.JsonSeqNoResponse;
import lombok.NonNull;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RuntimeCamelException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_AUDIT_TRAIL;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_EXTERNAL_SYSTEM_VALUE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_TRACE_ID;

public class AuditTrailFilter implements Filter
{
	private final ProducerTemplate producerTemplate;

	public AuditTrailFilter(@NonNull final ProducerTemplate producerTemplate)
	{
		this.producerTemplate = producerTemplate;
	}

	@Override
	public void doFilter(
			@NonNull final ServletRequest servletRequest,
			@NonNull final ServletResponse servletResponse,
			@NonNull final FilterChain filterChain) throws IOException
	{
		final HttpServletRequest httpRequest = (HttpServletRequest)(servletRequest);
		final HttpServletResponse httpResponse = (HttpServletResponse)(servletResponse);

		try
		{
			final RequestWrapper requestWrapper = addAuditTrailHeaders(httpRequest);

			filterChain.doFilter(requestWrapper, httpResponse);
		}
		catch (final Exception exception)
		{
			SecurityContextHolder.clearContext();
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception.getMessage());
		}
	}

	@NonNull
	private RequestWrapper addAuditTrailHeaders(@NonNull final HttpServletRequest httpRequest)
	{
		final RequestWrapper requestWrapper = new RequestWrapper(httpRequest);

		final TokenCredentials credentials = (TokenCredentials)SecurityContextHolder.getContext().getAuthentication().getCredentials();

		if (credentials == null || credentials.getAuditTrailEndpoint() == null)
		{
			return requestWrapper;
		}

		final JsonSeqNoResponse jsonSeqNoResponse = (JsonSeqNoResponse)producerTemplate
				.sendBody("direct:" + ExternalSystemCamelConstants.MF_SEQ_NO_ROUTE_ID, ExchangePattern.InOut, null);

		if (jsonSeqNoResponse == null)
		{
			throw new RuntimeCamelException("Failed to retrieve traceId!");
		}

		requestWrapper.setHeader(HEADER_TRACE_ID, jsonSeqNoResponse.getSeqNo());
		requestWrapper.setHeader(HEADER_AUDIT_TRAIL, credentials.getAuditTrailEndpoint());
		requestWrapper.setHeader(HEADER_PINSTANCE_ID, credentials.getPInstance().toString());

		if (credentials.getExternalSystemValue() != null)
		{
			requestWrapper.setHeader(HEADER_EXTERNAL_SYSTEM_VALUE, credentials.getExternalSystemValue());
		}

		return requestWrapper;
	}
}
