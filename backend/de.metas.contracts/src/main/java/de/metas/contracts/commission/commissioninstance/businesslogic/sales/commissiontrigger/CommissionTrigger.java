package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.Customer;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.commission
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * "basically" an invoice candidate; but can be other things in future as well.
 */
@Value
public class CommissionTrigger
{
	Customer customer;

	/**
	 * The direct sales rep;
	 * <p>
	 * Note: used to be the customer's "direct" sales rep or the customer himself. Now it's always the sales rep as it's up to the commission algorithm to decide
	 * whether the customer can get something out of it or not.
	 */
	BPartnerId salesRepId;

	BPartnerId orgBPartnerId;

	CommissionTriggerData commissionTriggerData;

	@Builder
	private CommissionTrigger(
			@NonNull final Customer customer,
			@NonNull final BPartnerId salesRepId,
			@NonNull final BPartnerId orgBPartnerId,
			@NonNull final CommissionTriggerData commissionTriggerData)
	{
		this.customer = customer;
		this.salesRepId = salesRepId;
		this.orgBPartnerId = orgBPartnerId;
		this.commissionTriggerData = commissionTriggerData;
	}

	public CommissionPoints getCommissionBase()
	{
		return commissionTriggerData.getForecastedBasePoints()
				.add(commissionTriggerData.getInvoiceableBasePoints())
				.add(commissionTriggerData.getInvoicedBasePoints());
	}
}
