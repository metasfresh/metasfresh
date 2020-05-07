package org.adempiere.util.lang;

import javax.annotation.Nullable;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Immutable {@link IPair} implementation. Both the left and right value may be {@code null}.
 * 
 * @param <LT> left element type
 * @param <RT> right element type
 */
public final class ImmutablePair<LT, RT> implements IPair<LT, RT>
{
	/**
	 * Creates an immutable pair
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public static final <L, R> ImmutablePair<L, R> of(
			@Nullable final L left,
			@Nullable final R right)
	{
		return new ImmutablePair<L, R>(left, right);
	}

	private final LT left;
	private final RT right;

	private ImmutablePair(@Nullable final LT left, @Nullable final RT right)
	{
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString()
	{
		return "ImmutablePair [left=" + left + ", right=" + right + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		final ImmutablePair<?, ?> other = (ImmutablePair<?, ?>)obj;
		if (left == null)
		{
			if (other.left != null)
				return false;
		}
		else if (!left.equals(other.left))
			return false;
		if (right == null)
		{
			if (other.right != null)
				return false;
		}
		else if (!right.equals(other.right))
			return false;
		return true;
	}

	@Override
	public LT getLeft()
	{
		return left;
	}

	@Override
	public RT getRight()
	{
		return right;
	}
}
