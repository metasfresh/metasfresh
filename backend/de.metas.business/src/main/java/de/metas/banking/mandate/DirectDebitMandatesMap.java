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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.banking.BankAccountId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

/**
 * The active mandates of a set of bank accounts, as seen by one export org:
 * the export org's own mandates, with the org-0 ("*") mandates as fallback.
 */
@ToString
public class DirectDebitMandatesMap
{
	public static final DirectDebitMandatesMap EMPTY = new DirectDebitMandatesMap(ImmutableList.of(), OrgId.ANY);

	/**
	 * @param mandates active mandates, at most one per bank account and org (guaranteed by the DB unique index);
	 *                 only those of {@code exportOrgId} and of org 0 are indexed, others are ignored
	 */
	public static DirectDebitMandatesMap ofList(@NonNull final List<DirectDebitMandate> mandates, @NonNull final OrgId exportOrgId)
	{
		return !mandates.isEmpty() ? new DirectDebitMandatesMap(mandates, exportOrgId) : EMPTY;
	}

	private final ImmutableMap<BankAccountId, DirectDebitMandate> byBankAccountId;

	private DirectDebitMandatesMap(@NonNull final List<DirectDebitMandate> mandates, @NonNull final OrgId exportOrgId)
	{
		this.byBankAccountId = mandates.stream()
				.filter(mandate -> OrgId.equals(mandate.getOrgId(), exportOrgId) || mandate.getOrgId().isAny())
				// a bank account with both: the export org's mandate wins over the org-0 one
				.collect(ImmutableMap.toImmutableMap(
						DirectDebitMandate::getBankAccountId,
						mandate -> mandate,
						(mandate1, mandate2) -> mandate1.getOrgId().isAny() ? mandate2 : mandate1));
	}

	/**
	 * @return the export org's mandate of the given bank account, else the org-0 mandate; empty if there is none
	 */
	public Optional<DirectDebitMandate> getMandate(@NonNull final BankAccountId bankAccountId)
	{
		return Optional.ofNullable(byBankAccountId.get(bankAccountId));
	}
}
