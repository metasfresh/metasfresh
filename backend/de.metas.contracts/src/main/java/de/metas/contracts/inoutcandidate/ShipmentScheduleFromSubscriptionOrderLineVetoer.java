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

import ch.qos.logback.classic.Level;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.inoutcandidate.spi.ModelWithoutShipmentScheduleVetoer;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.model.InterfaceWrapperHelper;
import org.slf4j.Logger;

/**
 * This implementation vetoes the creation of shipment schedule records for {@link I_C_OrderLine}s if those order lines
 * are handled by a subscription contract.
 */
@ToString
public class ShipmentScheduleFromSubscriptionOrderLineVetoer implements ModelWithoutShipmentScheduleVetoer
{

	private static final Logger logger = LogManager.getLogger(ShipmentScheduleFromSubscriptionOrderLineVetoer.class);

	private final ISubscriptionBL subscriptionBL = Services.get(ISubscriptionBL.class);

	/**
	 * @param model the object for which we want to create a shipment schedule. The method
	 *              assumes that it can obtain a {@link I_C_OrderLine} instance for model by using the
	 *              {@link InterfaceWrapperHelper}.
	 */
	@Override
	public OnMissingCandidate foundModelWithoutInOutCandidate(@NonNull final Object model)
	{
		final I_C_OrderLine ol = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);

		final boolean subscription = subscriptionBL.isSubscription(ol);

		if (subscription)
		{
			Loggables.withLogger(logger, Level.DEBUG)
					.addLog("ShipmentScheduleFromSubscriptionOrderLineVetoer - isSubscription={}; return {}; orderLine={}",
							subscription, OnMissingCandidate.I_VETO, ol);
			return OnMissingCandidate.I_VETO;
		}
		return OnMissingCandidate.I_DONT_CARE;
	}
}
