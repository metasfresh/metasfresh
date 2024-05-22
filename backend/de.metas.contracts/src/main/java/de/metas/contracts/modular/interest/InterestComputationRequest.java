/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.interest;

import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.interest.run.InterestRunId;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.currency.CurrencyPrecision;
import de.metas.lock.api.LockOwner;
import de.metas.money.Money;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class InterestComputationRequest
{
	@NonNull InterestRunId interestRunId;
	@NonNull InvoicingGroupId invoicingGroupId;
	@NonNull Money interestToDistribute;
	@NonNull LockOwner lockOwner;
	@NonNull CurrencyPrecision interestCurrencyPrecision;
	@Nullable BonusComputationDetails bonusComputationDetails;

	@Builder(toBuilder = true)
	public InterestComputationRequest(
			@NonNull final InterestRunId interestRunId,
			@NonNull final InvoicingGroupId invoicingGroupId,
			@NonNull final Money interestToDistribute,
			@NonNull final LockOwner lockOwner,
			@NonNull final CurrencyPrecision interestCurrencyPrecision,
			@Nullable final BonusComputationDetails bonusComputationDetails)
	{
		this.interestRunId = interestRunId;
		this.invoicingGroupId = invoicingGroupId;
		this.interestToDistribute = interestToDistribute;
		this.lockOwner = lockOwner;
		this.interestCurrencyPrecision = interestCurrencyPrecision;
		this.bonusComputationDetails = bonusComputationDetails;
	}

	@NonNull
	public ComputingMethodType getComputingMethodType()
	{
		return bonusComputationDetails != null
				? ComputingMethodType.SubtractValueOnInterim
				: ComputingMethodType.AddValueOnInterim;
	}
}
