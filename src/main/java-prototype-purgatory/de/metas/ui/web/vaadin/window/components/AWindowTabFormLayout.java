package de.metas.ui.web.vaadin.window.components;

import java.util.List;

import com.vaadin.ui.Field;
import com.vaadin.ui.VerticalLayout;

import de.metas.ui.web.vaadin.window.WindowContext;
import de.metas.ui.web.vaadin.window.descriptor.DataRowDescriptor;
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
public class AWindowTabFormLayout extends VerticalLayout
{
	private final AWindowTabFieldGroup binder;
	private final DataRowDescriptor descriptor;

	public AWindowTabFormLayout(final WindowContext windowContext, final DataRowDescriptor descriptor)
	{
		super();

		binder = AWindowTabFieldGroup.builder()
				.setWindowContext(windowContext)
				.setDescriptor(descriptor)
				.setBuffered(false) // FIXME: atm setting it to false, because else row prev/next navigation is not working
				.build();

		this.descriptor = descriptor;
	}

	public void setRow(final DataRowItem row)
	{
		//
		// Build & bind
		final List<String> propertyIds = descriptor.getColumnNamesInFormOrder();
		for (final Object propertyId : propertyIds)
		{
			binder.buildAndBind(propertyId);
		}

		//
		// Add components
		for (final Object propertyId : propertyIds)
		{
			final Field<?> field = binder.getField(propertyId);
			if (field == null)
			{
				continue;
			}
			addComponent(field);
		}

		//
		// Set data row
		binder.setItemDataSource(row);
	}

	public DataRowItem getRow()
	{
		return binder.getItemDataSource();
	}
}
