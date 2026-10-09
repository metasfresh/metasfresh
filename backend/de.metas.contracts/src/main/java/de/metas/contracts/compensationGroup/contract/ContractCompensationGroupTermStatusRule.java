package de.metas.contracts.compensationGroup.contract;

import de.metas.contracts.FlatrateTermStatus;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Optional;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2026 metas GmbH
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
 * The contract status rules of completed {@code CompensationGroup} contract terms:
 * <ul>
 *     <li>on completion, a term without a status gets "not yet started" ({@code Wa}) when its start date lies in the future,
 *     else "running" ({@code Ru})</li>
 *     <li>daily, "not yet started" becomes "running" once the start date is reached, and "running" becomes "contract end"
 *     ({@code Ec}) once the end date has passed and the term was not extended</li>
 * </ul>
 * Any other status (e.g. quit, voided, contract end) is never changed.
 */
@UtilityClass
public class ContractCompensationGroupTermStatusRule
{
	/**
	 * @return the status to set when the term is completed, or empty if the term already has a status
	 */
	public static Optional<FlatrateTermStatus> computeStatusOnComplete(
			@Nullable final FlatrateTermStatus currentStatus,
			@NonNull final LocalDate startDate,
			@NonNull final LocalDate today)
	{
		if (currentStatus != null)
		{
			return Optional.empty();
		}
		return Optional.of(startDate.isAfter(today) ? FlatrateTermStatus.Waiting : FlatrateTermStatus.Running);
	}

	/**
	 * @param extended whether the term has a follow-up term ({@code C_FlatrateTerm_Next_ID})
	 * @return the new status if the daily update changes it, else empty
	 */
	public static Optional<FlatrateTermStatus> computeStatusUpdate(
			@Nullable final FlatrateTermStatus currentStatus,
			@NonNull final LocalDate startDate,
			@Nullable final LocalDate endDate,
			final boolean extended,
			@NonNull final LocalDate today)
	{
		FlatrateTermStatus status = currentStatus;
		if (status == FlatrateTermStatus.Waiting && !startDate.isAfter(today))
		{
			status = FlatrateTermStatus.Running;
		}
		if (status == FlatrateTermStatus.Running && endDate != null && endDate.isBefore(today) && !extended)
		{
			status = FlatrateTermStatus.EndingContract;
		}
		return status != currentStatus ? Optional.of(status) : Optional.empty();
	}
}
