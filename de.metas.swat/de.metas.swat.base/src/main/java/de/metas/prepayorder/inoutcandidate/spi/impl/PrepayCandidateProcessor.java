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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.inout.util.DeliveryGroupCandidate;
import org.adempiere.inout.util.DeliveryLineCandidate;
import org.adempiere.inout.util.IShipmentSchedulesDuringUpdate;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.slf4j.Logger;

import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.spi.IShipmentSchedulesAfterFirstPassUpdater;
import de.metas.logging.LogManager;
import de.metas.prepayorder.service.IPrepayOrderBL;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php?title=US342:_Definition_Provisionsausloeser_(2010070510001145)'>US342: Definition Provisionsausloeser (2010070510001145)</a>"
 */
public class PrepayCandidateProcessor implements IShipmentSchedulesAfterFirstPassUpdater
{

	private static final Logger logger = LogManager.getLogger(PrepayCandidateProcessor.class);

	private static final String MSG_ORDER_NOT_PAID_3P = null;

	/**
	 * Makes sure that candidates are not delivered, if they belong to a metas prepay order that is not fully paid.
	 * 
	 * @see de.metas.prepayorder.model.I_C_DocType#DOCSUBTYPE_PrepayOrder_metas
	 */
	@Override
	public int doUpdateAfterFirstPass(
			final Properties ctx,
			final IShipmentSchedulesDuringUpdate candidates,
			final String trxName)
	{
		final IPrepayOrderBL prepayOrderBL = Services.get(IPrepayOrderBL.class);

		for (final DeliveryGroupCandidate inOut : candidates.getCandidates())
		{
			for (final DeliveryLineCandidate ioLine : inOut.getLines())
			{
				final TableRecordReference scheduleReference = ioLine.getReferenced();
				if (!I_C_OrderLine.Table_Name.equals(scheduleReference.getTableName()))
				{
					continue;
				}

				final I_C_OrderLine orderLine = scheduleReference.getModel(PlainContextAware.newWithTrxName(ctx, trxName), I_C_OrderLine.class);
				if (!prepayOrderBL.isPrepayOrder(ctx, orderLine.getC_Order_ID(), trxName))
				{
					// nothing to do
					continue;
				}

				final BigDecimal allocatedAmt = prepayOrderBL.retrieveAllocatedAmt(ctx, orderLine.getC_Order_ID(), trxName);

				final I_C_Order order = orderLine.getC_Order();

				if (allocatedAmt.compareTo(order.getGrandTotal()) < 0)
				{
					// add a warning
					final String statusInfo = Services.get(IMsgBL.class).getMsg(ctx, MSG_ORDER_NOT_PAID_3P, new Object[] { order.getDocumentNo(), order.getGrandTotal(), allocatedAmt });
					candidates.addStatusInfo(ioLine, statusInfo);

					// discard the line if the qty has been set manually
					if (ioLine.getQtyToDeliverOverride().signum() <= 0)
					{
						logger.debug("Discarding candidate for {}", ioLine);
						ioLine.setDiscarded();
					}
					else
					{
						logger.debug("Not discarding line despite insufficent allocation, because {} has a qty override", ioLine);
					}
				}
			}
		}

		// always returning null, because this processor only discards candidates.
		return 0;
	}

}
