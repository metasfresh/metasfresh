package de.metas.ui.web.document.filter.json;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.process.BarcodeScannerType;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions;
import de.metas.ui.web.window.datatypes.json.JSONLayoutType;
import de.metas.ui.web.window.datatypes.json.JSONLayoutWidgetType;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.util.GuavaCollectors;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@ToString
/* package */final class JSONDocumentFilterParamDescriptor
{
	/* package */static List<JSONDocumentFilterParamDescriptor> ofCollection(
			final Collection<DocumentFilterParamDescriptor> params,
			final JSONDocumentLayoutOptions options)
	{
		return params.stream()
				.map(filter -> of(filter, options))
				.collect(GuavaCollectors.toImmutableList());
	}

	private static JSONDocumentFilterParamDescriptor of(final DocumentFilterParamDescriptor param, final JSONDocumentLayoutOptions jsonOpts)
	{
		return new JSONDocumentFilterParamDescriptor(param, jsonOpts);
	}

	@JsonProperty("caption")
	private final String caption;

	@JsonProperty("parameterName")
	private final String parameterName;

	@JsonProperty("widgetType")
	private final JSONLayoutWidgetType widgetType;
	@JsonProperty("multiListValue")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Boolean multiListValue;

	@JsonProperty("range")
	private final boolean rangeParameter;

	@JsonProperty("defaultValue")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Object defaultValue;

	@JsonProperty("defaultValueTo")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Object defaultValueTo;

	@JsonProperty("mandatory")
	private final boolean mandatory;

	@JsonProperty("displayed")
	private final boolean displayed;

	@JsonProperty("readonly")
	private final boolean readonly;

	@ApiModelProperty(allowEmptyValue = false)
	@JsonProperty("type")
	private final JSONLayoutType type;

	@JsonProperty("showIncrementDecrementButtons")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean showIncrementDecrementButtons;

	@JsonProperty("barcodeScannerType")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final BarcodeScannerType barcodeScannerType;

	private JSONDocumentFilterParamDescriptor(final DocumentFilterParamDescriptor param, final JSONDocumentLayoutOptions options)
	{
		parameterName = param.getParameterName();

		if (options.isDebugShowColumnNamesForCaption())
		{
			caption = parameterName;
		}
		else
		{
			final String adLanguage = options.getAdLanguage();
			caption = param.getDisplayName(adLanguage);
		}

		if (param.getWidgetType() == DocumentFieldWidgetType.MultiValuesList)
		{
			// FIXME: workaround until frontend will implement the MultiValuesList widget type,
			widgetType = JSONLayoutWidgetType.List;
			multiListValue = Boolean.TRUE;
		}
		else
		{
			widgetType = JSONLayoutWidgetType.fromNullable(param.getWidgetType());
			multiListValue = null;
		}
		rangeParameter = param.isRange();

		defaultValue = Values.valueToJsonObject(param.getDefaultValueConverted(), options.getJsonOpts());
		defaultValueTo = Values.valueToJsonObject(param.getDefaultValueToConverted(), options.getJsonOpts());

		mandatory = param.isMandatory();
		displayed = true;
		readonly = false;

		type = toJSONLayoutType(widgetType);

		showIncrementDecrementButtons = param.isShowIncrementDecrementButtons() ? Boolean.TRUE : null;

		barcodeScannerType = param.getBarcodeScannerType();
	}

	private static JSONLayoutType toJSONLayoutType(final JSONLayoutWidgetType widgetType)
	{
		// Checkboxes
		// see https://github.com/metasfresh/metasfresh-webui-api/issues/352
		if (widgetType == JSONLayoutWidgetType.YesNo || widgetType == JSONLayoutWidgetType.Switch)
		{
			return JSONLayoutType.primaryLongLabels;
		}
		// Default "primary"
		// see https://github.com/metasfresh/metasfresh-webui-api/issues/334
		else
		{
			return JSONLayoutType.primary;
		}

	}
}
