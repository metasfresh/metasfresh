package org.compiere.server.exception;

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;

import de.metas.i18n.IMsgBL;

/**
 * This exception is used if an error occurs during execution of an ADempiere server process.
 * 
 * @author ts
 * @see http://dewiki908/mediawiki/index.php/03034:_ADempiere_ServerProcesses_can_die_%282012072510000033%29
 */
public class ServerThreadException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1395917089148875113L;

	private static final String SERVER_THREAD_EXCEPTION_MESSAGE = "ServerThreadException_Msg";
	// private static final String MSG_SERVER_THREAD_EXCEPTION_ERR = "ServerThreadException_Err";

	private final String adempiereProcessorName;

	/**
	 * 
	 * @param localizedMsg a message that has been created by {@link IMsgBL}, i.e. that has already been localized and can be displayed as-is.
	 * @param cause
	 */
	public ServerThreadException(final String adempiereProcessorName, final Throwable cause)
	{
		super(cause);
		this.adempiereProcessorName = adempiereProcessorName;
		setParseTranslation(false);
	}

	@Override
	protected String buildMessage()
	{
		final Throwable cause = getCause();
		final String causeMessage = cause == null ? "unknown cause" : cause.getLocalizedMessage();

		final IMsgBL msgBL = Services.get(IMsgBL.class);
		return msgBL.getMsg(getADLanguage(),
				SERVER_THREAD_EXCEPTION_MESSAGE,
				new Object[] { adempiereProcessorName, causeMessage });
	}
}
