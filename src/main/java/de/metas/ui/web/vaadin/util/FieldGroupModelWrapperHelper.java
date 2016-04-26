package de.metas.ui.web.vaadin.util;

import org.adempiere.ad.wrapper.IInterfaceWrapperHelper;

import de.metas.ui.web.vaadin.window.components.IFieldGroup;

/*
 * #%L
 * de.metas.ui.web.vaadin
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

public class FieldGroupModelWrapperHelper implements IInterfaceWrapperHelper
{
	public static final transient FieldGroupModelWrapperHelper instance = new FieldGroupModelWrapperHelper();
	
	private FieldGroupModelWrapperHelper()
	{
		super();
	}
	
	@Override
	public boolean canHandled(Object model)
	{
		if (model == null)
		{
			return false;
		}
		else if (model instanceof IFieldGroup)
		{
			return true;
		}

		return FieldGroupModelWrapper.getWrapper(model) != null;
	}

	@Override
	public <T> T wrap(Object model, Class<T> modelClass, boolean useOldValues)
	{
		return FieldGroupModelWrapper.wrap(model, modelClass, useOldValues);
	}

	@Override
	public void refresh(Object model, boolean discardChanges)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void refresh(Object model, String trxName)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasModelColumnName(Object model, String columnName)
	{
		final IFieldGroup fieldGroup = FieldGroupModelWrapper.getFieldGroup(model);
		if(fieldGroup == null)
		{
			return false;
		}
		
		return fieldGroup.getFieldDescriptor(columnName) != null;
	}

}
