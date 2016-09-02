package de.metas.ui.web.window.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;

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

public class DocumentSideListView implements IDocumentSideListView
{
	public static final Builder builder(DocumentEntityDescriptor entityDescriptor)
	{
		return new Builder(entityDescriptor);
	}

	private final DocumentPath documentPath;
	private final String idFieldName;
	private final int id;
	private final Map<String, Object> values;

	private DocumentSideListView(final Builder builder)
	{
		super();
		documentPath = builder.getDocumentPath();

		idFieldName = builder.idFieldName;
		id = builder.id;
		values = ImmutableMap.copyOf(builder.values);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("values", values)
				.toString();
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	@Override
	public String getIdFieldNameOrNull()
	{
		return idFieldName;
	}

	@Override
	public int getDocumentId()
	{
		return id;
	}

	@Override
	public Set<String> getFieldNames()
	{
		return values.keySet();
	}

	@Override
	public Object getFieldValue(final String fieldName)
	{
		return values.get(fieldName);
	}

	@Override
	public Map<String, Object> getFieldNameAndJsonValues()
	{
		return values;
	}

	public static final class Builder
	{
		private final DocumentEntityDescriptor entityDescriptor;
		private String idFieldName;
		private int id;
		private final Map<String, Object> values = new LinkedHashMap<>();

		private Builder(DocumentEntityDescriptor entityDescriptor)
		{
			super();
			this.entityDescriptor = entityDescriptor;
		}

		public DocumentSideListView build()
		{
			return new DocumentSideListView(this);
		}
		
		public DocumentPath getDocumentPath()
		{
			return DocumentPath.rootDocumentPath(entityDescriptor.getAD_Window_ID(), id);
		}
		
		public Builder putKeyFieldValue(final String fieldName, final Object jsonValue)
		{
			putFieldValue(fieldName, jsonValue);

			idFieldName = fieldName;
			Preconditions.checkNotNull(jsonValue, "id");

			if (jsonValue instanceof Integer)
			{
				id = (Integer)jsonValue;
			}
			else
			{
				throw new IllegalArgumentException("Cannot convert id '" + jsonValue + "' to integer");
			}
			
			return this;
		}

		public void putFieldValue(final String fieldName, final Object jsonValue)
		{
			if (jsonValue == null)
			{
				values.remove(fieldName);
			}
			else
			{
				values.put(fieldName, jsonValue);
			}
		}
	}
}
