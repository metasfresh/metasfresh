package de.metas.ui.web.vaadin.window.prototype.order.view;

import com.vaadin.ui.Component;

import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;
import de.metas.ui.web.vaadin.window.prototype.order.model.PropertyValuesDTO;

/*
 * #%L
 * metasfresh-webui
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

public class NullWindowView implements WindowView
{
	public static final transient NullWindowView instance = new NullWindowView();
	
	private NullWindowView()
	{
		super();
	}

	@Override
	public Component getComponent()
	{
		return null;
	}

	@Override
	public void setListener(WindowViewListener listener)
	{
		// nothing
	}

	@Override
	public void setRootPropertyDescriptor(PropertyDescriptor rootPropertyDescriptor)
	{
		// nothing
	}

	@Override
	public void setNextRecordEnabled(boolean enabled)
	{
		// nothing
	}

	@Override
	public void setPreviousRecordEnabled(boolean enabled)
	{
		// nothing
	}

	@Override
	public void setProperties(PropertyValuesDTO propertiesAsMap)
	{
		// nothing
	}

	@Override
	public void setProperty(PropertyName propertyName, Object value)
	{
		// nothing
	}

	@Override
	public void setGridProperty(PropertyName gridPropertyName, Object rowId, PropertyName propertyName, Object value)
	{
		// nothing
	}

	@Override
	public void gridNewRow(PropertyName gridPropertyName, Object rowId, PropertyValuesDTO rowValues)
	{
		// nothing
	}

	@Override
	public void commitChanges()
	{
		// nothing
	}

	@Override
	public void confirmDiscardChanges()
	{
		// nothing
	}

	@Override
	public void showError(String message)
	{
		// nothing
	}
}
