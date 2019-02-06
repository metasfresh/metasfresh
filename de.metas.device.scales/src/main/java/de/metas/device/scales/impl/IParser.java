package de.metas.device.scales.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <C> the command whose response this instance is able to parse.
 */
public interface IParser<C extends ICmd>
{
	/**
	 * Parse the given elementName from the given string, and convert the result to the given clazz.
	 * <p>
	 * <b>IMPORTANT: </b> throw a {@link ParserException} if there are problems
	 * 
	 * @param cmd
	 * @param stringToParse
	 * @param elementName
	 * @param clazz
	 * @return
	 * @throws ParserException
	 */
	<T> T parse(C cmd, String stringToParse, String elementName, Class<T> clazz);
}
