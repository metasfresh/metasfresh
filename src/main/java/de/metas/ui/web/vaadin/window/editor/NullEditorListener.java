package de.metas.ui.web.vaadin.window.editor;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

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

public final class NullEditorListener implements EditorListener
{
	public static final transient NullEditorListener instance = new NullEditorListener();

	private NullEditorListener()
	{
		super();
	}

	@Override
	public void valueChange(final PropertyName propertyName, final Object value)
	{
		// nothing
	}

	@Override
	public ListenableFuture<Object> requestValue(PropertyName propertyName)
	{
		final Object value = null;
		return Futures.immediateFuture(value);
	}

	@Override
	public void gridValueChanged(PropertyName gridPropertyName, Object rowId, PropertyName propertyName, Object value)
	{
		// nothing
	}

	@Override
	public ListenableFuture<Object> requestGridValue(PropertyName gridPropertyName, Object rowId, PropertyName propertyName)
	{
		final Object value = null;
		return Futures.immediateFuture(value);
	}
}
