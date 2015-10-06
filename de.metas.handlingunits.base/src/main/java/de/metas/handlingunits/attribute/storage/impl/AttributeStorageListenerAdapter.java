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


import org.adempiere.mm.attributes.spi.IAttributeValueContext;

import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageListener;

public class AttributeStorageListenerAdapter implements IAttributeStorageListener
{
	@Override
	public void onAttributeValueCreated(final IAttributeValueContext attributeValueContext, final IAttributeStorage storage, final IAttributeValue attributeValue)
	{
		// nothing
	}

	@Override
	public void onAttributeValueChanged(final IAttributeValueContext attributeValueContext, final IAttributeStorage storage, final IAttributeValue attributeValue, final Object valueOld)
	{
		// nothing
	}

	@Override
	public void onAttributeValueDeleted(final IAttributeValueContext attributeValueContext, final IAttributeStorage storage, final IAttributeValue attributeValue)
	{
		// nothing
	}

	@Override
	public void onAttributeStorageDisposed(final IAttributeStorage storage)
	{
		// nothing
	}
}
