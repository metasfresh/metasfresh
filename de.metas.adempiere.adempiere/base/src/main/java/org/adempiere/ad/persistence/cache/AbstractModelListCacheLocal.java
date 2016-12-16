package org.adempiere.ad.persistence.cache;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.wrapper.POJOLookupMapInstancesTracker;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;

/**
 * Abstract list of models which expires when parent model's context or transaction was changed.
 *
 * @author tsa
 *
 * @param <PT>
 * @param <T>
 */
public abstract class AbstractModelListCacheLocal<PT, T>
{
	// Services
	// private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	private final WeakReference<PT> parentModelRef;
	private int parentModelLoadCount;
	private final Comparator<T> itemsComparator;

	private IContextAware ctx = null;
	private List<T> items = null;
	private boolean cacheDisabled = false;

	/**
	 * Debug flag. If enabled, intermediare checkings will be performed.
	 *
	 * i.e. make sure that the cached items are the same with the ones that it would be retrieved from database. So, each time when cache is accessed, records are also fetched from database and them
	 * compared with cached ones.
	 *
	 * NOTE: this will ensure data integrity but will decrease the performances as hell. To be used in automated tests only.
	 */
	public static boolean DEBUG = false;
	private boolean debugEmptyNotStaledSet = false;

	public AbstractModelListCacheLocal(final PT parentModel)
	{
		super();
		Check.assumeNotNull(parentModel, "parentModel not null");
		this.parentModelRef = new WeakReference<PT>(parentModel);
		this.parentModelLoadCount = InterfaceWrapperHelper.getLoadCount(parentModel);
		this.ctx = null;
		this.items = null;

		this.itemsComparator = createItemsComparator();
	}

	protected void setCacheDisabled(final boolean cacheDisabled)
	{
		this.cacheDisabled = cacheDisabled;
	}

	/**
	 * Creates comparator used to sort items when added to iternal list.
	 *
	 * @return comparator or null
	 */
	protected abstract Comparator<T> createItemsComparator();

	/**
	 * Retrieves items from database.
	 *
	 * @param ctx context and transaction of parent model
	 * @param parentModel parent model
	 * @return retrieved items
	 */
	protected abstract List<T> retrieveItems(final IContextAware ctx, final PT parentModel);

	// NOTE: took out because it can cause StackOverflowError (e.g. in some tests it's doing this)
	// @Override
	// public String toString()
	// {
	// return getClass().getSimpleName() + "["
	// + "parent=" + parentModelRef.get()
	// + ", ctx=" + ctx
	// + ", items(" + (items == null ? 0 : items.size()) + ")=" + items
	// + "]";
	// }

	/**
	 * Gets a read-write copy of the inner items list.
	 *
	 * If items are not loaded or they are staled, they will be reloaded.
	 *
	 * @return read-write copy of the inner items list.
	 */
	public final List<T> getItems()
	{
		if (checkStaled())
		{
			loadItems();
		}
		else
		{
			// System.out.println("Returning CACHED items for " + this);
		}

		debugCheckItemsValid();

		final List<T> itemsCopy = new ArrayList<T>(items);
		return itemsCopy;
	}

	/**
	 * Mark this list as staled.
	 */
	private final void markStaled()
	{
		this.items = null;
		this.debugEmptyNotStaledSet = false;
	}

	/**
	 *
	 * @return true if this list is staled and it needs to be reloaded
	 */
	private final boolean isStaled()
	{
		return items == null;
	}

	/**
	 * Validate this list and mark it as staled if needed.
	 *
	 * @return true if staled
	 */
	private final boolean checkStaled()
	{
		if (isStaled())
		{
			return true;
		}

		if (cacheDisabled)
		{
			markStaled();
			return true;
		}

		//
		// Check if parent context changed
		final PT parentModel = getParentModel();
		final PlainContextAware parentModelCtx = createPlainContextAware(parentModel);
		if (!Check.equals(this.ctx, parentModelCtx))
		{
			markStaled(); // mark as staled
			return true;
		}

		// NOTE: commented out because we don't needed, but i am leaving it here because you never know.
		// //
		// // Don't cache if out of transaction because it could happen that in some transactions the models where changed in meantime
		// if (trxManager.isNull(parentModelCtx.getTrxName()))
		// {
		// markStaled(); // mark as staled
		// return true;
		// }

		//
		// Check if parent's load count changed
		// In case the load count was changed, it means a refresh was performed on that object so we will also reset our cache.
		final int parentModelLoadCountNow = InterfaceWrapperHelper.getLoadCount(parentModel);
		if (this.parentModelLoadCount != parentModelLoadCountNow)
		{
			markStaled(); // mark as staled
			return true;
		}

		//
		// Check if loaded items are valid
		final List<T> itemsToCheck = items;
		for (final T item : itemsToCheck)
		{
			if (!isValidItem(item))
			{
				markStaled(); // mark as staled
				return true;
			}
		}

		return false;
	}

	/**
	 * Validate this list and mark it as staled if needed or if the given item is not suitable for this list.
	 *
	 * @param item
	 * @return true if staled or item is not valid
	 */
	private final boolean checkAndStaleIfItemNotValid(final T item)
	{
		if (checkStaled())
		{
			// do nothing, this list is staled anyways
			return true;
		}

		if (!isValidItem(item))
		{
			// mark it as staled because something is not ok here so we will need to reload it anyways
			markStaled();
			return true;
		}

		return false; // not staled, item is valid
	}

	/**
	 * Checks if given item is valid and can be added/removed from this lisr
	 *
	 * @param item
	 * @return true if valid
	 */
	private final boolean isValidItem(final T item)
	{
		final PlainContextAware itemCtx = createPlainContextAware(item);
		if (!itemCtx.equals(ctx))
		{
			return false;
		}

		return true;
	}

	/**
	 * Try to add given item to this list.
	 *
	 * @param itemToAdd
	 */
	public final void addItem(final T itemToAdd)
	{
		if (checkAndStaleIfItemNotValid(itemToAdd))
		{
			// staled, return right away
			return;
		}

		System.out.println("Adding item:");
		System.out.println("Item to add: "+itemToAdd);
		System.out.println("Cached items: "+items);
		System.out.println("Parent: "+getParentModel());
		System.out.println("---------------------------------------------");

		//
		// Remove existing item (if any)
		final Object itemToAddKey = mkKey(itemToAdd);
		final Iterator<T> it = items.iterator();
		while(it.hasNext())
		{
			final T item = it.next();
			if (item == itemToAdd || itemToAddKey.equals(mkKey(item)))
			{
				it.remove();
				break;
			}
		}

		//
		// Add item to items
		items.add(itemToAdd);

		//
		// Make sure items are sorted
		if (itemsComparator != null)
		{
			Collections.sort(items, itemsComparator);
		}

		debugCheckItemsValid();
	}

	protected abstract Object mkKey(final T item);

	/**
	 * Try to remove item from this list.
	 *
	 * @param itemToRemove
	 */
	public final void removeItem(final T itemToRemove)
	{
		if (checkAndStaleIfItemNotValid(itemToRemove))
		{
			// staled, return right away
			return;
		}


		System.out.println("Removing item:");
		System.out.println("Item to remove: "+itemToRemove);
		System.out.println("Cached items: "+items);
		System.out.println("Parent: "+getParentModel());
		System.out.println("---------------------------------------------");

		//
		// Remove existing item (if any)
		boolean removed = false;
		final Object itemToRemoveKey = mkKey(itemToRemove);
		final Iterator<T> it = items.iterator();
		while(it.hasNext())
		{
			final T item = it.next();
			if (item == itemToRemove || itemToRemoveKey.equals(mkKey(item)))
			{
				it.remove();
				removed = true;
				break;
			}
		}

		if (!removed)
		{
			markStaled();
			return;
//			throw new AdempiereException("Cannot remove item from cached items."
//					+ "\n Item to remove: " + item
//					+ "\n Cached items(" + items.size() + "): " + items
//					+ "\n Parent: " + getParentModel()
//					+ "\n debugEmptyNotStaledSet=" + debugEmptyNotStaledSet //
//			);
		}

		debugCheckItemsValid();
	}

	/**
	 * Gets parent model.
	 *
	 * @return parent model
	 * @throws IllegalStateException if parent's weak reference expired
	 */
	private final PT getParentModel()
	{
		final PT parentModel = parentModelRef.get();
		if (parentModel == null)
		{
			throw new IllegalStateException("Model list cache expired for good");
		}
		return parentModel;
	}

	/**
	 * Creates a {@link PlainContextAware} from given <code>model</code>.
	 *
	 * @param model
	 * @return
	 */
	private static final PlainContextAware createPlainContextAware(final Object model)
	{
		final IContextAware contextAware = InterfaceWrapperHelper.getContextAware(model);
		final PlainContextAware contextAwarePlainCopy = PlainContextAware.newCopy(contextAware);
		return contextAwarePlainCopy;
	}

	/**
	 * Load items from database.
	 */
	private final void loadItems()
	{
		final PT parentModel = getParentModel();
		final IContextAware ctx = createPlainContextAware(parentModel);

		//
		// Retrieve fresh items
		final List<T> items = retrieveItems(ctx, parentModel);

		//
		// Update status
		this.ctx = ctx;
		this.items = items == null ? null : new ArrayList<T>(items); // NOTE: we need to do a copy just in case we get an unmodifiable list
		this.parentModelLoadCount = InterfaceWrapperHelper.getLoadCount(parentModel);
		this.debugEmptyNotStaledSet = false;
	}

	/**
	 * Sets an empty inner list and flag this list as not staled anymore.
	 *
	 * To be used after parent model is created, to start maintaining this list from the very beginning.
	 */
	public final void setEmptyNotStaled()
	{
		final PT parentModel = getParentModel();
		this.ctx = createPlainContextAware(parentModel);
		this.items = new ArrayList<T>();
		this.debugEmptyNotStaledSet = true;

		debugCheckItemsValid();
	}

	private final void debugCheckItemsValid()
	{
		if (!DEBUG)
		{
			return;
		}

		final PT parentModel = getParentModel();
		final IContextAware ctx = createPlainContextAware(parentModel);

		final boolean instancesTrackerEnabled = POJOLookupMapInstancesTracker.ENABLED;
		POJOLookupMapInstancesTracker.ENABLED = false;
		try
		{
			//
			// Retrieve fresh items
			final List<T> itemsRetrievedNow = retrieveItems(ctx, parentModel);
			if (itemsComparator != null)
			{
				Collections.sort(itemsRetrievedNow, itemsComparator);
			}

			if (!Check.equals(this.items, itemsRetrievedNow))
			{
				final int itemsCount = this.items == null ? 0 : this.items.size();
				final int itemsRetrievedNowCount = itemsRetrievedNow.size();
				throw new AdempiereException("Loaded items and cached items does not match."
						+ "\n Parent: " + parentModelRef.get()
						+ "\n Cached items(" + itemsCount + "): " + this.items
						+ "\n Fresh items(" + itemsRetrievedNowCount + "): " + itemsRetrievedNow
						+ "\n debugEmptyNotStaledSet=" + debugEmptyNotStaledSet //
				);
			}
		}
		finally
		{
			POJOLookupMapInstancesTracker.ENABLED = instancesTrackerEnabled;
		}
	}
}
