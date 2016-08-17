package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
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
	public static Set<JSONDocumentLayoutElementField> ofSet(final Set<DocumentLayoutElementFieldDescriptor> fieldDescriptors)
	{
		return fieldDescriptors.stream()
				.map(field -> of(field))
				.collect(GuavaCollectors.toImmutableSet());
	}

	public static JSONDocumentLayoutElementField of(final DocumentLayoutElementFieldDescriptor fieldDescriptor)
	{
		return new JSONDocumentLayoutElementField(fieldDescriptor);
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

	private final String field;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONLookupSource source;

	private JSONDocumentLayoutElementField(final DocumentLayoutElementFieldDescriptor fieldDescriptor)
	{
		super();
		field = fieldDescriptor.getField();
		source = JSONLookupSource.fromNullable(fieldDescriptor.getLookupSource());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("field", field)
				.toString();
	}

	public String getField()
	{
		return field;
	}

	public JSONLookupSource getSource()
	{
		return source;
	}
}
