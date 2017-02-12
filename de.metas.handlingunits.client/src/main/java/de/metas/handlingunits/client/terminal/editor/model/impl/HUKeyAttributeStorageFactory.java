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


import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.impl.AbstractModelAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.impl.NullAttributeStorage;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;

public class HUKeyAttributeStorageFactory extends AbstractModelAttributeStorageFactory<IHUKey, IAttributeStorage>
{
	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("HUKeyAttributeStorageFactory [toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

	@Override
	public boolean isHandled(final Object modelObj)
	{
		if (modelObj == null)
		{
			return false;
		}

		if (modelObj instanceof IHUKey)
		{
			return true;
		}

		return false;
	}

	@Override
	protected IHUKey getModelFromObject(final Object modelObj)
	{
		final IHUKey huKey = (IHUKey)modelObj;
		return huKey;
	}

	@Override
	protected ArrayKey mkKey(final IHUKey model)
	{
		return Util.mkKey(

				model.getId()

				// also include the disposed status in the key
				, model.isDisposed()

				// Because the HUKey is also embedded in HUKeyAttributeStorage,
				// we have to make sure we are not returning same HUKeyAttributeStorage for different HUKeys which are wrapping the same HU.
				// Shall not happen, better to make sure.
				, System.identityHashCode(model)
				);
	}

	@Override
	protected IAttributeStorage createAttributeStorage(final IHUKey model)
	{
		if (model.isGrouping())
		{
			return NullAttributeStorage.instance;
		}

		return new HUKeyAttributeStorage(this, model);
	}

	@Override
	protected void onAttributeStorageRemovedFromCache(IAttributeStorage attributeStorage)
	{
		if (NullAttributeStorage.isNull(attributeStorage))
		{
			return;
		}

		final HUKeyAttributeStorage huKeyAttributeStorage = HUKeyAttributeStorage.cast(attributeStorage);
		removeListenersFromAttributeStorage(huKeyAttributeStorage);
		huKeyAttributeStorage.markDisposed();
	}
}
