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

import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.util.Check;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util.ArrayKey;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * Abstract implementation of {@link IAttributeStorageFactory} which is oriented by having an underlying data-type from which we can get the {@link IAttributeStorage} in a standard way.
 *
 * @param <ModelType>            underlying data-type
 * @param <AttributeStorageType> attribute storage type
 * @author tsa
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
			.removalListener((RemovalListener<ArrayKey, AttributeStorageType>)notification -> {
				final AttributeStorageType attributeStorage = notification.getValue();
				onAttributeStorageRemovedFromCache(attributeStorage);
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
	 * @return <ul>
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
	 * @return key
	 */
	protected abstract ArrayKey mkKey(final ModelType model);

	/**
	 * Checks if given <code>model</code> is null.
	 *
	 * @return true if given model is conceptually null (e.g. is actually null, is a null marker etc)
	 */
	protected boolean isNullModel(final ModelType model)
	{
		return model == null;
	}

	@Nullable
	protected IAttributeStorage getAttributeStorageForModel(final ModelType model)
	{
		final ArrayKey key = mkKey(model);
		try
		{
			final IAttributeStorage result = key2storage.get(key, () -> {
				final AttributeStorageType storage = createAttributeStorage(model);
				Check.assumeNotNull(storage, "storage not null");

				// Add listeners to our storage
				addListenersToAttributeStorage(storage);

				return storage;
			});

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
	 * @return attribute storage
	 */
	protected abstract AttributeStorageType createAttributeStorage(final ModelType model);

	@Override
	public void flush()
	{
		getHUAttributesDAO().flush();
	}

	/**
	 * Method called when an {@link IAttributeStorage} is removed from cache.
	 * <p>
	 * If needed you could do some cleanup work like, {@link #removeListenersFromAttributeStorage(IAttributeStorage)}, destroy it etc.
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
