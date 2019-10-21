package de.metas.contracts.commission.commissioninstance.businesslogic.sales;

import java.time.Instant;

import javax.annotation.Nullable;

import de.metas.contracts.commission.Beneficiary;
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

/** "basically" an invoice candidate */
@Value
public class CommissionTrigger
{
	Customer customer;

	/** The direct beneficiary; usually the customer's "direct" sales rep. Will probably be part of a hierarchy. */
	Beneficiary beneficiary;

	@NonNull
	CommissionTriggerData commissionTriggerData;

	@Builder
	private CommissionTrigger(
			@NonNull final Instant timestamp,
			@NonNull final Customer customer,
			@NonNull final Beneficiary beneficiary,
			@Nullable final CommissionTriggerData commissionTriggerData)
	{
		this.customer = customer;
		this.beneficiary = beneficiary;
		this.commissionTriggerData = commissionTriggerData;
	}

	public CommissionPoints getCommissionBase()
	{
		return commissionTriggerData.getForecastedPoints()
				.add(commissionTriggerData.getInvoiceablePoints())
				.add(commissionTriggerData.getInvoicedPoints());
	}
}
