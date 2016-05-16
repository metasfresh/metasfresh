package de.metas.ui.web.vaadin.window.editor;

import java.util.List;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

import de.metas.ui.web.vaadin.window.PropertyName;

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

@SuppressWarnings("serial")
public class LabelEditor extends AbstractEditor
{
	private final Label valueField = new Label();

	public LabelEditor(final PropertyName propertyName)
	{
		super(propertyName);
		setCompositionRoot(valueField);

		valueField.addValueChangeListener(new ValueChangeListener()
		{

			@Override
			public void valueChange(ValueChangeEvent event)
			{
				listener().valueChange(propertyName, valueField.getValue());
			}
		});
	}

	@Override
	public void setValue(final PropertyName propertyName, final Object value)
	{
		if (getPropertyName().equals(propertyName))
		{
			final String valueView = convertToView(value);
			valueField.setValue(valueView);
		}
	}

	private String convertToView(final Object valueObj)
	{
		return valueObj == null ? "" : valueObj.toString();
	}

	public void setContentMode(final ContentMode contentMode)
	{
		valueField.setContentMode(contentMode);
	}
	
	@Override
	public boolean isAddingChildEditorsAllowed()
	{
		return false;
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

	@Override
	protected Label createLabelComponent()
	{
		return null; // no label, this is a label by itself
	}

}
