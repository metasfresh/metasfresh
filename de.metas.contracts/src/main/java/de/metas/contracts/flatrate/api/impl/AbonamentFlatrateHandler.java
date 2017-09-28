package de.metas.contracts.flatrate.api.impl;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.ISubscriptionDAO.SubscriptionProgressQuery;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class AbonamentFlatrateHandler extends DefaultFlatrateHandler
{
	public static final String TYPE_CONDITIONS = X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription;

	private static final String MSG_TERM_ERROR_DELIVERY_ALREADY_HAS_SHIPMENT_SCHED_0P = "Term_Error_Delivery_Already_Has_Shipment_Sched";

	@Override
	public void beforeFlatrateTermReactivate(@NonNull final I_C_Flatrate_Term term)
	{
			// Delete subscription progress entries
		final ISubscriptionDAO subscriptionBL = Services.get(ISubscriptionDAO.class);
		final List<I_C_SubscriptionProgress> entries = subscriptionBL.retrieveSubscriptionProgresses(SubscriptionProgressQuery.builder()
				.term(term).build());

		for (final I_C_SubscriptionProgress entry : entries)
		{
			if (entry.getM_ShipmentSchedule_ID() > 0)
			{
				throw new AdempiereException("@" + MSG_TERM_ERROR_DELIVERY_ALREADY_HAS_SHIPMENT_SCHED_0P + "@");
			}
			InterfaceWrapperHelper.delete(entry);
		}
	}

}
