package de.metas.commission.pricing.spi.impl;

/*
 * #%L
 * de.metas.commission.base
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
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListBL.IPlvCreationListener;
import org.adempiere.util.Services;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.util.CLogger;

import de.metas.adempiere.model.I_M_PriceList;
import de.metas.adempiere.model.I_M_ProductPrice;
import de.metas.commission.interfaces.I_M_DiscountSchemaLine;
import de.metas.commission.service.IPriceListBL;

/**
 * Contains the code from the former jboss-aop aspect <code>de.metas.commission.aop.PriceListCreate</code>
 * @author ts
 * @task http://dewiki908/mediawiki/index.php/07286_get_rid_of_jboss-aop_for_good_%28104432455599%29
 */
public class CommissionPlvCreationListener implements IPlvCreationListener
{

	private static final CLogger logger = CLogger.getCLogger(CommissionPlvCreationListener.class);

	@Override
	public void onPlvCreation(final IContextAware ctxAware,
			final I_M_PriceList_Version targetPriceListVersion,
			final Iterator<I_M_ProductPrice> oldProductPrices,
			final org.compiere.model.I_M_DiscountSchemaLine dsl,
			final int adPinstanceId)
	{
		final I_M_DiscountSchemaLine dslToUse = InterfaceWrapperHelper.create(dsl, I_M_DiscountSchemaLine.class);

		if (targetPriceListVersion.getM_Pricelist_Version_Base_ID() == 0)
		{
			logger.info(targetPriceListVersion + " has M_Pricelist_Version_Base_ID=0; nothing to do");
			// process.addLog("Only working with base price list version");
			return;
		}

		final IPriceListBL plBL = Services.get(IPriceListBL.class);

		final int plCountryId = InterfaceWrapperHelper.create(targetPriceListVersion.getM_PriceList(), I_M_PriceList.class).getC_Country_ID();
		if (plCountryId <= 0)
		{
			if (dslToUse.isCommissionPoints_SubtractVAT())
			{
				logger.info("Ignoriere '@" + I_M_DiscountSchemaLine.COLUMNNAME_CommissionPoints_SubtractVAT
						+ "@' fuer Produktpreise, da in der Preisliste " + targetPriceListVersion.getM_PriceList().getName()
						+ " kein Land vermerkt ist.\n");
			}
		}

		final String trxName = ctxAware.getTrxName();
		plBL.updateCommissionPoints(targetPriceListVersion, dslToUse, adPinstanceId, trxName);
		plBL.updateSalcePriceCommissionPoints(targetPriceListVersion, dslToUse, adPinstanceId, trxName);

		return;
	}

	/**
	 * Returns <code>10</code>.
	 * 
	 * From the orginal jboss-aop.xml file.
	 * 
	 * <pre>
	 *  Make sure that the commission aspect is called before the swat aspect.
	 * 	The this means that the commission aspect will be called around the swat aspect (which in turn will be called around the joinpoint).
	 * 	This further means that the commission aspect can work with the results created by the swat aspect.
	 * </pre>
	 */
	@Override
	public int getExecutionOrderSeqNo()
	{
		return 10;
	}

}
