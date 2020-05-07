package de.metas.shipper.gateway.api.model;

import org.adempiere.util.Check;

import lombok.EqualsAndHashCode;

/*
 * #%L
 * de.metas.shipper.gateway.api
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

/**
 * House Way Bill (HWB)
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @see https://en.wikipedia.org/wiki/Global_Identification_Number_for_Consignment
 */
@EqualsAndHashCode
public final class HWBNumber
{
	public static final HWBNumber of(final String hwbNumberStr)
	{
		return new HWBNumber(hwbNumberStr);
	}
	
	public static final HWBNumber ofNullable(final String hwbNumberStr)
	{
		if(Check.isEmpty(hwbNumberStr, true))
		{
			return null;
		}
		return new HWBNumber(hwbNumberStr);
	}

	private final String hwbNumberStr;

	private HWBNumber(final String hwbNumberStr)
	{
		Check.assumeNotEmpty(hwbNumberStr, "hwbNumberStr is not empty");
		this.hwbNumberStr = hwbNumberStr;
	}

	@Override
	public String toString()
	{
		return getAsString();
	}

	public String getAsString()
	{
		return hwbNumberStr;
	}
}
