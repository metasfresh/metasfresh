package de.metas.ui.web.window.datatypes.json;

import java.util.List;
import java.util.Set;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.devices.JSONDeviceDescriptor;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.window.datatypes.MediaType;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutElementField.JSONFieldType;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutElementField.JSONLookupSource;
import de.metas.ui.web.window.descriptor.ButtonFieldActionDescriptor;
import de.metas.ui.web.window.descriptor.ButtonFieldActionDescriptor.ButtonFieldActionType;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.descriptor.WidgetSize;
import io.swagger.annotations.ApiModel;
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

@ApiModel("element")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@ToString
public final class JSONDocumentLayoutElement
{
	public static List<JSONDocumentLayoutElement> ofList(final List<DocumentLayoutElementDescriptor> elements, final JSONOptions jsonOpts)
	{
		return elements.stream()
				.filter(jsonOpts.documentLayoutElementFilter())
				.map(element -> new JSONDocumentLayoutElement(element, jsonOpts))
				.filter(JSONDocumentLayoutElement::hasFields) // IMPORTANT: we shall avoid having elements without any field, see https://github.com/metasfresh/metasfresh-webui-frontend/issues/870#issuecomment-307770832
				.collect(GuavaCollectors.toImmutableList());
	}

	static JSONDocumentLayoutElement fromNullable(final DocumentLayoutElementDescriptor element, final JSONOptions jsonOpts)
	{
		if (element == null)
		{
			return null;
		}
		return new JSONDocumentLayoutElement(element, jsonOpts);
	}

	public static JSONDocumentLayoutElement debuggingField(final String fieldName, final DocumentFieldWidgetType widgetType)
	{
		return new JSONDocumentLayoutElement(fieldName, widgetType);
	}

	@JsonProperty("caption")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String caption;

	@JsonProperty("description")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String description;

	@JsonProperty("widgetType")
	private final JSONLayoutWidgetType widgetType;

	@JsonProperty("allowShowPassword")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Boolean allowShowPassword; // in case widgetType is Password

	@JsonProperty("buttonProcessId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final ProcessId buttonProcessId;

	/** Type: primary, secondary */
	@JsonProperty("type")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONLayoutType type;

	/** Widget size (see https://github.com/metasfresh/metasfresh-webui-api/issues/411). */
	@JsonProperty("size")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final WidgetSize size;

	@JsonProperty("gridAlign")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONLayoutAlign gridAlign;

	@JsonProperty("viewEditorRenderMode")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String viewEditorRenderMode;
	@JsonProperty("sortable")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Boolean viewAllowSorting;
	
	@JsonProperty("restrictToMediaTypes")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Set<MediaType> restrictToMediaTypes;

	@JsonProperty("fields")
	@JsonInclude(Include.NON_EMPTY)
	private final Set<JSONDocumentLayoutElementField> fields;

	private JSONDocumentLayoutElement(final DocumentLayoutElementDescriptor element, final JSONOptions jsonOpts)
	{
		final String adLanguage = jsonOpts.getAD_Language();

		final String caption = element.getCaption(adLanguage);
		if (jsonOpts.isDebugShowColumnNamesForCaption())
		{
			this.caption = element.getCaptionAsFieldNames();
		}
		else
		{
			this.caption = caption;
		}

		description = element.getDescription(adLanguage);

		widgetType = JSONLayoutWidgetType.fromNullable(element.getWidgetType());
		allowShowPassword = element.isAllowShowPassword() ? Boolean.TRUE : null;

		final ButtonFieldActionDescriptor buttonAction = element.getButtonActionDescriptor();
		final ButtonFieldActionType buttonActionType = buttonAction == null ? null : buttonAction.getActionType();
		if (buttonActionType == ButtonFieldActionType.processCall)
		{
			buttonProcessId = buttonAction.getProcessId();
		}
		else
		{
			buttonProcessId = null;
		}

		type = JSONLayoutType.fromNullable(element.getLayoutType());
		size = element.getWidgetSize();

		gridAlign = JSONLayoutAlign.fromNullable(element.getGridAlign());
		viewEditorRenderMode = element.getViewEditorRenderMode() != null ? element.getViewEditorRenderMode().toJson() : null;
		viewAllowSorting = element.isGridElement() ? element.isViewAllowSorting() : null;
		
		restrictToMediaTypes = ImmutableSet.copyOf(element.getRestrictToMediaTypes());

		fields = JSONDocumentLayoutElementField.ofSet(element.getFields(), jsonOpts);
	}

	/** Debugging field constructor */
	private JSONDocumentLayoutElement(final String fieldName, final DocumentFieldWidgetType widgetType)
	{
		caption = fieldName;
		description = null;

		this.widgetType = JSONLayoutWidgetType.fromNullable(widgetType);
		allowShowPassword = null;
		buttonProcessId = null;

		type = null;
		size = null;
		gridAlign = JSONLayoutAlign.right;
		viewEditorRenderMode = ViewEditorRenderMode.NEVER.toJson();
		viewAllowSorting = null;
		
		restrictToMediaTypes = null;

		fields = ImmutableSet.of(new JSONDocumentLayoutElementField( //
				fieldName, (JSONFieldType)null // type
				, (JSONLookupSource)null // lookupSource
				, "no " + fieldName // emptyText
				, (List<JSONDeviceDescriptor>)null // devices
				, (String)null // newRecordWindowId
				, (String)null // newRecordCaption
				, widgetType.isSupportZoomInto() // supportZoomInfo
		));
	}

	private boolean hasFields()
	{
		return !fields.isEmpty();
	}
}
