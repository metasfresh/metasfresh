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

import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageListener;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.ArrayList;
import java.util.List;

public class CompositeAttributeStorageFactory implements IAttributeStorageFactory
{
	private IHUAttributesDAO huAttributesDAO;
	private IHUStorageFactory huStorageFactory;

	private final List<IAttributeStorageFactory> factories = new ArrayList<>();

	private final List<IAttributeStorageListener> attributeStorageListeners = new ArrayList<>();

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
		// Add listener to current existing factories
		for (final IAttributeStorageFactory factory : factories)
		{
			factory.addAttributeStorageListener(listener);
		}
	}

	@Override
	public void removeAttributeStorageListener(final IAttributeStorageListener listener)
	{
		if (listener == null)
		{
			return;
		}

		attributeStorageListeners.remove(listener);

		//
		// Unregister the listener from current existing factories:
		for (final IAttributeStorageFactory factory : factories)
		{
			factory.removeAttributeStorageListener(listener);
		}
	}

	@Override
	public String toString()
	{
		return "CompositeAttributeStorageFactory [factories=" + factories + "]";
	}

	public void addAttributeStorageFactory(final IAttributeStorageFactory factory)
	{
		Check.assumeNotNull(factory, "factory not null");
		factories.add(factory);

		//
		// Add listeners to factory
		for (final IAttributeStorageListener listener : attributeStorageListeners)
		{
			factory.addAttributeStorageListener(listener);
		}

		factory.setHUAttributesDAO(huAttributesDAO);
		factory.setHUStorageFactory(huStorageFactory);
	}

	public void addAttributeStorageFactory(final Class<? extends IAttributeStorageFactory> factoryClass)
	{
		final IAttributeStorageFactory factory = new ClassAttributeStorageFactory(factoryClass);
		addAttributeStorageFactory(factory);
	}

	public void addAttributeStorageFactoryClasses(final List<Class<? extends IAttributeStorageFactory>> factoryClasses)
	{
		if (factoryClasses == null || factoryClasses.isEmpty())
		{
			return;
		}

		for (final Class<? extends IAttributeStorageFactory> factoryClass : factoryClasses)
		{
			addAttributeStorageFactory(factoryClass);
		}
	}

	@Override
	public boolean isHandled(final Object model)
	{
		for (final IAttributeStorageFactory factory : factories)
		{
			if (factory.isHandled(model))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public IAttributeStorage getAttributeStorage(@NonNull final Object model)
	{
		final IAttributeStorage storage = getAttributeStorageIfHandled(model);
		if (storage == null)
		{
			throw new AdempiereException("None of the registered factories can handle the given model")
					.appendParametersToMessage()
					.setParameter("model", model)
					.setParameter("this instance", this);
		}

		return storage;
	}

	@Override
	public IAttributeStorage getAttributeStorageIfHandled(final Object model)
	{
		for (final IAttributeStorageFactory factory : factories)
		{
			final IAttributeStorage storage = factory.getAttributeStorageIfHandled(model);
			if (storage != null)
			{
				return storage;
			}
		}

		return null;
	}

	@Override
	public final IHUAttributesDAO getHUAttributesDAO()
	{
		Check.assumeNotNull(huAttributesDAO, "huAttributesDAO not null");
		return huAttributesDAO;
	}

	@Override
	public void setHUAttributesDAO(final IHUAttributesDAO huAttributesDAO)
	{
		this.huAttributesDAO = huAttributesDAO;

		for (final IAttributeStorageFactory factory : factories)
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
	public IHUStorageFactory getHUStorageFactory()
	{
		return huStorageFactory;
	}

	@Override
	public void setHUStorageFactory(final IHUStorageFactory huStorageFactory)
	{
		this.huStorageFactory = huStorageFactory;

		for (final IAttributeStorageFactory factory : factories)
		{
			factory.setHUStorageFactory(huStorageFactory);
		}
	}

	@Override
	public void flush()
	{
		for (final IAttributeStorageFactory factory : factories)
		{
			factory.flush();
		}
	}
}
