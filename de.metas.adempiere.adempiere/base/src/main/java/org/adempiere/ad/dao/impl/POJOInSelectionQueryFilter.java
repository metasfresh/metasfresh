package org.adempiere.ad.dao.impl;

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


import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;

/**
 * Accepts only the records which are in selection (see {@link POJOLookupMap#isInSelection(int, int)}.
 * 
 * WARNING: this filter works only in database decoupled mode.
 * 
 * @author tsa
 *
 * @param <T>
 */
public class POJOInSelectionQueryFilter<T> implements IQueryFilter<T>
{
	private final int selectionId;

	public POJOInSelectionQueryFilter(final int selectionId)
	{
		super();
		Check.assume(selectionId > 0, "selectionId > 0");
		this.selectionId = selectionId;
	}

	@Override
	public String toString()
	{
		return "InSelection-" + selectionId;
	}

	@Override
	public boolean accept(final T model)
	{
		if (model == null)
		{
			return false;
		}

		final int id = InterfaceWrapperHelper.getId(model);
		return POJOLookupMap.get().isInSelection(selectionId, id);
	}

	public int getSelectionId()
	{
		return selectionId;
	}
}
