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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Direct debit mandates, indexed by bank account: per bank account the first mandate of the list is kept.
 * The caller decides the precedence by the order of the list (e.g. {@link DirectDebitMandateRepository} puts the mandate of the specific org before the one of org 0).
 */
@ToString
public class DirectDebitMandatesMap
{
	public static final DirectDebitMandatesMap EMPTY = new DirectDebitMandatesMap(ImmutableList.of());

	/**
	 * @param mandates in order of precedence; per bank account the first one wins
	 */
	public static DirectDebitMandatesMap ofList(@NonNull final List<DirectDebitMandate> mandates)
	{
		return !mandates.isEmpty() ? new DirectDebitMandatesMap(mandates) : EMPTY;
	}

	private final ImmutableMap<BankAccountId, DirectDebitMandate> byBankAccountId;

	private DirectDebitMandatesMap(@NonNull final List<DirectDebitMandate> mandates)
	{
		final Map<BankAccountId, DirectDebitMandate> firstByBankAccountId = new LinkedHashMap<>();
		mandates.forEach(mandate -> firstByBankAccountId.putIfAbsent(mandate.getBankAccountId(), mandate));
		this.byBankAccountId = ImmutableMap.copyOf(firstByBankAccountId);
	}

	/**
	 * @return the mandate of the given bank account; empty if there is none
	 */
	public Optional<DirectDebitMandate> getMandate(@NonNull final BankAccountId bankAccountId)
	{
		return Optional.ofNullable(byBankAccountId.get(bankAccountId));
	}
}
