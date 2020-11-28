package de.metas.vertical.pharma.msv3.server;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.AbstractEndpointExceptionResolver;

/*
 * #%L
 * metasfresh-pharma.msv3.server
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class LoggingWSEndpointExceptionResolver extends AbstractEndpointExceptionResolver
{
	@Value("${msv3.server.ws.logEndpointFailures:true}")
	private boolean enabled;

	public LoggingWSEndpointExceptionResolver()
	{
	}

	@PostConstruct
	private void postConstruct()
	{
		if (enabled)
		{
			setWarnLogCategory(LoggingWSEndpointExceptionResolver.class.getSimpleName());
		}
	}

	@Override
	protected boolean resolveExceptionInternal(final MessageContext messageContext, final Object endpoint, final Exception ex)
	{
		return false;
	}
}
