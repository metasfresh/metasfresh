package de.metas.handlingunits.attribute.impl;

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


import org.adempiere.mm.attributes.api.IAttributeSet;

import de.metas.handlingunits.IHUAware;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.model.I_M_HU;

public class HUAttributesBL implements IHUAttributesBL
{
	@Override
	public I_M_HU getM_HU_OrNull(final IAttributeSet attributeSet)
	{
		if (attributeSet instanceof IHUAware)
		{
			final IHUAware huAware = (IHUAware)attributeSet;
			return huAware.getM_HU();
		}
		else
		{
			return null;
		}
	}

	@Override
	public I_M_HU getM_HU(final IAttributeSet attributeSet)
	{
		final I_M_HU hu = getM_HU_OrNull(attributeSet);
		if (hu == null)
		{
			throw new IllegalArgumentException("Cannot get M_HU from " + attributeSet);
		}

		return hu;
	}
}
