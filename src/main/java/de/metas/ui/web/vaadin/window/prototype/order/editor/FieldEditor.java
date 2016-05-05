package de.metas.ui.web.vaadin.window.prototype.order.editor;

import java.util.Collection;
import java.util.List;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;

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
public abstract class FieldEditor<T> extends AbstractEditor implements Field<T>
{
	static final String STYLE_Field = "mf-editor-field";
	private final AbstractField<T> valueField;

	public FieldEditor(final PropertyDescriptor descriptor)
	{
		super(descriptor);
		addStyleName(STYLE_Field);

		valueField = createValueField();
		valueField.addStyleName(STYLE_ValueField);
		valueField.setCaption(descriptor.getPropertyName().toString());

		final Component content = valueField;
		content.setSizeFull();
		setCompositionRoot(content);

		valueField.addValueChangeListener(new Property.ValueChangeListener()
		{

			@Override
			public void valueChange(final Property.ValueChangeEvent event)
			{
				final T valueNew = valueField.getValue();
				listener().valueChange(getPropertyName(), valueNew);
			}
		});
		
		updateUI();
	}

	protected abstract AbstractField<T> createValueField();

	protected AbstractField<T> getValueField()
	{
		return valueField;
	}

	@Override
	public final void setValue(final T value)
	{
		setValue(getPropertyName(), value);
	}
	
	@Override
	public void setValue(final PropertyName propertyName, final Object value)
	{
		if (getPropertyName().equals(propertyName))
		{
			final T valueView = convertToView(value);
			valueField.setValue(valueView);
		}
	}

	protected abstract T convertToView(final Object valueObj);

	@Override
	public T getValue()
	{
		final T value = valueField.getValue();
		return value;
	}
	
	@Override
	public final boolean isAddingChildEditorsAllowed()
	{
		return false;
	}

	@Override
	public final void addChildEditor(final de.metas.ui.web.vaadin.window.prototype.order.editor.Editor editor)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final List<de.metas.ui.web.vaadin.window.prototype.order.editor.Editor> getChildEditors()
	{
		return ImmutableList.of();
	}

	@Override
	public void focus()
	{
		valueField.focus();
	}

	@Override
	public boolean isInvalidCommitted()
	{
		return valueField.isInvalidCommitted();
	}

	@Override
	public void setInvalidCommitted(final boolean isCommitted)
	{
		valueField.setInvalidCommitted(isCommitted);
	}

	@Override
	public void commit() throws SourceException, InvalidValueException
	{
		valueField.commit();
	}

	@Override
	public void discard() throws SourceException
	{
		valueField.discard();
	}

	@Override
	public void setBuffered(final boolean buffered)
	{
		valueField.setBuffered(buffered);
	}

	@Override
	public boolean isBuffered()
	{
		return valueField.isBuffered();
	}

	@Override
	public boolean isModified()
	{
		return valueField.isModified();
	}

	@Override
	public void addValidator(final Validator validator)
	{
		valueField.addValidator(validator);
	}

	@Override
	public void removeValidator(final Validator validator)
	{
		valueField.removeValidator(validator);
	}

	@Override
	public void removeAllValidators()
	{
		valueField.removeAllValidators();
	}

	@Override
	public Collection<Validator> getValidators()
	{
		return valueField.getValidators();
	}

	@Override
	public boolean isValid()
	{
		return valueField.isValid();
	}

	@Override
	public void validate() throws InvalidValueException
	{
		valueField.validate();
	}

	@Override
	public boolean isInvalidAllowed()
	{
		return valueField.isInvalidAllowed();
	}

	@Override
	public void setInvalidAllowed(final boolean invalidValueAllowed) throws UnsupportedOperationException
	{
		valueField.setInvalidAllowed(true);
	}

	@Override
	public Class<? extends T> getType()
	{
		// TODO
		return null;
		// final Class<? extends IT> type = valueField.getType();
		// return type;
	}

	@Override
	public void addValueChangeListener(final com.vaadin.data.Property.ValueChangeListener listener)
	{
		valueField.addValueChangeListener(listener);
	}

	@Override
	@Deprecated
	public void addListener(final com.vaadin.data.Property.ValueChangeListener listener)
	{
		valueField.addListener(listener);
	}

	@Override
	public void removeValueChangeListener(final com.vaadin.data.Property.ValueChangeListener listener)
	{
		valueField.removeValueChangeListener(listener);
	}

	@Override
	@Deprecated
	public void removeListener(final com.vaadin.data.Property.ValueChangeListener listener)
	{
		valueField.removeListener(listener);
	}

	@Override
	public void valueChange(final com.vaadin.data.Property.ValueChangeEvent event)
	{
		valueField.valueChange(event);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void setPropertyDataSource(final Property newDataSource)
	{
		valueField.setPropertyDataSource(newDataSource);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Property getPropertyDataSource()
	{
		return valueField.getPropertyDataSource();
	}

	@Override
	public int getTabIndex()
	{
		return valueField.getTabIndex();
	}

	@Override
	public void setTabIndex(final int tabIndex)
	{
		valueField.setTabIndex(tabIndex);
	}

	@Override
	public boolean isRequired()
	{
		return valueField.isRequired();
	}

	@Override
	public void setRequired(final boolean required)
	{
		valueField.setRequired(required);
	}

	@Override
	public void setRequiredError(final String requiredMessage)
	{
		valueField.setRequiredError(requiredMessage);
	}

	@Override
	public String getRequiredError()
	{
		return valueField.getRequiredError();
	}

	@Override
	public boolean isEmpty()
	{
		return valueField.isEmpty();
	}

	@Override
	public void clear()
	{
		valueField.clear();
	}
	
	private void updateUI()
	{
		// TODO: move this to model
		
		final PropertyDescriptor propertyDescriptor = getPropertyDescriptor();
		final boolean visible = propertyDescriptor != null && propertyDescriptor.getLayoutInfo().isDisplayed();
		
		setVisible(visible);
		final Label label = getLabel();
		if (label != null)
		{
			label.setVisible(visible);
		}
	}

}
