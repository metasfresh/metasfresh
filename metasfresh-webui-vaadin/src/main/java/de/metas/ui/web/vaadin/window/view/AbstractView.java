package de.metas.ui.web.vaadin.window.view;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.vaadin.server.ErrorEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.editor.Editor;
import de.metas.ui.web.vaadin.window.editor.EditorFactory;
import de.metas.ui.web.vaadin.window.editor.GridEditor;
import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.descriptor.PropertyDescriptor;
import de.metas.ui.web.window.shared.datatype.NullValue;
import de.metas.ui.web.window.shared.datatype.PropertyValuesDTO;

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

/**
 * Base class for {@link WindowView} implementations.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public abstract class AbstractView implements WindowView
{
	// services
	private static final Logger logger = LogManager.getLogger(AbstractView.class);
	protected final EditorFactory editorFactory = new EditorFactory();

	private final Component content;

	//
	// Editors (UI)
	private Editor _rootPropertyEditor = null;
	private Set<Editor> _allRootEditors = new LinkedHashSet<>();
	private Map<PropertyName, Editor> _propertyName2editor = ImmutableMap.of();

	/** Listens editors and forwards to {@link WindowViewListener} */
	private final WindowViewListener2EditorListenerWrapper editorListener = new WindowViewListener2EditorListenerWrapper();

	public AbstractView()
	{
		super();

		content = createUI();
		Preconditions.checkNotNull(content, "content cannot be null");
		content.setErrorHandler(event -> showError(event));
	}

	protected abstract Component createUI();
	
	@Override
	public final Component getComponent()
	{
		return content;
	}

	protected final void registerEditor(final Editor editor)
	{
		Preconditions.checkNotNull(editor, "editor");
		if (!_allRootEditors.add(editor))
		{
			// already added
			return;
		}

		// Set editor listener
		for (final Editor e : editorFactory.getAllWatchedPropertyNamesAndEditors(editor).values())
		{
			e.setEditorListener(editorListener);
		}

		// Rebuild editors map
		rebuildEditorsMap();
	}

	protected final void unregisterEditor(final Editor editor)
	{
		if (editor == null)
		{
			return;
		}

		if (!_allRootEditors.remove(editor))
		{
			return; // already unregistered or never registered
		}

		rebuildEditorsMap();
	}

	private final void rebuildEditorsMap()
	{
		logger.debug("Rebuilding editors map");
		final Map<PropertyName, Editor> propertyName2editorOld = _propertyName2editor;

		final ImmutableMap.Builder<PropertyName, Editor> editorsCollector = ImmutableMap.builder();
		for (final Editor registeredRootEditor : getRegisteredEditors())
		{
			for (final Map.Entry<PropertyName, Editor> e : editorFactory.getAllWatchedPropertyNamesAndEditors(registeredRootEditor).entrySet())
			{
				final PropertyName propertyName = e.getKey();
				final Editor editor = e.getValue();
				logger.debug("Adding editor to map: {} = {} (root: {})", propertyName, editor, registeredRootEditor);
				editorsCollector.put(propertyName, editor);
			}
		}
		final Map<PropertyName, Editor> propertyName2editorNew = editorsCollector.build();

		if (Objects.equals(propertyName2editorOld, propertyName2editorNew))
		{
			// nothing changed
			return;
		}

		this._propertyName2editor = propertyName2editorNew;
		updateUI_OnEditorsChanged();
		fireSubscribeToValueChangesEvent();
	}

	protected void updateUI_OnEditorsChanged()
	{
		// nothing on this level
	}

	private Set<Editor> getRegisteredEditors()
	{
		return _allRootEditors;
	}

	@Override
	public final void setRootPropertyDescriptor(final PropertyDescriptor rootPropertyDescriptor)
	{
		//
		// Create all editors recursively
		final Editor rootEditor;
		if (rootPropertyDescriptor != null)
		{
			rootEditor = editorFactory.createEditorsRecursivelly(rootPropertyDescriptor);
			if (rootEditor == null)
			{
				throw new IllegalStateException("No editor was created");
			}
		}
		else
		{
			rootEditor = null;
		}

		unregisterEditor(_rootPropertyEditor);

		this._rootPropertyEditor = rootEditor;
		updateUI_OnRootPropertyEditorChanged(rootEditor);

		if (rootEditor != null)
		{
			registerEditor(rootEditor);
		}
	}

	protected abstract void updateUI_OnRootPropertyEditorChanged(final Editor rootEditor);

	@Override
	public void setListener(final WindowViewListener listener)
	{
		final WindowViewListener listenerOld = editorListener.getWindowViewListener();
		if (listener == listenerOld)
		{
			return;
		}

		if (listenerOld != null)
		{
			listenerOld.viewSubscribeToValueChanges(ImmutableSet.of());
		}

		this.editorListener.setWindowViewListener(listener);
		fireSubscribeToValueChangesEvent();
	}

	protected final WindowViewListener getWindowViewListener()
	{
		return editorListener.getWindowViewListener();
	}

	private void fireSubscribeToValueChangesEvent()
	{
		final WindowViewListener listener = getWindowViewListener();
		if (listener != null)
		{
			final Set<PropertyName> watchedPropertyNames = getWatchedPropertyNames();
			listener.viewSubscribeToValueChanges(watchedPropertyNames);
		}
	}

	@Override
	public final void setProperties(final PropertyValuesDTO propertiesAsMap)
	{
		logger.trace("Setting all properties from {}", propertiesAsMap);
		for (final Map.Entry<PropertyName, Object> entry : propertiesAsMap.entrySet())
		{
			try
			{
				final PropertyName propertyName = entry.getKey();
				final Object value = entry.getValue();
				setProperty(propertyName, value);
			}
			catch (Exception e)
			{
				// TODO: handle the error
				e.printStackTrace();
			}
		}
	}

	@Override
	public final void setProperty(final PropertyName propertyName, Object value)
	{
		logger.trace("Setting propery: {}={}", propertyName, value);
		final Editor editor = getEditor(propertyName);
		if (editor == null)
		{
			// TODO: handle missing editor
			logger.trace("Skip setting {} because there is no editor for it", propertyName);
			return;
		}

		if (NullValue.isNull(value))
		{
			value = null;
		}

		editor.setValue(propertyName, value);
	}

	private Set<PropertyName> getWatchedPropertyNames()
	{
		return _propertyName2editor.keySet();
	}

	protected final Editor getEditor(final PropertyName propertyName)
	{
		return _propertyName2editor.get(propertyName);
	}

	protected final Collection<Editor> getAllEditors()
	{
		return _propertyName2editor.values();
	}

	private final GridEditor getGridEditor(final PropertyName gridPropertyName)
	{
		final Editor editor = getEditor(gridPropertyName);
		if (editor instanceof GridEditor)
		{
			return (GridEditor)editor;
		}
		return null;
	}

	@Override
	public final void setGridProperty(final PropertyName gridPropertyName, final Object rowId, final PropertyName propertyName, Object value)
	{
		logger.trace("Setting grid propery {}, {}: {}={}", gridPropertyName, rowId, propertyName, value);

		final GridEditor editor = getGridEditor(gridPropertyName);
		if (editor == null)
		{
			// TODO: handle missing editor
			logger.trace("Skip setting {} because there is no editor for it", gridPropertyName);
			return;
		}

		if (NullValue.isNull(value))
		{
			value = null;
		}

		editor.setValueAt(rowId, propertyName, value);
	}

	@Override
	public final void gridNewRow(PropertyName gridPropertyName, Object rowId, PropertyValuesDTO rowValues)
	{
		logger.trace("Creating new grid row {}, {} with values: {}", gridPropertyName, rowId, rowValues);

		final GridEditor editor = getGridEditor(gridPropertyName);
		if (editor == null)
		{
			// TODO: handle missing editor
			logger.trace("Skip {} because there is no editor for it", gridPropertyName);
			return;
		}

		editor.newRow(rowId, rowValues);
	}

	@Override
	public void commitChanges()
	{
		// TODO Auto-generated method stub
		logger.info("Commiting field changes (TODO)");
	}

	@Override
	public void confirmDiscardChanges()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void showError(final String message)
	{
		Notification.show(message, Notification.Type.WARNING_MESSAGE);
	}
	
	private void showError(final ErrorEvent event)
	{
		final Throwable ex = event.getThrowable();
		logger.warn("Got error", ex);
		
		final String errorMessage = Throwables.getRootCause(ex).getLocalizedMessage();
		showError(errorMessage);
	}

}
