package de.metas.ui.web.vaadin.window.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.descriptor.PropertyLayoutInfo;
import de.metas.ui.web.window.shared.datatype.PropertyPath;
import de.metas.ui.web.window.shared.descriptor.ViewPropertyDescriptor;

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
	private final ViewPropertyDescriptor propertyDescriptor;
	private final PropertyPath propertyPath;
	private final PropertyName propertyName;
	private final ImmutableSet<PropertyName> watchedPropertyNames;
	
	private EditorListener listener = NullEditorListener.instance;
	private Optional<Label> _labelComp;
	
	private final Map<String, Object> _attributes = new HashMap<>();
	
	public AbstractEditor(final PropertyName propertyName)
	{
		this(propertyName, null);
	}
	
	public AbstractEditor(final ViewPropertyDescriptor propertyDescriptor)
	{
		this(propertyDescriptor.getPropertyName(), propertyDescriptor);
	}

	private AbstractEditor(final PropertyName propertyName, final ViewPropertyDescriptor propertyDescriptor)
	{
		super();
		addStyleName(STYLE);
		this.propertyDescriptor = propertyDescriptor;
		this.propertyName = propertyName;
		this.propertyPath = PropertyPath.of(propertyName);
		
		final ImmutableSet.Builder<PropertyName> watchedPropertyNames = ImmutableSet.builder();
		collectWatchedPropertyNamesOnInit(watchedPropertyNames);
		this.watchedPropertyNames = watchedPropertyNames.build();
	}
	
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("propertyName", propertyName)
				.add("instanceId", System.identityHashCode(this))
				.toString();
	}
	
	@OverridingMethodsMustInvokeSuper
	protected void collectWatchedPropertyNamesOnInit(final ImmutableSet.Builder<PropertyName> watchedPropertyNames)
	{
		// nothing on this level
	}
	
	@Override
	public void setEditorListener(final EditorListener listener)
	{
		this.listener = listener != null ? listener : NullEditorListener.instance;
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
	public final Component getComponent()
	{
		return this;
	}
	
	public final PropertyPath getPropertyPath()
	{
		return propertyPath;
	}
	
	@Override
	public final PropertyName getPropertyName()
	{
		return propertyName;
	}
	
	@Override
	public final Set<PropertyName> getWatchedPropertyNames()
	{
		return watchedPropertyNames;
	}
	
	protected final ViewPropertyDescriptor getPropertyDescriptor()
	{
		return propertyDescriptor;
	}
	
	@Override
	public final String getCaption()
	{
		final ViewPropertyDescriptor propertyDescriptor = getPropertyDescriptor();
		if(propertyDescriptor != null)
		{
			return propertyDescriptor.getCaption();
		}
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
		final ViewPropertyDescriptor propertyDescriptor = getPropertyDescriptor();
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
	
	protected final Label getLabelIfCreated()
	{
		final Optional<Label> labelComp = _labelComp;
		return labelComp == null ? null : labelComp.orNull();
	}
	
	protected Label createLabelComponent()
	{
		return new Label(getCaption());
	}
	
	@Override
	public final <T> T getAttribute(String name)
	{
		@SuppressWarnings("unchecked")
		final T value = (T)_attributes.get(name);
		return value;
	}
	
	@Override
	public final void setAttribute(String name, Object value)
	{
		Preconditions.checkNotNull(name, "name shall not be null");
		if(value == null)
		{
			_attributes.remove(name);
		}
		else
		{
			_attributes.put(name, value);
		}
	}
}
