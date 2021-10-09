package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import org.compiere.model.Null;

import javax.annotation.Nullable;

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
 * JSON null marker.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ApiModel("null-value")
@JsonSerialize(using = JSONNullValueSerializer.class)
public final class JSONNullValue
{
	public static Object wrapIfNull(@Nullable final Object value)
	{
		return value != null && !isNull(value) ? value : instance;
	}

	public static boolean isNull(@Nullable final Object value)
	{
		return value == null || value instanceof JSONNullValue || value instanceof Null;
	}

	public static final transient JSONNullValue instance = new JSONNullValue();

	private JSONNullValue()
	{
	}

	@Override
	public String toString()
	{
		return "null";
	}

	@Nullable
	public static Object toNullIfInstance(@Nullable final Object jsonValueObj)
	{
		return !isNull(jsonValueObj) ? jsonValueObj : null;
	}
}
