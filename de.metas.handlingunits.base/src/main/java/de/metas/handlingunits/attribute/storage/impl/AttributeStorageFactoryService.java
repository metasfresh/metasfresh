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


import java.util.concurrent.CopyOnWriteArrayList;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.impl.HUAttributesDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;

public class AttributeStorageFactoryService implements IAttributeStorageFactoryService
{
	private final CopyOnWriteArrayList<Class<? extends IAttributeStorageFactory>> attributeStorageFactories = new CopyOnWriteArrayList<>();

	public AttributeStorageFactoryService()
	{
		super();

		//
		// Setup Default Attribute Storage Factories
		addAttributeStorageFactory(HUAttributeStorageFactory.class);
		addAttributeStorageFactory(ASIAttributeStorageFactory.class);
		addAttributeStorageFactory(ASIAwareAttributeStorageFactory.class);
	}

	@Override
	public IAttributeStorageFactory createHUAttributeStorageFactory(final IHUContext huContext)
	{
		final IHUAttributesDAO huAttributesDAO = HUAttributesDAO.instance;
		return createHUAttributeStorageFactory(huAttributesDAO, huContext);
	}

	@Override
	public IAttributeStorageFactory createHUAttributeStorageFactory(final IHUAttributesDAO huAttributesDAO, final IHUContext huContext)
	{
		final CompositeAttributeStorageFactory factory = new CompositeAttributeStorageFactory(huContext);
		factory.setHUAttributesDAO(huAttributesDAO);
		factory.addAttributeStorageFactoryClasses(attributeStorageFactories);
		return factory;
	}

	@Override
	public void addAttributeStorageFactory(final Class<? extends IAttributeStorageFactory> attributeStorageFactoryClass)
	{
		attributeStorageFactories.addIfAbsent(attributeStorageFactoryClass);
	}

}
