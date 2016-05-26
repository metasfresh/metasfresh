package de.metas.ui.web.vaadin.window.editor;

import java.util.List;
import java.util.Objects;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.vaadin.ui.TextArea;

import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.shared.datatype.LookupValue;
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

@SuppressWarnings("serial")
public class LocationEditor extends AbstractEditor
{
	private LookupValue value;
	private TextArea valueField;

	public LocationEditor(ViewPropertyDescriptor descriptor)
	{
		super(descriptor);
		
		
		this.valueField = new TextArea();
		valueField.setSizeFull();
		valueField.setReadOnly(true);
		valueField.addContextClickListener(event -> showPopup());
		
		setCompositionRoot(valueField);
	}

	@Override
	public void setValue(final PropertyName propertyName, final Object value)
	{
		valueField.setReadOnly(false);
		try
		{
			if (getPropertyName().equals(propertyName))
			{
				final LookupValue lookupValue = LookupValue.cast(value);
				final LookupValue valueOld = this.value;
				this.value = lookupValue;
				
				valueField.setValue(lookupValue == null ? "" : lookupValue.getDisplayName());
				
				if(!Objects.equals(valueOld, value))
				{
					listener().valueChange(getPropertyPath(), this.value);
				}
			}
		}
		finally
		{
			valueField.setReadOnly(true);
		}
	}

	public void setValue(final LookupValue value)
	{
		setValue(getPropertyName(), value);
	}

	private LookupValue getValue()
	{
		return value;
	}

	@Override
	public void addChildEditor(Editor editor)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Editor> getChildEditors()
	{
		return ImmutableList.of();
	}

	protected void showPopup()
	{
		final LocationEditorPopup popup = new LocationEditorPopup(getValue());
		popup.addCloseListener(e -> setValue(popup.getLookupValue()));

		popup.center();
		getUI().addWindow(popup);
	}
}
