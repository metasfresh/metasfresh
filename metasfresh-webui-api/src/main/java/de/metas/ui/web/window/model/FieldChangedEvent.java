package de.metas.ui.web.window.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

import de.metas.ui.web.window.util.JSONConverters;

/*
 * #%L
 * metasfresh-webui-api
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
 * Mutable field changed event.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class FieldChangedEvent
{
	/* package */static final FieldChangedEvent of(final String fieldName)
	{
		return new FieldChangedEvent(fieldName);
	}

	private final String fieldName;
	private Object value;
	private boolean valueSet;
	private Boolean readonly;
	private Boolean mandatory;
	private Boolean displayed;
	private Boolean lookupValuesStale;

	private FieldChangedEvent(final String fieldName)
	{
		super();
		this.fieldName = fieldName;
	}

	@Override
	public String toString()
	{
		final ToStringHelper toStringBuilder = MoreObjects.toStringHelper(this);
		toStringBuilder.add("fieldName", fieldName);

		if (valueSet)
		{
			toStringBuilder.add("value", value);
		}
		if (readonly != null)
		{
			toStringBuilder.add("readonly", readonly);
		}
		if (mandatory != null)
		{
			toStringBuilder.add("mandatory", mandatory);
		}
		if (displayed != null)
		{
			toStringBuilder.add("displayed", displayed);
		}

		return toStringBuilder.toString();
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public void setValue(final Object value)
	{
		this.value = value;
		valueSet = true;
	}

	public boolean isValueSet()
	{
		return valueSet;
	}

	public Object getValueAsJsonObject()
	{
		return JSONConverters.valueToJsonObject(value);
	}

	public Boolean getReadonly()
	{
		return readonly;
	}

	public void setReadonly(final Boolean readonly)
	{
		this.readonly = readonly;
	}

	public Boolean getMandatory()
	{
		return mandatory;
	}

	public void setMandatory(final Boolean mandatory)
	{
		this.mandatory = mandatory;
	}

	public Boolean getDisplayed()
	{
		return displayed;
	}

	public void setDisplayed(final Boolean displayed)
	{
		this.displayed = displayed;
	}

	public Boolean getLookupValuesStale()
	{
		return lookupValuesStale;
	}

	public void setLookupValuesStale(final Boolean lookupValuesStale)
	{
		this.lookupValuesStale = lookupValuesStale;
	}
}
