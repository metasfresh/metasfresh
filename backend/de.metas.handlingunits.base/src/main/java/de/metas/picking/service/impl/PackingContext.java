package de.metas.picking.service.impl;

/*
 * #%L
 * de.metas.fresh.base
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


import java.util.Properties;

import org.adempiere.util.Check;

import de.metas.picking.service.IPackingContext;
import de.metas.picking.service.PackingItemsMap;

public class PackingContext implements IPackingContext
{
	private final Properties ctx;
	private Integer packingItemsMapKey;
	private PackingItemsMap packingItems;

	public PackingContext(final Properties ctx)
	{
		super();

		Check.assumeNotNull(ctx, "ctx not null");
		this.ctx = ctx;
	}

	@Override
	public Properties getCtx()
	{
		return ctx;
	}

	@Override
	public void setPackingItemsMapKey(final int packingItemsMapKey)
	{
		this.packingItemsMapKey = packingItemsMapKey;
	}

	@Override
	public int getPackingItemsMapKey()
	{
		Check.assumeNotNull(packingItemsMapKey, "packingItemsMapKey set");
		return packingItemsMapKey;
	}

	@Override
	public void setPackingItemsMap(final PackingItemsMap packingItems)
	{
		this.packingItems = packingItems;
	}

	@Override
	public PackingItemsMap getPackingItemsMap()
	{
		Check.assumeNotNull(packingItems, "packingItems set");
		return packingItems;
	}
}
