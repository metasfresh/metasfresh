package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

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

	private final List<DocumentLayoutColumnDescriptor> columns;

	private DocumentLayoutSectionDescriptor(final Builder builder)
	{
		super();
		columns = ImmutableList.copyOf(builder.buildColumns());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("columns", columns)
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
		private final List<DocumentLayoutColumnDescriptor.Builder> columnsBuilders = new ArrayList<>();

		private Builder()
		{
			super();
		}

		public DocumentLayoutSectionDescriptor build()
		{
			return new DocumentLayoutSectionDescriptor(this);
		}

		private List<DocumentLayoutColumnDescriptor> buildColumns()
		{
			return columnsBuilders
					.stream()
					.map(columnBuilder -> columnBuilder.build())
					.filter(column -> column.hasElementGroups())
					.collect(GuavaCollectors.toImmutableList());
		}

		public Builder addColumn(final DocumentLayoutColumnDescriptor.Builder columnBuilder)
		{
			Check.assumeNotNull(columnBuilder, "Parameter columnBuilder is not null");
			columnsBuilders.add(columnBuilder);
			return this;
		}

		public Stream<String> streamAllFieldNames()
		{
			return columnsBuilders
					.stream()
					.flatMap(columnBuilder -> columnBuilder.streamAllFieldNames());
		}
	}
}
