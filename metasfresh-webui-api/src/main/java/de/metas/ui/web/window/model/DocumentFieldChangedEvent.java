package de.metas.ui.web.window.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.base.Preconditions;

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
public final class DocumentFieldChangedEvent
{
	/* package */static final DocumentFieldChangedEvent of(final String fieldName)
	{
		return new DocumentFieldChangedEvent(fieldName);
	}

	private final String fieldName;
	//
	private boolean valueSet;
	private Object value;
	private String valueReason;
	//
	private Boolean readonly;
	private String readonlyReason;
	//
	private Boolean mandatory;
	private String mandatoryReason;
	//
	private Boolean displayed;
	private String displayedReason;
	//
	private Boolean lookupValuesStale;
	private String lookupValuesStaleReason;

	private DocumentFieldChangedEvent(final String fieldName)
	{
		super();
		this.fieldName = Preconditions.checkNotNull(fieldName, "fieldName shall not be null");
	}

	@Override
	public String toString()
	{
		final ToStringHelper toStringBuilder = MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("fieldName", fieldName);

		if (valueSet)
		{
			toStringBuilder.add("value", value == null ? "<NULL>" : value);
			toStringBuilder.add("valueReason", valueReason);
		}
		if (readonly != null)
		{
			toStringBuilder.add("readonly", readonly);
			toStringBuilder.add("readonlyReason", readonlyReason);
		}
		if (mandatory != null)
		{
			toStringBuilder.add("mandatory", mandatory);
			toStringBuilder.add("mandatoryReason", mandatoryReason);
		}
		if (displayed != null)
		{
			toStringBuilder.add("displayed", displayed);
			toStringBuilder.add("displayedReason", displayedReason);
		}
		if (lookupValuesStale != null)
		{
			toStringBuilder.add("lookupValuesStale", lookupValuesStale);
			toStringBuilder.add("lookupValuesStaleReason", lookupValuesStaleReason);
		}

		return toStringBuilder.toString();
	}

	public String getFieldName()
	{
		return fieldName;
	}

	/* package */void setValue(final Object value, final String reason)
	{
		valueSet = true;
		this.value = value;
		valueReason = reason;
	}

	public boolean isValueSet()
	{
		return valueSet;
	}

	public Object getValueAsJsonObject()
	{
		return JSONConverters.valueToJsonObject(value);
	}

	public String getValueReason()
	{
		return valueReason;
	}

	public Boolean getReadonly()
	{
		return readonly;
	}

	public String getReadonlyReason()
	{
		return readonlyReason;
	}

	/* package */void setReadonly(final Boolean readonly, final String reason)
	{
		this.readonly = readonly;
		readonlyReason = reason;
	}

	public Boolean getMandatory()
	{
		return mandatory;
	}

	public String getMandatoryReason()
	{
		return mandatoryReason;
	}

	/* package */void setMandatory(final Boolean mandatory, final String reason)
	{
		this.mandatory = mandatory;
		mandatoryReason = reason;
	}

	public Boolean getDisplayed()
	{
		return displayed;
	}

	public String getDisplayedReason()
	{
		return displayedReason;
	}

	/* package */void setDisplayed(final Boolean displayed, final String reason)
	{
		this.displayed = displayed;
		displayedReason = reason;
	}

	public Boolean getLookupValuesStale()
	{
		return lookupValuesStale;
	}

	public String getLookupValuesStaleReason()
	{
		return lookupValuesStaleReason;
	}

	/* package */void setLookupValuesStale(final Boolean lookupValuesStale, final String reason)
	{
		this.lookupValuesStale = lookupValuesStale;
		lookupValuesStaleReason = reason;
	}

	/* package */ void mergeFrom(final DocumentFieldChangedEvent fromEvent)
	{
		if (fromEvent.valueSet)
		{
			valueSet = true;
			value = fromEvent.value;
			valueReason = fromEvent.valueReason;
		}
		//
		if (fromEvent.readonly != null)
		{
			readonly = fromEvent.readonly;
			readonlyReason = fromEvent.readonlyReason;
		}
		//
		if (fromEvent.mandatory != null)
		{
			mandatory = fromEvent.mandatory;
			mandatoryReason = fromEvent.mandatoryReason;
		}
		//
		if (fromEvent.displayed != null)
		{
			displayed = fromEvent.displayed;
			displayedReason = fromEvent.displayedReason;
		}
		//
		if (fromEvent.lookupValuesStale != null)
		{
			lookupValuesStale = fromEvent.lookupValuesStale;
			lookupValuesStaleReason = fromEvent.lookupValuesStaleReason;
		}
	}
}
