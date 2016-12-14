package de.metas.ui.web.window.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;

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
 * Mutable document field change.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class DocumentFieldChange
{
	/* package */static final DocumentFieldChange of(final String fieldName, final boolean key, final boolean publicField, final boolean advancedField, final DocumentFieldWidgetType widgetType)
	{
		return new DocumentFieldChange(fieldName, key, publicField, advancedField, widgetType);
	}

	private static final Logger logger = LogManager.getLogger(DocumentFieldChange.class);

	public static final String DEBUGPROPERTY_FieldInfo = "field-info";

	private final String fieldName;
	/** Is key column? */
	private final boolean key;
	/** <code>true</code> if this field is public and will be published to API clients */
	private final boolean publicField;
	private final boolean advancedField;
	private final DocumentFieldWidgetType widgetType;

	//
	private boolean valueSet;
	private Object value;
	private ReasonSupplier valueReason;
	//
	private LogicExpressionResult readonly;
	private ReasonSupplier readonlyReason;
	//
	private LogicExpressionResult mandatory;
	private ReasonSupplier mandatoryReason;
	//
	private LogicExpressionResult displayed;
	private ReasonSupplier displayedReason;
	//
	private Boolean lookupValuesStale;
	private ReasonSupplier lookupValuesStaleReason;
	//
	private DocumentValidStatus validStatus;

	private Map<String, Object> debugProperties;


	private DocumentFieldChange(final String fieldName, final boolean key, final boolean publicField, final boolean advancedField, final DocumentFieldWidgetType widgetType)
	{
		super();
		this.fieldName = Preconditions.checkNotNull(fieldName, "fieldName shall not be null");
		this.key = key;
		this.publicField = publicField;
		this.advancedField = advancedField;
		this.widgetType = widgetType;
	}

	@Override
	public String toString()
	{
		final ToStringHelper toStringBuilder = MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("fieldName", fieldName)
				.add("key", key ? Boolean.TRUE : null)
				.add("privateField", !publicField ? Boolean.TRUE : null)
				.add("advancedField", advancedField ? Boolean.TRUE : null)
				.add("widgetType", widgetType)
				.add("validStatus", validStatus);
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

		if (debugProperties != null && !debugProperties.isEmpty())
		{
			toStringBuilder.add("debugProperties", debugProperties);
		}

		return toStringBuilder.toString();
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public boolean isKey()
	{
		return key;
	}

	/**
	 * @return true if this field is public and will be published to API clients
	 */
	public boolean isPublicField()
	{
		return publicField;
	}

	public boolean isAdvancedField()
	{
		return advancedField;
	}
	
	public DocumentFieldWidgetType getWidgetType()
	{
		return widgetType;
	}

	/* package */void setValue(final Object value, final ReasonSupplier reason)
	{
		valueSet = true;
		this.value = value;
		valueReason = reason;

		if (logger.isTraceEnabled())
		{
			logger.trace("collect {} value: {} -- {}", fieldName, value, reason == null ? null : reason.get());
		}
	}

	public boolean isValueSet()
	{
		return valueSet;
	}

	public Object getValue()
	{
		return value;
	}

	public Object getValueAsJsonObject()
	{
		return Values.valueToJsonObject(value);
	}

	public ReasonSupplier getValueReason()
	{
		return valueReason;
	}

	public LogicExpressionResult getReadonly()
	{
		return readonly;
	}

	public ReasonSupplier getReadonlyReason()
	{
		return readonlyReason;
	}

	/* package */void setReadonly(final LogicExpressionResult readonly, final ReasonSupplier reason)
	{
		this.readonly = readonly;
		readonlyReason = reason;
		if (logger.isTraceEnabled())
		{
			logger.trace("collect {} readonly: {} -- {}", fieldName, readonly, reason == null ? null : reason.get());
		}
	}

	public LogicExpressionResult getMandatory()
	{
		return mandatory;
	}

	public ReasonSupplier getMandatoryReason()
	{
		return mandatoryReason;
	}

	/* package */void setMandatory(final LogicExpressionResult mandatory, final ReasonSupplier reason)
	{
		this.mandatory = mandatory;
		mandatoryReason = reason;
		if (logger.isTraceEnabled())
		{
			logger.trace("collect {} mandatory: {} -- {}", fieldName, mandatory, reason == null ? null : reason.get());
		}
	}

	public LogicExpressionResult getDisplayed()
	{
		return displayed;
	}

	public ReasonSupplier getDisplayedReason()
	{
		return displayedReason;
	}

	/* package */void setDisplayed(final LogicExpressionResult displayed, final ReasonSupplier reason)
	{
		this.displayed = displayed;
		displayedReason = reason;
		if (logger.isTraceEnabled())
		{
			logger.trace("collect {} displayed: {} -- {}", fieldName, displayed, reason == null ? null : reason.get());
		}
	}

	public Boolean getLookupValuesStale()
	{
		return lookupValuesStale;
	}

	public ReasonSupplier getLookupValuesStaleReason()
	{
		return lookupValuesStaleReason;
	}

	/* package */void setLookupValuesStale(final Boolean lookupValuesStale, final ReasonSupplier reason)
	{
		this.lookupValuesStale = lookupValuesStale;
		lookupValuesStaleReason = reason;
		logger.trace("collect {} lookupValuesStale: {} -- {}", fieldName, lookupValuesStale, lookupValuesStaleReason);
	}
	
	public DocumentValidStatus getValidStatus()
	{
		return validStatus;
	}
	
	/*package*/void setValidStatus(final DocumentValidStatus validStatus)
	{
		this.validStatus = validStatus;
		logger.trace("collect {} validStatus: {}", fieldName, validStatus);
	}

	/* package */ void mergeFrom(final DocumentFieldChange fromEvent)
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
		
		if(fromEvent.validStatus != null)
		{
			validStatus = fromEvent.validStatus;
		}

		putDebugProperties(fromEvent.debugProperties);
	}

	public void putDebugProperty(final String name, final Object value)
	{
		if (debugProperties == null)
		{
			debugProperties = new LinkedHashMap<>();
		}
		debugProperties.put(name, value);
	}

	public void putDebugProperties(final Map<String, Object> debugProperties)
	{
		if (debugProperties == null || debugProperties.isEmpty())
		{
			return;
		}

		if (this.debugProperties == null)
		{
			this.debugProperties = new LinkedHashMap<>();
		}
		this.debugProperties.putAll(debugProperties);
	}

	public Map<String, Object> getDebugProperties()
	{
		return debugProperties == null ? ImmutableMap.of() : debugProperties;
	}
}
