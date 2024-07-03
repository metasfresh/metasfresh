package de.metas.ui.web.view.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.collect.ImmutableList;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

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
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
@Builder
@Jacksonized
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE) // cannot use it because of "otherProperties"
public class JSONViewOrderBy
{
	@NonNull String fieldName;
	boolean ascending;

	public static List<JSONViewOrderBy> ofList(final DocumentQueryOrderByList orderBys)
	{
		if (orderBys == null || orderBys.isEmpty())
		{
			return ImmutableList.of();
		}

		return orderBys.stream().map(JSONViewOrderBy::of).collect(ImmutableList.toImmutableList());
	}

	private static JSONViewOrderBy of(@NonNull final DocumentQueryOrderBy orderBy)
	{
		return new JSONViewOrderBy(orderBy.getFieldName(), orderBy.isAscending());
	}
}
