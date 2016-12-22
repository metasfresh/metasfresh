package de.metas.ui.web.view;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;

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

public final class DocumentView implements IDocumentView
{
	public static final Builder builder(final int adWindowId)
	{
		return new Builder(adWindowId);
	}

	private final DocumentPath documentPath;
	private final String idFieldName;
	private final int documentId;
	private final Map<String, Object> values;

	private final IDocumentViewAttributesProvider attributesProvider;

	private final List<DocumentView> includedDocuments;

	private DocumentView(final Builder builder)
	{
		super();
		documentPath = builder.getDocumentPath();

		idFieldName = builder.idFieldName;
		documentId = builder.documentId;
		values = ImmutableMap.copyOf(builder.values);

		attributesProvider = builder.getAttributesProviderOrNull();

		includedDocuments = builder.buildIncludedDocuments();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("id", documentId)
				.add("values", values)
				.add("attributesProvider", attributesProvider)
				.add("includedDocuments.count", includedDocuments.size())
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
		return documentId;
	}

	@Override
	public Set<String> getFieldNames()
	{
		return values.keySet();
	}

	@Override
	public Object getFieldValueAsJson(final String fieldName)
	{
		return values.get(fieldName);
	}

	@Override
	public Map<String, Object> getFieldNameAndJsonValues()
	{
		return values;
	}

	@Override
	public IDocumentViewAttributes getAttributes()
	{
		final IDocumentViewAttributes attributes = attributesProvider == null ? null : attributesProvider.getAttributes(documentId);
		if (attributes == null)
		{
			throw new IllegalStateException("Document does not support attributes");
		}
		return attributes;
	}

	@Override
	public List<DocumentView> getIncludedDocuments()
	{
		return includedDocuments;
	}

	public static final class Builder
	{
		private final int adWindowId;
		private String idFieldName;
		private int documentId;
		private final Map<String, Object> values = new LinkedHashMap<>();
		private IDocumentViewAttributesProvider attributesProvider;
		private final List<DocumentView.Builder> includedDocuments = null;

		private Builder(final int adWindowId)
		{
			super();
			Check.assume(adWindowId > 0, "adWindowId > 0 but it was {}", adWindowId);
			this.adWindowId = adWindowId;
		}

		public DocumentView build()
		{
			documentId = findId();
			return new DocumentView(this);
		}

		private int findId()
		{
			if (idFieldName == null)
			{
				throw new IllegalStateException("No idFieldName was specified");
			}

			final Object idJson = values.get(idFieldName);
			Preconditions.checkNotNull(idJson, "id");

			if (idJson instanceof Integer)
			{
				return (Integer)idJson;
			}
			else
			{
				throw new IllegalArgumentException("Cannot convert id '" + idJson + "' to integer");
			}

		}

		public DocumentPath getDocumentPath()
		{
			return DocumentPath.rootDocumentPath(DocumentType.Window, adWindowId, documentId);
		}

		public Builder setIdFieldName(final String idFieldName)
		{
			this.idFieldName = idFieldName;
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

		private IDocumentViewAttributesProvider getAttributesProviderOrNull()
		{
			return attributesProvider;
		}

		public Builder setAttributesProvider(@Nullable final IDocumentViewAttributesProvider attributesProvider)
		{
			this.attributesProvider = attributesProvider;
			return this;
		}

		private List<DocumentView> buildIncludedDocuments()
		{
			if (includedDocuments == null || includedDocuments.isEmpty())
			{
				return ImmutableList.of();
			}

			return includedDocuments
					.stream()
					.map(builder -> builder.build())
					.collect(GuavaCollectors.toImmutableList());
		}

	}
}
