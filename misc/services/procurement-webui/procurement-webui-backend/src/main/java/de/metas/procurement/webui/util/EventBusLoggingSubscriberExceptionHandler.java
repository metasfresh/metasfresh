package de.metas.procurement.webui.util;

import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import lombok.NonNull;
import org.slf4j.Logger;

/*
 * #%L
 * de.metas.procurement.webui
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
 * {@link SubscriberExceptionHandler} implementation which logs the exception to a given logger.
 * <p>
 * NOTE: the reason why we are not using the default handler which is doing almost the same thing is because in guava versions until 19 inclusive, this is buggy and it's actually not logging the
 * exception (i.e. logs exception.getCause() which can be null). In recent versions it seems to be fixed.
 *
 * @author metas-dev <dev@metas-fresh.com>
 */
public class EventBusLoggingSubscriberExceptionHandler implements SubscriberExceptionHandler
{
	public static EventBusLoggingSubscriberExceptionHandler of(@NonNull final Logger logger)
	{
		return new EventBusLoggingSubscriberExceptionHandler(logger);
	}

	private final Logger logger;

	private EventBusLoggingSubscriberExceptionHandler(@NonNull final Logger logger)
	{
		this.logger = logger;
	}

	@Override
	public void handleException(final Throwable exception, final SubscriberExceptionContext context)
	{
		logger.error("Could not dispatch event: {} to {}", context.getSubscriber(), context.getSubscriberMethod(), exception);
	}
}
