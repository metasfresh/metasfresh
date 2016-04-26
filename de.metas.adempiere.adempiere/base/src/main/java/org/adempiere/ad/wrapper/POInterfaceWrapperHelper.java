package org.adempiere.ad.wrapper;

import org.adempiere.model.POWrapper;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class POInterfaceWrapperHelper implements IInterfaceWrapperHelper
{
	@Override
	public boolean canHandled(final Object model)
	{
		return POWrapper.isHandled(model);
	}

	@Override
	public <T> T wrap(final Object model, final Class<T> cl, final boolean useOldValues)
	{
		if (useOldValues)
		{
			return POWrapper.create(model, cl, true);
		}
		else
		{
			// preserve "old values" flag
			return POWrapper.create(model, cl);
		}
	}

	@Override
	public void refresh(Object model, boolean discardChanges)
	{
		POWrapper.refresh(model);
	}

	@Override
	public void refresh(Object model, String trxName)
	{
		POWrapper.refresh(model, trxName);
	}

	@Override
	public boolean hasModelColumnName(Object model, String columnName)
	{
		return POWrapper.hasModelColumnName(model, columnName);
	}

}
