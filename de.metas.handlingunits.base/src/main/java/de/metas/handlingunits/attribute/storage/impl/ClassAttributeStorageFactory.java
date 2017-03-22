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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageListener;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.handlingunits.storage.IHUStorageFactory;

/**
 * A lazy delegate implementation of {@link IAttributeStorageFactory}.
 *
 * This object is instantiated with a {@link IAttributeStorageFactory} implementation class.
 *
 * The implementation class will be instantiated only when needed and all methods will be redirected to that underlying implementation.
 *
 * @author tsa
 *
 */
/* package */class ClassAttributeStorageFactory implements IAttributeStorageFactory
{
	private final Class<? extends IAttributeStorageFactory> factoryClass;
	private IAttributeStorageFactory factory = null;
	private final List<IAttributeStorageListener> attributeStorageListeners = new ArrayList<IAttributeStorageListener>();
	private IHUAttributesDAO huAttributesDAO = null;
	private IHUStorageFactory huStorageFactory = null;

	public ClassAttributeStorageFactory(final Class<? extends IAttributeStorageFactory> factoryClass)
	{
		super();

		Check.assumeNotNull(factoryClass, "factoryClass not null");
		this.factoryClass = factoryClass;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "["
				+ "factoryClass=" + factoryClass
				+ ", factory=" + factory
				+ "]";
	}

	private final IAttributeStorageFactory getDelegate()
	{
		if (factory == null)
		{
			//
			// Create factory instance
			try
			{
				factory = factoryClass.newInstance();
			}
			catch (final Exception e)
			{
				throw new AdempiereException("Failed to instantiate class " + factoryClass, e);
			}

			factory.setHUAttributesDAO(huAttributesDAO);
			factory.setHUStorageFactory(huStorageFactory);

			//
			// Add listeners to factory
			for (final IAttributeStorageListener listener : attributeStorageListeners)
			{
				factory.addAttributeStorageListener(listener);
			}
		}

		return factory;
	}

	@Override
	public void addAttributeStorageListener(final IAttributeStorageListener listener)
	{
		Check.assumeNotNull(listener, "listener not null");
		if (attributeStorageListeners.contains(listener))
		{
			// already added
			return;
		}

		attributeStorageListeners.add(listener);

		//
		// Update factory(the delegate) if is instantiated
		if (factory != null)
		{
			factory.addAttributeStorageListener(listener);
		}
	}

	@Override
	public void removeAttributeStorageListener(IAttributeStorageListener listener)
	{
		if (listener == null)
		{
			return;
		}

		attributeStorageListeners.remove(listener);

		// Unregister the listner from factory(the delegate)
		if (factory != null)
		{
			factory.removeAttributeStorageListener(listener);
		}
	}

	@Override
	public boolean isHandled(final Object model)
	{
		final IAttributeStorageFactory delegate = getDelegate();
		return delegate.isHandled(model);
	}

	@Override
	public IAttributeStorage getAttributeStorage(final Object model)
	{
		final IAttributeStorageFactory delegate = getDelegate();
		return delegate.getAttributeStorage(model);
	}

	@Override
	public IAttributeStorage getAttributeStorageIfHandled(final Object model)
	{
		final IAttributeStorageFactory delegate = getDelegate();
		return delegate.getAttributeStorageIfHandled(model);
	}

	@Override
	public final IHUAttributesDAO getHUAttributesDAO()
	{
		throw new HUException("No IHUAttributesDAO found on " + this);
	}

	@Override
	public void setHUAttributesDAO(final IHUAttributesDAO huAttributesDAO)
	{
		this.huAttributesDAO = huAttributesDAO;

		//
		// Update factory if is instantiated
		if (factory != null)
		{
			factory.setHUAttributesDAO(huAttributesDAO);
		}
	}

	@Override
	public IHUStorageDAO getHUStorageDAO()
	{
		return getHUStorageFactory().getHUStorageDAO();
	}

	@Override
	public void setHUStorageFactory(final IHUStorageFactory huStorageFactory)
	{
		this.huStorageFactory = huStorageFactory;

		//
		// Update factory if is instantiated
		if (factory != null)
		{
			factory.setHUStorageFactory(huStorageFactory);
		}
	}

	@Override
	public IHUStorageFactory getHUStorageFactory()
	{
		return huStorageFactory;
	}
}
