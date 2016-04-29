package de.metas.ui.web.vaadin.window.prototype.order.editor;

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

/**
 * Base class for all editor containers
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
public abstract class EditorsContainer extends AbstractEditor
{
	public EditorsContainer(final PropertyDescriptor descriptor)
	{
		super(descriptor);
	}

	public EditorsContainer(final PropertyName propertyName)
	{
		super(propertyName);
	}

	@Override
	public void setValue(final PropertyName propertyName, final Object value)
	{
		throw new UnsupportedOperationException("Setting value to an " + getClass() + " is not allowed.");
	}

	public final boolean isDocumentFragment()
	{
		final PropertyDescriptor descriptor = getPropertyDescriptor();
		return descriptor != null && descriptor.isDocumentFragment();
	}

	public static boolean isDocumentFragment(final Editor editor)
	{
		if (editor instanceof EditorsContainer)
		{
			return ((EditorsContainer)editor).isDocumentFragment();
		}
		else
		{
			return false;
		}
	}

	@Override
	protected Label createLabelComponent()
	{
		return null; // no label
	}
}
