package de.metas.ui.web.vaadin.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

import de.metas.ui.web.vaadin.RootUI;

/*
 * #%L
 * test_vaadin
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

public class UIEventBus implements SubscriberExceptionHandler
{
	private final EventBus eventBus = new EventBus(this);

	public static void post(final Object event)
	{
		RootUI.getCurrentEventBus().eventBus.post(event);
	}

	public static void register(final Object object)
	{
		RootUI.getCurrentEventBus().eventBus.register(object);
	}

	public static void unregister(final Object object)
	{
		RootUI.getCurrentEventBus().eventBus.unregister(object);
	}

	@Override
	public final void handleException(final Throwable exception, final SubscriberExceptionContext context)
	{
		exception.printStackTrace();
	}
}
