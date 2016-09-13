package de.metas.ui.web.window_old.shared.datatype;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import de.metas.ui.web.window_old.PropertyName;

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

@SuppressWarnings("serial")
public final class PropertyPath implements Serializable
{
	public static final PropertyPath of(final PropertyName propertyName)
	{
		final PropertyName gridPropertyName = null;
		final GridRowId rowId = null;
		return new PropertyPath(gridPropertyName, rowId, propertyName);
	}

	public static final PropertyPath of(final PropertyName gridPropertyName, final GridRowId rowId, final PropertyName propertyName)
	{
		return new PropertyPath(gridPropertyName, rowId, propertyName);
	}

	@JsonProperty("gridPropertyName")
	private final PropertyName gridPropertyName;
	@JsonProperty("rowId")
	private final GridRowId rowId;
	@JsonProperty("propertyName")
	private final PropertyName propertyName;

	private PropertyPath(
			@JsonProperty("gridPropertyName") final PropertyName gridPropertyName //
			, @JsonProperty("rowId") final GridRowId rowId //
			, @JsonProperty("propertyName") final PropertyName propertyName)
	{
		super();

		this.gridPropertyName = gridPropertyName;

		this.rowId = rowId;
		if (gridPropertyName != null)
		{
			Preconditions.checkNotNull(rowId, "rowId");
		}

		this.propertyName = Preconditions.checkNotNull(propertyName, "propertyName");
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("gridPropertyName", gridPropertyName)
				.add("rowId", rowId)
				.add("propertyName", propertyName)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(gridPropertyName, rowId, propertyName);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}

		if (getClass() != obj.getClass())
		{
			return false;
		}

		final PropertyPath other = (PropertyPath)obj;
		return Objects.equals(gridPropertyName, other.gridPropertyName)
				&& Objects.equals(rowId, other.rowId)
				&& Objects.equals(propertyName, other.propertyName);
	}

	public PropertyName getPropertyName()
	{
		return propertyName;
	}

	public PropertyName getGridPropertyName()
	{
		return gridPropertyName;
	}

	public GridRowId getRowId()
	{
		return rowId;
	}

	@JsonIgnore
	public boolean isGridProperty()
	{
		return gridPropertyName != null;
	}
}
