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

package de.metas.camel.externalsystems.core.authorizationmf;

import com.sun.istack.Nullable;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.core.CoreConstants;
import de.metas.camel.externalsystems.core.authorizationmf.provider.MetasfreshAuthProvider;
import lombok.NonNull;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spi.CamelEvent;
import org.apache.camel.support.EventNotifierSupport;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.ERROR_WRITE_TO_ADISSUE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;

public class MetasfreshAuthorizationTokenNotifier extends EventNotifierSupport
{
	private final ApplicationContext context;
	private static final Logger logger = Logger.getLogger(MetasfreshAuthorizationTokenNotifier.class.getName());

	private final ProducerTemplate producerTemplate;

	private final String defaultAuthToken;

	public MetasfreshAuthorizationTokenNotifier(@NonNull final ApplicationContext context, @NonNull final ProducerTemplate producerTemplate, @Nullable final String defaultAuthToken)
	{
		this.producerTemplate = producerTemplate;
		this.defaultAuthToken = defaultAuthToken;
		this.context = context;
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

	private void handleSendingEvent(@NonNull final Endpoint endpoint, @NonNull final Exchange exchange)
	{
		final MetasfreshAuthProvider authProvider = context.getBean(MetasfreshAuthProvider.class);
		if (endpoint.getEndpointUri() != null && context.getEnvironment().getProperty(ExternalSystemCamelConstants.MF_API_BASE_URL_PROPERTY).contains(endpoint.getEndpointUri()))
		{
			if (authProvider.getAuthToken() != null)
			{
				exchange.getIn().setHeader(CoreConstants.AUTHORIZATION, authProvider.getAuthToken());
			}
			else
			{
				exchange.getIn().setHeader(CoreConstants.AUTHORIZATION, defaultAuthToken);
			}
		}
	}

}