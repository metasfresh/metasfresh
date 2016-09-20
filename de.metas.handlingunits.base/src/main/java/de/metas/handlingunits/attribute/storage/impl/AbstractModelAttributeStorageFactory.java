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

import java.util.Collection;
import java.util.concurrent.Callable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.util.Util.ArrayKey;

import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;

/**
 * Abstract implementation of {@link IAttributeStorageFactory} which is oriented by having an underlying data-type from which we can get the {@link IAttributeStorage} in a standard way.
 *
 * @author tsa
 *
 * @param <ModelType> underlying data-type
 * @param <AttributeStorageType> attribute storage type
 */
public abstract class AbstractModelAttributeStorageFactory<ModelType, AttributeStorageType extends IAttributeStorage>
		extends AbstractAttributeStorageFactory
{
	/**
	 * Map used to "cache" attribute storages which were already created. Also see {@link #getAttributeStorageForModel(Object)}.
	 * <ul>
	 * <li>Key: underlying data model key (see {@link #mkKey(Object)})
	 * <li>Value: existing {@link IAttributeStorage}
	 * </ul>
	 */
	private final Cache<ArrayKey, AttributeStorageType> key2storage = CacheBuilder.newBuilder()
			.removalListener(new RemovalListener<ArrayKey, AttributeStorageType>()
			{
				@Override
				public void onRemoval(final RemovalNotification<ArrayKey, AttributeStorageType> notification)
				{
					final AttributeStorageType attributeStorage = notification.getValue();
					onAttributeStorageRemovedFromCache(attributeStorage);
				}
			})
			.build();

	@Override
	protected final Collection<AttributeStorageType> getExistingAttributeStorages()
	{
		return key2storage.asMap().values();
	}

	@Override
	public abstract boolean isHandled(final Object modelObj);

	/**
	 * Gets the underlying data-type from given <code>modelObj</code>
	 *
	 * @param modelObj
	 * @return
	 * 		<ul>
	 *         <li>underlying data-type model
	 *         <li><code>null</code> if this modelObj cannot be handled by this factory
	 *         <li>a null marker if this modelObj is actually handled by this factory but there is no underlying data-type model for it. In this case {@link #isNullModel(Object)} shall return true for
	 *         this returned model
	 *         </ul>
	 */
	protected abstract ModelType getModelFromObject(final Object modelObj);

	@Override
	public final IAttributeStorage getAttributeStorage(final Object modelObj)
	{
		final ModelType model = getModelFromObject(modelObj);
		Check.assume(!isNullModel(model), "model not \"conceptually\" (depending on implementation) null for modelObj={} (model={})", modelObj, model);

		return getAttributeStorageForModel(model);
	}

	@Override
	public IAttributeStorage getAttributeStorageIfHandled(final Object modelObj)
	{
		if (modelObj == null)
		{
			return null;
		}
		if (!isHandled(modelObj))
		{
			return null;
		}

		return getAttributeStorage(modelObj);
	}

	/**
	 * Creates a key from underlying data model to be used when caching current storages. Note that the implementation does only need to create a key that is unique per <code>ModelType</code>.
	 *
	 * @param model
	 * @return key
	 */
	protected abstract ArrayKey mkKey(final ModelType model);

	/**
	 * Checks if given <code>model</code> is null.
	 *
	 * @param model
	 * @return true if given model is conceptually null (e.g. is actually null, is a null marker etc)
	 */
	protected boolean isNullModel(final ModelType model)
	{
		if (model == null)
		{
			return true;
		}
		return false;
	}

	protected final IAttributeStorage getAttributeStorageForModelIfLoaded(final ModelType model)
	{
		final boolean onlyIfPresent = true; // if it's not yet there, then return null
		return getAttributeStorageForModel(model, onlyIfPresent);
	}

	protected IAttributeStorage getAttributeStorageForModel(final ModelType model)
	{
		final boolean onlyIfPresent = false; // also get it if it's not yet loaded.
		return getAttributeStorageForModel(model, onlyIfPresent);
	}

	private IAttributeStorage getAttributeStorageForModel(final ModelType model, final boolean onlyIfPresent)
	{
		final ArrayKey key = mkKey(model);
		try
		{
			final IAttributeStorage result;
			if (onlyIfPresent)
			{
				result = key2storage.getIfPresent(key);
			}
			else
			{
				result = key2storage.get(key, new Callable<AttributeStorageType>()
				{
					@Override
					public AttributeStorageType call() throws Exception
					{
						final AttributeStorageType storage = createAttributeStorage(model);
						Check.assumeNotNull(storage, "storage not null");

						// Add listeners to our storage
						addListenersToAttributeStorage(storage);

						return storage;
					}
				});
			}

			if (result != null)
			{
				result.assertNotDisposed();
			}
			return result;
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	/**
	 * Create attribute storage for underlying model
	 *
	 * @param model
	 * @return attribute storage
	 */
	protected abstract AttributeStorageType createAttributeStorage(final ModelType model);

	/**
	 * Clears internal attribute storages cache and flushes pending saves on {@link #getHUAttributesDAO()}.
	 */
	public final void clearCache()
	{
		// First thing, clear the cached attribute storages.
		// We do this first because in case the attribute storages are disposed while clearing the cache,
		// we want to make sure no more changes will be pushed to be saved.
		key2storage.invalidateAll();
		key2storage.cleanUp();

		// Also flush and clear the HUAttributesDAO cache because if we are not doing this,
		// next time when we will load an Attributes Storage we will get the cached ones.
		getHUAttributesDAO().flushAndClearCache();
	}

	/**
	 * Method called when an {@link IAttributeStorage} is removed from cache.
	 *
	 * If needed you could do some cleanup work like, {@link #removeListenersFromAttributeStorage(IAttributeStorage)}, destroy it etc.
	 *
	 * @param attributeStorage
	 */
	protected void onAttributeStorageRemovedFromCache(final AttributeStorageType attributeStorage)
	{
		// nothing on this level
	}

	@Override
	protected void toString(final ToStringHelper stringHelper)
	{
		// NOTE: avoid printing the whole map because it might get to HU storages that also print factory, leading to recursive calls of the toString.
		stringHelper.add("storages#", key2storage.size());
	}
}
