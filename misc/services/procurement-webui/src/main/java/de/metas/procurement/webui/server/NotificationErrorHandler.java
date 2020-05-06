package de.metas.procurement.webui.server;

import com.google.gwt.thirdparty.guava.common.base.Preconditions;
import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

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
 * {@link ErrorHandler} which displays the errors as notifications.
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
@SuppressWarnings("serial")
public class NotificationErrorHandler extends AbstractErrorHandler
{
	public static final NotificationErrorHandler of(final Notification.Type defaultType)
	{
		return new NotificationErrorHandler(defaultType);
	}

	private final Type defaultType;

	private NotificationErrorHandler(final Type defaultType)
	{
		super();
		this.defaultType = Preconditions.checkNotNull(defaultType);
	}

	@Override
	protected void displayError(final ErrorMessage errorMessage, final ErrorEvent event)
	{
		final Notification notification = new Notification("", errorMessage.getFormattedHtmlMessage(), defaultType, true);
		notification.show(Page.getCurrent());
	}

}
