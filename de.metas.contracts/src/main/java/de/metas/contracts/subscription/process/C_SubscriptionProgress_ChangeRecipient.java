package de.metas.contracts.subscription.process;

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

import java.sql.Timestamp;

import de.metas.contracts.subscription.impl.SubscriptionService;
import de.metas.contracts.subscription.impl.SubscriptionService.ChangeRecipientsRequest;
import de.metas.process.Param;

/**
 * @task https://github.com/metasfresh/metasfresh/issues/2496
 */
public class C_SubscriptionProgress_ChangeRecipient
		extends C_SubscriptionProgressBase
{

	@Param(parameterName = "DateGeneral", mandatory = false)
	private Timestamp dateFrom;

	@Param(parameterName = "DateGeneral", mandatory = false, parameterTo = true)
	private Timestamp dateTo;

	@Param(parameterName = "DropShip_BPartner_ID", mandatory = true)
	private int DropShip_BPartner_ID;

	@Param(parameterName = "DropShip_Location_ID", mandatory = true)
	private int DropShip_Location_ID;

	@Param(parameterName = "DropShip_User_ID", mandatory = false)
	private int DropShip_User_ID;

	@Param(parameterName = "IsPermanentRecipient", mandatory = true)
	private boolean isPermanentRecipient;

	@Override
	protected String doIt()
	{
		final ChangeRecipientsRequest request = ChangeRecipientsRequest.builder()
				.term(getTermFromProcessInfo())
				.dateFrom(dateFrom)
				.dateTo(dateTo)
				.DropShip_BPartner_ID(DropShip_BPartner_ID)
				.DropShip_Location_ID(DropShip_Location_ID)
				.DropShip_User_ID(DropShip_User_ID)
				.IsPermanentRecipient(isPermanentRecipient)
				.build();

		SubscriptionService.changeRecipient(request);

		return MSG_OK;
	}
}
