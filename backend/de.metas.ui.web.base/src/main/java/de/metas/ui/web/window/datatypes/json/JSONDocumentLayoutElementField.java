package de.metas.ui.web.window.datatypes.json;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.devices.JSONDeviceDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.FieldType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.factory.NewRecordDescriptorsProvider;
import de.metas.util.GuavaCollectors;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

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

@ApiModel("field")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class JSONDocumentLayoutElementField
{
	public static Set<JSONDocumentLayoutElementField> ofSet(
			final Set<DocumentLayoutElementFieldDescriptor> fieldDescriptors,
			final JSONDocumentLayoutOptions options)
	{
		return fieldDescriptors.stream()
				.map(fieldDescriptor -> of(fieldDescriptor, options))
				.collect(GuavaCollectors.toImmutableSet());
	}

	private static JSONDocumentLayoutElementField of(final DocumentLayoutElementFieldDescriptor fieldDescriptor, final JSONDocumentLayoutOptions jsonOpts)
	{
		return new JSONDocumentLayoutElementField(fieldDescriptor, jsonOpts);
	}

	/**
	 * Optional property used to address special cases. Most fields don't need it.
	 *
	 * Please keep in sync with {@link FieldType}
	 */
	@ApiModel("field-type")
	public enum JSONFieldType
	{
		/**
		 * For the docstatus/docaction widget, the two fields @{@code DocStatus} and {@code DocAction} are mashed together.
		 * This value and {@link #ActionButton} is used to specify which field is which.
		 */
		ActionButtonStatus,

		/** See {@value #ActionButtonStatus}. */
		ActionButton,

		/**
		 * Used to tell the frontend that a field which is part of a composition shall be shown as tooltip,
		 * when the respective tooltip icon/button is pressed
		 */
		Tooltip;

		public static JSONFieldType fromNullable(final FieldType fieldType)
		{
			if (fieldType == null)
			{
				return null;
			}
			final JSONFieldType jsonFieldType = fieldType2json.get(fieldType);
			if (jsonFieldType == null)
			{
				throw new IllegalArgumentException("Cannot convert " + fieldType + " to " + JSONFieldType.class);
			}
			return jsonFieldType;
		}

		private static final Map<FieldType, JSONFieldType> fieldType2json = ImmutableMap.<FieldType, JSONFieldType> builder()
				.put(FieldType.ActionButtonStatus, JSONFieldType.ActionButtonStatus)
				.put(FieldType.ActionButton, JSONFieldType.ActionButton)
				.put(FieldType.Tooltip, JSONFieldType.Tooltip)
				.build();

	}

	/**
	 * If one {@link DocumentLayoutElementDescriptor} has multiple fields,
	 * then this tells the frontend how to render each particular "sub-widget".
	 *
	 * Please keep in sync with {@link LookupSource}.
	 */
	@ApiModel("lookup-source")
	public enum JSONLookupSource
	{
		lookup, list,

		/** This one is used for fields that are tooltips. Also see {@link FieldType#Tooltip}. */
		text;

		public static JSONLookupSource fromNullable(@Nullable final LookupSource lookupSource)
		{
			if (lookupSource == null)
			{
				return null;
			}
			final JSONLookupSource jsonLookupSource = lookupSource2json.get(lookupSource);
			if (jsonLookupSource == null)
			{
				throw new IllegalArgumentException("Cannot convert " + lookupSource + " to " + JSONLookupSource.class);
			}
			return jsonLookupSource;
		}

		private static final Map<LookupSource, JSONLookupSource> lookupSource2json = ImmutableMap.<LookupSource, JSONLookupSource> builder()
				.put(LookupSource.list, list)
				.put(LookupSource.lookup, lookup)
				.put(LookupSource.text, text)
				.build();
	}

	@JsonProperty(value = "field", required = true)
	@Getter
	private final String field;

	@JsonProperty(value = "caption", required = true)
	private final String caption;

	@JsonProperty("type")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONFieldType type;

	@JsonProperty("tooltipIconName")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String tooltipIconName;

	/** Text to be displayed when the field is empty */
	@JsonProperty("emptyText")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String emptyText;

	/** Null Item's caption */
	@JsonProperty("clearValueText")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String clearValueText;

	@JsonProperty("devices")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDeviceDescriptor> devices;

	@JsonProperty("newRecordWindowId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String newRecordWindowId;

	@JsonProperty("newRecordCaption")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String newRecordCaption;

	//
	// Lookup
	@JsonProperty("source")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONLookupSource source;
	//
	@JsonProperty("lookupSearchStringMinLength")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer lookupSearchStringMinLength;
	//
	@JsonProperty("lookupSearchStartDelayMillis")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer lookupSearchStartDelayMillis;

	//
	// Zoom
	@JsonProperty("supportZoomInto")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Boolean supportZoomInto;

	private JSONDocumentLayoutElementField(
			@NonNull final DocumentLayoutElementFieldDescriptor fieldDescriptor,
			@NonNull final JSONDocumentLayoutOptions jsonOpts)
	{
		field = fieldDescriptor.getField();
		caption = fieldDescriptor.getCaption().translate(jsonOpts.getAdLanguage());
		type = JSONFieldType.fromNullable(fieldDescriptor.getFieldType());
		tooltipIconName = fieldDescriptor.getTooltipIconName();
		emptyText = fieldDescriptor.getEmptyFieldText(jsonOpts.getAdLanguage());
		clearValueText = fieldDescriptor.getListNullItemCaption(jsonOpts.getAdLanguage());
		devices = JSONDeviceDescriptor.ofList(fieldDescriptor.getDevices(), jsonOpts.getAdLanguage());

		final DocumentEntityDescriptor newRecordEntityDescriptor = findNewRecordEntityDescriptor(fieldDescriptor.getLookupTableName().orElse(null), jsonOpts);
		if (newRecordEntityDescriptor != null)
		{
			newRecordWindowId = newRecordEntityDescriptor.getDocumentTypeId().toJson();
			newRecordCaption = newRecordEntityDescriptor.getCaption().translate(jsonOpts.getAdLanguage());
		}
		else
		{
			newRecordWindowId = null;
			newRecordCaption = null;
		}

		//
		// Lookup
		source = JSONLookupSource.fromNullable(fieldDescriptor.getLookupSource());
		if (source != null)
		{
			lookupSearchStringMinLength = fieldDescriptor.getLookupSearchStringMinLength();
			lookupSearchStartDelayMillis = (int)fieldDescriptor.getLookupSearchStartDelay()
					.orElseGet(jsonOpts::getDefaultLookupSearchStartDelay)
					.toMillis();
		}
		else
		{
			lookupSearchStringMinLength = null;
			lookupSearchStartDelayMillis = null;
		}

		supportZoomInto = fieldDescriptor.isSupportZoomInto() ? Boolean.TRUE : null;
	}

	@JsonCreator
	@Builder
	private JSONDocumentLayoutElementField(
			@JsonProperty("field") final String field,
			@JsonProperty("caption") final String caption,
			@JsonProperty("type") final JSONFieldType type,
			@JsonProperty("tooltipIconName") final String tooltipIconName,
			@JsonProperty("emptyText") final String emptyText,
			@JsonProperty("clearValueText") final String clearValueText,
			@JsonProperty("devices") final List<JSONDeviceDescriptor> devices,
			@JsonProperty("newRecordWindowId") final String newRecordWindowId,
			@JsonProperty("newRecordCaption") final String newRecordCaption,
			//
			@JsonProperty("source") final JSONLookupSource source,
			@JsonProperty("lookupSearchStringMinLength") final Integer lookupSearchStringMinLength,
			@JsonProperty("lookupSearchStartDelayMillis") final Integer lookupSearchStartDelayMillis,
			//
			@JsonProperty("supportZoomInto") final boolean supportZoomInto)
	{
		this.field = field;
		this.caption = caption;
		this.type = type;
		this.tooltipIconName = tooltipIconName;
		this.emptyText = emptyText;
		this.clearValueText = clearValueText;
		this.devices = devices == null ? ImmutableList.of() : ImmutableList.copyOf(devices);

		this.newRecordWindowId = newRecordWindowId;
		this.newRecordCaption = newRecordCaption;

		//
		// Lookup
		this.source = source;
		this.lookupSearchStringMinLength = lookupSearchStringMinLength;
		this.lookupSearchStartDelayMillis = lookupSearchStartDelayMillis;

		this.supportZoomInto = supportZoomInto;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("field", field)
				.add("type", type)
				.add("source", source)
				.add("emptyText", emptyText)
				.add("clearValueText", clearValueText)
				.add("actions", devices.isEmpty() ? null : devices)
				.add("newRecordWindowId", newRecordWindowId)
				.add("supportZoomInto", supportZoomInto)
				.toString();
	}

	private static DocumentEntityDescriptor findNewRecordEntityDescriptor(final String lookupTableName, final JSONDocumentLayoutOptions jsonOpts)
	{
		if (lookupTableName == null)
		{
			return null;
		}

		final NewRecordDescriptorsProvider newRecordDescriptorsProvider = jsonOpts.getNewRecordDescriptorsProvider();
		if (newRecordDescriptorsProvider == null)
		{
			return null;
		}

		return newRecordDescriptorsProvider.getNewRecordEntityDescriptorIfAvailable(lookupTableName);
	}
}
