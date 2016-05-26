package de.metas.ui.web.vaadin.window.view;

import com.vaadin.ui.Component;

import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.shared.datatype.PropertyPath;
import de.metas.ui.web.window.shared.datatype.PropertyValuesDTO;
import de.metas.ui.web.window.shared.descriptor.ViewPropertyDescriptor;

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
	public void setListener(final WindowViewListener listener)
	{
		// nothing
	}

	@Override
	public void setRootPropertyDescriptor(final ViewPropertyDescriptor rootPropertyDescriptor)
	{
		// nothing
	}

	@Override
	public void setNextRecordEnabled(final boolean enabled)
	{
		// nothing
	}

	@Override
	public void setPreviousRecordEnabled(final boolean enabled)
	{
		// nothing
	}

	@Override
	public void setProperties(final PropertyValuesDTO propertiesAsMap)
	{
		// nothing
	}

	@Override
	public void setProperty(final PropertyPath propertyPath, final Object value)
	{
		// nothing
	}

	@Override
	public void gridNewRow(final PropertyName gridPropertyName, final Object rowId, final PropertyValuesDTO rowValues)
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
	public void showError(final String message)
	{
		// nothing
	}
}
