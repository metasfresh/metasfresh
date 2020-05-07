package de.metas.contracts.subscription.impl.subscriptioncommands;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.IQuery;

import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.subscription.impl.SubscriptionService.ChangeRecipientsRequest;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
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

public class ChangeRecipient
{
	public static int changeRecipient(@NonNull final ChangeRecipientsRequest changeRecipientsRequest)
	{
		final IQuery<I_C_SubscriptionProgress> query = createRecipienRecordsQuery(changeRecipientsRequest);

		final List<I_C_SubscriptionProgress> shipmentSchedulesToUpdate = query.list();
		for (final I_C_SubscriptionProgress subscriptionProgress : shipmentSchedulesToUpdate)
		{
			updateSubScriptionProgressFromRequest(subscriptionProgress, changeRecipientsRequest);

			if (subscriptionProgress.getM_ShipmentSchedule_ID() > 0)
			{
				updateShipmentScheduleFromRequest(subscriptionProgress, changeRecipientsRequest);
			}
		}
		return shipmentSchedulesToUpdate.size();

	}

	private static IQuery<I_C_SubscriptionProgress> createRecipienRecordsQuery(@NonNull final ChangeRecipientsRequest changeRecipientsRequest)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_SubscriptionProgress> query = queryBL.createQueryBuilder(I_C_SubscriptionProgress.class)
				.addOnlyActiveRecordsFilter();

		if (changeRecipientsRequest.isIsPermanentRecipient())
		{
			final Timestamp now = SystemTime.asDayTimestamp();
			query.addCompareFilter(I_C_SubscriptionProgress.COLUMN_EventDate, Operator.GREATER_OR_EQUAL, now);
		}
		else
		{
			query.addCompareFilter(I_C_SubscriptionProgress.COLUMN_EventDate, Operator.GREATER_OR_EQUAL, changeRecipientsRequest.getDateFrom());
			query.addCompareFilter(I_C_SubscriptionProgress.COLUMN_EventDate, Operator.LESS_OR_EQUAL, changeRecipientsRequest.getDateTo());
		}

		return query.addEqualsFilter(I_C_SubscriptionProgress.COLUMN_C_Flatrate_Term_ID, changeRecipientsRequest.getTerm().getC_Flatrate_Term_ID())
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMN_EventType, X_C_SubscriptionProgress.EVENTTYPE_Delivery)
				.addNotEqualsFilter(I_C_SubscriptionProgress.COLUMN_Status, X_C_SubscriptionProgress.STATUS_Done)
				.create();
	}

	private static void updateSubScriptionProgressFromRequest(
			@NonNull final I_C_SubscriptionProgress subscriptionProgress,
			@NonNull final ChangeRecipientsRequest request)
	{
		subscriptionProgress.setDropShip_BPartner_ID(request.getDropShip_BPartner_ID());
		subscriptionProgress.setDropShip_Location_ID(request.getDropShip_Location_ID());
		subscriptionProgress.setDropShip_User_ID(request.getDropShip_User_ID());
		save(subscriptionProgress);
	}

	private static void updateShipmentScheduleFromRequest(
			@NonNull final I_C_SubscriptionProgress subscriptionProgress,
			@NonNull final ChangeRecipientsRequest changeRecipientsRequest)
	{
		final I_M_ShipmentSchedule shipmentSchedule = subscriptionProgress.getM_ShipmentSchedule();
		shipmentSchedule.setC_BPartner_ID(changeRecipientsRequest.getDropShip_BPartner_ID());
		shipmentSchedule.setC_BPartner_Location_ID(changeRecipientsRequest.getDropShip_Location_ID());
		shipmentSchedule.setAD_User_ID(changeRecipientsRequest.getDropShip_User_ID());
		save(shipmentSchedule);
	}
}
