/*
 * #%L
 * de.metas.business
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

package de.metas.banking.mandate;

import de.metas.banking.BankAccountId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Optional;

/**
 * A direct debit mandate of a debtor's bank account.
 * The validity rules live here, so that every use case applies the same rule.
 */
@Value
@Builder(toBuilder = true)
public class DirectDebitMandate
{
	/**
	 * Months after the reference date (last use, else mandate date) after which a mandate is no longer usable.
	 */
	private static final int EXPIRY_MONTHS = 36;

	@NonNull DirectDebitMandateId id;
	@NonNull BankAccountId bankAccountId;
	@NonNull OrgId orgId;
	@NonNull DirectDebitMandateType type;
	@NonNull String reference;
	@NonNull LocalDate mandateDate;
	boolean isRecurring;
	@Nullable LocalDate dateLastUsed;

	/**
	 * @return the date of the last use, or the mandate date if the mandate was never used
	 */
	public LocalDate getReferenceDate()
	{
		return dateLastUsed != null ? dateLastUsed : mandateDate;
	}

	/**
	 * @return {@code true} if the given collection date is more than 36 months after the {@link #getReferenceDate()};
	 * exactly 36 months after it is still valid.
	 */
	public boolean isExpiredAt(@NonNull final LocalDate collectionDate)
	{
		return collectionDate.isAfter(getReferenceDate().plusMonths(EXPIRY_MONTHS));
	}

	/**
	 * @return {@code true} for a one-off mandate that was already used once (so it must not be collected again)
	 */
	public boolean isOneOffAlreadyUsed()
	{
		return !isRecurring && dateLastUsed != null;
	}

	/**
	 * Records a use of this mandate for a collection on the given date.
	 * The last use only ever moves forward: a mandate already used on or after {@code collectionDate} keeps its {@code dateLastUsed}.
	 *
	 * @return this mandate with {@code dateLastUsed = collectionDate}, or empty if its {@code dateLastUsed} does not change
	 */
	public Optional<DirectDebitMandate> withUsedOn(@NonNull final LocalDate collectionDate)
	{
		if (dateLastUsed != null && !dateLastUsed.isBefore(collectionDate))
		{
			return Optional.empty();
		}
		return Optional.of(toBuilder().dateLastUsed(collectionDate).build());
	}
}
