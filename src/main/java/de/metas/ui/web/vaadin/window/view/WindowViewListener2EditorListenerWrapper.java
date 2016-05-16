package de.metas.ui.web.vaadin.window.view;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import de.metas.ui.web.vaadin.window.PropertyName;
import de.metas.ui.web.vaadin.window.editor.EditorListener;

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

public class WindowViewListener2EditorListenerWrapper implements EditorListener
{
	private WindowViewListener windowViewListener;

	public void setWindowViewListener(final WindowViewListener listener)
	{
		this.windowViewListener = listener;
	}
	
	public WindowViewListener getWindowViewListener()
	{
		return windowViewListener;
	}

	@Override
	public void valueChange(final PropertyName propertyName, final Object value)
	{
		final WindowViewListener windowViewListener = getWindowViewListener();
		if (windowViewListener == null)
		{
			return;
		}
		windowViewListener.viewPropertyChanged(propertyName, value);
	}

	@Override
	public ListenableFuture<Object> requestValue(PropertyName propertyName)
	{
		final WindowViewListener windowViewListener = getWindowViewListener();
		if (windowViewListener == null)
		{
			return Futures.immediateFuture(null);
		}
		return windowViewListener.viewRequestValue(propertyName);
	}

	@Override
	public void gridValueChanged(final PropertyName gridPropertyName, final Object rowId, final PropertyName propertyName, final Object value)
	{
		final WindowViewListener windowViewListener = getWindowViewListener();
		if (windowViewListener == null)
		{
			return;
		}
		windowViewListener.viewGridPropertyChanged(gridPropertyName, rowId, propertyName, value);
	}

	@Override
	public ListenableFuture<Object> requestGridValue(PropertyName gridPropertyName, Object rowId, PropertyName propertyName)
	{
		final WindowViewListener windowViewListener = getWindowViewListener();
		if (windowViewListener == null)
		{
			return Futures.immediateFuture(null);
		}
		return windowViewListener.viewRequestGridValue(gridPropertyName, rowId, propertyName);
	};
}
