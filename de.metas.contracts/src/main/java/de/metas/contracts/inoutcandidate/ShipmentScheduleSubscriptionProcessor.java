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
import org.adempiere.inout.util.IShipmentSchedulesDuringUpdate;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.spi.IShipmentSchedulesAfterFirstPassUpdater;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php?title=Auftrag_versenden_mit_Abo-Lieferung_(2009_0027_G62)'>(2009 0027 G62)</a>"
 * 
 */
public class ShipmentScheduleSubscriptionProcessor implements IShipmentSchedulesAfterFirstPassUpdater
{
	public static final String MSG_OPEN_INVOICE_1P = "ShipmentSchedule_OpenInvoice_1P";

	public static final String MSG_WITH_NEXT_SUBSCRIPTION = "ShipmentSchedule_WithNextSubscription";

	@Override
	public int doUpdateAfterFirstPass(
			final Properties ctx,
			final IShipmentSchedulesDuringUpdate candidates,
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
			final IShipmentSchedulesDuringUpdate candidates,
			final DeliveryLineCandidate inOutLine,
			final String trxName)
	{
		final String iolDeliveryRule = inOutLine.getDeliveryRule();

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
			inOutLine.setDiscarded();
			return 1;
		}

		return 0;
	}

	private boolean hasSubscriptionDelivery(
			final Properties ctx,
			final IShipmentSchedulesDuringUpdate candidates,
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
			final TableRecordReference scheduleReference = inOutLine.getReferenced();

			if(I_C_SubscriptionProgress.Table_Name.equals(scheduleReference.getTableName()))
			{
				final I_C_SubscriptionProgress sp = scheduleReference.getModel(PlainContextAware.newWithTrxName(ctx, trxName), I_C_SubscriptionProgress.class);
				final String status = sp.getStatus();
				Check.assume(X_C_SubscriptionProgress.STATUS_Open.equals(status),
						"{} referenced by {} doesn't have status {}", sp, inOutLine, status);
				atLeastOneSubscription = true;
				break;
			}
		}
		return atLeastOneSubscription;
	}
}
