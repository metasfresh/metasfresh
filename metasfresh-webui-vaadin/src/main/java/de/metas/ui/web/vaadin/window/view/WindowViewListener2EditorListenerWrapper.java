package de.metas.ui.web.vaadin.window.view;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import de.metas.ui.web.vaadin.window.editor.EditorListener;
import de.metas.ui.web.window.shared.command.ViewCommand;
import de.metas.ui.web.window.shared.datatype.PropertyPath;

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
		windowViewListener = listener;
	}

	public WindowViewListener getWindowViewListener()
	{
		return windowViewListener;
	}

	@Override
	public void valueChange(final PropertyPath propertyPath, final Object value)
	{
		final WindowViewListener windowViewListener = getWindowViewListener();
		if (windowViewListener == null)
		{
			return;
		}
		windowViewListener.viewPropertyChanged(propertyPath, value);
	}

	@Override
	public ListenableFuture<Object> requestValue(final PropertyPath propertyPath)
	{
		final WindowViewListener windowViewListener = getWindowViewListener();
		if (windowViewListener == null)
		{
			return Futures.immediateFuture(null);
		}
		return windowViewListener.viewRequestValue(propertyPath);
	}

	@Override
	public void executeCommand(final ViewCommand command)
	{
		final ViewCommandCallback callback = null;
		executeCommand(command, callback);
	}

	@Override
	public void executeCommand(final ViewCommand command, final ViewCommandCallback callback)
	{
		final WindowViewListener windowViewListener = getWindowViewListener();
		if (windowViewListener == null)
		{
			final RuntimeException ex = new RuntimeException("No " + WindowViewListener.class + " set");
			if (callback != null)
			{
				callback.onError(ex);
			}
			else
			{
				throw ex;
			}
			return;
		}

		windowViewListener.viewCommandExecute(command, callback);
	}
}
