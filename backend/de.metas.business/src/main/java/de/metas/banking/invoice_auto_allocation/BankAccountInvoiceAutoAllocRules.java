/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.banking.invoice_auto_allocation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.banking.BankAccountId;
import de.metas.document.DocTypeId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.List;

@EqualsAndHashCode
@ToString
public class BankAccountInvoiceAutoAllocRules
{
	static final BankAccountInvoiceAutoAllocRules NO_RESTRICTIONS = new BankAccountInvoiceAutoAllocRules(ImmutableList.of());

	private final ImmutableListMultimap<DocTypeId, BankAccountInvoiceAutoAllocRule> rulesByInvoiceDocTypeId;
	private final ImmutableListMultimap<BankAccountId, BankAccountInvoiceAutoAllocRule> rulesByBankAccountId;

	private BankAccountInvoiceAutoAllocRules(@NonNull final List<BankAccountInvoiceAutoAllocRule> rules)
	{
		rulesByInvoiceDocTypeId = Multimaps.index(rules, BankAccountInvoiceAutoAllocRule::getInvoiceDocTypeId);
		rulesByBankAccountId = Multimaps.index(rules, BankAccountInvoiceAutoAllocRule::getBankAccountId);
	}

	public static BankAccountInvoiceAutoAllocRules ofRulesList(@NonNull final List<BankAccountInvoiceAutoAllocRule> rules)
	{
		return !rules.isEmpty()
				? new BankAccountInvoiceAutoAllocRules(rules)
				: NO_RESTRICTIONS;
	}

	public boolean isAutoAllocate(
			@Nullable final BankAccountId bankAccountId,
			@NonNull final DocTypeId invoiceDocTypeId)
	{
		final ImmutableList<BankAccountInvoiceAutoAllocRule> rulesForInvoiceDocTypeId = rulesByInvoiceDocTypeId.get(invoiceDocTypeId);
		if (bankAccountId == null) // no bankAccountId - let the allocation go through if there is no restriction for the given doctype
		{
			return rulesForInvoiceDocTypeId.isEmpty();
		}
		
		if (rulesForInvoiceDocTypeId.isEmpty())
		{
			final ImmutableList<BankAccountInvoiceAutoAllocRule> rulesForBankAccountId = rulesByBankAccountId.get(bankAccountId);
			return rulesForBankAccountId.isEmpty();
		}
		else
		{
			return rulesForInvoiceDocTypeId.stream().anyMatch(rule -> BankAccountId.equals(rule.getBankAccountId(), bankAccountId));
		}
	}
}
