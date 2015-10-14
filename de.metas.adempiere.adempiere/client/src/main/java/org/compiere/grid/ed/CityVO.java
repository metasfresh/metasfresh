/**
 * 
 */
package org.compiere.grid.ed;

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
public class CityVO
{
	public final int C_City_ID;
	public final String CityName;
	public final int C_Region_ID;
	public final String RegionName;
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
		final int prime = 31;
		int result = 1;
		result = prime * result + C_City_ID;
		result = prime * result + C_Region_ID;
		result = prime * result + ((CityName == null) ? 0 : CityName.hashCode());
		result = prime * result + ((RegionName == null) ? 0 : RegionName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CityVO other = (CityVO) obj;
		if (C_City_ID != other.C_City_ID)
			return false;
		if (C_Region_ID != other.C_Region_ID)
			return false;
		if (CityName == null)
		{
			if (other.CityName != null)
				return false;
		}
		else if (!CityName.equals(other.CityName))
			return false;
		if (RegionName == null)
		{
			if (other.RegionName != null)
				return false;
		}
		else if (!RegionName.equals(other.RegionName))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
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

}
