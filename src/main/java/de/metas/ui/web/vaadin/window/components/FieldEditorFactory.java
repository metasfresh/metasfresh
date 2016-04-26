package de.metas.ui.web.vaadin.window.components;

import org.compiere.util.DisplayType;
import org.compiere.util.NamePair;
import org.vaadin.viritin.fields.LazyComboBox;

import com.google.common.base.Preconditions;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import de.metas.ui.web.vaadin.window.WindowContext;
import de.metas.ui.web.vaadin.window.data.FieldEditorContainer;
import de.metas.ui.web.vaadin.window.data.ILookupDataSource;
import de.metas.ui.web.vaadin.window.data.LazyDataSource;
import de.metas.ui.web.vaadin.window.data.LookupDataSourceRequest;
import de.metas.ui.web.vaadin.window.data.LookupFieldEditorContainer;
import de.metas.ui.web.vaadin.window.descriptor.DataFieldPropertyDescriptor;

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

public class FieldEditorFactory
{
	private final WindowContext windowContext;
	private final boolean gridMode;

	public FieldEditorFactory(final WindowContext windowContext, final boolean gridMode)
	{
		super();
		this.windowContext = Preconditions.checkNotNull(windowContext, "window context not null");
		this.gridMode = gridMode;

	}
	public Field<?> createEditor(final DataFieldPropertyDescriptor descriptor)
	{
		final int displayType = descriptor.getDisplayType();
		final int adReferenceId = descriptor.getAD_Reference_Value_ID();
		String caption = descriptor.getCaption();

		final Field<?> field;
		if (displayType == DisplayType.List
				|| displayType == DisplayType.Button && adReferenceId > 0)
		{
			field = createField_ComboBox(descriptor);
		}
		else if (displayType == DisplayType.TableDir || displayType == DisplayType.Table)
		{
			field = createField_ComboBox(descriptor);
		}
		else if (displayType == DisplayType.Search)
		{
			field = createField_LazyComboBox(descriptor);
		}
		else if (descriptor.isLongTextField())
		{
			final TextArea textArefield = new TextArea();
			field = textArefield;
		}
		else if (DisplayType.isText(displayType))
		{
			final TextField textField = new TextField();
			field = textField;
		}
		else if (DisplayType.isDate(displayType))
		{
			final DateField dateField = new DateField();
			field = dateField;
		}
		else if (DisplayType.isNumeric(displayType))
		{
			final TextField textField = new TextField();
			field = textField;
		}
		else if (DisplayType.YesNo == displayType)
		{
			final CheckBox checkbox = new CheckBox();
			if (gridMode)
			{
				caption = "";
			}
			field = checkbox;
		}
		else
		{
			// final Class<?> type = DisplayType.getClass(displayType, true);
			// field = defaultFieldFactory.createField(type, Field.class);
			field = null;
		}

		if (field == null)
		{
			return null;
		}

		field.setCaption(caption);
		return field;
	}

	private final ComboBox createField_ComboBox(final DataFieldPropertyDescriptor descriptor)
	{
		//
		// DataSource & Container
		final String columnName = descriptor.getColumnName();
		final int displayType = descriptor.getDisplayType();
		final int adReferenceId = descriptor.getAD_Reference_Value_ID();
		final ILookupDataSource dataSource = windowContext.getLookupDataSource(columnName, displayType, adReferenceId);
		final FieldEditorContainer container = LookupFieldEditorContainer.of(dataSource);

		//
		// Converter
		// final Converter<Object, ?> converter = container.getConverter();

		//
		// ComboBox
		final ComboBox comboboxField = new ComboBox();
		// comboboxField.setConverter(converter);
		comboboxField.setContainerDataSource(container);
		comboboxField.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		comboboxField.setItemCaptionPropertyId(container.getCaptionPropertyId());
		comboboxField.setItemIconPropertyId(container.getIconPropertyId());
		return comboboxField;
	}

	private Field<?> createField_LazyComboBox(final DataFieldPropertyDescriptor descriptor)
	{
		final LookupDataSourceRequest request = LookupDataSourceRequest.of(windowContext.getWindowNo(), descriptor.getColumnName(), descriptor.getDisplayType(), descriptor.getAD_Reference_Value_ID());
		final LazyDataSource dataSource = new LazyDataSource(request);
		final int pageLength = dataSource.getSqlFetchLimit();
		final LazyComboBox<NamePair> comboBoxField = new LazyComboBox<>(NamePair.class, dataSource, dataSource, pageLength);
		return comboBoxField;
	}

}
