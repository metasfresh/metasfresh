package de.metas.contracts.subscription.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.compiere.util.TimeUtil;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;

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

public class SubscriptionTestUtil
{
	public static I_C_SubscriptionProgress createDeliverySubscriptionProgress(
			final I_C_Flatrate_Term term,
			final String eventDate,
			final int seqNo)
	{
		final I_C_SubscriptionProgress subscriptionProgress = newInstance(I_C_SubscriptionProgress.class);
		POJOWrapper.setInstanceName(subscriptionProgress, eventDate);
		subscriptionProgress.setContractStatus(X_C_SubscriptionProgress.CONTRACTSTATUS_Running);
		subscriptionProgress.setStatus(X_C_SubscriptionProgress.STATUS_Planned);
		subscriptionProgress.setEventType(X_C_SubscriptionProgress.EVENTTYPE_Delivery);
		subscriptionProgress.setC_Flatrate_Term(term);
		subscriptionProgress.setEventDate(TimeUtil.parseTimestamp(eventDate));
		subscriptionProgress.setSeqNo(seqNo);
		save(subscriptionProgress);

		return subscriptionProgress;
	}
}
