package de.metas.handlingunits.util;

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


import java.util.Comparator;

import org.adempiere.ad.model.util.ModelByIdComparator;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Check;

/**
 * Comparator which is comparing {@link I_M_HU}s by their M_HU_ID.
 * 
 * NOTE: this implementation works exactly the same as {@link ModelByIdComparator} but instead of using {@link InterfaceWrapperHelper#getId(Object)}, we are using {@link I_M_HU#getM_HU_ID()}.
 *
 * @author tsa
 *
 */
public final class HUByIdComparator implements Comparator<I_M_HU>
{
	public static final transient HUByIdComparator instance = new HUByIdComparator();

	private HUByIdComparator()
	{
		super();
	}

	@Override
	public int compare(final I_M_HU hu1, final I_M_HU hu2)
	{
		if (hu1 == hu2)
		{
			return 0;
		}
		Check.assumeNotNull(hu1, "hu1 not null"); // shall not happen
		Check.assumeNotNull(hu2, "hu2 not null"); // shall not happen

		final int huId1 = hu1.getM_HU_ID();
		final int huId2 = hu2.getM_HU_ID();
		return huId1 - huId2;
	}
}
