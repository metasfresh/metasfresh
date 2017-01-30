package de.metas.ui.web.config;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.ui.web.login.exceptions.NotLoggedInException;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * An {@link HandlerExceptionResolver} implementation which logs all REST exceptions.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Component
// Order: IMPORTANT: because we want to call this handler before any other. Else, if it's the last one added, it might be that it will be never called
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionLogger implements HandlerExceptionResolver
{
	private static final transient Logger logger = LogManager.getLogger(RestExceptionLogger.class);

	@Value("${de.metas.ui.web.config.RestExceptionLogger.enabled:true}")
	private boolean enabled;
	
	private final Set<Class<?>> EXCEPTIONS_ExcludeFromLogging = ImmutableSet.of(NotLoggedInException.class);

	public RestExceptionLogger()
	{
		super();
	}

	@Override
	public ModelAndView resolveException(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex)
	{
		if (enabled)
		{
			if(isExcludeFromLogging(ex))
			{
				logger.debug("Got REST (excluded from logging) exception from handler={}", handler, ex);
			}
			else
			{
				logger.warn("Got REST exception from handler={}", handler, ex);
			}
		}

		return null; // no model => go forward with default processing
	}
	
	private final boolean isExcludeFromLogging(final Exception ex)
	{
		for (Class<?> exceptionClass : EXCEPTIONS_ExcludeFromLogging)
		{
			if(exceptionClass.isAssignableFrom(ex.getClass()))
			{
				return true;
			}
		}
		
		return false;
	}
}
