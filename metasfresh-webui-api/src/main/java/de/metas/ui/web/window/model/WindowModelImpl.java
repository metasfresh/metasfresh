package de.metas.ui.web.window.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.ZoomInfoFactory.ZoomInfo;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.process.ProcessInfo;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;

import de.metas.logging.LogManager;
import de.metas.ui.web.service.IImageProvider;
import de.metas.ui.web.service.IWebProcessCtl;
import de.metas.ui.web.window.HARDCODED_Order;
import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.WindowConstants.OnChangesFound;
import de.metas.ui.web.window.datasource.IDataSourceFactory;
import de.metas.ui.web.window.datasource.ModelDataSource;
import de.metas.ui.web.window.datasource.SaveResult;
import de.metas.ui.web.window.descriptor.PropertyDescriptor;
import de.metas.ui.web.window.model.action.Action;
import de.metas.ui.web.window.model.action.Action.ActionEvent;
import de.metas.ui.web.window.model.action.Action.Listener;
import de.metas.ui.web.window.model.action.ActionGroup;
import de.metas.ui.web.window.model.event.AllPropertiesChangedModelEvent;
import de.metas.ui.web.window.model.event.GridPropertyChangedModelEvent;
import de.metas.ui.web.window.model.event.GridRowAddedModelEvent;
import de.metas.ui.web.window.model.event.ModelEvent;
import de.metas.ui.web.window.model.event.PropertyChangedModelEvent;
import de.metas.ui.web.window.model.event.ZoomToWindowEvent;
import de.metas.ui.web.window.shared.datatype.GridRowId;
import de.metas.ui.web.window.shared.datatype.PropertyValuesDTO;

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

@Component
@Scope("prototype")
/* package */class WindowModelImpl implements WindowModel
{
	private static final Logger logger = LogManager.getLogger(WindowModelImpl.class);

	private final String id = UUID.randomUUID().toString();
	private final EventBus eventBus = new EventBus(getClass().getName());

	//
	// Properties
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

	public WindowModelImpl()
	{
		super();
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
	public void setRootPropertyDescriptor(final PropertyDescriptor rootPropertyDescriptor)
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

		postEvent(AllPropertiesChangedModelEvent.of(this));
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
	public boolean hasProperty(final PropertyName propertyName)
	{
		final PropertyValueCollection properties = getPropertiesLoaded();
		return properties.getPropertyValueOrNull(propertyName) != null;

	}

	@Override
	public Object getProperty(final PropertyName propertyName)
	{
		final PropertyValueCollection properties = getPropertiesLoaded();
		final PropertyValue propertyValue = properties.getPropertyValue(propertyName);
		return propertyValue.getValue();
	}

	@Override
	public Object getPropertyOrNull(final PropertyName propertyName)
	{
		final PropertyValueCollection properties = getPropertiesLoaded();
		final PropertyValue propertyValue = properties.getPropertyValueOrNull(propertyName);
		if (propertyValue == null)
		{
			return null;
		}
		return propertyValue.getValue();
	}

	@Override
	public void setProperty(final PropertyName propertyName, final Object value)
	{
		if (logger.isTraceEnabled())
		{
			logger.trace("Setting property: {}={} ({})", propertyName, value, value == null ? null : value.getClass());
		}

		final PropertyValueCollection properties = getPropertiesLoaded();
		final PropertyValue prop = properties.getPropertyValue(propertyName);
		if (prop == null)
		{
			// TODO: handle missing model property
			logger.trace("Skip setting propery {} because property value is missing", propertyName);
			return;
		}

		if (prop.isReadOnlyForUser())
		{
			logger.trace("Skip setting propery {} because property value is readonly for user", propertyName);
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
		postEvent(PropertyChangedModelEvent.of(this, propertyName, value, valueOld));

		//
		// Update dependencies
		updateAllWhichDependOn(propertyName);
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
			eventsCollector.put(propertyName, PropertyChangedModelEvent.of(this, propertyName, calculatedValueNew, calculatedValueOld));
		}
	}

	@Override
	public void setGridProperty(final PropertyName gridPropertyName, final Object rowId, final PropertyName propertyName, final Object value)
	{
		if (logger.isTraceEnabled())
		{
			logger.trace("Setting grid property {}, {}: {}={} ({})", gridPropertyName, rowId, propertyName, value, value == null ? null : value.getClass());
		}

		final PropertyValueCollection properties = getPropertiesLoaded();
		final GridPropertyValue gridProp = GridPropertyValue.cast(properties.getPropertyValue(gridPropertyName));
		if (gridProp == null)
		{
			// TODO: handle missing model property
			logger.trace("Skip setting propery {} because property value is missing", propertyName);
			return;
		}

		final Object valueOld = gridProp.setValueAt(rowId, propertyName, value);

		//
		// Check if it's an actual value change
		if (Check.equals(value, valueOld))
		{
			return;
		}

		// Fire event
		postEvent(GridPropertyChangedModelEvent.of(this, gridPropertyName, rowId, propertyName, value, valueOld));

		// TODO: process dependencies
	}

	@Override
	public Object getGridProperty(final PropertyName gridPropertyName, final Object rowId, final PropertyName propertyName)
	{
		final PropertyValueCollection properties = getPropertiesLoaded();
		final GridPropertyValue gridProp = GridPropertyValue.cast(properties.getPropertyValueOrNull(gridPropertyName));
		if (gridProp == null)
		{
			// TODO: handle missing model property
			logger.trace("Skip setting propery {} because property value is missing", propertyName);
			return null;
		}

		final Object value = gridProp.getValueAt(rowId, propertyName);
		return value;
	}

	@Override
	public GridRowId gridNewRow(final PropertyName gridPropertyName)
	{
		final PropertyValueCollection properties = getPropertiesLoaded();
		final GridPropertyValue grid = GridPropertyValue.cast(properties.getPropertyValue(gridPropertyName));
		if (grid == null)
		{
			throw new IllegalArgumentException("No such grid property found for " + gridPropertyName);
		}

		final GridRow row = grid.newRow(PropertyValuesDTO.of());
		final GridRowId rowId = row.getRowId();
		final PropertyValuesDTO rowValues = row.getValuesAsMap();

		postEvent(GridRowAddedModelEvent.of(this, gridPropertyName, rowId, rowValues));

		return rowId;
	}

	@Override
	public PropertyValuesDTO getPropertyValuesDTO(final Set<PropertyName> selectedPropertyNames)
	{
		if (selectedPropertyNames.isEmpty())
		{
			return PropertyValuesDTO.of();
		}
		final PropertyValueCollection properties = getPropertiesLoaded();
		return properties.getValuesAsMap(selectedPropertyNames);
	}

	private void setFromPropertyValuesDTO(final PropertyValuesDTO values)
	{
		// TODO: find a way to aggregate all events and send them all together

		for (final Map.Entry<PropertyName, Object> e : values.entrySet())
		{
			final PropertyName propertyName = e.getKey();
			final Object value = e.getValue();
			setProperty(propertyName, value);
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

	@Override
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

	@Override
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

	@Override
	public List<Action> getActions()
	{
		final ActionGroup crudActionGroup = ActionGroup.of("CRUD");
		final ActionGroup reportActionGroup = ActionGroup.of("Report");
		final ActionGroup goActionGroup = ActionGroup.of("Go");
		final ActionGroup helpActionGroup = ActionGroup.of("Help");

		return ImmutableList.<Action> builder()
				//
				.add(createActionWithListener(crudActionGroup, "Ignore", event -> cancelRecordEditing()).setToolbarAction().build())
				.add(createActionWithListener(crudActionGroup, "New", event -> newRecord()).setToolbarAction().build())
				.add(createActionWithListener(crudActionGroup, "Save", event -> saveRecord()).setToolbarAction().build())
				.add(createActionWithListener(crudActionGroup, "Copy", ACTIONLISTENER_NotImpleted).build())
				.add(createActionWithListener(crudActionGroup, "CopyDetails", ACTIONLISTENER_NotImpleted).build())
				.add(createActionWithListener(crudActionGroup, "Delete", ACTIONLISTENER_NotImpleted).build())
				.add(createActionWithListener(crudActionGroup, "DeleteSelection", ACTIONLISTENER_NotImpleted).build())
				.add(createActionWithListener(crudActionGroup, "Refresh", ACTIONLISTENER_NotImpleted).build())
				//
				.add(createActionWithListener(reportActionGroup, "Report", ACTIONLISTENER_NotImpleted).build())
				.add(createActionWithListener(reportActionGroup, "Print", event -> print(event, false)).build())
				.add(createActionWithListener(reportActionGroup, "PrintPreview", event -> print(event, true)).build())
				//
				.add(createActionWithChildrenProvider(goActionGroup, "ZoomAcross", () -> createZoomAccrossActions()))
				.add(createActionWithListener(goActionGroup, "Request", ACTIONLISTENER_NotImpleted).build())
				.add(createActionWithListener(goActionGroup, "Archive", ACTIONLISTENER_NotImpleted).build())
				//
				.add(createActionWithListener(helpActionGroup, "Help", ACTIONLISTENER_NotImpleted).build())
				//
				.build();
	}

	private Action.Builder createActionWithListener(final ActionGroup actionGroup, final String actionId, final Action.Listener listener)
	{
		return Action.builder()
				.setActionGroup(actionGroup)
				.setActionIdAndUpdateFromAD_Messages(actionId)
				.setActionId(actionId)
				.setListener(listener);
	}

	private Action createActionWithChildrenProvider(final ActionGroup actionGroup, final String actionId, final Action.Provider childrenProvider)
	{
		return Action.builder()
				.setActionGroup(actionGroup)
				.setActionIdAndUpdateFromAD_Messages(actionId)
				.setChildrenProvider(childrenProvider)
				.build();
	}

	@Override
	public void executeAction(final Action action)
	{
		if (action instanceof Action.Listener)
		{
			final Listener listener = (Action.Listener)action;
			listener.handleAction(ActionEvent.of(action, this));
		}
		else
		{
			throw new UnsupportedOperationException("Unsupported action: " + action);
		}
	}

	private static final Action.Listener ACTIONLISTENER_NotImpleted = event -> {
		throw new UnsupportedOperationException("Action not implemented: " + event.getAction());
	};

	private List<Action> createZoomAccrossActions()
	{
		final int recordIndex = getRecordIndex();
		if (recordIndex < 0)
		{
			return ImmutableList.of();
		}

		final ModelDataSource dataSource = getDataSource();
		final List<ZoomInfo> zoomInfos = dataSource.retrieveZoomAccrossInfos(recordIndex);

		final ImmutableList.Builder<Action> actionsCollector = ImmutableList.builder();
		for (final ZoomInfo zoomInfo : zoomInfos)
		{
			final Action action = Action.builder()
					.setActionId(zoomInfo.getId())
					.setCaption(zoomInfo.getLabel())
					.setIcon(Services.get(IImageProvider.class).getImageResourceForNameWithoutExt(IImageProvider.ICONNAME_Window))
					.setListener(target -> {
						postEvent(ZoomToWindowEvent.of(WindowModelImpl.this, zoomInfo));
					})
					.build();
			actionsCollector.add(action);
		}

		final List<Action> actions = actionsCollector.build();
		if (actions.isEmpty())
		{
			throw new AdempiereException("No zoom targets found");
		}

		return actions;
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
		final ProcessInfo pi = new ProcessInfo(title, printProcessId, table_ID, record_ID);
		pi.setAD_User_ID(Env.getAD_User_ID(ctx));
		pi.setAD_Client_ID(Env.getAD_Client_ID(ctx));
		pi.setPrintPreview(printPreview);
		pi.setWindowNo(windowNo); // metas: 03040
		// pi.setTabNo(getTabNo()); // TODO

		Services.get(IWebProcessCtl.class).reportAsync(pi, event);
	}
}
