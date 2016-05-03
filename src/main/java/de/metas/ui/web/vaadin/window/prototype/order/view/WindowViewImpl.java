package de.metas.ui.web.vaadin.window.prototype.order.view;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.vaadin.spring.annotation.PrototypeScope;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.ListenableFuture;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;
import de.metas.ui.web.vaadin.window.prototype.order.WindowConstants;
import de.metas.ui.web.vaadin.window.prototype.order.WindowConstants.OnChangesFound;
import de.metas.ui.web.vaadin.window.prototype.order.editor.Editor;
import de.metas.ui.web.vaadin.window.prototype.order.editor.EditorFactory;
import de.metas.ui.web.vaadin.window.prototype.order.editor.EditorListener;
import de.metas.ui.web.vaadin.window.prototype.order.editor.GridEditor;
import de.metas.ui.web.vaadin.window.prototype.order.editor.LabelEditor;
import de.metas.ui.web.vaadin.window.prototype.order.editor.NullValue;

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

@org.springframework.stereotype.Component
@PrototypeScope
@SuppressWarnings("serial")
public class WindowViewImpl extends VerticalLayout implements WindowView
{
	// services
	private static final Logger logger = LogManager.getLogger(WindowViewImpl.class);
	private final EditorFactory editorFactory = new EditorFactory();

	static final String STYLE = "mf-window";

	//
	// UI components
	private HorizontalLayout actionsPanel;
	private Button btnPreviousRecord;
	private Button btnNextRecord;
	private WindowRecordIndicators recordIndicators;
	private WindowPanelsBar panelsBar;
	private HorizontalLayout rootEditorContainer;
	
	private WindowViewListener listener;

	//
	// Editors (UI)
	private LabelEditor titleEditor;
	private LabelEditor recordSummaryEditor;
	private LabelEditor recordAdditionalSummaryEditor;
	private Map<PropertyName, Editor> _propertyName2editor = ImmutableMap.of();

	/** Listens editors and forwards to {@link WindowViewListener} */
	private final EditorListener editorListener = new EditorListener()
	{

		@Override
		public void valueChange(final PropertyName propertyName, final Object value)
		{
			final WindowViewListener listener = WindowViewImpl.this.listener;
			if (listener == null)
			{
				return;
			}
			listener.viewPropertyChanged(propertyName, value);
		}

		@Override
		public ListenableFuture<Object> requestValue(PropertyName propertyName)
		{
			return listener.viewRequestValue(propertyName);
		}

		@Override
		public void gridValueChanged(final PropertyName gridPropertyName, final Object rowId, final PropertyName propertyName, final Object value)
		{
			final WindowViewListener listener = WindowViewImpl.this.listener;
			if (listener == null)
			{
				return;
			}
			listener.viewGridPropertyChanged(gridPropertyName, rowId, propertyName, value);
		}

		@Override
		public ListenableFuture<Object> requestGridValue(PropertyName gridPropertyName, Object rowId, PropertyName propertyName)
		{
			return listener.viewRequestGridValue(gridPropertyName, rowId, propertyName);
		};

	};
	
	public WindowViewImpl()
	{
		super();

		createUI();
	}

	private void createUI()
	{
		final ComponentContainer content = this;
		content.addStyleName(STYLE);

		//
		// Lane: title
		{
			final Button icon = new Button();
			icon.setPrimaryStyleName(STYLE + "-title-icon");
			icon.setIcon(FontAwesome.BARS);

			this.titleEditor = new LabelEditor(WindowConstants.PROPERTYNAME_WindowTitle);
			titleEditor.setPrimaryStyleName(STYLE + "-title-text");
			titleEditor.setEditorListener(editorListener);
	
			final HorizontalLayout panel = new HorizontalLayout(icon, titleEditor);
			panel.addStyleName(STYLE + "-title-lane");
			content.addComponent(panel);
		}

		//
		// Lane: Rating & actions
		{
			actionsPanel = new HorizontalLayout();
			actionsPanel.addStyleName(STYLE + "-actions-lane");
			content.addComponent(actionsPanel);

			final Button btnSave = new Button();
			btnSave.setCaption("Save");
			btnSave.addClickListener(new Button.ClickListener()
			{

				@Override
				public void buttonClick(Button.ClickEvent event)
				{
					listener.viewSaveEditing();
				}
			});
			actionsPanel.addComponent(btnSave);

			final Button btnCancel = new Button();
			btnCancel.setCaption("Cancel");
			btnCancel.addClickListener(new Button.ClickListener()
			{
				@Override
				public void buttonClick(Button.ClickEvent event)
				{
					listener.viewCancelEditing();
				}
			});
			actionsPanel.addComponent(btnCancel);
		}

		//
		// Lane: document summary
		{
			final HorizontalLayout panelSummary = new HorizontalLayout();
			panelSummary.addStyleName(STYLE + "-summary-lane");
			content.addComponent(panelSummary);

			// Component: navigation buttons and document summary text
			{
				this.recordSummaryEditor = new LabelEditor(WindowConstants.PROPERTYNAME_RecordSummary);
				recordSummaryEditor.addStyleName(STYLE + "-record-summary-label");
				recordSummaryEditor.setContentModel(ContentMode.PREFORMATTED);
				recordSummaryEditor.setEditorListener(editorListener);

				btnPreviousRecord = new Button();
				btnPreviousRecord.setPrimaryStyleName(STYLE + "-record-nav-btn");
				btnPreviousRecord.setIcon(FontAwesome.CARET_LEFT);
				btnPreviousRecord.addClickListener(new Button.ClickListener()
				{

					@Override
					public void buttonClick(Button.ClickEvent event)
					{
						listener.viewPreviousRecord(OnChangesFound.Ask);
					}
				});

				btnNextRecord = new Button();
				btnNextRecord.setPrimaryStyleName(STYLE + "-record-nav-btn");
				btnNextRecord.setIcon(FontAwesome.CARET_RIGHT);
				btnNextRecord.addClickListener(new Button.ClickListener()
				{

					@Override
					public void buttonClick(Button.ClickEvent event)
					{
						listener.viewNextRecord(OnChangesFound.Ask);
					}
				});

				final HorizontalLayout recordNavigationComp = new HorizontalLayout(btnPreviousRecord, recordSummaryEditor, btnNextRecord);
				recordNavigationComp.addStyleName(STYLE + "-record-nav");
				panelSummary.addComponent(recordNavigationComp);
			}

			// Component: current record indicators
			{
				recordIndicators = new WindowRecordIndicators();
				panelSummary.addComponent(recordIndicators);
			}

			// Component: document additional summary text
			{
				this.recordAdditionalSummaryEditor = new LabelEditor(WindowConstants.PROPERTYNAME_RecordAditionalSummary);
				recordAdditionalSummaryEditor.addStyleName(STYLE + "-record-addsummary-label");
				recordAdditionalSummaryEditor.setContentModel(ContentMode.PREFORMATTED);
				recordAdditionalSummaryEditor.setEditorListener(editorListener);
				panelSummary.addComponent(recordAdditionalSummaryEditor);
			}
		}

		//
		// Lane: window panels bar
		{
			panelsBar = new WindowPanelsBar();
			panelsBar.addStyleName(STYLE + "-tabbar-lane");
			content.addComponent(panelsBar);
		}

		//
		// Panels container
		{
			rootEditorContainer = new HorizontalLayout();
			rootEditorContainer.addStyleName(STYLE + "-content-lane");
			rootEditorContainer.setSizeFull();
			content.addComponent(rootEditorContainer);
		}
	}

	private Editor createEditorsRecursivelly(final PropertyDescriptor descriptor, final ImmutableMap.Builder<PropertyName, Editor> editorsCollector)
	{
		final Editor editor = editorFactory.createEditor(descriptor);
		editor.setEditorListener(editorListener);
		editorsCollector.put(editor.getPropertyName(), editor);

		if (editor.isAddingChildEditorsAllowed())
		{
			for (final PropertyDescriptor childDescriptor : descriptor.getChildPropertyDescriptors())
			{
				final Editor childEditor = createEditorsRecursivelly(childDescriptor, editorsCollector);
				editor.addChildEditor(childEditor);
			}
		}

		final Set<PropertyName> editorWatchedPropertyNames = editor.getWatchedPropertyNames();
		if (editorWatchedPropertyNames != null && !editorWatchedPropertyNames.isEmpty())
		{
			for (final PropertyName editorWatchedPropertyName : editorWatchedPropertyNames)
			{
				editorsCollector.put(editorWatchedPropertyName, editor);
			}
		}

		return editor;
	}
	
	@Override
	public void setRootPropertyDescriptor(final PropertyDescriptor rootPropertyDescriptor)
	{
		//
		// Create editors
		{
			final ImmutableMap.Builder<PropertyName, Editor> editorsCollector = ImmutableMap.builder();
			
			editorsCollector.put(titleEditor.getPropertyName(), titleEditor);
			editorsCollector.put(recordSummaryEditor.getPropertyName(), recordSummaryEditor);
			editorsCollector.put(recordAdditionalSummaryEditor.getPropertyName(), recordAdditionalSummaryEditor);
			
			final Editor rootEditor = createEditorsRecursivelly(rootPropertyDescriptor, editorsCollector);
			if (rootEditor == null)
			{
				throw new IllegalStateException("No editor was created");
			}

			rootEditorContainer.removeAllComponents();
			rootEditorContainer.addComponent(rootEditor);
			_propertyName2editor = editorsCollector.build();

			//
			// Set navigation bar shortcuts
			panelsBar.setNavigationShortcutsFromEditors(_propertyName2editor.values());
		}

		//
		// Notify listener
		if (listener != null)
		{
			final Set<PropertyName> watchedPropertyNames = getWatchedPropertyNames();
			listener.viewSubscribeToValueChanges(watchedPropertyNames);
		}

	}

	@Override
	public void setListener(final WindowViewListener listener)
	{
		if (this.listener == listener)
		{
			return;
		}

		final WindowViewListener listenerOld = this.listener;
		if (listenerOld != null)
		{
			listener.viewSubscribeToValueChanges(ImmutableSet.of());
		}

		this.listener = listener;

		if (listener != null)
		{
			final Set<PropertyName> watchedPropertyNames = getWatchedPropertyNames();
			listener.viewSubscribeToValueChanges(watchedPropertyNames);
		}
	}
	
	@Override
	public Component getComponent()
	{
		return this;
	}

	@Override
	public void setNextRecordEnabled(final boolean enabled)
	{
		btnNextRecord.setEnabled(enabled);
	}

	@Override
	public void setPreviousRecordEnabled(final boolean enabled)
	{
		btnPreviousRecord.setEnabled(enabled);
	}

	@Override
	public void setProperties(final Map<PropertyName, Object> propertiesAsMap)
	{
		for (final Map.Entry<PropertyName, Object> entry : propertiesAsMap.entrySet())
		{
			final PropertyName propertyName = entry.getKey();
			final Object value = entry.getValue();
			setProperty(propertyName, value);
		}
	}

	@Override
	public void setProperty(final PropertyName propertyName, Object value)
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

	private final Editor getEditor(final PropertyName propertyName)
	{
		return _propertyName2editor.get(propertyName);
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
	public void setGridProperty(final PropertyName gridPropertyName, final Object rowId, final PropertyName propertyName, Object value)
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
	public void gridNewRow(PropertyName gridPropertyName, Object rowId, Map<PropertyName, Object> rowValues)
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
}
