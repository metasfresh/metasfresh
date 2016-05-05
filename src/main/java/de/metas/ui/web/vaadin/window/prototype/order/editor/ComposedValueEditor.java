package de.metas.ui.web.vaadin.window.prototype.order.editor;

import java.util.List;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;

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
public class ComposedValueEditor extends AbstractEditor
{
	private static final String STYLE = "mf-editor-composed";
	private static final String STYLE_Description = "mf-editor-description";
	
	//
	// UI
	private final TextField valueField;
	private final Label description;
	
	//
	// State
	@SuppressWarnings("unused")
	private ComposedValue value;

	public ComposedValueEditor(final PropertyDescriptor descriptor)
	{
		super(descriptor);
		addStyleName(STYLE);
		addStyleName(FieldEditor.STYLE_Field);

		valueField = new TextField();
		valueField.setEnabled(false);
		valueField.addStyleName(STYLE_ValueField);
		
		description = new Label();
		description.addStyleName(STYLE_Description);
		description.setContentMode(ContentMode.HTML);

		final CssLayout content = new CssLayout(valueField, description);
		content.setSizeFull();
		setCompositionRoot(content);
	}

	@Override
	public void setValue(final PropertyName propertyName, final Object value)
	{
		if(getPropertyName().equals(propertyName))
		{
			final ComposedValue composedValue = ComposedValue.cast(value);
			this.value = composedValue;
			
			if (composedValue == null)
			{
				valueField.setValue("");
				description.setValue("");
			}
			else
			{
				valueField.setValue(composedValue.getDisplayName());
				description.setValue(composedValue.getLongDisplayName());
			}
		}
	}
	
	@Override
	public boolean isAddingChildEditorsAllowed()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void addChildEditor(Editor editor)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public List<Editor> getChildEditors()
	{
		// TODO Auto-generated method stub
		return ImmutableList.of();
	}

	@Override
	protected Label createLabelComponent()
	{
		return null; // no label
	}
}
