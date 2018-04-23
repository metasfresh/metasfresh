package org.adempiere.mm.attributes.api;

import java.util.Date;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_AttributeSetInstance;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface ILotNumberBL extends ISingletonService
{

	/**
	 * Calculate the lotNumber.
	 * Currently, it contains the week number in the year and the day of week.
	 * To be completed with extra info if needed.
	 * 
	 * PLEASE KEEP IN SYNC WITH the sql function : "de.metas.handlingunits".hu_lotnumberdate_tostring(p_Date date)
	 * 
	 * Both the logic called by this method and the sql function are according to international standard ISO 8601.
	 * Please, check https://en.wikipedia.org/wiki/ISO_week_date for extra details
	 * 
	 * @param date
	 * @return
	 */
	String calculateLotNumber(Date date);

	/**
	 * @param asi
	 * @return the value of the LotNo attribute if found, null otherwise
	 */
	String getLotNumberAttributeValueOrNull(I_M_AttributeSetInstance asi);

}
