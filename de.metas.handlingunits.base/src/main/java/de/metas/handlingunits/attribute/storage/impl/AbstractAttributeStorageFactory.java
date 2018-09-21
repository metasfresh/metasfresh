package de.metas.handlingunits.attribute.storage.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageListener;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.util.Check;

public abstract class AbstractAttributeStorageFactory implements IAttributeStorageFactory
{
	private final List<IAttributeStorageListener> attributeStorageListeners = new ArrayList<IAttributeStorageListener>();
	private IHUAttributesDAO huAttributesDAO;
	private IHUStorageFactory huStorageFactory;

	@Override
	public final void addAttributeStorageListener(final IAttributeStorageListener listener)
	{
		Check.assumeNotNull(listener, "listener not null");
		if (attributeStorageListeners.contains(listener))
		{
			// already added
			return;
		}

		attributeStorageListeners.add(listener);

		//
		// Add listener to current existing storages
		for (final IAttributeStorage storage : getExistingAttributeStorages())
		{
			storage.addListener(listener);
		}
	}

	@Override
	public final void removeAttributeStorageListener(final IAttributeStorageListener listener)
	{
		// guard against null
		if (listener == null)
		{
			return;
		}

		attributeStorageListeners.remove(listener);

		//
		// Add listener to current existing storages
		for (final IAttributeStorage storage : getExistingAttributeStorages())
		{
			storage.removeListener(listener);
		}
	}

	/**
	 * Gets current existing {@link IAttributeStorage}s from cache (if any)
	 *
	 * @return cached {@link IAttributeStorage}s
	 */
	protected abstract Collection<? extends IAttributeStorage> getExistingAttributeStorages();

	/**
	 * Adds current listeners to given {@link IAttributeStorage}.
	 *
	 * Use this method after you instantiate a new storage.
	 *
	 * @param attributeStorage
	 */
	protected final void addListenersToAttributeStorage(final IAttributeStorage attributeStorage)
	{
		//
		// Add listeners to our storage
		for (final IAttributeStorageListener listener : attributeStorageListeners)
		{
			attributeStorage.addListener(listener);
		}
	}

	/**
	 * Removes current listeners from given {@link IAttributeStorage}.
	 * 
	 * @param attributeStorage
	 */
	protected final void removeListenersFromAttributeStorage(final IAttributeStorage attributeStorage)
	{
		for (final IAttributeStorageListener listener : attributeStorageListeners)
		{
			attributeStorage.removeListener(listener);
		}
	}

	@Override
	public final IHUAttributesDAO getHUAttributesDAO()
	{
		Check.assumeNotNull(huAttributesDAO, "huAttributesDAO not null");
		return huAttributesDAO;
	}

	@Override
	public final void setHUAttributesDAO(final IHUAttributesDAO huAttributesDAO)
	{
		this.huAttributesDAO = huAttributesDAO;
	}

	@Override
	public IHUStorageDAO getHUStorageDAO()
	{
		return getHUStorageFactory().getHUStorageDAO();
	}

	@Override
	public IHUStorageFactory getHUStorageFactory()
	{
		Check.assumeNotNull(huStorageFactory, "IHUStorageFactory member of AbstractAttributeStorageFactory {} is not null", this);
		return huStorageFactory;
	}

	@Override
	public void setHUStorageFactory(final IHUStorageFactory huStorageFactory)
	{
		this.huStorageFactory = huStorageFactory;
	}

	@Override
	public String toString()
	{
		final ToStringHelper stringHelper = MoreObjects.toStringHelper(this);
		toString(stringHelper);

		return stringHelper.toString();
	}

	protected void toString(final ToStringHelper stringHelper)
	{
		// nothing on this level
	}

}
