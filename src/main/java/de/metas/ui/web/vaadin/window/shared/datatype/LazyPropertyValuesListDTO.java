package de.metas.ui.web.vaadin.window.shared.datatype;

import com.google.common.base.MoreObjects;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

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

public class LazyPropertyValuesListDTO
{
	// FIXME: we need a simple serializable POJO which later can instruct the View which service shall it call
	
	public static final LazyPropertyValuesListDTO memoize(final Supplier<PropertyValuesListDTO> supplier)
	{
		return new LazyPropertyValuesListDTO(Suppliers.memoize(supplier));
	}

	public static PropertyValuesListDTO getPropertyValuesListDTO(final Object value)
	{
		if (value == null)
		{
			return PropertyValuesListDTO.EMPTY;
		}
		else if (value instanceof PropertyValuesListDTO)
		{
			return PropertyValuesListDTO.cast(value);
		}
		else if (value instanceof LazyPropertyValuesListDTO)
		{
			return ((LazyPropertyValuesListDTO)value).get();
		}
		else
		{
			throw new IllegalArgumentException("Cannot extract " + PropertyValuesListDTO.class + " from " + value);
		}
	}

	private final Supplier<PropertyValuesListDTO> supplier;

	private LazyPropertyValuesListDTO(final Supplier<PropertyValuesListDTO> supplier)
	{
		super();
		this.supplier = supplier;
	}
	
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(supplier)
				.toString();
	}

	public PropertyValuesListDTO get()
	{
		return supplier.get();
	}
}
