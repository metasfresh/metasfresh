package de.metas.handlingunits.attribute.storage;

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


import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.handlingunits.storage.IHUStorageFactory;

/**
 * Attribute Storage Factory. Hint: use {@link IAttributeStorageFactoryService} to get an instance.
 *
 * @author tsa
 *
 */
public interface IAttributeStorageFactory
{
	/**
	 * Add {@link IAttributeStorageListener} to be added to all {@link IAttributeStorage}s that are factored by this factory.
	 *
	 * @param listener
	 */
	void addAttributeStorageListener(IAttributeStorageListener listener);

	/**
	 * Removes {@link IAttributeStorageListener} if it was registered.
	 *
	 * If it was not, this method silently ignores it.
	 *
	 * @param listener
	 */
	void removeAttributeStorageListener(IAttributeStorageListener listener);

	/**
	 *
	 * @param model
	 * @return true if given model can be handled by this factory
	 */
	boolean isHandled(Object model);

	/**
	 *
	 * @param model
	 * @return attribute storage; never return null
	 */
	IAttributeStorage getAttributeStorage(Object model);

	/**
	 * Gets {@link IAttributeStorage} if <code>model</code> is handled or <code>null</code> otherwise.
	 *
	 * NOTE: this method was introduced for the sake of optimizations, because some factories cannot tell if the model is handled until they fetch them.
	 *
	 * @param model
	 * @return attribute storage or null
	 */
	IAttributeStorage getAttributeStorageIfHandled(Object model);

	IHUAttributesDAO getHUAttributesDAO();

	void setHUAttributesDAO(IHUAttributesDAO huAttributesDAO);

	IHUStorageDAO getHUStorageDAO();

	IHUStorageFactory getHUStorageFactory();

	void setHUStorageFactory(IHUStorageFactory huStorageFactory);
}
