package de.metas.prepayorder.inoutcandidate.spi.impl;

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
import org.adempiere.inout.util.IShipmentCandidates.OverallStatus;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.Msg;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_C_Order;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.spi.ICandidateProcessor;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.prepayorder.service.IPrepayOrderBL;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php?title=US342:_Definition_Provisionsausloeser_(2010070510001145)'>US342: Definition Provisionsausloeser (2010070510001145)</a>"
 */
public class PrepayCandidateProcessor implements ICandidateProcessor
{

	private static final Logger logger = LogManager.getLogger(PrepayCandidateProcessor.class);

	private static final String MSG_ORDER_NOT_PAID_3P = null;

	/**
	 * Makes sure that candidates are not delivered, if they belong to a metas prepay order that is not fully paid.
	 * 
	 * @see de.metas.prepayorder.model.I_C_DocType#DOCSUBTYPE_PrepayOrder_metas
	 */
	@Override
	public int processCandidates(final Properties ctx, final IShipmentCandidates candidates, final CachedObjects cachedObjects, final String trxName)
	{
		final IPrepayOrderBL prepayOrderBL = Services.get(IPrepayOrderBL.class);

		for (final I_M_InOut inOut : candidates.getCandidates())
		{
			for (final I_M_InOutLine ioLine : candidates.getLines(inOut))
			{
				final I_C_Order order = cachedObjects.retrieveAndCacheOrder(InterfaceWrapperHelper.create(ioLine.getC_OrderLine(), I_C_OrderLine.class), trxName);

				assert order.getC_Order_ID() > 0;

				if (!prepayOrderBL.isPrepayOrder(ctx, order.getC_Order_ID(), trxName))
				{
					// nothing to do
					continue;
				}

				final BigDecimal allocatedAmt = prepayOrderBL.retrieveAllocatedAmt(ctx, order.getC_Order_ID(), trxName);

				if (allocatedAmt.compareTo(order.getGrandTotal()) < 0)
				{
					// add a warning
					final String statusInfo = Msg.getMsg(ctx, MSG_ORDER_NOT_PAID_3P, new Object[] { order.getDocumentNo(), order.getGrandTotal(), allocatedAmt });
					candidates.addStatusInfo(ioLine, statusInfo);

					// discard the line if the qty has been set manually
					if (candidates.getShipmentSchedule(ioLine).getQtyToDeliver_Override().signum() <= 0)
					{
						logger.debug("Discarding candidate for " + candidates.getShipmentSchedule(ioLine));
						candidates.setOverallStatus(ioLine, OverallStatus.DISCARD);
					}
					else
					{
						logger.debug("Not discarding line despite insufficent allocation, because " + candidates.getShipmentSchedule(ioLine) + " has a qty override");
					}
				}
			}
		}

		// always returning null, because this processor only discards candidates.
		return 0;
	}

}
