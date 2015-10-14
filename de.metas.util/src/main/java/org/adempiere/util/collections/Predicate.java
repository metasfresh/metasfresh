package org.adempiere.util.collections;

/*
 * #%L
 * de.metas.util
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
 * Performs some predicate which returns true or false based on the input object. Predicate instances can be used to implement queries or to do filtering.
 * 
 */
// this interface is based on org.apache.commons.collections.Predicate
public interface Predicate<T>
{
	/**
	 * Returns true if the input object matches this predicate.
	 * 
	 * @return true if the input object matches this predicate, else returns false
	 */
	boolean evaluate(T value);
}
