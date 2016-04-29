package de.metas.ui.web.vaadin.window.prototype.order.editor;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyLayoutInfo;
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

@SuppressWarnings("serial")
public abstract class AbstractEditor extends CustomComponent implements Editor
{
	private static final String STYLE = "mf-editor";
	protected static final String STYLE_ValueField = "mf-editor-value";
	
	// Descriptors
	private final PropertyDescriptor propertyDescriptor;
	private final PropertyName propertyName;
	
	private EditorListener listener = NullEditorListener.instance;
	private Optional<Label> _labelComp;
	
	
	public AbstractEditor(final PropertyName propertyName)
	{
		super();
		addStyleName(STYLE);
		this.propertyDescriptor = null;
		this.propertyName = propertyName;
	}
	
	public AbstractEditor(final PropertyDescriptor propertyDescriptor)
	{
		super();
		addStyleName(STYLE);
		this.propertyDescriptor = propertyDescriptor;
		this.propertyName = propertyDescriptor.getPropertyName();
	}
	
	@Override
	public void setEditorListener(EditorListener listener)
	{
		this.listener = listener;
	}
	
	protected final EditorListener getEditorListener()
	{
		return listener;
	}
	
	protected final EditorListener listener()
	{
		return listener;
	}
	
	@Override
	public final PropertyName getPropertyName()
	{
		return propertyName;
	}
	
	@Override
	public Set<PropertyName> getWatchedPropertyNames()
	{
		return ImmutableSet.of();
	}
	
	protected final PropertyDescriptor getPropertyDescriptor()
	{
		return propertyDescriptor;
	}
	
	@Override
	public final String getCaption()
	{
		return getPropertyName().getCaption();
	}
	
	@Override
	public final void setCaption(String caption)
	{
		throw new UnsupportedOperationException("Setting the caption is not allowed");
	}
	
	@Override
	public final PropertyLayoutInfo getLayoutInfo()
	{
		final PropertyDescriptor propertyDescriptor = getPropertyDescriptor();
		return propertyDescriptor == null ? PropertyLayoutInfo.DEFAULT : propertyDescriptor.getLayoutInfo();
	}
	
	@Override
	public boolean isAddingChildEditorsAllowed()
	{
		return true;
	}
	
	@Override
	public final Label getLabel()
	{
		if(_labelComp == null)
		{
			final Label label = createLabelComponent();
			_labelComp = Optional.fromNullable(label);
		}
		return _labelComp.orNull();
	}
	
	protected Label createLabelComponent()
	{
		return new Label(getCaption());
	}

}
