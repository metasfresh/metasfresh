package de.metas.ui.web.window_old.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.ZoomInfoFactory.ZoomInfo;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.process.ProcessInfo;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ListenableFuture;

import de.metas.logging.LogManager;
import de.metas.ui.web.service.IImageProvider;
import de.metas.ui.web.service.IWebProcessCtl;
import de.metas.ui.web.window_old.HARDCODED_Order;
import de.metas.ui.web.window_old.PropertyName;
import de.metas.ui.web.window_old.PropertyNameSet;
import de.metas.ui.web.window_old.WindowConstants;
import de.metas.ui.web.window_old.WindowConstants.OnChangesFound;
import de.metas.ui.web.window_old.datasource.IDataSourceFactory;
import de.metas.ui.web.window_old.datasource.ModelDataSource;
import de.metas.ui.web.window_old.datasource.SaveResult;
import de.metas.ui.web.window_old.descriptor.IPropertyDescriptorProvider;
import de.metas.ui.web.window_old.descriptor.PropertyDescriptor;
import de.metas.ui.web.window_old.descriptor.legacy.VOPropertyDescriptorProvider;
import de.metas.ui.web.window_old.model.action.ActionsManager;
import de.metas.ui.web.window_old.shared.action.Action;
import de.metas.ui.web.window_old.shared.action.Action.ActionEvent;
import de.metas.ui.web.window_old.shared.action.ActionGroup;
import de.metas.ui.web.window_old.shared.action.ActionsList;
import de.metas.ui.web.window_old.shared.command.ViewCommand;
import de.metas.ui.web.window_old.shared.command.ViewCommandResult;
import de.metas.ui.web.window_old.shared.datatype.GridRowId;
import de.metas.ui.web.window_old.shared.datatype.PropertyPath;
import de.metas.ui.web.window_old.shared.datatype.PropertyPathSet;
import de.metas.ui.web.window_old.shared.datatype.PropertyPathValuesDTO;
import de.metas.ui.web.window_old.shared.datatype.PropertyValuesDTO;
import de.metas.ui.web.window_old.shared.descriptor.ViewPropertyDescriptor;
import de.metas.ui.web.window_old.shared.event.AllPropertiesChangedModelEvent;
import de.metas.ui.web.window_old.shared.event.ModelEvent;
import de.metas.ui.web.window_old.shared.event.PropertyChangedModelEvent;
import de.metas.ui.web.window_old.shared.event.ZoomToWindowEvent;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

//@Component
//@Scope("prototype")
public class WindowModelImpl implements WindowModel
{
	private static final Logger logger = LogManager.getLogger(WindowModelImpl.class);

	private final String id = UUID.randomUUID().toString();
	private final EventBus eventBus = new EventBus(getClass().getName());

	//
	// Properties
	private final IPropertyDescriptorProvider propertyDescriptorProvider = new VOPropertyDescriptorProvider();
	private PropertyDescriptor _rootPropertyDescriptor;
	private PropertyValueCollection _properties = PropertyValueCollection.EMPTY;

	//
	// Data source
	private final IDataSourceFactory dataSourceFactory = Services.get(IDataSourceFactory.class);
	private final Object _dataSourceSync = new Object();
	private ModelDataSource _dataSource = null;

	//
	// Current record index
	private static final int RECORDINDEX_New = -1;
	private static final int RECORDINDEX_Unknown = -100;
	private int _recordIndex = RECORDINDEX_Unknown;
	private int _recordIndexPrev = RECORDINDEX_Unknown;

	private final ActionsManager _actionsManager;

	public WindowModelImpl()
	{
		super();
		_actionsManager = createActionsManager();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("rootPropertyDescriptor", _rootPropertyDescriptor)
				.add("recordIndex", _recordIndex)
				.toString();
	}
	
	@Override
	public void setRootPropertyDescriptorFromWindow(int windowId)
	{
		final PropertyDescriptor rootPropertyDescriptor = propertyDescriptorProvider.provideForWindow(windowId);
		setRootPropertyDescriptor(rootPropertyDescriptor);
	}

	private void setRootPropertyDescriptor(final PropertyDescriptor rootPropertyDescriptor)
	{
		if (_rootPropertyDescriptor == rootPropertyDescriptor)
		{
			return;
		}
		Check.assumeNotNull(rootPropertyDescriptor, "Parameter rootPropertyDescriptor is not null");

		_rootPropertyDescriptor = rootPropertyDescriptor;

		final PropertyValueCollection.Builder propertiesCollector = PropertyValueCollection.builder();

		// Window: title
		{
			propertiesCollector.addProperty(StringExpressionPropertyValue.of(WindowConstants.PROPERTYNAME_WindowTitle, HARDCODED_Order.STRINGEXPRESSION_TitleSummary));
		}
		// Window: record summary
		{
			propertiesCollector.addProperty(StringExpressionPropertyValue.of(WindowConstants.PROPERTYNAME_RecordSummary, HARDCODED_Order.STRINGEXPRESSION_RecordSummary));
		}
		// Window: record additional summary
		{
			propertiesCollector.addProperty(StringExpressionPropertyValue.of(WindowConstants.PROPERTYNAME_RecordAditionalSummary, HARDCODED_Order.STRINGEXPRESSION_AdditionalRecordSummary));
		}

		//
		//
		{
			propertiesCollector.addPropertyRecursivelly(rootPropertyDescriptor);
		}

		//
		//
		_properties = propertiesCollector.build();

		//
		// Data source
		if (getRecordsCount() > 0)
		{
			setRecordIndexAndReload(0);
		}
		else
		{
			setRecordIndexAndReload(RECORDINDEX_New);
		}
	}
	
	@Override
	public ViewPropertyDescriptor getViewRootPropertyDescriptor()
	{
		final PropertyDescriptor rootPropertyDescriptor = _rootPropertyDescriptor;
		if (rootPropertyDescriptor == null)
		{
			return null;
		}
		return rootPropertyDescriptor.toViewPropertyDescriptor();
	}

	private final <ET extends ModelEvent> void postEvent(final ET event)
	{
		logger.trace("Firing event: {}", event);
		eventBus.post(event);
	}

	private final <ET extends ModelEvent> void postEvents(final Collection<ET> events)
	{
		if (events == null || events.isEmpty())
		{
			return;
		}
		for (final ET event : events)
		{
			postEvent(event);
		}
	}

	/**
	 * @return model's unique ID
	 */
	public String getId()
	{
		return id;
	}

	public PropertyDescriptor getRootPropertyDescriptor()
	{
		return _rootPropertyDescriptor;
	}

	private ModelDataSource getDataSource()
	{
		if (_dataSource == null)
		{
			synchronized (_dataSourceSync)
			{
				if (_dataSource == null)
				{
					_dataSource = dataSourceFactory.createDataSource(getRootPropertyDescriptor());
				}
			}
		}
		return _dataSource;
	}

	@Override
	public void subscribe(final Object subscriberObj)
	{
		eventBus.register(subscriberObj);
	}

	@Override
	public void unsubscribe(final Object subscriberObj)
	{
		eventBus.unregister(subscriberObj);
	}

	private int getRecordIndex()
	{
		return _recordIndex;
	}

	private void setRecordIndex(final int index)
	{
		if (_recordIndex == index)
		{
			return;
		}

		setRecordIndexAndReload(index);
	}

	private void setRecordIndexAndReload(final int recordIndex)
	{
		final boolean isNewRecord = recordIndex == RECORDINDEX_New;
		if (isNewRecord)
		{
			_recordIndexPrev = _recordIndex;
			_recordIndex = RECORDINDEX_New;
			loadRecord(PropertyValuesDTO.of());
		}
		else if (recordIndex < 0)
		{
			throw new IllegalArgumentException("Invalid index: " + recordIndex);
		}
		else
		{
			final ModelDataSource dataSource = getDataSource();
			final PropertyValuesDTO values = dataSource.getRecord(recordIndex);
			logger.trace("Loading current record ({})", recordIndex);

			_recordIndexPrev = _recordIndex;
			_recordIndex = recordIndex;
			loadRecord(values);
		}
	}

	private final void loadRecord(final PropertyValuesDTO values)
	{
		final PropertyValueCollection properties = getProperties();
		properties.setValuesFromMap(values);

		//
		logger.trace("Update calculated values (after load)");
		updateAllDependenciesNoFire();

		if (logger.isTraceEnabled())
		{
			logger.trace("Loaded record {}:\n{}", getRecordIndex(), PropertyValues.toStringRecursivelly(properties.getPropertyValues()));
		}

		postEvent(AllPropertiesChangedModelEvent.of(getRecordIndex()));
	}

	private int getRecordsCount()
	{
		final ModelDataSource dataSource = getDataSource();
		return dataSource.getRecordsCount();
	}

	@Override
	public boolean hasPreviousRecord()
	{
		final int recordIndex = getRecordIndex();
		return recordIndex > 0;
	}

	@Override
	public boolean hasNextRecord()
	{
		final int recordIndex = getRecordIndex();
		if (recordIndex < 0)
		{
			return false;
		}
		return recordIndex + 1 < getRecordsCount();
	}

	private final PropertyValueCollection getProperties()
	{
		return _properties;
	}

	private final PropertyValueCollection getPropertiesLoaded()
	{
		return _properties;
	}

	private final boolean hasChangesOnCurrentRecord()
	{
		final PropertyValueCollection properties = getProperties();
		return properties.hasChanges();
	}

	@Override
	public boolean hasProperty(final PropertyPath propertyPath)
	{
		final PropertyValueCollection properties = getPropertiesLoaded();
		if (propertyPath.isGridProperty())
		{
			final GridPropertyValue gridPropertyValue = GridPropertyValue.cast(properties.getPropertyValueOrNull(propertyPath.getGridPropertyName()));
			if (gridPropertyValue == null)
			{
				return false;
			}

			return gridPropertyValue.hasProperty(propertyPath.getRowId(), propertyPath.getPropertyName());
		}
		else
		{
			return properties.getPropertyValueOrNull(propertyPath.getPropertyName()) != null;
		}
	}

	@Override
	public Object getProperty(final PropertyPath propertyPath)
	{
		if (propertyPath.isGridProperty())
		{
			return getGridProperty(propertyPath);
		}
		else
		{
			final PropertyValueCollection properties = getPropertiesLoaded();
			final PropertyValue propertyValue = properties.getPropertyValue(propertyPath.getPropertyName());
			return propertyValue.getValue();
		}
	}

	private Object getGridProperty(final PropertyPath propertyPath)
	{
		final PropertyValueCollection properties = getPropertiesLoaded();
		final GridPropertyValue gridProp = GridPropertyValue.cast(properties.getPropertyValueOrNull(propertyPath.getGridPropertyName()));
		if (gridProp == null)
		{
			// TODO: handle missing model property
			logger.trace("Skip getting propery {} because property value is missing", propertyPath);
			return null;
		}

		final Object value = gridProp.getValueAt(propertyPath.getRowId(), propertyPath.getPropertyName());
		return value;
	}

	@Override
	public Object getPropertyOrNull(final PropertyPath propertyPath)
	{
		if (propertyPath.isGridProperty())
		{
			return getGridProperty(propertyPath);
		}
		else
		{
			final PropertyValueCollection properties = getPropertiesLoaded();
			final PropertyValue propertyValue = properties.getPropertyValueOrNull(propertyPath.getPropertyName());
			if (propertyValue == null)
			{
				return null;
			}
			return propertyValue.getValue();
		}
	}

	@Override
	public void setProperty(final PropertyPath propertyPath, final Object value)
	{
		if (propertyPath.isGridProperty())
		{
			setGridProperty(propertyPath, value);
			return;
		}

		if (logger.isTraceEnabled())
		{
			logger.trace("Setting property: {}={} ({})", propertyPath, value, value == null ? null : value.getClass());
		}

		final PropertyValueCollection properties = getPropertiesLoaded();
		final PropertyName propertyName = propertyPath.getPropertyName();
		final PropertyValue prop = properties.getPropertyValue(propertyName);
		if (prop == null)
		{
			// TODO: handle missing model property
			logger.trace("Skip setting propery {} because property value is missing", propertyPath);
			return;
		}

		if (prop.isReadOnlyForUser())
		{
			logger.trace("Skip setting propery {} because property value is readonly for user", propertyPath);
			return;
		}

		//
		// Check if it's an actual value change
		final Object valueOld = prop.getValue();
		if (Check.equals(value, valueOld))
		{
			return;
		}

		//
		// Set the value
		prop.setValue(value);

		// Fire event
		postEvent(PropertyChangedModelEvent.of(propertyPath, value, valueOld));

		//
		// Update dependencies
		updateAllWhichDependOn(propertyName);
	}

	private void setGridProperty(final PropertyPath propertyPath, final Object value)
	{
		if (logger.isTraceEnabled())
		{
			logger.trace("Setting grid property {}={} ({})", propertyPath, value, value == null ? null : value.getClass());
		}

		final PropertyValueCollection properties = getPropertiesLoaded();
		final PropertyName gridPropertyName = propertyPath.getGridPropertyName();
		final GridPropertyValue gridProp = GridPropertyValue.cast(properties.getPropertyValue(gridPropertyName));
		if (gridProp == null)
		{
			// TODO: handle missing model property
			logger.trace("Skip setting propery {} because property value is missing", propertyPath);
			return;
		}

		final GridRowId rowId = propertyPath.getRowId();
		final PropertyName propertyName = propertyPath.getPropertyName();
		final Object valueOld = gridProp.setValueAt(rowId, propertyName, value);

		//
		// Check if it's an actual value change
		if (Check.equals(value, valueOld))
		{
			return;
		}

		// Fire event
		postEvent(PropertyChangedModelEvent.of(propertyPath, value, valueOld));

		// TODO: process dependencies
	}

	private final void updateAllDependenciesNoFire()
	{
		final PropertyValueCollection properties = getPropertiesLoaded();
		final DependencyValueChangedEvent dependencyChangedEvent = DependencyValueChangedEvent.any(properties);
		for (final PropertyValue propertyValue : properties.getPropertyValues())
		{
			final Map<PropertyName, PropertyChangedModelEvent> eventsCollector = null;
			updateDependentPropertyValue(propertyValue, dependencyChangedEvent, eventsCollector);
		}
	}

	private final void updateAllWhichDependOn(final PropertyName propertyName)
	{
		logger.trace("Updating all properties which depend of {}", propertyName);

		// TODO: avoid loops because of cyclic dependencies
		final PropertyValueCollection properties = getProperties();

		properties.getDependencies().consume(propertyName, (dependentPropertyName, dependencyTypes) -> {
			final PropertyValue dependentPropertyValue = properties.getPropertyValue(dependentPropertyName);
			if (dependentPropertyValue == null)
			{
				logger.warn("Skip setting dependent propery {} because property value is missing", dependentPropertyName);
				return;
			}

			final DependencyValueChangedEvent dependencyChangedEvent = DependencyValueChangedEvent.of(properties, propertyName, dependencyTypes);
			final Map<PropertyName, PropertyChangedModelEvent> eventsCollector = new LinkedHashMap<>();
			updateDependentPropertyValue(dependentPropertyValue, dependencyChangedEvent, eventsCollector);
			postEvents(eventsCollector.values());

			for (final PropertyName dependentPropertyNameLvl2 : eventsCollector.keySet())
			{
				updateAllWhichDependOn(dependentPropertyNameLvl2);
			}
		});
	}

	private final void updateDependentPropertyValue(final PropertyValue propertyValue, final DependencyValueChangedEvent dependencyChangedEvent,
			final Map<PropertyName, PropertyChangedModelEvent> eventsCollector)
	{
		final Object calculatedValueOld = propertyValue.getValue();
		propertyValue.onDependentPropertyValueChanged(dependencyChangedEvent);
		final Object calculatedValueNew = propertyValue.getValue();
		if (Objects.equal(calculatedValueOld, calculatedValueNew))
		{
			return;
		}

		final PropertyName propertyName = propertyValue.getName();
		logger.trace("Updated dependent property: {}={} (event: {})", propertyName, calculatedValueNew, dependencyChangedEvent);

		//
		// Collect event
		if (eventsCollector != null)
		{
			final PropertyPath propertyPath = PropertyPath.of(propertyName);
			eventsCollector.put(propertyName, PropertyChangedModelEvent.of(propertyPath, calculatedValueNew, calculatedValueOld));
		}
	}

	@Override
	public PropertyValuesDTO getPropertyValuesDTO(final PropertyNameSet selectedPropertyNames)
	{
		if (selectedPropertyNames.isEmpty())
		{
			return PropertyValuesDTO.of();
		}
		final PropertyValueCollection properties = getPropertiesLoaded();
		return properties.getValuesAsMap(selectedPropertyNames.asSet());
	}

	@Override
	public PropertyPathValuesDTO getPropertyPathValuesDTO(final PropertyPathSet selectedPropertyPaths)
	{
		if (selectedPropertyPaths.isEmpty())
		{
			return PropertyPathValuesDTO.of();
		}
		
		final PropertyPathValuesDTO.Builder valuesBuilder = PropertyPathValuesDTO.builder();
		for (final PropertyPath propertyPath : selectedPropertyPaths)
		{
			if (!hasProperty(propertyPath))
			{
				continue;
			}
			final Object value = getPropertyOrNull(propertyPath);
			valuesBuilder.put(propertyPath, value);
		}
		
		return valuesBuilder.build();
	}

	private void setFromPropertyValuesDTO(final PropertyValuesDTO values)
	{
		// TODO: find a way to aggregate all events and send them all together

		for (final Map.Entry<PropertyName, Object> e : values.entrySet())
		{
			final PropertyPath propertyPath = PropertyPath.of(e.getKey());
			final Object value = e.getValue();
			setProperty(propertyPath, value);
		}
	}

	public boolean isNewRecord()
	{
		return getRecordIndex() == RECORDINDEX_New;
	}

	public void newRecord()
	{
		setRecordIndexAndReload(RECORDINDEX_New);
	}

	@Override
	public void newRecordAsCopyById(final Object recordId)
	{
		newRecord();

		final PropertyValuesDTO values = getDataSource().retrieveRecordById(recordId);
		setFromPropertyValuesDTO(values);
	}

	public void nextRecord(final OnChangesFound onChangesFound)
	{
		if (!hasNextRecord())
		{
			throw new RuntimeException("There is no next record");
		}

		if (onChangesFound == OnChangesFound.Discard)
		{
			// do nothing
		}
		else if (onChangesFound == OnChangesFound.Ask)
		{
			// if (hasChangesOnCurrentRecord())
			// {
			// postEvent(ConfirmDiscardChangesModelEvent.of(this));
			// return;
			// }
		}
		else
		{
			throw new IllegalArgumentException("Unknown OnChangesFound value: " + onChangesFound);
		}

		final int index = getRecordIndex();
		final int nextIndex = index + 1;
		setRecordIndex(nextIndex);
	}

	public void previousRecord(final OnChangesFound onChangesFound)
	{
		if (!hasPreviousRecord())
		{
			throw new RuntimeException("There is no previous record");
		}

		if (onChangesFound == OnChangesFound.Discard)
		{
			// do nothing
		}
		else if (onChangesFound == OnChangesFound.Ask)
		{
			// if (hasChangesOnCurrentRecord())
			// {
			// postEvent(ConfirmDiscardChangesModelEvent.of(this));
			// return;
			// }
		}
		else
		{
			throw new IllegalArgumentException("Unknown OnChangesFound value: " + onChangesFound);
		}

		final int index = getRecordIndex();
		final int nextIndex = index - 1;
		setRecordIndex(nextIndex);
	}

	@Override
	public SaveResult saveRecord()
	{
		final PropertyValuesDTO values = getProperties().getValuesAsMap();

		final ModelDataSource dataSource = getDataSource();
		final int index = getRecordIndex();
		final SaveResult result = dataSource.saveRecord(index, values);

		setRecordIndexAndReload(result.getRecordIndex());

		return result;
	}

	public void cancelRecordEditing()
	{
		if (isNewRecord())
		{
			setRecordIndexAndReload(_recordIndexPrev);
		}
		else
		{
			setRecordIndexAndReload(getRecordIndex());
		}
	}

	private ActionsManager createActionsManager()
	{
		final ActionGroup crudActionGroup = ActionGroup.of("CRUD");
		final ActionGroup reportActionGroup = ActionGroup.of("Report");
		final ActionGroup goActionGroup = ActionGroup.of("Go");
		final ActionGroup helpActionGroup = ActionGroup.of("Help");

		return ActionsManager.newInstance()
				.newActionWithListener(crudActionGroup, "Ignore", event -> cancelRecordEditing()).setToolbarAction().buildAndAdd()
				.newActionWithListener(crudActionGroup, "New", event -> newRecord()).setToolbarAction().buildAndAdd()
				.newActionWithListener(crudActionGroup, "Save", event -> saveRecord()).setToolbarAction().buildAndAdd()
				.newActionWithListener(crudActionGroup, "Copy", ACTIONLISTENER_NotImpleted).buildAndAdd()
				.newActionWithListener(crudActionGroup, "CopyDetails", ACTIONLISTENER_NotImpleted).buildAndAdd()
				.newActionWithListener(crudActionGroup, "Delete", ACTIONLISTENER_NotImpleted).buildAndAdd()
				.newActionWithListener(crudActionGroup, "DeleteSelection", ACTIONLISTENER_NotImpleted).buildAndAdd()
				.newActionWithListener(crudActionGroup, "Refresh", ACTIONLISTENER_NotImpleted).buildAndAdd()
				//
				.newActionWithListener(reportActionGroup, "Report", ACTIONLISTENER_NotImpleted).buildAndAdd()
				.newActionWithListener(reportActionGroup, "Print", event -> print(event, false)).buildAndAdd()
				.newActionWithListener(reportActionGroup, "PrintPreview", event -> print(event, true)).buildAndAdd()
				//
				.newActionWithListener(goActionGroup, WindowConstants.ACTION_PreviousRecord, event -> previousRecord(OnChangesFound.Ask)).buildAndAdd()
				.newActionWithListener(goActionGroup, WindowConstants.ACTION_NextRecord, event -> nextRecord(OnChangesFound.Ask)).buildAndAdd()
				.addActionWithManagerProvider(goActionGroup, "ZoomAcross", (parentAction) -> createZoomAccrossActions())
				.newActionWithListener(goActionGroup, "Request", ACTIONLISTENER_NotImpleted).buildAndAdd()
				.newActionWithListener(goActionGroup, "Archive", ACTIONLISTENER_NotImpleted).buildAndAdd()
				//
				.newActionWithListener(helpActionGroup, "Help", ACTIONLISTENER_NotImpleted).buildAndAdd();
	}

	private ActionsManager getActionsManager()
	{
		return _actionsManager;
	}

	@Override
	public ActionsList getActions()
	{
		return getActionsManager().getActionsList();
	}

	@Override
	public ActionsList getChildActions(final String actionId)
	{
		return getActionsManager().getChildActions(actionId);
	}

	@Override
	public void executeAction(final String actionId)
	{
		getActionsManager().executeAction(actionId, this);
	}

	private static final Action.Listener ACTIONLISTENER_NotImpleted = event -> {
		throw new UnsupportedOperationException("Action not implemented: " + event.getAction());
	};

	private ActionsManager createZoomAccrossActions()
	{
		final int recordIndex = getRecordIndex();
		if (recordIndex < 0)
		{
			return ActionsManager.newInstance();
		}

		final ModelDataSource dataSource = getDataSource();
		final List<ZoomInfo> zoomInfos = dataSource.retrieveZoomAccrossInfos(recordIndex);
		if (zoomInfos.isEmpty())
		{
			throw new AdempiereException("No zoom targets found");
		}

		final ActionsManager actionsManager = ActionsManager.newInstance();
		for (final ZoomInfo zoomInfo : zoomInfos)
		{
			actionsManager.newAction()
					.setActionId("Zoom_" + zoomInfo.getId())
					.setCaption(zoomInfo.getLabel())
					.setIcon(Services.get(IImageProvider.class).getImageResourceForNameWithoutExt(IImageProvider.ICONNAME_Window))
					.setListener(target -> postEvent(ZoomToWindowEvent.of(zoomInfo)))
					.buildAndAdd();
		}

		return actionsManager;
	}

	private void print(final ActionEvent event, final boolean printPreview)
	{
		final PropertyDescriptor rootPropertyDescriptor = getRootPropertyDescriptor();
		final int printProcessId = rootPropertyDescriptor.getPrintProcessId();

		if (printProcessId <= 0)
		{
			// TODO: call report
			throw new UnsupportedOperationException("No print process defined");
		}

		saveRecord();

		final ModelDataSource dataSource = getDataSource();
		final ITableRecordReference recordRef = dataSource.getTableRecordReference(getRecordIndex());

		//
		final Properties ctx = Env.getCtx();
		final int windowNo = 0; // TODO
		final int table_ID = recordRef.getAD_Table_ID();
		final int record_ID = recordRef.getRecord_ID();
		final String title = ""; // TODO
		final ProcessInfo pi = ProcessInfo.builder()
				.setAD_Process_ID(printProcessId)
				.setTitle(title)
				.setRecord(table_ID, record_ID)
				.setWindowNo(windowNo)
				// .setTabNo(getTabNo()); // TODO
				.setPrintPreview(printPreview)
				.build();
		pi.setAD_User_ID(Env.getAD_User_ID(ctx));
		pi.setAD_Client_ID(Env.getAD_Client_ID(ctx));

		Services.get(IWebProcessCtl.class).reportAsync(pi, event);
	}

	@Override
	public ViewCommandResult executeCommand(final ViewCommand viewCommand) throws Exception
	{
		final PropertyPath propertyPath = viewCommand.getPropertyPath();
		final PropertyValueCollection properties = getPropertiesLoaded();
		final PropertyValue propertyValue;
		if (propertyPath.isGridProperty())
		{
			propertyValue = properties.getPropertyValue(propertyPath.getGridPropertyName());
		}
		else
		{
			propertyValue = properties.getPropertyValue(propertyPath.getPropertyName());
		}

		final ModelCommand modelCommand = new ModelCommandImpl(viewCommand);
		final ListenableFuture<ViewCommandResult> futureResult = propertyValue.executeCommand(modelCommand);
		return ModelCommandHelper.extractResult(futureResult);
	}

	private final class ModelCommandImpl implements ModelCommand
	{
		private final ViewCommand viewCommand;

		private ModelCommandImpl(final ViewCommand viewCommand)
		{
			super();
			this.viewCommand = viewCommand;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("viewCommand", viewCommand)
					.toString();
		}

		@Override
		public PropertyPath getPropertyPath()
		{
			return viewCommand.getPropertyPath();
		}

		@Override
		public String getCommandId()
		{
			return viewCommand.getCommandId();
		}

		@Override
		public <PT> PT getParameter(final String parameterName)
		{
			return viewCommand.getParameter(parameterName);
		}

		@Override
		public int getParameterAsInt(final String parameterName, final int defaultValue)
		{
			return viewCommand.getParameterAsInt(parameterName, defaultValue);
		}

		@Override
		public String getParameterAsString(final String parameterName)
		{
			return viewCommand.getParameterAsString(parameterName);
		}

		@Override
		public <ET extends ModelEvent> void postEvent(final ET event)
		{
			WindowModelImpl.this.postEvent(event);
		}
	}
}
