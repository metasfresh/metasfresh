package de.metas.ui.web.vaadin.window.editor;

import com.google.common.util.concurrent.ListenableFuture;

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

public abstract class ForwardingEditorListener implements EditorListener
{
	protected abstract EditorListener getDelegate();

	@Override
	public void valueChange(final PropertyPath propertyPath, final Object value)
	{
		getDelegate().valueChange(propertyPath, value);
	}

	@Override
	public ListenableFuture<Object> requestValue(final PropertyPath propertyPath)
	{
		return getDelegate().requestValue(propertyPath);
	}

	@Override
	public void executeCommand(final ViewCommand command)
	{
		getDelegate().executeCommand(command);
	}

	@Override
	public void executeCommand(final ViewCommand command, final ViewCommandCallback callback)
	{
		getDelegate().executeCommand(command, callback);
	}
}
