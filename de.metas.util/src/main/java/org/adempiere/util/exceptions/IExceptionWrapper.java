package org.adempiere.util.exceptions;

/*
 * #%L
 * de.metas.util
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Implementors are used to augment one one the "basic" metasfresh exceptions.
 * Current/first example is <code>DBException</code> where a module specific implementor allow the DB exception to wrap and SQLException into a module specific DBEXception subclass.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <T> the "basic" exception which the implementor augments.
 */
public interface IExceptionWrapper<T extends Throwable>
{
	/**
	 * Wrap the given <code>throwable</code>, if possible.
	 *
	 * @param t the throwable to wrap. May be <code>null</code>.
	 * @return the wrapped exception or <code>null</code> if the exception could not be wrapped.
	 */
	T wrapIfNeededOrReturnNull(Throwable t);
}
