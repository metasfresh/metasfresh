package de.metas.ordercandidate.spi.impl;

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


import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Services;

import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandGroupingProvider;

/**
 * The default grouping provider.
 * 
 * @author ts
 * 
 */
public class DefaultGroupingProvider implements IOLCandGroupingProvider
{

	/**
	 * The method returns those candidate values such as <code>M_Product_ID</code> or <code>C_BPartner_ID</code>, that
	 * always result in different order lines if they are different.
	 * 
	 * @return a list of values that will be transformed into a grouping key
	 */
	@Override
	public List<Object> provideLineGroupingValues(final I_C_OLCand olCand)
	{
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
		
		final List<Object> groupingValues = new ArrayList<Object>();

		//
		// add the values that always result in different order lines and thus need to be considered as grouping keys.
		// Note that these values might or might not result in different orders as well
		groupingValues.add(olCandEffectiveValuesBL.getM_Product_Effective_ID(olCand));
		groupingValues.add(olCand.getC_Charge_ID());
		groupingValues.add(olCand.getM_AttributeSet_ID());
		groupingValues.add(olCand.getM_AttributeSetInstance_ID());
		groupingValues.add(olCandEffectiveValuesBL.getC_UOM_Effective_ID(olCand));
		groupingValues.add(olCand.getM_Warehouse_Dest_ID());

		groupingValues.add(olCandEffectiveValuesBL.getC_BPartner_Effective_ID(olCand));
		groupingValues.add(olCandEffectiveValuesBL.getC_BP_Location_Effective_ID(olCand));
		groupingValues.add(olCandEffectiveValuesBL.getAD_User_Effective_ID(olCand));

		groupingValues.add(olCandEffectiveValuesBL.getBill_BPartner_Effective_ID(olCand));
		groupingValues.add(olCandEffectiveValuesBL.getBill_Location_Effective_ID(olCand));
		groupingValues.add(olCandEffectiveValuesBL.getBill_User_Effective_ID(olCand));

		// task 06269 note that for now we set datepromised only in the header, so different DatePromised values result in different orders, and all ol have the same datepromised
		groupingValues.add(olCandEffectiveValuesBL.getDatePromised_Effective(olCand)); 
		return groupingValues;
	}

}
