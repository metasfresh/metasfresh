package de.metas.util.collections;

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

import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

@ToString
public class CompositePredicate<T> implements Predicate<T>
{
	private final CopyOnWriteArrayList<Predicate<T>> predicates = new CopyOnWriteArrayList<>();
	private boolean and = true;

	public CompositePredicate<T> addPredicate(@NonNull final Predicate<T> predicate)
	{
		predicates.addIfAbsent(predicate);
		return this;
	}

	@Override
	public boolean test(@Nullable final T value)
	{
		Check.assume(!predicates.isEmpty(), "There is at least one child predicate in this composite predicate");

		for (final Predicate<T> predicate : predicates)
		{
			final boolean accepted = predicate.test(value);
			if (and && !accepted)
			{
				return false;
			}
			else if (!and && accepted)
			{
				return true;
			}
		}

		return and;
	}

	/**
	 * @return true if this composite contains no predicates
	 */
	public boolean isEmpty()
	{
		return predicates.isEmpty();
	}

	/**
	 * @param and true if there shall be a logical AND between predicates; false if there shall be a logical OR between predicates
	 */
	public void setAnd(final boolean and)
	{
		this.and = and;
	}

	/**
	 * @return true if a logical AND is applied between predicates; false if a logical OR is applied between predicates
	 */
	public boolean isAnd()
	{
		return this.and;
	}
}
