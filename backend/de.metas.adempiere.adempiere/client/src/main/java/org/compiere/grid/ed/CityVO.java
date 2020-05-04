/**
 * 
 */
package org.compiere.grid.ed;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.compiere.swing.autocomplete.ResultItem;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2015 metas GmbH
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

/**
 * @author Teo Sarca , www.arhipac.ro
 *
 */
public class CityVO implements ResultItem
{
	private final int C_City_ID;
	private final String CityName;
	private final int C_Region_ID;
	private final String RegionName;

	public CityVO(int city_ID, String cityName, int region_ID, String regionName)
	{
		super();
		C_City_ID = city_ID;
		CityName = cityName;
		C_Region_ID = region_ID;
		RegionName = regionName;
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(C_City_ID)
				.append(C_Region_ID)
				.append(CityName)
				.append(RegionName)
				.toHashcode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		
		final CityVO other = EqualsBuilder.getOther(this, obj);
		if (other == null)
			return false;
		
		return new EqualsBuilder()
				.append(C_City_ID, other.C_City_ID)
				.append(C_Region_ID, other.C_Region_ID)
				.append(CityName, other.CityName)
				.append(RegionName, other.RegionName)
				.isEqual();
	}

	@Override
	public String getText()
	{
		return CityName;
	}
	
	@Override
	public String toString()
	{
		// needed for ListCellRenderer
		final StringBuilder sb = new StringBuilder();
		if (this.CityName != null)
		{
			sb.append(this.CityName);
		}
		if (this.RegionName != null)
		{
			sb.append(" (").append(this.RegionName).append(")");
		}
		return sb.toString();
	}
	
	public int getC_City_ID()
	{
		return C_City_ID;
	}

	public String getCityName()
	{
		return CityName;
	}

	public int getC_Region_ID()
	{
		return C_Region_ID;
	}
	
	public String getRegionName()
	{
		return RegionName;
	}

}
