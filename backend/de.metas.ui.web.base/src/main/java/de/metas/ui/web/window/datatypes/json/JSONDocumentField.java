package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import de.metas.ui.web.devices.JSONDeviceDescriptor;
import de.metas.ui.web.process.IProcessInstanceParameter;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.Password;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.model.DocumentFieldChange;
import de.metas.ui.web.window.model.DocumentValidStatus;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.IDocumentFieldView;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import org.adempiere.ad.expression.api.LogicExpressionResult;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ApiModel("document-field")
@JsonPropertyOrder({
		"field",
		"value", "value-reason",
		"readonly", "readonly-reason",
		"mandatory", "mandatory-reason",
		"displayed", "displayed-reason",
		"lookupValuesStale", "lookupValuesStale-reason",
		"valid", "validReason"
})
@Getter
public final class JSONDocumentField
{
	public static JSONDocumentField ofDocumentField(final IDocumentFieldView field, final JSONOptions jsonOpts)
	{
		final String name = field.getFieldName();
		final JSONLayoutWidgetType jsonWidgetType = JSONLayoutWidgetType.fromNullable(field.getWidgetType());
		final Object valueJSON = field.getValueAsJsonObject(jsonOpts);

		final JSONDocumentField jsonField = new JSONDocumentField(name, jsonWidgetType)
				.setValue(valueJSON, null)
				.setReadonly(field.getReadonly())
				.setMandatory(field.getMandatory())
				.setDisplayed(field.getDisplayed())
				.setValidStatus(field.getValidStatus());
		if (field.isLookupValuesStale())
		{
			jsonField.setLookupValuesStale(true, null);
		}

		if (WindowConstants.isProtocolDebugging())
		{
			jsonField.putDebugProperty(DocumentFieldChange.DEBUGPROPERTY_FieldInfo, field.toString());
		}

		return jsonField;
	}

	public static JSONDocumentField ofProcessParameter(final IProcessInstanceParameter parameter, final JSONOptions jsonOpts)
	{
		final String name = parameter.getParameterName();
		final JSONLayoutWidgetType jsonWidgetType = JSONLayoutWidgetType.fromNullable(parameter.getWidgetType());
		final Object valueJSON = parameter.getValueAsJsonObject(jsonOpts);

		final JSONDocumentField jsonField = new JSONDocumentField(name, jsonWidgetType)
				.setValue(valueJSON, null)
				.setReadonly(parameter.getReadonly())
				.setMandatory(parameter.getMandatory())
				.setDisplayed(parameter.getDisplayed())
				.setValidStatus(parameter.getValidStatus())
				.setDevices(JSONDeviceDescriptor.ofList(parameter.getDevices(), jsonOpts.getAdLanguage()));
		if (WindowConstants.isProtocolDebugging())
		{
			jsonField.putDebugProperty(DocumentFieldChange.DEBUGPROPERTY_FieldInfo, parameter.toString());
		}

		return jsonField;
	}

	public static JSONDocumentField idField(final Object jsonValue)
	{
		return new JSONDocumentField(FIELD_VALUE_ID, JSONLayoutWidgetType.Integer)
				.setValue(jsonValue, null);
	}

	public static JSONDocumentField ofNameAndValue(final String fieldName, final Object jsonValue)
	{
		return new JSONDocumentField(fieldName, null)
				.setValue(jsonValue, null);
	}

	public static JSONDocumentField ofDocumentFieldChangedEvent(final DocumentFieldChange event, final JSONOptions jsonOpts)
	{
		final JSONLayoutWidgetType widgetType = JSONLayoutWidgetType.fromNullable(event.getWidgetType());
		final JSONDocumentField jsonField = new JSONDocumentField(event.getFieldName(), widgetType);

		if (event.isValueSet())
		{
			jsonField.setValue(event.getValueAsJsonObject(jsonOpts), ReasonSupplier.toDebugString(event.getValueReason()));
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
		if (validStatus != null)
		{
			jsonField.setValidStatus(validStatus);
		}

		jsonField.setFieldWarning(JSONDocumentFieldWarning.ofNullable(event.getFieldWarning(), jsonOpts.getAdLanguage()));

		jsonField.putDebugProperties(event.getDebugProperties());

		return jsonField;
	}

	@JsonProperty("field")
	private final String field;
	public static final String FIELD_VALUE_ID = "ID";

	@JsonProperty("widgetType")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable private JSONLayoutWidgetType widgetType;

	@JsonProperty("precision")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable private Integer precision;

	@JsonProperty("value")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable private Object value;

	@JsonProperty("value-reason")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable private String valueReason;

	@JsonProperty("readonly")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean readonly;

	@JsonProperty("readonly-reason")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable private String readonlyReason;

	@JsonProperty("mandatory")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean mandatory;

	@JsonProperty("mandatory-reason")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable private String mandatoryReason;

	@JsonProperty("displayed")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean displayed;

	@JsonProperty("displayed-reason")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable private String displayedReason;

	@JsonProperty("lookupValuesStale")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean lookupValuesStale;

	@JsonProperty("lookupValuesStale-reason")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable private String lookupValuesStaleReason;

	@JsonProperty("validStatus")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private DocumentValidStatus validStatus;

	@JsonProperty("viewEditorRenderMode")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable private String viewEditorRenderMode;

	@JsonProperty("warning")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable private JSONDocumentFieldWarning fieldWarning;

	@JsonProperty("devices")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Nullable private List<JSONDeviceDescriptor> devices;

	/**
	 * Other properties
	 */
	private final Map<String, Object> otherProperties = new LinkedHashMap<>();

	@JsonCreator
		/* package */ JSONDocumentField(
			@JsonProperty("field") final String field,
			@JsonProperty("widgetType") @Nullable final JSONLayoutWidgetType widgetType)
	{
		this.field = field;
		this.widgetType = widgetType;
		this.precision = widgetType != null ? widgetType.getStandardNumberPrecision() : null;
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
				.add("validStatus", validStatus)
				.add("otherProperties", otherProperties)
				.toString();
	}

	/* package */ JSONDocumentField setValue(final Object jsonValue, @Nullable final String reason)
	{
		value = JSONNullValue.wrapIfNull(jsonValue);
		valueReason = reason;
		return this;
	}

	/* package */ void unboxPasswordField()
	{
		final Object value = this.value;
		if (value instanceof Password)
		{
			this.value = ((Password)value).getAsString();
		}
	}

	public JSONDocumentField setWidgetType(@Nullable final JSONLayoutWidgetType widgetType)
	{
		this.widgetType = widgetType;
		this.precision = widgetType != null ? widgetType.getStandardNumberPrecision() : null;
		return this;
	}

	public boolean isReadonly()
	{
		return readonly != null && readonly;
	}

	public void setReadonly(final boolean readonly, @Nullable final String reason)
	{
		this.readonly = readonly;
		readonlyReason = reason;
		setViewEditorRenderMode(readonly ? ViewEditorRenderMode.NEVER : ViewEditorRenderMode.ALWAYS);
	}

	public JSONDocumentField setReadonly(final LogicExpressionResult readonly)
	{
		setReadonly(readonly.booleanValue(), readonly.getName());
		return this;
	}

	public JSONDocumentField setReadonly(final boolean readonly)
	{
		setReadonly(readonly, null);
		return this;
	}

	private void setMandatory(final boolean mandatory, @Nullable final String reason)
	{
		this.mandatory = mandatory;
		mandatoryReason = reason;
	}

	public JSONDocumentField setMandatory(final LogicExpressionResult mandatory)
	{
		setMandatory(mandatory.booleanValue(), mandatory.getName());
		return this;
	}

	public JSONDocumentField setMandatory(final boolean mandatory)
	{
		setMandatory(mandatory, null);
		return this;
	}

	public JSONDocumentField setDisplayed(final boolean displayed, @Nullable final String reason)
	{
		this.displayed = displayed;
		displayedReason = reason;
		return this;
	}

	public JSONDocumentField setDisplayed(final LogicExpressionResult displayed)
	{
		setDisplayed(displayed.booleanValue(), displayed.getName());
		return this;
	}

	public JSONDocumentField setDisplayed(final boolean displayed)
	{
		setDisplayed(displayed, null);
		return this;
	}

	/* package */ void setLookupValuesStale(final boolean lookupValuesStale, @Nullable final String reason)
	{
		this.lookupValuesStale = lookupValuesStale;
		lookupValuesStaleReason = reason;
	}

	/* package */ JSONDocumentField setValidStatus(final DocumentValidStatus validStatus)
	{
		this.validStatus = validStatus;
		return this;
	}

	public String getField()
	{
		return field;
	}

	@Nullable
	public Object getValue()
	{
		return value;
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

	public JSONDocumentField setViewEditorRenderMode(final ViewEditorRenderMode viewEditorRenderMode)
	{
		this.viewEditorRenderMode = viewEditorRenderMode != null ? viewEditorRenderMode.toJson() : null;
		return this;
	}

	public void setFieldWarning(@Nullable final JSONDocumentFieldWarning fieldWarning)
	{
		this.fieldWarning = fieldWarning;
	}

	public JSONDocumentField setDevices(@Nullable final List<JSONDeviceDescriptor> devices)
	{
		this.devices = devices;
		return this;
	}
}
