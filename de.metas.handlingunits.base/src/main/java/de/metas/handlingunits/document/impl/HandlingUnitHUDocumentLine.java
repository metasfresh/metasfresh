package de.metas.handlingunits.document.impl;

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


import org.adempiere.util.Check;
import org.compiere.model.I_C_BPartner;

import de.metas.handlingunits.document.IHUAllocations;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.storage.IProductStorage;

/* package */class HandlingUnitHUDocumentLine extends AbstractHUDocumentLine
{
	private final I_M_HU_Item item;

	public HandlingUnitHUDocumentLine(final I_M_HU_Item item, final IProductStorage storage)
	{
		super(storage, item);

		Check.assumeNotNull(item, "item not null");
		this.item = item;
	}

	@Override
	public IHUAllocations getHUAllocations()
	{
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public I_M_HU_Item getInnerHUItem()
	{
		return item;
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		return null; // N/A
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		return -1; // N/A
	}
}
