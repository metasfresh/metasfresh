package de.metas.ui.web.document.filter.sql;

import com.google.common.collect.ImmutableMap;
import de.metas.security.UserRolePermissionsKey;
import de.metas.ui.web.view.ViewId;
import de.metas.util.GuavaCollectors;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class SqlDocumentFilterConverterContext
{
	@Nullable ViewId viewId;
	@Nullable UserRolePermissionsKey userRolePermissionsKey;
	@NonNull ImmutableMap<String, Object> parameters;
	boolean queryIfNoFilters;

	@Builder
	private SqlDocumentFilterConverterContext(
			@Nullable final ViewId viewId,
			@Nullable final UserRolePermissionsKey userRolePermissionsKey,
			@NonNull @Singular final Map<String, Object> parameters,
			@Nullable final Boolean queryIfNoFilters)
	{
		this.viewId = viewId;
		this.userRolePermissionsKey = userRolePermissionsKey;
		this.parameters = toImmutableMap(parameters);
		this.queryIfNoFilters = queryIfNoFilters != null ? queryIfNoFilters : true;
	}

	private static ImmutableMap<String, Object> toImmutableMap(final Map<String, Object> map)
	{
		if (map instanceof ImmutableMap)
		{
			return (ImmutableMap<String, Object>)map;
		}
		else
		{
			return map.entrySet()
					.stream()
					.filter(entry -> entry.getKey() != null && entry.getValue() != null)
					.collect(GuavaCollectors.toImmutableMap());
		}
	}

	public SqlDocumentFilterConverterContext withViewId(@Nullable final ViewId viewId)
	{
		return !ViewId.equals(this.viewId, viewId)
				? new SqlDocumentFilterConverterContext(viewId, this.userRolePermissionsKey, this.parameters, this.queryIfNoFilters)
				: this;
	}

	public SqlDocumentFilterConverterContext withUserRolePermissionsKey(final UserRolePermissionsKey userRolePermissionsKey)
	{
		return !Objects.equals(this.userRolePermissionsKey, userRolePermissionsKey)
				? new SqlDocumentFilterConverterContext(this.viewId, userRolePermissionsKey, this.parameters, this.queryIfNoFilters)
				: this;
	}

	public int getPropertyAsInt(@NonNull final String name, final int defaultValue)
	{
		final ImmutableMap<String, Object> params = getParameters();
		final Object value = params.get(name);
		return NumberUtils.asInt(value, defaultValue);
	}
}
