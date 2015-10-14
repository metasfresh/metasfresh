package org.adempiere.uom.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.uom.UOMConstants;
import org.adempiere.uom.api.IUOMBL;
import org.compiere.model.I_C_UOM;

public class UOMBL implements IUOMBL
{
	@Override
	public boolean isSecond(final I_C_UOM uom)
	{
		return UOMConstants.X12_SECOND.equals(uom.getX12DE355());
	}

	@Override
	public boolean isMinute(final I_C_UOM uom)
	{
		return UOMConstants.X12_MINUTE.equals(uom.getX12DE355());
	}

	@Override
	public boolean isHour(final I_C_UOM uom)
	{
		return UOMConstants.X12_HOUR.equals(uom.getX12DE355());
	}

	@Override
	public boolean isDay(final I_C_UOM uom)
	{
		return UOMConstants.X12_DAY.equals(uom.getX12DE355());
	}

	@Override
	public boolean isWorkDay(final I_C_UOM uom)
	{
		return UOMConstants.X12_DAY_WORK.equals(uom.getX12DE355());
	}

	@Override
	public boolean isWeek(final I_C_UOM uom)
	{
		return UOMConstants.X12_WEEK.equals(uom.getX12DE355());
	}

	@Override
	public boolean isMonth(final I_C_UOM uom)
	{
		return UOMConstants.X12_MONTH.equals(uom.getX12DE355());
	}

	@Override
	public boolean isWorkMonth(final I_C_UOM uom)
	{
		return UOMConstants.X12_MONTH_WORK.equals(uom.getX12DE355());
	}

	@Override
	public boolean isYear(final I_C_UOM uom)
	{
		return UOMConstants.X12_YEAR.equals(uom.getX12DE355());
	}
}
