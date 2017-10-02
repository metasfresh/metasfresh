package de.metas.contracts.subscription.impl.subscriptioncommands;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;

import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.subscription.impl.SubscriptionCommand.ChangeRecipientsRequest;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ChangeRecipient
{
	public static int changeRecipient(@NonNull final ChangeRecipientsRequest changeRecipientsRequest)
	{
		final IQuery<I_C_SubscriptionProgress> query = createRecipienRecordsQuery(changeRecipientsRequest);
		final ICompositeQueryUpdater<I_C_SubscriptionProgress> updater = createRecipientUpdater(changeRecipientsRequest);

		return query.update(updater);

	}

	private static IQuery<I_C_SubscriptionProgress> createRecipienRecordsQuery(@NonNull final ChangeRecipientsRequest changeRecipientsRequest)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL
				.createQueryBuilder(I_C_SubscriptionProgress.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_C_SubscriptionProgress.COLUMN_EventDate, Operator.GREATER_OR_EQUAL, changeRecipientsRequest.getDateFrom())
				.addCompareFilter(I_C_SubscriptionProgress.COLUMN_EventDate, Operator.LESS_OR_EQUAL, changeRecipientsRequest.getDateTo())
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMN_C_Flatrate_Term_ID, changeRecipientsRequest.getTerm().getC_Flatrate_Term_ID())
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMN_EventType, X_C_SubscriptionProgress.EVENTTYPE_Delivery)
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMN_Status, X_C_SubscriptionProgress.STATUS_Planned)
				.create();
	}

	private static ICompositeQueryUpdater<I_C_SubscriptionProgress> createRecipientUpdater(@NonNull final ChangeRecipientsRequest changeRecipientsRequest)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryUpdater<I_C_SubscriptionProgress> updater = queryBL
				.createCompositeQueryUpdater(I_C_SubscriptionProgress.class)
				.addSetColumnValue(I_C_SubscriptionProgress.COLUMNNAME_DropShip_BPartner_ID, changeRecipientsRequest.getDropShip_BPartner_ID())
				.addSetColumnValue(I_C_SubscriptionProgress.COLUMNNAME_DropShip_Location_ID, changeRecipientsRequest.getDropShip_Location_ID())
				.addSetColumnValue(I_C_SubscriptionProgress.COLUMNNAME_DropShip_User_ID, changeRecipientsRequest.getDropShip_User_ID());
		return updater;
	}
}
