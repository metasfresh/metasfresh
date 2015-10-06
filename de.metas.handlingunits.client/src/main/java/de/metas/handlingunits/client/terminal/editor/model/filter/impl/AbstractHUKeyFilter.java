package de.metas.handlingunits.client.terminal.editor.model.filter.impl;

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
import java.util.List;

import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.filter.IHUKeyFilter;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IProductStorage;

/**
 * Contains helper methods for HU Key Filters
 *
 * @author al
 */
/* package */abstract class AbstractHUKeyFilter implements IHUKeyFilter
{
	protected final List<IProductStorage> getProductStorages(final IHUKey key)
	{
		final List<IProductStorage> productStorages = new ArrayList<>();
		if (!(key instanceof HUKey)) // navigate down
		{
			final List<IHUKey> children = key.getChildren();
			for (final IHUKey child : children)
			{
				final List<IProductStorage> childStorages = getProductStorages(child);
				productStorages.addAll(childStorages);
			}
			return productStorages; // exit method once product storages are collected from children (current key should not contain any)
		}
		final HUKey huKey = HUKey.cast(key);
		final List<IHUProductStorage> huKeyProductStorages = huKey.getProductStorages();

		productStorages.addAll(huKeyProductStorages);
		return productStorages;
	}
}
