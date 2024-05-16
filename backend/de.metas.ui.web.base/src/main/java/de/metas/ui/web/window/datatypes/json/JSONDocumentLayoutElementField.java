package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.FieldType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.factory.AdvancedSearchDescriptorsProvider;
import de.metas.ui.web.window.descriptor.factory.NewRecordDescriptorsProvider;
import de.metas.util.GuavaCollectors;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
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
	 * <p>
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

		/**
		 * See ActionButtonStatus.
		 */
		ActionButton,

		/**
		 * Used to tell the frontend that a field which is part of a composition shall be shown as tooltip,
		 * when the respective tooltip icon/button is pressed
		 */
		Tooltip;

		@Nullable
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

		private static final Map<FieldType, JSONFieldType> fieldType2json = ImmutableMap.<FieldType, JSONFieldType>builder()
				.put(FieldType.ActionButtonStatus, JSONFieldType.ActionButtonStatus)
				.put(FieldType.ActionButton, JSONFieldType.ActionButton)
				.put(FieldType.Tooltip, JSONFieldType.Tooltip)
				.build();

	}

	/**
	 * If one {@link DocumentLayoutElementDescriptor} has multiple fields,
	 * then this tells the frontend how to render each particular "sub-widget".
	 * <p>
	 * Please keep in sync with {@link LookupSource}.
	 */
	@ApiModel("lookup-source")
	public enum JSONLookupSource
	{
		lookup, list,

		/**
		 * This one is used for fields that are tooltips. Also see {@link FieldType#Tooltip}.
		 */
		text;

		@Nullable
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

		private static final Map<LookupSource, JSONLookupSource> lookupSource2json = ImmutableMap.<LookupSource, JSONLookupSource>builder()
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

	/**
	 * Text to be displayed when the field is empty
	 */
	@JsonProperty("emptyText")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String emptyText;

	/**
	 * Null Item's caption
	 */
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

	@JsonProperty("advSearchWindowId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Nullable
	private String advSearchWindowId;

	@JsonProperty("advSearchCaption")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Nullable
	private String advSearchCaption;

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

	@JsonIgnore
	@Nullable
	private final String lookupTableName;

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
			lookupTableName = fieldDescriptor.getLookupTableName().orElse(null);
		}
		else
		{
			lookupSearchStringMinLength = null;
			lookupSearchStartDelayMillis = null;
			lookupTableName = null;
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
			@Nullable @JsonProperty("advSearchWindowId") final String advSearchWindowId,
			@Nullable @JsonProperty("advSearchCaption") final String advSearchCaption,
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
		this.advSearchWindowId = advSearchWindowId;
		this.advSearchCaption = advSearchCaption;

		//
		// Lookup
		this.source = source;
		this.lookupSearchStringMinLength = lookupSearchStringMinLength;
		this.lookupSearchStartDelayMillis = lookupSearchStartDelayMillis;
		this.lookupTableName = null;

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
				.add("advSearchWindowId", advSearchWindowId)
				.add("supportZoomInto", supportZoomInto)
				.toString();
	}

	@Nullable
	private static DocumentEntityDescriptor findNewRecordEntityDescriptor(
			@Nullable final String lookupTableName,
			final JSONDocumentLayoutOptions jsonOpts)
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

	void setAdvSearchWindow(
			final @NonNull WindowId windowId,
			final @Nullable DetailId tabId,
			final @NonNull JSONDocumentLayoutOptions jsonOpts)
	{
		if (lookupTableName == null)
		{
			return;
		}

		// avoid enabling advanced search assistant for included tabs,
		// because atm frontend does not support it.
		if (tabId != null)
		{
			return;
		}

		final AdvancedSearchDescriptorsProvider provider = jsonOpts.getAdvancedSearchDescriptorsProvider();
		if (provider == null)
		{
			return;
		}

		final DocumentEntityDescriptor advancedSearchEntityDescriptor = provider.getAdvancedSearchDescriptorIfAvailable(lookupTableName);
		if (advancedSearchEntityDescriptor != null)
		{
			advSearchWindowId = advancedSearchEntityDescriptor.getDocumentTypeId().toJson();
			advSearchCaption = advancedSearchEntityDescriptor.getCaption().translate(jsonOpts.getAdLanguage());
		}
		else
		{
			advSearchWindowId = null;
			advSearchCaption = null;
		}
	}
}
