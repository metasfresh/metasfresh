package de.metas.procurement.webui.event;

import com.google.gwt.thirdparty.guava.common.eventbus.EventBus;
import com.google.gwt.thirdparty.guava.common.eventbus.SubscriberExceptionContext;
import com.google.gwt.thirdparty.guava.common.eventbus.SubscriberExceptionHandler;

import de.metas.procurement.webui.MFProcurementUI;

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

public class UIEventBus implements SubscriberExceptionHandler
{
	private final EventBus eventBus = new EventBus(this);

	public static void post(final Object event)
	{
		getCurrent().eventBus.post(event);
	}

	public static void register(final Object object)
	{
		getCurrent().eventBus.register(object);
	}

	public static void unregister(final Object object)
	{
		getCurrent().eventBus.unregister(object);
	}
	
	public static UIEventBus getCurrent()
	{
		return MFProcurementUI.getCurrentEventBus();
	}

	@Override
	public final void handleException(final Throwable exception, final SubscriberExceptionContext context)
	{
		exception.printStackTrace();
	}
}
