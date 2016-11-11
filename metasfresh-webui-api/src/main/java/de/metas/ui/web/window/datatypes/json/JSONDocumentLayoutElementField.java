package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.FieldType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
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

@ApiModel("field")
@SuppressWarnings("serial")
public final class JSONDocumentLayoutElementField implements Serializable
{
	public static Set<JSONDocumentLayoutElementField> ofSet(final Set<DocumentLayoutElementFieldDescriptor> fieldDescriptors, final JSONOptions jsonOpts)
	{
		return fieldDescriptors.stream()
				.map(fieldDescriptor -> of(fieldDescriptor, jsonOpts))
				.collect(GuavaCollectors.toImmutableSet());
	}

	private static JSONDocumentLayoutElementField of(final DocumentLayoutElementFieldDescriptor fieldDescriptor, final JSONOptions jsonOpts)
	{
		return new JSONDocumentLayoutElementField(fieldDescriptor, jsonOpts);
	}

	@ApiModel("field-type")
	public static enum JSONFieldType
	{
		ActionButtonStatus, ActionButton;

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
				.build();

	}

	@ApiModel("lookup-source")
	public static enum JSONLookupSource
	{
		lookup, list;

		public static JSONLookupSource fromNullable(final LookupSource lookupSource)
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
				.build();
	}

	@JsonProperty(value = "field", required = true)
	private final String field;

	@JsonProperty("type")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONFieldType type;

	@JsonProperty("source")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONLookupSource source;

	@JsonProperty("emptyText")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String emptyText;

	private JSONDocumentLayoutElementField(final DocumentLayoutElementFieldDescriptor fieldDescriptor, final JSONOptions jsonOpts)
	{
		super();
		field = fieldDescriptor.getField();
		type = JSONFieldType.fromNullable(fieldDescriptor.getFieldType());
		source = JSONLookupSource.fromNullable(fieldDescriptor.getLookupSource());
		emptyText = fieldDescriptor.getEmptyText(jsonOpts.getAD_Language());
	}

	@JsonCreator
	private JSONDocumentLayoutElementField(
			@JsonProperty("field") final String field //
			, @JsonProperty("type") final JSONFieldType type //
			, @JsonProperty("source") final JSONLookupSource source //
			, @JsonProperty("emptyText") final String emptyText //
	)
	{
		super();
		this.field = field;
		this.type = type;
		this.source = source;
		this.emptyText = emptyText;
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
				.toString();
	}

	public String getField()
	{
		return field;
	}

	public JSONFieldType getType()
	{
		return type;
	}

	public JSONLookupSource getSource()
	{
		return source;
	}

	public String getEmptyText()
	{
		return emptyText;
	}
}
