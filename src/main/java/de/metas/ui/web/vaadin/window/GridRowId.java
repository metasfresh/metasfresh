package de.metas.ui.web.vaadin.window;

import java.io.Serializable;
import java.util.UUID;

import com.google.common.base.Objects;

import de.metas.ui.web.vaadin.window.editor.NullValue;

/*
 * #%L
 * de.metas.ui.web.vaadin
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
public final class GridRowId implements Serializable
{
	public static final GridRowId newRowId()
	{
		return new GridRowId(UUID.randomUUID().toString());
	}
	
	public static final GridRowId of(final Object obj)
	{
		if (NullValue.isNull(obj))
		{
			return null;
		}
		else if (obj instanceof GridRowId)
		{
			return (GridRowId)obj;
		}
		else
		{
			throw new IllegalArgumentException("Invalid row id: "+obj+" ("+obj.getClass()+")");
		}
	}

	private final String id;
	private Integer _hashCode = null;
	
	private GridRowId(final String id)
	{
		super();
		this.id = id;
	}
	
	@Override
	public String toString()
	{
		return id;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (obj.getClass() != this.getClass())
		{
			return false;
		}
		
		final GridRowId other = (GridRowId)obj;
		return Objects.equal(this.id, other.id);
	}
	
	@Override
	public int hashCode()
	{
		if(_hashCode == null)
		{
			_hashCode = Objects.hashCode(id);
		}
		return _hashCode;
	}
}