package de.metas.ui.web.vaadin.window.editor;

import java.util.Set;

import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractProperty;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.GridRowId;
import de.metas.ui.web.vaadin.window.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.PropertyName;
import de.metas.ui.web.vaadin.window.WindowConstants;

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
class GridCellProperty extends AbstractProperty<Object>
{
	private static final Logger logger = LogManager.getLogger(GridCellProperty.class);

	private final PropertyDescriptor descriptor;
	private final GridRowId rowId;
	private Object value;

	private final PropertyName propertyName;
	private final PropertyName propertyName_LookupValues;
	private final PropertyName propertyName_Displayed;
	private final PropertyName propertyName_Mandatory;
	private final PropertyName propertyName_Readonly;
	private final ImmutableSet<PropertyName> watchedPropertyNames;

	private boolean mandatory = false;
	private boolean displayed = true;

	public GridCellProperty(final GridRowId rowId, final PropertyDescriptor descriptor)
	{
		super();
		this.rowId = rowId;
		this.descriptor = descriptor;

		propertyName = descriptor.getPropertyName();

		final ImmutableSet.Builder<PropertyName> watchedPropertyNames = ImmutableSet.builder();
		watchedPropertyNames.add(propertyName);

		propertyName_LookupValues = WindowConstants.lookupValuesName(propertyName);
		watchedPropertyNames.add(propertyName_LookupValues);

		propertyName_Displayed = WindowConstants.displayFlagName(propertyName);
		watchedPropertyNames.add(propertyName_Displayed);

		propertyName_Mandatory = WindowConstants.mandatoryFlagName(propertyName);
		watchedPropertyNames.add(propertyName_Mandatory);

		propertyName_Readonly = WindowConstants.readonlyFlagName(propertyName);
		watchedPropertyNames.add(propertyName_Readonly);

		this.watchedPropertyNames = watchedPropertyNames.build();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add(propertyName.toString(), value)
				.toString();
	}

	public GridRowId getRowId()
	{
		return rowId;
	}

	public PropertyName getPropertyName()
	{
		return descriptor.getPropertyName();
	}

	public final Set<PropertyName> getWatchedPropertyNames()
	{
		return watchedPropertyNames;
	}

	@Override
	public Object getValue()
	{
		return value;
	}

	public void setValue(final PropertyName propertyName, final Object newValue)
	{
		final boolean noReadOnlyCheck = true;
		setValue(propertyName, newValue, noReadOnlyCheck);
	}

	private void setValue(final PropertyName propertyName, final Object newValue, final boolean noReadOnlyCheck)
	{
		logger.trace("Setting {}={} (noReadOnlyCheck={})", propertyName, newValue, noReadOnlyCheck);

		if (Objects.equal(propertyName, this.propertyName))
		{
			// Checks the mode
			if (!noReadOnlyCheck && isReadOnly())
			{
				throw new Property.ReadOnlyException();
			}

			if (Objects.equal(value, newValue))
			{
				return;
			}

			value = newValue;
			fireValueChange();
		}
		else if (Objects.equal(propertyName, propertyName_Readonly))
		{
			final boolean readonly = DisplayType.toBoolean(newValue);
			setReadOnly(readonly);
		}
		else if (Objects.equal(propertyName, propertyName_Displayed))
		{
			final boolean displayed = DisplayType.toBoolean(newValue);
			setDisplayed(displayed);
		}
		else if (Objects.equal(propertyName, propertyName_Mandatory))
		{
			final boolean mandatory = DisplayType.toBoolean(newValue);
			setMandatory(mandatory);
		}
		else if (Objects.equal(propertyName, propertyName_LookupValues))
		{
			// TODO
		}
		else
		{
			logger.warn("Property '{}' not handled by {}", propertyName, this);
		}
	}

	@Override
	public void setValue(final Object newValue) throws com.vaadin.data.Property.ReadOnlyException
	{
		final boolean noReadOnlyCheck = false;
		setValue(propertyName, newValue, noReadOnlyCheck);
	}

	@Override
	public Class<? extends Object> getType()
	{
		return descriptor.getValueType();
	}

	public final boolean isMandatory()
	{
		return mandatory;
	}

	public final void setMandatory(final boolean mandatory)
	{
		final boolean mandatoryOld = this.mandatory;
		if (mandatoryOld == mandatory)
		{
			return;
		}
		this.mandatory = mandatory;
		// TODO: fire event
	}

	public final boolean isDisplayed()
	{
		return displayed;
	}

	public final void setDisplayed(final boolean displayed)
	{
		final boolean displayedOld = this.displayed;
		if (displayedOld == displayed)
		{
			return;
		}
		this.displayed = displayed;
		// TODO: fire event
	}

}