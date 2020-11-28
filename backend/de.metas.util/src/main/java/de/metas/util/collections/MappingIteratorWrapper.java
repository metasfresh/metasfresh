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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Iterator;
import java.util.function.Function;

import lombok.NonNull;

/**
 * Adapter class which converts online between one parameterized iterator to other parameterized iterator
 * 
 * @author tsa
 * 
 * @param <OT> Output data type
 * @param <IT> Input data type
 */
final class MappingIteratorWrapper<OT, IT> implements Iterator<OT>, IteratorWrapper<IT>
{
	private final Iterator<IT> iterator;
	private final Function<IT, OT> mapper;

	public MappingIteratorWrapper(@NonNull final Iterator<IT> iterator, @NonNull final Function<IT, OT> mapper)
	{
		this.iterator = iterator;
		this.mapper = mapper;
	}

	@Override
	public Iterator<IT> getParentIterator()
	{
		return iterator;
	}

	@Override
	public boolean hasNext()
	{
		return iterator.hasNext();
	}

	@Override
	public OT next()
	{
		final IT valueIn = iterator.next();
		final OT valueOut = mapper.apply(valueIn);
		return valueOut;
	}

	@Override
	public void remove()
	{
		iterator.remove();
	}

}
