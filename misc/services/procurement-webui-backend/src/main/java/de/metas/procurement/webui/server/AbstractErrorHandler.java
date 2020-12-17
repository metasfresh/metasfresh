package de.metas.procurement.webui.server;

import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;

import com.vaadin.server.AbstractErrorMessage;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.SystemError;
import com.vaadin.server.UserError;

import de.metas.procurement.webui.Application;

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
 * Base class for implementing specific {@link ErrorHandler}s.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractErrorHandler implements com.vaadin.server.ErrorHandler
{
	@Autowired
    private I18N i18n;

	private final transient Logger logger = LoggerFactory.getLogger(getClass());
	
	public AbstractErrorHandler()
	{
		super();
		Application.autowire(this);
	}

	@Override
	public final void error(final ErrorEvent event)
	{
		//
		// Extract the relevant exception
		Throwable t = event.getThrowable();
		if (t instanceof SocketException)
		{
			// Most likely client browser closed socket
			logger.info("SocketException in CommunicationManager. Most likely client (browser) closed socket.");
			return;
		}
		t = DefaultErrorHandler.findRelevantThrowable(t);

		//
		// Create error message
		final ErrorMessage errorMessage = createErrorMessage(t);

		//
		// Display it
		displayError(errorMessage, event);

		// also print the error on console
		logger.error("", t);
	}

	/**
	 * Displays the given error message.
	 * 
	 * @param errorMessage error message to be displayed
	 * @param event original error event
	 */
	protected abstract void displayError(final ErrorMessage errorMessage, final ErrorEvent event);

	private ErrorMessage createErrorMessage(final Throwable t)
	{
		final ErrorMessage errorMessage = AbstractErrorMessage.getErrorMessageForException(t);

		// In case we deal with an System Error, don't show it to user because it's confusing.
		// Instead show a generic friendly error message.
		if (errorMessage instanceof SystemError)
		{
			return new UserError(i18n.get("error.default"));
		}

		return errorMessage;
	}
}
