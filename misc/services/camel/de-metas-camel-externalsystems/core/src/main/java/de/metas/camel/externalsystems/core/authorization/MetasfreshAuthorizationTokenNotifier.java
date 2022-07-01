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
import de.metas.common.util.EmptyUtil;
import lombok.NonNull;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spi.CamelEvent;
import org.apache.camel.support.EventNotifierSupport;

import static de.metas.camel.externalsystems.core.authorization.CustomMessageToMFRouteBuilder.CUSTOM_TO_MF_ROUTE_ID;

public class MetasfreshAuthorizationTokenNotifier extends EventNotifierSupport
{
	private final MetasfreshAuthProvider metasfreshAuthProvider;
	private final String baseUrlPropertyValue;
	private final CustomRouteController customRouteController;
	private final ProducerTemplate producerTemplate;

	public MetasfreshAuthorizationTokenNotifier(
			@NonNull final MetasfreshAuthProvider metasfreshAuthProvider,
			@NonNull final String baseUrlPropertyValue,
			@NonNull final CustomRouteController customRouteController,
			@NonNull final ProducerTemplate producerTemplate
	)
	{
		this.metasfreshAuthProvider = metasfreshAuthProvider;
		this.baseUrlPropertyValue = baseUrlPropertyValue;
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
				final CamelEvent.ExchangeSendingEvent sendingEvent = (CamelEvent.ExchangeSendingEvent)event;

				handleSendingEvent(sendingEvent.getEndpoint(), sendingEvent.getExchange());
			}
		}
		catch (final Exception exception)
		{
			customRouteController.stopAllRoutes(((CamelEvent.ExchangeSentEvent)event).getExchange());

			try
			{
				customRouteController.startAuthRoutes(((CamelEvent.ExchangeSentEvent)event).getExchange().getContext().getRouteController());

				producerTemplate.sendBody("direct:" + CUSTOM_TO_MF_ROUTE_ID, "trigger external system authentication for metasfresh!");
			}
			catch (final Exception e)
			{
				throw new RuntimeException("Failed to start custom authorization routes!", e);
			}
		}
	}

	private void handleSendingEvent(@NonNull final Endpoint endpoint, @NonNull final Exchange exchange)
	{
		if (endpoint.getEndpointUri() != null && baseUrlPropertyValue.contains(endpoint.getEndpointUri()))
		{
			if (EmptyUtil.isNotBlank(metasfreshAuthProvider.getAuthToken()))
			{
				exchange.getIn().setHeader(CoreConstants.AUTHORIZATION, metasfreshAuthProvider.getAuthToken());
			}
			else if (EmptyUtil.isNotBlank(metasfreshAuthProvider.getPropertiesAuthToken()))
			{
				exchange.getIn().setHeader(CoreConstants.AUTHORIZATION, metasfreshAuthProvider.getPropertiesAuthToken());
			}
		}
	}

}