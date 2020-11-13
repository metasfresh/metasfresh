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


import java.util.AbstractSet;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Same as {@link java.util.HashSet} but is using and {@link java.util.IdentityHashMap} internally, instead of {@link java.util.HashMap}
 * 
 * @author tsa
 * 
 * @param <E>
 * @see java.util.HashSet
 * @see java.util.IdentityHashMap
 */
// This class is a copy paste of org.zkoss.util.IdentityHashSet with some minor changes
public class IdentityHashSet<E> extends AbstractSet<E>
		implements Set<E>, Cloneable, java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2272822291760142606L;

	// Dummy value to associate with an Object in the backing Map
	private static final Object PRESENT = new Object();

	private transient IdentityHashMap<E, Object> map;

	/**
	 * Constructs a new, empty set; the backing <tt>IdentityHashMap</tt> instance has default capacity (32).
	 */
	public IdentityHashSet()
	{
		map = new IdentityHashMap<E, Object>();
	}

	/**
	 * Constructs a new set containing the elements in the specified collection.
	 * 
	 * @param c the collection whose elements are to be placed into this set.
	 * @throws NullPointerException if the specified collection is null.
	 */
	public IdentityHashSet(Collection<? extends E> c)
	{
		map = new IdentityHashMap<E, Object>(Math.max((c.size() * 4) / 3, 16));
		addAll(c);
	}

	/**
	 * Constructs a new, empty set with the specified expected maximum size.
	 * 
	 * @param expectedMaxSize the expected maximum size of the map.
	 * @throws IllegalArgumentException if <tt>expectedMaxSize</tt> is negative
	 */
	public IdentityHashSet(int expectedMaxSize)
	{
		map = new IdentityHashMap<E, Object>(expectedMaxSize);
	}

	// -- Set --//
	@Override
	public Iterator<E> iterator()
	{
		return map.keySet().iterator();
	}

	@Override
	public int size()
	{
		return map.size();
	}

	@Override
	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	@Override
	public boolean contains(Object o)
	{
		return map.containsKey(o);
	}

	@Override
	public boolean add(E o)
	{
		return map.put(o, PRESENT) == null;
	}

	@Override
	public boolean remove(Object o)
	{
		return map.remove(o) == PRESENT;
	}

	@Override
	public void clear()
	{
		map.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone()
	{
		try
		{
			final IdentityHashSet<E> newSet = (IdentityHashSet<E>)super.clone();
			newSet.map = (IdentityHashMap<E, Object>)map.clone();
			return newSet;
		}
		catch (CloneNotSupportedException e)
		{
			throw new InternalError();
		}
	}

	// -- Serializable --//
	private synchronized void writeObject(java.io.ObjectOutputStream s)
			throws java.io.IOException
	{
		// Write out any hidden serialization magic
		s.defaultWriteObject();

		// Write out size
		s.writeInt(map.size());

		// Write out all elements in the proper order.
		for (Iterator<E> i = map.keySet().iterator(); i.hasNext();)
			s.writeObject(i.next());
	}

	private synchronized void readObject(java.io.ObjectInputStream s)
			throws java.io.IOException, ClassNotFoundException
	{
		// Read in any hidden serialization magic
		s.defaultReadObject();

		// Read in size (number of Mappings)
		int size = s.readInt();

		// Read in IdentityHashMap capacity and load factor and create backing IdentityHashMap
		map = new IdentityHashMap<E, Object>((size * 4) / 3);
		// Allow for 33% growth (i.e., capacity is >= 2* size()).

		// Read in all elements in the proper order.
		for (int i = 0; i < size; i++)
		{
			@SuppressWarnings("unchecked")
			final E e = (E)s.readObject();

			map.put(e, PRESENT);
		}
	}
}
