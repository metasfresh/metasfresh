package de.metas.device.scales.impl;

import org.adempiere.util.StringUtils;

/*
 * #%L
 * de.metas.device.scales
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


import de.metas.device.api.DeviceException;

/**
 * Exception to be thrown by {@link IParser} implementations in case of errors
 *
 * @author ts
 *
 */
public class ParserException extends DeviceException
{
	/**
	 *
	 */
	private static final long serialVersionUID = 6780262709879240414L;

	/**
	 * Create a new exception. The parameters match the method parameters of {@link IParser#parse(ICmd, String, String, Class)}.
	 *
	 * @param cmd
	 * @param stringToParse
	 * @param elementName
	 * @param clazz
	 * @param cause
	 */
	public ParserException(final ICmd cmd,
			final String stringToParse,
			final String elementName,
			final Class<?> clazz,
			final Throwable cause)
	{
		super(buildMessage(cmd, stringToParse, elementName, clazz, cause),
				cause);
	}

	private static String buildMessage(final ICmd cmd, final String stringToParse, final String elementName, final Class<?> clazz,
			final Throwable cause)
	{
		final String exceptionClass = cause == null ? "<throwable class NULL>" : cause.getClass().getName();

		return StringUtils.formatMessage("Caught {} while parsing elementName {} from stringToParse {}; (cmd = {})",
				exceptionClass,	elementName, stringToParse, cmd);
	}

}
