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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.adempiere.util.Check;

import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.impl.AbstractHUAttributeStorage;
import de.metas.handlingunits.attribute.storage.impl.NullAttributeStorage;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;

/* package */class HUKeyAttributeStorage extends AbstractHUAttributeStorage
{
	public static final HUKeyAttributeStorage cast(final IAttributeStorage attributeStorage)
	{
		return (HUKeyAttributeStorage)attributeStorage;
	}

	public static final HUKeyAttributeStorage castOrNull(final IAttributeStorage attributeStorage)
	{
		if (attributeStorage instanceof HUKeyAttributeStorage)
		{
			return (HUKeyAttributeStorage)attributeStorage;
		}

		return null;
	}

	private final String id;
	private final IHUKey huKey;
	private final I_M_HU hu;
	private boolean disposed = false;
	private long disposedTS = 0;

	public HUKeyAttributeStorage(final IAttributeStorageFactory attributesStorageFactory, final IHUKey huKey)
	{
		super(attributesStorageFactory);

		//
		// Directly save to database all attribute value changes
		setSaveOnChange(true);

		Check.assumeNotNull(huKey, "huKey not null");
		this.huKey = huKey;

		if (huKey instanceof HUKey)
		{
			hu = ((HUKey)huKey).getM_HU();
			id = "HUKey-M_HU_ID=" + hu.getM_HU_ID();
		}
		else
		{
			hu = null;
			id = "HUKey-M_HU_ID=NULL;" + UUID.randomUUID().toString();
		}
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public String toString()
	{
		return "HUKeyAttributeStorage [id=" + id
				+ ", huKey=" + huKey
				+ ", hu=" + hu
				+ ", objectId=" + System.identityHashCode(this)
				+ "]";
	}

	@Override
	public I_M_HU getM_HU()
	{
		return hu;
	}

	@Override
	public IAttributeStorage getParentAttributeStorage()
	{
		final IHUKey parentHUKey = getParent(huKey);
		if (parentHUKey == null)
		{
			return NullAttributeStorage.instance;
		}

		final IAttributeStorage parentAttributeStorage = parentHUKey.getAttributeSet();
		return parentAttributeStorage;
	}

	@Override
	public List<IAttributeStorage> getChildAttributeStorages()
	{
		assertNotDisposed();

		final List<IAttributeStorage> children = new ArrayList<IAttributeStorage>();
		for (final IHUKey childKey : getChildren(huKey))
		{
			final IAttributeStorage childAttributeStorage = childKey.getAttributeSet();
			if (childAttributeStorage == null)
			{
				continue;
			}
			children.add(childAttributeStorage);
		}
		return children;
	}

	/**
	 * Method not supported. Note that this implementation can have a child attribute storage (see getter), but we can't add or remove them from outside.
	 *
	 * @throws UnsupportedOperationException
	 */
	@Override
	protected void addChildAttributeStorage(final IAttributeStorage childAttributeStorage)
	{
		throw new UnsupportedOperationException("Child attribute storages are not supported for " + this);
	}

	/**
	 * Method not supported. Note that this implementation can have a child attribute storage (see getter), but we can't add or remove them from outside.
	 *
	 * @throws UnsupportedOperationException
	 */
	@Override
	protected IAttributeStorage removeChildAttributeStorage(final IAttributeStorage childAttributeStorage)
	{
		throw new UnsupportedOperationException("Child attribute storages are not supported for " + this);
	}

	private static boolean isGroupingKey(final IHUKey key)
	{
		return key != null && key.isGrouping();
	}

	private static IHUKey getParent(final IHUKey key)
	{
		if (key == null)
		{
			return null;
		}

		// Get first parent key which is not a grouping key
		IHUKey parentHUKey = key.getParent();
		while (parentHUKey != null)
		{
			if (!isGroupingKey(parentHUKey))
			{
				break;
			}
			parentHUKey = parentHUKey.getParent();
		}

		return parentHUKey;
	}

	private static List<IHUKey> getChildren(final IHUKey key)
	{
		if (key == null)
		{
			return Collections.emptyList();
		}

		final List<IHUKey> result = new ArrayList<>();

		for (final IHUKey childKey : key.getChildren())
		{
			if (isGroupingKey(childKey))
			{
				final List<IHUKey> childOfChildKeys = getChildren(childKey);
				result.addAll(childOfChildKeys);
			}
			else
			{
				result.add(childKey);
			}
		}
		return result;
	}

	public void markDisposed()
	{
		// Do nothing if already disposed
		if (this.disposed)
		{
			return;
		}
		
		this.disposed = true;
		this.disposedTS = System.currentTimeMillis();
		fireAttributeStorageDisposed();
	}

	@Override
	public final boolean assertNotDisposed()
	{
		if (disposed)
		{
			final HUException ex = new HUException("Accessing an alteady dispose HUKeyAttributeStorage shall not be allowed"
					+ "\n Storage: " + this
					+ "\n Disposed on: " + (disposedTS > 0 ? new Date(disposedTS).toString() : "")
					);
			if (HUConstants.isfresh_AttributeStorageFailOnDisposed())
			{
				throw ex;
			}
			else
			{
				logger.log(Level.WARNING, ex.getLocalizedMessage(), ex);
			}

			return false; // already disposed
		}

		return true; // not disposed
	}

	public boolean isDisposed()
	{
		return disposed;
	}

}
