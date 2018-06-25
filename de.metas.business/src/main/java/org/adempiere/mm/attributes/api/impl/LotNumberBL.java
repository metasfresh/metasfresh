package org.adempiere.mm.attributes.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getCtx;

import java.util.Date;
import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ILotNumberBL;
import org.adempiere.mm.attributes.api.ILotNumberDateAttributeDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.TimeUtil;

import lombok.NonNull;

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
	
	@Override
	public String getLotNumberAttributeValueOrNull(@NonNull final I_M_AttributeSetInstance asi)
	{
		final Properties ctx = getCtx(asi);

		final I_M_Attribute lotNumberAttr = Services.get(ILotNumberDateAttributeDAO.class).getLotNumberAttribute(ctx);

		if (lotNumberAttr == null)
		{
			return null;
		}
		
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		final int lotNumberAttrID = lotNumberAttr.getM_Attribute_ID();
		final I_M_AttributeInstance lotNumberAI = attributeDAO.retrieveAttributeInstance(asi, lotNumberAttrID);

		if(lotNumberAI == null)
		{
			return null;
		}
		
		return lotNumberAI.getValue();
	}
		
}
