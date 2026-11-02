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
import com.google.common.collect.Multimaps;
import de.metas.banking.BankAccountId;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
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
		this.byBankAccountId = Multimaps.index(mandates, DirectDebitMandate::getBankAccountId)
				.asMap()
				.entrySet()
				.stream()
				.collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, entry -> regularOrgMandateWins(entry.getValue())));
	}

	/**
	 * @param mandates the mandates of one bank account
	 * @return the mandate of the regular org, else the one of org 0
	 * @throws AdempiereException if there is more than one mandate of a regular org or more than one of org 0
	 */
	private static DirectDebitMandate regularOrgMandateWins(@NonNull final Collection<DirectDebitMandate> mandates)
	{
		final List<DirectDebitMandate> regularOrgMandates = mandates.stream().filter(mandate -> mandate.getOrgId().isRegular()).collect(ImmutableList.toImmutableList());
		final List<DirectDebitMandate> orgAnyMandates = mandates.stream().filter(mandate -> mandate.getOrgId().isAny()).collect(ImmutableList.toImmutableList());
		if (regularOrgMandates.size() > 1 || orgAnyMandates.size() > 1)
		{
			throw new AdempiereException("Expected per bank account at most one mandate of a regular org and one of org 0")
					.appendParametersToMessage()
					.setParameter("mandates", mandates);
		}
		return !regularOrgMandates.isEmpty() ? regularOrgMandates.get(0) : orgAnyMandates.get(0);
	}

	/**
	 * @return the mandate of the given bank account; empty if there is none
	 */
	public Optional<DirectDebitMandate> getMandate(@NonNull final BankAccountId bankAccountId)
	{
		return Optional.ofNullable(byBankAccountId.get(bankAccountId));
	}
}
