package de.metas.inoutcandidate.api.impl;

import static org.compiere.model.X_C_Order.DELIVERYRULE_Force;

import java.math.BigDecimal;

import org.adempiere.inout.util.DeliveryLineCandidate;
import org.adempiere.inout.util.IShipmentCandidates;
import org.adempiere.inout.util.IShipmentCandidates.CompleteStatus;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@UtilityClass
/* package */class ShipmentScheduleQtysHelper
{
	private final static Logger logger = LogManager.getLogger(ShipmentScheduleQtysHelper.class);
	private static final String MSG_DeliveryStopStatus = "ShipmentSchedule_DeliveryStop_Status";

	public static void updateQtyToDeliver(final OlAndSched olAndSched, final IShipmentCandidates shipmentCandidates)
	{
		final I_M_ShipmentSchedule sched = olAndSched.getSched();
		
		if (sched.isDeliveryStop())
		{
			sched.setQtyToDeliver(BigDecimal.ZERO);
			sched.setStatus(Services.get(IMsgBL.class).getMsg(Env.getCtx(), MSG_DeliveryStopStatus));
			return;
		}

		final DeliveryLineCandidate inOutLine = shipmentCandidates.getInOutLineForOrderLine(sched.getM_ShipmentSchedule_ID());
		if (inOutLine != null)
		{
			sched.setQtyToDeliver(inOutLine.getQtyToDeliver());
			sched.setStatus(mkStatus(inOutLine, shipmentCandidates));
		}
		else
		{
			ShipmentScheduleQtysHelper.setQtyToDeliverWhenNullInoutLine(sched);
		}

		final BigDecimal newQtyToDeliverOverrideFulfilled = mkQtyToDeliverOverrideFulFilled(olAndSched);
		if (olAndSched.getQtyOverride() != null)
		{
			if (newQtyToDeliverOverrideFulfilled.compareTo(olAndSched.getQtyOverride()) >= 0)
			{
				// the QtyToDeliverOverride value that was set by the user has been fulfilled (or even
				// over-fulfilled)
				sched.setQtyToDeliver_Override(null);
				sched.setQtyToDeliver_OverrideFulfilled(null);
			}
			else
			{
				sched.setQtyToDeliver_OverrideFulfilled(newQtyToDeliverOverrideFulfilled);
			}
		}
		else
		{
			sched.setQtyToDeliver_OverrideFulfilled(null);
		}

	}

	/**
	 * Creates the status string for the {@link I_M_ShipmentSchedule#COLUMNNAME_Status} column.
	 *
	 * @param inOutLine
	 * @param shipmentCandidates
	 * @return
	 */
	private static String mkStatus(
			final DeliveryLineCandidate inOutLine, 
			final IShipmentCandidates shipmentCandidates)
	{
		final CompleteStatus completeStatus = inOutLine.getCompleteStatus();
		if (!IShipmentCandidates.CompleteStatus.OK.equals(completeStatus))
		{
			shipmentCandidates.addStatusInfo(inOutLine, Services.get(IMsgBL.class).getMsg(Env.getCtx(), completeStatus.toString()));
		}

		return shipmentCandidates.getStatusInfos(inOutLine);
	}

	/**
	 * Method sets the give {@code sched}'s {@code QtyToDeliver} value in case the previous allocation runs did <b>not</b> allocate a qty to deliver. Note that if the effective delivery rule is
	 * "FORCE", then we still need to set a qty even in that case.
	 *
	 * @param sched
	 */
	@VisibleForTesting
	/* package */static void setQtyToDeliverWhenNullInoutLine(final I_M_ShipmentSchedule sched)
	{
		//
		// if delivery rule == force :
		// // set qtyToDeliver = qtyToDeliverOveride (if exists)
		// // else set qtyToDeliver = QtyOrdered
		// if not forced, qtyToDeliver is 0
		final String deliveryRule = Services.get(IShipmentScheduleEffectiveBL.class).getDeliveryRule(sched);
		final boolean ruleForce = DELIVERYRULE_Force.equals(deliveryRule);
		if (ruleForce)
		{
			final BigDecimal qtyToDeliverOverride = sched.getQtyToDeliver_Override();
			if (qtyToDeliverOverride.compareTo(BigDecimal.ZERO) > 0)
			{
				sched.setQtyToDeliver(qtyToDeliverOverride);
			}
			else
			{
				// task 09005: make sure the correct qtyOrdered is taken from the shipmentSchedule
				final BigDecimal qtyOrdered = Services.get(IShipmentScheduleEffectiveBL.class).getQtyOrdered(sched);

				// task 07884-IT1: even if the rule is force: if there is an unconfirmed qty, then *don't* deliver it again
				sched.setQtyToDeliver(mkQtyToDeliver(qtyOrdered, sched.getQtyPickList()));
			}
		}
		else
		{
			sched.setQtyToDeliver(BigDecimal.ZERO);
		}
	}

	public static BigDecimal mkQtyToDeliver(final BigDecimal qtyRequired, final BigDecimal unconfirmedShippedQty)
	{
		final StringBuilder logInfo = new StringBuilder("Unconfirmed Qty=" + unconfirmedShippedQty + " - ToDeliver=" + qtyRequired + "->");

		BigDecimal toDeliver = qtyRequired.subtract(unconfirmedShippedQty);

		logInfo.append(toDeliver);

		if (toDeliver.signum() < 0)
		{
			toDeliver = BigDecimal.ZERO;
			logInfo.append(" (set to 0)");
		}
		logger.debug("{}", logInfo);
		return toDeliver;
	}

	public static BigDecimal mkQtyToDeliverOverrideFulFilled(final OlAndSched olAndSched)
	{
		final I_M_ShipmentSchedule sched = olAndSched.getSched();

		final BigDecimal deliveredDiff = sched.getQtyDelivered().subtract(olAndSched.getInitialSchedQtyDelivered());

		final BigDecimal newQtyToDeliverOverrideFulfilled = sched.getQtyToDeliver_OverrideFulfilled().add(deliveredDiff);
		if (newQtyToDeliverOverrideFulfilled.signum() < 0)
		{
			return BigDecimal.ZERO;
		}
		return newQtyToDeliverOverrideFulfilled;
	}

}
