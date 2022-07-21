/*
 * #%L
 * de-metas-camel-externalsystems-core
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

package de.metas.camel.externalsystems.core.authorization;

import de.metas.camel.externalsystems.core.CoreConstants;
import de.metas.camel.externalsystems.core.CustomRouteController;
import de.metas.camel.externalsystems.core.authorization.provider.MetasfreshAuthProvider;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.spi.CamelEvent;
import org.apache.camel.support.EventNotifierSupport;

import java.util.logging.Level;
import java.util.logging.Logger;

import static de.metas.camel.externalsystems.core.authorization.CustomMessageToMFRouteBuilder.CUSTOM_TO_MF_ROUTE_ID;

public class MetasfreshAuthorizationTokenNotifier extends EventNotifierSupport
{
	private final Logger logger = Logger.getLogger(MetasfreshAuthorizationTokenNotifier.class.getName());

	@NonNull
	private final MetasfreshAuthProvider metasfreshAuthProvider;
	@NonNull
	private final String metasfreshAPIURL;
	@NonNull
	private final CustomRouteController customRouteController;
	@NonNull
	private final ProducerTemplate producerTemplate;

	public MetasfreshAuthorizationTokenNotifier(
			@NonNull final MetasfreshAuthProvider metasfreshAuthProvider,
			@NonNull final String metasfreshAPIURL,
			@NonNull final CustomRouteController customRouteController,
			@NonNull final ProducerTemplate producerTemplate
	)
	{
		this.metasfreshAuthProvider = metasfreshAuthProvider;
		this.metasfreshAPIURL = metasfreshAPIURL;
		this.customRouteController = customRouteController;
		this.producerTemplate = producerTemplate;
	}

	@Override
	public void notify(@NonNull final CamelEvent event)
	{
		try
		{
			if (event instanceof CamelEvent.ExchangeSendingEvent)
			{
				handleSendingEvent((CamelEvent.ExchangeSendingEvent)event);
			}
			else if (event instanceof CamelEvent.ExchangeSentEvent)
			{
				handleSentEvent((CamelEvent.ExchangeSentEvent)event);
			}
		}
		catch (final Exception exception)
		{
			logger.log(Level.SEVERE, "Exception caught while trying to attach metasfresh Authorization header!");
		}
	}

	private void handleSendingEvent(@NonNull final CamelEvent.ExchangeSendingEvent event)
	{
		final Endpoint endpoint = event.getEndpoint();

		if (endpoint.getEndpointUri() == null || !metasfreshAPIURL.contains(endpoint.getEndpointUri()))
		{
			return;
		}

		final String authToken = metasfreshAuthProvider.getAuthToken();

		if (Check.isBlank(authToken))
		{
			throw new RuntimeCamelException("No authorization token provided by MetasfreshAuthProvider!");
		}

		event.getExchange().getIn().setHeader(CoreConstants.AUTHORIZATION, authToken);
	}

	private void handleSentEvent(@NonNull final CamelEvent.ExchangeSentEvent sentEvent)
	{
		final Endpoint endpoint = sentEvent.getEndpoint();

		if (endpoint.getEndpointUri() == null || !metasfreshAPIURL.contains(endpoint.getEndpointUri()))
		{
			return;
		}

		final Exception exception = sentEvent.getExchange().getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

		if (!(exception instanceof HttpOperationFailedException))
		{
			return;
		}

		final HttpOperationFailedException httpOperationFailedException = (HttpOperationFailedException)exception;

		if (httpOperationFailedException.getStatusCode() == 401)
		{
			this.customRouteController.stopAllRoutes();
			producerTemplate.sendBody("direct:" + CUSTOM_TO_MF_ROUTE_ID, "Trigger external system authentication for metasfresh!");
		}
	}
}