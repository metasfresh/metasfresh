package de.metas.ui.web.dashboard;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class KPIDataSetValue
{
	public static final Builder builder()
	{
		return new Builder();
	}

	@JsonIgnore
	private final Map<String, Object> map;

	private KPIDataSetValue(final Builder builder)
	{
		map = builder.mapBuilder.build();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(map)
				.toString();
	}

	@JsonAnyGetter
	private Map<String, Object> getMap()
	{
		return map;
	}

	public static class Builder
	{
		private final ImmutableMap.Builder<String, Object> mapBuilder = ImmutableMap.builder();

		private Builder()
		{
		}

		public KPIDataSetValue build()
		{
			return new KPIDataSetValue(this);
		}

		public Builder add(final String key, final Object value)
		{
			mapBuilder.put(key, value);
			return this;
		}
	}

}
