package org.adempiere.mm.attributes.api.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.adempiere.mm.attributes.api.ILotNumberBL;
import org.compiere.util.TimeUtil;

/*
 * #%L
 * de.metas.swat.base
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

public class LotNumberBL implements ILotNumberBL
{

	@Override
	public String calculateLotNumber(final Date date)
	{
		final StringBuilder lotNumber = new StringBuilder();
				
		final int weekNumber = TimeUtil.getWeekNumber(date);
		
		if(weekNumber < 10)
		{
			lotNumber.append(0);
		}
		
		lotNumber.append(weekNumber);
		
		final int dayOfWeek = TimeUtil.getDayOfWeek(date);
		
		lotNumber.append(dayOfWeek);
		
		return  lotNumber.toString();
	}
		
}
