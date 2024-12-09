package de.metas.ui.web.view.json;

<<<<<<< HEAD
import java.util.List;

=======
import com.fasterxml.jackson.annotation.JsonAutoDetect;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
<<<<<<< HEAD

import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.util.GuavaCollectors;
=======
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import de.metas.util.GuavaCollectors;
import lombok.Getter;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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

/**
 * JSON representation of {@link DocumentQueryOrderBy}.
<<<<<<< HEAD
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
=======
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
@Builder
@Jacksonized
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE) // cannot use it because of "otherProperties"
@Getter
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
public class JSONViewOrderBy
{
	public static List<JSONViewOrderBy> ofList(final DocumentQueryOrderByList orderBys)
	{
		if (orderBys == null || orderBys.isEmpty())
		{
			return ImmutableList.of();
		}

<<<<<<< HEAD
		return orderBys
				.stream()
				.map(orderBy -> of(orderBy))
				.filter(jsonOrderBy -> jsonOrderBy != null)
				.collect(GuavaCollectors.toImmutableList());
	}

	private static JSONViewOrderBy of(final DocumentQueryOrderBy orderBy)
=======
		return orderBys.stream().map(JSONViewOrderBy::of).collect(ImmutableList.toImmutableList());
	}

	private static JSONViewOrderBy of(@NonNull final DocumentQueryOrderBy orderBy)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return new JSONViewOrderBy(orderBy.getFieldName(), orderBy.isAscending());
	}

<<<<<<< HEAD
	@JsonProperty("fieldName")
	private final String fieldName;
	@JsonProperty("ascending")
	private final boolean ascending;

	@JsonCreator
	public JSONViewOrderBy(
			@JsonProperty("fieldName") final String fieldName //
			, @JsonProperty("ascending") final boolean ascending //
	)
	{
		super();
=======
	public static DocumentQueryOrderByList toDocumentQueryOrderByList(@Nullable final List<JSONViewOrderBy> orderBys)
	{
		if (orderBys == null || orderBys.isEmpty())
		{
			return DocumentQueryOrderByList.EMPTY;
		}

		return orderBys.stream()
				.map(JSONViewOrderBy::toDocumentQueryOrderBy)
				.collect(DocumentQueryOrderByList.toDocumentQueryOrderByList());
	}

	@JsonProperty("fieldName")
	String fieldName;
	@JsonProperty("ascending")
	boolean ascending;

	@JsonCreator
	public JSONViewOrderBy(
			@JsonProperty("fieldName") final String fieldName,
			@JsonProperty("ascending") final boolean ascending
	)
	{
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
	public String getFieldName()
	{
		return fieldName;
	}

	public boolean isAscending()
	{
		return ascending;
=======
	public DocumentQueryOrderBy toDocumentQueryOrderBy()
	{
		return DocumentQueryOrderBy.builder()
				.fieldName(fieldName)
				.ascending(ascending)
				.build();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
