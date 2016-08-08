package de.metas.ui.web.window_old.shared.datatype;

import java.util.Collection;
import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

/*
 * #%L
 * metasfresh-webui
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

public final class PropertyValuesListDTO
{
	public static final PropertyValuesListDTO of(final Collection<PropertyValuesDTO> list)
	{
		if (list == null || list.isEmpty())
		{
			return EMPTY;
		}
		return new PropertyValuesListDTO(list);
	}

	public static final Builder builder()
	{
		return new Builder();
	}

	public static final PropertyValuesListDTO cast(final Object valueObj)
	{
		return (PropertyValuesListDTO)valueObj;
	}

	public static final PropertyValuesListDTO EMPTY = new PropertyValuesListDTO();

	private final List<PropertyValuesDTO> list;

	public PropertyValuesListDTO(final Collection<PropertyValuesDTO> list)
	{
		super();
		this.list = ImmutableList.copyOf(list);
	}

	public PropertyValuesListDTO()
	{
		super();
		list = ImmutableList.of();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(list)
				.toString();
	}

	public List<PropertyValuesDTO> getList()
	{
		return list;
	}

	public static final class Builder
	{
		private final ImmutableList.Builder<PropertyValuesDTO> list = ImmutableList.builder();
		private boolean empty = true;

		private Builder()
		{
			super();
		}

		public PropertyValuesListDTO build()
		{
			if (empty)
			{
				return EMPTY;
			}
			return new PropertyValuesListDTO(list.build());
		}

		public Builder add(final PropertyValuesDTO values)
		{
			list.add(values);
			empty = false;
			return this;
		}
	}
}
