package de.metas.ui.web.vaadin.window.components;

import java.math.BigDecimal;
import java.util.Date;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.StringToBooleanConverter;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.NumberRenderer;

import de.metas.ui.web.vaadin.window.WindowContext;
import de.metas.ui.web.vaadin.window.descriptor.DataFieldPropertyDescriptor;
import de.metas.ui.web.vaadin.window.descriptor.DataRowDescriptor;
import de.metas.ui.web.vaadin.window.model.DataRowContainer;

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
public class AWindowTabGridLayout extends Grid
{
	private static final Converter<String, Boolean> CONVERTER_Boolean = new StringToBooleanConverter(FontAwesome.CHECK.getHtml(), "");
	
	public AWindowTabGridLayout(final WindowContext windowContext, final DataRowContainer container)
	{
		super();
		setSizeFull();

		final DataRowDescriptor descriptor = container.getDescriptor();

		setContainerDataSource(container);
		setColumns(descriptor.getColumnNamesInGridOrder().toArray());

		// NOTE: because when the container is set, the field group is reset, we are setting it here, after we set the container
		final AWindowTabFieldGroup binder = AWindowTabFieldGroup.builder()
				.setWindowContext(windowContext)
				.setDescriptor(descriptor)
				.setGridMode()
				.setBuffered(true) // FIXME: atm setting it to false, because else row prev/next navigation is not working
				.build();
		setEditorFieldGroup(binder);
		setEditorBuffered(binder.isBuffered());
		setEditorEnabled(true);
		setEditorSaveCaption("Save the shit");
		setEditorCancelCaption("Cancel it!");

		//
		// Setup columns
		for (final DataFieldPropertyDescriptor fieldDescriptor : descriptor.getFieldDescriptors())
		{
			final String columnName = fieldDescriptor.getColumnName();
			final Column column = getColumn(columnName);
			if (column == null)
			{
				continue;
			}
			
			column.setHeaderCaption(fieldDescriptor.getCaption());
			column.setHidable(true);

			final Class<?> modelType = fieldDescriptor.getValueClass();
			if (Boolean.class.equals(modelType))
			{
				column.setRenderer(new HtmlRenderer(), CONVERTER_Boolean);
				continue;
			}

			if (BigDecimal.class.equals(modelType))
			{
				column.setRenderer(new NumberRenderer());
				continue;
			}
			
			if (Date.class.equals(modelType))
			{
				// TODO: use DateRenderer and convert from DisplayType.getDateFormat to http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax
			}

//			final Converter<?, ?> converter = binder.getConverter(columnName);
//			if (converter != null)
//			{
//				column.setRenderer(new TextRenderer(), StringPresentationConverter.of(converter));
//			}
		}

		// setFrozenColumnCount(2);

		addItemClickListener(new ItemClickListener()
		{

			@Override
			public void itemClick(final ItemClickEvent event)
			{
				editItem(event.getItemId());
			}
		});
	}
}
