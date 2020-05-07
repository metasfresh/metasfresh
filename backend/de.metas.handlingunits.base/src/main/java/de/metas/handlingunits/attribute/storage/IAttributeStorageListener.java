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


import org.adempiere.mm.attributes.spi.IAttributeValueContext;

import de.metas.handlingunits.attribute.IAttributeValue;

/**
 * Note: currently this is used to collect attribute related events, so they can be persisted all at ones
 *
 * @author ts
 *
 */
public interface IAttributeStorageListener
{
	void onAttributeValueCreated(IAttributeValueContext attributeValueContext, IAttributeStorage storage, IAttributeValue attributeValue);

	/**
	 * Called when a particular IAttributeValue is changed.
	 * 
	 * @param attributeValueContext
	 * @param storage
	 * @param attributeValue
	 * @param valueOld
	 */
	void onAttributeValueChanged(IAttributeValueContext attributeValueContext, IAttributeStorage storage, IAttributeValue attributeValue, Object valueOld);

	void onAttributeValueDeleted(IAttributeValueContext attributeValueContext, IAttributeStorage storage, IAttributeValue attributeValue);

	/**
	 * Called after an attribute storage is disposed.
	 * 
	 * @param storage
	 */
	void onAttributeStorageDisposed(final IAttributeStorage storage);
}
