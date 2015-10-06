package de.metas.storage.spi.hu.impl;

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
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Locator;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;

/**
 * Part of the {@link HUStorageRecord} which provides all informations that are coming from {@link I_M_HU}.
 *
 * @author tsa
 *
 */
/* package */class HUStorageRecord_HUPart
{
	private final I_M_HU hu;
	private final IAttributeStorage huAttributes;

	public HUStorageRecord_HUPart(final I_M_HU hu, final IAttributeStorage huAttributes)
	{
		super();
		Check.assumeNotNull(hu, "hu not null");
		this.hu = hu;

		Check.assumeNotNull(huAttributes, "huAttributes not null");
		this.huAttributes = huAttributes;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public IAttributeStorage getAttributes()
	{
		return huAttributes;
	}

	public I_M_Locator getM_Locator()
	{
		return hu.getM_Locator();
	}

	public I_C_BPartner getC_BPartner()
	{
		return hu.getC_BPartner();
	}

	public I_C_BPartner_Location getC_BPartner_Location()
	{
		if (hu.getC_BPartner_ID() <= 0)
		{
			return null;
		}
		return hu.getC_BPartner_Location();
	}

	public I_M_HU getM_HU()
	{
		return hu;
	}
}
