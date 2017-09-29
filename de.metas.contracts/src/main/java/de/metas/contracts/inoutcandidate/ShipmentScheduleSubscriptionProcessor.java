package de.metas.contracts.inoutcandidate;

/*
 * #%L
 * de.metas.contracts
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

import static de.metas.inoutcandidate.model.X_M_ShipmentSchedule.DELIVERYRULE_MitNaechsterAbolieferung;

import java.util.Properties;

import org.adempiere.inout.util.DeliveryGroupCandidate;
import org.adempiere.inout.util.DeliveryLineCandidate;
import org.adempiere.inout.util.IShipmentCandidates;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ICandidateProcessor;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php?title=Auftrag_versenden_mit_Abo-Lieferung_(2009_0027_G62)'>(2009 0027 G62)</a>"
 * 
 */
public class ShipmentScheduleSubscriptionProcessor implements ICandidateProcessor
{
	public static final String MSG_OPEN_INVOICE_1P = "ShipmentSchedule_OpenInvoice_1P";

	public static final String MSG_WITH_NEXT_SUBSCRIPTION = "ShipmentSchedule_WithNextSubscription";

	@Override
	public int processCandidates(
			final Properties ctx,
			final IShipmentCandidates candidates,
			final String trxName)
	{
		int removeCount = 0;

		for (final DeliveryGroupCandidate inOut : candidates.getCandidates())
		{
			for (final DeliveryLineCandidate inOutLine : inOut.getLines())
			{
				if (inOutLine.isDiscarded())
				{
					// this line won't be delivered anyways. Nothing to do
					continue;
				}

				removeCount += handleWithNextSubscription(ctx, candidates, inOutLine, trxName);
			}
		}
		return removeCount;
	}

	private int handleWithNextSubscription(
			final Properties ctx,
			final IShipmentCandidates candidates,
			final DeliveryLineCandidate inOutLine,
			final String trxName)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final I_M_ShipmentSchedule sched = inOutLine.getShipmentSchedule();
		final String iolDeliveryRule = shipmentScheduleBL.getDeliveryRule(sched);

		if (!DELIVERYRULE_MitNaechsterAbolieferung.equals(iolDeliveryRule))
		{
			// this line doesn't need to be delivered together with a
			// subscription delivery
			// -> nothing to do
			return 0;

		}

		// find out there are subscription delivery lines
		boolean atLeastOneSubscription = hasSubscriptionDelivery(ctx, candidates, inOutLine, trxName);
		if (!atLeastOneSubscription)
		{
			candidates.addStatusInfo(inOutLine, Services.get(IMsgBL.class).getMsg(ctx, MSG_WITH_NEXT_SUBSCRIPTION));
			inOutLine.setDiscarded(true);
			return 1;
		}

		return 0;
	}

	private boolean hasSubscriptionDelivery(
			final Properties ctx,
			final IShipmentCandidates candidates,
			final DeliveryLineCandidate inOutLine,
			final String trxName)
	{
		final DeliveryGroupCandidate inOut = inOutLine.getGroup();

		boolean atLeastOneSubscription = false;

		for (final DeliveryLineCandidate currentLine : inOut.getLines())
		{
			if (currentLine == inOutLine)
			{
				continue;
			}
			if (currentLine.isDiscarded())
			{
				// 'currentLine' won't be delivered anyways, it is irrelevant here
				continue;
			}

			// find out if 'currentLine' has an open subscription delivery
			// (which means that it is to be delivered right now)
			final I_M_ShipmentSchedule sched = inOutLine.getShipmentSchedule();

			if (InterfaceWrapperHelper.getTableId(I_C_SubscriptionProgress.class) == sched.getAD_Table_ID())
			{
				final I_C_SubscriptionProgress sp = InterfaceWrapperHelper.create(ctx, sched.getRecord_ID(), I_C_SubscriptionProgress.class, trxName);
				Check.assume(X_C_SubscriptionProgress.STATUS_Open.equals(sp.getStatus()),
						sp + "referenced by " + sched + " doesn't have status " + sp.getStatus());
				atLeastOneSubscription = true;
				break;
			}
		}
		return atLeastOneSubscription;
	}
}
