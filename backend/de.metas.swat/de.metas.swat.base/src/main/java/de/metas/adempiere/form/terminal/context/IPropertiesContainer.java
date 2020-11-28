package de.metas.adempiere.form.terminal.context;

/*
 * #%L
 * de.metas.swat.base
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


/**
 * Implementing classes shall contain configurable properties
 */
public interface IPropertiesContainer
{
	/**
	 * Set internal property
	 *
	 * @param propertyName
	 * @param value
	 *
	 * @return old property value
	 */
	Object setProperty(String propertyName, Object value);

	/**
	 * Get internal property
	 *
	 * @param propertyName
	 * @return property value or null if property value was not registered
	 *
	 * @throws ClassCastException if property value could not be converted to the requested type
	 */
	<T> T getProperty(String propertyName) throws ClassCastException;
}
