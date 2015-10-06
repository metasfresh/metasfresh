package de.metas.inoutcandidate.model;

/*
 * #%L
 * de.metas.swat.base
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


import java.sql.ResultSet;
import java.util.Properties;

public final class MMShipmentSchedule extends X_M_ShipmentSchedule
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6361246373193780042L;

	public MMShipmentSchedule(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	public MMShipmentSchedule(Properties ctx, int M_ShipmentSchedule_ID, String trxName)
	{
		super(ctx, M_ShipmentSchedule_ID, trxName);
	}

	// TODO: do we still need this?
	@Override
	public int hashCode()
	{
		if (get_ID() != 0)
		{
			return get_ID();
		}
		return super.hashCode();
	}

	// TODO: do we still need this?
	@Override
	public boolean equals(Object other)
	{
		if (other == null)
		{
			return false;
		}
		if (other instanceof MMShipmentSchedule)
		{
			return ((MMShipmentSchedule)other).hashCode() == hashCode();
		}
		return false;
	}

	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer();

		sb.append("MShipmentSchedule[")
				.append(get_ID())
				.append(", C_OrderLine_ID=")
				.append(getC_OrderLine_ID())
				.append(", QtyToDeliver=")
				.append(getQtyToDeliver())
				.append("]");

		return sb.toString();
	}
}
