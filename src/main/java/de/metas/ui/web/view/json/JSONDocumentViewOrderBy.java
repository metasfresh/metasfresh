package de.metas.ui.web.view.json;

import java.util.List;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.model.DocumentQueryOrderBy;

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

/**
 * JSON representation of {@link DocumentQueryOrderBy}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class JSONDocumentViewOrderBy
{
	public static final List<DocumentQueryOrderBy> unwrapList(final List<JSONDocumentViewOrderBy> jsonOrderBys)
	{
		if (jsonOrderBys == null || jsonOrderBys.isEmpty())
		{
			return ImmutableList.of();
		}

		return jsonOrderBys
				.stream()
				.map(jsonOrderBy -> unwrap(jsonOrderBy))
				.filter(orderBy -> orderBy != null)
				.collect(GuavaCollectors.toImmutableList());
	}

	private static final DocumentQueryOrderBy unwrap(final JSONDocumentViewOrderBy jsonOrderBy)
	{
		return DocumentQueryOrderBy.byFieldName(jsonOrderBy.getFieldName(), jsonOrderBy.isAscending());
	}

	public static List<JSONDocumentViewOrderBy> ofList(final List<DocumentQueryOrderBy> orderBys)
	{
		if (orderBys == null || orderBys.isEmpty())
		{
			return ImmutableList.of();
		}

		return orderBys
				.stream()
				.map(orderBy -> of(orderBy))
				.filter(jsonOrderBy -> jsonOrderBy != null)
				.collect(GuavaCollectors.toImmutableList());
	}

	private static JSONDocumentViewOrderBy of(final DocumentQueryOrderBy orderBy)
	{
		return new JSONDocumentViewOrderBy(orderBy.getFieldName(), orderBy.isAscending());
	}

	@JsonProperty("fieldName")
	private final String fieldName;
	@JsonProperty("ascending")
	private final boolean ascending;

	@JsonCreator
	public JSONDocumentViewOrderBy(
			@JsonProperty("fieldName") final String fieldName //
			, @JsonProperty("ascending") final boolean ascending //
	)
	{
		super();
		this.fieldName = fieldName;
		this.ascending = ascending;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("fieldName", fieldName)
				.add("asc", ascending)
				.toString();
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public boolean isAscending()
	{
		return ascending;
	}
}
