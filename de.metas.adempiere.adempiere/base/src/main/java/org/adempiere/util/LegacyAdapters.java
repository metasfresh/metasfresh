package org.adempiere.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;

import de.metas.util.Check;

/**
 * Helper methods that can be used when moving business logic out of PO subclasses.
 * 
 */
public class LegacyAdapters
{
	public static <ST, TT extends PO> List<TT> convertToPOList(final List<ST> list)
	{
		if (list == null)
		{
			return null;
		}

		final List<TT> result = new ArrayList<TT>(list.size());
		for (int i = 0; i < list.size(); i++)
		{
			final ST model = list.get(i);
			final TT po;
			if (model == null)
			{
				// we are taking as it is
				po = null;
			}
			else
			{
				po = convertToPO(model);
			}

			result.add(po);
		}

		return result;
	}

	public static <ST, TT extends PO> TT[] convertToPOArray(final List<ST> list, final Class<TT> clazz)
	{
		if (list == null)
		{
			return null;
		}
		
		Check.assumeNotNull(clazz, "Param 'clazz' is not null");

		// Use Array native method to create array of a type only known at run time
		@SuppressWarnings("unchecked")
		final TT[] resultArray = (TT[])Array.newInstance(clazz, list.size());
		
		return convertToPOList(list).toArray(resultArray);
	}

	public static <ST, TT extends PO> TT convertToPO(final ST model)
	{
		if (model == null)
		{
			return null;
		}

		@SuppressWarnings("unchecked")
		final TT po = (TT)InterfaceWrapperHelper.getPO(model);

		Check.assumeNotNull(po, "po created from {} is not null", model);
		return po;
	}
}
