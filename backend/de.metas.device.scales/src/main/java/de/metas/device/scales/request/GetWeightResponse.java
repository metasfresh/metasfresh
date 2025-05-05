package de.metas.device.scales.request;

/*
 * #%L
 * de.metas.device.scales
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

import de.metas.device.api.ISingleValueResponse;

import java.math.BigDecimal;

public class GetWeightResponse implements ISingleValueResponse
{
	private final BigDecimal weight;
	private final String uom;

	public GetWeightResponse(final BigDecimal weight, final String uom)
	{
		this.weight = weight;
		this.uom = uom;
	}

	public BigDecimal getWeight()
	{
		return weight;
	}

	public String getUom()
	{
		return uom;
	}

	@Override
	public BigDecimal getSingleValue()
	{
		return getWeight();
	}

	@Override
	public String toString()
	{
		return "GetWeightResponse [weight=" + weight + ", uom=" + uom + "]";
	}


}
