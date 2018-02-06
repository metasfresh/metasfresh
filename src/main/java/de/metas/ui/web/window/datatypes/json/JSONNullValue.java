package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
@SuppressWarnings("serial")
public final class JSONNullValue implements Serializable
{
	public static final Object wrapIfNull(final Object value)
	{
		return value == null ? instance : value;
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

	public static Object toNullIfInstance(@Nullable final Object jsonValueObj)
	{
		if (jsonValueObj instanceof JSONNullValue)
		{
			return null;
		}
		return jsonValueObj;
	}
}
