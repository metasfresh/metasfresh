package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;
import de.metas.process.BarcodeScannerType;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.window.datatypes.MediaType;
import de.metas.ui.web.window.descriptor.ButtonFieldActionDescriptor;
import de.metas.ui.web.window.descriptor.ButtonFieldActionDescriptor.ButtonFieldActionType;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.descriptor.WidgetSize;
import de.metas.util.GuavaCollectors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

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
	public static List<JSONDocumentLayoutElement> ofList(
			@NonNull final List<DocumentLayoutElementDescriptor> elements,
			@NonNull final JSONDocumentLayoutOptions jsonOpts)
	{
		return elements.stream()
				.filter(jsonOpts.documentLayoutElementFilter())
				.map(element -> new JSONDocumentLayoutElement(element, jsonOpts))
				.filter(element -> !element.isEmpty()) // IMPORTANT: we shall avoid having elements without any field, see https://github.com/metasfresh/metasfresh-webui-frontend/issues/870#issuecomment-307770832
				.collect(GuavaCollectors.toImmutableList());
	}

	@Nullable
	static JSONDocumentLayoutElement fromNullable(
			@Nullable final DocumentLayoutElementDescriptor element,
			@NonNull final JSONDocumentLayoutOptions jsonOpts)
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

	@JsonProperty("multilineText")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Boolean multilineText;

	@JsonProperty("multilineTextLines")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer multilineTextLines;

	@JsonProperty("maxLength")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer maxLength;

	@JsonProperty("buttonProcessId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final ProcessId buttonProcessId;

	@JsonProperty("barcodeScannerType")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	final BarcodeScannerType barcodeScannerType;

	@ApiModelProperty(allowEmptyValue = true)
	@JsonProperty("type")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONLayoutType type;

	/**
	 * Widget size (see https://github.com/metasfresh/metasfresh-webui-api/issues/411).
	 */
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

	@JsonIgnore
	@Getter
	private final DetailId inlineTabId;

	@JsonProperty("inlineTab")
	@JsonInclude(Include.NON_EMPTY)
	private JSONDocumentLayoutTab inlineTab;

	private JSONDocumentLayoutElement(
			@NonNull final DocumentLayoutElementDescriptor element,
			@NonNull final JSONDocumentLayoutOptions options)
	{
		final String adLanguage = options.getAdLanguage();

		final String caption = element.getCaption(adLanguage);
		if (options.isDebugShowColumnNamesForCaption())
		{
			this.caption = element.getCaptionAsFieldNames();
		}
		else
		{
			this.caption = caption;
		}

		description = element.getDescription(adLanguage);

		widgetType = JSONLayoutWidgetType.fromNullable(element.getWidgetType());
		maxLength = element.getMaxLength() > 0 ? element.getMaxLength() : null;
		allowShowPassword = element.isAllowShowPassword() ? Boolean.TRUE : null;

		if (element.isMultilineText())
		{
			multilineText = Boolean.TRUE;
			multilineTextLines = element.getMultilineTextLines();
		}
		else
		{
			multilineText = null;
			multilineTextLines = null;
		}

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

		this.barcodeScannerType = element.getBarcodeScannerType();

		type = JSONLayoutType.fromNullable(element.getLayoutType());
		size = element.getWidgetSize();

		gridAlign = JSONLayoutAlign.fromNullable(element.getGridAlign());
		viewEditorRenderMode = element.getViewEditorRenderMode() != null ? element.getViewEditorRenderMode().toJson() : null;
		viewAllowSorting = element.isGridElement() ? element.isViewAllowSorting() : null;

		restrictToMediaTypes = ImmutableSet.copyOf(element.getRestrictToMediaTypes());

		fields = JSONDocumentLayoutElementField.ofSet(element.getFields(), options);

		inlineTabId = element.getInlineTabId();
	}

	/**
	 * Debugging field constructor
	 */
	private JSONDocumentLayoutElement(final String fieldName, final DocumentFieldWidgetType widgetType)
	{
		caption = fieldName;
		description = null;

		this.widgetType = JSONLayoutWidgetType.fromNullable(widgetType);
		allowShowPassword = null;
		multilineText = null;
		multilineTextLines = null;
		maxLength = null;
		buttonProcessId = null;
		barcodeScannerType = null;

		type = null;
		size = null;
		gridAlign = JSONLayoutAlign.right;
		viewEditorRenderMode = ViewEditorRenderMode.NEVER.toJson();
		viewAllowSorting = null;

		restrictToMediaTypes = null;

		fields = ImmutableSet.of(
				JSONDocumentLayoutElementField.builder()
						.field(fieldName)
						.emptyText("no " + fieldName)
						.supportZoomInto(widgetType.isSupportZoomInto())
						.build());

		inlineTabId = null;
	}

	private boolean isEmpty()
	{
		return fields.isEmpty() && inlineTabId == null;
	}

	public boolean hasField(@NonNull final String fieldName)
	{
		return fields.stream().anyMatch(field -> fieldName.equals(field.getField()));
	}

	public static boolean hasField(@NonNull final List<JSONDocumentLayoutElement> elements, @NonNull final String fieldName)
	{
		return elements.stream().anyMatch(element -> element.hasField(fieldName));
	}

	void setInlineTab(@NonNull final JSONDocumentLayoutTab inlineTab)
	{
		if (inlineTabId == null)
		{
			throw new AdempiereException("Setting inline tab not allowed if inlineTabId was not set before");
		}
		else if (!DetailId.equals(inlineTabId, inlineTab.getTabId()))
		{
			throw new AdempiereException("inlineTabId not matching: " + inlineTabId + ", " + inlineTab);
		}
		else
		{
			this.inlineTab = inlineTab;
		}
	}
}
