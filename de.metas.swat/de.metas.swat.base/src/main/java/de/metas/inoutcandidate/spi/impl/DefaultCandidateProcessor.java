package de.metas.inoutcandidate.spi.impl;

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


import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.inout.util.CachedObjects;
import org.adempiere.inout.util.IShipmentCandidates;
import org.adempiere.inout.util.IShipmentCandidates.CompleteStatus;
import org.adempiere.inout.util.IShipmentCandidates.OverallStatus;
import org.adempiere.inout.util.IShipmentCandidates.PostageFreeStatus;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_M_Product;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.spi.ICandidateProcessor;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;

public class DefaultCandidateProcessor implements ICandidateProcessor
{

	private static final String AD_SYSCONFIG_DE_METAS_INOUTCANDIDATE_ALLOW_SHIP_SINGLE_NON_ITEMS = "de.metas.inoutcandidate.AllowShipSingleNonItems";

	public final static String MSG_NO_ITEM_TO_SHIP = "ShipmentSchedule_NoItemToShip";

	private static final Logger logger = LogManager.getLogger(DefaultCandidateProcessor.class);

	@Override
	public final int processCandidates(final Properties ctx,
			final IShipmentCandidates candidates, final CachedObjects co, final String trxName)
	{
		return purgeLinesOK(ctx, candidates, trxName);
	}

	/**
	 * Removes all inOutLines (and afterwards also empty inOuts) that have {@link CompleteStatus#OK} <b>and</b>
	 * {@link PostageFreeStatus#OK}
	 */
	private int purgeLinesOK(final Properties ctx, final IShipmentCandidates candidates, final String trxName)
	{
		int rmInOutLines = 0;

		for (final I_M_InOut inOut : candidates.getCandidates())
		{
			for (final I_M_InOutLine inOutLine : candidates.getLines(inOut))
			{
				//
				// check the complete and postage free status
				final CompleteStatus completeStatus = candidates.getCompleteStatus(inOutLine);
				final PostageFreeStatus postageFreeStatus = candidates.getPostageFreeStatus(inOutLine);

				if (CompleteStatus.OK.equals(completeStatus) && PostageFreeStatus.OK.equals(postageFreeStatus))
				{
					candidates.setOverallStatus(inOutLine, OverallStatus.REVALIDATE);
					rmInOutLines++;
				}
				else
				{
					candidates.setOverallStatus(inOutLine, OverallStatus.DISCARD);
				}

				//
				//
				final IProductBL productBL = Services.get(IProductBL.class);
				final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

				final I_M_Product product = InterfaceWrapperHelper.create(inOutLine.getM_Product(), I_M_Product.class);

				// task 08745: by default we don't allow this, to stay back wards compatible 
				final boolean allowShipSingleNonItems = sysConfigBL.getBooleanValue(AD_SYSCONFIG_DE_METAS_INOUTCANDIDATE_ALLOW_SHIP_SINGLE_NON_ITEMS, false);
				final boolean isItemProduct = productBL.isItem(product);

				if (!allowShipSingleNonItems && !isItemProduct)
				{
					// allowShipSingleNonItems was true, we wouldn't have to care.

					// product is not an item -> check if there if there would
					// also be an item on the same inOut

					boolean inOutContainsItem = false;
					for (final I_M_InOutLine searchIol : candidates.getLines(inOut))
					{
						final org.compiere.model.I_M_Product iolProduct = searchIol.getM_Product();

						if (productBL.isItem(iolProduct))
						{
							inOutContainsItem = true;
							break;
						}
					}
					if (!inOutContainsItem)
					{
						// check if the delivery of this non-item has been
						// enforced using QtyToDeliver_Override>0

						final BigDecimal qtyToDeliverOverride = candidates.getShipmentSchedule(inOutLine).getQtyToDeliver_Override();

						if (qtyToDeliverOverride == null || qtyToDeliverOverride.signum() <= 0)
						{
							candidates.setOverallStatus(inOutLine, OverallStatus.DISCARD);
							candidates.addStatusInfo(inOutLine, Services.get(IMsgBL.class).getMsg(ctx, MSG_NO_ITEM_TO_SHIP));
						}
					}
				}
			}
		}
		logger.info("Removed " + rmInOutLines + " MInOutLine instances");
		return rmInOutLines;
	}
}
