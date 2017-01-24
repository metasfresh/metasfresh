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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.attribute.IHUAttributesDAO;

public interface IAttributeStorageFactoryService extends ISingletonService
{
	/**
	 * Calls {@link #createHUAttributeStorageFactory(IHUAttributesDAO, IHUContext)} with teh default {@link IHUAttributesDAO} implementation (no decoupled or on-commit saves).
	 * 
	 * @param huContext
	 * @return
	 */
	IAttributeStorageFactory createHUAttributeStorageFactory(IHUContext huContext);

	/**
	 * 
	 * @param huAttributesDAO
	 * @param huContext optional may be {@code null}. Used to get correct initial tare attribute values in aggregate VHUs (gh #460).
	 * @return
	 */
	IAttributeStorageFactory createHUAttributeStorageFactory(IHUAttributesDAO huAttributesDAO, IHUContext huContext);

	void addAttributeStorageFactory(Class<? extends IAttributeStorageFactory> attributeStorageFactoryClass);

}
