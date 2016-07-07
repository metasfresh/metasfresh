package de.metas.fresh.picking.form;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.compiere.util.Util;

import com.google.common.base.MoreObjects;

import de.metas.adempiere.form.IPackingItem;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

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
 * Wraps an {@link FreshPackingItem} and adds transaction support.<br>
 * Basically that means that this instance wraps another {@link IFreshPackingItem}.<br>
 * A copy of that wrapped item can be obtained via {@link #createNewState()}.<br>
 * Changes can be made to this this copy incrementally, and can then be "flushed" onto the original wrapped instance by calling {@link #commit(IFreshPackingItem)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @see TransactionalFreshPackingItemSupport
 */
public class TransactionalFreshPackingItem extends ForwardingFreshPackingItem
{
	private static final transient AtomicLong nextId = new AtomicLong(1);

	private final long id;

	private final FreshPackingItem root;

	TransactionalFreshPackingItem(final Map<I_M_ShipmentSchedule, BigDecimal> scheds2Qtys)
	{
		super();
		id = nextId.incrementAndGet();
		root = new FreshPackingItem(scheds2Qtys);
	}

	/** Copy constructor */
	private TransactionalFreshPackingItem(final TransactionalFreshPackingItem item)
	{
		super();
		id = nextId.incrementAndGet();
		root = new FreshPackingItem(item.getDelegate());
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
	 * Called by {@link TransactionalFreshPackingItemSupport} when the object is required in a new transaction
	 */
	IFreshPackingItem createNewState()
	{
		return root.copy();
	}

	@Override
	protected final IFreshPackingItem getDelegate()
	{
		final IFreshPackingItem state = getStateOrNull();
		if (state != null)
		{
			return state;
		}

		return root;
	}

	/**
	 * @return
	 * 		<ul>
	 *         <li>the {@link FreshPackingItem} as it is in current transaction
	 *         </li>or the main {@link FreshPackingItem} (i.e. the root) if there is no transaction running
	 *         </ul>
	 */
	private IFreshPackingItem getStateOrNull()
	{
		final TransactionalFreshPackingItemSupport transactionalSupport = TransactionalFreshPackingItemSupport.getCreate();
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
	public void commit(final IFreshPackingItem state)
	{
		root.updateFrom(state);
	}

	/**
	 * Creates a new instance which wraps a copy of this instances {@link #getDelegate()} value.
	 */
	@Override
	public IFreshPackingItem copy()
	{
		return new TransactionalFreshPackingItem(this);
	}

	@Override
	public boolean isSameAs(final IPackingItem item)
	{
		return Util.same(this, item);
	}
}
