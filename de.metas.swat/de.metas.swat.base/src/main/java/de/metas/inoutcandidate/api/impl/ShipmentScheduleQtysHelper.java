package de.metas.inoutcandidate.api.impl;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

import org.adempiere.inout.util.DeliveryLineCandidate;
import org.adempiere.inout.util.IShipmentSchedulesDuringUpdate;
import org.adempiere.inout.util.IShipmentSchedulesDuringUpdate.CompleteStatus;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.order.DeliveryRule;
import de.metas.util.Services;

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

	@VisibleForTesting
	static final String MSG_DeliveryStopStatus = "ShipmentSchedule_DeliveryStop_Status";

	@VisibleForTesting
	static final String MSG_ClosedStatus = "ShipmentSchedule_Closed_Status";

	public static void updateQtyToDeliver(
			@NonNull final OlAndSched olAndSched,
			@NonNull final IShipmentSchedulesDuringUpdate shipmentCandidates)
	{
		final I_M_ShipmentSchedule sched = olAndSched.getSched();

		if (sched.isClosed() || sched.isDeliveryStop())
		{
			setZeroQtyToDeliverAndRelatedStatuses(sched);
			return;
		}

		final DeliveryLineCandidate lineCandidate = shipmentCandidates.getLineCandidateForShipmentScheduleId(sched.getM_ShipmentSchedule_ID());
		if (lineCandidate == null)
		{
			ShipmentScheduleQtysHelper.setQtyToDeliverForDiscardedShipmentSchedule(sched);
		}
		else
		{
			sched.setQtyToDeliver(lineCandidate.getQtyToDeliver());
			sched.setStatus(mkStatus(lineCandidate, shipmentCandidates));
		}

		final BigDecimal newQtyToDeliverOverrideFulfilled = mkQtyToDeliverOverrideFulFilled(olAndSched);
		if (olAndSched.getQtyOverride() != null)
		{
			if (newQtyToDeliverOverrideFulfilled.compareTo(olAndSched.getQtyOverride()) >= 0)
			{
				// the QtyToDeliverOverride value that was set by the user has been fulfilled (or even over-fulfilled)
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

	private static void setZeroQtyToDeliverAndRelatedStatuses(final I_M_ShipmentSchedule sched)
	{
		final StringBuilder statusMessages = new StringBuilder();
		if (sched.isDeliveryStop())
		{
			statusMessages.append(Services.get(IMsgBL.class).getMsg(Env.getCtx(), MSG_DeliveryStopStatus) + "\n");
		}
		if (sched.isClosed())
		{
			statusMessages.append(Services.get(IMsgBL.class).getMsg(Env.getCtx(), MSG_ClosedStatus) + "\n");
		}
		sched.setQtyToDeliver(BigDecimal.ZERO);
		sched.setStatus(statusMessages.toString());
	}

	/**
	 * Method sets the give {@code sched}'s {@code QtyToDeliver} value in case the previous allocation runs did <b>not</b> allocate a qty to deliver. Note that if the effective delivery rule is
	 * "FORCE", then we still need to set a qty even in that case.
	 *
	 * @param discardedShipmentSchedule
	 */
	@VisibleForTesting
	/* package */static void setQtyToDeliverForDiscardedShipmentSchedule(final I_M_ShipmentSchedule discardedShipmentSchedule)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final boolean ruleForce = DeliveryRule.FORCE.equals(shipmentScheduleEffectiveBL.getDeliveryRule(discardedShipmentSchedule));
		if (!ruleForce)
		{
			discardedShipmentSchedule.setQtyToDeliver(BigDecimal.ZERO);
			return;
		}

		final BigDecimal qtyToDeliverOverride = discardedShipmentSchedule.getQtyToDeliver_Override();
		if (qtyToDeliverOverride.signum() > 0)
		{
			discardedShipmentSchedule.setQtyToDeliver(qtyToDeliverOverride);
		}
		else
		{
			// task 09005: make sure the correct qtyOrdered is taken from the shipmentSchedule
			final BigDecimal qtyOrdered = shipmentScheduleEffectiveBL.computeQtyOrdered(discardedShipmentSchedule);

			// task 07884-IT1: even if the rule is force: if there is an unconfirmed qty, then *don't* deliver it again
			discardedShipmentSchedule.setQtyToDeliver(mkQtyToDeliver(qtyOrdered, discardedShipmentSchedule.getQtyPickList()));
		}
	}

	/**
	 * Creates the status string for the {@link I_M_ShipmentSchedule#COLUMNNAME_Status} column.
	 *
	 * @param deliveryLineCandidate
	 * @param shipmentCandidates
	 * @return
	 */
	private static String mkStatus(
			@NonNull final DeliveryLineCandidate deliveryLineCandidate,
			@NonNull final IShipmentSchedulesDuringUpdate shipmentCandidates)
	{
		final CompleteStatus completeStatus = deliveryLineCandidate.getCompleteStatus();
		if (!IShipmentSchedulesDuringUpdate.CompleteStatus.OK.equals(completeStatus))
		{
			final String statusMessage = Services.get(IMsgBL.class).getMsg(Env.getCtx(), completeStatus.toString());
			shipmentCandidates.addStatusInfo(deliveryLineCandidate, statusMessage);
		}

		return shipmentCandidates.getStatusInfos(deliveryLineCandidate);
	}

	public static BigDecimal mkQtyToDeliver(
			@NonNull final BigDecimal qtyRequired,
			@NonNull final BigDecimal unconfirmedShippedQty)
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
