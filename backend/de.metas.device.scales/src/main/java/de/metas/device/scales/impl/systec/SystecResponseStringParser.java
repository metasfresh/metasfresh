package de.metas.device.scales.impl.systec;

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


import java.text.Format;

import de.metas.device.scales.impl.IParser;
import de.metas.device.scales.impl.ParserException;

public class SystecResponseStringParser implements IParser<ISystecCmd>
{
	@Override
	@SuppressWarnings("unchecked")
	public <T> T parse(final ISystecCmd cmd, final String stringToParse, final String elementName, final Class<T> clazz)
	{
		try
		{
			final SystecResultStringElement elementInfo = cmd.getResultElements().get(elementName);

			final String subString = stringToParse.substring(elementInfo.getStartByte() - 1, elementInfo.getStartByte() - 1 + elementInfo.getLength()).trim();

			final Format format = elementInfo.getFormat();
			if (format == null)
			{
				return (T)subString;
			}

			return (T)format.parseObject(subString);
		}
		catch (final Exception e)
		{
			throw new ParserException(cmd, stringToParse, elementName, clazz, e);
		}
	}

	@Override
	public String toString()
	{
		return String.format("SystecResponseStringParser []");
	}
}
