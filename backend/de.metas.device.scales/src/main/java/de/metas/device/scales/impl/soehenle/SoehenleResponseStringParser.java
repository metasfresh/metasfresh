/*
 * #%L
 * de.metas.device.scales
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

package de.metas.device.scales.impl.soehenle;

import ch.qos.logback.classic.Level;
import de.metas.device.scales.impl.IParser;
import de.metas.device.scales.impl.ParserException;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Loggables;
import org.slf4j.Logger;

import static de.metas.device.scales.impl.soehenle.ISoehenleCmd.RESULT_ELEMENT_STATUS;
import static java.math.BigDecimal.ZERO;

public class SoehenleResponseStringParser implements IParser<ISoehenleCmd>
{
	private static final Logger logger = LogManager.getLogger(SoehenleResponseStringParser.class);
	public static final int STATUS_IN_ALIBISPEICHER_EXPECTED_LENGTH = 15;
	public static final int STATUS_EXPECTED_LENGTH = 7;

	@SuppressWarnings("unchecked")
	@Override
	public <T> T parse(final ISoehenleCmd cmd, final String stringToParse, final String elementName, final Class<T> clazz)
	{
		try
		{
			if (Check.isBlank(stringToParse))
			{
				Loggables.withLogger(logger, Level.INFO).addLog("The scale returned no value for cmd={};", cmd);
				return (T)ZERO.toString();
			}

			final SoehenleResultStringElement elementInfo = cmd.getResultElements().get(elementName);
			final String[] tokens = stringToParse.split(" +"); // split string around spaces

			final String status = getStatus(cmd, tokens);

			//underweight
			if (status.startsWith("1"))
			{
				Loggables.withLogger(logger, Level.INFO).addLog("The scale returned an underweight measurement {} for cmd={};Returning 0;", stringToParse, cmd);
				return (T)ZERO.toString();
			}

			//overweight
			if (status.startsWith("01"))
			{
				Loggables.withLogger(logger, Level.INFO).addLog("The scale returned an overweight measurement {} for cmd={};Returning 0;", stringToParse, cmd);
				return (T)ZERO.toString();
			}

			final String resultToken = tokens[elementInfo.getPosition() - 1];

			return (T)resultToken;
		}
		catch (final Exception e)
		{
			throw new ParserException(cmd, stringToParse, elementName, clazz, e);
		}
	}

	private static String getStatus(final ISoehenleCmd cmd, final String[] tokens)
	{
		final SoehenleResultStringElement statusInfo = cmd.getResultElements().get(RESULT_ELEMENT_STATUS);
		final String status = tokens[statusInfo.getPosition() - 1];
		if (status.length() == STATUS_IN_ALIBISPEICHER_EXPECTED_LENGTH) //Message is in Alibispeicher format, drop the first 8 characters
		{
			return status.substring(8);
		}
		if (status.length() != STATUS_EXPECTED_LENGTH)
		{
			Loggables.withLogger(logger, Level.WARN).addLog("Found an incorrectly formatted status: {} ", status);
		}
		return status;
	}

	@Override
	public String toString()
	{
		return "SicsResponseStringParser []";
	}
}
