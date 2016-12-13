package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.adempiere.ad.expression.api.LogicExpressionResult;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.model.DocumentFieldChange;
import de.metas.ui.web.window.model.DocumentValidStatus;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.IDocumentFieldView;
import io.swagger.annotations.ApiModel;

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

@ApiModel("document-field")
@JsonPropertyOrder({
		"field" //
		, "value", "value-reason" //
		, "readonly", "readonly-reason" //
		, "mandatory", "mandatory-reason" //
		, "displayed", "displayed-reason" //
		, "lookupValuesStale", "lookupValuesStale-reason" //
		, "valid", "validReason" //
})
@SuppressWarnings("serial")
public final class JSONDocumentField implements Serializable
{
	public static final JSONDocumentField ofDocumentField(final IDocumentFieldView field)
	{
		final String name = field.getFieldName();
		final JSONLayoutWidgetType jsonWidgetType = JSONLayoutWidgetType.fromNullable(field.getWidgetType());
		final Object valueJSON = field.getValueAsJsonObject();
		final String reason = null; // N/A

		final JSONDocumentField jsonField = new JSONDocumentField(name, jsonWidgetType)
				.setValue(valueJSON, reason)
				.setReadonly(field.isReadonly(), reason)
				.setMandatory(field.isMandatory(), reason)
				.setDisplayed(field.isDisplayed(), reason)
				.setValidStatus(field.getValidStatus());
		if (field.isLookupValuesStale())
		{
			jsonField.setLookupValuesStale(true, reason);
		}

		if (WindowConstants.isProtocolDebugging())
		{
			jsonField.putDebugProperty(DocumentFieldChange.DEBUGPROPERTY_FieldInfo, field.toString());
		}

		return jsonField;
	}

	public static final JSONDocumentField idField(final Object jsonValue)
	{
		final String reason = null; // N/A
		return new JSONDocumentField(FIELD_VALUE_ID, JSONLayoutWidgetType.Integer)
				.setValue(jsonValue, reason);
	}

	public static final JSONDocumentField ofNameAndValue(final String fieldName, final Object jsonValue)
	{
		final String reason = null; // N/A
		final JSONLayoutWidgetType widgetType = null; // N/A
		return new JSONDocumentField(fieldName, widgetType)
				.setValue(jsonValue, reason);
	}

	public static JSONDocumentField ofDocumentFieldChangedEvent(final DocumentFieldChange event)
	{
		final JSONLayoutWidgetType widgetType = JSONLayoutWidgetType.fromNullable(event.getWidgetType());
		final JSONDocumentField jsonField = new JSONDocumentField(event.getFieldName(), widgetType);

		if (event.isValueSet())
		{
			jsonField.setValue(event.getValueAsJsonObject(), ReasonSupplier.toDebugString(event.getValueReason()));
		}

		final LogicExpressionResult readonly = event.getReadonly();
		if (readonly != null)
		{
			jsonField.setReadonly(readonly.booleanValue(), ReasonSupplier.toDebugString(event.getReadonlyReason()));
		}

		final LogicExpressionResult mandatory = event.getMandatory();
		if (mandatory != null)
		{
			jsonField.setMandatory(mandatory.booleanValue(), ReasonSupplier.toDebugString(event.getMandatoryReason()));
		}

		final LogicExpressionResult displayed = event.getDisplayed();
		if (displayed != null)
		{
			jsonField.setDisplayed(displayed.booleanValue(), ReasonSupplier.toDebugString(event.getDisplayedReason()));
		}

		final Boolean lookupValuesStale = event.getLookupValuesStale();
		if (lookupValuesStale != null)
		{
			jsonField.setLookupValuesStale(lookupValuesStale, ReasonSupplier.toDebugString(event.getLookupValuesStaleReason()));
		}
		
		final DocumentValidStatus validStatus = event.getValidStatus();
		if(validStatus != null)
		{
			jsonField.setValidStatus(validStatus);
		}

		jsonField.putDebugProperties(event.getDebugProperties());

		return jsonField;
	}

	@JsonProperty("field")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private final String field;
	public static final String FIELD_VALUE_ID = "ID";
	
	@JsonProperty("widgetType")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONLayoutWidgetType widgetType;

	@JsonProperty("value")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Object value;

	@JsonProperty("value-reason")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String valueReason;

	@JsonProperty("readonly")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean readonly;

	@JsonProperty("readonly-reason")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String readonlyReason;

	@JsonProperty("mandatory")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean mandatory;

	@JsonProperty("mandatory-reason")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String mandatoryReason;

	@JsonProperty("displayed")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean displayed;

	@JsonProperty("displayed-reason")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String displayedReason;

	@JsonProperty("lookupValuesStale")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean lookupValuesStale;

	@JsonProperty("lookupValuesStale-reason")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String lookupValuesStaleReason;
	
	@JsonProperty("valid")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean valid;
	
	@JsonProperty("validReason")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String validReason;

	/** Other properties */
	private final Map<String, Object> otherProperties = new LinkedHashMap<>();

	@JsonCreator
	/* package */ JSONDocumentField(@JsonProperty("field") final String field, @JsonProperty("widgetType") final JSONLayoutWidgetType widgetType)
	{
		super();
		this.field = field;
		this.widgetType = widgetType;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("field", field)
				.add("value", value)
				.add("value-reason", valueReason)
				.add("mandatory", mandatory)
				.add("mandatory-reason", mandatoryReason)
				.add("readonly", readonly)
				.add("readonly-reason", readonlyReason)
				.add("displayed", displayed)
				.add("displayed-reason", displayedReason)
				.add("lookupValuesStale", lookupValuesStale)
				.add("lookupValuesStale-reason", lookupValuesStaleReason)
				.add("valid", valid)
				.add("validReason", validReason)
				.add("otherProperties", otherProperties)
				.toString();
	}

	/* package */ JSONDocumentField setValue(final Object jsonValue, final String reason)
	{
		value = JSONNullValue.wrapIfNull(jsonValue);
		valueReason = reason;
		return this;
	}

	/* package */ JSONDocumentField setReadonly(final boolean readonly, final String reason)
	{
		this.readonly = readonly;
		readonlyReason = reason;
		return this;
	}

	/* package */ JSONDocumentField setMandatory(final boolean mandatory, final String reason)
	{
		this.mandatory = mandatory;
		mandatoryReason = reason;
		return this;
	}

	/* package */ JSONDocumentField setDisplayed(final boolean displayed, final String reason)
	{
		this.displayed = displayed;
		displayedReason = reason;
		return this;
	}

	/* package */ JSONDocumentField setLookupValuesStale(final boolean lookupValuesStale, final String reason)
	{
		this.lookupValuesStale = lookupValuesStale;
		lookupValuesStaleReason = reason;
		return this;
	}
	
	/*package*/ JSONDocumentField setValidStatus(final DocumentValidStatus validStatus)
	{
		if(validStatus == null)
		{
			valid = null;
			validReason = null;
		}
		else
		{
			valid = validStatus.isValid();
			validReason = validStatus.getReason();
		}
		
		return this;
	}


	public String getField()
	{
		return field;
	}

	public Object getValue()
	{
		return value;
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

	public Boolean getMandatory()
	{
		return mandatory;
	}

	public String getMandatoryReason()
	{
		return mandatoryReason;
	}

	public Boolean getDisplayed()
	{
		return displayed;
	}

	public String getDisplayedReason()
	{
		return displayedReason;
	}

	public Boolean getLookupValuesStale()
	{
		return lookupValuesStale;
	}

	public String getLookupValuesStaleReason()
	{
		return lookupValuesStaleReason;
	}
	
	@JsonAnyGetter
	public Map<String, Object> getOtherProperties()
	{
		return otherProperties;
	}

	@JsonAnySetter
	public void putOtherProperty(final String name, final Object jsonValue)
	{
		otherProperties.put(name, jsonValue);
	}

	public JSONDocumentField putDebugProperty(final String name, final Object jsonValue)
	{
		otherProperties.put("debug-" + name, jsonValue);
		return this;
	}

	public void putDebugProperties(final Map<String, Object> debugProperties)
	{
		if (debugProperties == null || debugProperties.isEmpty())
		{
			return;
		}

		for (final Map.Entry<String, Object> e : debugProperties.entrySet())
		{
			putDebugProperty(e.getKey(), e.getValue());
		}
	}

}
