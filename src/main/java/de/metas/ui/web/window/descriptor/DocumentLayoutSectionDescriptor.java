package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

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

@SuppressWarnings("serial")
public final class DocumentLayoutSectionDescriptor implements Serializable
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String internalName;
	private final List<DocumentLayoutColumnDescriptor> columns;

	private DocumentLayoutSectionDescriptor(final Builder builder)
	{
		super();
		internalName = builder.internalName;
		columns = ImmutableList.copyOf(builder.buildColumns());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("internalName", internalName)
				.add("columns", columns.isEmpty() ? null : columns)
				.toString();
	}

	public List<DocumentLayoutColumnDescriptor> getColumns()
	{
		return columns;
	}

	public boolean hasColumns()
	{
		return !columns.isEmpty();
	}

	public static final class Builder
	{
		private static final Logger logger = LogManager.getLogger(DocumentLayoutSectionDescriptor.Builder.class);

		private String internalName;
		private final List<DocumentLayoutColumnDescriptor.Builder> columnsBuilders = new ArrayList<>();
		private String invalidReason;

		private Builder()
		{
			super();
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("internalName", internalName)
					.add("invalidReason", invalidReason)
					.add("columns-count", columnsBuilders.size())
					.toString();
		}

		public DocumentLayoutSectionDescriptor build()
		{
			if (isInvalid())
			{
				throw new IllegalStateException("Builder is invalid: " + getInvalidReason());
			}
			return new DocumentLayoutSectionDescriptor(this);
		}

		private List<DocumentLayoutColumnDescriptor> buildColumns()
		{
			return columnsBuilders
					.stream()
					.map(columnBuilder -> columnBuilder.build())
					.filter(column -> checkValid(column))
					.collect(GuavaCollectors.toImmutableList());
		}

		private boolean checkValid(final DocumentLayoutColumnDescriptor column)
		{
			if (!column.hasElementGroups())
			{
				logger.trace("Skip adding {} to {} because it does not have elements groups", column, this);
				return false;
			}

			return true;
		}

		public Builder setInternalName(final String internalName)
		{
			this.internalName = internalName;
			return this;
		}

		public Builder addColumn(final DocumentLayoutColumnDescriptor.Builder columnBuilder)
		{
			Check.assumeNotNull(columnBuilder, "Parameter columnBuilder is not null");
			columnsBuilders.add(columnBuilder);
			return this;
		}

		public DocumentLayoutElementDescriptor.Builder findElementBuilderByFieldName(final String fieldName)
		{
			for (final DocumentLayoutColumnDescriptor.Builder columnBuilder : columnsBuilders)
			{
				final DocumentLayoutElementDescriptor.Builder elementBuilder = columnBuilder.findElementBuilderByFieldName(fieldName);
				if (elementBuilder == null)
				{
					continue;
				}

				return elementBuilder;

			}
			return null;
		}

		public Builder setInvalid(final String invalidReason)
		{
			Check.assumeNotEmpty(invalidReason, "invalidReason is not empty");
			this.invalidReason = invalidReason;
			logger.trace("Layout section was marked as invalid: {}", this);
			return this;
		}

		public boolean isValid()
		{
			return invalidReason == null;
		}

		public boolean isInvalid()
		{
			return invalidReason != null;
		}

		private String getInvalidReason()
		{
			return invalidReason;
		}

		private Stream<DocumentLayoutElementDescriptor.Builder> streamElementBuilders()
		{
			return columnsBuilders.stream()
					.flatMap(columnsBuilder -> columnsBuilder.streamElementBuilders());
		}

		public Builder setExcludeSpecialFields()
		{
			streamElementBuilders()
					.forEach(elementBuilder -> elementBuilder.setExcludeSpecialFields());
			return this;
		}
	}
}
