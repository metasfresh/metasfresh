package de.metas.ui.web.vaadin.window.data;

import java.util.Objects;

import org.adempiere.util.lang.EqualsBuilder;

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


public final class LookupDataSourceRequest
{
	public static final LookupDataSourceRequest of(final int windowNo, final String columnName, final int displayType, final int AD_Reference_Value_ID)
	{
		return new LookupDataSourceRequest(windowNo, columnName, displayType, AD_Reference_Value_ID);
	}

	private final int windowNo;
	private final String columnName;
	private final int displayType;
	private final int AD_Reference_Value_ID;

	private LookupDataSourceRequest(final int windowNo, final String columnName, final int displayType, final int AD_Reference_Value_ID)
	{
		super();
		this.windowNo = windowNo;
		this.columnName = columnName;
		this.displayType = displayType;
		this.AD_Reference_Value_ID = AD_Reference_Value_ID;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(windowNo, columnName, displayType, AD_Reference_Value_ID);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final LookupDataSourceRequest other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(windowNo, other.windowNo)
				.append(columnName, other.columnName)
				.append(displayType, other.displayType)
				.append(AD_Reference_Value_ID, other.AD_Reference_Value_ID)
				.isEqual();
	}

	public int getWindowNo()
	{
		return windowNo;
	}

	public String getColumnName()
	{
		return columnName;
	}

	public int getDisplayType()
	{
		return displayType;
	}

	public int getAD_Reference_Value_ID()
	{
		return AD_Reference_Value_ID;
	}
}