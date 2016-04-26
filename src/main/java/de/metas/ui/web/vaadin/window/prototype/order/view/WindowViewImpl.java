package de.metas.ui.web.vaadin.window.prototype.order.view;

import java.util.Map;

import org.slf4j.Logger;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
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
import de.metas.ui.web.vaadin.window.prototype.order.editor.EditorsContainer;
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
	private HorizontalLayout panelsContainer;
	private WindowViewListener listener;

	//
	// Properties
	private Map<PropertyName, Editor> editors;

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
		public void gridValueChanged(final PropertyName gridPropertyName, final Object rowId, final PropertyName propertyName, final Object value)
		{
			final WindowViewListener listener = WindowViewImpl.this.listener;
			if (listener == null)
			{
				return;
			}
			listener.viewGridPropertyChanged(gridPropertyName, rowId, propertyName, value);
		}
	};

	public WindowViewImpl(final PropertyDescriptor rootPropertyDescriptor)
	{
		super();

		createUI(rootPropertyDescriptor);
	}

	private void createUI(final PropertyDescriptor rootPropertyDescriptor)
	{
		final ComponentContainer content = this;
		content.addStyleName(STYLE);

		final ImmutableMap.Builder<PropertyName, Editor> editorsCollector = ImmutableMap.builder();

		//
		// Lane: title
		{
			final Button icon = new Button();
			icon.setPrimaryStyleName(STYLE + "-title-icon");
			icon.setIcon(FontAwesome.BARS);

			final LabelEditor title = new LabelEditor(WindowConstants.PROPERTYNAME_WindowTitle);
			title.setPrimaryStyleName(STYLE + "-title-text");
			title.setEditorListener(editorListener);
			editorsCollector.put(title.getPropertyName(), title);

			final HorizontalLayout panel = new HorizontalLayout(icon, title);
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
				final LabelEditor recordSummary = new LabelEditor(WindowConstants.PROPERTYNAME_RecordSummary);
				recordSummary.addStyleName(STYLE + "-record-summary-label");
				recordSummary.setContentModel(ContentMode.PREFORMATTED);
				recordSummary.setEditorListener(editorListener);
				editorsCollector.put(recordSummary.getPropertyName(), recordSummary);

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
				
				final HorizontalLayout recordNavigationComp = new HorizontalLayout(btnPreviousRecord, recordSummary, btnNextRecord);
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
				final LabelEditor recordAdditionalSummary = new LabelEditor(WindowConstants.PROPERTYNAME_RecordAditionalSummary);
				recordAdditionalSummary.addStyleName(STYLE + "-record-addsummary-label");
				recordAdditionalSummary.setContentModel(ContentMode.PREFORMATTED);
				recordAdditionalSummary.setEditorListener(editorListener);
				editorsCollector.put(recordAdditionalSummary.getPropertyName(), recordAdditionalSummary);
				panelSummary.addComponent(recordAdditionalSummary);
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
			panelsContainer = new HorizontalLayout();
			panelsContainer.addStyleName(STYLE + "-content-lane");
			panelsContainer.setSizeFull();
			content.addComponent(panelsContainer);
		}

		//
		// Create editors
		{
			final Editor editorsRoot = createEditorsRecursivelly(rootPropertyDescriptor, editorsCollector);
			panelsContainer.addComponent(editorsRoot);
			editors = editorsCollector.build();

			//
			for (final Editor editor : editors.values())
			{
				if (EditorsContainer.isDocumentFragment(editor))
				{
					panelsBar.addNavigationShortcut(editor);
				}
			}
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

		return editor;
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
			listener.viewSubscribeToValueChanges(editors.keySet());
		}
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
		final Editor editor = editors.get(propertyName);
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

		editor.setValue(value);
	}
	
	private final GridEditor getGridEditor(final PropertyName gridPropertyName)
	{
		final Editor editor = editors.get(gridPropertyName);
		if(editor instanceof GridEditor)
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
	public void showError(String message)
	{
		Notification.show(message, Notification.Type.WARNING_MESSAGE);
	}
}
