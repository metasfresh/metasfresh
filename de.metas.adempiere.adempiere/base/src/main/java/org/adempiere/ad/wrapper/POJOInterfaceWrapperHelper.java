package org.adempiere.ad.wrapper;

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

public class POJOInterfaceWrapperHelper implements IInterfaceWrapperHelper
{

	@Override
	public boolean canHandled(Object model)
	{
		return POJOWrapper.isHandled(model);
	}

	@Override
	public <T> T wrap(Object model, Class<T> modelClass, boolean useOldValues)
	{
		if (useOldValues)
		{
			return POJOWrapper.create(model, modelClass, true);
		}
		else
		{
			// preserve "old values" flag
			return POJOWrapper.create(model, modelClass);
		}
	}

	@Override
	public void refresh(Object model, boolean discardChanges)
	{
		POJOWrapper.refresh(model, discardChanges, POJOWrapper.getTrxName(model));
	}

	@Override
	public void refresh(Object model, String trxName)
	{
		final boolean discardChanges = false;
		POJOWrapper.refresh(model, discardChanges, trxName);
	}

	@Override
	public boolean hasModelColumnName(Object model, String columnName)
	{
		final POJOWrapper wrapper = POJOWrapper.getWrapper(model);
		return wrapper.hasColumnName(columnName);
	}
}
