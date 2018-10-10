package de.metas.picking.service;

import java.util.concurrent.atomic.AtomicLong;

import org.compiere.util.Util;

import com.google.common.base.MoreObjects;

/*
 * #%L
 * de.metas.fresh.base
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
 * Wraps an {@link PackingItem} and adds transaction support.<br>
 * Basically that means that this instance wraps another {@link IPackingItem}.<br>
 * A copy of that wrapped item can be obtained via {@link #createNewState()}.<br>
 * Changes can be made to this this copy incrementally, and can then be "flushed" onto the original wrapped instance by calling {@link #commit(IPackingItem)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @see TransactionalPackingItemSupport
 */
final class TransactionalPackingItem extends ForwardingPackingItem
{
	private static final transient AtomicLong nextId = new AtomicLong(1);

	private final long id;

	private final PackingItem root;

	TransactionalPackingItem(final PackingItemParts parts)
	{
		id = nextId.incrementAndGet();
		root = new PackingItem(parts);
	}

	/** Copy constructor */
	private TransactionalPackingItem(final TransactionalPackingItem item)
	{
		id = nextId.incrementAndGet();
		root = new PackingItem(item.getDelegate());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("root", root)
				.add("state", getStateOrNull())
				.toString();
	}

	public long getId()
	{
		return id;
	}

	/**
	 * Called by {@link TransactionalPackingItemSupport} when the object is required in a new transaction
	 */
	PackingItem createNewState()
	{
		return root.copy();
	}

	@Override
	protected final PackingItem getDelegate()
	{
		final PackingItem state = getStateOrNull();
		if (state != null)
		{
			return state;
		}

		return root;
	}

	/**
	 * @return
	 * 		<ul>
	 *         <li>the {@link PackingItem} as it is in current transaction
	 *         </li>or the main {@link PackingItem} (i.e. the root) if there is no transaction running
	 *         </ul>
	 */
	private PackingItem getStateOrNull()
	{
		final TransactionalPackingItemSupport transactionalSupport = TransactionalPackingItemSupport.getCreate();
		if (transactionalSupport == null)
		{
			// not running in transaction
			return null;
		}

		return transactionalSupport.getState(this);
	}

	/**
	 * Called by API when the transaction is committed.
	 *
	 * @param state
	 */
	public void commit(final PackingItem state)
	{
		root.updateFrom(state);
	}

	/**
	 * Creates a new instance which wraps a copy of this instances {@link #getDelegate()} value.
	 */
	@Override
	public IPackingItem copy()
	{
		return new TransactionalPackingItem(this);
	}

	@Override
	public boolean isSameAs(final IPackingItem item)
	{
		return Util.same(this, item);
	}
}
