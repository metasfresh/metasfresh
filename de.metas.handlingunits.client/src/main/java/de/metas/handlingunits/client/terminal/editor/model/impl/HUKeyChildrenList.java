package de.metas.handlingunits.client.terminal.editor.model.impl;

/*
 * #%L
 * de.metas.handlingunits.client
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.adempiere.ad.service.ITaskExecutorService;
import org.slf4j.Logger;

import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.logging.LogManager;

/**
 * Manages a list of child {@link IHUKey}s by offering following features:
 *
 * <ul>
 * <li>basic operations (add, remove, count etc)
 * <li>lazy loading on demand
 * <li>asynchronous loading via {@link ITaskExecutorService}
 * </ul>
 *
 * @author tsa
 *
 */
/* package */class HUKeyChildrenList implements IDisposable
{
	private static final transient Logger logger = LogManager.getLogger(HUKeyChildrenList.class);

	private final AbstractHUKey _huKey;

	private final ReentrantLock childrenLock = new ReentrantLock();
	private List<IHUKey> _children = null;
	private List<IHUKey> _childrenRO = null;

	private boolean disposed = false;

	/**
	 * @param huKey parent
	 */
	public HUKeyChildrenList(final AbstractHUKey huKey)
	{
		super();
		_huKey = huKey;
	}

	/**
	 * Gets child keys.
	 * If children were not loaded it, this method will load them first.
	 *
	 * @return all children
	 */
	public final List<IHUKey> getChildren()
	{
		childrenLock.lock();
		try
		{
			loadChildrenIfNeeded();
			return _childrenRO;
		}
		finally
		{
			childrenLock.unlock();
		}
	}

	/**
	 *
	 * @return currently loaded children
	 */
	public final List<IHUKey> getChildrenNoLoad()
	{
		childrenLock.lock();
		try
		{
			return _childrenRO == null ? Collections.<IHUKey> emptyList() : _childrenRO;
		}
		finally
		{
			childrenLock.unlock();
		}
	}

	/**
	 * Gets inner (i.e. read/write) child list.
	 *
	 * If children were not loaded it, this method will load them first.
	 *
	 * @param loadIfNeeded
	 * @return
	 */
	private final List<IHUKey> getChildrenInnerList(final boolean loadIfNeeded)
	{
		childrenLock.lock();
		try
		{
			if (loadIfNeeded)
			{
				loadChildrenIfNeeded();
				return _children; // shall not be null at this point
			}
			else if (_children == null)
			{
				return Collections.emptyList();
			}
			return _children;
		}
		finally
		{
			childrenLock.unlock();
		}
	}

	private final void loadChildrenIfNeeded()
	{
		childrenLock.lock();
		try
		{
			if (_children != null)
			{
				return;
			}

			final List<IHUKey> loadedChildren = retrieveChildren();
			logger.trace("Got children for {}: count={}", new Object[] { _huKey, loadedChildren.size() });

			_children = Collections.synchronizedList(new ArrayList<>(loadedChildren));
			_childrenRO = Collections.unmodifiableList(_children);
		}
		finally
		{
			childrenLock.unlock();
		}
	}

	/**
	 * Actually retrieve/load children.
	 *
	 * i.e. forwards the call to {@link IHUKey}.
	 *
	 * @return loaded children
	 */
	private final List<IHUKey> retrieveChildren()
	{
		logger.trace("Starting for {}", _huKey);
		final List<IHUKey> children = _huKey.retrieveChildrenAndAddListeners();
		logger.trace("Finished for {} and got {}", new Object[] { _huKey, children });
		return children;
	}

	/**
	 * Checks if there are any children.
	 *
	 * @return true if this list is empty
	 */
	public boolean isEmpty()
	{
		// Get/Load children
		final List<IHUKey> children = getChildrenInnerList(true); // loadIfNeeded = true
		return children.isEmpty();
	}

	/**
	 *
	 * @param child
	 * @return true if <code>child</code> is contained in our list
	 */
	public final boolean contains(final IHUKey child)
	{
		// We get only the children that are currently loaded
		// NOTE: there is no point to load them because if they are not loaded, for sure the given "child" is not ours ;)
		final List<IHUKey> children = getChildrenInnerList(false); // loadIfNeeded=false

		return children.contains(child);
	}

	/**
	 *
	 * @param child
	 * @return <code>true</code> if at least one of children is contained in this list
	 */
	public final boolean containsAny(final Collection<? extends IHUKey> children)
	{
		if (children == null || children.isEmpty())
		{
			return false;
		}

		// We get only the children that are currently loaded
		// NOTE: there is no point to load them because if they are not loaded, for sure the given "child" is not ours ;)
		final List<IHUKey> childrenExisting = getChildrenInnerList(false); // loadIfNeeded=false
		if (childrenExisting.isEmpty())
		{
			return false;
		}

		for (final IHUKey childToCheck : children)
		{
			if (childrenExisting.contains(childToCheck))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Remove child from this list.
	 *
	 * @param child
	 */
	public final void remove(final IHUKey child)
	{
		// We get only the children that are currently loaded
		// NOTE: there is no point to load them because if they are not loaded, for sure the given "child" is not ours ;)
		final List<IHUKey> children = getChildrenInnerList(false); // loadIfNeeded=false

		// guard against EmptyLists which are throwing UnsupportedOperationException
		if (children.isEmpty())
		{
			return;
		}

		children.remove(child);
	}

	/**
	 * Adds child to this list.
	 *
	 * @param child
	 */
	public void add(final IHUKey child)
	{
		final List<IHUKey> children = getChildrenInnerList(true); // loadIfNeeded=true
		children.add(child);
	}

	/**
	 * Remove all children.
	 */
	@Override
	public void dispose()
	{
		childrenLock.lock();
		try
		{
			_children = null;
			_childrenRO = null;
		}
		finally
		{
			childrenLock.unlock();
		}

		logger.trace("Cleared for {}", _huKey);
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

	@Override
	public String toString()
	{
		return "HUKeyChildrenList [_huKey=" + _huKey + ", childrenLock=" + childrenLock + ", _children=" + _children + ", _childrenRO=" + _childrenRO + "]";
	}
}
