package de.metas.ui.web.vaadin.window.components;

import java.util.Collection;

import org.compiere.util.DisplayType;

import com.google.common.base.Preconditions;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Field;

import de.metas.ui.web.vaadin.window.WindowContext;
import de.metas.ui.web.vaadin.window.descriptor.DataFieldPropertyDescriptor;
import de.metas.ui.web.vaadin.window.descriptor.DataRowDescriptor;
import de.metas.ui.web.vaadin.window.model.DataFieldProperty;
import de.metas.ui.web.vaadin.window.model.DataRowItem;

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
public class AWindowTabFieldGroup extends FieldGroup implements IFieldGroup
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final WindowContext windowContext;
	private final FieldEditorFactory fieldEditorFactory;
	private final DataRowDescriptor descriptor;

	private IFieldGroupContext fieldValuesContext = new FieldValuesContext();
	private final CompositeFieldDependenciesBinder dependencyBinders = new CompositeFieldDependenciesBinder()
			.add(new ReadOnlyFieldDependenciesBinder())
			.add(new DisplayLogicFieldDependenciesBinder())
			.add(new MandatoryFieldDependenciesBinder())
			.add(new CalloutFieldDependenciesBinder())
	//
	;

	public AWindowTabFieldGroup(final Builder builder)
	{
		super();

		windowContext = builder.windowContext;
		fieldEditorFactory = new FieldEditorFactory(builder.windowContext, builder.gridMode);
		descriptor = Preconditions.checkNotNull(builder.descriptor, "descriptor is null");

		if (builder.buffered != null)
		{
			setBuffered(builder.buffered);
		}
	}

	@Override
	public IFieldGroupContext getFieldGroupContext()
	{
		return fieldValuesContext;
	}

	@Override
	public DataRowDescriptor getDescriptor()
	{
		return descriptor;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public <T extends Field> T buildAndBind(final String caption, final Object propertyId, final Class<T> fieldType) throws BindException
	{
		final DataFieldPropertyDescriptor fieldDescriptor = getFieldDescriptor(propertyId);
		if (fieldDescriptor == null)
		{
			throw new BindException("Property " + propertyId + " not supported");
		}
		if (!fieldDescriptor.isDisplayable())
		{
			return null;
			// throw new BindException("Property " + propertyId + " is not displayed at all");
		}

		// FIXME: debugging
		// if (!"C_DocTypeTarget_ID".equals(propertyId))
		// if (!"C_BPartner_ID".equals(propertyId))
		// {
		// return null;
		// }

		final Field<?> editor = createEditor(fieldDescriptor);
		if (editor == null)
		{
			return null;
		}

		bind(editor, propertyId);

		@SuppressWarnings("unchecked")
		final T editorCasted = (T)editor;
		return editorCasted;
	}

	private Field<?> createEditor(final DataFieldPropertyDescriptor fieldDescriptor)
	{
		return fieldEditorFactory.createEditor(fieldDescriptor);
	}

	@Override
	public Collection<Object> getPropertyIds()
	{
		return getBoundPropertyIds();
	}

	@Override
	public Property<?> getProperty(final Object propertyId)
	{
		Field<?> field = getField(propertyId);
		if (field != null)
		{
			return field;
		}

		final DataRowItem row = getItemDataSource();
		final DataFieldProperty property = row.getItemProperty(propertyId);
		if (property != null)
		{
			return property;
		}

		return null;
	}

	@Override
	public DataFieldPropertyDescriptor getFieldDescriptor(final Object propertyId)
	{
		return descriptor.getFieldDescriptorByColumnName(propertyId);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected final <T extends Field> T build(final String caption, final Class<?> dataType, final Class<T> fieldType) throws BindException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public final FieldGroupFieldFactory getFieldFactory()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public final void setFieldFactory(final FieldGroupFieldFactory fieldFactory)
	{
		throw new UnsupportedOperationException();
	}

	public final Converter<?, ?> getConverter(final String columnName)
	{
		Field<?> field = getField(columnName);
		if (field == null)
		{
			field = buildAndBind(columnName);
		}

		if (field == null)
		{
			return null;
		}
		else if (field instanceof AbstractField)
		{
			final Converter<?, ?> converter = ((AbstractField<?>)field).getConverter();
			return converter;
		}

		return null;
	}

	@Override
	public void setItemDataSource(final Item itemDataSource)
	{
		destroyEditingContext();

		final DataRowItem row = (DataRowItem)itemDataSource;
		if (row != null)
		{
			fieldValuesContext = new FieldValuesContext();
		}

		super.setItemDataSource(row);

		if (row != null)
		{
			dependencyBinders.bind(this);
		}

	}

	private final void destroyEditingContext()
	{
		if (fieldValuesContext == null)
		{
			return;
		}

		dependencyBinders.unbind();

		fieldValuesContext = null;
	}

	@Override
	public DataRowItem getItemDataSource()
	{
		return (DataRowItem)super.getItemDataSource();
	}

	private final class FieldValuesContext implements IFieldGroupContext
	{
		@Override
		public int getWindowNo()
		{
			return windowContext.getWindowNo();
		}

		@Override
		public String get_ValueAsString(final String variableName)
		{
			final Property<?> field = getProperty(variableName);
			if (field == null)
			{
				return null;
			}

			final Object value = field.getValue();
			if (value == null)
			{
				return null;
			}
			else if (value instanceof Boolean)
			{
				return DisplayType.toBooleanString((Boolean)value);
			}
			else
			{
				return value.toString();
			}
		}

		@Override
		public boolean has_Variable(final String variableName)
		{
			final Field<?> field = getField(variableName);
			return field != null;
		}

		@Override
		public String get_ValueOldAsString(final String variableName)
		{
			// TODO
			return null;
		}

	}

	public static final class Builder
	{
		private WindowContext windowContext;
		private DataRowDescriptor descriptor;
		private boolean gridMode;
		private Boolean buffered;

		private Builder()
		{
			super();
		}

		public AWindowTabFieldGroup build()
		{
			return new AWindowTabFieldGroup(this);
		}

		public Builder setWindowContext(final WindowContext windowContext)
		{
			this.windowContext = windowContext;
			return this;
		}

		public Builder setDescriptor(final DataRowDescriptor descriptor)
		{
			this.descriptor = descriptor;
			return this;
		}

		public Builder setGridMode()
		{
			gridMode = true;
			return this;
		}

		public Builder setBuffered(final boolean buffered)
		{
			this.buffered = buffered;
			return this;
		}
	}
}
