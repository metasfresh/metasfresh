package de.metas.fresh.picking.form;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.ad.trx.spi.TrxListenerAdapter;
import org.adempiere.util.Services;

import com.google.common.base.Supplier;

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
 * Transactional support helper for {@link TransactionalFreshPackingItem}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class TransactionalFreshPackingItemSupport
{
	private static final transient String TRXPROPERTYNAME = TransactionalFreshPackingItemSupport.class.getName();

	/**
	 * Gets the transaction support for current transaction.
	 * 
	 * If there is no transaction support, a new instance will be created and registered.
	 * 
	 * @return transaction support or <code>null</code> if running out of transaction
	 */
	public static final TransactionalFreshPackingItemSupport getCreate()
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (trxManager.isNull(trx))
		{
			// running out of transaction
			return null;
		}

		//
		// Gets/Creates a new transaction support
		return trx.getProperty(TRXPROPERTYNAME, new Supplier<TransactionalFreshPackingItemSupport>()
		{
			@Override
			public TransactionalFreshPackingItemSupport get()
			{
				return new TransactionalFreshPackingItemSupport(trx);
			}
		});
	}

	private final Map<Long, ItemState> items = new LinkedHashMap<>();

	private TransactionalFreshPackingItemSupport(final ITrx trx)
	{
		super();

		//
		// Register the commit/rollback transaction listener
		trx.getTrxListenerManager().registerListener(new TrxListenerAdapter()
		{
			@Override
			public void afterCommit(final ITrx trx)
			{
				commit();
			}

			@Override
			public void afterRollback(final ITrx trx)
			{
				rollback();
			}
		});
	}

	private synchronized void commit()
	{
		final List<ItemState> itemsToCommit = new ArrayList<>(items.values());
		for (final ItemState item : itemsToCommit)
		{
			item.commit();
		}
		items.clear();
	}

	private synchronized void rollback()
	{
		items.clear();
	}

	/**
	 * Gets the current state for given transactional item.
	 * 
	 * @param item
	 * @return current stateș never returns null.
	 */
	public synchronized IFreshPackingItem getState(final TransactionalFreshPackingItem item)
	{
		final long id = item.getId();
		ItemState itemState = items.get(id);
		if (itemState == null)
		{
			itemState = new ItemState(item);
			items.put(id, itemState);
		}
		return itemState.getState();
	}

	/**
	 * Transactional item state holder.
	 * 
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	private static final class ItemState
	{
		private final Reference<TransactionalFreshPackingItem> transactionalItemRef;
		private final IFreshPackingItem state;

		public ItemState(final TransactionalFreshPackingItem transactionalItem)
		{
			super();
			// NOTE: we keep a weak reference to our transactional item
			// because in case nobody is referencing it, there is no point to update on commit.
			this.transactionalItemRef = new WeakReference<>(transactionalItem);
			state = transactionalItem.createNewState();
		}

		public IFreshPackingItem getState()
		{
			return state;
		}

		public void commit()
		{
			final TransactionalFreshPackingItem transactionalItem = transactionalItemRef.get();
			if (transactionalItem == null)
			{
				// reference already expired
				return;
			}
			transactionalItem.commit(state);
		}
	}
}
