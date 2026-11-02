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
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.Optional;

/**
 * Direct debit mandates, indexed by bank account.
 * Per bank account it holds one mandate; if there is one of a regular org and one of org 0 ("*"), the regular org's mandate is kept.
 */
@ToString
public class DirectDebitMandatesMap
{
	public static final DirectDebitMandatesMap EMPTY = new DirectDebitMandatesMap(ImmutableList.of());

	/**
	 * @param mandates per bank account at most one mandate of a regular org and one of org 0
	 */
	public static DirectDebitMandatesMap ofList(@NonNull final List<DirectDebitMandate> mandates)
	{
		return !mandates.isEmpty() ? new DirectDebitMandatesMap(mandates) : EMPTY;
	}

	private final ImmutableMap<BankAccountId, DirectDebitMandate> byBankAccountId;

	private DirectDebitMandatesMap(@NonNull final List<DirectDebitMandate> mandates)
	{
		this.byBankAccountId = mandates.stream()
				.collect(ImmutableMap.toImmutableMap(
						DirectDebitMandate::getBankAccountId,
						mandate -> mandate,
						DirectDebitMandatesMap::regularOrgMandateWins));
	}

	/**
	 * @return the mandate of the regular org
	 * @throws AdempiereException if both mandates are of org 0 or both are of a regular org
	 */
	private static DirectDebitMandate regularOrgMandateWins(@NonNull final DirectDebitMandate mandate1, @NonNull final DirectDebitMandate mandate2)
	{
		if (mandate1.getOrgId().isAny() == mandate2.getOrgId().isAny())
		{
			throw new AdempiereException("Expected per bank account at most one mandate of a regular org and one of org 0")
					.appendParametersToMessage()
					.setParameter("mandate1", mandate1)
					.setParameter("mandate2", mandate2);
		}
		return mandate1.getOrgId().isAny() ? mandate2 : mandate1;
	}

	/**
	 * @return the mandate of the given bank account; empty if there is none
	 */
	public Optional<DirectDebitMandate> getMandate(@NonNull final BankAccountId bankAccountId)
	{
		return Optional.ofNullable(byBankAccountId.get(bankAccountId));
	}
}
