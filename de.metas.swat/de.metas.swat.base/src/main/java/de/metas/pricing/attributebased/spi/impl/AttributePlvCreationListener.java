package de.metas.pricing.attributebased.spi.impl;

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


import java.util.Iterator;

import org.adempiere.model.IContextAware;
import org.adempiere.pricing.api.IPriceListBL.IPlvCreationListener;
import org.adempiere.util.Services;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_PriceList_Version;

import de.metas.adempiere.model.I_M_ProductPrice;
import de.metas.pricing.attributebased.IAttributePricingBL;

/**
 * Replaces the former jboss-aop aspect <code>de.metas.adempiere.aop.PriceListCreate</code>.
 * 
 * @author ts
 * @task http://dewiki908/mediawiki/index.php/fresh_07286_get_rid_of_jboss-aop_for_good_%28104432455599%29
 */
public class AttributePlvCreationListener implements IPlvCreationListener
{
	@Override
	public void onPlvCreation(final IContextAware ctxAware,
			final I_M_PriceList_Version targetPriceListVersion,
			final Iterator<I_M_ProductPrice> oldProductPrices,
			final I_M_DiscountSchemaLine dsl_IGNORED,
			final int adPinstanceId_IGNORED
			)
	{
		//
		// Update Attribute pricing records
		Services.get(IAttributePricingBL.class).createUpdateProductPriceAttributes(ctxAware.getCtx(),
				targetPriceListVersion,
				null, // no filter beacause we want to copy all price attributes, even if it's Season Fixed Price
				oldProductPrices,
				ctxAware.getTrxName());
	}

	/**
	 * Returns <code>100</code>.
	 * 
	 * From the orginal jboss-aop.xml file.
	 * 
	 * <pre>
	 *  Make sure that the commission aspect is called before the swat aspect (*if* we are in a customer-project that uses commission).
	 * 	The this means that the commission aspect will be called around the swat aspect (which in turn will be called around the joinpoint).
	 * 	This further means that the commission aspect can work with the results created by the swat aspect.
	 * </pre>
	 */
	@Override
	public int getExecutionOrderSeqNo()
	{
		return 100;
	}
}
