package de.metas.rfq.impl;

import java.util.List;

import org.adempiere.util.Services;
import org.compiere.model.MProductCategory;

import de.metas.rfq.IRfqTopicBL;
import de.metas.rfq.IRfqTopicDAO;
import de.metas.rfq.model.I_C_RfQ_TopicSubscriber;
import de.metas.rfq.model.I_C_RfQ_TopicSubscriberOnly;

/*
 * #%L
 * de.metas.business
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

public class RfqTopicBL implements IRfqTopicBL
{
	@Override
	public boolean isProductIncluded (final I_C_RfQ_TopicSubscriber subscriber, final int M_Product_ID)
	{
		final List<I_C_RfQ_TopicSubscriberOnly> restrictions = Services.get(IRfqTopicDAO.class).retrieveRestrictions(subscriber);
		//	No restrictions
		if (restrictions.isEmpty())
		{
			return true;
		}
		
		for (final I_C_RfQ_TopicSubscriberOnly restriction : restrictions)
		{
			if (!restriction.isActive())
			{
				continue;
			}
			
			//	Product
			if (restriction.getM_Product_ID() == M_Product_ID)
			{
				return true;
			}
			
			//	Product Category
			if (MProductCategory.isCategory(restriction.getM_Product_Category_ID(), M_Product_ID))
			{
				return true;
			}
		}
		
		//	must be on "positive" list
		return false;
	}	//	isIncluded

}
