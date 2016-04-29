package de.metas.ui.web.vaadin.window.prototype.order.editor;

import com.google.common.util.concurrent.ListenableFuture;

import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;

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
	public void valueChange(PropertyName propertyName, Object value)
	{
		getDelegate().valueChange(propertyName, value);
	}

	@Override
	public ListenableFuture<Object> requestValue(PropertyName propertyName)
	{
		return getDelegate().requestValue(propertyName);
	}

	@Override
	public void gridValueChanged(PropertyName gridPropertyName, Object rowId, PropertyName propertyName, Object value)
	{
		getDelegate().gridValueChanged(gridPropertyName, rowId, propertyName, value);
	}
	
	@Override
	public ListenableFuture<Object> requestGridValue(PropertyName gridPropertyName, Object rowId, PropertyName propertyName)
	{
		return getDelegate().requestGridValue(gridPropertyName, rowId, propertyName);
	}
}
