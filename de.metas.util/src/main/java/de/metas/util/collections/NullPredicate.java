package de.metas.util.collections;

import java.util.function.Predicate;

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
 * A pass-through {@link Predicate} implementation, which alreadys returns <code>true</code>.
 * 
 * @author tsa
 *
 */
public final class NullPredicate implements Predicate<Object>
{
	public static final <T> Predicate<T> of()
	{
		@SuppressWarnings("unchecked")
		final Predicate<T> predicate = (Predicate<T>)instance;
		return predicate;
	}

	private static final transient NullPredicate instance = new NullPredicate();

	private NullPredicate()
	{
		super();
	}

	/**
	 * @return true
	 */
	@Override
	public final boolean test(final Object value)
	{
		return true;
	}

}
