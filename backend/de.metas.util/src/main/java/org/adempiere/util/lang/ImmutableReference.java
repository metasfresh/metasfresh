package org.adempiere.util.lang;

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
 * Immutable hard reference.
 *
 * @author tsa
 *
 * @param <T> value type
 */
public final class ImmutableReference<T> implements IReference<T>
{
	public final static <T> ImmutableReference<T> valueOf(final T value)
	{
		return new ImmutableReference<>(value);
	}

	private final T value;

	public ImmutableReference(final T value)
	{
		super();
		this.value = value;
	}

	@Override
	public T getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		return "ImmutableReference [value=" + value + "]";
	}

}
