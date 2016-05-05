package de.metas.ui.web.vaadin.window.prototype.order.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.adempiere.util.Check;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.vaadin.spring.annotation.PrototypeScope;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.eventbus.EventBus;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.prototype.order.GridRowId;
import de.metas.ui.web.vaadin.window.prototype.order.HARDCODED_Order;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;
import de.metas.ui.web.vaadin.window.prototype.order.WindowConstants;
import de.metas.ui.web.vaadin.window.prototype.order.WindowConstants.OnChangesFound;
import de.metas.ui.web.vaadin.window.prototype.order.datasource.ModelDataSource;
import de.metas.ui.web.vaadin.window.prototype.order.datasource.sql.NullModelDataSource;
import de.metas.ui.web.vaadin.window.prototype.order.datasource.sql.SqlModelDataSource;
import de.metas.ui.web.vaadin.window.prototype.order.model.event.AllPropertiesChangedModelEvent;
import de.metas.ui.web.vaadin.window.prototype.order.model.event.GridPropertyChangedModelEvent;
import de.metas.ui.web.vaadin.window.prototype.order.model.event.GridRowAddedModelEvent;
import de.metas.ui.web.vaadin.window.prototype.order.model.event.ModelEvent;
import de.metas.ui.web.vaadin.window.prototype.order.model.event.PropertyChangedModelEvent;

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
@PrototypeScope
public class WindowModel
{
	private static final Logger logger = LogManager.getLogger(WindowModel.class);

	private final String id = UUID.randomUUID().toString();
	private final EventBus eventBus = new EventBus(getClass().getName());

	//
	// Properties
	private PropertyDescriptor _rootPropertyDescriptor;
	private PropertyValueCollection _properties = PropertyValueCollection.EMPTY;
	private ModelDataSource _dataSource = NullModelDataSource.instance;

	private int _recordIndex = -1;

	public WindowModel()
	{
		super();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("rootPropertyDescriptor", _rootPropertyDescriptor)
				.toString();
	}

	public void setRootPropertyDescriptor(final PropertyDescriptor rootPropertyDescriptor)
	{
		if (this._rootPropertyDescriptor == rootPropertyDescriptor)
		{
			return;
		}
		Check.assumeNotNull(rootPropertyDescriptor, "Parameter rootPropertyDescriptor is not null");

		this._rootPropertyDescriptor = rootPropertyDescriptor;
		// this.dataSource = new DummyModelDataSource(rootPropertyDescriptor);
		this._dataSource = new SqlModelDataSource(rootPropertyDescriptor);

		final PropertyValueCollection.Builder propertiesCollector = PropertyValueCollection.builder();

		// Window: title
		{
			final CalculatedPropertyValue title = new HARDCODED_Order.OrderWindowTitleSummaryPropertyValue(WindowConstants.PROPERTYNAME_WindowTitle);
			propertiesCollector.addProperty(title);
		}
		// Window: record summary
		{
			final PropertyName propertyName = WindowConstants.PROPERTYNAME_RecordSummary;
			final CalculatedPropertyValue recordSummary = new HARDCODED_Order.RecordSummaryPropertyValue(propertyName);
			propertiesCollector.addProperty(recordSummary);
		}
		// Window: record additional summary
		{
			final PropertyName propertyName = WindowConstants.PROPERTYNAME_RecordAditionalSummary;
			final PropertyValue recordSummary = new HARDCODED_Order.AdditionalRecordSummaryPropertyValue(propertyName);
			propertiesCollector.addProperty(recordSummary);
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
			_recordIndex = 0;
			loadRecord();
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
		return _dataSource;
	}

	public EventBus getEventBus()
	{
		return eventBus;
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

	private void setRecordIndexAndReload(final int index)
	{
		if (index < 0)
		{
			throw new IllegalArgumentException("Invalid index: " + index);
		}
		_recordIndex = index;
		loadRecord();
	}

	private int getRecordsCount()
	{
		final ModelDataSource dataSource = getDataSource();
		return dataSource.getRecordsCount();
	}

	public boolean hasPreviousRecord()
	{
		return getRecordIndex() > 0;
	}

	public boolean hasNextRecord()
	{
		return getRecordIndex() + 1 < getRecordsCount();
	}

	private final PropertyValueCollection getProperties()
	{
		return _properties;
	}

	private final PropertyValueCollection getPropertiesLoaded()
	{
		return _properties;
	}

	private final void loadRecord()
	{
		final ModelDataSource dataSource = getDataSource();
		final int recordIndex = getRecordIndex();
		final Map<PropertyName, Object> values = dataSource.getRecord(recordIndex);

		logger.trace("Loading current record ({})", recordIndex);

		//
		//
		final PropertyValueCollection properties = getProperties();
		for (final PropertyValue propertyValue : properties.getPropertyValues())
		{
			if (ConstantPropertyValue.isConstant(propertyValue))
			{
				logger.debug("Skip setting value to {} because it's constant", propertyValue);
				continue;
			}

			final PropertyName propertyName = propertyValue.getName();
			if (values.containsKey(propertyName))
			{
				final Object value = values.get(propertyName);
				propertyValue.setValue(value);
			}
			else
			{
				propertyValue.setValue(null); // TODO: implement default
			}
		}

		//
		logger.trace("Update calculated values (after load)");
		for (final PropertyName propertyName : properties.getPropertyNames())
		{
			updateAllWhichDependOn(propertyName);
		}

		if (logger.isTraceEnabled())
			logger.trace("Loaded record {}: {}", recordIndex, TraceHelper.toStringRecursivelly(properties.getPropertyValues()));

		postEvent(AllPropertiesChangedModelEvent.of(this));
	}

	private final boolean hasChangesOnCurrentRecord()
	{
		final PropertyValueCollection properties = getProperties();
		return properties.hasChanges();
	}

	public Object getProperty(final PropertyName propertyName)
	{
		final PropertyValueCollection properties = getPropertiesLoaded();
		final PropertyValue propertyValue = properties.getPropertyValue(propertyName);
		return propertyValue.getValue();
	}

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

	private final void updateAllWhichDependOn(final PropertyName propertyName)
	{
		logger.trace("Updating all properties which depend of {}", propertyName);

		// TODO: avoid loops because of cyclic dependencies
		final PropertyValueCollection properties = getProperties();
		for (final PropertyName dependentPropertyName : properties.getPropertyNamesWhichDependOn(propertyName))
		{
			final PropertyValue dependentPropertyValue = properties.getPropertyValue(dependentPropertyName);
			if (dependentPropertyValue == null)
			{
				logger.warn("Skip setting dependent propery {} because property value is missing", dependentPropertyName);
				continue;
			}

			final Map<PropertyName, PropertyChangedModelEvent> eventsCollector = new LinkedHashMap<>();
			updateDependentPropertyValue(dependentPropertyValue, propertyName, eventsCollector);
			postEvents(eventsCollector.values());

			for (final PropertyName dependentPropertyNameLvl2 : eventsCollector.keySet())
			{
				updateAllWhichDependOn(dependentPropertyNameLvl2);
			}
		}

	}

	private final void updateDependentPropertyValue(final PropertyValue propertyValue, PropertyName changedPropertyName, final Map<PropertyName, PropertyChangedModelEvent> eventsCollector)
	{
		final PropertyValueCollection properties = getProperties();

		final Object calculatedValueOld = propertyValue.getValue();
		propertyValue.onDependentPropertyValueChanged(properties, changedPropertyName);
		final Object calculatedValueNew = propertyValue.getValue();
		if (Objects.equal(calculatedValueOld, calculatedValueNew))
		{
			return;
		}

		final PropertyName propertyName = propertyValue.getName();
		logger.trace("Updated dependent property: {}={}", propertyName, calculatedValueNew);

		eventsCollector.put(propertyName, PropertyChangedModelEvent.of(this, propertyName, calculatedValueNew, calculatedValueOld));
	}

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

	/**
	 * Ask the model to create a new grid row.
	 * 
	 * @param gridPropertyName
	 * @return the ID of newly created now
	 */
	public GridRowId gridNewRow(final PropertyName gridPropertyName)
	{
		final PropertyValueCollection properties = getPropertiesLoaded();
		final GridPropertyValue grid = (GridPropertyValue)properties.getPropertyValue(gridPropertyName);
		if (grid == null)
		{
			throw new IllegalArgumentException("No such grid property found for " + gridPropertyName);
		}
		final GridRow row = grid.newRow();
		final GridRowId rowId = row.getRowId();
		final Map<PropertyName, Object> rowValues = row.getValuesAsMap();

		postEvent(GridRowAddedModelEvent.of(this, gridPropertyName, rowId, rowValues));

		return rowId;
	}

	/**
	 * Gets a map of all "selected" property name and their values.
	 * 
	 * @param selectedPropertyNames
	 * @return
	 */
	public Map<PropertyName, Object> getPropertiesAsMap(final Set<PropertyName> selectedPropertyNames)
	{
		final PropertyValueCollection properties = getPropertiesLoaded();
		return properties.getValuesAsMap(selectedPropertyNames);
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

	public void saveRecord()
	{
		final Map<PropertyName, Object> values = getProperties().getValuesAsMap();

		final ModelDataSource dataSource = getDataSource();
		final int index = getRecordIndex();
		final int indexActual = dataSource.saveRecord(index, values);

		setRecordIndexAndReload(indexActual);
	}

	public void reloadRecord()
	{
		loadRecord();
	}

	public Iterable<Object> getPropertyAvailableValues(final PropertyName propertyName)
	{
		final PropertyValueCollection properties = getPropertiesLoaded();
		return properties.getAvailableValues(propertyName);
	}
}
